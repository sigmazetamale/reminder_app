package com.pet.reminder_app.database.repository;


import com.pet.reminder_app.database.model.Reminder;
import com.pet.reminder_app.database.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReminderRepository extends JpaRepository<Reminder,Long>, FilterReminderRepository{

    Page<Reminder> findAllByUser(User user, Pageable pageable);

    List<Reminder> findAllByUser(User user);

    List<Reminder> findAllByUser(User user, Sort sort);

    Optional<Reminder> findByIdAndUser(Long id, User user);

}
