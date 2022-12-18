package com.daniloewerton.todolist.services;

import com.daniloewerton.todolist.domain.Task;
import com.daniloewerton.todolist.domain.Status;
import com.daniloewerton.todolist.domain.User;
import com.daniloewerton.todolist.repositories.TaskRepository;
import com.daniloewerton.todolist.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class DatabaseService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    public void insertDB() {

        User u1 = new User(null, "Danilo Ewerton", "ewerton@email.com", "123");
        User u2 = new User(null, "Mariazinha", "mariazinha@email.com", "321");

        Task a1 = new Task(null, "Ride bike", LocalDate.parse("2022-10-25"), Status.TODO, u1);
        Task a2 = new Task(null, "Go to the gym", LocalDate.parse("2022-10-28"), Status.TODO, u1);
        Task a3 = new Task(null, "Read a book", LocalDate.parse("2022-10-20"), Status.TODO, u1);
        Task a4 = new Task(null, "Go to parent's house", LocalDate.parse("2022-11-05"), Status.TODO, u2);
        Task a5 = new Task(null, "Study at least 1 hour", LocalDate.parse("2022-11-01"), Status.TODO, u2);
        Task a6 = new Task(null, "Walk the dog", LocalDate.parse("2022-11-02"), Status.TODO, u2);

        userRepository.saveAll(Arrays.asList(u1, u2));
        taskRepository.saveAll(Arrays.asList(a1, a2, a3, a4, a5, a6));
    }
}
