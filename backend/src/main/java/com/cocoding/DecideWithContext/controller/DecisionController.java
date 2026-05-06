package com.cocoding.DecideWithContext.controller;

import com.cocoding.DecideWithContext.model.UserContext;
import com.cocoding.DecideWithContext.model.CandidateOption;
import com.cocoding.DecideWithContext.service.DecisionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DecisionController {

    private final DecisionService decisionService;

    public DecisionController(DecisionService decisionService) {
        this.decisionService = decisionService;
    }

    @PostMapping("/decide")
    public List<CandidateOption> decide(@RequestBody UserContext context) {
        return decisionService.getRecommendations(context);
    }
}