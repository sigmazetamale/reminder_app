package com.pet.reminder_app.database.repository;

import com.pet.reminder_app.database.model.Reminder;
import com.pet.reminder_app.database.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);

}
