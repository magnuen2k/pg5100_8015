package no.kristiania.backend.service;

import no.kristiania.backend.entity.Genre;
import no.kristiania.backend.entity.Movie;
import no.kristiania.backend.entity.Person;
import no.kristiania.backend.entity.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@Transactional
public class MovieService {

    @Autowired
    private EntityManager em;

    public Long createMovie(String title, long directorId, int yearOfRelease, long genreId) {

        // Check if movie already exists
        Person person = em.find(Person.class, directorId);
        Genre genre = em.find(Genre.class, genreId);

        Movie movie = new Movie();
        movie.setTitle(title);
        movie.setDirector(person);
        movie.setYearOfRelease(yearOfRelease);
        movie.setGenre(genre);

        em.persist(movie);
        return movie.getId();
    }

    public boolean deleteMovie(long movieId) {
        if(em.find(Movie.class, movieId) == null) {
            return false;
        }

        // If this does not work make a query and set movie ID as parameter
        em.remove(em.find(Movie.class, movieId));
        return true;
    }

    public Movie getMovie(long movieId) {
        return em.find(Movie.class, movieId);
    }

    public List<Movie> getAllMovies() {
        TypedQuery<Movie> query = em.createQuery("SELECT m FROM Movie m", Movie.class);
        List<Movie> movies = query.getResultList();
        movies.sort(Comparator.comparing(Movie::averageStars).reversed());
        return movies;
    }
}
