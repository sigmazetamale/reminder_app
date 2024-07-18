package com.pet.reminder_app.service;

import com.pet.reminder_app.database.model.User;
import com.pet.reminder_app.database.repository.UserRepository;
import com.pet.reminder_app.util.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

//    @Transactional
//    public void save(User user) {
//        String email = user.getEmail();
//        Optional<User> userOptional = userRepository.findByEmail(email);
//
//        if (!userOptional.isPresent()) {
//            userRepository.save(user);
//        }
//    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }
}
