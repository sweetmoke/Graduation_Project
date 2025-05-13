package com.example.springboot.service;

import cn.hutool.core.util.ObjectUtil;
import com.example.springboot.common.ResultCode;
import com.example.springboot.dao.AdminDao;
import com.example.springboot.entity.Account;
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

    public Admin adminLogin(Admin admin) {
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

    public Admin findById(Integer id) {
        return adminDao.findById(id);
    }

    public Admin adminRegister(Admin admin) {

        String userName = admin.getUserName();
//     1.先校验用户名是不是为空： 如果用户名，为空，抛出异常
        if (ObjectUtil.isEmpty(userName)){
            throw new CustomException(ResultCode.USERNAME_ISNULL);
        }
//     2.校验用户名的唯一性：
        Admin dbAdmin = adminDao.findByName(userName);
        if (ObjectUtil.isNotEmpty(dbAdmin)) {
            throw new CustomException(ResultCode.USER_EXIST_ERROR);//用户名已存在
        }
        adminDao.insertSelective(admin);
        return admin;
    }
}
