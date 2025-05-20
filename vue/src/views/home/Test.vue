<template>
  <div class="admin">
    <h1>安全情境模拟</h1>

    <div v-if="loading" class="loading">加载中...</div>
    <div v-else-if="error" class="error">错误：{{ error }}</div>
    <div v-else>
      <!-- 下拉选择情境标题 -->
      <el-form label-width="200px">
        <el-form-item label="请选择一个情境：">
          <el-select v-model="selectedIndex" placeholder="请选择情境标题" style="width: 200px">
            <el-option
                v-for="(scenario, index) in scenarios"
                :key="scenario.id"
                :label="scenario.title"
                :value="index"
            />
          </el-select>
        </el-form-item>
      </el-form>

      <!-- 展示选择的情境 -->
      <div v-if="selectedIndex !== ''">
        <el-card class="scenario-card" shadow="always">
          <h2>{{ scenarios[selectedIndex].title }}</h2>
          <p><strong>情境描述：</strong>{{ scenarios[selectedIndex].context }}</p>
          <p><strong>问题：</strong>{{ scenarios[selectedIndex].question }}</p>

          <el-input
              type="textarea"
              v-model="scenarios[selectedIndex].userAnswer"
              placeholder="请详细描述你的应对方法"
              :rows="5"
              resize="vertical"
          />

          <div class="btn-group">
            <el-button type="primary" @click="submitAnswer(selectedIndex)">提交回答</el-button>
          </div>

          <!-- 判断关键词匹配结果 -->
          <div
              v-if="scenarios[selectedIndex].result !== null"
              class="result"
              :class="{
              correct: scenarios[selectedIndex].result,
              incorrect: !scenarios[selectedIndex].result
            }"
          >
            <span v-if="scenarios[selectedIndex].result">回答正确 ✔️</span>
            <span v-else>回答缺少关键词 ❌</span>
            <p><strong>正确关键词：</strong>{{ scenarios[selectedIndex].correctKeywords.join('，') }}</p>
          </div>

          <!-- AI 点评反馈 -->
          <div
              v-if="scenarios[selectedIndex].feedback"
              class="result"
              style="white-space: pre-line; margin-top: 10px; background: #eaf4ff; padding: 10px; border-radius: 5px; border: 1px solid #90caf9;"
          >
            <strong>AI点评：</strong>
            <p>{{ scenarios[selectedIndex].feedback }}</p>
          </div>
        </el-card>
      </div>
    </div>
  </div>
</template>

<script>
import { ElMessage } from 'element-ui';

export default {
  name: 'Test',
  data() {
    return {
      scenarios: [],
      loading: true,
      error: null,
      selectedIndex: '',
    };
  },
  async created() {
    try {
      const token = localStorage.getItem('token');
      const res = await fetch('http://localhost:9090/scenario/all', {
        headers: { token }
      });
      if (!res.ok) throw new Error('接口请求失败');
      const data = await res.json();
      console.log('后端返回数据:', data); // 👈 加上这行

      this.scenarios = data.map(s => ({
        ...s,
        userAnswer: '',
        result: null,
        feedback: ''
      }));
    } catch (err) {
      this.error = err.message;
    } finally {
      this.loading = false;
    }
  },
  methods: {
    async submitAnswer(index) {
      const scenario = this.scenarios[index];
      const answer = scenario.userAnswer.trim();
      if (!answer) {
        ElMessage.warning('请先输入回答内容');
        return;
      }

      // 关键词判断
      const containsAll = scenario.correctKeywords.every(keyword =>
          answer.includes(keyword)
      );
      scenario.result = containsAll;

      // 构造提示词进行 AI 点评
      const prompt = `你是一位专注于中小学生安全教育的“安全小导师”，具备丰富的校园安全、交通安全、防拐骗、防溺水、防校园欺凌、消防知识、网络安全和急救常识等相关知识，能够用简单易懂、适合学生理解的方式进行科普讲解。
现在你给学生出了一个"${scenario.title}"类型题目，情境是"${scenario.context}"，题目是"${scenario.question}"，学生答题的关键点是"${scenario.correctKeywords.join('，')}"。
学生的回答是："${answer}"。
请你点评该回答是否正确，是否完整，有没有改进建议，用鼓励和引导的语气反馈给学生。`;

      try {
        const res = await fetch('http://localhost/api/chat-messages', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({
            response_mode: 'streaming',
            conversation_id: 'b7746b1b-fb7a-44cf-9d2b-5ad12671de6a',
            files: [],
            query: prompt,
            inputs: {},
            parent_message_id: '606bfd94-f714-4cc5-8149-e86b66c8b11a'
          })
        });

        if (!res.ok) throw new Error('请求失败');

        const resultText = await res.text(); // 假设服务端返回纯文本
        scenario.feedback = resultText;
      } catch (err) {
        scenario.feedback = 'AI 点评请求失败，请稍后再试。';
      }
    }
  }
};
</script>

<style scoped>
.admin {
  width: 100%;
  max-width: 900px;
  margin: 0 auto;
  padding: 40px;
  border: 1px solid #cdcdcd;
  border-radius: 10px;
  box-shadow: 4px 4px 10px rgba(218, 218, 218, 0.534);
  background-color: #fff;
}

h1 {
  text-align: center;
  margin-bottom: 30px;
}

.scenario-card {
  margin-top: 20px;
  background: #f9f9f9;
}

.scenario-card h2 {
  margin-top: 0;
  color: #333;
  margin-bottom: 15px;
}

.btn-group {
  margin-top: 15px;
}

.result {
  margin-top: 20px;
  font-weight: bold;
  padding: 12px;
  border-radius: 4px;
}

.correct {
  color: #155724;
  background-color: #d4edda;
  border: 1px solid #c3e6cb;
}

.incorrect {
  color: #721c24;
  background-color: #f8d7da;
  border: 1px solid #f5c6cb;
}

.loading,
.error {
  text-align: center;
  font-size: 18px;
  color: #666;
}
.error {
  color: red;
}
</style>
