package com.daniloewerton.todolist.domain;

import com.daniloewerton.todolist.domain.dto.RoleDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_role")
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String authority;

    public static Role converter(final RoleDTO roleDTO) {
        Role role = new Role();
        role.setId(roleDTO.getId());
        role.setAuthority(roleDTO.getAuthority());
        return role;
    }
}
