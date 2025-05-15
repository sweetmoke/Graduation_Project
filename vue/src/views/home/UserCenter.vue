<template>
  <div>
    <!-- 安全提示 -->
    <el-alert
        title="温馨提示：请妥善保管您的账号信息，定期修改密码，防止账号被盗。"
        type="info"
        show-icon
        style="margin-bottom: 15px;"
    />
    <!-- 个人信息卡片 -->
    <el-card class="box-card" shadow="hover">
      <div slot="header" class="clearfix">
        <strong>个人信息</strong>
      </div>

      <el-form :model="userForm" :rules="rules" ref="userFormRef" label-width="80px" label-position="left">
        <el-form-item label="用户名" prop="userName">
          <el-input :value="userForm.userName" disabled></el-input>
        </el-form-item>

        <el-form-item label="身份">
          <el-input :value="userForm.role === 1 ? '管理员' : '用户'" disabled></el-input>
        </el-form-item>

        <el-form-item label="年龄" prop="age">
          <el-input v-model="userForm.age" />
        </el-form-item>

        <el-form-item label="性别" prop="gender">
          <el-radio-group v-model="userForm.gender">
            <el-radio label="男">男</el-radio>
            <el-radio label="女">女</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="电话" prop="phone">
          <el-input v-model="userForm.phone" placeholder="请输入电话"></el-input>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="save">保存信息</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <!-- 修改密码卡片 -->
    <el-card class="box-card" shadow="hover" style="margin-top: 20px;">
      <div slot="header" class="clearfix">
        <strong>修改密码</strong>
      </div>

      <el-form :model="passwordForm" :rules="passwordRules" ref="passwordFormRef" label-width="100px">
        <el-form-item label="原密码" prop="oldPassword">
          <el-input v-model="passwordForm.oldPassword" show-password></el-input>
        </el-form-item>
        <el-form-item label="新密码" prop="newPassword">
          <el-input v-model="passwordForm.newPassword" show-password></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="confirmPassword">
          <el-input v-model="passwordForm.confirmPassword" show-password></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onChangePassword">确认修改</el-button>
        </el-form-item>
      </el-form>
    </el-card>


  </div>
</template>

<script>
import request from '@/utils/request';

export default {
  name: 'UserCenter',
  data() {
    return {
      // 定义 User 对象，从缓存中取值
      user: JSON.parse(localStorage.getItem("user") || "{}"),
      userForm: {
        id: '',
        userName: '',
        role: 0,
        age: '',
        gender: '',
        phone: '',
      },
      passwordForm: {
        oldPassword: '',
        newPassword: '',
        confirmPassword: '',
      },
      rules: {
        phone: [
          // 正则表达式：手机号必须以 1 开头，第二位是 3 到 9 之间的数字，后面跟 9 个数字
          { pattern: /^1[3-9]\d{9}$/, message: '电话格式不正确', trigger: 'blur' }
        ],
        gender: [
          { message: '请选择性别', trigger: 'change' }
        ],
        age: [
          // 正则表达式：年龄必须是一个正整数，且不能以零开头
          { pattern: /^[1-9][0-9]*$/, message: '请输入正确的年龄', trigger: 'blur' }
        ]
      },
      passwordRules: {
        oldPassword: [{ required: true, message: '请输入原密码', trigger: 'blur' }],
        newPassword: [{ required: true, message: '请输入新密码', trigger: 'blur' }],
        confirmPassword: [
          { required: true, message: '请再次输入新密码', trigger: 'blur' },
          {
            validator: (rule, value, callback) => {
              if (value !== this.passwordForm.newPassword) {
                callback(new Error('两次输入的密码不一致'));
              } else {
                callback();
              }
            }, trigger: 'blur'
          }
        ]
      },
      originalUserName: '',
    };
  },
  mounted() {
    const user = JSON.parse(localStorage.getItem('user') || '{}');
    this.userForm = { ...user };
    this.originalUserName = user.userName;
  },
  methods: {
    async save() {
      this.$refs.userFormRef.validate(async (valid) => {
        if (!valid) return;

        let url = '';
        if (this.userForm.role === 1) {
          url = '/admin/edit';
        } else if (this.userForm.role === 2) {
          url = '/user/edit';
        }

        const res = await request.post(url, this.userForm);
        if (res.code === '0') {
          this.$message.success('信息更新成功');
          localStorage.setItem('user', JSON.stringify(this.userForm));
          this.dialogVisible = false;
          this.load && this.load();
        } else {
          this.$message.error(res.msg || '更新失败');
        }
      });
    },
    async onChangePassword() {
      this.$refs.passwordFormRef.validate(async (valid) => {
        if (!valid) return;

        // 先校验原密码是否正确
        // if (this.passwordForm.oldPassword !== this.user.password) {
        //   this.$message.error('原密码错误');
        //   return;
        // }

        // 设置新密码
        this.userForm.password = this.passwordForm.newPassword;

        // 确定请求地址
        let url = '';
        if (this.userForm.role === 1) {
          url = '/admin/edit';
        } else if (this.userForm.role === 2) {
          url = '/user/edit';
        }

        // 发送请求
        const res = await request.post(url, this.userForm);
        if (res.code === '0') {
          this.$message.success('密码修改成功，请重新登录');
          localStorage.removeItem('user');
          this.$router.push('/login');
        } else {
          this.$message.error(res.msg || '密码修改失败');
        }
      });
    }


  }
};
</script>

<style scoped>
.admin {
  width: 100%;
  height: 1000px;
  padding: 40px;
  border: 1px solid #cdcdcd;
  border-radius: 10px;
  box-shadow: 4px 4px 10px rgba(218, 218, 218, 0.534);
  /* 阴影效果 */
}

.box-card {
  background-color: #fbfffd;
  padding: 40px;
  border: 1px solid #cdcdcd;
  border-radius: 10px;
  box-shadow: 4px 4px 10px rgba(218, 218, 218, 0.534);
}

.el-form-item__label {
  color: #009688;
}

.el-button--primary {
  background-color: #009688;
  border-color: #009688;
}
</style>
