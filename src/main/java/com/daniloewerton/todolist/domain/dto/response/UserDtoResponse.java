package com.daniloewerton.todolist.domain.dto.response;

import com.daniloewerton.todolist.domain.Task;
import com.daniloewerton.todolist.domain.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDtoResponse implements Serializable {

    private Long id;
    @NotNull
    private String name;
    @JsonIgnore
    private Set<Task> tasks;
    @NotNull
    private String email;

    public static UserDtoResponse convert(final User user) {
        UserDtoResponse dto = new UserDtoResponse();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setTasks(user.getTasks());
        return dto;
    }
}
