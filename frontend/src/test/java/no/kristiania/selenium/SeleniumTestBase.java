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
}
