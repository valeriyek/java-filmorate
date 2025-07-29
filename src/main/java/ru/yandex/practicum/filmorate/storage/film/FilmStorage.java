package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Optional;
/**
 * Интерфейс хранилища фильмов.
 * Определяет базовые операции добавления, обновления и получения фильмов.
 */
public interface FilmStorage {
    /**
     * Добавляет фильм.
     *
     * @param film фильм для добавления
     * @return добавленный фильм с ID
     */
    Film addFilm(Film film);
    /**
     * Обновляет фильм.
     *
     * @param film фильм с обновлёнными данными
     * @return обновлённый фильм
     */
    Film updateFilm(Film film);
    /**
     * Возвращает фильм по ID.
     *
     * @param id ID фильма
     * @return Optional с фильмом, если найден
     */
    Optional<Film> getFilmById(int id);
    /**
     * Возвращает список всех фильмов.
     *
     * @return список фильмов
     */
    List<Film> getAllFilms();
    /**
     * Возвращает список самых популярных фильмов.
     *
     * @param count максимальное количество фильмов
     * @return отсортированный список популярных фильмов
     */
    List<Film> getMostPopularFilms(int count);
}
