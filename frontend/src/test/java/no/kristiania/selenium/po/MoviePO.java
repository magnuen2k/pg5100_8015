package no.kristiania.selenium.po;

import no.kristiania.selenium.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MoviePO extends LayoutPO {

    public MoviePO (PageObject other) {
        super(other);
    }

    public boolean canGiveReview() {
        return getDriver().findElements(By.id("reviewSubmitBtn")).size() >= 1;
    }

    public void giveReview(String reviewText, String stars) {
        setText("reviewInput", reviewText);
        Select starsDropDown = new Select(getDriver().findElement(By.id("reviewStars")));
        starsDropDown.selectByValue(stars);
        clickAndWait("reviewSubmitBtn");
    }

    public boolean verifyReview(String reviewText) {
        return getDriver().findElements(By.className("allReviews")).stream().anyMatch(e -> e.getText().contains(reviewText));
    }

    @Override
    public boolean isOnPage() {
        return getDriver().getTitle().contains("Movie |");
    }
}
