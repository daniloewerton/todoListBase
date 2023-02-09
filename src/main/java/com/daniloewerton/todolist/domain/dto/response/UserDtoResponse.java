package com.daniloewerton.todolist.domain.dto.response;

import com.daniloewerton.todolist.domain.Task;
import com.daniloewerton.todolist.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDtoResponse {

    private Long id;
    @NotNull
    private String name;
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
