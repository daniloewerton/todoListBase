package com.daniloewerton.todolist.services;

import com.daniloewerton.todolist.domain.Task;
import com.daniloewerton.todolist.domain.dto.TaskDTO;
import com.daniloewerton.todolist.repositories.TaskRepository;
import com.daniloewerton.todolist.repositories.UserRepository;
import com.daniloewerton.todolist.services.exceptions.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository repository;
    private final UserRepository userRepository;

    public Task create(final TaskDTO taskDTO) {
        try {
            Task task = Task.converter(taskDTO);
            task.setUser(userRepository.findById(taskDTO.getUserId()).get());
            return repository.save(task);
        } catch (Exception ex) {
            throw new ObjectNotFoundException("Object Not Found.");
        }
    }

    public Task getTask(final Long id) {
        return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Object Not Found."));
    }

    public List<Task> getAllTasks() {
        return repository.findAll();
    }

    public Task update(final TaskDTO dto, final Long id) {

        Task task = repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Object Not Found."));
        task.setId(id);
        task.setDescription(dto.getDescription());
        task.setDeadline(dto.getDeadline());
        task.setStatus(dto.getStatus());
        return task;
    }
}