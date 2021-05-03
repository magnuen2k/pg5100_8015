package no.kristiania.selenium.po;

import no.kristiania.selenium.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class IndexPO extends LayoutPO {

    public IndexPO(PageObject other) {
        super(other);
    }

    public IndexPO(WebDriver driver, String host, int port) {
        super(driver, host, port);
    }

    public void clickLoginButton() {
        WebElement loginButton = driver.findElement(By.id("linkToLoginId"));
        loginButton.click();
    }

    public MoviePO toMoviePage(int movieId) {
        clickAndWait("id-" + movieId);

        MoviePO moviePO = new MoviePO(this);

        assertTrue(moviePO.isOnPage());

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