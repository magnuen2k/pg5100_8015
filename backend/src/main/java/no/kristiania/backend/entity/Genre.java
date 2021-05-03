package no.kristiania.backend.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class Genre {

    @Id
    @GeneratedValue
    @NotNull
    private Long id;

    @NotBlank
    @Size(max = 20)
    private String name;

    @ManyToMany(mappedBy = "genres", cascade = CascadeType.ALL)
    private List<Movie> movies;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
