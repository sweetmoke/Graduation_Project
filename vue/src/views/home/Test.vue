<template>
  <div class="test">
    <el-card>
      <el-form label-position="top">
        <el-form-item label="选择一个安全情境：">
          <el-select v-model="selectedScenario" placeholder="请选择一个情境">
            <el-option
                v-for="(scenario, index) in scenarios"
                :key="index"
                :label="scenario.title"
                :value="index">
            </el-option>
          </el-select>
        </el-form-item>

        <div v-if="currentScenario">
          <el-alert :title="currentScenario.context" type="info" show-icon></el-alert>
          <p class="question"><strong>引导问题：</strong>{{ currentScenario.question }}</p>

          <el-form-item label="你的应对方法：">
            <el-input
                type="textarea"
                :rows="4"
                v-model="userAnswer"
                placeholder="请输入你的答案" />
          </el-form-item>

          <el-button type="primary" @click="evaluateAnswer">提交答案</el-button>

          <div v-if="feedback" class="feedback">
            <el-alert
                :title="feedback"
                :type="feedbackType"
                show-icon
                :closable="false"
                style="margin-top: 20px;" />
          </div>
        </div>
      </el-form>
    </el-card>
  </div>
</template>

<script>
export default {
  name: 'Test',
  data() {
    return {
      selectedScenario: null,
      userAnswer: '',
      feedback: '',
      feedbackType: 'success',
      scenarios: [
        {
          title: '校园突发火灾演练',
          context: '放学后你还在教室收拾书包，突然外面有人大喊“起火了！快跑！”你看到走廊外有烟雾飘进来。',
          question: '你会怎么做？请详细描述你的逃生方法。',
          correctKeywords: ['湿毛巾', '捂住口鼻', '弯腰', '逃生通道', '不乘电梯'],
        },
        {
          title: '地震发生在学校时',
          context: '你正在上课，地面突然震动，吊灯开始晃动，老师大喊“地震了！”',
          question: '你该怎么应对这场地震？',
          correctKeywords: ['躲在桌子下', '保护头部', '不乱跑', '震后有序撤离'],
        },
        {
          title: '朋友在河边落水',
          context: '你和朋友在小河边玩，一个同学不小心掉进水里，正在挣扎。',
          question: '你会怎么救他？',
          correctKeywords: ['不跳水', '呼救', '找树枝', '绳子', '大人帮忙'],
        },
      ]
    };
  },
  computed: {
    currentScenario() {
      return this.scenarios[this.selectedScenario] || null;
    }
  },
  methods: {
    evaluateAnswer() {
      if (!this.userAnswer.trim()) {
        this.feedback = '请先填写你的应对方法。';
        this.feedbackType = 'warning';
        return;
      }

      const keywords = this.currentScenario.correctKeywords;
      const answer = this.userAnswer.toLowerCase();
      const matched = keywords.filter(k => answer.includes(k.toLowerCase()));

      if (matched.length >= Math.ceil(keywords.length / 2)) {
        this.feedback = '你的答案很棒，已经包含了多数关键点，继续保持！';
        this.feedbackType = 'success';
      } else {
        this.feedback = '你的答案有待改进，注意关键点如：' + keywords.join('、');
        this.feedbackType = 'error';
      }
    }
  }
};
</script>

<style scoped>
.test {
  max-width: 700px;
  margin: 40px auto;
}
.question {
  margin: 15px 0;
  font-size: 16px;
}
</style>
