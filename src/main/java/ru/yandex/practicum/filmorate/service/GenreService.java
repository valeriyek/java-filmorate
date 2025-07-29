package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ResourceNotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;

import java.util.List;
import java.util.Optional;
/**
 * Сервис для работы с жанрами фильмов.
 */
@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreStorage genreStorage;
    /**
     * Возвращает список всех жанров.
     *
     * @return список жанров
     */
    public List<Genre> getAllGenres() {
        return genreStorage.getAllGenres();
    }
    /**
     * Возвращает жанр по ID.
     *
     * @param id ID жанра
     * @return найденный жанр
     * @throws ResourceNotFoundException если жанр не найден
     */
    public Genre getGenreById(int id) {
        Optional<Genre> genre = genreStorage.getGenreById(id);
        return genre.orElseThrow(() -> new ResourceNotFoundException("Жанр с id " + id + " не найден"));
    }
}
