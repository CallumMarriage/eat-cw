package com.coursework.lessons.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * Created by callummarriage on 07/12/2019.
 */
@Entity
@Data
@Table(name = "lessons")
public class Lesson {

    @Id
    @Column(name = "lessonid")
    private String lessonId;

    @Column(name = "description")
    private String description;

    @Column(name = "level")
    private Short level;

    @Column(name = "startdatetime")
    private LocalDateTime startDateTime;

    @Column(name = "enddatetime")
    private LocalDateTime endDateTime;
}
