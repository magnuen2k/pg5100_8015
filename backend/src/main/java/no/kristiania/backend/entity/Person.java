package no.kristiania.backend.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class Person {

    @Id
    @GeneratedValue
    @NotNull
    private Long id;

    @NotBlank
    @Size(max = 64)
    private String name;

    @ManyToMany(mappedBy = "actors", cascade = CascadeType.ALL)
    private List<Movie> actingIn;

    @OneToMany
    private List<Movie> directorFor;

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

    public List<Movie> getActingIn() {
        return actingIn;
    }

    public void setActingIn(List<Movie> actingIn) {
        this.actingIn = actingIn;
    }

    public List<Movie> getDirectorFor() {
        return directorFor;
    }

    public void setDirectorFor(List<Movie> directorFor) {
        this.directorFor = directorFor;
    }
}
