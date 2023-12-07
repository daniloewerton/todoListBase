package com.daniloewerton.todolist.domain.dto;

import com.daniloewerton.todolist.domain.Status;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDate;

public record TaskDTO (Long id,
                       @NotNull(message = "Mandatory field") String description,
                       @NotNull(message = "Mandatory field") LocalDate deadline,
                       @NotNull(message = "Mandatory field") Status status) implements Serializable { }
