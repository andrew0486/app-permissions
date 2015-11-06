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
public class RolesJpaController implements Serializable {

    public RolesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Roles roles) {
        if (roles.getRollPermissionsList() == null) {
            roles.setRollPermissionsList(new ArrayList<RollPermission>());
        }
        if (roles.getUsersList() == null) {
            roles.setUsersList(new ArrayList<User>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User userCreator = roles.getUserCreator();
            if (userCreator != null) {
                userCreator = em.getReference(userCreator.getClass(), userCreator.getUserId());
                roles.setUserCreator(userCreator);
            }
            User userUpdater = roles.getUserUpdater();
            if (userUpdater != null) {
                userUpdater = em.getReference(userUpdater.getClass(), userUpdater.getUserId());
                roles.setUserUpdater(userUpdater);
            }
            List<RollPermission> attachedRollPermissionsList = new ArrayList<RollPermission>();
            for (RollPermission rollPermissionsListRollPermissionsToAttach : roles.getRollPermissionsList()) {
                rollPermissionsListRollPermissionsToAttach = em.getReference(rollPermissionsListRollPermissionsToAttach.getClass(), rollPermissionsListRollPermissionsToAttach.getRollPermId());
                attachedRollPermissionsList.add(rollPermissionsListRollPermissionsToAttach);
            }
            roles.setRollPermissionsList(attachedRollPermissionsList);
            List<User> attachedUsersList = new ArrayList<User>();
            for (User usersListUsersToAttach : roles.getUsersList()) {
                usersListUsersToAttach = em.getReference(usersListUsersToAttach.getClass(), usersListUsersToAttach.getUserId());
                attachedUsersList.add(usersListUsersToAttach);
            }
            roles.setUsersList(attachedUsersList);
            em.persist(roles);
            if (userCreator != null) {
                userCreator.getRolesList().add(roles);
                userCreator = em.merge(userCreator);
            }
            if (userUpdater != null) {
                userUpdater.getRolesList().add(roles);
                userUpdater = em.merge(userUpdater);
            }
            for (RollPermission rollPermissionsListRollPermissions : roles.getRollPermissionsList()) {
                rollPermissionsListRollPermissions.getRolesList().add(roles);
                rollPermissionsListRollPermissions = em.merge(rollPermissionsListRollPermissions);
            }
            for (User usersListUsers : roles.getUsersList()) {
                usersListUsers.getRolesList().add(roles);
                usersListUsers = em.merge(usersListUsers);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Roles roles) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Roles persistentRoles = em.find(Roles.class, roles.getRollId());
            User userCreatorOld = persistentRoles.getUserCreator();
            User userCreatorNew = roles.getUserCreator();
            User userUpdaterOld = persistentRoles.getUserUpdater();
            User userUpdaterNew = roles.getUserUpdater();
            List<RollPermission> rollPermissionsListOld = persistentRoles.getRollPermissionsList();
            List<RollPermission> rollPermissionsListNew = roles.getRollPermissionsList();
            List<User> usersListOld = persistentRoles.getUsersList();
            List<User> usersListNew = roles.getUsersList();
            if (userCreatorNew != null) {
                userCreatorNew = em.getReference(userCreatorNew.getClass(), userCreatorNew.getUserId());
                roles.setUserCreator(userCreatorNew);
            }
            if (userUpdaterNew != null) {
                userUpdaterNew = em.getReference(userUpdaterNew.getClass(), userUpdaterNew.getUserId());
                roles.setUserUpdater(userUpdaterNew);
            }
            List<RollPermission> attachedRollPermissionsListNew = new ArrayList<RollPermission>();
            for (RollPermission rollPermissionsListNewRollPermissionsToAttach : rollPermissionsListNew) {
                rollPermissionsListNewRollPermissionsToAttach = em.getReference(rollPermissionsListNewRollPermissionsToAttach.getClass(), rollPermissionsListNewRollPermissionsToAttach.getRollPermId());
                attachedRollPermissionsListNew.add(rollPermissionsListNewRollPermissionsToAttach);
            }
            rollPermissionsListNew = attachedRollPermissionsListNew;
            roles.setRollPermissionsList(rollPermissionsListNew);
            List<User> attachedUsersListNew = new ArrayList<User>();
            for (User usersListNewUsersToAttach : usersListNew) {
                usersListNewUsersToAttach = em.getReference(usersListNewUsersToAttach.getClass(), usersListNewUsersToAttach.getUserId());
                attachedUsersListNew.add(usersListNewUsersToAttach);
            }
            usersListNew = attachedUsersListNew;
            roles.setUsersList(usersListNew);
            roles = em.merge(roles);
            if (userCreatorOld != null && !userCreatorOld.equals(userCreatorNew)) {
                userCreatorOld.getRolesList().remove(roles);
                userCreatorOld = em.merge(userCreatorOld);
            }
            if (userCreatorNew != null && !userCreatorNew.equals(userCreatorOld)) {
                userCreatorNew.getRolesList().add(roles);
                userCreatorNew = em.merge(userCreatorNew);
            }
            if (userUpdaterOld != null && !userUpdaterOld.equals(userUpdaterNew)) {
                userUpdaterOld.getRolesList().remove(roles);
                userUpdaterOld = em.merge(userUpdaterOld);
            }
            if (userUpdaterNew != null && !userUpdaterNew.equals(userUpdaterOld)) {
                userUpdaterNew.getRolesList().add(roles);
                userUpdaterNew = em.merge(userUpdaterNew);
            }
            for (RollPermission rollPermissionsListOldRollPermissions : rollPermissionsListOld) {
                if (!rollPermissionsListNew.contains(rollPermissionsListOldRollPermissions)) {
                    rollPermissionsListOldRollPermissions.getRolesList().remove(roles);
                    rollPermissionsListOldRollPermissions = em.merge(rollPermissionsListOldRollPermissions);
                }
            }
            for (RollPermission rollPermissionsListNewRollPermissions : rollPermissionsListNew) {
                if (!rollPermissionsListOld.contains(rollPermissionsListNewRollPermissions)) {
                    rollPermissionsListNewRollPermissions.getRolesList().add(roles);
                    rollPermissionsListNewRollPermissions = em.merge(rollPermissionsListNewRollPermissions);
                }
            }
            for (User usersListOldUsers : usersListOld) {
                if (!usersListNew.contains(usersListOldUsers)) {
                    usersListOldUsers.getRolesList().remove(roles);
                    usersListOldUsers = em.merge(usersListOldUsers);
                }
            }
            for (User usersListNewUsers : usersListNew) {
                if (!usersListOld.contains(usersListNewUsers)) {
                    usersListNewUsers.getRolesList().add(roles);
                    usersListNewUsers = em.merge(usersListNewUsers);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = roles.getRollId();
                if (findRoles(id) == null) {
                    throw new NonexistentEntityException("The roles with id " + id + " no longer exists.");
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
            Roles roles;
            try {
                roles = em.getReference(Roles.class, id);
                roles.getRollId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The roles with id " + id + " no longer exists.", enfe);
            }
            User userCreator = roles.getUserCreator();
            if (userCreator != null) {
                userCreator.getRolesList().remove(roles);
                userCreator = em.merge(userCreator);
            }
            User userUpdater = roles.getUserUpdater();
            if (userUpdater != null) {
                userUpdater.getRolesList().remove(roles);
                userUpdater = em.merge(userUpdater);
            }
            List<RollPermission> rollPermissionsList = roles.getRollPermissionsList();
            for (RollPermission rollPermissionsListRollPermissions : rollPermissionsList) {
                rollPermissionsListRollPermissions.getRolesList().remove(roles);
                rollPermissionsListRollPermissions = em.merge(rollPermissionsListRollPermissions);
            }
            List<User> usersList = roles.getUsersList();
            for (User usersListUsers : usersList) {
                usersListUsers.getRolesList().remove(roles);
                usersListUsers = em.merge(usersListUsers);
            }
            em.remove(roles);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Roles> findRolesEntities() {
        return findRolesEntities(true, -1, -1);
    }

    public List<Roles> findRolesEntities(int maxResults, int firstResult) {
        return findRolesEntities(false, maxResults, firstResult);
    }

    private List<Roles> findRolesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery<Object> cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Roles.class));
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

    public Roles findRoles(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Roles.class, id);
        } finally {
            em.close();
        }
    }

    public int getRolesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery<Object> cq = em.getCriteriaBuilder().createQuery();
            Root<Roles> rt = cq.from(Roles.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
