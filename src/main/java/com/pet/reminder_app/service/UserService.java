package com.pet.reminder_app.service;

import com.pet.reminder_app.database.model.User;
import com.pet.reminder_app.database.repository.UserRepository;
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
    public void save(User user) {
        String email = user.getEmail();
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (!userOptional.isPresent()) {
            userRepository.save(user);
        }
    }
}
