package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
/**
 * Модель пользователя.
 * Содержит информацию о пользователе,
 * включая email, логин, имя, дату рождения
 * и список друзей.
 */
@Data
@NoArgsConstructor
public class User {
    private int id;

    @Email(message = "Неправильный формат электронной почты.")
    @NotBlank(message = "Email не может быть пустым.")
    private String email;

    @NotBlank(message = "Логин не может быть пустым.")
    @Pattern(regexp = "\\S+", message = "Логин не должен содержать пробелы.")
    private String login;

    private String name;

    @NotNull(message = "Дата рождения не может быть пустой.")
    @Past(message = "Дата рождения не может быть в будущем.")
    private LocalDate birthday;
    // Идентификаторы пользователей-друзей.
    private Set<Integer> friends = new HashSet<>();
    /**
     * Устанавливает имя пользователя. Если имя пустое, устанавливается логин.
     *
     * @param name имя пользователя
     */
    public void setName(String name) {
        if (name == null || name.isBlank()) {
            this.name = this.login;
        } else {
            this.name = name;
        }
    }
}
