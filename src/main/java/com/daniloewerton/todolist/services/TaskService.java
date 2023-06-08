package com.daniloewerton.todolist.services;

import com.daniloewerton.todolist.config.CacheConfig;
import com.daniloewerton.todolist.domain.Task;
import com.daniloewerton.todolist.domain.dto.TaskDTO;
import com.daniloewerton.todolist.repositories.TaskRepository;
import com.daniloewerton.todolist.repositories.UserRepository;
import com.daniloewerton.todolist.services.exceptions.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository repository;
    private final UserRepository userRepository;

    public Task create(final TaskDTO taskDTO) {

        try {
            final Task task = Task.converter(taskDTO);
            task.setUser(userRepository.findById(taskDTO.getUserId()).get());
            return repository.saveAndFlush(task);
        } catch (final Exception ex) {
            throw new ObjectNotFoundException("Object Not Found.");
        }
    }

    public Task getTask(final Long id) {
        return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Object Not Found."));
    }

    @Cacheable(cacheManager = CacheConfig.CACHE_MANAGER,
            value = CacheConfig.CACHE_TASK,
            key = "#userId",
            condition = "#userId != null")
    public List<Task> getAllByUser(final Long userId) {

        final List<Task> tasks = repository.getTasksByUserId(userId);
        if (tasks.isEmpty()) {
            throw new ObjectNotFoundException("Object Not Found.");
        }
        return tasks;
    }

    public Task update(final TaskDTO dto, final Long id) {

        final Task task = repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Object Not Found."));

        task.setId(id);
        task.setDescription(dto.getDescription());
        task.setDeadline(dto.getDeadline());
        task.setStatus(dto.getStatus());
        return task;
    }

    @CacheEvict(cacheManager = CacheConfig.CACHE_MANAGER,
            value = CacheConfig.CACHE_TASK,
            key = "#userId")
    public void evictCache(final Long userId) {}
}