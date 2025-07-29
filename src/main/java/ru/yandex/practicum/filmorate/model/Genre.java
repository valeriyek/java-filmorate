package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
/**
 * Модель жанра фильма.
 * Содержит идентификатор и название жанра.
 */
@Data
public class Genre {
    private int id;
    @NotBlank
    private String name;
}