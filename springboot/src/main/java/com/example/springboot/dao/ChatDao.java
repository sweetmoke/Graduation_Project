package com.example.springboot.dao;

import com.example.springboot.entity.Chat;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChatDao {
    List<Chat> getChatsByUserId(Integer userId);
    void insertChat(Chat chat);
}
