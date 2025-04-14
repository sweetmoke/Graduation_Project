package com.example.springboot.controller;

import com.example.springboot.common.Result;
import com.example.springboot.common.ResultCode;
import com.example.springboot.entity.Admin;
import com.example.springboot.exception.CustomException;
import com.example.springboot.service.AdminService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Resource
    AdminService adminService;

    @GetMapping("/alldata")
    public Result getData(){
        List<Admin> all = adminService.getAll();
        if(all.size() < 1){
            throw new CustomException(ResultCode.DATA_LESS);
        }

        return Result.success(adminService.getAll());

    }
}
