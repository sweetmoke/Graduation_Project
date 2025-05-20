package com.example.springboot.service;

import com.example.springboot.entity.Chat;

import java.util.List;

public interface ChatService {
    List<Chat> getChatsByUserId(Integer userId);
    void saveChat(Chat chat);


}
