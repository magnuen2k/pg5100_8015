package no.kristiania.backend.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Movie {
    @Id
    @GeneratedValue
    @NotNull
    private Long id;

    @NotBlank
    // Max size?
    private String title;

    @NotNull
    @ManyToOne
    private Person director;

    @NotNull
    private int yearOfRelease;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private List<Review> reviews;

    @ManyToMany
    private List<Person> actors;

    @ManyToMany
    private List<Genre> genres;

    public Movie() {
        actors = new ArrayList<>();
        reviews = new ArrayList<>();
        genres = new ArrayList<>();
    }

    public double averageStars() {
        if(reviews.size() < 1) {
            return 0;
        }
        double stars = 0;
        for(Review r : reviews) {
            stars += r.getStars();
        }
        return stars / reviews.size();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Person getDirector() {
        return director;
    }

    public void setDirector(Person director) {
        this.director = director;
    }

    public int getYearOfRelease() {
        return yearOfRelease;
    }

    public void setYearOfRelease(int yearOfRelease) {
        this.yearOfRelease = yearOfRelease;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Review review) {
        this.reviews.add(review);
    }

    public List<Person> getActors() {
        return actors;
    }

    public void setActors(List<Person> actors) {
        this.actors = actors;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }
}
