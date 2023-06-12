package com.daniloewerton.todolist.services;

import com.daniloewerton.todolist.config.CacheConfig;
import com.daniloewerton.todolist.domain.User;
import com.daniloewerton.todolist.domain.dto.UserDTO;
import com.daniloewerton.todolist.repositories.UserRepository;
import com.daniloewerton.todolist.services.exceptions.DataIntegratyViolation;
import com.daniloewerton.todolist.services.exceptions.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public boolean findByEmail(final String email) {
        Optional<User> user = repository.findByEmail(email);
        return user.isPresent();
    }

    public User create(final UserDTO userDTO) {

        final boolean userExists = findByEmail(userDTO.getEmail());
        if (Boolean.TRUE.equals(userExists)) {
            throw new DataIntegratyViolation("Email in use");
        }
        return repository.save(User.converter(userDTO));
    }

    @Cacheable(cacheManager = CacheConfig.CACHE_MANAGER,
            value = CacheConfig.CACHE_USER,
            key = "#id")
    public User getUser(final Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Object Not Found."));
    }

    public User update(final UserDTO userDTO, final Long id) {

        final User user = repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Object Not Found."));

        final boolean userExists = findByEmail(userDTO.getEmail());

        if (Boolean.TRUE.equals(userExists)) {
            throw new DataIntegratyViolation("Email already exists");
        }

        user.setId(id);
        user.setName(userDTO.getName());
        user.setTasks(userDTO.getTasks());
        user.setEmail(userDTO.getEmail());
        return user;
    }

    @CacheEvict(cacheManager = CacheConfig.CACHE_MANAGER,
            value = CacheConfig.CACHE_USER,
            key = "#id")
    public void evictCache(final Long id) {}
}