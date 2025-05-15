package com.example.springboot.controller;

import com.example.springboot.entity.Information;
import com.example.springboot.service.InformationService;
import com.example.springboot.common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/information")
public class InformationController {

    @Autowired
    private InformationService informationService;

    @GetMapping
    public Result getInformationList(@RequestParam Integer userId,
                                     @RequestParam String role,
                                     @RequestParam Integer page,
                                     @RequestParam Integer pageSize) {
        List<Information> list = informationService.getInformationList(userId, role, page, pageSize);
        int total = informationService.getTotal(userId, role);

        Map<String, Object> res = new HashMap<>();
        res.put("data", list);
        res.put("total", total);

        return Result.success(res);
    }

    @PostMapping("/read")
    public Result markAsRead(@RequestBody Map<String, Integer> params) {
        Integer informationId = params.get("informationId");
        informationService.markAsRead(informationId);
        return Result.success();
    }
}
