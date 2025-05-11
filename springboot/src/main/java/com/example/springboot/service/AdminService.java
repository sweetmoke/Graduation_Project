package com.example.springboot.service;

import cn.hutool.core.util.ObjectUtil;
import com.example.springboot.common.ResultCode;
import com.example.springboot.dao.AdminDao;
import com.example.springboot.entity.Admin;
import com.example.springboot.entity.User;
import com.example.springboot.exception.CustomException;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    @Resource
    AdminDao adminDao;

    public List<Admin> getAll() {

        return adminDao.selectAll();
    }

    public Object adminLogin(Admin admin) {
        //1.拿到用户名到数据库根据用户名匹配
        Admin dbAdmin = adminDao.findByName(admin.getUserName());
        if (ObjectUtil.isEmpty(dbAdmin)) {
            throw new CustomException(ResultCode.USER_ACCOUNT_ERROR);
        }

        //2.如果用户存在，再对比用户输入的密码和数据库存的密码是否一致，如一致则允许登录
        String password = admin.getPassword();//用户输入的密码
        String dbPassword = dbAdmin.getPassword();//数据路找到用户的密码

        if (!password.equals(dbPassword)) {
            throw new CustomException(ResultCode.USER_ACCOUNT_ERROR);//报错账户或密码错误

        }

        return dbAdmin;

    }
}
