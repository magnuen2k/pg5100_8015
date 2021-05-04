package no.kristiania.frontend.controller;

import no.kristiania.backend.entity.Movie;
import no.kristiania.backend.entity.Review;
import no.kristiania.backend.service.MovieService;
import no.kristiania.backend.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class MovieController implements Serializable {

    @Autowired
    private MovieService movieService;

    @Autowired
    private ReviewService reviewService;

    private int stars = 1;
    private String reviewText = "";
    private long selectedMovieId;
    private boolean sortByRating = false;
    private boolean sortAsc = false;
    private List<Review> reviews = new ArrayList<>();

    public List<Movie> getMovies() {
        return movieService.getAllMovies();
    }

    public List<Movie> getSimilarMovies() {
        return movieService.getSimilarMovies(selectedMovieId);
    }

    public String openMoviePage(long movieId) {
        selectedMovieId = movieId;
        return "/movie.jsf?faces-redirect=true";
    }

    public Movie getMovie(long movieId) {
        return movieService.getMovie(movieId);
    }

    public String getAverageRating(long movieId) {
        double d = reviewService.getAverageReview(movieId);
        DecimalFormat df = new DecimalFormat("#.#");
        return df.format(d);
    }

    public boolean reviewGivenByUser(String username) {
        for(Review review : reviews) {
            if(review.getAuthor().getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    // Give error if not working
    public String addReview(String username) {
        boolean ok = reviewService.addReview(selectedMovieId, username, reviewText, stars);
        return reload();
    }

    public List<Review> getReviews() {
        reviews = reviewService.getAllReviews(selectedMovieId, sortByRating, sortAsc);
        return reviews;
    }

    public String reload() {
        return "/movie.jsf";
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public long getSelectedMovieId() {
        return selectedMovieId;
    }

    public void setSelectedMovieId(long selectedMovieId) {
        this.selectedMovieId = selectedMovieId;
    }

    public boolean isSortByRating() {
        return sortByRating;
    }

    public void setSortByRating(boolean sortByRating) {
        this.sortByRating = sortByRating;
    }

    public boolean isSortAsc() {
        return sortAsc;
    }

    public void setSortAsc(boolean sortAsc) {
        this.sortAsc = sortAsc;
    }
}
