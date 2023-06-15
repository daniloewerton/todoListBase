package com.daniloewerton.todolist.services;

import com.daniloewerton.todolist.domain.User;
import com.daniloewerton.todolist.repositories.UserRepository;
import com.daniloewerton.todolist.security.UserSS;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository repository;

    @Override
    @SneakyThrows
    public UserDetails loadUserByUsername(final String email) {

        final Optional<User> optional = repository.findByEmail(email);

        if (optional.isPresent()) {
            final User user = optional.get();
            return new UserSS(user.getId(), user.getEmail(), user.getPassword(), user.getRoles());
        }
        throw new UsernameNotFoundException(email);
    }
}
