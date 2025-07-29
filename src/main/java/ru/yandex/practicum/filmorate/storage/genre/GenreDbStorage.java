package ru.yandex.practicum.filmorate.storage.genre;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Реализация {@link GenreStorage}, использующая JDBC для работы с таблицей жанров.
 * Поддерживает получение всех жанров, жанра по ID и списка жанров по нескольким ID.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class GenreDbStorage implements GenreStorage {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * Возвращает список всех жанров.
     *
     * @return список жанров
     */
    @Override
    public List<Genre> getAllGenres() {
        log.info("Получение всех жанров");
        String sql = "SELECT * FROM genres";
        List<Genre> genres = jdbcTemplate.query(sql, this::mapRowToGenre);
        log.info("Найдено жанров: {}", genres.size());
        return genres;
    }

    /**
     * Возвращает жанр по его ID.
     *
     * @param id ID жанра
     * @return Optional с жанром, если найден
     */
    @Override
    public Optional<Genre> getGenreById(int id) {
        log.info("Получение жанра с id: {}", id);
        String sql = "SELECT * FROM genres WHERE genre_id = ?";
        List<Genre> genres = jdbcTemplate.query(sql, this::mapRowToGenre, id);
        if (genres.isEmpty()) {
            log.warn("Жанр с id {} не найден", id);
            return Optional.empty();
        }
        Genre genre = genres.get(0);
        log.info("Жанр найден: {}", genre);
        return Optional.of(genre);
    }


    /**
     * Возвращает список жанров по списку ID.
     *
     * @param genreIds список ID жанров
     * @return список найденных жанров
     */
    @Override
    public List<Genre> getGenresByIds(List<Integer> genreIds) {
        String sql = "SELECT * FROM genres WHERE genre_id IN (:ids)";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("ids", genreIds);
        return namedParameterJdbcTemplate.query(sql, parameters, this::mapRowToGenre);
    }

    private Genre mapRowToGenre(ResultSet rs, int rowNum) throws SQLException {
        Genre genre = new Genre();
        genre.setId(rs.getInt("genre_id"));
        genre.setName(rs.getString("genre_name"));
        return genre;
    }
}
