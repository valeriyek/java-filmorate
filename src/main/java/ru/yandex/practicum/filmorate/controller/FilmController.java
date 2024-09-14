package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ResourceNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {

    private final List<Film> films = new ArrayList<>();
    private final AtomicInteger idGenerator = new AtomicInteger(1); // Генератор ID


    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.error("Ошибка валидации фильма: дата релиза раньше 28 декабря 1895 года.");
            throw new ValidationException("Дата релиза не может быть раньше 28 декабря 1895 года.");
        }
        film.setId(idGenerator.getAndIncrement());
        films.add(film);
        log.info("Фильм добавлен: {}", film);
        return film;
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        Film existingFilm = findFilmById(film.getId());
        if (existingFilm == null) {
            log.error("Фильм с id {} не найден.", film.getId());
            throw new ResourceNotFoundException("Фильм с id " + film.getId() + " не найден");
        }
        updateFilmInStorage(film);
        log.info("Фильм обновлён: {}", film);
        return film;
    }

    // Метод поиска фильма по ID
    private Film findFilmById(int id) {
        return films.stream()
                .filter(film -> film.getId() == id)
                .findFirst()
                .orElse(null);
    }

    private void updateFilmInStorage(Film film) {
        // Обновляем фильм в хранилище (списке)
        int index = films.indexOf(findFilmById(film.getId()));
        if (index != -1) {
            films.set(index, film);
        }
    }

    @GetMapping
    public List<Film> getAllFilms() {
        log.info("Запрошен список всех фильмов.");
        return films;
    }
}
