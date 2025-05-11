import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
    {path: '/home', name: 'home', component: () => import(/* webpackChunkName: "about" */ '../views/HomeLayout.vue'),
        children: [
            {path: 'homepage', component: () => import('../views/home/HomePage.vue'),},
            {path: 'chat', component: () => import('../views/home/Chat.vue'),},
            {path: 'test', component: () => import('../views/home/Test.vue'),},
            {path: 'information', component: () => import('../views/home/Information.vue'),},
            {path: 'admin', component: () => import(/* webpackChunkName: "about" */ '../views/home/Admin.vue'),},
            {path: 'user', component: () => import(/* webpackChunkName: "about" */ '../views/home/User.vue'),},
            {path: 'user-center', component: () => import('../views/home/UserCenter.vue'),},
        ]

    },
    {path: '/register', name: 'register', component: () => import(/* webpackChunkName: "about" */ '../views/Register.vue'),},
    {path: '/login', name: 'login', component: () => import(/* webpackChunkName: "about" */ '../views/Login.vue'),},

    // {
    //     path: '/knowledge',
    //     name: 'knowledge',
    //     component: () => import(/* webpackChunkName: "about" */ '../views/manager/AboutView.vue'),
    //     children: [
    //         {path: 'about', component: () => import('../views/manager/AboutView.vue')},
    //     ]
    // }
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

export default router
