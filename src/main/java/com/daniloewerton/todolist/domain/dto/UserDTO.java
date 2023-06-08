package com.daniloewerton.todolist.domain.dto;

import com.daniloewerton.todolist.domain.Task;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class UserDTO implements Serializable {

    private Long id;
    @NotNull
    private String name;
    private Set<Task> tasks;
    @NotNull
    private String email;
    @NotNull
    private String password;
}