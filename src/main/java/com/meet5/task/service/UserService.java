package com.meet5.task.service;

import com.meet5.task.domain.User;
import com.meet5.task.domain.projection.Visitor;
import com.meet5.task.dto.UserDto;
import com.meet5.task.exception.FraudulentActivityException;
import com.meet5.task.repository.InteractionsRepository;
import com.meet5.task.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.meet5.task.enums.InteractionType.LIKE;
import static com.meet5.task.enums.InteractionType.VISIT;
import static com.meet5.task.validator.UserValidator.validateUserDto;
import static com.meet5.task.validator.UserValidator.validateUserEntity;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final FraudDetectionService fraudDetectionService;
    private final UserRepository userRepository;
    private final InteractionsRepository interactionsRepository;

    /**
     * Gets user by userId.
     * @param userId id of user.
     * @return User
     */
    public UserDto getUser(Integer userId) {
        User user = userRepository.findUserById(userId);
        return UserDto.builder()
                .userId(userId)
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .age(user.getAge())
                .build();
    }

    /**
     * Saves user visit.
     * @param sourceUserId id of visiting user.
     * @param targetUserId id of visited user.
     * @return boolean
     */
    public boolean saveVisit(Integer sourceUserId, Integer targetUserId) {
        return saveInteraction(sourceUserId, targetUserId, VISIT.name());
    }

    /**
     * Saves user like.
     * @param sourceUserId id of liker user.
     * @param targetUserId id of liked user.
     * @return boolean
     */
    public boolean saveLike(Integer sourceUserId, Integer targetUserId) {
        return saveInteraction(sourceUserId, targetUserId, LIKE.name());
    }

    /**
     * Saves interaction to database.
     * @param sourceUserId id of source user.
     * @param targetUserId id of target user.
     * @param interactionType interaction type.
     * @return boolean
     */
    public boolean saveInteraction(Integer sourceUserId, Integer targetUserId, String interactionType) {
        if (fraudDetectionService.isLikelyFraudulent(sourceUserId)) {
            fraudDetectionService.logFraudAttempt(sourceUserId);
            throw new FraudulentActivityException("Fraudulent Activity detected.");
        }
        interactionsRepository.saveInteraction(sourceUserId, targetUserId, interactionType);
        return true;
    }

    /**
     * Gets the visitors of a user.
     * @param visitedUserId id of user.
     * @return List
     */
    public List<Visitor> getVisitors(Integer visitedUserId) {
        return userRepository.findVisitorsByUserId(visitedUserId);
    }

    /**
     * Saves user to database.
     * @param userDto user details.
     */
    public void saveUser(UserDto userDto) {
        User user = User.builder()
                .firstName(userDto.firstName())
                .lastName(userDto.lastName())
                .email(userDto.email())
                .username(userDto.username())
                .age(userDto.age())
                .build();

        validateUserEntity(user);
        userRepository.save(user);
    }


    /**
     * Bulk Save users.
     * @param userDtoList User list.
     */
    public void bulkSaveUsers(List<UserDto> userDtoList) {
        List<User> users = new ArrayList<>();
        for (UserDto userDto : userDtoList) {
            try {
                validateUserDto(userDto);
                User user = User.builder()
                        .firstName(userDto.firstName())
                        .lastName(userDto.lastName())
                        .email(userDto.email())
                        .username(userDto.username())
                        .age(userDto.age())
                        .build();
                users.add(user);

            } catch (IllegalArgumentException e) {
                log.error("Exception occurred {}, while processing {}", e.getMessage(), userDto.toString());
            }
        }

        userRepository.saveAll(users);
    }
}
