package com.cocoding.DecideWithContext.service.ai;

import com.cocoding.DecideWithContext.model.CandidateOption;
import com.google.genai.Client;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AIService {

    /** Gemini 3.1 Flash-Lite (preview) — see https://ai.google.dev/gemini-api/docs/models/gemini-3.1-flash-lite-preview */
    private static final String GEMINI_MODEL = "gemini-3.1-flash-lite-preview";

    private final Optional<Client> gemini;

    public AIService(Optional<Client> gemini) {
        this.gemini = gemini;
    }

    public List<CandidateOption> rankCandidates(List<CandidateOption> candidates) {
        if (gemini.isEmpty()) {
            return candidates;
        }

        String prompt = """
                You are a ranking engine.
                Rank these options from best to worst: B, E, A, C
                Return only the ranked list.
                """;
        try {
           // gemini.get().models.generateContent(GEMINI_MODEL, prompt, null);
        } catch (Exception ignored) {
            // For this stage we do not fail decision flow if AI is unavailable.
        }
        return candidates;
    }
}