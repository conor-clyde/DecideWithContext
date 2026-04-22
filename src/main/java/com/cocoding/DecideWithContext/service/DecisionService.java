package com.cocoding.DecideWithContext.service;

import com.cocoding.DecideWithContext.model.CandidateOption;
import com.cocoding.DecideWithContext.model.UserContext;
import com.cocoding.DecideWithContext.service.ai.AIService;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DecisionService {
    private final AIService aiService;

    public DecisionService(AIService aiService) {
        this.aiService = aiService;
    }

    public List<CandidateOption> getRecommendations(UserContext context) {
        List<CandidateOption> candidates = generateCandidates(context);
        return aiService.rankCandidates(candidates, context);
    }

    private List<CandidateOption> generateCandidates(UserContext context) {
        List<CandidateOption> candidates = new ArrayList<>();

        if (context.energyLevel <= 3) {
            candidates.add(new CandidateOption(
                    "Music playlist",
                    "music",
                    40,
                    "Low energy detected, suitable for passive listening"));

            candidates.add(new CandidateOption(
                    "Comedy episode",
                    "movie",
                    25,
                    "Light entertainment matches low energy state"));
        }

        if (context.freeTimeMinutes >= 60) {
            candidates.add(new CandidateOption(
                    "Full movie",
                    "movie",
                    120,
                    "Long free time available, full movie is a good option"));
        }

        if (context.energyLevel > 5) {
            candidates.add(new CandidateOption(
                    "Short walk",
                    "activity",
                    30,
                    "High energy detected, suitable for physical activity"));
        }

        return candidates;
    }
}