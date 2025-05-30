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

    /**
     * 描述：根据ID删除
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        adminService.delete(id);
        return Result.success();
    }


    /**
     * 编辑管理员信息接口
     */
    @PostMapping("/edit")
    public Result adminEdit(@RequestBody Admin admin){

        return Result.success(adminService.adminEdit(admin));

    }

    /**
     * 描述：分页查询
     */
    @PostMapping("/page")
    public Result page(@RequestBody Admin search,
                       @RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "10") Integer pageSize) {
        return Result.success(adminService.findPage(search, pageNum, pageSize));
    }

}
