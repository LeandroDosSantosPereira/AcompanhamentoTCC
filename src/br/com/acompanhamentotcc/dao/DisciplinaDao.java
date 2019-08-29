/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.acompanhamentotcc.dao;

import br.com.acompanhamentotcc.dao.exceptions.NonexistentEntityException;
import br.com.acompanhamentotcc.model.Disciplina;
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
public class DisciplinaDao implements Serializable {

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        if(emf == null){
            this.emf = Persistence.
                    createEntityManagerFactory("AcompanhamentoTCCPU");
        }
        return emf.createEntityManager();    
    }

    public void create(Disciplina disciplina) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(disciplina);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Disciplina disciplina) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            disciplina = em.merge(disciplina);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = disciplina.getId();
                if (findDisciplina(id) == null) {
                    throw new NonexistentEntityException("The disciplina with id " + id + " no longer exists.");
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
            Disciplina disciplina;
            try {
                disciplina = em.getReference(Disciplina.class, id);
                disciplina.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The disciplina with id " + id + " no longer exists.", enfe);
            }
            em.remove(disciplina);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Disciplina> findDisciplinaEntities() {
        return findDisciplinaEntities(true, -1, -1);
    }

    public List<Disciplina> findDisciplinaEntities(int maxResults, int firstResult) {
        return findDisciplinaEntities(false, maxResults, firstResult);
    }

    private List<Disciplina> findDisciplinaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Disciplina.class));
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

    public Disciplina findDisciplina(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Disciplina.class, id);
        } finally {
            em.close();
        }
    }

    public int getDisciplinaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Disciplina> rt = cq.from(Disciplina.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<Disciplina> pesquisar(Disciplina disciplina) {
        EntityManager em = getEntityManager();
        StringBuilder sql = 
                new StringBuilder("from Disciplina as c where 1 = 1 ");
        
        if(disciplina.getId()!= null){
            sql.append("and c.id = :id ");
        }
        if(disciplina.getNome() != null && !disciplina.getNome().equals("")){
            sql.append("and c.nome like :nome ");
        }
        
        Query query = em.createQuery(sql.toString());
        
        if(disciplina.getId()!= null){
            query.setParameter("id", disciplina.getId());
        }
        if(disciplina.getNome() != null && !disciplina.getNome().equals("")){
            query.setParameter("nome", "%"+ disciplina.getNome()+"%");
        }
        return query.getResultList();
    }


    public void excluir(Disciplina disciplinaDigitada) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        disciplinaDigitada = em.merge(disciplinaDigitada);
        em.remove(disciplinaDigitada);
        em.getTransaction().commit();
        em.close();
    }
    
}
