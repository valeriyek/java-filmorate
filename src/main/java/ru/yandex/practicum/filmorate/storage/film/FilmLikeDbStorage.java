package ru.yandex.practicum.filmorate.storage.film;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
/**
 * Хранилище для управления лайками фильмов.
 * Работает с таблицей film_likes через JdbcTemplate.
 */
@Component
@RequiredArgsConstructor
public class FilmLikeDbStorage {

    private final JdbcTemplate jdbcTemplate;
    /**
     * Добавляет лайк фильму от пользователя.
     *
     * @param filmId ID фильма
     * @param userId ID пользователя
     */
    public void addLike(int filmId, int userId) {
        String sql = "INSERT INTO film_likes (film_id, user_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, filmId, userId);
    }
    /**
     * Удаляет лайк пользователя у фильма.
     *
     * @param filmId ID фильма
     * @param userId ID пользователя
     */
    public void removeLike(int filmId, int userId) {
        String sql = "DELETE FROM film_likes WHERE film_id = ? AND user_id = ?";
        jdbcTemplate.update(sql, filmId, userId);
    }
    /**
     * Возвращает общее количество лайков у фильма.
     *
     * @param filmId ID фильма
     * @return количество лайков
     */
    public int getFilmLikes(int filmId) {
        String sql = "SELECT COUNT(user_id) FROM film_likes WHERE film_id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, filmId);
    }
}
