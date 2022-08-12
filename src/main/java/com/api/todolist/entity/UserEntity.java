package com.api.todolist.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="TB_USER")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "client_id", nullable = false, unique = true)
    private String clientId;

    @Column(name = "client_secret", nullable = false)
    private String clientSecret;

    @Column(name = "admin", nullable = false)
    private Boolean admin;

    public UserEntity(String clientId, String clientSecret, Boolean admin) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.admin = admin;
    }
}
