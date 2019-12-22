package com.coursework.lessons.model.presentation;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Created by callummarriage on 07/12/2019.
 */
@Data
@Builder
public class LessonPresentation {


    private String lessonId;

    private String description;

    private Short level;

    private LocalDate lessonDate;

    private LocalTime startTime;

    private LocalTime endTime;

    private Boolean bookedThisLessonBefore;
}
