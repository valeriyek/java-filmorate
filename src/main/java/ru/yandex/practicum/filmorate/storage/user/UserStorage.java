package ru.yandex.practicum.filmorate.storage.user;


import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;

public interface UserStorage {
    User createUser(User user);

    User updateUser(User user);

    Optional<User> getUserById(int id);

    List<User> getAllUsers();

}
