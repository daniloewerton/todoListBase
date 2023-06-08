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

    public Optional<User> findByEmail(final String email) {
        return repository.findByEmail(email);
    }

    public User create(final UserDTO userDTO) {
        final Optional<User> user = findByEmail(userDTO.getEmail());

        if (user.isPresent()) {
            throw new DataIntegratyViolation("Email already exists");
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

    public User update(final UserDTO dto, Long id) {

        final User user = repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Object Not Found."));

        user.setId(id);
        user.setName(dto.getName());
        user.setTasks(dto.getTasks());
        return user;
    }

    @CacheEvict(cacheManager = CacheConfig.CACHE_MANAGER,
            value = CacheConfig.CACHE_USER,
            key = "#id")
    public void evictCache(final Long id) {}
}