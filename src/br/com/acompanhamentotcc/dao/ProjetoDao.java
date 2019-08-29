/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.acompanhamentotcc.dao;

import br.com.acompanhamentotcc.dao.exceptions.NonexistentEntityException;
import br.com.acompanhamentotcc.model.Projeto;

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
public class ProjetoDao implements Serializable {

    private EntityManagerFactory emf;

    public EntityManager getEntityManager() {
        if(emf == null){
            this.emf = Persistence.
                    createEntityManagerFactory("AcompanhamentoTCCPU");
        }
        return emf.createEntityManager();
    }

    public void create(Projeto projeto) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(projeto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Projeto projeto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            projeto = em.merge(projeto);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = projeto.getId();
                if (findProjeto(id) == null) {
                    throw new NonexistentEntityException("The projeto with id " + id + " no longer exists.");
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
            Projeto projeto;
            try {
                projeto = em.getReference(Projeto.class, id);
                projeto.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The projeto with id " + id + " no longer exists.", enfe);
            }
            em.remove(projeto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Projeto> findProjetoEntities() {
        return findProjetoEntities(true, -1, -1);
    }

    public List<Projeto> findProjetoEntities(int maxResults, int firstResult) {
        return findProjetoEntities(false, maxResults, firstResult);
    }

    private List<Projeto> findProjetoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Projeto.class));
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

    public Projeto findProjeto(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Projeto.class, id);
        } finally {
            em.close();
        }
    }

    public int getProjetoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Projeto> rt = cq.from(Projeto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    
    public List<Projeto> pesquisar(Projeto projeto) {
        EntityManager em = getEntityManager();
        StringBuilder sql = 
                new StringBuilder("from Projeto as c where 1 = 1 ");
        
        if(projeto.getId()!= null){
            sql.append("and c.id = :id ");
        }
        if(projeto.getDescricao() != null && !projeto.getDescricao().equals("")){
            sql.append("and c.descricao like :descricao ");
        }
        
        Query query = em.createQuery(sql.toString());
        
        if(projeto.getId()!= null){
            query.setParameter("id", projeto.getId());
        }
        if(projeto.getDescricao() != null && !projeto.getDescricao().equals("")){
            query.setParameter("descricao", "%"+ projeto.getDescricao()+"%");
        }
        return query.getResultList();
    }
    
    
        public void excluir(Projeto projetoDigitado) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        projetoDigitado = em.merge(projetoDigitado);
        em.remove(projetoDigitado);
        em.getTransaction().commit();
        em.close();
    }
    
}
