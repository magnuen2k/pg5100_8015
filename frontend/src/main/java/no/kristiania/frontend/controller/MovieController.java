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

    public List<Movie> getMovies() {
        return movieService.getAllMovies();
    }

    public double getAverageRating(long movieId) {
        return reviewService.getAverageReview(movieId);
    }

    public String openMoviePage(long movieId) {
        return "/movie.jsf?movieId=" + movieId + "&faces-redirect=true";
    }

    public Movie getMovie(long movieId) {
        return movieService.getMovie(movieId);
    }

    public List<Review> getReviews(long movieId) {
        return reviewService.getAllReviews(movieId);
    }
}
