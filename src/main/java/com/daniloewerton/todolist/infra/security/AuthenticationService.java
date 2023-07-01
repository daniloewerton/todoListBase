package com.daniloewerton.todolist.infra.security;

import com.daniloewerton.todolist.domain.Role;
import com.daniloewerton.todolist.domain.User;
import com.daniloewerton.todolist.domain.dto.request.CredentialDTO;
import com.daniloewerton.todolist.domain.dto.response.AuthResponse;
import com.daniloewerton.todolist.repositories.UserRepository;
import com.daniloewerton.todolist.services.exceptions.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final TokenUtils tokenUtils;
    private final UserRepository repository;

    public AuthResponse authenticate(final CredentialDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.getEmail(), data.getPassword());
        Authentication auth = authenticationManager.authenticate(usernamePassword);
        return new AuthResponse(tokenUtils.generateToken((UserSS) auth.getPrincipal()));
    }

    public Optional<User> getAuthenticatedUser() {
        try {
            final String email = SecurityContextHolder.getContext().getAuthentication().getName();
            return repository.findByEmail(email);
        } catch (final Exception e) {
            throw new ObjectNotFoundException("Invalid User.");
        }
    }

    public boolean isAdmin() {
        final Optional<User> userOptional = getAuthenticatedUser();
        if (userOptional.isPresent()) {
            final User user = userOptional.get();
            final Set<Role> roles = user.getRoles();
            for (final Role role : roles) {
                if (role.getAuthority().equals("ADMIN")) {
                    return true;
                }
            }
        }
        return false;
    }
}
