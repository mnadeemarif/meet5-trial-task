package com.meet5.task.service;

import com.meet5.task.repository.FraudLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

@Service
@RequiredArgsConstructor
@Slf4j
public class FraudDetectionService {

    private final FraudLogRepository fraudLogRepository;

    @Value("${user.max-interactions:100}")
    private Integer MAX_INTERACTIONS;
    @Value("${user.interaction-window-minutes:10}")
    private Integer INTERACTION_WINDOW_MINUTES;

    private static final String INTERACTIONS_KEY_PREFIX = "user:interactions:";

    private final RedisTemplate<String, String> redisTemplate;

    /**
     * Checks if user is performing fraudulent activity.
     * @param userId id of source user.
     * @return boolean
     */
    public boolean isLikelyFraudulent(Integer userId) {
        String key = INTERACTIONS_KEY_PREFIX + userId;
        ZSetOperations<String, String> zSetOps = redisTemplate.opsForZSet();

        double now = Instant.now().toEpochMilli();
        double windowStart = now - (INTERACTION_WINDOW_MINUTES * 60 * 1000);
        zSetOps.removeRangeByScore(key, 0, windowStart);
        zSetOps.add(key, String.valueOf(now), now);

        Long interactionCount = zSetOps.count(key, windowStart, now);

        boolean isFraudulent = interactionCount != null && interactionCount > MAX_INTERACTIONS;

        if (interactionCount != null && interactionCount > 0) {
            redisTemplate.expire(key, Duration.ofMinutes(INTERACTION_WINDOW_MINUTES + 1));
        }

        return isFraudulent;
    }

    /**
     * Logs the fraud attempt to database.
     * @param userId id of source user.
     */
    public void logFraudAttempt(Integer userId) {
        fraudLogRepository.saveFraudLog(userId);
    }
}
