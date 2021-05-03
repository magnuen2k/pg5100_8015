package no.kristiania.service;

import no.kristiania.entity.Movie;
import no.kristiania.entity.Review;
import no.kristiania.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class ReviewService {

    @Autowired
    private EntityManager em;

    public boolean addReview(long movieId, String username, String reviewText, int stars) {
        // Check if user and movie exists
        Movie movie = em.find(Movie.class, movieId);
        User user = em.find(User.class, username);

        if(movie == null && user == null) {
            return false;
        }

        LocalDate date = LocalDate.now();

        Review review = new Review();
        review.setAuthor(user);
        review.setMovie(movie);
        review.setReviewText(reviewText);
        review.setStars(stars);
        review.setCreatedAt(date);

        em.persist(review);
        return true;
    }

    public double getAverageReview(long movieId) {
        Query query = em.createQuery("SELECT AVG(r.stars) FROM Review r WHERE r.movie.id = ?1 ");
        query.setParameter(1, movieId);
        return (Double) query.getSingleResult();
    }

    // Sorted by stars, could get sorting as parameter?
    public List<Review> getAllReviews(long movieId) {
        TypedQuery<Review> query = em.createQuery("SELECT r FROM Review r WHERE r.movie.id = ?1 ORDER BY r.stars ASC", Review.class);
        query.setParameter(1, movieId);
        return query.getResultList();
    }
}