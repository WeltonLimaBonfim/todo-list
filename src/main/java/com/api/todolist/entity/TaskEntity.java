package com.api.todolist.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "TB_TASK")
@EntityListeners(AuditingEntityListener.class)
public class TaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name="ID_USER")
    private UserEntity user;

    @ManyToOne()
    @JoinColumn(name="ID_STATUS")
    private StatusEntity status;

    @Column(name = "RESUME", nullable = false)
    private String resume;

    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

    @CreatedDate
    @Column(name = "DT_INSERT", nullable = false, updatable = false)
    private LocalDateTime dtInsert;

    @LastModifiedDate
    @Column(name = "DT_UPDATE")
    private LocalDateTime dtUpdate;

    public TaskEntity(UserEntity user, StatusEntity status, String resume, String description, LocalDateTime dtInsert, LocalDateTime dtUpdate) {
        this.user = user;
        this.status = status;
        this.resume = resume;
        this.description = description;
        this.dtInsert = dtInsert;
        this.dtUpdate = dtUpdate;
    }
}
