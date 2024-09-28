package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ResourceNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    // Добавление в друзья
    public void addFriend(int userId, int friendId) {
        User user = getUserById(userId);
        User friend = getUserById(friendId);
        user.getFriends().add(friendId);
        friend.getFriends().add(userId);
    }

    // Удаление из друзей
    public void removeFriend(int userId, int friendId) {
        User user = getUserById(userId);
        User friend = getUserById(friendId);
        user.getFriends().remove(friendId);
        friend.getFriends().remove(userId);
    }

    // Получение списка друзей
    public List<User> getFriends(int userId) {
        User user = getUserById(userId);
        return user.getFriends().stream()
                .map(userStorage::getUserById)
                .collect(Collectors.toList());
    }

    // Получение списка общих друзей
    public List<User> getCommonFriends(int userId, int otherId) {
        User user = getUserById(userId);
        User otherUser = getUserById(otherId);
        Set<Integer> commonIds = new HashSet<>(user.getFriends());
        commonIds.retainAll(otherUser.getFriends());
        return commonIds.stream()
                .map(userStorage::getUserById)
                .collect(Collectors.toList());
    }

    // Получение пользователя по ID
    public User getUserById(int id) {
        User user = userStorage.getUserById(id);
        if (user == null) {
            throw new ResourceNotFoundException("Пользователь с id " + id + " не найден");
        }
        return user;
    }

    // Получение всех пользователей
    public List<User> getAllUsers() {
        return userStorage.getAllUsers();
    }

    // Создание пользователя
    public User createUser(User user) {
        return userStorage.createUser(user);
    }

    // Обновление пользователя
    public User updateUser(User user) {
        if (userStorage.getUserById(user.getId()) == null) {
            throw new ResourceNotFoundException("Пользователь с id " + user.getId() + " не найден");
        }
        return userStorage.updateUser(user);
    }
}
