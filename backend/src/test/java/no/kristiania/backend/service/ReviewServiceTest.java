package no.kristiania.backend.service;

import no.kristiania.backend.StubApplication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.Collections;

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

    private int count = 0;
    private String uniqueName = "test_" + count++;

   @Test
    public void testAddReview() {
        // Create director, actors and genre
        Long directorId = personService.createPerson(uniqueName);
        Long genreId = genreService.createGenre(uniqueName);
        Long actorId1 = personService.createPerson(uniqueName);
        Long actorId2 = personService.createPerson(uniqueName);

        // Create user (Cannot use unique name here as we need to provide the username when adding a new review)
        boolean userCreated = userService.createUser("test", "password", "test@test.no");
        assertTrue(userCreated);

        // Create movie
        Long movieId = movieService.createMovie(uniqueName, directorId, 2010, Collections.singletonList(genreId), Arrays.asList(actorId1, actorId2), "test desc");

        boolean review = reviewService.addReview(movieId, "test", "review text comes here", 4);

        assertTrue(review);

        // Get all reviews for this movie and verify that the review has been added
        assertEquals(1, reviewService.getAllReviews(movieId, true, true).size());

    }


    @Test
    public void testGetAverageStars() {
        // Create director, actors and genre
        Long directorId = personService.createPerson(uniqueName);
        Long genreId = genreService.createGenre(uniqueName);
        Long actorId1 = personService.createPerson(uniqueName);
        Long actorId2 = personService.createPerson(uniqueName);

        // Create user (Cannot use unique name here as we need to provide the username when adding a new review)
        boolean userCreated = userService.createUser("test", "password", "test@test.no");
        assertTrue(userCreated);

        // Create movie
        Long movieId = movieService.createMovie(uniqueName, directorId, 2010, Collections.singletonList(genreId), Arrays.asList(actorId1, actorId2), "test desc");

        boolean review1 = reviewService.addReview(movieId, "test", "review text comes here", 4);
        boolean review2 = reviewService.addReview(movieId, "test", "review text comes here", 2);
        boolean review3 = reviewService.addReview(movieId, "test", "review text comes here", 3);

        assertTrue(review1);
        assertTrue(review2);
        assertTrue(review3);

        // Get all reviews for this movie and verify that the review has been added
        assertEquals(3, reviewService.getAverageReview(movieId));
    }


    @Test
    public void testGetAllReviews() {
        // Create director, actors and genre
        Long directorId = personService.createPerson(uniqueName);
        Long genreId = genreService.createGenre(uniqueName);
        Long actorId1 = personService.createPerson(uniqueName);
        Long actorId2 = personService.createPerson(uniqueName);

        // Create user (Cannot use unique name here as we need to provide the username when adding a new review)
        boolean userCreated = userService.createUser("test", "password", "test@test.no");
        assertTrue(userCreated);

        // Create movie
        Long movieId = movieService.createMovie(uniqueName, directorId, 2010, Collections.singletonList(genreId), Arrays.asList(actorId1, actorId2), "test desc");

        boolean review1 = reviewService.addReview(movieId, "test", "review text comes here", 4);
        boolean review2 = reviewService.addReview(movieId, "test", "review text comes here", 2);
        boolean review3 = reviewService.addReview(movieId, "test", "review text comes here", 3);
        boolean review4 = reviewService.addReview(movieId, "test", "review text comes here", 3);
        boolean review5 = reviewService.addReview(movieId, "test", "review text comes here", 3);

        assertTrue(review1);

        // Verify that all reviews got persisted
        assertEquals(5, reviewService.getAllReviews(movieId, true, true).size());
    }
}
