package com.cocoding.DecideWithContext.service.ai;

import com.cocoding.DecideWithContext.model.CandidateOption;
import com.cocoding.DecideWithContext.model.UserContext;
import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AIService {

    private final Optional<Client> gemini;

    public AIService(Optional<Client> gemini) {
        this.gemini = gemini;
    }

    public List<CandidateOption> rankCandidates(List<CandidateOption> candidates, UserContext context) {
        if (gemini.isEmpty()) {
            return candidates;
        }

        String prompt = """
                You are a ranking engine.
                Rank these options from best to worst: A, B, C.
                Return only the ranked list.
                """;
        try {
            GenerateContentResponse response = gemini.get().models.generateContent("gemini-2.5-flash", prompt, null);
            System.out.println("Gemini connectivity check response: " + response.text());
        } catch (Exception ignored) {
            // For this stage we do not fail decision flow if AI is unavailable.
        }
        return candidates;
    }
}