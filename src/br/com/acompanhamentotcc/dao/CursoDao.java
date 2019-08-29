/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.acompanhamentotcc.dao;

import br.com.acompanhamentotcc.dao.exceptions.NonexistentEntityException;
import br.com.acompanhamentotcc.model.Curso;
import java.io.Serializable;
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
 * @author LEANDRO
 */
public class CursoDao implements Serializable {

    private EntityManagerFactory emf;

    public EntityManager getEntityManager() {
        if(emf == null){
            this.emf = Persistence.
                    createEntityManagerFactory("AcompanhamentoTCCPU");
        }
        return emf.createEntityManager(); 
    }

    public void create(Curso curso) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            if(curso.getId() != null){
                curso = em.merge(curso);
            }
            em.persist(curso);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Curso curso) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            curso = em.merge(curso);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = curso.getId();
                if (findCurso(id) == null) {
                    throw new NonexistentEntityException("The curso with id " + id + " no longer exists.");
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
            Curso curso;
            try {
                curso = em.getReference(Curso.class, id);
                curso.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The curso with id " + id + " no longer exists.", enfe);
            }
            em.remove(curso);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    public void excluir(Curso curso) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        curso = em.merge(curso);
        em.remove(curso);
        em.getTransaction().commit();
        em.close();
    }

    public List<Curso> findCursoEntities() {
        return findCursoEntities(true, -1, -1);
    }

    public List<Curso> findCursoEntities(int maxResults, int firstResult) {
        return findCursoEntities(false, maxResults, firstResult);
    }

    private List<Curso> findCursoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Curso.class));
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

    public Curso findCurso(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Curso.class, id);
        } finally {
            em.close();
        }
    }

    public int getCursoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Curso> rt = cq.from(Curso.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<Curso> pesquisar(Curso curso) {
        EntityManager em = getEntityManager();
        StringBuilder sql = 
                new StringBuilder("from Curso as c where 1 = 1 ");
        
        if(curso.getId()!= null){
            sql.append("and c.id = :id ");
        }
        if(curso.getNome() != null && !curso.getNome().equals("")){
            sql.append("and c.nome like :nome ");
        }
        
        Query query = em.createQuery(sql.toString());
        
        if(curso.getId()!= null){
            query.setParameter("id", curso.getId());
        }
        if(curso.getNome() != null && !curso.getNome().equals("")){
            query.setParameter("nome", "%"+curso.getNome()+"%");
        }
        return query.getResultList();
    }
}
