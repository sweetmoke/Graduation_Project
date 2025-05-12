package com.example.springboot.service;

import cn.hutool.core.util.ObjectUtil;
import com.example.springboot.common.ResultCode;
import com.example.springboot.dao.UserDao;
import com.example.springboot.entity.Account;
import com.example.springboot.entity.User;
import com.example.springboot.exception.CustomException;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Resource
    UserDao userDao;

    public List<User> getAll() {

        return userDao.selectAll();
    }

    public User userRegister(User user) {
        //1.拿到用户输入的用户名，去数据库查询是否存在，如果存在。抛出异常
        String userName = user.getUserName();//用户输入的用户名

        User dbUser = userDao.findByName(userName);//根据用户输入的用户名到数据查询的结果，也就是说跟用户输入的用户名一致的对象

        if (ObjectUtil.isNotEmpty(dbUser)) {//如果找到这个用户，抛出异常提示用户已存在
            throw new CustomException(ResultCode.USER_EXIST_ERROR);//用户名已存在
        }

        //2.用insertSelective 方法，把用户注册信息写到数据表
        userDao.insertSelective(user);

        return user;
    }

    public User userLogin(User user) {
        //1.拿到用户名到数据库根据用户名匹配
        User dbUser = userDao.findByName(user.getUserName());
        if (ObjectUtil.isEmpty(dbUser)) {
            throw new CustomException(ResultCode.USER_NOT_EXIST_ERROR);
        }

        //2.如果用户存在，再对比用户输入的密码和数据库存的密码是否一致，如一致则允许登录
        String password = user.getPassword();//用户输入的密码
        String dbPassword = dbUser.getPassword();//数据路找到用户的密码

        if (!password.equals(dbPassword)) {
            throw new CustomException(ResultCode.USER_ACCOUNT_ERROR);//报错账户或密码错误

        }

        return dbUser;

    }

    public User findById(Integer id) {
        return userDao.findById(id);
    }
}
