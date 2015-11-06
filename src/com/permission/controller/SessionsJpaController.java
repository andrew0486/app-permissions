/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.permission.controller;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.permission.exceptions.NonexistentEntityException;
import com.permission.exceptions.PreexistingEntityException;
import com.permission.model.User;
import com.permission.model.UserSession;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author ahuertas
 */
public class SessionsJpaController implements Serializable {

    public SessionsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(UserSession sessions) throws PreexistingEntityException, NonexistentEntityException, Exception {
        if (sessions.getUser() == null) {
            throw new NonexistentEntityException("Session Error, User null");
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            
            User user = sessions.getUser();
            user = em.getReference(user.getClass(), user.getUserId());
            sessions.setUser(user);
            
            em.persist(sessions);
            
            user.setUserSession(sessions);
            user = em.merge(user);
            
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSessions(sessions.getUsername()) != null) {
                throw new PreexistingEntityException("Sessions " + sessions + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(UserSession sessions) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            UserSession persistentSessions = em.find(UserSession.class, sessions.getUsername());
            User usersListOld = persistentSessions.getUser();
            User usersListNew = sessions.getUser();
            
            usersListNew = em.getReference(usersListNew.getClass(), usersListNew.getUserId());
            
            sessions.setUser(usersListNew);
            sessions = em.merge(sessions);
            
            if (usersListNew != usersListOld) {
                usersListOld.setUserSession(null);
                usersListOld = em.merge(usersListOld);
            }
            
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = sessions.getUsername();
                if (findSessions(id) == null) {
                    throw new NonexistentEntityException("The sessions with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            UserSession sessions;
            try {
                sessions = em.getReference(UserSession.class, id);
                sessions.getUsername();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sessions with id " + id + " no longer exists.", enfe);
            }
            User usersListUsers = sessions.getUser();
            usersListUsers.setUserSession(null);
            usersListUsers = em.merge(usersListUsers);
                
            em.remove(sessions);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<UserSession> findSessionsEntities() {
        return findSessionsEntities(true, -1, -1);
    }

    public List<UserSession> findSessionsEntities(int maxResults, int firstResult) {
        return findSessionsEntities(false, maxResults, firstResult);
    }

    private List<UserSession> findSessionsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery<Object> cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(UserSession.class));
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

    public UserSession findSessions(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(UserSession.class, id);
        } finally {
            em.close();
        }
    }

    public int getSessionsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery<Object> cq = em.getCriteriaBuilder().createQuery();
            Root<UserSession> rt = cq.from(UserSession.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
