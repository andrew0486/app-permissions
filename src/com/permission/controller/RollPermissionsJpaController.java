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
import com.permission.model.Roles;
import com.permission.model.RollPermission;
import com.permission.model.User;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author ahuertas
 */
public class RollPermissionsJpaController implements Serializable {

    public RollPermissionsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(RollPermission rollPermissions) {
        if (rollPermissions.getRolesList() == null) {
            rollPermissions.setRolesList(new ArrayList<Roles>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User userCreator = rollPermissions.getUserCreator();
            if (userCreator != null) {
                userCreator = em.getReference(userCreator.getClass(), userCreator.getUserId());
                rollPermissions.setUserCreator(userCreator);
            }
            User userUpdater = rollPermissions.getUserUpdater();
            if (userUpdater != null) {
                userUpdater = em.getReference(userUpdater.getClass(), userUpdater.getUserId());
                rollPermissions.setUserUpdater(userUpdater);
            }
            List<Roles> attachedRolesList = new ArrayList<Roles>();
            for (Roles rolesListRolesToAttach : rollPermissions.getRolesList()) {
                rolesListRolesToAttach = em.getReference(rolesListRolesToAttach.getClass(), rolesListRolesToAttach.getRollId());
                attachedRolesList.add(rolesListRolesToAttach);
            }
            rollPermissions.setRolesList(attachedRolesList);
            em.persist(rollPermissions);
            if (userCreator != null) {
                userCreator.getUserCreatorRollPermissionsList().add(rollPermissions);
                userCreator = em.merge(userCreator);
            }
            if (userUpdater != null) {
                userUpdater.getUserUpdaterRollPermissionsList().add(rollPermissions);
                userUpdater = em.merge(userUpdater);
            }
            for (Roles rolesListRoles : rollPermissions.getRolesList()) {
                rolesListRoles.getRollPermissionsList().add(rollPermissions);
                rolesListRoles = em.merge(rolesListRoles);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(RollPermission rollPermissions) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            RollPermission persistentRollPermissions = em.find(RollPermission.class, rollPermissions.getRollPermId());
            User userCreatorOld = persistentRollPermissions.getUserCreator();
            User userCreatorNew = rollPermissions.getUserCreator();
            User userUpdaterOld = persistentRollPermissions.getUserUpdater();
            User userUpdaterNew = rollPermissions.getUserUpdater();
            List<Roles> rolesListOld = persistentRollPermissions.getRolesList();
            List<Roles> rolesListNew = rollPermissions.getRolesList();
            if (userCreatorNew != null) {
                userCreatorNew = em.getReference(userCreatorNew.getClass(), userCreatorNew.getUserId());
                rollPermissions.setUserCreator(userCreatorNew);
            }
            if (userUpdaterNew != null) {
                userUpdaterNew = em.getReference(userUpdaterNew.getClass(), userUpdaterNew.getUserId());
                rollPermissions.setUserUpdater(userUpdaterNew);
            }
            List<Roles> attachedRolesListNew = new ArrayList<Roles>();
            for (Roles rolesListNewRolesToAttach : rolesListNew) {
                rolesListNewRolesToAttach = em.getReference(rolesListNewRolesToAttach.getClass(), rolesListNewRolesToAttach.getRollId());
                attachedRolesListNew.add(rolesListNewRolesToAttach);
            }
            rolesListNew = attachedRolesListNew;
            rollPermissions.setRolesList(rolesListNew);
            rollPermissions = em.merge(rollPermissions);
            if (userCreatorOld != null && !userCreatorOld.equals(userCreatorNew)) {
                userCreatorOld.getUserCreatorRollPermissionsList().remove(rollPermissions);
                userCreatorOld = em.merge(userCreatorOld);
            }
            if (userCreatorNew != null && !userCreatorNew.equals(userCreatorOld)) {
                userCreatorNew.getUserCreatorRollPermissionsList().add(rollPermissions);
                userCreatorNew = em.merge(userCreatorNew);
            }
            if (userUpdaterOld != null && !userUpdaterOld.equals(userUpdaterNew)) {
                userUpdaterOld.getUserUpdaterRollPermissionsList().remove(rollPermissions);
                userUpdaterOld = em.merge(userUpdaterOld);
            }
            if (userUpdaterNew != null && !userUpdaterNew.equals(userUpdaterOld)) {
                userUpdaterNew.getUserUpdaterRollPermissionsList().add(rollPermissions);
                userUpdaterNew = em.merge(userUpdaterNew);
            }
            for (Roles rolesListOldRoles : rolesListOld) {
                if (!rolesListNew.contains(rolesListOldRoles)) {
                    rolesListOldRoles.getRollPermissionsList().remove(rollPermissions);
                    rolesListOldRoles = em.merge(rolesListOldRoles);
                }
            }
            for (Roles rolesListNewRoles : rolesListNew) {
                if (!rolesListOld.contains(rolesListNewRoles)) {
                    rolesListNewRoles.getRollPermissionsList().add(rollPermissions);
                    rolesListNewRoles = em.merge(rolesListNewRoles);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = rollPermissions.getRollPermId();
                if (findRollPermissions(id) == null) {
                    throw new NonexistentEntityException("The rollPermissions with id " + id + " no longer exists.");
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
            RollPermission rollPermissions;
            try {
                rollPermissions = em.getReference(RollPermission.class, id);
                rollPermissions.getRollPermId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The rollPermissions with id " + id + " no longer exists.", enfe);
            }
            User userCreator = rollPermissions.getUserCreator();
            if (userCreator != null) {
                userCreator.getUserCreatorRollPermissionsList().remove(rollPermissions);
                userCreator = em.merge(userCreator);
            }
            User userUpdater = rollPermissions.getUserUpdater();
            if (userUpdater != null) {
                userUpdater.getUserUpdaterRollPermissionsList().remove(rollPermissions);
                userUpdater = em.merge(userUpdater);
            }
            List<Roles> rolesList = rollPermissions.getRolesList();
            for (Roles rolesListRoles : rolesList) {
                rolesListRoles.getRollPermissionsList().remove(rollPermissions);
                rolesListRoles = em.merge(rolesListRoles);
            }
            em.remove(rollPermissions);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<RollPermission> findRollPermissionsEntities() {
        return findRollPermissionsEntities(true, -1, -1);
    }

    public List<RollPermission> findRollPermissionsEntities(int maxResults, int firstResult) {
        return findRollPermissionsEntities(false, maxResults, firstResult);
    }

    private List<RollPermission> findRollPermissionsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery<Object> cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RollPermission.class));
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

    public RollPermission findRollPermissions(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RollPermission.class, id);
        } finally {
            em.close();
        }
    }

    public int getRollPermissionsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery<Object> cq = em.getCriteriaBuilder().createQuery();
            Root<RollPermission> rt = cq.from(RollPermission.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
