package com.daniloewerton.todolist.domain.dto;

import com.daniloewerton.todolist.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO implements Serializable {

    private Long id;
    private String authority;

    public static RoleDTO converter(final Role role) {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(role.getId());
        roleDTO.setAuthority(role.getAuthority());
        return roleDTO;
    }
}
