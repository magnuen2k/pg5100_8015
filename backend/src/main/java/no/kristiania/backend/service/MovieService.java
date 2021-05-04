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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@Transactional
public class MovieService {

    @Autowired
    private EntityManager em;

    public Long createMovie(String title, long directorId, int yearOfRelease, List<Long> genreIds, List<Long> actorIds, String description) {

        Person director = em.find(Person.class, directorId);

        List<Genre> genres = new ArrayList<>();
        for(Long genreId : genreIds ) {
            Genre genre = em.find(Genre.class, genreId);
            if(genre != null) {
                genres.add(genre);
            }
        }

        List<Person> actors = new ArrayList<>();
        for(Long actorId : actorIds ) {
            Person actor = em.find(Person.class, actorId);
            if(actor != null) {
               actors.add(actor);
            }
        }

        Movie movie = new Movie();
        movie.setTitle(title);
        movie.setDirector(director);
        movie.setYearOfRelease(yearOfRelease);
        movie.setGenres(genres);
        movie.setActors(actors);
        movie.setDescription(description);

        em.persist(movie);
        return movie.getId();
    }

    public boolean deleteMovie(long movieId) {
        if(em.find(Movie.class, movieId) == null) {
            return false;
        }

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

    public List<Movie> getSimilarMovies(long movieId) {
        Movie movie = em.find(Movie.class, movieId);
        List<Movie> similarMovies = new ArrayList<>();

        for(Movie m : getAllMovies()) {
            if(m.getId() != movieId) {
                for(Genre g : movie.getGenres()) {
                    if(m.getGenres().contains(g)) {
                        similarMovies.add(m);
                        break;
                    }
                }
            }
        }

        return similarMovies;
    }
}
