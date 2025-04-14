package com.example.springboot.service;

import com.example.springboot.dao.AdminDao;
import com.example.springboot.entity.Admin;
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
}
