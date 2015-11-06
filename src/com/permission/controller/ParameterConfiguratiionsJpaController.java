/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.permission.controller;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.permission.exceptions.NonexistentEntityException;
import com.permission.model.ParameterConfiguratiions;
import com.permission.model.User;

/**
 *
 * @author ahuertas
 */
public class ParameterConfiguratiionsJpaController implements Serializable {

    public ParameterConfiguratiionsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ParameterConfiguratiions parameterConfiguratiions) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User userUpdater = parameterConfiguratiions.getUserUpdater();
            if (userUpdater != null) {
                userUpdater = em.getReference(userUpdater.getClass(), userUpdater.getUserId());
                parameterConfiguratiions.setUserUpdater(userUpdater);
            }
            em.persist(parameterConfiguratiions);
            if (userUpdater != null) {
                userUpdater.getUserUpdaterParameterConfiguratiionsList().add(parameterConfiguratiions);
                userUpdater = em.merge(userUpdater);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ParameterConfiguratiions parameterConfiguratiions) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ParameterConfiguratiions persistentParameterConfiguratiions = em.find(ParameterConfiguratiions.class, parameterConfiguratiions.getParamId());
            User userUpdaterOld = persistentParameterConfiguratiions.getUserUpdater();
            User userUpdaterNew = parameterConfiguratiions.getUserUpdater();
            if (userUpdaterNew != null) {
                userUpdaterNew = em.getReference(userUpdaterNew.getClass(), userUpdaterNew.getUserId());
                parameterConfiguratiions.setUserUpdater(userUpdaterNew);
            }
            parameterConfiguratiions = em.merge(parameterConfiguratiions);
            if (userUpdaterOld != null && !userUpdaterOld.equals(userUpdaterNew)) {
                userUpdaterOld.getUserUpdaterParameterConfiguratiionsList().remove(parameterConfiguratiions);
                userUpdaterOld = em.merge(userUpdaterOld);
            }
            if (userUpdaterNew != null && !userUpdaterNew.equals(userUpdaterOld)) {
                userUpdaterNew.getUserUpdaterParameterConfiguratiionsList().add(parameterConfiguratiions);
                userUpdaterNew = em.merge(userUpdaterNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = parameterConfiguratiions.getParamId();
                if (findParameterConfiguratiions(id) == null) {
                    throw new NonexistentEntityException("The parameterConfiguratiions with id " + id + " no longer exists.");
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
            ParameterConfiguratiions parameterConfiguratiions;
            try {
                parameterConfiguratiions = em.getReference(ParameterConfiguratiions.class, id);
                parameterConfiguratiions.getParamId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The parameterConfiguratiions with id " + id + " no longer exists.", enfe);
            }
            User userUpdater = parameterConfiguratiions.getUserUpdater();
            if (userUpdater != null) {
                userUpdater.getUserUpdaterParameterConfiguratiionsList().remove(parameterConfiguratiions);
                userUpdater = em.merge(userUpdater);
            }
            em.remove(parameterConfiguratiions);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ParameterConfiguratiions> findParameterConfiguratiionsEntities() {
        return findParameterConfiguratiionsEntities(true, -1, -1);
    }

    public List<ParameterConfiguratiions> findParameterConfiguratiionsEntities(int maxResults, int firstResult) {
        return findParameterConfiguratiionsEntities(false, maxResults, firstResult);
    }

    private List<ParameterConfiguratiions> findParameterConfiguratiionsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery<Object> cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ParameterConfiguratiions.class));
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

    public ParameterConfiguratiions findParameterConfiguratiions(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ParameterConfiguratiions.class, id);
        } finally {
            em.close();
        }
    }

    public int getParameterConfiguratiionsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery<Object> cq = em.getCriteriaBuilder().createQuery();
            Root<ParameterConfiguratiions> rt = cq.from(ParameterConfiguratiions.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
