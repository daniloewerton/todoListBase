package com.daniloewerton.todolist.domain.dto.response;

import com.daniloewerton.todolist.domain.Task;
import com.daniloewerton.todolist.domain.dto.RoleDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDtoResponse implements Serializable {

    private Long id;
    private String name;
    @JsonIgnore
    private Set<Task> tasks;
    private String email;
    private Set<RoleDTO> roles = new HashSet<>();
}
