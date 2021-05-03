package no.kristiania.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.function.Supplier;

@Service
public class DefaultDataInitializerService {

    @Autowired
    private UserService userService;

    @Autowired
    private MovieService movieService;

    @Autowired
    private PersonService personService;

    @Autowired
    private GenreService genreService;

    @Autowired
    private ReviewService reviewService;


    @PostConstruct
    public void init() {
        // Create testing users to add ratings
        attempt(() -> userService.createUser("test", "test", "test@test.no"));
        attempt(() -> userService.createUser("dummy", "test", "dummy@test.no"));

        // Add directors
        Long director1 = attempt(() -> personService.createPerson("J.K Rowling"));
        Long cn = attempt(() -> personService.createPerson("Nolan"));

        // Add genres
        Long comedy = attempt(() -> genreService.createGenre("comedy"));
        Long action = attempt(() -> genreService.createGenre("action"));

        // Add movies
        Long movie1 = attempt(() -> movieService.createMovie("Harry Potter", director1, 2015, comedy));
        Long movie2 = attempt(() -> movieService.createMovie("movie2", director1, 2019, comedy));
        Long inception = attempt(() -> movieService.createMovie("Inception", cn, 2019, action));

        // Add reviews
        attempt(() -> reviewService.addReview(movie2, "dummy", "long review", 2));
        attempt(() -> reviewService.addReview(movie1, "test", "long review", 1));
        attempt(() -> reviewService.addReview(inception, "test", "What makes Inception — written and directed by Christopher Nolan –  so successful is the panache with which it brazens right past its ludicrous premise", 3));
        attempt(() -> reviewService.addReview(inception, "dummy", "What makes Inception — written and directed by Christopher Nolan –  so successful is the panache with which it brazens right past its ludicrous premise", 4));
        attempt(() -> reviewService.addReview(inception, "dummy", "What makes Inception — written and directed by Christopher Nolan –  so successful is the panache with which it brazens right past its ludicrous premise", 2));
        attempt(() -> reviewService.addReview(inception, "dummy", "What makes Inception — written and directed by Christopher Nolan –  so successful is the panache with which it brazens right past its ludicrous premise", 4));
    }

    // This functions is copied from class
    private  <T> T attempt(Supplier<T> lambda){
        try{
            return lambda.get();
        }catch (Exception e){
            return null;
        }
    }
}

