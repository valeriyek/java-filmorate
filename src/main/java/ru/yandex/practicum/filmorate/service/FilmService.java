package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ResourceNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {
    private final FilmStorage filmStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    // Добавление лайка фильму
    public void addLike(int filmId, int userId) {
        Film film = getFilmById(filmId);
        film.getLikes().add(userId);
    }

    // Удаление лайка у фильма
    public void removeLike(int filmId, int userId) {
        Film film = getFilmById(filmId);
        film.getLikes().remove(userId);
    }

    // Получение самых популярных фильмов
    public List<Film> getMostPopularFilms(int count) {
        return filmStorage.getAllFilms().stream()
                .sorted((f1, f2) -> Integer.compare(f2.getLikes().size(), f1.getLikes().size()))
                .limit(count)
                .collect(Collectors.toList());
    }

    // Получение фильма по ID
    public Film getFilmById(int id) {
        Film film = filmStorage.getFilmById(id);
        if (film == null) {
            throw new ResourceNotFoundException("Фильм с id " + id + " не найден");
        }
        return film;
    }

    // Получение всех фильмов
    public List<Film> getAllFilms() {
        return filmStorage.getAllFilms();
    }

    // Добавление фильма
    public Film addFilm(Film film) {
        validateFilm(film);
        return filmStorage.addFilm(film);
    }

    // Обновление фильма
    public Film updateFilm(Film film) {
        if (filmStorage.getFilmById(film.getId()) == null) {
            throw new ResourceNotFoundException("Фильм с id " + film.getId() + " не найден");
        }
        validateFilm(film);
        return filmStorage.updateFilm(film);
    }

    // Валидация фильма
    private void validateFilm(Film film) {
        LocalDate earliestReleaseDate = LocalDate.of(1895, 12, 28);
        if (film.getReleaseDate().isBefore(earliestReleaseDate)) {
            throw new ValidationException("Дата релиза не может быть раньше 28 декабря 1895 года.");
        }
    }
}
