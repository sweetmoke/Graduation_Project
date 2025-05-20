package com.example.springboot.controller;

import com.example.springboot.entity.Scenario;
import com.example.springboot.service.ScenarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/scenario")
public class ScenarioController {

    @Autowired
    private ScenarioService scenarioService;

    @GetMapping("/all")
    public List<Scenario> getAllScenarios() {
        return scenarioService.getAllScenarios();
    }
}
