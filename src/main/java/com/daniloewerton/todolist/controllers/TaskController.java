package com.daniloewerton.todolist.controllers;

import com.daniloewerton.todolist.domain.Task;
import com.daniloewerton.todolist.domain.dto.TaskDTO;
import com.daniloewerton.todolist.services.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
@Tag(name = "Tasks")
public class TaskController {

    private final TaskService service;

    @PostMapping
    @Operation(summary = "create a new task")
    public ResponseEntity<TaskDTO> create(@RequestBody @Valid final TaskDTO dto) {
        final Task task = service.create(dto);
        final URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(task.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "get a specific task by its id")
    public ResponseEntity<TaskDTO> getTask(@PathVariable final Long id) {
        final Task task = service.getTask(id);
        return ResponseEntity.ok().body(TaskDTO.convert(task));
    }

    @GetMapping("/{userId}/users")
    @Operation(summary = "get all tasks by user")
    public ResponseEntity<List<TaskDTO>> getAllByUser(@PathVariable final Long userId) {
        final List<TaskDTO> list = service.getAllByUser(userId).stream().map(TaskDTO::convert).collect(Collectors.toList());
        return ResponseEntity.ok().body(list);
    }

    @PutMapping("/{id}")
    @Operation(summary = "update a specific task by its id")
    public ResponseEntity<TaskDTO> update(@RequestBody final TaskDTO dto, @PathVariable final Long id) {
        final Task task = service.update(dto, id);
        return ResponseEntity.ok().body(TaskDTO.convert(task));
    }

    @DeleteMapping("/{userId}")
    @Operation(summary = "evict user's task cache")
    public ResponseEntity<Void> evictCaches(@PathVariable final Long userId) {
        service.evictCache(userId);
        return ResponseEntity.noContent().build();
    }
}