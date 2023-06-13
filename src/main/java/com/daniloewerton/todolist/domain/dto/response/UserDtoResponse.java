package com.daniloewerton.todolist.domain.dto.response;

import com.daniloewerton.todolist.domain.Task;
import com.daniloewerton.todolist.domain.User;
import com.daniloewerton.todolist.domain.dto.RoleDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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

    public static UserDtoResponse convert(final User user) {
        UserDtoResponse dto = new UserDtoResponse();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setTasks(user.getTasks());
        dto.setRoles(user.getRoles()
                .stream()
                .map(RoleDTO::converter)
                .collect(Collectors.toSet()));
        return dto;
    }
}
