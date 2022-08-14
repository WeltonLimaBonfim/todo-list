package com.api.todolist.infrastructure.entity;

import com.api.todolist.common.enums.StatusEnum;
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
@Table(name = "TbStatus")
public class StatusEntity {
    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Description", nullable = false, unique = true)
    private String description;

    @CreatedDate
    @Column(name = "DtInsert", nullable = false, updatable = false)
    private LocalDateTime dtInsert;

    @LastModifiedDate
    @Column(name = "DtUpdate")
    private LocalDateTime dtUpdate;

    public StatusEntity(String description, LocalDateTime dtInsert, LocalDateTime dtUpdate) {
        this.description = description;
        this.dtInsert = dtInsert;
        this.dtUpdate = dtUpdate;
    }
}
