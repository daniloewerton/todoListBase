package com.daniloewerton.todolist.controllers;

import com.daniloewerton.todolist.domain.Task;
import com.daniloewerton.todolist.domain.dto.TaskDTO;
import com.daniloewerton.todolist.services.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/activity")
@RequiredArgsConstructor
@Tag(name = "Tasks")
public class TaskController {

    private final TaskService service;

    @PostMapping
    @Operation(summary = "create a new task")
    public ResponseEntity<TaskDTO> create(@RequestBody @Valid final TaskDTO dto) {
        Task task = service.create(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(task.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "get a specific task by its id")
    public ResponseEntity<TaskDTO> getTask(@PathVariable final Long id) {
        Task task = service.getTask(id);
        return ResponseEntity.ok().body(TaskDTO.convert(task));
    }

    @GetMapping
    @Operation(summary = "get all tasks")
    public ResponseEntity<List<TaskDTO>> getAllTasks() {
        List<TaskDTO> list = service.getAllTasks().stream().map(TaskDTO::convert).collect(Collectors.toList());
        return ResponseEntity.ok().body(list);
    }

    @PutMapping("/{id}")
    @Operation(summary = "update a specific task by its id")
    public ResponseEntity<TaskDTO> update(@RequestBody final TaskDTO dto, @PathVariable final Long id) {
        Task task = service.update(dto, id);
        return ResponseEntity.ok().body(TaskDTO.convert(task));
    }
}