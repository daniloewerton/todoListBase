package com.daniloewerton.todolist.domain.dto;

import com.daniloewerton.todolist.domain.Task;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "Mandatory field")
    private String name;
    private Set<Task> tasks;
    @NotBlank(message = "Mandatory field")
    private String email;
    @NotBlank(message = "Mandatory field")
    private String password;
}