package com.pet.reminder_app.database.repository;

import com.pet.reminder_app.database.model.Reminder;
import com.pet.reminder_app.dto.ReminderFilter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class FilterReminderRepositoryImpl implements FilterReminderRepository {

    private final EntityManager entityManager;

    @Override
    public List<Reminder> findAllByFilter(ReminderFilter filter) {

        var cb = entityManager.getCriteriaBuilder();
        var criteria = cb.createQuery(Reminder.class);

        var reminder = criteria.from(Reminder.class);
        criteria.select(reminder);

        List<Predicate> predicates = new ArrayList<>();

        predicates.add(cb.equal(reminder.get("user").get("id"), filter.getUserId()));

        if (filter.getTitle() != null && !filter.getTitle().isBlank()){
            predicates.add(cb.equal(reminder.get("title"), filter.getTitle()));
        }
        if (filter.getDescription() != null && !filter.getDescription().isBlank()){
            predicates.add(cb.like(reminder.get("description"), "%" + filter.getDescription() + "%"));
        }
        if (filter.getDate() != null && filter.getTime() != null){
            LocalDateTime localDateTime = filter.getDate().atTime(filter.getTime());
            predicates.add(cb.lessThanOrEqualTo(reminder.get("remind"), localDateTime));
        }else if (filter.getDate() != null){
            predicates.add(cb.lessThanOrEqualTo(reminder.get("remind"), filter.getDate()));
        }else if (filter.getTime() != null){
            predicates.add(cb.lessThanOrEqualTo(reminder.get("remind"), filter.getTime()));
        }

        criteria.where(predicates.toArray(new Predicate[predicates.size()]));

        return entityManager.createQuery(criteria).getResultList();
    }
}
