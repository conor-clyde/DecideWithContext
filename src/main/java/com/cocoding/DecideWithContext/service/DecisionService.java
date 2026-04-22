package com.cocoding.DecideWithContext.service;

import com.cocoding.DecideWithContext.model.CandidateOption;
import com.cocoding.DecideWithContext.model.UserContext;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DecisionService {

    public List<CandidateOption> getRecommendations(UserContext context) {
        List<CandidateOption> options = new ArrayList<>();

        if (context.energyLevel <= 3) {
            options.add(new CandidateOption("Music playlist", "music", 40));
            options.add(new CandidateOption("Comedy episode", "movie", 25));
        }

        if (context.freeTimeMinutes >= 60) {
            options.add(new CandidateOption("Full movie", "movie", 120));
        }

        if (context.energyLevel > 5) {
            options.add(new CandidateOption("Short walk", "activity", 30));
        }

        return options;
    }
}