package com.daniloewerton.todolist.security;

import com.daniloewerton.todolist.domain.User;
import com.daniloewerton.todolist.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

    private final TokenUtils tokenUtils;
    private final UserRepository userRepository;

    @Override
    @SneakyThrows
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain) {
        final String token = this.recoverToken(request);
        if (token != null) {
            final String login = tokenUtils.validateToken(token);
            final Optional<User> user = userRepository.findByEmail(login);
            if (user.isPresent()) {
                final UserSS userSS = new UserSS(user.get().getId(), user.get().getEmail(), user.get().getPassword(), user.get().getRoles());
                final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userSS.getUsername(), null, userSS.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.info("m=doFilterInternal c=AuthenticationFilter :::: User authenticated successfully: {}", userSS.getUsername());
            }
        }
        filterChain.doFilter(request, response);
    }

    private String recoverToken(final HttpServletRequest request) {
        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }
}