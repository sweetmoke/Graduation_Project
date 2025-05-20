package com.example.springboot.dao;

import com.example.springboot.entity.Scenario;
import java.util.List;

public interface ScenarioDao {
    List<Scenario> findAll();
}
