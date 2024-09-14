package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ResourceNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    private final List<User> users = new ArrayList<>();
    private final AtomicInteger idGenerator = new AtomicInteger(1); // Генератор ID


    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin()); // Устанавливаем имя равным логину, если оно пустое
        }
        user.setId(idGenerator.getAndIncrement());
        users.add(user);
        log.info("Пользователь создан: {}", user);
        return user;
    }


    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        User existingUser = findUserById(user.getId());
        if (existingUser == null) {
            log.error("Пользователь с id {} не найден", user.getId());
            throw new ResourceNotFoundException("Пользователь с id " + user.getId() + " не найден");
        }
        updateUserInStorage(user);
        log.info("Пользователь обновлен: {}", user);
        return user;
    }


    private void updateUserInStorage(User user) {
        // Обновляем пользователя в хранилище (списке)
        int index = users.indexOf(findUserById(user.getId()));
        if (index != -1) {
            users.set(index, user);
        }
    }

    @GetMapping
    public List<User> getAllUsers() {
        return users; // Возвращаем всех пользователей
    }

    // Вспомогательный метод для поиска пользователя по ID
    private User findUserById(int id) {
        return users.stream()
                .filter(user -> user.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
