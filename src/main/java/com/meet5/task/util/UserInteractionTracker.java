package com.meet5.task.util;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class UserInteractionTracker {
    private final ConcurrentMap<LocalDateTime, Integer> interactionWindows
            = new ConcurrentHashMap<>();

    public boolean isFraudulent() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime windowStart = now.minusMinutes(10);

        interactionWindows.keySet().removeIf(key -> key.isBefore(windowStart));

        interactionWindows.merge(now, 1, Integer::sum);

        int totalInteractions = interactionWindows.values().stream()
                .mapToInt(Integer::intValue)
                .sum();

        return totalInteractions > 10;
    }
}
