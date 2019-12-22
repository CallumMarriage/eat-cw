package com.coursework.users.model;

import lombok.*;

import javax.persistence.*;

/**
 * Created by callummarriage on 08/12/2019.
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@Table(name ="clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "clientid")
    private Integer clientId;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;


    public Client(Integer id, String username, String password){
        this.clientId = id;
        this.username = username;
        this.password = password;
    }
}
