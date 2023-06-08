package com.daniloewerton.todolist.domain;

import com.daniloewerton.todolist.domain.dto.UserDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "USER_TABLE")
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<Task> tasks;
    @Column(unique = true)
    private String email;
    private String password;

    public User(final Long id, final String name, final String email, final String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public static User converter(final UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setTasks(userDTO.getTasks());
        return user;
    }
}
