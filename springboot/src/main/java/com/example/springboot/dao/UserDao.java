package com.example.springboot.dao;

import com.example.springboot.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface UserDao extends Mapper<User> {

    //数据库中如果有与用户输入的名字重复的，返回名字
    @Select("select * from user where `userName` = #{userName}")
    User findByName(@Param("userName") String userName);

    @Select("select * from user where `id` = #{id}")
    User findById(@Param("id")Integer id);

    List<User> findBySearch(@Param("search") User search);
}
