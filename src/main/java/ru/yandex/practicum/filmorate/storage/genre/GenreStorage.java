package ru.yandex.practicum.filmorate.storage.genre;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.Optional;
/**
 * Интерфейс хранилища жанров фильмов.
 * Определяет операции получения жанров по ID и списку ID.
 */
public interface GenreStorage {
    /**
     * Возвращает список всех жанров.
     *
     * @return список жанров
     */
    List<Genre> getAllGenres();
    /**
     * Возвращает жанр по ID.
     *
     * @param id ID жанра
     * @return Optional с жанром, если найден
     */
    Optional<Genre> getGenreById(int id);
    /**
     * Возвращает список жанров по списку ID.
     *
     * @param genreIds список ID жанров
     * @return список найденных жанров
     */
    List<Genre> getGenresByIds(List<Integer> genreIds);
}
