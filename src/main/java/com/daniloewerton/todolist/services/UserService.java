package com.daniloewerton.todolist.services;

import com.daniloewerton.todolist.domain.User;
import com.daniloewerton.todolist.domain.dto.UserDTO;
import com.daniloewerton.todolist.repositories.UserRepository;
import com.daniloewerton.todolist.services.exceptions.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public User create(final UserDTO userDTO) {
        return repository.save(User.converter(userDTO));
    }

    public User getUser(final Long id) {
        return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Object Not Found."));
    }

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public User update(final UserDTO dto, Long id) {

        User user = repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Object Not Found."));
        user.setId(id);
        user.setName(dto.getName());
        user.setTasks(dto.getTasks());
        return user;
    }
}