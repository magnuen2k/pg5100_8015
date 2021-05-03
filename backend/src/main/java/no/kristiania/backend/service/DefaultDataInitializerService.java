package no.kristiania.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

        // Add persons (actors and directors)
        Long p1 = attempt(() -> personService.createPerson(" James Caan"));
        Long p2 = attempt(() -> personService.createPerson("Al Pacino"));
        Long p3 = attempt(() -> personService.createPerson("Alex Garland"));
        Long p4 = attempt(() -> personService.createPerson("Alicia Vikander"));
        Long p5 = attempt(() -> personService.createPerson("Carrie Fisher"));
        Long p6 = attempt(() -> personService.createPerson("David Carradine"));
        Long p7 = attempt(() -> personService.createPerson("Domhball Gleeson"));
        Long p8 = attempt(() -> personService.createPerson("Francis Ford Coppola"));
        Long p9 = attempt(() -> personService.createPerson("George Lucas"));
        Long p10 = attempt(() -> personService.createPerson("George Miller"));
        Long p11 = attempt(() -> personService.createPerson("Gerardo Taracena"));
        Long p12 = attempt(() -> personService.createPerson("Harrison Ford"));
        Long p13 = attempt(() -> personService.createPerson("Hugh Keays-Byrne"));
        Long p14 = attempt(() -> personService.createPerson("Humphrey Bogart"));
        Long p15 = attempt(() -> personService.createPerson("Ingrid Bergman"));
        Long p16 = attempt(() -> personService.createPerson("Jessica Chastain"));
        Long p17 = attempt(() -> personService.createPerson("Joanne Samuel"));
        Long p18 = attempt(() -> personService.createPerson("John Travolta"));
        Long p19 = attempt(() -> personService.createPerson("Kristen Wiig"));
        Long p20 = attempt(() -> personService.createPerson("Mark Hamill"));
        Long p21 = attempt(() -> personService.createPerson("Marlon Brando"));
        Long p22 = attempt(() -> personService.createPerson("Matt Damon"));
        Long p23 = attempt(() -> personService.createPerson("Mel Gibson"));
        Long p24 = attempt(() -> personService.createPerson("Michael Curtiz"));
        Long p25 = attempt(() -> personService.createPerson("Oscar Isaac"));
        Long p26 = attempt(() -> personService.createPerson("Paul Henreid"));
        Long p27 = attempt(() -> personService.createPerson("Paul Le Mat"));
        Long p28 = attempt(() -> personService.createPerson("Quention Tarantino"));
        Long p29 = attempt(() -> personService.createPerson("Raoul Max Trujillo"));
        Long p30 = attempt(() -> personService.createPerson("Richard Dreyfuss"));
        Long p31 = attempt(() -> personService.createPerson("Ridley Scott"));
        Long p32 = attempt(() -> personService.createPerson("Ron Howard"));
        Long p33 = attempt(() -> personService.createPerson("Samuel L. Jackson"));
        Long p34 = attempt(() -> personService.createPerson("Uma Thurman"));
        Long p35 = attempt(() -> personService.createPerson("Vivica A. F"));

        // Add genres
        Long comedy = attempt(() -> genreService.createGenre("Comedy"));
        Long action = attempt(() -> genreService.createGenre("Action"));
        Long crime = attempt(() -> genreService.createGenre("Crime"));
        Long drama = attempt(() -> genreService.createGenre("Drama"));
        Long romance = attempt(() -> genreService.createGenre("Romance"));
        Long war = attempt(() -> genreService.createGenre("War"));
        Long thriller = attempt(() -> genreService.createGenre("Thriller"));
        Long adventure = attempt(() -> genreService.createGenre("Adventure"));
        Long fantasy = attempt(() -> genreService.createGenre("Fantasy"));
        Long sciFi = attempt(() -> genreService.createGenre("Sci-Fi"));

        // Add movies
        Long movie1 = attempt(() -> movieService.createMovie("The Godfather", p8, 1972, Arrays.asList(crime, drama), Arrays.asList(p21, p2)));

        // Add reviews
        attempt(() -> reviewService.addReview(movie1, "test", "very good movie", 5));
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

