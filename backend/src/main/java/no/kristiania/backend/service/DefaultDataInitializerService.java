package no.kristiania.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.function.Supplier;

// This class is extended from class shown in lecture
// https://github.com/arcuri82/testing_security_development_enterprise_systems/blob/master/intro/exercise-solutions/quiz-game/part-11/backend/src/main/java/org/tsdes/intro/exercises/quizgame/backend/service/DefaultDataInitializerService.java

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
        attempt(() -> userService.createUser("test", "test", "test@test.no", "test", "testing"));
        attempt(() -> userService.createUser("dummy", "test", "dummy@test.no", "dummy", "dummy"));

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

        String d1 = "An organized crime dynasty's aging patriarch transfers control of his clandestine empire to his reluctant son.";
        String d2 = "A cynical expatriate American cafe owner struggles to decide whether or not to help his former lover and her fugitive husband escape the Nazis in French Morocco.";
        String d3 = "A couple of high school grads spend one final night cruising the strip with their buddies before they go off to college.";
        String d4 = "The lives of two mob hitmen, a boxer, a gangster and his wife, and a pair of diner bandits intertwine in four tales of violence and redemption.";
        String d5 = "The Bride must kill her ex-boss and lover Bill who betrayed her at her wedding rehearsal, shot her in the head and took away her unborn daughter. But first, she must make the other four members of the Deadly Viper Assassination Squad suffer.";
        String d6 = "Luke Skywalker joins forces with a Jedi Knight, a cocky pilot, a Wookiee and two droids to save the galaxy from the Empire's world-destroying battle station, while also attempting to rescue Princess Leia from the mysterious Darth Vader.";
        String d7 = "A young programmer is selected to participate in a ground-breaking experiment in synthetic intelligence by evaluating the human qualities of a highly advanced humanoid A.I.";
        String d8 = "An astronaut becomes stranded on Mars after his team assume him dead, and must rely on his ingenuity to find a way to signal to Earth that he is alive.";
        String d9 = "As the Mayan kingdom faces its decline, a young man is taken on a perilous journey to a world ruled by fear and oppression.";
        String d10 = "In a self-destructing world, a vengeful Australian policeman sets out to stop a violent motorcycle gan";

        // Add movies
        Long movie1 = attempt(() -> movieService.createMovie("The Godfather", p8, 1972, Arrays.asList(crime, drama), Arrays.asList(p21, p2, p1), d1));
        Long movie2 = attempt(() -> movieService.createMovie("Casablanca", p24, 1942, Arrays.asList(drama, romance, war), Arrays.asList(p14, p15, p26), d2));
        Long movie3 = attempt(() -> movieService.createMovie("American Graffiti", p9, 1973, Arrays.asList(comedy, drama), Arrays.asList(p30, p32, p27), d3));
        Long movie4 = attempt(() -> movieService.createMovie("Pulp Fiction", p28, 1994, Arrays.asList(crime, drama), Arrays.asList(p18, p34, p33), d4));
        Long movie5 = attempt(() -> movieService.createMovie("Kill Bill: The Whole Bloody Affair", p28, 2011, Arrays.asList(crime, action, thriller), Arrays.asList(p34, p35, p6), d5));
        Long movie6 = attempt(() -> movieService.createMovie("Star Wars: Episode IV - A New Hope", p9, 1977, Arrays.asList(adventure, action, fantasy, sciFi), Arrays.asList(p20, p12, p5), d6));
        Long movie7 = attempt(() -> movieService.createMovie("Ex Machina", p3, 2014, Arrays.asList(drama, thriller, sciFi), Arrays.asList(p4, p7, p25), d7));
        Long movie8 = attempt(() -> movieService.createMovie("The Martian", p31, 2015, Arrays.asList(drama, adventure, sciFi), Arrays.asList(p22, p16, p19), d8));
        Long movie9 = attempt(() -> movieService.createMovie("Apocalypto", p23, 2006, Arrays.asList(drama, adventure, action, thriller), Arrays.asList(p11, p29), d9));
        Long movie10 = attempt(() -> movieService.createMovie("Mad Max", p10, 1979, Arrays.asList(sciFi, adventure, action, thriller), Arrays.asList(p23, p17, p13), d10));

        // Users to review
        attempt(() -> userService.createUser("foo", "bar", "foo@bar.no", "foo", "bar"));
        attempt(() -> userService.createUser("foo_1", "bar", "foo@bar.no", "foo_1", "foo_1"));
        attempt(() -> userService.createUser("foo_2", "bar", "foo@bar.no", "foo_2", "foo_2"));
        attempt(() -> userService.createUser("foo_3", "bar", "foo@bar.no", "foo_3", "foo_3"));
        attempt(() -> userService.createUser("foo_4", "bar", "foo@bar.no", "foo_4", "foo_4"));
        attempt(() -> userService.createUser("foo_5", "bar", "foo@bar.no", "foo_5", "foo_5"));
        attempt(() -> userService.createUser("foo_6", "bar", "foo@bar.no", "foo_6", "foo_6"));
        attempt(() -> userService.createUser("foo_7", "bar", "foo@bar.no", "foo_7", "foo_7"));
        attempt(() -> userService.createUser("foo_8", "bar", "foo@bar.no", "foo_8", "foo_8"));
        attempt(() -> userService.createUser("foo_9", "bar", "foo@bar.no", "foo_9", "foo_9"));
        attempt(() -> userService.createUser("foo_10", "bar", "foo@bar.no", "foo_10", "foo_10"));


        // Add reviews
        attempt(() -> reviewService.addReview(movie1, "foo_2", "Very good movie!", 5));
        attempt(() -> reviewService.addReview(movie1, "foo_9", "Very good", 5));
        attempt(() -> reviewService.addReview(movie1, "foo_10", "Not my favorite..", 3));
        attempt(() -> reviewService.addReview(movie1, "foo_3", "My favorite!", 5));
        attempt(() -> reviewService.addReview(movie3, "foo_8", "Not a fan...", 3));
        attempt(() -> reviewService.addReview(movie3, "foo_2", "Not a fan...", 2));
        attempt(() -> reviewService.addReview(movie3, "foo_5", "Very nice", 4));
        attempt(() -> reviewService.addReview(movie4, "foo_5", "Very nice", 4));
        attempt(() -> reviewService.addReview(movie4, "foo_3", "Good one", 4));
        attempt(() -> reviewService.addReview(movie5, "foo_9", "Nice one", 5));
        attempt(() -> reviewService.addReview(movie5, "foo_1", "So good!", 5));
        attempt(() -> reviewService.addReview(movie6, "foo_5", "Love this!!", 5));
        attempt(() -> reviewService.addReview(movie7, "foo_4", "Love this!!", 5));
        attempt(() -> reviewService.addReview(movie7, "foo_7", "Not my favorite..", 3));
        attempt(() -> reviewService.addReview(movie7, "foo_7", "Not very nice", 2));
        attempt(() -> reviewService.addReview(movie8, "foo_3", "So nice", 5));
        attempt(() -> reviewService.addReview(movie8, "foo_2", "Nice one", 4));
        attempt(() -> reviewService.addReview(movie9, "foo_5", "Nice", 4));
        attempt(() -> reviewService.addReview(movie9, "foo_3", "Good one", 5));
        attempt(() -> reviewService.addReview(movie9, "foo_8", "Not my favorite, but a good one!", 3));
        attempt(() -> reviewService.addReview(movie10, "foo", "Nice", 5));
        attempt(() -> reviewService.addReview(movie10, "foo_1", "Not my favorite..", 2));
        attempt(() -> reviewService.addReview(movie10, "foo_2", "Very good", 5));
        attempt(() -> reviewService.addReview(movie10, "foo_3", "Good", 4));
        attempt(() -> reviewService.addReview(movie10, "foo_6", "Nice one", 4));
    }

    // This functions is copied from class
    // https://github.com/arcuri82/testing_security_development_enterprise_systems/blob/master/intro/exercise-solutions/quiz-game/part-11/backend/src/main/java/org/tsdes/intro/exercises/quizgame/backend/service/DefaultDataInitializerService.java
    private  <T> T attempt(Supplier<T> lambda){
        try{
            return lambda.get();
        }catch (Exception e){
            return null;
        }
    }
}

