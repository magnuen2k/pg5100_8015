package no.kristiania.selenium.po;

import no.kristiania.selenium.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertTrue;

// Code extended but heavily modified from lectures
// https://github.com/arcuri82/testing_security_development_enterprise_systems/blob/master/intro/exercise-solutions/quiz-game/part-11/frontend/src/test/java/org/tsdes/intro/exercises/quizgame/selenium/po/IndexPO.java

public class IndexPO extends LayoutPO {

    public IndexPO(PageObject other) {
        super(other);
    }

    public IndexPO(WebDriver driver, String host, int port) {
        super(driver, host, port);
    }

    public String getAverageStars(String movieId) {
        return getDriver().findElement(By.id("averageRating-" + movieId)).getText();
    }

    public MoviePO toMoviePage(int movieId) {
        clickAndWait("id-" + movieId);

        MoviePO moviePO = new MoviePO(this);

        //assertTrue(moviePO.isOnPage());

        return moviePO;
    }

    public void toStartingPage() {
        toOrigin();
    }

    @Override
    public boolean isOnPage() {
        return getDriver().getTitle().contains("MovieMania");
    }
}