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

    private static final String ID = "/{id}";

    private final TaskService service;

    @PostMapping
    @Operation(summary = "create a new task")
    public ResponseEntity<TaskDTO> create(@RequestBody @Valid final TaskDTO dto) {
        final Task task = service.create(dto);
        final URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(task.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping(ID)
    @Operation(summary = "get a specific task by its id")
    public ResponseEntity<TaskDTO> getTask(@PathVariable final Long id) {
        final Task task = service.getTask(id);
        return ResponseEntity.ok().body(TaskDTO.convert(task));
    }

    @GetMapping(ID + "/users")
    @Operation(summary = "get all tasks by user")
    public ResponseEntity<List<TaskDTO>> getAllByUser(@PathVariable final Long id) {
        final List<TaskDTO> list = service.getAllByUser(id).stream().map(TaskDTO::convert).collect(Collectors.toList());
        return ResponseEntity.ok().body(list);
    }

    @PutMapping(ID)
    @Operation(summary = "update a specific task by its id")
    public ResponseEntity<TaskDTO> update(@RequestBody final @Valid TaskDTO dto, @PathVariable final Long id) {
        final Task task = service.update(dto, id);
        return ResponseEntity.ok().body(TaskDTO.convert(task));
    }

    @DeleteMapping(ID)
    @Operation(summary = "evict user's task cache")
    public ResponseEntity<Void> evictCache(@PathVariable final Long id) {
        service.evictCache(id);
        return ResponseEntity.noContent().build();
    }
}