package com.daniloewerton.todolist.domain.dto.request;

import com.daniloewerton.todolist.domain.Task;
import com.daniloewerton.todolist.domain.dto.RoleDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDtoRequest implements Serializable {

    private Long id;
    @NotBlank(message = "Mandatory field")
    private String name;
    @JsonIgnore
    private Set<Task> tasks;
    private String email;
    @NotEmpty(message = "Mandatory field")
    @Size(min = 1, max = 2, message = "Unexpected amount of roles")
    private Set<RoleDTO> roles = new HashSet<>();
}
