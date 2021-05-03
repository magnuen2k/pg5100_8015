package no.kristiania.backend.service;

import no.kristiania.backend.StubApplication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

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

    private int count = 0;
    private String uniqueName = "test_" + count++;

    @Test
    public void testCreateMovie() {
        Long directorId = personService.createPerson(uniqueName);
        Long actorId = personService.createPerson(uniqueName);
        Long genreId = genreService.createGenre(uniqueName);

        Long movieId = movieService.createMovie(uniqueName, directorId, 2004, Collections.singletonList(genreId), Collections.singletonList(actorId), "test desc");

        assertNotNull(movieId);
        assertEquals(1, movieService.getAllMovies().size());
    }

    @Test
    public void testDeleteMovie() {
        Long directorId = personService.createPerson(uniqueName);
        Long actorId = personService.createPerson(uniqueName);
        Long genreId = genreService.createGenre(uniqueName);

        Long movieId = movieService.createMovie(uniqueName, directorId, 2004, Collections.singletonList(genreId), Collections.singletonList(actorId), "test desc");

        // Check that movie got persisted
        assertEquals(1, movieService.getAllMovies().size());

        boolean deleted = movieService.deleteMovie(movieId);

        // Check that movie got deleted
        assertTrue(deleted);
        assertEquals(0, movieService.getAllMovies().size());
    }

    @Test
    public void testGetSpecificMovie() {
        Long directorId = personService.createPerson(uniqueName);
        Long actorId = personService.createPerson(uniqueName);
        Long genreId = genreService.createGenre(uniqueName);

        Long movieId1 = movieService.createMovie(uniqueName, directorId, 2004, Collections.singletonList(genreId), Collections.singletonList(actorId), "test desc");
        Long movieId2 = movieService.createMovie(uniqueName, directorId, 2001, Collections.singletonList(genreId), Collections.singletonList(actorId), "very informative description");

        // Verify that correct movie gets returned
        assertEquals(movieId1, movieService.getMovie(movieId1).getId());

    }
}
