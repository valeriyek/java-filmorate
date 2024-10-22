package ru.yandex.practicum.filmorate.storage.film;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.time.LocalDate;
import java.util.List;
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


    @Test
    public void testGetAllFilms() {
        Mpa mpa = new Mpa();
        mpa.setId(1);
        mpa.setName("G");

        Film film1 = new Film();
        film1.setName("Film 1");
        film1.setDescription("Description 1");
        film1.setReleaseDate(LocalDate.of(2000, 1, 1));
        film1.setDuration(120);
        film1.setMpa(mpa);

        Film film2 = new Film();
        film2.setName("Film 2");
        film2.setDescription("Description 2");
        film2.setReleaseDate(LocalDate.of(2001, 1, 1));
        film2.setDuration(100);
        film2.setMpa(mpa);

        filmDbStorage.addFilm(film1);
        filmDbStorage.addFilm(film2);

        List<Film> films = filmDbStorage.getAllFilms();
        assertThat(films).hasSize(2);
        assertThat(films).extracting(Film::getName).containsExactlyInAnyOrder("Film 1", "Film 2");
    }

}
