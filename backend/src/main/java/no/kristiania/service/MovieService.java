package no.kristiania.service;

import no.kristiania.entity.Genre;
import no.kristiania.entity.Movie;
import no.kristiania.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
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

    /*public List<Movie> getAllMovies() {
        TypedQuery<Movie> query = em.createQuery("SELECT m FROM Movie m ORDER BY m.")
    }*/

}
