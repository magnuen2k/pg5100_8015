package no.kristiania.service;

import no.kristiania.StubApplication;
import no.kristiania.entity.Movie;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = StubApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class MovieServiceTest extends ServiceTestBase {

    @Autowired
    private MovieService movieService;

    @Autowired
    private PersonService personService;

    @Autowired
    private GenreService genreService;

    @Test
    public void testCreateMovie() {
        Long directorId = personService.createPerson("test name");
        Long genreId = genreService.createGenre("comedy");
        Long id = movieService.createMovie("Test Title", directorId, 2004, genreId);
        assertNotNull(id);
    }

    @Test
    public void testDeleteMovie() {
        Long directorId = personService.createPerson("director");
        Long genreId = genreService.createGenre("comedy");
        Long id = movieService.createMovie("Test Title", directorId, 2004, genreId);
        boolean deleted = movieService.deleteMovie(id);
        assertTrue(deleted);
    }
}
