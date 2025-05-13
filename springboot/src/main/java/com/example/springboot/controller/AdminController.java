package com.example.springboot.controller;

import com.example.springboot.common.Result;
import com.example.springboot.common.ResultCode;
import com.example.springboot.entity.Admin;
import com.example.springboot.exception.CustomException;
import com.example.springboot.service.AdminService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Resource
    AdminService adminService;

    @GetMapping("/alldata")
    public Result getData(){
        List<Admin> admins = adminService.getAll();
//        报错，当管理员数据小于1
//        if(all.size() < 1){
//            throw new CustomException(ResultCode.DATA_LESS);
//        }

        return Result.success(admins);

    }

    //管理员登录接口
//    @PostMapping("/login")
//    public Result adminLogin(@RequestBody Admin admin){
//
//        return Result.success(adminService.adminLogin(admin));
//    }

    /**
     * 新增管理员接口
     */
    @PostMapping
    public Result adminRegister(@RequestBody Admin admin){

        return Result.success(adminService.adminRegister(admin));


    }

}
