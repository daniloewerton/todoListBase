package com.daniloewerton.todolist.infra.mapper;

import com.daniloewerton.todolist.domain.Role;
import com.daniloewerton.todolist.domain.dto.RoleDTO;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    Set<RoleDTO> toRole(Set<Role> roleDTO);
}
