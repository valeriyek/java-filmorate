package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ResourceNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.film.FilmLikeDbStorage;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;
import ru.yandex.practicum.filmorate.storage.mpa.MpaStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Сервис для управления фильмами: добавление, обновление, получение,
 * обработка лайков и выбор популярных фильмов.
 */
@Service
public class FilmService {

    private final FilmStorage filmStorage;
    private final UserStorage userStorage;
    private final MpaStorage mpaStorage;
    private final GenreStorage genreStorage;
    private final FilmLikeDbStorage filmLikeDbStorage;

    public FilmService(@Qualifier("filmDbStorage") FilmStorage filmStorage,
                       @Qualifier("userDbStorage") UserStorage userStorage,
                       MpaStorage mpaStorage,
                       GenreStorage genreStorage,
                       FilmLikeDbStorage filmLikeDbStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
        this.mpaStorage = mpaStorage;
        this.genreStorage = genreStorage;
        this.filmLikeDbStorage = filmLikeDbStorage;
    }

    /**
     * Добавляет лайк фильму от указанного пользователя.
     *
     * @param filmId ID фильма
     * @param userId ID пользователя
     * @throws ResourceNotFoundException если фильм или пользователь не найден
     */
    public void addLike(int filmId, int userId) {
        if (filmStorage.getFilmById(filmId).isEmpty() || userStorage.getUserById(userId).isEmpty()) {
            throw new ResourceNotFoundException("Фильм или пользователь не найден");
        }
        filmLikeDbStorage.addLike(filmId, userId);
    }

    /**
     * Удаляет лайк фильма от пользователя.
     *
     * @param filmId ID фильма
     * @param userId ID пользователя
     */
    public void removeLike(int filmId, int userId) {
        filmLikeDbStorage.removeLike(filmId, userId);
    }

    /**
     * Возвращает список самых популярных фильмов по количеству лайков.
     *
     * @param count максимальное количество фильмов
     * @return список популярных фильмов
     */
    public List<Film> getMostPopularFilms(int count) {
        return filmStorage.getMostPopularFilms(count);
    }

    /**
     * Возвращает фильм по его идентификатору.
     *
     * @param id ID фильма
     * @return фильм
     * @throws ResourceNotFoundException если фильм не найден
     */
    public Film getFilmById(int id) {
        return filmStorage.getFilmById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Фильм с id " + id + " не найден"));
    }

    /**
     * Возвращает список всех фильмов.
     *
     * @return список фильмов
     */
    public List<Film> getAllFilms() {
        return filmStorage.getAllFilms();
    }

    /**
     * Добавляет новый фильм с валидацией.
     *
     * @param film фильм для добавления
     * @return добавленный фильм
     * @throws ValidationException если нарушены бизнес-правила
     */
    public Film addFilm(Film film) {
        validateFilm(film);
        validateMpa(film);
        validateFilmGenres(film);
        return filmStorage.addFilm(film);
    }

    /**
     * Обновляет данные фильма с валидацией.
     *
     * @param film фильм для обновления
     * @return обновлённый фильм
     * @throws ResourceNotFoundException если фильм не найден
     * @throws ValidationException       если нарушены бизнес-правила
     */
    public Film updateFilm(Film film) {
        if (filmStorage.getFilmById(film.getId()).isEmpty()) {
            throw new ResourceNotFoundException("Фильм с id " + film.getId() + " не найден");
        }
        validateFilm(film);
        validateMpa(film);
        validateFilmGenres(film);
        return filmStorage.updateFilm(film);
    }

    private void validateFilm(Film film) {
        LocalDate earliestReleaseDate = LocalDate.of(1895, 12, 28);
        if (film.getReleaseDate().isBefore(earliestReleaseDate)) {
            throw new ValidationException("Дата релиза не может быть раньше 28 декабря 1895 года.");
        }
    }

    private void validateFilmGenres(Film film) {
        if (film.getGenres() != null && !film.getGenres().isEmpty()) {
            List<Integer> genreIds = film.getGenres().stream()
                    .map(Genre::getId)
                    .collect(Collectors.toList());
            List<Genre> existingGenres = genreStorage.getGenresByIds(genreIds);

            if (existingGenres.size() != genreIds.size()) {
                throw new ValidationException("Ошибка жанра: один или несколько жанров не найдены.");
            }
        }
    }

    private void validateMpa(Film film) {
        if (film.getMpa() == null || mpaStorage.getMpaById(film.getMpa().getId()).isEmpty()) {
            throw new ValidationException("MPA с id " + film.getMpa().getId() + " не найден");
        }
    }
}
