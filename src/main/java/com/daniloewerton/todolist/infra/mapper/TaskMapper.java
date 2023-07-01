package com.daniloewerton.todolist.infra.mapper;

import com.daniloewerton.todolist.domain.Task;
import com.daniloewerton.todolist.domain.dto.TaskDTO;
import com.daniloewerton.todolist.infra.mapper.converter.UserUtils;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", imports = UserUtils.class)
public interface TaskMapper {

    Task toTask(final TaskDTO dto);
    TaskDTO toTaskDTO(final Task task);
}
