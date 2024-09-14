package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


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
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        // Если имя пользователя пустое, заменяем его на логин
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        // Назначаем уникальный ID
        user.setId(idGenerator.getAndIncrement());
        // Добавляем пользователя в коллекцию
        users.add(user);
        log.info("Пользователь создан: {}", user);
        return new ResponseEntity<>(user, HttpStatus.CREATED); // Возвращаем статус 201
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable int id, @Valid @RequestBody User user) {
        // Ищем пользователя по ID
        User existingUser = findUserById(id);
        if (existingUser == null) {
            log.error("Пользователь с id {} не найден", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Возвращаем статус 404
        }

        // Если имя пользователя пустое, заменяем его на логин
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }

        // Логика обновления данных пользователя
        existingUser.setLogin(user.getLogin());
        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());

        log.info("Пользователь с id {} обновлён: {}", id, existingUser);
        return new ResponseEntity<>(existingUser, HttpStatus.OK); // Возвращаем обновленного пользователя
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        log.info("Запрошен список всех пользователей.");
        return new ResponseEntity<>(users, HttpStatus.OK); // Возвращаем статус 200
    }

    // Вспомогательный метод для поиска пользователя по ID
    private User findUserById(int id) {
        return users.stream()
                .filter(user -> user.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
