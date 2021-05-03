package no.kristiania.frontend.controller;

import no.kristiania.backend.entity.Review;
import no.kristiania.backend.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class ReviewController implements Serializable {

    @Autowired
    private ReviewService reviewService;

    private String reviewText;
    private int stars = 1;
    private long selectedMovieId;

    public double getAverageRating(long movieId) {
        selectedMovieId = movieId;
        return reviewService.getAverageReview(movieId);
    }

    public List<Review> getReviews(long movieId) {
        return reviewService.getAllReviews(movieId);
    }

    // Give error if not working
    public String addReview(String username) {
        boolean ok = reviewService.addReview(selectedMovieId, username, reviewText, stars);
        System.out.println(ok);
        return "/index.jsf?&faces-redirect=true";
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
}
