package com.coursework.lessons.repository;

import com.coursework.lessons.model.entity.LessonsBooked;
import com.coursework.lessons.model.entity.LessonsBookedKey;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LessonsBookedRepository extends CrudRepository<LessonsBooked, LessonsBookedKey> {

    Optional<LessonsBooked> findDistinctByClientIdAndLessonId(Integer clientId, String lessonId);

    List<LessonsBooked> findAllByClientId(Integer clientId);

    void deleteAllByClientId(Integer clientId);
}
