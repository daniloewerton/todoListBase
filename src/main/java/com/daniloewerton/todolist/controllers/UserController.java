package com.daniloewerton.todolist.controllers;

import com.daniloewerton.todolist.domain.User;
import com.daniloewerton.todolist.domain.dto.UserDTO;
import com.daniloewerton.todolist.domain.dto.response.UserDtoResponse;
import com.daniloewerton.todolist.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "Users")
public class UserController {

    private final UserService service;

    @PostMapping
    @Operation(summary = "create a new user")
    public ResponseEntity<UserDtoResponse> create(@RequestBody @Valid final UserDTO dto) {
        User user = service.create(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "get a specific user by its id")
    public ResponseEntity<UserDtoResponse> getUser(@PathVariable final Long id) {
        User user = service.getUser(id);
        return ResponseEntity.ok().body(UserDtoResponse.convert(user));
    }

    @GetMapping
    @Operation(summary = "get all users")
    public ResponseEntity<List<UserDtoResponse>> getAllUsers() {
        List<UserDtoResponse> list = service.getAllUsers().stream().map(UserDtoResponse::convert).collect(Collectors.toList());
        return ResponseEntity.ok().body(list);
    }

    @PutMapping("/{id}")
    @Operation(summary = "update an user by its id")
    public ResponseEntity<UserDtoResponse> update(@RequestBody final UserDTO dto, @PathVariable final Long id) {
        User user = service.update(dto, id);
        return ResponseEntity.ok().body(UserDtoResponse.convert(user));
    }
}