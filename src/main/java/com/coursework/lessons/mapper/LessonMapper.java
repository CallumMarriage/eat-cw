package com.coursework.lessons.mapper;

import com.coursework.lessons.model.entity.Lesson;
import com.coursework.lessons.model.presentation.LessonPresentation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


public class LessonMapper {

    public static LessonPresentation mapLessonToPresentation(Lesson lesson){


        return LessonPresentation.builder()
                .lessonId(lesson.getLessonId())
                .description(lesson.getDescription())
                .lessonDate(LocalDate.from(lesson.getStartDateTime()))
                .startTime(getLocalTimeFromDate(lesson.getStartDateTime()))
                .endTime(getLocalTimeFromDate(lesson.getEndDateTime()))
                .level(lesson.getLevel()).build();
    }

    private static LocalTime getLocalTimeFromDate(LocalDateTime dateTime){
        return LocalTime.of(dateTime.getHour(),
                dateTime.getMinute(),
                dateTime.getSecond());
    }
}
