package com.daniloewerton.todolist.domain.dto;

import com.daniloewerton.todolist.domain.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class TaskDTO implements Serializable {

    private Long id;
    @NotBlank(message = "Mandatory field")
    private String description;
    @NotNull(message = "Mandatory field")
    private LocalDate deadline;
    @NotNull(message = "Mandatory field")
    private Status status;
}
