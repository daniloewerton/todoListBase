package com.daniloewerton.todolist.services;

import com.daniloewerton.todolist.config.CacheConfig;
import com.daniloewerton.todolist.domain.Task;
import com.daniloewerton.todolist.domain.User;
import com.daniloewerton.todolist.domain.dto.TaskDTO;
import com.daniloewerton.todolist.infra.mapper.TaskMapper;
import com.daniloewerton.todolist.repositories.TaskRepository;
import com.daniloewerton.todolist.infra.security.AuthenticationService;
import com.daniloewerton.todolist.services.exceptions.ForbiddenException;
import com.daniloewerton.todolist.services.exceptions.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TaskService {

    private final TaskMapper taskMapper;
    private final TaskRepository repository;
    private final AuthenticationService authenticationService;

    public TaskDTO create(final TaskDTO taskDTO) {

        final Optional<User> authenticatedUser = authenticationService.getAuthenticatedUser();

        if (authenticatedUser.isPresent()) {
            try {
                final Task task = taskMapper.toTask(taskDTO);
                task.setUser(authenticatedUser.get());
                return taskMapper.toTaskDTO(repository.saveAndFlush(task));
            } catch (final Exception ex) {
                throw new ObjectNotFoundException("Object Not Found.");
            }
        }
        throw new ForbiddenException("User not authenticated.");
    }

    public TaskDTO getTask(final Long id) {
        return taskMapper.toTaskDTO(repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Object Not Found.")));
    }

    @Cacheable(cacheManager = CacheConfig.CACHE_MANAGER,
                value = CacheConfig.CACHE_TASK,
                key = "#userId",
                condition = "#userId != null")
    public List<TaskDTO> getAllByUser(final Long userId) {

        final Optional<User> authenticatedUser = authenticationService.getAuthenticatedUser();

        if (authenticatedUser.isPresent() && authenticatedUser.get().getId().equals(userId)) {
            final List<Task> tasks = repository.getTasksByUserId(authenticatedUser.get().getId());
            if (tasks.isEmpty()) {
                throw new ObjectNotFoundException("User does not have any tasks.");
            }
            return tasks.stream().map(taskMapper::toTaskDTO).collect(Collectors.toList());
        }
        throw new ForbiddenException("User not authenticated.");
    }

    public TaskDTO update(final TaskDTO dto, final Long taskId) {

        final Optional<User> authenticatedUser = authenticationService.getAuthenticatedUser();

        if (authenticatedUser.isPresent()) {
            if (verifyRequestIntegrity(authenticatedUser.get().getId(), taskId)) {
                final Task task = taskMapper.toTask(getTask(taskId));

                task.setId(taskId);
                task.setDescription(dto.getDescription());
                task.setDeadline(dto.getDeadline());
                task.setStatus(dto.getStatus());
                task.setUser(authenticatedUser.get());
                return taskMapper.toTaskDTO(repository.save(task));
            }
            throw new ForbiddenException("User does not own this task.");
        }
        throw new ForbiddenException("User not authenticated.");
    }

    @CacheEvict(cacheManager = CacheConfig.CACHE_MANAGER,
                value = CacheConfig.CACHE_TASK,
                key = "#userId")
    public void evictCache(final Long userId) {}

    private boolean verifyRequestIntegrity(final Long userId, final Long taskId) {
        final List<Task> list = repository.getTasksByUserId(userId);
        for (final Task task : list) {
            if (task.getId().equals(taskId)) {
                return true;
            }
        }
        return false;
    }
}