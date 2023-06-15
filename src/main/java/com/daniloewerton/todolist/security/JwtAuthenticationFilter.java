package com.daniloewerton.todolist.security;

import com.daniloewerton.todolist.domain.dto.CredentialDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Date;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    @Override
    @SneakyThrows
    public Authentication attemptAuthentication(final HttpServletRequest request, final HttpServletResponse response) {
        try {
            final CredentialDTO credentials = new ObjectMapper().readValue(request.getInputStream(), CredentialDTO.class);
            final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword());
            return authenticationManager.authenticate(authenticationToken);
        } catch (final Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    @SneakyThrows
    protected void successfulAuthentication(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain, final Authentication authResult) {
        final String username = ((UserSS) authResult.getPrincipal()).getUsername();
        final String token = jwtUtil.generateToken(username);
        response.setHeader("access-control-expose-headers", "Authorization");
        response.setHeader("Authorization", "Bearer " + token);
    }

    @Override
    @SneakyThrows
    protected void unsuccessfulAuthentication(final HttpServletRequest request, final HttpServletResponse response, final AuthenticationException failed) {
        response.setStatus(401);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().append(json());
    }

    //Ver como pode simplificar
    private CharSequence json() {
        long date = new Date().getTime();
        return "{"
                + "\"timestamp\": " + date + ", "
                + "\"status\": 401, "
                + "\"error\": \"Não autorizado\", "
                + "\"message\": \"Email ou senha inválidos\", "
                + "\"path\": \"/login\"}";
    }
}
