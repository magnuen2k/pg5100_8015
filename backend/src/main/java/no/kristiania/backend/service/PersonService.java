package no.kristiania.backend.service;

import no.kristiania.backend.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Service
@Transactional
public class PersonService {

    @Autowired
    private EntityManager em;

    public Long createPerson(String name) {
        Person person = new Person();
        person.setName(name);

        em.persist(person);

        return person.getId();
    }
}
