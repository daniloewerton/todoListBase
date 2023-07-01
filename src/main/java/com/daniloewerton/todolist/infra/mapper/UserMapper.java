package com.daniloewerton.todolist.infra.mapper;

import com.daniloewerton.todolist.domain.User;
import com.daniloewerton.todolist.domain.dto.UserDTO;
import com.daniloewerton.todolist.domain.dto.request.UserDtoRequest;
import com.daniloewerton.todolist.domain.dto.response.UserDtoResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(final UserDTO userDTO);
    User toUser(final UserDtoRequest request);
    UserDtoResponse toUserResponse(final User user);
    UserDtoResponse toUserResponse(final UserDtoRequest dto);
}
