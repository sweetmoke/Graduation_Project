<template>
  <div>
    <div class="admin">
      <div style="margin-bottom:40px;font-size:18px;font-weight:530;">管理员管理</div>

      <div style="margin-bottom: 20px; display: flex">
        <div style="flex: 10;text-align: left">
          <el-input placeholder="请输入查询内容" size="small" v-model="input" style="width: 40%; margin-right: 20px">
            <template #suffix>
              <i class="el-input__icon el-icon-search"></i>
            </template>
          </el-input>
        </div>
        <div style="flex: 2; text-align: right">
          <el-button type="success" size="small" style="border-radius: 1px; width: 100px; text-align: center">
            新增
          </el-button>
        </div>
      </div>

      <!-- 管理员界面 -->
      <el-table :data="tableData" style="width: 100%" fit="true" stripe :cell-style="{ textAlign: 'center' }"
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
            <el-button @click="handleClick(scope.row)" type="text" size="small">查看</el-button>
            <el-button type="text" size="small">编辑</el-button>
          </template>
        </el-table-column>
      </el-table>

      <div class="block" style="text-align:center;margin-top:40px">
        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
          :current-page="currentPage" :page-sizes="[25, 50, 100, 200]" :page-size="1"
          layout="total, sizes, prev, pager, next, jumper" :total="400">
        </el-pagination>
      </div>


    </div>
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
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
    },
    load(){
      request.get("admin/alldata").then(res =>{
        if (res.code === '0'){
          //从后端调取管理员alldata
          this.tableData = res.data;
        }else{
          //不成功返回错误信息
          this.$notify.error(res.msg)
        }
      })
    }
  },
  data() {
    return {
      input: '',
      currentPage: 1,
      tableData: []
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
  height: 940px;
  padding: 40px;
  border: 1px solid #cdcdcd;
  /* background-color: white; 这里是内部容器，保持白色 */
  border-radius: 10px;
  box-shadow: 4px 4px 10px rgba(218, 218, 218, 0.534);
  /* 阴影效果 */
}
</style>