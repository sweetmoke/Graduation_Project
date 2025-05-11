<template>
  <div class="auth-container">
    <el-card class="auth-card">
      <!-- 系统名称 -->
      <h2 class="system-title">中小学生安全知识智能问答系统</h2>

      <h2 class="title">注册</h2>
      <el-form :model="registerForm" :rules="rules" ref="registerFormRef" label-width="90px">
        <!-- 身份选择 -->
        <el-form-item label="身份" prop="role">
          <el-select v-model="registerForm.role" placeholder="请选择身份" class="full-width">
            <!-- <el-option label="管理员" value="admin" /> -->
            <el-option label="用户" value="2" />
          </el-select>
        </el-form-item>

        <el-form-item label="用户名" prop="userName">
          <el-input v-model="registerForm.userName" placeholder="请输入用户名" />
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="registerForm.password" type="password" placeholder="请输入密码" show-password/>
        </el-form-item>
        <el-form-item label="确认密码" prop="confirmPassword">
          <el-input v-model="registerForm.confirmPassword" type="password" placeholder="请确认密码" show-password/>
        </el-form-item>
        <div class="button-wrapper">
          <el-button type="primary" class="submit-btn" @click="Register">注册</el-button>
        </div>
        <div class="link-wrapper">
          <span>已有账号？</span>
          <a @click="$router.push('/login')">去登录</a>
        </div>
      </el-form>
    </el-card>
  </div>
</template>

<script>
import request from "@/utils/request";
export default {
  name: 'Register',
  data() {
    return {
      registerForm: {
        role: '',
        userName: '',
        password: '',
        confirmPassword: ''
      },
      rules: {
        role: [{ required: true, message: '请选择身份', trigger: 'change' }],
        userName: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
        password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
        confirmPassword: [
          { required: true, message: '请确认密码', trigger: 'blur' },
          {
            validator: (rule, value, callback) => {
              if (value !== this.registerForm.password) {
                callback(new Error('两次密码输入不一致'));
              } else {
                callback();
              }
            },
            trigger: 'blur'
          }
        ]
      }
    };
  },
  methods: {
    Register() {
      this.$refs.registerFormRef.validate(valid => {
        if (valid) {
          request.post("user/register", this.registerForm).then(res => {
            if (res.code === '0') {
              this.$message.success("注册成功");
              this.$router.push("/home/homepage");
            } else {
              this.$message.error(res.msg);
            }
          });
        } else {
          this.$message.error("请完善信息");
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
  background: linear-gradient(325deg, #037c72 0%, #c0ffda 100%);
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

/* 响应式支持 */
@media (max-width: 480px) {
  .auth-card {
    padding: 20px 15px;
  }

  .submit-btn {
    width: 100%;
  }
}
</style>
