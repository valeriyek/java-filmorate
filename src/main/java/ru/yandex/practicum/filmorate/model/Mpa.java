package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
/**
 * Модель рейтинга MPA (возрастное ограничение фильма).
 * Содержит идентификатор и название рейтинга.
 */
@Data
public class Mpa {
    private int id;
    @NotBlank
    private String name;
}