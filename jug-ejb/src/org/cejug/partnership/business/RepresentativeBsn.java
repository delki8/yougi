package org.cejug.partnership.business;

import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.cejug.entity.UserAccount;
import org.cejug.partnership.entity.Representative;

/**
 * Manages partners of the user group.
 * @author Hildeberto Mendonca
 */
@Stateless
@LocalBean
public class RepresentativeBsn {
	
    @PersistenceContext
    private EntityManager em;

    public Representative findRepresentative(String id) {
        if(id != null)
            return em.find(Representative.class, id);
        else
            return null;
    }
    
    public Representative findRepresentative(UserAccount person) {
    	try {
    		return (Representative) em.createQuery("select r from Representative r where r.person = :person")
    								  .setParameter("person", person)
                                      .getSingleResult();
    	}
    	catch(NoResultException nre) {
    		return null;
    	}
    }
    
    public void save(Representative representative) {
        Representative existing = em.find(Representative.class, representative.getId());
        if(existing == null)
            em.persist(representative);
        else
            em.merge(representative);
    }

    public void remove(String id) {
        Representative representative = em.find(Representative.class, id);
        if(representative != null)
            em.remove(representative);
    }
}