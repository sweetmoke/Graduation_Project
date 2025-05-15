package com.example.springboot.service;

import com.example.springboot.dao.InformationDao;
import com.example.springboot.entity.Information;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InformationService {

    @Autowired
    private InformationDao informationDao;

    public List<Information> getInformationList(Integer userId, String role, Integer page, Integer pageSize) {
        int offset = (page - 1) * pageSize;
        return informationDao.selectByUser(userId, role, offset, pageSize);
    }

    public int getTotal(Integer userId, String role) {
        return informationDao.countByUser(userId, role);
    }

    public void markAsRead(Integer informationId) {
        informationDao.updateIsRead(informationId);
    }
}
