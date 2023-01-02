package com.daniloewerton.todolist.controllers;

import com.daniloewerton.todolist.domain.User;
import com.daniloewerton.todolist.domain.dto.UserDTO;
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
    public ResponseEntity<UserDTO> create(@RequestBody @Valid final UserDTO dto) {
        User user = service.create(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "get a specific user by its id")
    public ResponseEntity<UserDTO> getUser(@PathVariable final Long id) {
        User user = service.getUser(id);
        return ResponseEntity.ok().body(UserDTO.convert(user));
    }

    @GetMapping
    @Operation(summary = "get all users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> list = service.getAllUsers().stream().map(UserDTO::convert).collect(Collectors.toList());
        return ResponseEntity.ok().body(list);
    }

    @PutMapping("/{id}")
    @Operation(summary = "update an user by its id")
    public ResponseEntity<UserDTO> update(@RequestBody final UserDTO dto, @PathVariable final Long id) {
        User user = service.update(dto, id);
        return ResponseEntity.ok().body(UserDTO.convert(user));
    }
}