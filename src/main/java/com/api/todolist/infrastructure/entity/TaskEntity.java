package com.api.todolist.infrastructure.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "TbTask")
@EntityListeners(AuditingEntityListener.class)
public class TaskEntity {
    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name="IdUser")
    private UserEntity user;

    @ManyToOne()
    @JoinColumn(name="IdStatus")
    private StatusEntity status;

    @Column(name = "Resume", nullable = false)
    private String resume;

    @Column(name = "Description", nullable = false)
    private String description;

    @CreatedDate
    @Column(name = "DtInsert", nullable = false, updatable = false)
    private LocalDateTime dtInsert = LocalDateTime.now();

    @LastModifiedDate
    @Column(name = "DtUpdate")
    private LocalDateTime dtUpdate = LocalDateTime.now();

    public TaskEntity(UserEntity user, StatusEntity status, String resume, String description) {
        this.user = user;
        this.status = status;
        this.resume = resume;
        this.description = description;
    }
}
