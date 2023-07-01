package com.daniloewerton.todolist.controllers;

import com.daniloewerton.todolist.domain.User;
import com.daniloewerton.todolist.domain.dto.UserDTO;
import com.daniloewerton.todolist.domain.dto.request.UserDtoRequest;
import com.daniloewerton.todolist.domain.dto.response.UserDtoResponse;
import com.daniloewerton.todolist.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "Users")
public class UserController {

    private static final String ID = "/{id}";

    private final UserService service;

    @PostMapping("/")
    @Operation(summary = "create a new user")
    public ResponseEntity<UserDtoResponse> create(@RequestBody @Valid final UserDTO request) {
        final User user = service.create(request);
        final URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path(ID).buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping(ID)
    @Operation(summary = "get a specific user by its id")
    public ResponseEntity<UserDtoResponse> getUser(@PathVariable final Long id) {
        return ResponseEntity.ok().body(service.findById(id));
    }

    @PutMapping(ID)
    @Operation(summary = "update an user by its id")
    public ResponseEntity<UserDtoResponse> update(@RequestBody @Valid final UserDtoRequest request) {
        final UserDtoResponse user = service.update(request);
        return ResponseEntity.ok().body(user);
    }

    @DeleteMapping(ID)
    @Operation(summary = "evict user's cache by its id")
    public ResponseEntity<Void> evictCache(@PathVariable final Long id) {
        service.evictCache(id);
        return ResponseEntity.noContent().build();
    }
}