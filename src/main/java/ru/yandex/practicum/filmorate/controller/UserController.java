package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.List;

/**
 * Контроллер для управления пользователями и их дружескими связями.
 */
@RestController
@RequestMapping("/users")
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    /**
     * Создаёт нового пользователя.
     *
     * @param user пользователь для создания
     * @return созданный пользователь
     */
    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        User createdUser = userService.createUser(user);
        log.info("Пользователь создан: {}", createdUser);
        return createdUser;
    }
    /**
     * Обновляет существующего пользователя.
     *
     * @param user пользователь с обновлёнными данными
     * @return обновлённый пользователь
     */
    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        User updatedUser = userService.updateUser(user);
        log.info("Пользователь обновлен: {}", updatedUser);
        return updatedUser;
    }
    /**
     * Возвращает пользователя по ID.
     *
     * @param id ID пользователя
     * @return найденный пользователь
     */
    @GetMapping("/{id}")
    public User getUserById(@PathVariable int id) {
        log.info("Получение пользователя с id {}", id);
        return userService.getUserById(id);
    }
    /**
     * Возвращает список всех пользователей.
     *
     * @return список пользователей
     */
    @GetMapping
    public List<User> getAllUsers() {
        log.info("Получение списка всех пользователей");
        return userService.getAllUsers();
    }
    /**
     * Добавляет пользователя в друзья другому пользователю.
     *
     * @param id       ID пользователя
     * @param friendId ID добавляемого друга
     */
    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable int id, @PathVariable int friendId) {
        userService.addFriend(id, friendId);
        log.info("Пользователь {} добавил пользователя {} в друзья", id, friendId);
    }
    /**
     * Удаляет пользователя из списка друзей.
     *
     * @param id       ID пользователя
     * @param friendId ID удаляемого друга
     */
    @DeleteMapping("/{id}/friends/{friendId}")
    public void removeFriend(@PathVariable int id, @PathVariable int friendId) {
        userService.removeFriend(id, friendId);
        log.info("Пользователь {} удалил пользователя {} из друзей", id, friendId);
    }
    /**
     * Возвращает список друзей пользователя.
     *
     * @param id ID пользователя
     * @return список друзей
     */
    @GetMapping("/{id}/friends")
    public List<User> getFriends(@PathVariable int id) {
        log.info("Получение друзей пользователя {}", id);
        return userService.getFriends(id);
    }
    /**
     * Возвращает список общих друзей двух пользователей.
     *
     * @param id       ID первого пользователя
     * @param otherId  ID второго пользователя
     * @return список общих друзей
     */
    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getCommonFriends(@PathVariable int id, @PathVariable int otherId) {
        log.info("Получение общих друзей пользователей {} и {}", id, otherId);
        return userService.getCommonFriends(id, otherId);
    }
}
