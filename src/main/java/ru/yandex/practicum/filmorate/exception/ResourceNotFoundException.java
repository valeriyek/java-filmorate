package ru.yandex.practicum.filmorate.exception;

/**
 * Исключение, выбрасываемое при попытке получить несуществующий ресурс.
 * Используется для обработки случаев, когда сущность не найдена в хранилище.
 */
public class ResourceNotFoundException extends RuntimeException {
    /**
     * Создаёт исключение с сообщением об ошибке.
     *
     * @param message описание ошибки
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
