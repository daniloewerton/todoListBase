package com.daniloewerton.todolist.domain.dto;

import com.daniloewerton.todolist.domain.Task;
import com.daniloewerton.todolist.domain.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class TaskDTO {

    private Long id;
    @NotNull
    private String description;
    @NotNull
    private LocalDate deadline;
    @NotNull
    private Status status;
    @NotNull
    private Long userId;

    public static TaskDTO convert(final Task task) {
        TaskDTO dto = new TaskDTO();
        dto.setId(task.getId());
        dto.setDescription(task.getDescription());
        dto.setDeadline(task.getDeadline());
        dto.setStatus(task.getStatus());
        dto.setUserId(task.getUser().getId());
        return dto;
    }
}
