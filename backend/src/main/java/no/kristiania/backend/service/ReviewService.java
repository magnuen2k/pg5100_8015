package no.kristiania.backend.service;

import no.kristiania.backend.entity.Movie;
import no.kristiania.backend.entity.Review;
import no.kristiania.backend.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

        LocalDateTime date = LocalDateTime.now();

        Review review = new Review();
        review.setAuthor(user);
        review.setMovie(movie);
        review.setReviewText(reviewText);
        review.setStars(stars);
        review.setCreatedAt(date);
        movie.setReviews(review);

        em.persist(review);
        return true;
    }

    public double getAverageReview(long movieId) {
        if(em.find(Movie.class, movieId) != null) {
            return em.find(Movie.class, movieId).averageStars();
        }
        return 0;
    }

    // Sorted by stars, could get sorting as parameter?
    public List<Review> getAllReviews(long movieId, boolean sortByRating, boolean sortAsc) {
        TypedQuery<Review> query;
        if(sortByRating) {
            if(sortAsc) {
                query = em.createQuery("SELECT r FROM Review r WHERE r.movie.id = ?1 ORDER BY r.stars ASC", Review.class);
                System.out.println("SELECT r FROM Review r WHERE r.movie.id = ?1 ORDER BY r.stars ASC");
            } else {
                query = em.createQuery("SELECT r FROM Review r WHERE r.movie.id = ?1 ORDER BY r.stars DESC", Review.class);
                System.out.println("SELECT r FROM Review r WHERE r.movie.id = ?1 ORDER BY r.stars DESC");
            }
        } else {
            if(sortAsc) {
                query = em.createQuery("SELECT r FROM Review r WHERE r.movie.id = ?1 ORDER BY r.createdAt ASC", Review.class);
                System.out.println("SELECT r FROM Review r WHERE r.movie.id = ?1 ORDER BY r.createdAt ASC");
            } else {
                query = em.createQuery("SELECT r FROM Review r WHERE r.movie.id = ?1 ORDER BY r.createdAt DESC", Review.class);
                System.out.println("SELECT r FROM Review r WHERE r.movie.id = ?1 ORDER BY r.createdAt DESC");
            }
        }
        query.setParameter(1, movieId);
        List<Review> res = query.getResultList();
        System.out.println(res.get(0).getCreatedAt());
        return query.getResultList();
    }
}


