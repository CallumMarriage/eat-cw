package com.coursework.lessons.repository;

import com.coursework.lessons.model.entity.Lesson;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by callummarriage on 07/12/2019.
 */
@Repository
public interface LessonRepository extends CrudRepository<Lesson, String> {

    List<Lesson> findAll();

    Optional<Lesson> findDistinctByLessonId(String lessonId);
}
