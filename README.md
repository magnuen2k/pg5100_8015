# pg5100_8015

## Instructions
 Entrypoint class
 How to build and run tests
 
 ## Covered exam topics
 
 I did R1, R2, R3, R4 and R5.
 
 R4 Note: All the Selenium tests required are implemented. Function `testSorting()` have failed unexpectedly twice, but failure have not been reproducible for the last 100 test runs. Therefore, I decided to include the test.  
 
 Extras(R5)
 * Similar movies: Button accessible from specific movie page. Takes you to a new page listing all movies that share at least one of the genres with the specific movie.
    * Covered by Selenium test: `testGetSimilarMovies()`.
 * Expanded functionality: Sort reviews both asc and desc for either time or stars. These are selectable commands on a specific movie page.
    * Covered by Selenium test: `testSorting()`. This test is part of R4, but is expanded to cover this extra feature.
 * DOCKER HVIS JEG TAR DET MED

## Entity structure
The following entities are defined according to R1:
* User
* Movie
* Review

Extra entities:

* Person
    * The director of a movie is referencing a Person. The Movie entity also contains a list of actors, where each actor is a Person. The reason for using Person for both actors and directors is that one single person can actually be both an actor and a director. 
    Therefore, there exists both a `many-to-one` relation (director) and a `many-to-many` relation (actors).
* Genre
    * A movie can be categorized with several genres. A movie can for an example be both romantic and comedy, thus there is a `many-to-many` relation between Movie and Genre.

## How to use the application

## Credits
* All movie data is retrieved from `https://imdb.com`.
* Code copied from class is credited in the source files.