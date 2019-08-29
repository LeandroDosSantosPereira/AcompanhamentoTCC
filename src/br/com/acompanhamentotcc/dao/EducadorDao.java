/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.acompanhamentotcc.dao;

import br.com.acompanhamentotcc.dao.exceptions.NonexistentEntityException;
import br.com.acompanhamentotcc.model.Curso;
import br.com.acompanhamentotcc.model.Educador;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 *  @author LEANDRO
 */
public class EducadorDao implements Serializable {

    private EntityManagerFactory emf;

    public EntityManager getEntityManager() {
        if(emf == null){
            this.emf = Persistence.
                    createEntityManagerFactory("AcompanhamentoTCCPU");
        }
        return emf.createEntityManager();
    }

    public void create(Educador educador) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(educador);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Educador educador) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            educador = em.merge(educador);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = educador.getId();
                if (findEducador(id) == null) {
                    throw new NonexistentEntityException("The educador with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Educador educador;
            try {
                educador = em.getReference(Educador.class, id);
                educador.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The educador with id " + id + " no longer exists.", enfe);
            }
            em.remove(educador);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Educador> findEducadorEntities() {
        return findEducadorEntities(true, -1, -1);
    }

    public List<Educador> findEducadorEntities(int maxResults, int firstResult) {
        return findEducadorEntities(false, maxResults, firstResult);
    }

    private List<Educador> findEducadorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Educador.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Educador findEducador(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Educador.class, id);
        } finally {
            em.close();
        }
    }

    public int getEducadorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Educador> rt = cq.from(Educador.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

  public List<Educador> pesquisar(Educador educador) {
        EntityManager em = getEntityManager();
        StringBuilder sql = 
                new StringBuilder("from Educador as c where 1 = 1 ");
        
        if(educador.getId()!= null){
            sql.append("and c.id = :id ");
        }
        if(educador.getNome() != null && !educador.getNome().equals("")){
            sql.append("and c.nome like :nome ");
        }
        
        Query query = em.createQuery(sql.toString());
        
        if(educador.getId()!= null){
            query.setParameter("id", educador.getId());
        }
        if(educador.getNome() != null && !educador.getNome().equals("")){
            query.setParameter("nome", "%"+educador.getNome()+"%");
        }
        return query.getResultList();
    }


    public void excluir(Educador educador) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        educador = em.merge(educador);
        em.remove(educador);
        em.getTransaction().commit();
        em.close();
    }
    
}
