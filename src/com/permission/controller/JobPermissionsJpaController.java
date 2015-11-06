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
import com.permission.model.JobPermission;
import com.permission.model.User;

/**
 *
 * @author ahuertas
 */
public class JobPermissionsJpaController implements Serializable {

    public JobPermissionsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(JobPermission jobPermissions) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User userUpdater = jobPermissions.getUserUpdater();
            if (userUpdater != null) {
                userUpdater = em.getReference(userUpdater.getClass(), userUpdater.getUserId());
                jobPermissions.setUserUpdater(userUpdater);
            }
            User userCreator = jobPermissions.getUserCreator();
            if (userCreator != null) {
                userCreator = em.getReference(userCreator.getClass(), userCreator.getUserId());
                jobPermissions.setUserCreator(userCreator);
            }
            User userId = jobPermissions.getUserId();
            if (userId != null) {
                userId = em.getReference(userId.getClass(), userId.getUserId());
                jobPermissions.setUserId(userId);
            }
            em.persist(jobPermissions);
            if (userUpdater != null) {
                userUpdater.getJobPermissionsList().add(jobPermissions);
                userUpdater = em.merge(userUpdater);
            }
            if (userCreator != null) {
                userCreator.getJobPermissionsList().add(jobPermissions);
                userCreator = em.merge(userCreator);
            }
            if (userId != null) {
                userId.getJobPermissionsList().add(jobPermissions);
                userId = em.merge(userId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(JobPermission jobPermissions) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            JobPermission persistentJobPermissions = em.find(JobPermission.class, jobPermissions.getPermId());
            User userUpdaterOld = persistentJobPermissions.getUserUpdater();
            User userUpdaterNew = jobPermissions.getUserUpdater();
            User userCreatorOld = persistentJobPermissions.getUserCreator();
            User userCreatorNew = jobPermissions.getUserCreator();
            User userIdOld = persistentJobPermissions.getUserId();
            User userIdNew = jobPermissions.getUserId();
            if (userUpdaterNew != null) {
                userUpdaterNew = em.getReference(userUpdaterNew.getClass(), userUpdaterNew.getUserId());
                jobPermissions.setUserUpdater(userUpdaterNew);
            }
            if (userCreatorNew != null) {
                userCreatorNew = em.getReference(userCreatorNew.getClass(), userCreatorNew.getUserId());
                jobPermissions.setUserCreator(userCreatorNew);
            }
            if (userIdNew != null) {
                userIdNew = em.getReference(userIdNew.getClass(), userIdNew.getUserId());
                jobPermissions.setUserId(userIdNew);
            }
            jobPermissions = em.merge(jobPermissions);
            if (userUpdaterOld != null && !userUpdaterOld.equals(userUpdaterNew)) {
                userUpdaterOld.getJobPermissionsList().remove(jobPermissions);
                userUpdaterOld = em.merge(userUpdaterOld);
            }
            if (userUpdaterNew != null && !userUpdaterNew.equals(userUpdaterOld)) {
                userUpdaterNew.getJobPermissionsList().add(jobPermissions);
                userUpdaterNew = em.merge(userUpdaterNew);
            }
            if (userCreatorOld != null && !userCreatorOld.equals(userCreatorNew)) {
                userCreatorOld.getJobPermissionsList().remove(jobPermissions);
                userCreatorOld = em.merge(userCreatorOld);
            }
            if (userCreatorNew != null && !userCreatorNew.equals(userCreatorOld)) {
                userCreatorNew.getJobPermissionsList().add(jobPermissions);
                userCreatorNew = em.merge(userCreatorNew);
            }
            if (userIdOld != null && !userIdOld.equals(userIdNew)) {
                userIdOld.getJobPermissionsList().remove(jobPermissions);
                userIdOld = em.merge(userIdOld);
            }
            if (userIdNew != null && !userIdNew.equals(userIdOld)) {
                userIdNew.getJobPermissionsList().add(jobPermissions);
                userIdNew = em.merge(userIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = jobPermissions.getPermId();
                if (findJobPermissions(id) == null) {
                    throw new NonexistentEntityException("The jobPermissions with id " + id + " no longer exists.");
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
            JobPermission jobPermissions;
            try {
                jobPermissions = em.getReference(JobPermission.class, id);
                jobPermissions.getPermId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The jobPermissions with id " + id + " no longer exists.", enfe);
            }
            User userUpdater = jobPermissions.getUserUpdater();
            if (userUpdater != null) {
                userUpdater.getJobPermissionsList().remove(jobPermissions);
                userUpdater = em.merge(userUpdater);
            }
            User userCreator = jobPermissions.getUserCreator();
            if (userCreator != null) {
                userCreator.getJobPermissionsList().remove(jobPermissions);
                userCreator = em.merge(userCreator);
            }
            User userId = jobPermissions.getUserId();
            if (userId != null) {
                userId.getJobPermissionsList().remove(jobPermissions);
                userId = em.merge(userId);
            }
            em.remove(jobPermissions);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<JobPermission> findJobPermissionsEntities() {
        return findJobPermissionsEntities(true, -1, -1);
    }

    public List<JobPermission> findJobPermissionsEntities(int maxResults, int firstResult) {
        return findJobPermissionsEntities(false, maxResults, firstResult);
    }

    private List<JobPermission> findJobPermissionsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery<Object> cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(JobPermission.class));
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

    public JobPermission findJobPermissions(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(JobPermission.class, id);
        } finally {
            em.close();
        }
    }

    public int getJobPermissionsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery<Object> cq = em.getCriteriaBuilder().createQuery();
            Root<JobPermission> rt = cq.from(JobPermission.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
