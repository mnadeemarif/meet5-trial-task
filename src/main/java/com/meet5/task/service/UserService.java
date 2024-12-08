package com.meet5.task.service;

import com.meet5.task.domain.User;
import com.meet5.task.domain.projection.Visitor;
import com.meet5.task.exception.FraudulentActivityException;
import com.meet5.task.repository.InteractionsRepository;
import com.meet5.task.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.meet5.task.enums.InteractionType.LIKE;
import static com.meet5.task.enums.InteractionType.VISIT;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final FraudDetectionService fraudDetectionService;
    private final UserRepository userRepository;
    private final InteractionsRepository interactionsRepository;


    public User getUser(Integer userId) {
        return userRepository.findUserById(userId);
    }

    public boolean saveVisit(Integer sourceUserId, Integer targetUserId) {
        return saveInteraction(sourceUserId, targetUserId, VISIT.name());
    }

    public boolean saveLike(Integer sourceUserId, Integer targetUserId) {
        return saveInteraction(sourceUserId, targetUserId, LIKE.name());
    }

    public boolean saveInteraction(Integer sourceUserId, Integer targetUserId, String interactionType) {
        if (fraudDetectionService.isFraudulent(sourceUserId)) {
            fraudDetectionService.logFraudAttempt(sourceUserId);
            throw new FraudulentActivityException("Fraudulent Activity detected.");
        }
        interactionsRepository.saveInteraction(sourceUserId, targetUserId, interactionType);
        return true;
    }

    public List<Visitor> getVisitors(Integer visitedUserId) {
        return userRepository.findVisitorsByUserId(visitedUserId);
    }
}
