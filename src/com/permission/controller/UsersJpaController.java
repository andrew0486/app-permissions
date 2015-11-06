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

import com.permission.exceptions.IllegalOrphanException;
import com.permission.exceptions.NonexistentEntityException;
import com.permission.model.JobPermission;
import com.permission.model.ParameterConfiguratiions;
import com.permission.model.Roles;
import com.permission.model.RollPermission;
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
public class UsersJpaController implements Serializable {

	public UsersJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(User users) {
        if (users.getRolesList() == null) {
            users.setRolesList(new ArrayList<Roles>());
        }
        if (users.getUserCreatorList() == null) {
            users.setUserCreatorList(new ArrayList<User>());
        }
        if (users.getManagerList() == null) {
            users.setManagerList(new ArrayList<User>());
        }
        if (users.getUserUpdaterList() == null) {
            users.setUserUpdaterList(new ArrayList<User>());
        }
        if (users.getUserCreatorRollPermissionsList() == null) {
            users.setUserCreatorRollPermissionsList(new ArrayList<RollPermission>());
        }
        if (users.getUserUpdaterRollPermissionsList() == null) {
            users.setUserUpdaterRollPermissionsList(new ArrayList<RollPermission>());
        }
        if (users.getUserUpdaterJobPermissionsList() == null) {
            users.setUserUpdaterJobPermissionsList(new ArrayList<JobPermission>());
        }
        if (users.getUserCreatorJobPermissionsList() == null) {
            users.setUserCreatorJobPermissionsList(new ArrayList<JobPermission>());
        }
        if (users.getJobPermissionsList() == null) {
            users.setJobPermissionsList(new ArrayList<JobPermission>());
        }
        if (users.getUserCreatorRolesList() == null) {
            users.setUserCreatorRolesList(new ArrayList<Roles>());
        }
        if (users.getUserUpdaterRolesList() == null) {
            users.setUserUpdaterRolesList(new ArrayList<Roles>());
        }
        if (users.getUserUpdaterParameterConfiguratiionsList() == null) {
            users.setUserUpdaterParameterConfiguratiionsList(new ArrayList<ParameterConfiguratiions>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User userCreator = users.getUserCreator();
            if (userCreator != null) {
                userCreator = em.getReference(userCreator.getClass(), userCreator.getUserId());
                users.setUserCreator(userCreator);
            }
            UserSession username = users.getUserSession();
            if (username != null) {
                username = em.getReference(username.getClass(), username.getUsername());
                users.setUserSession(username);
            }
            User manager = users.getManager();
            if (manager != null) {
                manager = em.getReference(manager.getClass(), manager.getUserId());
                users.setManager(manager);
            }
            User userUpdater = users.getUserUpdater();
            if (userUpdater != null) {
                userUpdater = em.getReference(userUpdater.getClass(), userUpdater.getUserId());
                users.setUserUpdater(userUpdater);
            }
            List<Roles> attachedRolesList = new ArrayList<Roles>();
            for (Roles rolesListRolesToAttach : users.getRolesList()) {
                rolesListRolesToAttach = em.getReference(rolesListRolesToAttach.getClass(), rolesListRolesToAttach.getRollId());
                attachedRolesList.add(rolesListRolesToAttach);
            }
            users.setRolesList(attachedRolesList);
            List<User> attachedUsersList = new ArrayList<User>();
            for (User usersListUsersToAttach : users.getUserCreatorList()) {
                usersListUsersToAttach = em.getReference(usersListUsersToAttach.getClass(), usersListUsersToAttach.getUserId());
                attachedUsersList.add(usersListUsersToAttach);
            }
            users.setUserCreatorList(attachedUsersList);
            List<User> attachedUsersList1 = new ArrayList<User>();
            for (User usersList1UsersToAttach : users.getManagerList()) {
                usersList1UsersToAttach = em.getReference(usersList1UsersToAttach.getClass(), usersList1UsersToAttach.getUserId());
                attachedUsersList1.add(usersList1UsersToAttach);
            }
            users.setManagerList(attachedUsersList1);
            List<User> attachedUsersList2 = new ArrayList<User>();
            for (User usersList2UsersToAttach : users.getUserUpdaterList()) {
                usersList2UsersToAttach = em.getReference(usersList2UsersToAttach.getClass(), usersList2UsersToAttach.getUserId());
                attachedUsersList2.add(usersList2UsersToAttach);
            }
            users.setUserUpdaterList(attachedUsersList2);
            List<RollPermission> attachedRollPermissionsList = new ArrayList<RollPermission>();
            for (RollPermission rollPermissionsListRollPermissionsToAttach : users.getUserCreatorRollPermissionsList()) {
                rollPermissionsListRollPermissionsToAttach = em.getReference(rollPermissionsListRollPermissionsToAttach.getClass(), rollPermissionsListRollPermissionsToAttach.getRollPermId());
                attachedRollPermissionsList.add(rollPermissionsListRollPermissionsToAttach);
            }
            users.setUserCreatorRollPermissionsList(attachedRollPermissionsList);
            List<RollPermission> attachedRollPermissionsList1 = new ArrayList<RollPermission>();
            for (RollPermission rollPermissionsList1RollPermissionsToAttach : users.getUserUpdaterRollPermissionsList()) {
                rollPermissionsList1RollPermissionsToAttach = em.getReference(rollPermissionsList1RollPermissionsToAttach.getClass(), rollPermissionsList1RollPermissionsToAttach.getRollPermId());
                attachedRollPermissionsList1.add(rollPermissionsList1RollPermissionsToAttach);
            }
            users.setUserUpdaterRollPermissionsList(attachedRollPermissionsList1);
            List<JobPermission> attachedJobPermissionsList = new ArrayList<JobPermission>();
            for (JobPermission jobPermissionsListJobPermissionsToAttach : users.getUserUpdaterJobPermissionsList()) {
                jobPermissionsListJobPermissionsToAttach = em.getReference(jobPermissionsListJobPermissionsToAttach.getClass(), jobPermissionsListJobPermissionsToAttach.getPermId());
                attachedJobPermissionsList.add(jobPermissionsListJobPermissionsToAttach);
            }
            users.setUserUpdaterJobPermissionsList(attachedJobPermissionsList);
            List<JobPermission> attachedJobPermissionsList1 = new ArrayList<JobPermission>();
            for (JobPermission jobPermissionsList1JobPermissionsToAttach : users.getUserCreatorJobPermissionsList()) {
                jobPermissionsList1JobPermissionsToAttach = em.getReference(jobPermissionsList1JobPermissionsToAttach.getClass(), jobPermissionsList1JobPermissionsToAttach.getPermId());
                attachedJobPermissionsList1.add(jobPermissionsList1JobPermissionsToAttach);
            }
            users.setUserCreatorJobPermissionsList(attachedJobPermissionsList1);
            List<JobPermission> attachedJobPermissionsList2 = new ArrayList<JobPermission>();
            for (JobPermission jobPermissionsList2JobPermissionsToAttach : users.getJobPermissionsList()) {
                jobPermissionsList2JobPermissionsToAttach = em.getReference(jobPermissionsList2JobPermissionsToAttach.getClass(), jobPermissionsList2JobPermissionsToAttach.getPermId());
                attachedJobPermissionsList2.add(jobPermissionsList2JobPermissionsToAttach);
            }
            users.setJobPermissionsList(attachedJobPermissionsList2);
            List<Roles> attachedRolesList1 = new ArrayList<Roles>();
            for (Roles rolesList1RolesToAttach : users.getUserCreatorRolesList()) {
                rolesList1RolesToAttach = em.getReference(rolesList1RolesToAttach.getClass(), rolesList1RolesToAttach.getRollId());
                attachedRolesList1.add(rolesList1RolesToAttach);
            }
            users.setUserCreatorRolesList(attachedRolesList1);
            List<Roles> attachedRolesList2 = new ArrayList<Roles>();
            for (Roles rolesList2RolesToAttach : users.getUserUpdaterRolesList()) {
                rolesList2RolesToAttach = em.getReference(rolesList2RolesToAttach.getClass(), rolesList2RolesToAttach.getRollId());
                attachedRolesList2.add(rolesList2RolesToAttach);
            }
            users.setUserUpdaterRolesList(attachedRolesList2);
            List<ParameterConfiguratiions> attachedParameterConfiguratiionsList = new ArrayList<ParameterConfiguratiions>();
            for (ParameterConfiguratiions parameterConfiguratiionsListParameterConfiguratiionsToAttach : users.getUserUpdaterParameterConfiguratiionsList()) {
                parameterConfiguratiionsListParameterConfiguratiionsToAttach = em.getReference(parameterConfiguratiionsListParameterConfiguratiionsToAttach.getClass(), parameterConfiguratiionsListParameterConfiguratiionsToAttach.getParamId());
                attachedParameterConfiguratiionsList.add(parameterConfiguratiionsListParameterConfiguratiionsToAttach);
            }
            users.setUserUpdaterParameterConfiguratiionsList(attachedParameterConfiguratiionsList);
            em.persist(users);
            if (userCreator != null) {
                userCreator.getUserCreatorList().add(users);
                userCreator = em.merge(userCreator);
            }
            if (username != null) {
                username.setUser(users);
                username = em.merge(username);
            }
            if (manager != null) {
                manager.getUserCreatorList().add(users);
                manager = em.merge(manager);
            }
            if (userUpdater != null) {
                userUpdater.getUserUpdaterList().add(users);
                userUpdater = em.merge(userUpdater);
            }
            for (Roles rolesListRoles : users.getRolesList()) {
                rolesListRoles.getUsersList().add(users);
                rolesListRoles = em.merge(rolesListRoles);
            }
            for (User usersListUsers : users.getUserCreatorList()) {
                User oldUserCreatorOfUsersListUsers = usersListUsers.getUserCreator();
                usersListUsers.setUserCreator(users);
                usersListUsers = em.merge(usersListUsers);
                if (oldUserCreatorOfUsersListUsers != null) {
                    oldUserCreatorOfUsersListUsers.getUserCreatorList().remove(usersListUsers);
                    oldUserCreatorOfUsersListUsers = em.merge(oldUserCreatorOfUsersListUsers);
                }
            }
            for (User usersList1Users : users.getManagerList()) {
                User oldManagerOfUsersList1Users = usersList1Users.getManager();
                usersList1Users.setManager(users);
                usersList1Users = em.merge(usersList1Users);
                if (oldManagerOfUsersList1Users != null) {
                    oldManagerOfUsersList1Users.getManagerList().remove(usersList1Users);
                    oldManagerOfUsersList1Users = em.merge(oldManagerOfUsersList1Users);
                }
            }
            for (User usersList2Users : users.getUserUpdaterList()) {
                User oldUserUpdaterOfUsersList2Users = usersList2Users.getUserUpdater();
                usersList2Users.setUserUpdater(users);
                usersList2Users = em.merge(usersList2Users);
                if (oldUserUpdaterOfUsersList2Users != null) {
                    oldUserUpdaterOfUsersList2Users.getUserUpdaterList().remove(usersList2Users);
                    oldUserUpdaterOfUsersList2Users = em.merge(oldUserUpdaterOfUsersList2Users);
                }
            }
            for (RollPermission rollPermissionsListRollPermissions : users.getUserCreatorRollPermissionsList()) {
                User oldUserCreatorOfRollPermissionsListRollPermissions = rollPermissionsListRollPermissions.getUserCreator();
                rollPermissionsListRollPermissions.setUserCreator(users);
                rollPermissionsListRollPermissions = em.merge(rollPermissionsListRollPermissions);
                if (oldUserCreatorOfRollPermissionsListRollPermissions != null) {
                    oldUserCreatorOfRollPermissionsListRollPermissions.getUserCreatorRollPermissionsList().remove(rollPermissionsListRollPermissions);
                    oldUserCreatorOfRollPermissionsListRollPermissions = em.merge(oldUserCreatorOfRollPermissionsListRollPermissions);
                }
            }
            for (RollPermission rollPermissionsList1RollPermissions : users.getUserUpdaterRollPermissionsList()) {
                User oldUserUpdaterOfRollPermissionsList1RollPermissions = rollPermissionsList1RollPermissions.getUserUpdater();
                rollPermissionsList1RollPermissions.setUserUpdater(users);
                rollPermissionsList1RollPermissions = em.merge(rollPermissionsList1RollPermissions);
                if (oldUserUpdaterOfRollPermissionsList1RollPermissions != null) {
                    oldUserUpdaterOfRollPermissionsList1RollPermissions.getUserUpdaterRollPermissionsList().remove(rollPermissionsList1RollPermissions);
                    oldUserUpdaterOfRollPermissionsList1RollPermissions = em.merge(oldUserUpdaterOfRollPermissionsList1RollPermissions);
                }
            }
            for (JobPermission jobPermissionsListJobPermissions : users.getUserUpdaterJobPermissionsList()) {
                User oldUserUpdaterOfJobPermissionsListJobPermissions = jobPermissionsListJobPermissions.getUserUpdater();
                jobPermissionsListJobPermissions.setUserUpdater(users);
                jobPermissionsListJobPermissions = em.merge(jobPermissionsListJobPermissions);
                if (oldUserUpdaterOfJobPermissionsListJobPermissions != null) {
                    oldUserUpdaterOfJobPermissionsListJobPermissions.getUserUpdaterJobPermissionsList().remove(jobPermissionsListJobPermissions);
                    oldUserUpdaterOfJobPermissionsListJobPermissions = em.merge(oldUserUpdaterOfJobPermissionsListJobPermissions);
                }
            }
            for (JobPermission jobPermissionsList1JobPermissions : users.getUserCreatorJobPermissionsList()) {
                User oldUserCreatorOfJobPermissionsList1JobPermissions = jobPermissionsList1JobPermissions.getUserCreator();
                jobPermissionsList1JobPermissions.setUserCreator(users);
                jobPermissionsList1JobPermissions = em.merge(jobPermissionsList1JobPermissions);
                if (oldUserCreatorOfJobPermissionsList1JobPermissions != null) {
                    oldUserCreatorOfJobPermissionsList1JobPermissions.getUserCreatorJobPermissionsList().remove(jobPermissionsList1JobPermissions);
                    oldUserCreatorOfJobPermissionsList1JobPermissions = em.merge(oldUserCreatorOfJobPermissionsList1JobPermissions);
                }
            }
            for (JobPermission jobPermissionsList2JobPermissions : users.getJobPermissionsList()) {
                User oldUserIdOfJobPermissionsList2JobPermissions = jobPermissionsList2JobPermissions.getUserId();
                jobPermissionsList2JobPermissions.setUserId(users);
                jobPermissionsList2JobPermissions = em.merge(jobPermissionsList2JobPermissions);
                if (oldUserIdOfJobPermissionsList2JobPermissions != null) {
                    oldUserIdOfJobPermissionsList2JobPermissions.getJobPermissionsList().remove(jobPermissionsList2JobPermissions);
                    oldUserIdOfJobPermissionsList2JobPermissions = em.merge(oldUserIdOfJobPermissionsList2JobPermissions);
                }
            }
            for (Roles rolesList1Roles : users.getUserCreatorRolesList()) {
                User oldUserCreatorOfRolesList1Roles = rolesList1Roles.getUserCreator();
                rolesList1Roles.setUserCreator(users);
                rolesList1Roles = em.merge(rolesList1Roles);
                if (oldUserCreatorOfRolesList1Roles != null) {
                    oldUserCreatorOfRolesList1Roles.getUserCreatorRolesList().remove(rolesList1Roles);
                    oldUserCreatorOfRolesList1Roles = em.merge(oldUserCreatorOfRolesList1Roles);
                }
            }
            for (Roles rolesList2Roles : users.getUserUpdaterRolesList()) {
                User oldUserUpdaterOfRolesList2Roles = rolesList2Roles.getUserUpdater();
                rolesList2Roles.setUserUpdater(users);
                rolesList2Roles = em.merge(rolesList2Roles);
                if (oldUserUpdaterOfRolesList2Roles != null) {
                    oldUserUpdaterOfRolesList2Roles.getUserUpdaterRolesList().remove(rolesList2Roles);
                    oldUserUpdaterOfRolesList2Roles = em.merge(oldUserUpdaterOfRolesList2Roles);
                }
            }
            for (ParameterConfiguratiions parameterConfiguratiionsListParameterConfiguratiions : users.getUserUpdaterParameterConfiguratiionsList()) {
                User oldUserUpdaterOfParameterConfiguratiionsListParameterConfiguratiions = parameterConfiguratiionsListParameterConfiguratiions.getUserUpdater();
                parameterConfiguratiionsListParameterConfiguratiions.setUserUpdater(users);
                parameterConfiguratiionsListParameterConfiguratiions = em.merge(parameterConfiguratiionsListParameterConfiguratiions);
                if (oldUserUpdaterOfParameterConfiguratiionsListParameterConfiguratiions != null) {
                    oldUserUpdaterOfParameterConfiguratiionsListParameterConfiguratiions.getUserUpdaterParameterConfiguratiionsList().remove(parameterConfiguratiionsListParameterConfiguratiions);
                    oldUserUpdaterOfParameterConfiguratiionsListParameterConfiguratiions = em.merge(oldUserUpdaterOfParameterConfiguratiionsListParameterConfiguratiions);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(User users) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User persistentUsers = em.find(User.class, users.getUserId());
            User userCreatorOld = persistentUsers.getUserCreator();
            User userCreatorNew = users.getUserCreator();
            UserSession usernameOld = persistentUsers.getUserSession();
            UserSession usernameNew = users.getUserSession();
            User managerOld = persistentUsers.getManager();
            User managerNew = users.getManager();
            User userUpdaterOld = persistentUsers.getUserUpdater();
            User userUpdaterNew = users.getUserUpdater();
            List<Roles> rolesListOld = persistentUsers.getRolesList();
            List<Roles> rolesListNew = users.getRolesList();
            List<User> usersListOld = persistentUsers.getUserCreatorList();
            List<User> usersListNew = users.getUserCreatorList();
            List<User> usersList1Old = persistentUsers.getManagerList();
            List<User> usersList1New = users.getManagerList();
            List<User> usersList2Old = persistentUsers.getUserUpdaterList();
            List<User> usersList2New = users.getUserUpdaterList();
            List<RollPermission> rollPermissionsListOld = persistentUsers.getUserCreatorRollPermissionsList();
            List<RollPermission> rollPermissionsListNew = users.getUserCreatorRollPermissionsList();
            List<RollPermission> rollPermissionsList1Old = persistentUsers.getUserUpdaterRollPermissionsList();
            List<RollPermission> rollPermissionsList1New = users.getUserUpdaterRollPermissionsList();
            List<JobPermission> jobPermissionsListOld = persistentUsers.getUserUpdaterJobPermissionsList();
            List<JobPermission> jobPermissionsListNew = users.getUserUpdaterJobPermissionsList();
            List<JobPermission> jobPermissionsList1Old = persistentUsers.getUserCreatorJobPermissionsList();
            List<JobPermission> jobPermissionsList1New = users.getUserCreatorJobPermissionsList();
            List<JobPermission> jobPermissionsList2Old = persistentUsers.getJobPermissionsList();
            List<JobPermission> jobPermissionsList2New = users.getJobPermissionsList();
            List<Roles> rolesList1Old = persistentUsers.getUserCreatorRolesList();
            List<Roles> rolesList1New = users.getUserCreatorRolesList();
            List<Roles> rolesList2Old = persistentUsers.getUserUpdaterRolesList();
            List<Roles> rolesList2New = users.getUserUpdaterRolesList();
            List<ParameterConfiguratiions> parameterConfiguratiionsListOld = persistentUsers.getUserUpdaterParameterConfiguratiionsList();
            List<ParameterConfiguratiions> parameterConfiguratiionsListNew = users.getUserUpdaterParameterConfiguratiionsList();
            List<String> illegalOrphanMessages = null;
            for (RollPermission rollPermissionsListOldRollPermissions : rollPermissionsListOld) {
                if (!rollPermissionsListNew.contains(rollPermissionsListOldRollPermissions)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain RollPermission " + rollPermissionsListOldRollPermissions + " since its userCreator field is not nullable.");
                }
            }
            for (JobPermission jobPermissionsList1OldJobPermissions : jobPermissionsList1Old) {
                if (!jobPermissionsList1New.contains(jobPermissionsList1OldJobPermissions)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain JobPermission " + jobPermissionsList1OldJobPermissions + " since its userCreator field is not nullable.");
                }
            }
            for (JobPermission jobPermissionsList2OldJobPermissions : jobPermissionsList2Old) {
                if (!jobPermissionsList2New.contains(jobPermissionsList2OldJobPermissions)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain JobPermission " + jobPermissionsList2OldJobPermissions + " since its userId field is not nullable.");
                }
            }
            for (Roles rolesList1OldRoles : rolesList1Old) {
                if (!rolesList1New.contains(rolesList1OldRoles)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Roles " + rolesList1OldRoles + " since its userCreator field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (userCreatorNew != null) {
                userCreatorNew = em.getReference(userCreatorNew.getClass(), userCreatorNew.getUserId());
                users.setUserCreator(userCreatorNew);
            }
            if (usernameNew != null) {
                usernameNew = em.getReference(usernameNew.getClass(), usernameNew.getUsername());
                users.setUserSession(usernameNew);
            }
            if (managerNew != null) {
                managerNew = em.getReference(managerNew.getClass(), managerNew.getUserId());
                users.setManager(managerNew);
            }
            if (userUpdaterNew != null) {
                userUpdaterNew = em.getReference(userUpdaterNew.getClass(), userUpdaterNew.getUserId());
                users.setUserUpdater(userUpdaterNew);
            }
            List<Roles> attachedRolesListNew = new ArrayList<Roles>();
            for (Roles rolesListNewRolesToAttach : rolesListNew) {
                rolesListNewRolesToAttach = em.getReference(rolesListNewRolesToAttach.getClass(), rolesListNewRolesToAttach.getRollId());
                attachedRolesListNew.add(rolesListNewRolesToAttach);
            }
            rolesListNew = attachedRolesListNew;
            users.setRolesList(rolesListNew);
            List<User> attachedUsersListNew = new ArrayList<User>();
            for (User usersListNewUsersToAttach : usersListNew) {
                usersListNewUsersToAttach = em.getReference(usersListNewUsersToAttach.getClass(), usersListNewUsersToAttach.getUserId());
                attachedUsersListNew.add(usersListNewUsersToAttach);
            }
            usersListNew = attachedUsersListNew;
            users.setUserCreatorList(usersListNew);
            List<User> attachedUsersList1New = new ArrayList<User>();
            for (User usersList1NewUsersToAttach : usersList1New) {
                usersList1NewUsersToAttach = em.getReference(usersList1NewUsersToAttach.getClass(), usersList1NewUsersToAttach.getUserId());
                attachedUsersList1New.add(usersList1NewUsersToAttach);
            }
            usersList1New = attachedUsersList1New;
            users.setManagerList(usersList1New);
            List<User> attachedUsersList2New = new ArrayList<User>();
            for (User usersList2NewUsersToAttach : usersList2New) {
                usersList2NewUsersToAttach = em.getReference(usersList2NewUsersToAttach.getClass(), usersList2NewUsersToAttach.getUserId());
                attachedUsersList2New.add(usersList2NewUsersToAttach);
            }
            usersList2New = attachedUsersList2New;
            users.setUserUpdaterList(usersList2New);
            List<RollPermission> attachedRollPermissionsListNew = new ArrayList<RollPermission>();
            for (RollPermission rollPermissionsListNewRollPermissionsToAttach : rollPermissionsListNew) {
                rollPermissionsListNewRollPermissionsToAttach = em.getReference(rollPermissionsListNewRollPermissionsToAttach.getClass(), rollPermissionsListNewRollPermissionsToAttach.getRollPermId());
                attachedRollPermissionsListNew.add(rollPermissionsListNewRollPermissionsToAttach);
            }
            rollPermissionsListNew = attachedRollPermissionsListNew;
            users.setUserCreatorRollPermissionsList(rollPermissionsListNew);
            List<RollPermission> attachedRollPermissionsList1New = new ArrayList<RollPermission>();
            for (RollPermission rollPermissionsList1NewRollPermissionsToAttach : rollPermissionsList1New) {
                rollPermissionsList1NewRollPermissionsToAttach = em.getReference(rollPermissionsList1NewRollPermissionsToAttach.getClass(), rollPermissionsList1NewRollPermissionsToAttach.getRollPermId());
                attachedRollPermissionsList1New.add(rollPermissionsList1NewRollPermissionsToAttach);
            }
            rollPermissionsList1New = attachedRollPermissionsList1New;
            users.setUserUpdaterRollPermissionsList(rollPermissionsList1New);
            List<JobPermission> attachedJobPermissionsListNew = new ArrayList<JobPermission>();
            for (JobPermission jobPermissionsListNewJobPermissionsToAttach : jobPermissionsListNew) {
                jobPermissionsListNewJobPermissionsToAttach = em.getReference(jobPermissionsListNewJobPermissionsToAttach.getClass(), jobPermissionsListNewJobPermissionsToAttach.getPermId());
                attachedJobPermissionsListNew.add(jobPermissionsListNewJobPermissionsToAttach);
            }
            jobPermissionsListNew = attachedJobPermissionsListNew;
            users.setUserUpdaterJobPermissionsList(jobPermissionsListNew);
            List<JobPermission> attachedJobPermissionsList1New = new ArrayList<JobPermission>();
            for (JobPermission jobPermissionsList1NewJobPermissionsToAttach : jobPermissionsList1New) {
                jobPermissionsList1NewJobPermissionsToAttach = em.getReference(jobPermissionsList1NewJobPermissionsToAttach.getClass(), jobPermissionsList1NewJobPermissionsToAttach.getPermId());
                attachedJobPermissionsList1New.add(jobPermissionsList1NewJobPermissionsToAttach);
            }
            jobPermissionsList1New = attachedJobPermissionsList1New;
            users.setUserCreatorJobPermissionsList(jobPermissionsList1New);
            List<JobPermission> attachedJobPermissionsList2New = new ArrayList<JobPermission>();
            for (JobPermission jobPermissionsList2NewJobPermissionsToAttach : jobPermissionsList2New) {
                jobPermissionsList2NewJobPermissionsToAttach = em.getReference(jobPermissionsList2NewJobPermissionsToAttach.getClass(), jobPermissionsList2NewJobPermissionsToAttach.getPermId());
                attachedJobPermissionsList2New.add(jobPermissionsList2NewJobPermissionsToAttach);
            }
            jobPermissionsList2New = attachedJobPermissionsList2New;
            users.setJobPermissionsList(jobPermissionsList2New);
            List<Roles> attachedRolesList1New = new ArrayList<Roles>();
            for (Roles rolesList1NewRolesToAttach : rolesList1New) {
                rolesList1NewRolesToAttach = em.getReference(rolesList1NewRolesToAttach.getClass(), rolesList1NewRolesToAttach.getRollId());
                attachedRolesList1New.add(rolesList1NewRolesToAttach);
            }
            rolesList1New = attachedRolesList1New;
            users.setUserCreatorRolesList(rolesList1New);
            List<Roles> attachedRolesList2New = new ArrayList<Roles>();
            for (Roles rolesList2NewRolesToAttach : rolesList2New) {
                rolesList2NewRolesToAttach = em.getReference(rolesList2NewRolesToAttach.getClass(), rolesList2NewRolesToAttach.getRollId());
                attachedRolesList2New.add(rolesList2NewRolesToAttach);
            }
            rolesList2New = attachedRolesList2New;
            users.setUserUpdaterRolesList(rolesList2New);
            List<ParameterConfiguratiions> attachedParameterConfiguratiionsListNew = new ArrayList<ParameterConfiguratiions>();
            for (ParameterConfiguratiions parameterConfiguratiionsListNewParameterConfiguratiionsToAttach : parameterConfiguratiionsListNew) {
                parameterConfiguratiionsListNewParameterConfiguratiionsToAttach = em.getReference(parameterConfiguratiionsListNewParameterConfiguratiionsToAttach.getClass(), parameterConfiguratiionsListNewParameterConfiguratiionsToAttach.getParamId());
                attachedParameterConfiguratiionsListNew.add(parameterConfiguratiionsListNewParameterConfiguratiionsToAttach);
            }
            parameterConfiguratiionsListNew = attachedParameterConfiguratiionsListNew;
            users.setUserUpdaterParameterConfiguratiionsList(parameterConfiguratiionsListNew);
            users = em.merge(users);
            if (userCreatorOld != null && !userCreatorOld.equals(userCreatorNew)) {
                userCreatorOld.getUserCreatorList().remove(users);
                userCreatorOld = em.merge(userCreatorOld);
            }
            if (userCreatorNew != null && !userCreatorNew.equals(userCreatorOld)) {
                userCreatorNew.getUserCreatorList().add(users);
                userCreatorNew = em.merge(userCreatorNew);
            }
            if (usernameOld != null && !usernameOld.equals(usernameNew)) {
                usernameOld.setUser(null);
                usernameOld = em.merge(usernameOld);
            }
            if (usernameNew != null && !usernameNew.equals(usernameOld)) {
                usernameNew.setUser(users);
                usernameNew = em.merge(usernameNew);
            }
            if (managerOld != null && !managerOld.equals(managerNew)) {
                managerOld.getUserCreatorList().remove(users);
                managerOld = em.merge(managerOld);
            }
            if (managerNew != null && !managerNew.equals(managerOld)) {
                managerNew.getUserCreatorList().add(users);
                managerNew = em.merge(managerNew);
            }
            if (userUpdaterOld != null && !userUpdaterOld.equals(userUpdaterNew)) {
                userUpdaterOld.getUserUpdaterList().remove(users);
                userUpdaterOld = em.merge(userUpdaterOld);
            }
            if (userUpdaterNew != null && !userUpdaterNew.equals(userUpdaterOld)) {
                userUpdaterNew.getUserUpdaterList().add(users);
                userUpdaterNew = em.merge(userUpdaterNew);
            }
            for (Roles rolesListOldRoles : rolesListOld) {
                if (!rolesListNew.contains(rolesListOldRoles)) {
                    rolesListOldRoles.getUsersList().remove(users);
                    rolesListOldRoles = em.merge(rolesListOldRoles);
                }
            }
            for (Roles rolesListNewRoles : rolesListNew) {
                if (!rolesListOld.contains(rolesListNewRoles)) {
                    rolesListNewRoles.getUsersList().add(users);
                    rolesListNewRoles = em.merge(rolesListNewRoles);
                }
            }
            for (User usersListOldUsers : usersListOld) {
                if (!usersListNew.contains(usersListOldUsers)) {
                    usersListOldUsers.setUserCreator(null);
                    usersListOldUsers = em.merge(usersListOldUsers);
                }
            }
            for (User usersListNewUsers : usersListNew) {
                if (!usersListOld.contains(usersListNewUsers)) {
                    User oldUserCreatorOfUsersListNewUsers = usersListNewUsers.getUserCreator();
                    usersListNewUsers.setUserCreator(users);
                    usersListNewUsers = em.merge(usersListNewUsers);
                    if (oldUserCreatorOfUsersListNewUsers != null && !oldUserCreatorOfUsersListNewUsers.equals(users)) {
                        oldUserCreatorOfUsersListNewUsers.getUserCreatorList().remove(usersListNewUsers);
                        oldUserCreatorOfUsersListNewUsers = em.merge(oldUserCreatorOfUsersListNewUsers);
                    }
                }
            }
            for (User usersList1OldUsers : usersList1Old) {
                if (!usersList1New.contains(usersList1OldUsers)) {
                    usersList1OldUsers.setManager(null);
                    usersList1OldUsers = em.merge(usersList1OldUsers);
                }
            }
            for (User usersList1NewUsers : usersList1New) {
                if (!usersList1Old.contains(usersList1NewUsers)) {
                    User oldManagerOfUsersList1NewUsers = usersList1NewUsers.getManager();
                    usersList1NewUsers.setManager(users);
                    usersList1NewUsers = em.merge(usersList1NewUsers);
                    if (oldManagerOfUsersList1NewUsers != null && !oldManagerOfUsersList1NewUsers.equals(users)) {
                        oldManagerOfUsersList1NewUsers.getManagerList().remove(usersList1NewUsers);
                        oldManagerOfUsersList1NewUsers = em.merge(oldManagerOfUsersList1NewUsers);
                    }
                }
            }
            for (User usersList2OldUsers : usersList2Old) {
                if (!usersList2New.contains(usersList2OldUsers)) {
                    usersList2OldUsers.setUserUpdater(null);
                    usersList2OldUsers = em.merge(usersList2OldUsers);
                }
            }
            for (User usersList2NewUsers : usersList2New) {
                if (!usersList2Old.contains(usersList2NewUsers)) {
                    User oldUserUpdaterOfUsersList2NewUsers = usersList2NewUsers.getUserUpdater();
                    usersList2NewUsers.setUserUpdater(users);
                    usersList2NewUsers = em.merge(usersList2NewUsers);
                    if (oldUserUpdaterOfUsersList2NewUsers != null && !oldUserUpdaterOfUsersList2NewUsers.equals(users)) {
                        oldUserUpdaterOfUsersList2NewUsers.getUserUpdaterList().remove(usersList2NewUsers);
                        oldUserUpdaterOfUsersList2NewUsers = em.merge(oldUserUpdaterOfUsersList2NewUsers);
                    }
                }
            }
            for (RollPermission rollPermissionsListNewRollPermissions : rollPermissionsListNew) {
                if (!rollPermissionsListOld.contains(rollPermissionsListNewRollPermissions)) {
                    User oldUserCreatorOfRollPermissionsListNewRollPermissions = rollPermissionsListNewRollPermissions.getUserCreator();
                    rollPermissionsListNewRollPermissions.setUserCreator(users);
                    rollPermissionsListNewRollPermissions = em.merge(rollPermissionsListNewRollPermissions);
                    if (oldUserCreatorOfRollPermissionsListNewRollPermissions != null && !oldUserCreatorOfRollPermissionsListNewRollPermissions.equals(users)) {
                        oldUserCreatorOfRollPermissionsListNewRollPermissions.getUserCreatorRollPermissionsList().remove(rollPermissionsListNewRollPermissions);
                        oldUserCreatorOfRollPermissionsListNewRollPermissions = em.merge(oldUserCreatorOfRollPermissionsListNewRollPermissions);
                    }
                }
            }
            for (RollPermission rollPermissionsList1OldRollPermissions : rollPermissionsList1Old) {
                if (!rollPermissionsList1New.contains(rollPermissionsList1OldRollPermissions)) {
                    rollPermissionsList1OldRollPermissions.setUserUpdater(null);
                    rollPermissionsList1OldRollPermissions = em.merge(rollPermissionsList1OldRollPermissions);
                }
            }
            for (RollPermission rollPermissionsList1NewRollPermissions : rollPermissionsList1New) {
                if (!rollPermissionsList1Old.contains(rollPermissionsList1NewRollPermissions)) {
                    User oldUserUpdaterOfRollPermissionsList1NewRollPermissions = rollPermissionsList1NewRollPermissions.getUserUpdater();
                    rollPermissionsList1NewRollPermissions.setUserUpdater(users);
                    rollPermissionsList1NewRollPermissions = em.merge(rollPermissionsList1NewRollPermissions);
                    if (oldUserUpdaterOfRollPermissionsList1NewRollPermissions != null && !oldUserUpdaterOfRollPermissionsList1NewRollPermissions.equals(users)) {
                        oldUserUpdaterOfRollPermissionsList1NewRollPermissions.getUserUpdaterRollPermissionsList().remove(rollPermissionsList1NewRollPermissions);
                        oldUserUpdaterOfRollPermissionsList1NewRollPermissions = em.merge(oldUserUpdaterOfRollPermissionsList1NewRollPermissions);
                    }
                }
            }
            for (JobPermission jobPermissionsListOldJobPermissions : jobPermissionsListOld) {
                if (!jobPermissionsListNew.contains(jobPermissionsListOldJobPermissions)) {
                    jobPermissionsListOldJobPermissions.setUserUpdater(null);
                    jobPermissionsListOldJobPermissions = em.merge(jobPermissionsListOldJobPermissions);
                }
            }
            for (JobPermission jobPermissionsListNewJobPermissions : jobPermissionsListNew) {
                if (!jobPermissionsListOld.contains(jobPermissionsListNewJobPermissions)) {
                    User oldUserUpdaterOfJobPermissionsListNewJobPermissions = jobPermissionsListNewJobPermissions.getUserUpdater();
                    jobPermissionsListNewJobPermissions.setUserUpdater(users);
                    jobPermissionsListNewJobPermissions = em.merge(jobPermissionsListNewJobPermissions);
                    if (oldUserUpdaterOfJobPermissionsListNewJobPermissions != null && !oldUserUpdaterOfJobPermissionsListNewJobPermissions.equals(users)) {
                        oldUserUpdaterOfJobPermissionsListNewJobPermissions.getUserUpdaterJobPermissionsList().remove(jobPermissionsListNewJobPermissions);
                        oldUserUpdaterOfJobPermissionsListNewJobPermissions = em.merge(oldUserUpdaterOfJobPermissionsListNewJobPermissions);
                    }
                }
            }
            for (JobPermission jobPermissionsList1NewJobPermissions : jobPermissionsList1New) {
                if (!jobPermissionsList1Old.contains(jobPermissionsList1NewJobPermissions)) {
                    User oldUserCreatorOfJobPermissionsList1NewJobPermissions = jobPermissionsList1NewJobPermissions.getUserCreator();
                    jobPermissionsList1NewJobPermissions.setUserCreator(users);
                    jobPermissionsList1NewJobPermissions = em.merge(jobPermissionsList1NewJobPermissions);
                    if (oldUserCreatorOfJobPermissionsList1NewJobPermissions != null && !oldUserCreatorOfJobPermissionsList1NewJobPermissions.equals(users)) {
                        oldUserCreatorOfJobPermissionsList1NewJobPermissions.getUserCreatorJobPermissionsList().remove(jobPermissionsList1NewJobPermissions);
                        oldUserCreatorOfJobPermissionsList1NewJobPermissions = em.merge(oldUserCreatorOfJobPermissionsList1NewJobPermissions);
                    }
                }
            }
            for (JobPermission jobPermissionsList2NewJobPermissions : jobPermissionsList2New) {
                if (!jobPermissionsList2Old.contains(jobPermissionsList2NewJobPermissions)) {
                    User oldUserIdOfJobPermissionsList2NewJobPermissions = jobPermissionsList2NewJobPermissions.getUserId();
                    jobPermissionsList2NewJobPermissions.setUserId(users);
                    jobPermissionsList2NewJobPermissions = em.merge(jobPermissionsList2NewJobPermissions);
                    if (oldUserIdOfJobPermissionsList2NewJobPermissions != null && !oldUserIdOfJobPermissionsList2NewJobPermissions.equals(users)) {
                        oldUserIdOfJobPermissionsList2NewJobPermissions.getJobPermissionsList().remove(jobPermissionsList2NewJobPermissions);
                        oldUserIdOfJobPermissionsList2NewJobPermissions = em.merge(oldUserIdOfJobPermissionsList2NewJobPermissions);
                    }
                }
            }
            for (Roles rolesList1NewRoles : rolesList1New) {
                if (!rolesList1Old.contains(rolesList1NewRoles)) {
                    User oldUserCreatorOfRolesList1NewRoles = rolesList1NewRoles.getUserCreator();
                    rolesList1NewRoles.setUserCreator(users);
                    rolesList1NewRoles = em.merge(rolesList1NewRoles);
                    if (oldUserCreatorOfRolesList1NewRoles != null && !oldUserCreatorOfRolesList1NewRoles.equals(users)) {
                        oldUserCreatorOfRolesList1NewRoles.getUserCreatorRolesList().remove(rolesList1NewRoles);
                        oldUserCreatorOfRolesList1NewRoles = em.merge(oldUserCreatorOfRolesList1NewRoles);
                    }
                }
            }
            for (Roles rolesList2OldRoles : rolesList2Old) {
                if (!rolesList2New.contains(rolesList2OldRoles)) {
                    rolesList2OldRoles.setUserUpdater(null);
                    rolesList2OldRoles = em.merge(rolesList2OldRoles);
                }
            }
            for (Roles rolesList2NewRoles : rolesList2New) {
                if (!rolesList2Old.contains(rolesList2NewRoles)) {
                    User oldUserUpdaterOfRolesList2NewRoles = rolesList2NewRoles.getUserUpdater();
                    rolesList2NewRoles.setUserUpdater(users);
                    rolesList2NewRoles = em.merge(rolesList2NewRoles);
                    if (oldUserUpdaterOfRolesList2NewRoles != null && !oldUserUpdaterOfRolesList2NewRoles.equals(users)) {
                        oldUserUpdaterOfRolesList2NewRoles.getUserUpdaterRolesList().remove(rolesList2NewRoles);
                        oldUserUpdaterOfRolesList2NewRoles = em.merge(oldUserUpdaterOfRolesList2NewRoles);
                    }
                }
            }
            for (ParameterConfiguratiions parameterConfiguratiionsListOldParameterConfiguratiions : parameterConfiguratiionsListOld) {
                if (!parameterConfiguratiionsListNew.contains(parameterConfiguratiionsListOldParameterConfiguratiions)) {
                    parameterConfiguratiionsListOldParameterConfiguratiions.setUserUpdater(null);
                    parameterConfiguratiionsListOldParameterConfiguratiions = em.merge(parameterConfiguratiionsListOldParameterConfiguratiions);
                }
            }
            for (ParameterConfiguratiions parameterConfiguratiionsListNewParameterConfiguratiions : parameterConfiguratiionsListNew) {
                if (!parameterConfiguratiionsListOld.contains(parameterConfiguratiionsListNewParameterConfiguratiions)) {
                    User oldUserUpdaterOfParameterConfiguratiionsListNewParameterConfiguratiions = parameterConfiguratiionsListNewParameterConfiguratiions.getUserUpdater();
                    parameterConfiguratiionsListNewParameterConfiguratiions.setUserUpdater(users);
                    parameterConfiguratiionsListNewParameterConfiguratiions = em.merge(parameterConfiguratiionsListNewParameterConfiguratiions);
                    if (oldUserUpdaterOfParameterConfiguratiionsListNewParameterConfiguratiions != null && !oldUserUpdaterOfParameterConfiguratiionsListNewParameterConfiguratiions.equals(users)) {
                        oldUserUpdaterOfParameterConfiguratiionsListNewParameterConfiguratiions.getUserUpdaterParameterConfiguratiionsList().remove(parameterConfiguratiionsListNewParameterConfiguratiions);
                        oldUserUpdaterOfParameterConfiguratiionsListNewParameterConfiguratiions = em.merge(oldUserUpdaterOfParameterConfiguratiionsListNewParameterConfiguratiions);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = users.getUserId();
                if (findUsers(id) == null) {
                    throw new NonexistentEntityException("The users with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User users;
            try {
                users = em.getReference(User.class, id);
                users.getUserId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The users with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<RollPermission> rollPermissionsListOrphanCheck = users.getUserCreatorRollPermissionsList();
            for (RollPermission rollPermissionsListOrphanCheckRollPermissions : rollPermissionsListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This User (" + users + ") cannot be destroyed since the RollPermission " + rollPermissionsListOrphanCheckRollPermissions + " in its rollPermissionsList field has a non-nullable userCreator field.");
            }
            List<JobPermission> jobPermissionsList1OrphanCheck = users.getUserCreatorJobPermissionsList();
            for (JobPermission jobPermissionsList1OrphanCheckJobPermissions : jobPermissionsList1OrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This User (" + users + ") cannot be destroyed since the JobPermission " + jobPermissionsList1OrphanCheckJobPermissions + " in its jobPermissionsList1 field has a non-nullable userCreator field.");
            }
            List<JobPermission> jobPermissionsList2OrphanCheck = users.getJobPermissionsList();
            for (JobPermission jobPermissionsList2OrphanCheckJobPermissions : jobPermissionsList2OrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This User (" + users + ") cannot be destroyed since the JobPermission " + jobPermissionsList2OrphanCheckJobPermissions + " in its jobPermissionsList2 field has a non-nullable userId field.");
            }
            List<Roles> rolesList1OrphanCheck = users.getUserCreatorRolesList();
            for (Roles rolesList1OrphanCheckRoles : rolesList1OrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This User (" + users + ") cannot be destroyed since the Roles " + rolesList1OrphanCheckRoles + " in its rolesList1 field has a non-nullable userCreator field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            User userCreator = users.getUserCreator();
            if (userCreator != null) {
                userCreator.getUserCreatorList().remove(users);
                userCreator = em.merge(userCreator);
            }
            UserSession username = users.getUserSession();
            if (username != null) {
                username.setUser(null);;
                username = em.merge(username);
            }
            User manager = users.getManager();
            if (manager != null) {
                manager.getUserCreatorList().remove(users);
                manager = em.merge(manager);
            }
            User userUpdater = users.getUserUpdater();
            if (userUpdater != null) {
                userUpdater.getUserUpdaterList().remove(users);
                userUpdater = em.merge(userUpdater);
            }
            List<Roles> rolesList = users.getRolesList();
            for (Roles rolesListRoles : rolesList) {
                rolesListRoles.getUsersList().remove(users);
                rolesListRoles = em.merge(rolesListRoles);
            }
            List<User> usersList = users.getUserCreatorList();
            for (User usersListUsers : usersList) {
                usersListUsers.setUserCreator(null);
                usersListUsers = em.merge(usersListUsers);
            }
            List<User> usersList1 = users.getManagerList();
            for (User usersList1Users : usersList1) {
                usersList1Users.setManager(null);
                usersList1Users = em.merge(usersList1Users);
            }
            List<User> usersList2 = users.getUserUpdaterList();
            for (User usersList2Users : usersList2) {
                usersList2Users.setUserUpdater(null);
                usersList2Users = em.merge(usersList2Users);
            }
            List<RollPermission> rollPermissionsList1 = users.getUserUpdaterRollPermissionsList();
            for (RollPermission rollPermissionsList1RollPermissions : rollPermissionsList1) {
                rollPermissionsList1RollPermissions.setUserUpdater(null);
                rollPermissionsList1RollPermissions = em.merge(rollPermissionsList1RollPermissions);
            }
            List<JobPermission> jobPermissionsList = users.getUserUpdaterJobPermissionsList();
            for (JobPermission jobPermissionsListJobPermissions : jobPermissionsList) {
                jobPermissionsListJobPermissions.setUserUpdater(null);
                jobPermissionsListJobPermissions = em.merge(jobPermissionsListJobPermissions);
            }
            List<Roles> rolesList2 = users.getUserUpdaterRolesList();
            for (Roles rolesList2Roles : rolesList2) {
                rolesList2Roles.setUserUpdater(null);
                rolesList2Roles = em.merge(rolesList2Roles);
            }
            List<ParameterConfiguratiions> parameterConfiguratiionsList = users.getUserUpdaterParameterConfiguratiionsList();
            for (ParameterConfiguratiions parameterConfiguratiionsListParameterConfiguratiions : parameterConfiguratiionsList) {
                parameterConfiguratiionsListParameterConfiguratiions.setUserUpdater(null);
                parameterConfiguratiionsListParameterConfiguratiions = em.merge(parameterConfiguratiionsListParameterConfiguratiions);
            }
            em.remove(users);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<User> findUsersEntities() {
        return findUsersEntities(true, -1, -1);
    }

    public List<User> findUsersEntities(int maxResults, int firstResult) {
        return findUsersEntities(false, maxResults, firstResult);
    }

    private List<User> findUsersEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery<Object> cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(User.class));
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

    public User findUsers(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(User.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsersCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery<Object> cq = em.getCriteriaBuilder().createQuery();
            Root<User> rt = cq.from(User.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
