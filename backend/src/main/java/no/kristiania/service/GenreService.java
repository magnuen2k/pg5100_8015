package no.kristiania.service;

import no.kristiania.entity.Genre;
import no.kristiania.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Service
@Transactional
public class GenreService {

    @Autowired
    private EntityManager em;

    public Long createGenre(String name) {
        Genre genre = new Genre();
        genre.setName(name);

        em.persist(genre);
        return genre.getId();
    }
}
