package ru.yandex.practicum.filmorate.storage.friend;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
/**
 * Реализация {@link FriendStorage} для работы с друзьями пользователей.
 * Осуществляет добавление, удаление, получение друзей и общих друзей через JdbcTemplate.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class FriendDbStorage implements FriendStorage {

    private final JdbcTemplate jdbcTemplate;
    /**
     * Добавляет друга пользователю.
     *
     * @param userId   ID пользователя
     * @param friendId ID друга
     */
    @Override
    public void addFriend(int userId, int friendId) {
        log.info("Добавление друга: пользователь {} добавляет пользователя {}", userId, friendId);
        String sql = "INSERT INTO friends (user_id, friend_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, userId, friendId);
    }
    /**
     * Удаляет друга у пользователя.
     *
     * @param userId   ID пользователя
     * @param friendId ID друга
     */
    @Override
    public void removeFriend(int userId, int friendId) {
        log.info("Удаление друга: пользователь {} удаляет пользователя {}", userId, friendId);
        String sql = "DELETE FROM friends WHERE user_id = ? AND friend_id = ?";
        jdbcTemplate.update(sql, userId, friendId);
    }
    /**
     * Возвращает список друзей пользователя.
     *
     * @param userId ID пользователя
     * @return список друзей
     */
    @Override
    public List<User> getFriends(int userId) {
        log.info("Получение списка друзей пользователя {}", userId);
        String sql = "SELECT u.* FROM users u JOIN friends f ON u.user_id = f.friend_id WHERE f.user_id = ?";
        return jdbcTemplate.query(sql, this::mapRowToUser, userId);
    }

    /**
     * Возвращает список общих друзей двух пользователей.
     *
     * @param userId   ID первого пользователя
     * @param otherId  ID второго пользователя
     * @return список общих друзей
     */
    @Override
    public List<User> getCommonFriends(int userId, int otherId) {
        log.info("Получение общих друзей пользователей {} и {}", userId, otherId);
        String sql = "SELECT u.* FROM users u " +
                "JOIN friends f1 ON u.user_id = f1.friend_id " +
                "JOIN friends f2 ON u.user_id = f2.friend_id " +
                "WHERE f1.user_id = ? AND f2.user_id = ?";
        return jdbcTemplate.query(sql, this::mapRowToUser, userId, otherId);
    }

    private User mapRowToUser(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("user_id"));
        user.setEmail(rs.getString("email"));
        user.setLogin(rs.getString("login"));
        user.setName(rs.getString("user_name"));
        user.setBirthday(rs.getDate("birthday") != null ? rs.getDate("birthday").toLocalDate() : null);
        return user;
    }
}
