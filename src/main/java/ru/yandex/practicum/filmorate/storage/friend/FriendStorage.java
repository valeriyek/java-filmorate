package ru.yandex.practicum.filmorate.storage.friend;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
/**
 * Интерфейс хранилища для управления дружескими связями между пользователями.
 */
public interface FriendStorage {
    /**
     * Добавляет пользователя в друзья.
     *
     * @param userId   ID пользователя
     * @param friendId ID добавляемого друга
     */
    void addFriend(int userId, int friendId);
    /**
     * Удаляет пользователя из списка друзей.
     *
     * @param userId   ID пользователя
     * @param friendId ID друга для удаления
     */
    void removeFriend(int userId, int friendId);
    /**
     * Возвращает список друзей пользователя.
     *
     * @param userId ID пользователя
     * @return список пользователей-друзей
     */
    List<User> getFriends(int userId);
    /**
     * Возвращает список общих друзей двух пользователей.
     *
     * @param userId  ID первого пользователя
     * @param otherId ID второго пользователя
     * @return список общих друзей
     */
    List<User> getCommonFriends(int userId, int otherId);
}
