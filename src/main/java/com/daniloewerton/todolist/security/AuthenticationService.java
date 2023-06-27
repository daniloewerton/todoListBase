package com.daniloewerton.todolist.security;

import com.daniloewerton.todolist.domain.dto.CredentialDTO;
import com.daniloewerton.todolist.domain.dto.response.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final TokenUtils tokenUtils;

    public AuthResponse authenticate(final CredentialDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.getEmail(), data.getPassword());
        Authentication auth = authenticationManager.authenticate(usernamePassword);
        return new AuthResponse(tokenUtils.generateToken((UserSS) auth.getPrincipal()));
    }
}
