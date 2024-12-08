package com.meet5.task.service;

import com.meet5.task.repository.FraudLogRepository;
import com.meet5.task.repository.InteractionsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class FraudDetectionService {

    private final FraudLogRepository fraudLogRepository;
    private final InteractionsRepository interactionsRepository;

    private static final int MAX_INTERACTIONS = 100;
    private static final int INTERACTION_WINDOW_MINUTES = 10;

    /*
    private final ConcurrentMap<Integer, UserInteractionTracker> interactionTrackers
            = new ConcurrentHashMap<>();

    public boolean isLikelyFraudulent(Integer userId) {
        UserInteractionTracker tracker = interactionTrackers.computeIfAbsent(
                userId,
                k -> new UserInteractionTracker()
        );

        return tracker.isFraudulent();
    }
    */

    private Integer getInteractionCount(Integer userId) {
        LocalDateTime windowStart = LocalDateTime.now().minusMinutes(INTERACTION_WINDOW_MINUTES);
        return interactionsRepository.userInteractionByUserIdAndTimeWindow(userId, windowStart);
    }

    public boolean isFraudulent(Integer userId) {
        Integer totalInteractions = getInteractionCount(userId);
        log.info("Total interactions: {}", totalInteractions);
        return totalInteractions > MAX_INTERACTIONS;
    }

    public void logFraudAttempt(Integer userId) {
        fraudLogRepository.saveFraudLog(userId);
    }
}
