package com.daniloewerton.todolist.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private JWTUtil jwtUtil;
    private UserDetailsService userDetailsService;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil, UserDetailsService userDetailsService) {
        super(authenticationManager);
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    @SneakyThrows
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain) {
        final String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            final UsernamePasswordAuthenticationToken authToken = getAuthentication(header.substring(7));
            if (authToken != null) {
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(final String token) {
        if (jwtUtil.validateToken(token)) {
            final String username = jwtUtil.getUsername(token);
            final UserDetails details = userDetailsService.loadUserByUsername(username);
            return new UsernamePasswordAuthenticationToken(details.getUsername(), null, details.getAuthorities());
        }
        return null;
    }
}
