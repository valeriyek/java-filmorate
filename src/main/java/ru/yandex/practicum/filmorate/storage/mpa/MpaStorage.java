package ru.yandex.practicum.filmorate.storage.mpa;

import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;
import java.util.Optional;
/**
 * Интерфейс хранилища для работы с рейтингами MPA.
 */
public interface MpaStorage {
    /**
     * Возвращает список всех рейтингов MPA.
     *
     * @return список MPA
     */
    List<Mpa> getAllMpa();
    /**
     * Возвращает рейтинг MPA по его ID.
     *
     * @param id ID рейтинга
     * @return Optional с MPA, если найден
     */
    Optional<Mpa> getMpaById(int id);
}
