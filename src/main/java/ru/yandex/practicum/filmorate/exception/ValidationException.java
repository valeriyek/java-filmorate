package ru.yandex.practicum.filmorate.exception;

/**
 * Исключение, выбрасываемое при нарушении правил валидации данных.
 * Используется для обработки ошибок пользовательского ввода и бизнес-правил.
 */
public class ValidationException extends RuntimeException {
    /**
     * Создаёт исключение с сообщением об ошибке.
     *
     * @param message описание ошибки
     */
    public ValidationException(String message) {
        super(message);
    }
}
