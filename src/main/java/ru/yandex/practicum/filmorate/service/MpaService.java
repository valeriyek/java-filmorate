package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ResourceNotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.mpa.MpaStorage;

import java.util.List;
import java.util.Optional;
/**
 * Сервис для работы с рейтингами MPA.
 */
@Service
@RequiredArgsConstructor
public class MpaService {

    private final MpaStorage mpaStorage;
    /**
     * Возвращает список всех рейтингов MPA.
     *
     * @return список MPA
     */
    public List<Mpa> getAllMpa() {
        return mpaStorage.getAllMpa();
    }
    /**
     * Возвращает рейтинг MPA по ID.
     *
     * @param id ID MPA
     * @return найденный рейтинг MPA
     * @throws ResourceNotFoundException если рейтинг не найден
     */
    public Mpa getMpaById(int id) {
        Optional<Mpa> mpa = mpaStorage.getMpaById(id);
        return mpa.orElseThrow(() -> new ResourceNotFoundException("MPA с id " + id + " не найден"));
    }
}
