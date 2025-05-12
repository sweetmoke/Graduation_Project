package com.example.springboot.dao;

import com.example.springboot.entity.Account;
import com.example.springboot.entity.Admin;
import com.example.springboot.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface AdminDao extends Mapper<Admin> {

    //数据库中如果有与用户输入的名字重复的，返回名字
    @Select("select * from admin where `userName` = #{userName}")
    Admin findByName(@Param("userName") String userName);

    @Select("select * from admin where `id` = #{id}")
    Admin findById(@Param("id") Integer id);
}
