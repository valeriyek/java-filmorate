package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.List;

/**
 * Контроллер для управления фильмами.
 * Поддерживает операции добавления, обновления, получения, лайков и получения популярных фильмов.
 */
@RestController
@RequestMapping("/films")
@Slf4j
@RequiredArgsConstructor
public class FilmController {
    private final FilmService filmService;

    /**
     * Добавляет новый фильм.
     *
     * @param film объект фильма
     * @return добавленный фильм
     */
    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) {
        Film createdFilm = filmService.addFilm(film);
        log.info("Фильм добавлен: {}", createdFilm);
        return createdFilm;
    }

    /**
     * Обновляет существующий фильм.
     *
     * @param film объект фильма
     * @return обновлённый фильм
     */
    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        Film updatedFilm = filmService.updateFilm(film);
        log.info("Фильм обновлен: {}", updatedFilm);
        return updatedFilm;
    }

    /**
     * Возвращает фильм по ID.
     *
     * @param id ID фильма
     * @return найденный фильм
     */
    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable int id) {
        log.info("Получение фильма с id {}", id);
        return filmService.getFilmById(id);
    }

    /**
     * Возвращает список всех фильмов.
     *
     * @return список фильмов
     */
    @GetMapping
    public List<Film> getAllFilms() {
        log.info("Получение списка всех фильмов");
        return filmService.getAllFilms();
    }

    /**
     * Добавляет лайк фильму от пользователя.
     *
     * @param id     ID фильма
     * @param userId ID пользователя
     */
    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable int id, @PathVariable int userId) {
        filmService.addLike(id, userId);
        log.info("Пользователь {} поставил лайк фильму {}", userId, id);
    }

    /**
     * Удаляет лайк у фильма от пользователя.
     *
     * @param id     ID фильма
     * @param userId ID пользователя
     */
    @DeleteMapping("/{id}/like/{userId}")
    public void removeLike(@PathVariable int id, @PathVariable int userId) {
        filmService.removeLike(id, userId);
        log.info("Пользователь {} удалил лайк у фильма {}", userId, id);
    }

    /**
     * Возвращает список самых популярных фильмов по количеству лайков.
     *
     * @param count максимальное количество фильмов (по умолчанию 10)
     * @return список популярных фильмов
     */
    @GetMapping("/popular")
    public List<Film> getMostPopularFilms(@RequestParam(defaultValue = "10") int count) {
        log.info("Получение топ-{} популярных фильмов", count);
        return filmService.getMostPopularFilms(count);
    }
}
