package com.daniloewerton.todolist.config;

import com.daniloewerton.todolist.security.JWTUtil;
import com.daniloewerton.todolist.security.JwtAuthenticationFilter;
import com.daniloewerton.todolist.security.JwtAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private static final String[] AUTH_WHITE_LIST = {
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/v2/api-docs/**",
            "/swagger-resources/**"
    };

    private final JWTUtil jwtUtil;
    private final AuthenticationConfiguration configuration;
    private final UserDetailsService userDetailsService;

    @Bean
    @SneakyThrows
    public SecurityFilterChain filterChain(final HttpSecurity http) {
        return http.authorizeHttpRequests()
                .requestMatchers(PathRequest.toH2Console())
                .permitAll()
                .requestMatchers(AUTH_WHITE_LIST)
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .headers()
                .frameOptions()
                .disable()
                .and()
                .cors()
                .and()
                .csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JwtAuthenticationFilter(configuration.getAuthenticationManager(), jwtUtil))
                .addFilter(new JwtAuthorizationFilter(configuration.getAuthenticationManager(), jwtUtil, userDetailsService))
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
        configuration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "OPTIONS"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
