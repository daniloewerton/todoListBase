package com.daniloewerton.todolist;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@AutoConfigureWebMvc
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@Import({TestContainersConfiguration.class})
@SpringBootTest(classes = {ToDoListApplication.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = TestContainersConfiguration.TestInitializer.class)
class ToDoListApplicationTests {

    @Test
    void contextLoads() {

    }
}
