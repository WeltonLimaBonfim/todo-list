package com.api.todolist.entity;

import com.api.todolist.enums.StatusEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "TB_STATUS")
public class StatusEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME_STATUS", nullable = false, unique = true)
    private StatusEnum name;

    @CreatedDate
    @Column(name = "DT_INSERT", nullable = false)
    private LocalDateTime dtInsert;

    @LastModifiedDate
    @Column(name = "DT_UPDATE")
    private LocalDateTime dtUpdate;

    public StatusEntity(StatusEnum name, LocalDateTime dtInsert, LocalDateTime dtUpdate) {
        this.name = name;
        this.dtInsert = dtInsert;
        this.dtUpdate = dtUpdate;
    }
}
