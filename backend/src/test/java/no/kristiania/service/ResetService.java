package no.kristiania.service;

import no.kristiania.entity.Movie;
import no.kristiania.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

// CLASS HEAVILY INSPIRED BY CODE FROM CLASS
// LINK TO REPO
@Service
@Transactional
public class ResetService {

    @PersistenceContext
    private EntityManager em;

    public void resetDatabase(){
        //Have to use native SQL for ElementCollection
        Query query = em.createNativeQuery("delete from user_roles");
        query.executeUpdate();

        deleteEntities(User.class);
        deleteEntities(Movie.class);
    }

    private void deleteEntities(Class<?> entity){

        if(entity == null || entity.getAnnotation(Entity.class) == null){
            throw new IllegalArgumentException("Invalid non-entity class");
        }

        String name = entity.getSimpleName();

        // This is fine for testing but NOT okay in production code
        Query query = em.createQuery("delete from " + name);
        query.executeUpdate();
    }

}