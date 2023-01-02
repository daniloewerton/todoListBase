package com.daniloewerton.todolist.domain.dto;

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
@Getter @Setter
public class UserDTO {

    private Long id;
    @NotNull
    private String name;
    private Set<Task> tasks;
    @NotNull
    private String email;
    @NotNull
    private String password;

    public static UserDTO convert(final User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());
        dto.setTasks(user.getTasks());
        return dto;
    }
}