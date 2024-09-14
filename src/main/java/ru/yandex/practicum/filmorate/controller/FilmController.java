package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
        film.setId(idGenerator.getAndIncrement()); // Назначаем уникальный ID

        films.add(film);
        log.info("Фильм добавлен: {}", film);
        return film;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Film> updateFilm(@PathVariable int id, @Valid @RequestBody Film film) {
        // Ищем фильм по ID
        Film existingFilm = findFilmById(id);
        if (existingFilm == null) {
            log.error("Фильм с id {} не найден.", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Возвращаем 404
        }
        // Проверяем дату релиза
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.error("Ошибка валидации фильма: дата релиза раньше 28 декабря 1895 года.");
            throw new ValidationException("Дата релиза не может быть раньше 28 декабря 1895 года.");
        }
        // Обновляем фильм
        existingFilm.setName(film.getName());
        existingFilm.setDescription(film.getDescription());
        existingFilm.setReleaseDate(film.getReleaseDate());
        existingFilm.setDuration(film.getDuration());

        log.info("Фильм с id {} обновлён: {}", id, existingFilm);
        return ResponseEntity.ok(existingFilm);
    }

    // Метод поиска фильма по ID
    private Film findFilmById(int id) {
        return films.stream()
                .filter(film -> film.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @GetMapping
    public List<Film> getAllFilms() {
        log.info("Запрошен список всех фильмов.");
        return films;
    }
}
