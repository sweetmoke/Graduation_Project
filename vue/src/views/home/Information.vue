<template>
  <div class="information-page">
    <h2>安全中心</h2>

    <el-table
        :data="informationList"
        border
        style="width: 100%"
        @row-click="handleRowClick"
    >
      <el-table-column
          prop="title"
          label="点击下面标题查看详情"
      >
        <template slot-scope="scope">
          <span :style="{ color: scope.row.isRead ? '#606266' : '#F56C6C', fontWeight: scope.row.isRead ? 'normal' : 'bold' }">
            {{ scope.row.title }}
          </span>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
        v-if="total > pageSize"
        :current-page.sync="page"
        :page-size="pageSize"
        :total="total"
        layout="prev, pager, next"
        @current-change="fetchInformation"
        style="margin-top: 20px; text-align: center;"
    />

    <el-dialog
        :visible.sync="dialogVisible"
        title="正文"
        width="80%"
    >
      <h3 style="margin-bottom: 10px;">{{ selectedItem.title }}</h3>
      <div v-html="selectedItem.content" class="rich-text-content"></div>

      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="dialogVisible = false">关闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import request from '@/utils/request';

export default {
  data() {
    return {
      informationList: [],
      page: 1,
      pageSize: 10,
      total: 0,
      user: JSON.parse(localStorage.getItem('user') || '{}'),
      loading: false,
      dialogVisible: false,
      selectedItem: {}
    };
  },
  mounted() {
    this.fetchInformation();
  },
  methods: {
    async fetchInformation() {
      this.loading = true;
      try {
        const response = await request.get('/api/information', {
          params: {
            userId: this.user.id,
            role: this.user.role,
            page: this.page,
            pageSize: this.pageSize
          }
        });

        // 为每个项添加前端的 isRead 属性（默认 false）
        this.informationList = response.data.data.map(item => ({
          ...item,
          isRead: false
        }));

        this.total = response.data.total;
      } catch (error) {
        this.$message.error('获取通知失败');
      } finally {
        this.loading = false;
      }
    },
    handleRowClick(row) {
      this.selectedItem = row;
      this.dialogVisible = true;

      // 前端设置为已读
      if (!row.isRead) {
        row.isRead = true;
      }
    }
  }
};
</script>

<style scoped>
.information-page {
  width: 100%;
  padding: 40px;
  border: 1px solid #cdcdcd;
  border-radius: 10px;
  box-shadow: 4px 4px 10px rgba(218, 218, 218, 0.534);
}

.rich-text-content {
  line-height: 1.6;
  font-size: 14px;
}

.rich-text-content img {
  max-width: 100%;
}

.rich-text-content a {
  color: #009688;
  text-decoration: none;
}

.rich-text-content a:hover {
  text-decoration: underline;
}
</style>
