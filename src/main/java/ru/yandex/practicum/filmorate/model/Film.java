package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
/**
 * Модель фильма.
 * Содержит информацию о названии, описании, дате релиза, продолжительности,
 * рейтинге MPA, жанрах и лайках от пользователей.
 */
@Data
public class Film {
    private int id;

    @NotBlank(message = "Название не может быть пустым.")
    private String name;

    @Size(max = 200, message = "Описание не может превышать 200 символов.")
    private String description;

    @NotNull(message = "Дата релиза не может быть пустой.")
    @PastOrPresent(message = "Дата релиза не может быть в будущем.")
    private LocalDate releaseDate;

    @Positive(message = "Продолжительность должна быть положительным числом.")
    private int duration;
    // Идентификаторы пользователей, поставивших лайк.
    private Set<Integer> likes = new HashSet<>();

    private Mpa mpa;


    private Set<Genre> genres = new HashSet<>();


}
