package com.cocoding.DecideWithContext.controller;

import com.cocoding.DecideWithContext.model.UserContext;
import com.cocoding.DecideWithContext.service.DecisionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class DecisionController {

    private final DecisionService decisionService;

    public DecisionController(DecisionService decisionService) {
        this.decisionService = decisionService;
    }

    @PostMapping("/decide")
    public Object decide(@RequestBody UserContext context) {
        return decisionService.getRecommendations(context);
    }
}