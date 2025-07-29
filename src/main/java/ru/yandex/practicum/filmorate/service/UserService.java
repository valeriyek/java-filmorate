package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ResourceNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.friend.FriendStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;

/**
 * Сервис для управления пользователями и их друзьями.
 */
@Service
@Slf4j
public class UserService {

    private final UserStorage userStorage;
    private final FriendStorage friendStorage;

    public UserService(@Qualifier("userDbStorage") UserStorage userStorage,
                       FriendStorage friendStorage) {
        this.userStorage = userStorage;
        this.friendStorage = friendStorage;
    }

    /**
     * Добавляет друга пользователю.
     *
     * @param userId   ID пользователя
     * @param friendId ID друга
     * @throws ResourceNotFoundException если один из пользователей не найден
     */
    public void addFriend(int userId, int friendId) {
        getUserById(userId);
        getUserById(friendId);

        friendStorage.addFriend(userId, friendId);
        log.info("Пользователь {} добавил в друзья пользователя {}", userId, friendId);
    }
    /**
     * Удаляет друга у пользователя.
     *
     * @param userId   ID пользователя
     * @param friendId ID друга
     * @throws ResourceNotFoundException если один из пользователей не найден
     */
    public void removeFriend(int userId, int friendId) {
        getUserById(userId);
        getUserById(friendId);

        friendStorage.removeFriend(userId, friendId);
        log.info("Пользователь {} удалил из друзей пользователя {}", userId, friendId);
    }

    /**
     * Возвращает список друзей пользователя.
     *
     * @param userId ID пользователя
     * @return список друзей
     * @throws ResourceNotFoundException если пользователь не найден
     */
    public List<User> getFriends(int userId) {
        // Проверка существования пользователя
        getUserById(userId);

        // Получение друзей как списка объектов User
        return friendStorage.getFriends(userId);
    }

    /**
     * Возвращает список общих друзей двух пользователей.
     *
     * @param userId   ID первого пользователя
     * @param otherId  ID второго пользователя
     * @return список общих друзей
     * @throws ResourceNotFoundException если один из пользователей не найден
     */
    public List<User> getCommonFriends(int userId, int otherId) {
        getUserById(userId);
        getUserById(otherId);

        // Получение общего списка друзей сразу через friendStorage
        return friendStorage.getCommonFriends(userId, otherId);
    }

    /**
     * Возвращает пользователя по ID.
     *
     * @param id ID пользователя
     * @return найденный пользователь
     * @throws ResourceNotFoundException если пользователь не найден
     */
    public User getUserById(int id) {
        return userStorage.getUserById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь с id " + id + " не найден"));

    }

    /**
     * Возвращает список всех пользователей.
     *
     * @return список пользователей
     */
    public List<User> getAllUsers() {
        return userStorage.getAllUsers();
    }

    /**
     * Создаёт нового пользователя.
     *
     * @param user пользователь для создания
     * @return созданный пользователь
     */
    public User createUser(User user) {
        return userStorage.createUser(user);
    }

    /**
     * Обновляет данные пользователя.
     *
     * @param user пользователь для обновления
     * @return обновлённый пользователь
     * @throws ResourceNotFoundException если пользователь не найден
     */
    public User updateUser(User user) {
        if (userStorage.getUserById(user.getId()).isEmpty()) {
            throw new ResourceNotFoundException("Пользователь с id " + user.getId() + " не найден");
        }
        return userStorage.updateUser(user);
    }
}
