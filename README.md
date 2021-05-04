# PG5100 Enterprise 1, Exam May 2021

## Instructions
To run the application:
* Entrypoint class to run from an IDE is `LocalApplicationRunner` which is located under `frontend/test/java/no/kristiania/`
    * Application is then accessible in your browser at `http://localhost:8080`.

To run all tests and build project
* `mvn verify`

When running the application, on can either sign up or use any of these premade users:
    
    * username: test
    * password: test
    
    * username: dummy
    * password: test
    
    * username: foo
    * password: bar
 
## Covered exam topics
 
I did R1, R2, R3, R4 and R5.

R4 NOTE: After experimenting with Selenium and Docker, I have learned that Selenium tests are heavily dependent on page load. One of my Selenium tests have failed 1 out of 50 times, and I suspect this is a page load issue. 
 
Extras(R5)
* Similar movies: Button accessible from specific movie page. Takes you to a new page listing all movies that share at least one of the genres with the specific movie.
    * Covered by Selenium test: `testGetSimilarMovies()`.
* Expanded functionality: Sort reviews both asc and desc for either time or stars. These are selectable commands on a specific movie page.
    * Covered by Selenium test: `testSorting()`. This test is part of R4, but is expanded to cover this extra feature.
* Implemented Docker with Selenium tests.

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
This is a simple application where users can leave reviews for movies. All navigation is done through the menu at the top.

* View movies
   * All movies are displayed on the homepage. 

* To sign in/up
    * Click either `Sign in` or `Log in` on the menu at the top of the page.
    * Fill out the form.
    
* Review movie
    * Click on the movie to review.
    * Fill out a review and choose stars (rating) and submit.

* Sort reviews
    * Navigate to a specific movie.
    * Choose sorting options and click `sort`.

* View similar movies
    * Navigate to a specific movie.
    * Click `See movies in the same genre!`.

## Credits
* All movie data is retrieved from `https://imdb.com`.
* Code copied from class is credited in the source files.