package no.kristiania.selenium.po;

import no.kristiania.selenium.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SimilarMoviesPO extends LayoutPO{

    public SimilarMoviesPO(PageObject other) {
        super(other);
    }

    public boolean displaysSimilarMovies(List<String> genres) {
        List<WebElement> similarMovieGenres = getDriver().findElements(By.className("genres"));
        boolean genreFound = false;
        for(WebElement similarGenres : similarMovieGenres) {
            genreFound = false;
            for(String genre : genres) {
                if(similarGenres.getText().contains(genre)) {
                    genreFound = true;
                    break;
                }
            }
            if(!genreFound) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isOnPage() {
        return getDriver().getTitle().contains("Similar Movies");
    }
}
