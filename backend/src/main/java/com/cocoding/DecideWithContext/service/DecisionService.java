package com.cocoding.DecideWithContext.service;

import com.cocoding.DecideWithContext.model.CandidateOption;
import com.cocoding.DecideWithContext.model.Activity;
import com.cocoding.DecideWithContext.model.UserContext;
import com.cocoding.DecideWithContext.repository.ActivityRepository;
import com.cocoding.DecideWithContext.service.ai.AIService;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DecisionService {
    private final AIService aiService;
    private final ActivityRepository activityRepository;

    public DecisionService(AIService aiService, ActivityRepository activityRepository) {
        this.aiService = aiService;
        this.activityRepository = activityRepository;
    }

    public List<CandidateOption> getRecommendations(UserContext context) {
        List<CandidateOption> candidates = generateCandidates(context);
        return aiService.rankCandidates(candidates);
    }

    private List<CandidateOption> generateCandidates(UserContext context) {
        List<CandidateOption> candidates = new ArrayList<>();
        List<Activity> activities = activityRepository.findAll();

        for (Activity activity : activities) {
            if (activity.getEnergyMin() != null && context.energyLevel < activity.getEnergyMin()) {
                continue;
            }
            if (activity.getEnergyMax() != null && context.energyLevel > activity.getEnergyMax()) {
                continue;
            }
            if (activity.getMinDurationMinutes() != null && context.freeTimeMinutes < activity.getMinDurationMinutes()) {
                continue;
            }
            if (activity.getMaxDurationMinutes() != null && context.freeTimeMinutes > activity.getMaxDurationMinutes()) {
                continue;
            }

            candidates.add(new CandidateOption(
                    activity.getName(),
                    activity.getCategory().getName().toLowerCase(),
                    activity.getSuggestedDurationMinutes(),
                    activity.getReasonTemplate()));
        }

        if (candidates.isEmpty()) {
            for (Activity activity : activities) {
                candidates.add(new CandidateOption(
                        activity.getName(),
                        activity.getCategory().getName().toLowerCase(),
                        activity.getSuggestedDurationMinutes(),
                        "Fallback option from activity catalog"));
                if (candidates.size() >= 5) {
                    break;
                }
            }
        }

        return candidates;
    }
}