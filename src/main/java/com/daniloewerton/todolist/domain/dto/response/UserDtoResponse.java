package com.daniloewerton.todolist.domain.dto.response;

import com.daniloewerton.todolist.domain.Task;
import com.daniloewerton.todolist.domain.dto.RoleDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Set;

public record UserDtoResponse (Long id,
                               String name,
                               @JsonIgnore Set<Task> tasks,
                               String email,
                               Set<RoleDTO> roles) implements Serializable  { }
