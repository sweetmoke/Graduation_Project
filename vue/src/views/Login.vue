<template>
  <div class="auth-container">
    <el-card class="auth-card">
      <!-- 系统名称 -->
      <h2 class="system-title">中小学生安全知识智能问答系统</h2>

      <h2 class="title">登录</h2>
      <el-form :model="loginForm" :rules="rules" ref="loginFormRef" label-width="90px">
        <!-- 身份选择 -->
        <el-form-item label="身份" prop="role">
          <el-select v-model="loginForm.role" placeholder="请选择身份" class="full-width">
            <el-option label="管理员" value="1" />
            <el-option label="用户" value="2" />
<!--            <el-option label="老师" value="3" />-->
          </el-select>
        </el-form-item>

        <el-form-item label="用户名" prop="userName">
          <el-input v-model="loginForm.userName" placeholder="请输入用户名" />
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="loginForm.password" type="password" placeholder="请输入密码" show-password/>
        </el-form-item>
        <div class="button-wrapper">
          <el-button type="primary" class="submit-btn" @click="Login">登录</el-button>
        </div>
        <div class="link-wrapper">
          <span>还没有账号？</span>
          <a @click="$router.push('/register')">去注册</a>
        </div>
      </el-form>
    </el-card>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  data() {
    return {
      loginForm: {
        role: '',
        userName: '',
        password: ''
      },
      rules: {
        role: [{ required: true, message: '请选择身份', trigger: 'change' }],
        userName: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
        password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
      }
    };
  },
  methods: {
    Login() {
      this.$refs.loginFormRef.validate(valid => {
        if (valid) {
          //走父类account的登录接口
            request.post("/account/login", this.loginForm).then(res=>{
              if (res.code === '0'){
                this.$message.success('登录成功');
                //获取后端传来的token和user对象
                let token = res.data.token;
                let user = res.data.user;
                // console.log(res.data.token)

                //拿到之后，存到前端缓存里
                localStorage.setItem("token",token)
                localStorage.setItem("user",JSON.stringify(user))
                // localStorage.getItem("")

                this.$router.push("/home/homepage")
                // 登录逻辑
              } else {
                this.$message.error(res.msg);
              }
            })

          //管理员登录
          // if (this.loginForm.role === '1'){
          //   request.post("/admin/login", this.loginForm).then(res=>{
          //     if (res.code === '0'){
          //       this.$message.success('登录成功');
          //       this.$router.push("/home/homepage")
          //       // 登录逻辑
          //     } else {
          //       this.$message.error(res.msg);
          //     }
          //   })
          // }
          //用户登录
          // if (this.loginForm.role === '2'){
          //   request.post("/user/login", this.loginForm).then(res=>{
          //     if (res.code === '0'){
          //       this.$message.success('登录成功');
          //       this.$router.push("/home/homepage")
          //       // 登录逻辑
          //     } else {
          //       this.$message.error(res.msg);
          //     }
          //   })
          // }


        }
      });
    }
  }
};
</script>

<style scoped>
.auth-container {
  display: flex;
  justify-content: center;
  align-items: center;
  background: linear-gradient(145deg, #daf5f3 0%, #00796b 100%);
  min-height: 100vh;
  padding: 20px;
  box-sizing: border-box;
}

.auth-card {
  background: #fff;
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
  padding: 30px 20px;
  width: 100%;
  max-width: 550px;
}

.system-title {
  text-align: center;
  font-size: 2em;
  color: #037c74;
  margin-bottom: 20px;
  font-weight: bold;
}

.title {
  text-align: center;
  margin-bottom: 20px;
  font-weight: bold;
  font-size: 24px;
}

.button-wrapper {
  display: flex;
  justify-content: center;
  margin-top: 10px;
}

.submit-btn {
  background-color: #009688;
  border-color: #009688;
  color: #fff;
  width: 200px;
  transition: transform 0.2s ease;
}

.submit-btn:hover {
  transform: scale(1.05);
  background-color: #00796b;
  border-color: #00796b;
}

.link-wrapper {
  text-align: center;
  margin-top: 15px;
  font-size: 14px;
}

.link-wrapper a {
  color: #009688;
  cursor: pointer;
  margin-left: 5px;
  text-decoration: none;
  transition: color 0.3s;
}

.link-wrapper a:hover {
  color: #00796b;
}

.el-input input:focus {
  border-color: #409EFF !important;
}

.full-width {
  width: 100%;
}

@media (max-width: 480px) {
  .auth-card {
    padding: 20px 15px;
  }

  .submit-btn {
    width: 100%;
  }
}
</style>
