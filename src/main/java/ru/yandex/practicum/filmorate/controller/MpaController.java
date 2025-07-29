package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.MpaService;

import java.util.List;
/**
 * Контроллер для получения рейтингов MPA (возрастных ограничений).
 */
@RestController
@RequestMapping("/mpa")
@RequiredArgsConstructor
public class MpaController {

    private final MpaService mpaService;
    /**
     * Возвращает список всех рейтингов MPA.
     *
     * @return список рейтингов
     */
    @GetMapping
    public List<Mpa> getAllMpa() {
        return mpaService.getAllMpa();
    }
    /**
     * Возвращает рейтинг MPA по ID.
     *
     * @param id ID рейтинга
     * @return найденный рейтинг MPA
     */
    @GetMapping("/{id}")
    public Mpa getMpaById(@PathVariable int id) {
        return mpaService.getMpaById(id);
    }
}
