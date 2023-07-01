package com.daniloewerton.todolist.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "tb_task")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class Task implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private LocalDate deadline;
    @Enumerated(value = EnumType.STRING)
    private Status status;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "USER_ACTIVITY")
    private User user;
}
