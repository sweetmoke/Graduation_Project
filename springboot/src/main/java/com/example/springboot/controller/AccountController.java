package com.example.springboot.controller;

import com.example.springboot.common.Result;
import com.example.springboot.common.config.JwtTokenUtils;
import com.example.springboot.entity.Account;
import com.example.springboot.entity.Admin;
import com.example.springboot.entity.User;
import com.example.springboot.service.AdminService;
import com.example.springboot.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/account")
public class AccountController {
    @Resource
    AdminService adminService;

    @Resource
    UserService userService;

    //用户注册接口
    @PostMapping("/register")
    public Result accountRegister(@RequestBody Account account){
        Integer role = account.getRole();
        Account newAccount = new Account();//定义一个Account类的login，用于返回给前端
        if(role == 2){
            User user = new User();
            BeanUtils.copyProperties(account,user);
            newAccount = userService.userRegister(user);
        }

        return Result.success(newAccount);
    }


    //角色登录接口
    @PostMapping("/login")
    public Result accountLogin(@RequestBody Account account){
        Integer role = account.getRole();
        Account login = new Account();//定义一个Account类的login，用于返回给前端
        //如果登录角色为1，为管理员登录，到管理员表去检验账号名称和密码
        if(role == 1){
            //1.创建一个Admin类的对象
            Admin admin = new Admin();
            //2.转换javabean类：把account父类的属性拷贝给子类admin
            BeanUtils.copyProperties(account,admin);//将 account 对象中与 admin 对象中字段名和类型相同的属性值复制到 admin 对象中
            login = adminService.adminLogin(admin);//调用服务层的登录逻辑
        }
        //如果登录角色为2，为用户登录，到用户表去检验账号名称和密码
        if(role == 2){
            User user = new User();
            BeanUtils.copyProperties(account,user);
            login = userService.userLogin(user);
        }
        if (login == null) {
            return Result.error("用户名或密码错误");
        }

        // 1. 生成 token，传入 "id-role" 和 password
        String token = JwtTokenUtils.genToken(login.getId() + "-" + login.getRole(), login.getPassword());

        // 2. 创建一个键值对 map 集合，把 token 和 user 信息封装进去返回前端
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("user", login);

        // 3. 把装好的数据map，返回结果给前端
        return Result.success(map);

    }

}
