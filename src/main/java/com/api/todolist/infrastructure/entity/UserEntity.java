package com.api.todolist.infrastructure.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="TbUser")
public class UserEntity implements Serializable {
    @Id
    @Column(name = "Id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "Email", nullable = false)
    private String email;

    @Column(name = "ClientId", nullable = false, unique = true)
    private String clientId;

    @Column(name = "ClientSecret", nullable = false)
    private String clientSecret;

    @Column(name = "FlAdmin", nullable = false)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean flAdmin;

    @CreatedDate
    @Column(name = "DtInsert", nullable = false, updatable = false)
    private LocalDateTime dtInsert;

    @LastModifiedDate
    @Column(name = "DtUpdate")
    private LocalDateTime dtUpdate;

    public UserEntity(String name, String email, String clientId, String clientSecret, boolean flAdmin, LocalDateTime dtInsert, LocalDateTime dtUpdate) {
        this.name = name;
        this.email = email;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.flAdmin = flAdmin;
        this.dtInsert = dtInsert;
        this.dtUpdate = dtUpdate;
    }
}
