<template>
  <div class="homepage-container">
    <el-card shadow="hover" class="welcome-card">
      <h2>欢迎你，{{ user.userName || '同学' }}！</h2>
      <p>这里是中小学生安全知识智能问答系统的首页，祝你学习安全知识，健康成长！</p>
    </el-card>

    <el-row :gutter="24" class="modules-row">
      <el-col
          v-for="module in filteredModules"
          :key="module.title"
          :xs="24"
          :sm="12"
          :md="8"
          :lg="6"
      >
        <router-link :to="module.path" class="router-card-link">
          <el-card
              shadow="always"
              class="module-card"
              :body-style="{ padding: '20px' }"
          >
            <h3>{{ module.title }}</h3>
            <p>{{ module.desc }}</p>
          </el-card>
        </router-link>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: "HomePage",
  data() {
    return {
      user: JSON.parse(localStorage.getItem("user") || "{}"),
      modules: [
        {
          title: "安全小导师",
          desc: "智能问答，帮你解答安全疑问。",
          path: "/home/chat",
        },
        {
          title: "情境模拟",
          desc: "通过模拟练习提升安全应对能力。",
          path: "/home/test",
        },
        {
          title: "安全中心",
          desc: "查看系统发布的最新安全知识。",
          path: "/home/information",
        },
        {
          title: "管理员管理",
          desc: "系统用户及权限管理。",
          path: "/home/admin",
          requireRole: 1,
        },
        {
          title: "用户管理",
          desc: "管理普通用户信息。",
          path: "/home/user",
          requireRole: 1,
        },
        {
          title: "个人中心",
          desc: "查看和编辑个人信息。",
          path: "/home/user-center",
        },
      ],
    };
  },
  computed: {
    filteredModules() {
      return this.modules.filter((mod) => {
        if (mod.requireRole !== undefined) {
          return this.user.role === mod.requireRole;
        }
        return true;
      });
    },
  },
};
</script>

<style scoped>
.homepage-container {
  max-width: 1200px;
  margin: 40px auto 0;
}

.welcome-card {
  background: #e6f7ff;
  border-radius: 10px;
  padding: 30px 20px;
  box-shadow: 0 6px 15px rgba(24, 144, 255, 0.2);
  margin-bottom: 40px;
  text-align: center;
  color: #096dd9;
}

.welcome-card h2 {
  font-weight: 700;
  font-size: 2em;
  margin-bottom: 10px;
}

.welcome-card p {
  font-size: 1.2em;
  color: #595959;
}

.modules-row {
  margin-top: 20px;
}

.module-card {
  cursor: pointer;
  border-radius: 12px;
  margin: 6px 0px 10px 6px;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.12);
  background: #ffffff;
  user-select: none;
  height: 140px;
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.module-card h3 {
  color: #1890ff;
  font-weight: 600;
  font-size: 1.4em;
  margin-bottom: 8px;
  user-select: none;
}

.module-card p {
  color: #595959;
  font-size: 1em;
  user-select: none;
}

/* 鼠标悬停动画效果 */
.module-card:hover {
  transform: translateY(-8px);
  box-shadow: 0 12px 24px rgba(24, 144, 255, 0.35);
  background: #f0f5ff;
}

/* Router link 样式修复 */
.router-card-link {
  text-decoration: none;
  color: inherit;
  display: block;
}

/* 移动端适配 */
@media (max-width: 768px) {
  .homepage-container {
    padding: 0 10px;
  }
}
</style>
