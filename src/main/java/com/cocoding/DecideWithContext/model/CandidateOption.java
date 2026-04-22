package com.cocoding.DecideWithContext.model;

public class CandidateOption {
    public String title;
    public String type;
    public int duration;
    public String reason;

    public CandidateOption(String title, String type, int duration, String reason) {
        this.title = title;
        this.type = type;
        this.duration = duration;
        this.reason = reason;
    }
}