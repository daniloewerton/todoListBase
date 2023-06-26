package com.daniloewerton.todolist.controllers;

import com.daniloewerton.todolist.domain.dto.CredentialDTO;
import com.daniloewerton.todolist.domain.dto.response.AuthResponse;
import com.daniloewerton.todolist.services.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    @Operation(summary = "sign up in this application")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid final CredentialDTO data){
        return ResponseEntity.ok(authenticationService.authenticate(data));
    }
}