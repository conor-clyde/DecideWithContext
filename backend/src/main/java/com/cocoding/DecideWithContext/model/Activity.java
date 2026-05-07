package com.cocoding.DecideWithContext.model;

import jakarta.persistence.*;

@Entity
@Table(name = "activities")
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "interest_id", nullable = false)
    private Interest interest;

    @Column(nullable = false)
    private int suggestedDurationMinutes;

    @Column(length = 1000, nullable = false)
    private String reasonTemplate;

    private Integer energyMin;
    private Integer energyMax;
    private Integer minDurationMinutes;
    private Integer maxDurationMinutes;

    public Activity() {
    }

    public Activity(String name, Category category, Interest interest, int suggestedDurationMinutes,
                    String reasonTemplate, Integer energyMin, Integer energyMax,
                    Integer minDurationMinutes, Integer maxDurationMinutes) {
        this.name = name;
        this.category = category;
        this.interest = interest;
        this.suggestedDurationMinutes = suggestedDurationMinutes;
        this.reasonTemplate = reasonTemplate;
        this.energyMin = energyMin;
        this.energyMax = energyMax;
        this.minDurationMinutes = minDurationMinutes;
        this.maxDurationMinutes = maxDurationMinutes;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public Interest getInterest() {
        return interest;
    }

    public int getSuggestedDurationMinutes() {
        return suggestedDurationMinutes;
    }

    public String getReasonTemplate() {
        return reasonTemplate;
    }

    public Integer getEnergyMin() {
        return energyMin;
    }

    public Integer getEnergyMax() {
        return energyMax;
    }

    public Integer getMinDurationMinutes() {
        return minDurationMinutes;
    }

    public Integer getMaxDurationMinutes() {
        return maxDurationMinutes;
    }
}
