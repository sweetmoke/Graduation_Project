<template>

  <div class="admin">
    <div style="margin-bottom:40px;font-size:18px;font-weight:530;">管理员管理</div>

      <div style="margin-bottom: 20px; display: flex">
        <div style="flex: 10;text-align: left">
          <el-input placeholder="请输入用户名查询" size="small" v-model="search.userName" style="width: 40%; margin-right: 20px">
            <template #suffix>
              <i class="el-input__icon el-icon-search"></i>
            </template>
          </el-input>
          <el-button type="success" size="small" style="border-radius: 1px;width: 100px;text-align: center" @click="load">查询</el-button>
        </div>

        <div style="flex: 2; text-align: right">
          <el-button type="success" size="small" style="border-radius: 1px; width: 100px; text-align: center" @click="add">
            新增
          </el-button>
        </div>
      </div>

      <!-- 管理员界面 -->
      <el-table :data="tableData" style="width: 100%" :fit="true" stripe :cell-style="{ textAlign: 'center' }"
        :header-cell-style="{ textAlign: 'center' }">
        <el-table-column fixed prop="id" label="ID">
        </el-table-column>
        <el-table-column prop="userName" label="姓名">
        </el-table-column>
        <el-table-column prop="password" label="密码">
        </el-table-column>
        <el-table-column prop="age" label="年龄">
        </el-table-column>
        <el-table-column prop="gender" label="性别">
        </el-table-column>
        <el-table-column prop="phone" label="电话">
        </el-table-column>
        <el-table-column fixed="right" label="操作">
          <template slot-scope="scope">
<!--            <el-button @click="handleClick(scope.row)" type="text" size="small">查看</el-button>-->
            <el-button type="text" size="small" @click="edit(scope.row)">编辑</el-button>
            <el-popconfirm title="确定删除吗？" @confirm="del(scope.row.id)" style="margin-left: 10px">
              <el-button size="small" type="text" slot="reference">删除</el-button>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>

      <!--  分页样式  -->
      <div class="block" style="text-align:center;margin-top:40px">
<!--        <el-pagination-->
<!--            @size-change="handleSizeChange"-->
<!--            @current-change="handleCurrentChange"-->
<!--            :current-page="currentPage"-->
<!--            :page-sizes="[25, 50, 100, 200]"-->
<!--            :page-size="1"-->
<!--            layout="total, sizes, prev, pager, next, jumper"-->
<!--            :total="400">-->
<!--        </el-pagination>-->
        <el-pagination
            background
            @current-change="handleCurrentChange"
            :current-page="pageNum"
            :page-size="10"
            layout="total, prev, pager, next, jumper"
            :total="total">
        </el-pagination>

      </div>

    <!--  模态框  -->
    <el-dialog title="请填写信息" :visible.sync="dialogVisible" width="40%">
      <el-form :model="form" label-position="right" label-width="100px" style="padding-right: 40px">
        <el-form-item label="用户名">
          <el-input size="small" v-model="form.userName" disabled></el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input size="small" type="password" show-password v-model="form.password" placeholder="请输入密码"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-radio-group size="small" v-model="form.gender">
            <el-radio label="男"></el-radio>
            <el-radio label="女"></el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="年龄">
          <el-input size="small" v-model="form.age" placeholder="请输入手机号"></el-input>
        </el-form-item>
        <el-form-item label="电话">
          <el-input size="small" v-model="form.phone" placeholder="请输入手机号"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button size="small" class="cancel-btn" @click="dialogVisible = false">
          取 消
        </el-button>
        <el-button size="small" class="confirm-btn" @click="save">
          保 存
        </el-button>
      </div>

    </el-dialog>

  </div>

</template>

<script>
import request from "@/utils/request";

export default {
  created() {
    this.load()
  },
  methods: {
    handleClick(row) {
      console.log(row);
    },
    //  当点击页码时，当前页码数赋值给data变量pageNum
    handleCurrentChange(pageNum) {
      this.pageNum = pageNum;
      this.load();
    },
    load(){
      request.post("/admin/page?pageNum=" + this.pageNum, this.search).then(res => {
        if (res.code === '0') {
          this.tableData = res.data.list;
          this.total = res.data.total;
        }else{
          //不成功返回错误信息
          this.$message.error(res.msg)
        }
      })
    },
    // 点击新增按钮点击事件调用
    add(){
      this.form = {};//清空老数据
      this.dialogVisible = true;
    },
    save(){
      if (!this.form.id){ //如果没有id 走新增接口。有是编辑
        this.form.role = 1;
        request.post("/admin",this.form).then(res =>{ //post请求把form对象传到后端，后端写逻辑把form存到数据库里
          if (res.code === '0'){ //如果接口调用成功，则把模态框关闭，重新加载一下数据库的数据
            this.$message.success("新增成功");
            this.dialogVisible = false;
            this.load();
          } else {
            this.$message.error(res.msg); // 如果不成功，返回报错信息
          }
        })
      }else {
        request.post("/admin/edit",this.form).then(res =>{
          if (res.code === '0'){
            this.$message.success("修改成功");
            this.dialogVisible = false;
            this.load();
          } else {
            this.$message.error(res.msg); // 如果不成功，返回报错信息
          }
        })
      }

    },
    // 点击编辑，打开模态框，回显当前对象的信息
    // edit(row){
    //   this.dialogVisible = true;
    //   this.form = row;
    // },
    edit(row) {
      this.form = JSON.parse(JSON.stringify(row));
      this.$nextTick(() => {
        this.$refs.tx.clearFiles()
      });
      this.dialogVisible = true;
    },
    // 根据ID删除某条数据
    del(id) {
      request.delete("/admin/" + id).then(res => {
        if (res.code === '0') {
          this.$message.success('删除成功');
          this.load();
        } else {
          this.$message.error(res.msg);
        }
      });
    },


  },
  data() {
    return {
      form:{},
      dialogVisible:false,
      // input: '',
      pageNum: 1,
      total:0,
      search:{},
      tableData: [],
    }
  }
}
</script>

<style>
/* body, html {
  background-color: #f0f0f0;
  margin: 0;
  padding: 0;
} */
.admin {
  width: 100%;
  padding: 40px;
  border: 1px solid #cdcdcd;
  /* background-color: white; 这里是内部容器，保持白色 */
  border-radius: 10px;
  box-shadow: 4px 4px 10px rgba(218, 218, 218, 0.534);
  /* 阴影效果 */
}
.cancel-btn {
  background-color: #f2f2f2;
  color: #333;
  border: 1px solid #ccc;
  border-radius: 4px;
  transition: all 0.3s;
}

.cancel-btn:hover {
  background-color: #e0e0e0;
  color: #000;
}

.confirm-btn {
  background-color: #009688;
  color: white !important; /* 强制白色字体 */
  border: 1px solid #009688;
  border-radius: 4px;
  transition: all 0.3s;
}

.confirm-btn:hover {
  background-color: #00796b;
  border-color: #00796b;
  color: white !important; /* hover 时也强制白色 */
}

</style>