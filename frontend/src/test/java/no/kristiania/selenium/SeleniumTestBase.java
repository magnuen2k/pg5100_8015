package no.kristiania.selenium;

import no.kristiania.selenium.po.IndexPO;
import no.kristiania.selenium.po.MoviePO;
import no.kristiania.selenium.po.SignUpPO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
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
    }

    @Test
    public void testPort() {
        assertThat(getServerPort(), is(greaterThan(0)));
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
        home = signUpPO.createUser("newUserName", "password", "test2@test.no");

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
    }

    @Test
    public void testStars() {
        // Get average stars from a movie
        double averageStars = Double.parseDouble(home.getAverageStars("46"));

        // Sign up to able to leave a review
        SignUpPO signUpPO = home.toSignUp();
        home = signUpPO.createUser("Uniqueusername", "password", "test5@test.no");

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
        assertTrue(Double.parseDouble(home.getAverageStars("46")) < averageStars);
    }

    @Test
    public void testSorting() {
        String reviewUser1 = "Review from user1";
        String reviewUser2 = "Review from user2";
        String reviewUser3 = "Review from user3";

        // AUTHOR 1
        SignUpPO signUpPO = home.toSignUp();
        home = signUpPO.createUser("user1", "password", "user1@test.no");
        assertTrue(home.isLoggedIn());

        home.toStartingPage();

        // Go to a movie page
        MoviePO moviePO = home.toMoviePage(47);

        assertTrue(moviePO.canGiveReview());

        moviePO.giveReview(reviewUser1, "4");
        home.doLogout();

        // AUTHOR 2
        signUpPO = home.toSignUp();
        home = signUpPO.createUser("user2", "password", "user2@test.no");

        assertTrue(home.isLoggedIn());

        home.toStartingPage();

        // Go to same movie page
        moviePO = home.toMoviePage(47);

        assertTrue(moviePO.canGiveReview());

        moviePO.giveReview(reviewUser2, "3");
        home.doLogout();

        // AUTHOR 3
        signUpPO = home.toSignUp();
        home = signUpPO.createUser("user3", "password", "user3@test.no");

        assertTrue(home.isLoggedIn());

        home.toStartingPage();

        // Go to same movie page
        moviePO = home.toMoviePage(47);

        assertTrue(moviePO.canGiveReview());

        moviePO.giveReview(reviewUser3, "5");

        // Check sorting
        // Verify reviews came through
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
}
