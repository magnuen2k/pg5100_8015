package no.kristiania.selenium;

import no.kristiania.selenium.po.IndexPO;
import no.kristiania.selenium.po.MoviePO;
import no.kristiania.selenium.po.SignUpPO;
import no.kristiania.selenium.po.SimilarMoviesPO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Selenium test structure is extended from code shown in lectures, but heavily modified
// https://github.com/arcuri82/testing_security_development_enterprise_systems/tree/master/intro/exercise-solutions/quiz-game/part-11/frontend/src/test/java/org/tsdes/intro/exercises/quizgame/selenium

public abstract class SeleniumTestBase {
    protected abstract WebDriver getDriver();

    protected abstract String getServerHost();

    protected abstract int getServerPort();

    private IndexPO home;

    @BeforeEach
    public void initTest() {

        /*
            we want to have a new session, otherwise the tests
            will share the same Session beans
         */
        getDriver().manage().deleteAllCookies();

        home = new IndexPO(getDriver(), getServerHost(), getServerPort());

        home.toStartingPage();

        assertTrue(home.isOnPage(), "Failed to start from Home Page");
        assertNotNull(getDriver().findElement(By.id("id-46")));
        assertNotNull(getDriver().findElement(By.id("id-55")));
    }

    @Test
    public void testDefaultMovies() {
        // Should be on home page when test is starting
        // Verify not logged in
        assertFalse(home.isLoggedIn());

        assertTrue(home.getText("movies").contains("The Godfather"));
        assertTrue(home.getText("movies").contains("Casablanca"));

        // Verify that there is more than 2 movies displayed on the homepage
        assertTrue(home.getElementsByClassname("movieTitle").size() > 2);
    }

    @Test
    public void testWriteReview() {
        String reviewText = "This is a sample review text";

        // Verify not logged in
        assertFalse(home.isLoggedIn());

        // Go to a movie page
        MoviePO moviePO = home.toMoviePage(50);

        // Verify add review button is not there
        assertFalse(moviePO.canGiveReview());

        // Click sign up and create user
        SignUpPO signUpPO = home.toSignUp();
        home = signUpPO.createUser("newUserName", "password", "test2@test.no", "new", "user");

        // Should be logged in now
        assertTrue(home.isLoggedIn());

        // Navigate back to home page
        home.toStartingPage();

        // Go to same movie page
        moviePO = home.toMoviePage(50);

        // Verify add review button is there
        assertTrue(moviePO.canGiveReview());

        moviePO.giveReview(reviewText, "4");

        // Verify that user cannot leave review cause already left one
        assertFalse(moviePO.canGiveReview());

        // verify that review is there
        assertTrue(moviePO.verifyReview(reviewText));

        // Log out
        home.doLogout();
        home.toStartingPage();

        // Go to same movie page
        moviePO = home.toMoviePage(50);

        // verify that review is still there
        assertTrue(moviePO.verifyReview(reviewText));

    }

    @Test
    public void testStars() {
        // Get average stars from a movie
        String averageStars = home.getAverageStars("46");

        // Sign up to able to leave a review
        SignUpPO signUpPO = home.toSignUp();
        home = signUpPO.createUser("Uniqueusername", "password", "test5@test.no", "unique", "user");

        // Should be logged in now
        assertTrue(home.isLoggedIn());

        // Navigate back to home page
        home.toStartingPage();

        // Go to same movie page
        MoviePO moviePO = home.toMoviePage(46);

        // Verify add review button is there
        assertTrue(moviePO.canGiveReview());

        moviePO.giveReview("New review for this movie!", "2");

        // Navigate back to home page
        home = moviePO.toHomePage();

        // Verify average stars has changed
        assertNotEquals(averageStars, home.getAverageStars("46"));
    }

    @Test
    public void testSorting() {
        assertFalse(home.isLoggedIn());
        String reviewUser1 = "Review from user1";
        String reviewUser2 = "Review from user2";
        String reviewUser3 = "Review from user3";

        // AUTHOR 1
        SignUpPO signUpPO = home.toSignUp();
        home = signUpPO.createUser("user1", "password", "user1@test.no", "user", "1");
        assertTrue(home.isLoggedIn());

        home.toStartingPage();

        // Go to a movie page
        MoviePO moviePO = home.toMoviePage(47);

        assertTrue(moviePO.canGiveReview());

        moviePO.giveReview(reviewUser1, "4");
        home.doLogout();

        // AUTHOR 2
        signUpPO = home.toSignUp();
        home = signUpPO.createUser("user2", "password", "user2@test.no", "user", "2");

        assertTrue(home.isLoggedIn());

        home.toStartingPage();

        // Go to same movie page
        moviePO = home.toMoviePage(47);

        assertTrue(moviePO.canGiveReview());

        moviePO.giveReview(reviewUser2, "3");
        home.doLogout();

        // AUTHOR 3
        signUpPO = home.toSignUp();
        home = signUpPO.createUser("user3", "password", "user3@test.no", "user", "3");

        assertTrue(home.isLoggedIn());

        home.toStartingPage();

        // Go to same movie page
        moviePO = home.toMoviePage(47);

        assertTrue(moviePO.canGiveReview());

        moviePO.giveReview(reviewUser3, "5");

        // Check sorting
        // Verify reviews came through (Testing movie with no sample/fake reviews)
        assertEquals(3, home.getElementsByClassname("reviewWrapper").size());
        // Sort by rating
        moviePO.sort("sortByTypeId", "Rating");
        // Direction
        moviePO.sort("sortByDirectionId", "ASC");
        assertTrue(home.getElementsByClassname("reviewWrapper").get(0).getText().contains(reviewUser2));
        moviePO.sort("sortByDirectionId", "DESC");
        assertTrue(home.getElementsByClassname("reviewWrapper").get(0).getText().contains(reviewUser3));

        // Sort by time
        moviePO.sort("sortByTypeId", "Time");
        // Direction
        moviePO.sort("sortByDirectionId", "ASC");
        assertTrue(home.getElementsByClassname("reviewWrapper").get(0).getText().contains(reviewUser1));
        moviePO.sort("sortByDirectionId", "DESC");
        assertTrue(home.getElementsByClassname("reviewWrapper").get(0).getText().contains(reviewUser3));

    }

    @Test
    public void testGetSimilarMovies() {
        assertFalse(home.isLoggedIn());

        // Go to a moviepage
        MoviePO moviePO = home.toMoviePage(50);

        // Get genres of selected movie
        List<String> genreNames = moviePO.getGenres();

        // Go to similar movies page
        SimilarMoviesPO similarMoviesPO = moviePO.toSimilarMovies();

        // All movies should have at least one of the genres from the selected movie
        assertTrue(similarMoviesPO.displaysSimilarMovies(genreNames));

    }
}
