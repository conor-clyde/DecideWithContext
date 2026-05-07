package com.cocoding.DecideWithContext.config;

import com.cocoding.DecideWithContext.model.Activity;
import com.cocoding.DecideWithContext.model.Category;
import com.cocoding.DecideWithContext.model.Interest;
import com.cocoding.DecideWithContext.repository.ActivityRepository;
import com.cocoding.DecideWithContext.repository.CategoryRepository;
import com.cocoding.DecideWithContext.repository.InterestRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
public class ActivityDataLoader {

    @Bean
    CommandLineRunner loadActivities(ActivityRepository activityRepository,
                                     InterestRepository interestRepository,
                                     CategoryRepository categoryRepository) {
        return args -> {
            if (activityRepository.count() > 0) {
                return;
            }

            Category entertainment = categoryRepository.findByName("ENTERTAINMENT")
                    .orElseGet(() -> categoryRepository.save(new Category("ENTERTAINMENT")));

            List<Interest> savedInterests = interestRepository.findAll();
            if (savedInterests.isEmpty()) {
                List<Interest> starterInterests = List.of(
                        new Interest("Movies + TV", entertainment),
                        new Interest("Anime", entertainment),
                        new Interest("Reading", entertainment),
                        new Interest("Podcasts", entertainment),
                        new Interest("Music", entertainment),
                        new Interest("Board Games", entertainment)
                );
                savedInterests = interestRepository.saveAll(starterInterests);
            }
            Map<String, Interest> interestsByName = savedInterests.stream()
                    .collect(Collectors.toMap(Interest::getName, Function.identity()));

            List<Activity> starterActivities = List.of(
                    new Activity("Watch a movie", entertainment, interestsByName.get("Movies + TV"), 120,
                            "Great for low-energy downtime and easy enjoyment", 1, 5, 60, null),
                    new Activity("Watch TV episode", entertainment, interestsByName.get("Movies + TV"), 30,
                            "Short and easy entertainment when focus is limited", 1, 6, 20, null),
                    new Activity("Watch YouTube", entertainment, interestsByName.get("Movies + TV"), 25,
                            "Flexible entertainment that can fit short breaks", 1, 7, 10, null),
                    new Activity("Casual gaming session", entertainment, interestsByName.get("Board Games"), 45,
                            "Interactive fun without heavy pressure", 3, 8, 20, null),
                    new Activity("Competitive gaming", entertainment, interestsByName.get("Board Games"), 60,
                            "Higher-energy challenge when you want stimulation", 6, 10, 30, null),
                    new Activity("Watch anime", entertainment, interestsByName.get("Anime"), 35,
                            "Light story-based entertainment for relaxing", 1, 6, 20, null),
                    new Activity("Listen to podcast", entertainment, interestsByName.get("Podcasts"), 40,
                            "Good passive entertainment while resting or multitasking", 1, 8, 15, null),
                    new Activity("Listen to music album", entertainment, interestsByName.get("Music"), 40,
                            "Low-friction entertainment that matches most moods", 1, 10, 15, null),
                    new Activity("Read a chapter", entertainment, interestsByName.get("Reading"), 30,
                            "Quiet, low-stimulation entertainment for calmer moments", 1, 6, 15, null)
            );

            activityRepository.saveAll(starterActivities);
        };
    }
}
