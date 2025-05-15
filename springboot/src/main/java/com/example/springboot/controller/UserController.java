package com.example.springboot.controller;

import com.example.springboot.common.Result;
import com.example.springboot.common.ResultCode;
import com.example.springboot.entity.User;
import com.example.springboot.exception.CustomException;
import com.example.springboot.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    UserService userService;

    @GetMapping("/alldata")
    public Result getData(){
        List<User> all = userService.getAll();


        return Result.success(userService.getAll());

    }

//    //用户注册接口
//    @PostMapping("/register")
//    public Result userRegister(@RequestBody User user){
//        userService.userRegister(user);
//
//        return Result.success(user);
//    }

    //用户登录接口
//    @PostMapping("/login")
//    public Result userLogin(@RequestBody User user){
//
//        return Result.success(userService.userLogin(user));
//    }

    /**
     * 新增用户接口
     */
    @PostMapping
    public Result userRegister(@RequestBody User user){

        return Result.success(userService.userRegister(user));


    }


    /**
     * 描述：根据ID删除
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        userService.delete(id);
        return Result.success();
    }

    /**
     * 描述：分页查询
     */
    @PostMapping("/page")
    public Result page(@RequestBody User search,
                       @RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "10") Integer pageSize) {
        return Result.success(userService.findPage(search, pageNum, pageSize));
    }


    /**
     * 编辑用户信息接口
     */
    @PostMapping("/edit")
    public Result userEdit(@RequestBody User user){

        return Result.success(userService.userEdit(user));

    }



}


