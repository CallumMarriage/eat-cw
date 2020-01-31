package com.coursework.lessons.service;

import com.coursework.lessons.exception.LessonBookedException;
import com.coursework.lessons.mapper.LessonMapper;
import com.coursework.lessons.model.entity.Lesson;
import com.coursework.lessons.model.entity.LessonsBooked;
import com.coursework.lessons.model.presentation.LessonPresentation;
import com.coursework.lessons.repository.LessonRepository;
import com.coursework.lessons.repository.LessonsBookedRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class LessonService {

    private final LessonRepository lessonRepository;

    private final LessonsBookedRepository lessonsBookedRepository;


    @Autowired
    public LessonService(LessonRepository lessonRepository, LessonsBookedRepository lessonsBookedRepository) {
        this.lessonRepository = lessonRepository;
        this.lessonsBookedRepository = lessonsBookedRepository;
    }


    /**
     * Finds lessons by their ids.
     *
     * @param lessonIds ids of lessons to be returned
     * @return list of lessons
     */
    public List<LessonPresentation> getAllLessonsByMultipleIds(Collection<String> lessonIds){
        Iterable<Lesson> lessonIterable = lessonRepository.findAllById(lessonIds);

        List<Lesson> lessons = new ArrayList<>();
        lessonIterable.forEach(lessons::add);

        return lessons.stream()
                .map(LessonMapper::mapLessonToPresentation)
                .collect(Collectors.toList());
    }

    @Transactional
    public void removeCurrentLessonsForClient(Integer clientID){
        lessonsBookedRepository.deleteAllByClientId(clientID);
    }


    /**
     * Creates a new row in the lesson booked table for each new lesson the user is apply to
     *
     * @param clientID id of the current client
     * @param lessons list of lessons they want to be booked onto.
     * @throws LessonBookedException
     */
    public void finaliseBookingForClient(Integer clientID, Collection<String> lessons) throws LessonBookedException{

        lessons.stream()
                .map(id -> new LessonsBooked(clientID, id))
                .forEach(lessonsBookedRepository::save);

    }

    /**
     * Gets all available lessons from database and maps to presentation model.
     *
     * @return List of Lessons
     */
    public List<LessonPresentation> getAllLessons(){
        return lessonRepository.findAll()
                .stream()
                .map(LessonMapper::mapLessonToPresentation)
                .collect(Collectors.toList());
    }
}
