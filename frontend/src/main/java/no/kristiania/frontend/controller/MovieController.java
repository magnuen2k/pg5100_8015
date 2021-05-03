package no.kristiania.frontend.controller;

import no.kristiania.backend.entity.Movie;
import no.kristiania.backend.entity.Review;
import no.kristiania.backend.service.MovieService;
import no.kristiania.backend.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
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
    private boolean starSort = true;

    public List<Movie> getMovies() {
        return movieService.getAllMovies();
    }

    public String openMoviePage(long movieId) {
        selectedMovieId = movieId;
        return "/movie.jsf?faces-redirect=true";
    }

    public Movie getMovie(long movieId) {
        return movieService.getMovie(movieId);
    }

    public double getAverageRating(long movieId) {
        return reviewService.getAverageReview(movieId);
    }

    // Give error if not working
    public String addReview(String username) {
        boolean ok = reviewService.addReview(selectedMovieId, username, reviewText, stars);
        return reload();
    }

    public List<Review> getReviews() {
        return reviewService.getAllReviews(selectedMovieId, starSort);
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

    public boolean isStarSort() {
        return starSort;
    }

    public void setStarSort(boolean starSort) {
        this.starSort = starSort;
    }

    public long getSelectedMovieId() {
        return selectedMovieId;
    }

    public void setSelectedMovieId(long selectedMovieId) {
        this.selectedMovieId = selectedMovieId;
    }
}
