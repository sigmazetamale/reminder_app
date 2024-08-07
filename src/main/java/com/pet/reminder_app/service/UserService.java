package com.pet.reminder_app.service;

import com.pet.reminder_app.database.model.User;
import com.pet.reminder_app.database.repository.UserRepository;
import com.pet.reminder_app.util.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void save(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (!userOptional.isPresent()) {
            User user = User.builder().email(email).build();
            userRepository.save(user);
        }
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
    }

    @Transactional
    public void findByEmailAndUpdate(String email, Long chatId) {
        User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        user.setChatId(chatId);
        userRepository.save(user);
    }
}
