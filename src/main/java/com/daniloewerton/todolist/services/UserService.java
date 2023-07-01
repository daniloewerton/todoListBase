package com.daniloewerton.todolist.services;

import com.daniloewerton.todolist.config.CacheConfig;
import com.daniloewerton.todolist.domain.User;
import com.daniloewerton.todolist.domain.dto.UserDTO;
import com.daniloewerton.todolist.domain.dto.request.UserDtoRequest;
import com.daniloewerton.todolist.domain.dto.response.UserDtoResponse;
import com.daniloewerton.todolist.infra.mapper.RoleMapper;
import com.daniloewerton.todolist.infra.mapper.UserMapper;
import com.daniloewerton.todolist.infra.security.AuthenticationService;
import com.daniloewerton.todolist.repositories.UserRepository;
import com.daniloewerton.todolist.services.exceptions.ForbiddenException;
import com.daniloewerton.todolist.services.exceptions.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final RoleMapper roleMapper;
    private final UserRepository repository;
    private final BCryptPasswordEncoder encoder;
    private final AuthenticationService authenticationService;

    @Cacheable(cacheManager = CacheConfig.CACHE_MANAGER,
                value = CacheConfig.CACHE_USER,
                key = "#id")
    public UserDtoResponse findById(final Long id) {
        Optional<User> user = repository.findById(id);
        return userMapper.toUserResponse(user.orElseThrow(() -> new ObjectNotFoundException("Object Not Found.")));
    }

    public User create(final UserDTO userDTO) {

        if (authenticationService.isAdmin()) {
            userDTO.setPassword(encoder.encode(userDTO.getPassword()));
            return repository.save(userMapper.toUser(userDTO));
        }
        throw new ForbiddenException("User not allowed to create a new user.");
    }

    public UserDtoResponse update(final UserDtoRequest userDTO, final Long userId) {

        final Optional<User> authenticatedUser = authenticationService.getAuthenticatedUser();

        if (authenticatedUser.isPresent() && authenticatedUser.get().getId().equals(userId)) {
            userDTO.setId(userId);
            userDTO.setName(userDTO.getName());
            userDTO.setEmail(userDTO.getEmail());
            userDTO.setPassword(encoder.encode(userDTO.getPassword()));
            userDTO.setRoles(roleMapper.toRole(authenticatedUser.get().getRoles()));
            repository.save(userMapper.toUser(userDTO));
            return userMapper.toUserResponse(userDTO);
        }
        throw new ForbiddenException("User not authenticated or not allowed.");
    }

    @CacheEvict(cacheManager = CacheConfig.CACHE_MANAGER,
                value = CacheConfig.CACHE_USER,
                key = "#id")
    public void evictCache(final Long id) {}
}