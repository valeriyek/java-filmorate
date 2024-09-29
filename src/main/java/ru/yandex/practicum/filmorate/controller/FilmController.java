package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.List;

@RestController
@RequestMapping("/films")
@Slf4j
@RequiredArgsConstructor
public class FilmController {
    private final FilmService filmService;

    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) {
        Film createdFilm = filmService.addFilm(film);
        log.info("Фильм добавлен: {}", createdFilm);
        return createdFilm;
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        Film updatedFilm = filmService.updateFilm(film);
        log.info("Фильм обновлен: {}", updatedFilm);
        return updatedFilm;
    }

    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable int id) {
        log.info("Получение фильма с id {}", id);
        return filmService.getFilmById(id);
    }

    @GetMapping
    public List<Film> getAllFilms() {
        log.info("Получение списка всех фильмов");
        return filmService.getAllFilms();
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable int id, @PathVariable int userId) {
        filmService.addLike(id, userId);
        log.info("Пользователь {} поставил лайк фильму {}", userId, id);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void removeLike(@PathVariable int id, @PathVariable int userId) {
        filmService.removeLike(id, userId);
        log.info("Пользователь {} удалил лайк у фильма {}", userId, id);
    }

    @GetMapping("/popular")
    public List<Film> getMostPopularFilms(@RequestParam(defaultValue = "10") int count) {
        log.info("Получение топ-{} популярных фильмов", count);
        return filmService.getMostPopularFilms(count);
    }
}
