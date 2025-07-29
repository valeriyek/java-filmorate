package ru.yandex.practicum.filmorate.storage.user;


import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс хранилища пользователей.
 * Определяет базовые операции для работы с сущностью {@link User}.
 */
public interface UserStorage {
    /**
     * Создаёт нового пользователя.
     *
     * @param user пользователь для создания
     * @return созданный пользователь с присвоенным ID
     */
    User createUser(User user);

    /**
     * Обновляет существующего пользователя.
     *
     * @param user пользователь с обновлёнными данными
     * @return обновлённый пользователь
     */

    User updateUser(User user);

    /**
     * Возвращает пользователя по ID.
     *
     * @param id ID пользователя
     * @return Optional с пользователем, если найден
     */
    Optional<User> getUserById(int id);

    /**
     * Возвращает список всех пользователей.
     *
     * @return список пользователей
     */
    List<User> getAllUsers();

}
