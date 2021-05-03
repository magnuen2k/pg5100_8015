package no.kristiania.backend.service;

import no.kristiania.backend.StubApplication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = StubApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class ReviewServiceTest extends ServiceTestBase{

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private MovieService movieService;

    @Autowired
    private PersonService personService;

    @Autowired
    private GenreService genreService;

    @Autowired
    private UserService userService;

   /* @Test
    public void testAddReview() {
        // Create director and genre
        Long directorId = personService.createPerson("directorname");
        Long genreId = genreService.createGenre("comedy");
        boolean userCreated = userService.createUser("test", "password", "test@test.no");

        Long movieId = movieService.createMovie("title", directorId, 2020, genreId);

        boolean review = reviewService.addReview(movieId, "test", "review text comes here", 4);

        assertTrue(review);
        // can check that getReviews will be returning 1 review

    }

    @Test
    public void testGetAverageStars() {
        // Create director and genre
        Long directorId = personService.createPerson("directorname");
        Long genreId = genreService.createGenre("comedy");
        boolean userCreated = userService.createUser("test", "password", "test@test.no");

        Long movieId = movieService.createMovie("title", directorId, 2020, genreId);

        reviewService.addReview(movieId, "test", "review text comes here", 4);
        reviewService.addReview(movieId, "test", "review text comes here", 2);

        assertEquals(3, reviewService.getAverageReview(movieId));
        // can check that getReviews will be returning 1 review
    }

    @Test
    public void testGetAllReviews() {
        // Create director and genre
        Long directorId = personService.createPerson("directorname");
        Long genreId = genreService.createGenre("comedy");
        boolean userCreated = userService.createUser("test", "password", "test@test.no");

        Long movieId = movieService.createMovie("title", directorId, 2020, genreId);

        reviewService.addReview(movieId, "test", "review text comes here", 4);
        reviewService.addReview(movieId, "test", "review text comes here", 2);
        reviewService.addReview(movieId, "test", "review text comes here", 3);

        assertEquals(3, reviewService.getAllReviews(movieId, true).size());
    }*/
}
