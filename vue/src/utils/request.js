//引入
import axios from "axios";
import router from "@/router";

//创建一个axios对象
const request = axios.create({
    baseURL:"http://localhost:9090",
    timeout:5000
})

//request拦截器
//可以自请求发送前对请求做一些处理
//如统一加token，对请求参数统一加密
request.interceptors.request.use(config => {
  config.headers["Content-Type"] = "application/json;charset=UTF-8"

    //从缓存里获取token
    const token = localStorage.getItem("token")
    //如果有token就把token作为请求头
    if(token){
        config.headers['token'] = token
    }
    return config
},error => {
    return Promise.reject(error)
})


//response拦截器
//可以对响应数据做一些处理
request.interceptors.response.use(
  response =>{
    //response.data为后端返回的Result
    let res = response.data
    //兼容服务端返回的字符串数据，如果是字符串则转为对象
    if(typeof res === 'string'){
      res = res ? JSON.parse(res) : res
    }
    //权限验证不通过的时候跳转到登录页面
    if (res.code === '401'){
        router.push("/login").catch(() =>{} )
    }
    return res
  },
  error => {
    console.log('err' + error) // for debug
    return Promise.reject(error) // 返回接口返回的错误信息
    
  }
)

export default request//出request在其他页面可以引用