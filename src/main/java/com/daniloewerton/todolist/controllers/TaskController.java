package com.daniloewerton.todolist.controllers;

import com.daniloewerton.todolist.domain.Task;
import com.daniloewerton.todolist.domain.dto.TaskDTO;
import com.daniloewerton.todolist.services.TaskService;
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
public class TaskController {

    private final TaskService service;

    @PostMapping
    public ResponseEntity<TaskDTO> create(@RequestBody @Valid TaskDTO dto) {
        Task task = service.create(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(task.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getActivity(@PathVariable Long id) {
        Task task = service.getTask(id);
        return ResponseEntity.ok().body(TaskDTO.convert(task));
    }

    @GetMapping
    public ResponseEntity<List<TaskDTO>> getAllActivity() {
        List<TaskDTO> list = service.getAllTasks().stream().map(TaskDTO::convert).collect(Collectors.toList());
        return ResponseEntity.ok().body(list);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> update(@RequestBody TaskDTO dto, @PathVariable Long id) {
        Task task = service.update(dto, id);
        return ResponseEntity.ok().body(TaskDTO.convert(task));
    }
}
