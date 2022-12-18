package com.daniloewerton.todolist.domain;

import com.daniloewerton.todolist.domain.dto.TaskDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "ACTIVITYTABLE")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class Task {

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

    public static Task converter(TaskDTO obj) {
        Task task = new Task();
        task.setId(obj.getId());
        task.setDescription(obj.getDescription());
        task.setDeadline(obj.getDeadline());
        task.setStatus(obj.getStatus());
        return task;
    }
}
