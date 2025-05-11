<!-- 放置不动的header -->
<template>
    <div id="app" style="margin: 0 auto">
        <el-container>
            <!-- 首页头部 -->
            <el-header style="display: flex; justify-content: space-between; align-items: center;">
                <div class="home_header" style="display: flex; align-items: center;">
                    <img src="@/assets/logo.png" style="height: 50px; margin-right: 15px;">
                    <strong style="font-size: 1.2em;">中小学生安全知识智能问答系统</strong>
                </div>
                <span style="font-size: 0.9em; font-style: italic;">
                    安全是生命的基石，守护是成长的护航。
                </span>
            </el-header>
            <!-- 导航栏 -->
            <el-menu 
                :default-active="$route.path" 
                router 
                class="el-menu-demo" 
                mode="horizontal"
                background-color="#daf5f3" 
                text-color="#333" 
                active-text-color="#009688"
                style="margin: 0px 0px 3px 0px">
                <!--              @select="handleSelect"-->

                <!-- 左侧菜单栏 -->
                <el-menu-item index="/home/homepage" style="transition: all 0.3s;" class="nav-item">首页</el-menu-item>
                <el-menu-item index="/home/chat" style="transition: all 0.3s;" class="nav-item">安全小导师</el-menu-item>
                <el-menu-item index="/home/test" style="transition: all 0.3s;" class="nav-item">
                    情境模拟
                </el-menu-item>
                <!-- <el-submenu index="/knowledge">
                    <template slot="title">知识学习</template>
<el-submenu index="/knowledge/life-safety">
    <template slot="title">生活安全</template>
    <el-menu-item index="/knowledge/life-safety/traffic">交通安全</el-menu-item>
    <el-menu-item index="/knowledge/life-safety/food">饮食安全</el-menu-item>
    <el-menu-item index="/knowledge/life-safety/drowning-prevention">防溺水</el-menu-item>
    <el-menu-item index="/knowledge/life-safety/fire-prevention">防火</el-menu-item>
    <el-menu-item index="/knowledge/life-safety/electrical-safety">防触电</el-menu-item>
    <el-menu-item index="/knowledge/life-safety/fall-prevention">防坠落</el-menu-item>
</el-submenu>
<el-submenu index="/knowledge/campus-safety">
    <template slot="title">校园与人际安全</template>
    <el-menu-item index="/knowledge/campus-safety/bullying-prevention">防欺凌</el-menu-item>
    <el-menu-item index="/knowledge/campus-safety/sexual-assault-prevention">防性侵</el-menu-item>
</el-submenu>
<el-submenu index="/knowledge/disaster-safety">
    <template slot="title">灾害与事故防护</template>
    <el-menu-item index="/knowledge/disaster-safety/firefighting-safety">消防安全</el-menu-item>
    <el-menu-item index="/knowledge/disaster-safety/fire-prevention">防火</el-menu-item>
    <el-menu-item index="/knowledge/disaster-safety/electrical-safety">防触电</el-menu-item>
</el-submenu>
<el-menu-item index="/knowledge/cyber-safety">网络安全</el-menu-item>
</el-submenu>
<el-menu-item index="/news">新闻</el-menu-item>
<el-menu-item index="/policy">政策法规</el-menu-item>
<el-menu-item index="/interaction">交流互动</el-menu-item> -->



                <!-- 右侧菜单栏 -->
                <div style="display: flex;float: right">
                    <el-submenu index="1">
                        <template slot="title">账户管理</template>
                        <el-menu-item index="/home/admin" style="transition: all 0.3s;" class="nav-item">管理员管理</el-menu-item>
                        <el-menu-item index="/home/user" style="transition: all 0.3s;" class="nav-item">用户管理</el-menu-item>
                    </el-submenu>


                    <!-- v-if="userRole === 'admin'" -->
                    <el-menu-item index="/home/information"
                        style="display: flex;align-items: center;justify-content: center;transition: all 0.3s;"
                        class="nav-item">
                        <i class="el-icon-bell"></i>通知
                    </el-menu-item>
                    <el-menu-item index="/home/user-center"
                        style="display: flex;align-items: center;justify-content: center;transition: all 0.3s;"
                        class="nav-item">
                        <i class="el-icon-user"></i>用户中心
                    </el-menu-item>
                </div>

            </el-menu>
            <el-main>
                <router-view />
            </el-main>
        </el-container>
    </div>
</template>

<style>
.el-header,
.el-footer {
    background-color: #009688;
    color: white;
    text-align: center;
    line-height: 60px;
}

.el-menu {
    background-color: #daf5f3;
}

.el-aside {
    background-color: #D3DCE6;
    color: #333;
    text-align: center;
    line-height: 200px;
}

/* .el-main {
    background-color: #f0f0f0;
    margin: 8px;
} */

body>.el-container {
    margin-bottom: 40px;
}

.el-container:nth-child(5) .el-aside,
.el-container:nth-child(6) .el-aside {
    line-height: 260px;
}

.el-container:nth-child(7) .el-aside {
    line-height: 320px;
}

/* 新增悬停动画 */
.nav-item:hover {
    background-color: rgba(0, 150, 136, 0.1) !important;
    transform: translateY(-2px);
}

/* 平滑过渡效果 */
.el-menu-item,
.home_header {
    transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
}

/* 点击反馈 */
.el-menu-item:active {
    transform: scale(0.98);
}
</style>
<script lang="ts">
</script>