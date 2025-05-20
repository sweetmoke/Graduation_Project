package com.example.springboot.service;

import com.example.springboot.dao.ScenarioDao;
import com.example.springboot.entity.Scenario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ScenarioService {
    List<Scenario> getAllScenarios();

    @Service
    class ScenarioServiceImpl implements ScenarioService {

        @Autowired
        private ScenarioDao scenarioDao;

        @Override
        public List<Scenario> getAllScenarios() {
            return scenarioDao.findAll();
        }
    }
}
