package com.coursework.lessons.model.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name= "lessons_booked")
@IdClass(LessonsBookedKey.class)
public class LessonsBooked {

    @Id
    @Column(name="clientid")
    private Integer clientId;

    @Id
    @Column(name = "lessonid")
    private String lessonId;

}
