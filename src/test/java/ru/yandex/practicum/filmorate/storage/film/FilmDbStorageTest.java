package ru.yandex.practicum.filmorate.storage.film;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
public class FilmDbStorageTest {

    @Autowired
    @Qualifier("filmDbStorage")
    private FilmDbStorage filmDbStorage;


    @Test
    public void testCreateAndFindFilm() {
        Mpa mpa = new Mpa();
        mpa.setId(1);
        mpa.setName("G");

        Film film = new Film();
        film.setName("Test Film");
        film.setDescription("Test Description");
        film.setReleaseDate(LocalDate.of(2000, 1, 1));
        film.setDuration(120);
        film.setMpa(mpa);

        Genre genre = new Genre();
        genre.setId(1);
        genre.setName("Комедия");

        film.setGenres(Set.of(genre));

        filmDbStorage.addFilm(film);

        Optional<Film> retrievedFilm = filmDbStorage.getFilmById(film.getId());
        assertThat(retrievedFilm)
                .isPresent()
                .hasValueSatisfying(f ->
                        assertThat(f).hasFieldOrPropertyWithValue("name", "Test Film")
                );
    }
}
