/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.permission.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.permission.model.enums.Status;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author ahuertas
 */
@Entity
@Table(name = "users")
@XmlRootElement
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "user_id")
    @Getter @Setter
    private Long userId;
    
    @Basic(optional = false)
    @Column(name = "document_number")
    @Getter @Setter
    private String documentNumber;
    
    @Basic(optional = false)
    @Column(name = "document_type")
    @Getter @Setter
    private String documentType;
    
    @Basic(optional = false)
    @Column(name = "first_name")
    @Getter @Setter
    private String firstName;
    
    @Basic(optional = false)
    @Column(name = "last_name_one")
    @Getter @Setter
    private String lastNameOne;
   
    @Column(name = "last_name_two")
    @Getter @Setter
    private String lastNameTwo;
   
    @Basic(optional = false)
    @Column(name = "user_status")
    @Enumerated(EnumType.STRING)
    @Getter @Setter
    private Status userStatus;
    
    @ManyToMany(mappedBy = "usersList")
    @Setter
    private List<Roles> rolesList;
   
    @OneToMany(mappedBy = "userCreator")
    @Setter
    private List<User> userCreatorList;
   
    @JoinColumn(name = "user_creator", referencedColumnName = "user_id")
    @ManyToOne
    @Getter @Setter
    private User userCreator;
    
    @JoinColumn(name = "username", referencedColumnName = "username")
    @OneToOne
    @Getter @Setter
    private UserSession userSession;
   
    @OneToMany(mappedBy = "manager")
    @Setter
    private List<User> managerList;
   
    @JoinColumn(name = "manager", referencedColumnName = "user_id")
    @ManyToOne
    @Getter @Setter
    private User manager;
   
    @OneToMany(mappedBy = "userUpdater")
    @Setter
    private List<User> userUpdaterList;
    
    @JoinColumn(name = "user_updater", referencedColumnName = "user_id")
    @ManyToOne
    @Getter @Setter
    private User userUpdater;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userCreator")
    @Setter
    private List<RollPermission> userCreatorRollPermissionsList;
    
    @OneToMany(mappedBy = "userUpdater")
    @Setter
    private List<RollPermission> userUpdaterRollPermissionsList;
    
    @OneToMany(mappedBy = "userUpdater")
    @Setter
    private List<JobPermission> userUpdaterJobPermissionsList;
   
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userCreator")
    @Setter
    private List<JobPermission> userCreatorJobPermissionsList;
   
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    @Setter 
    private List<JobPermission> jobPermissionsList;
   
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userCreator")
    @Setter
    private List<Roles> userCreatorRolesList;
  
    @OneToMany(mappedBy = "userUpdater")
    @Setter
    private List<Roles> userUpdaterRolesList;
    
    @OneToMany(mappedBy = "userUpdater")
    @Setter
    private List<ParameterConfiguratiions> userUpdaterParameterConfiguratiionsList;

    public User() {
    }

    public User(Long userId) {
        this.userId = userId;
    }

    public User(Long userId, String documentNumber, String documentType, String firstName, String lastNameOne, Status userStatus) {
        this.userId = userId;
        this.documentNumber = documentNumber;
        this.documentType = documentType;
        this.firstName = firstName;
        this.lastNameOne = lastNameOne;
        this.userStatus = userStatus;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Users[ userId=" + userId + " ]";
    }
    
    @XmlTransient
    public List<Roles> getRolesList() {
		return rolesList;
	}
    
    @XmlTransient
    public List<User> getUserCreatorList() {
		return userCreatorList;
	}
    
    @XmlTransient
    public List<User> getManagerList() {
		return managerList;
	}
    
    @XmlTransient
    public List<User> getUserUpdaterList() {
		return userUpdaterList;
	}
    
    @XmlTransient
    public List<RollPermission> getUserCreatorRollPermissionsList() {
		return userCreatorRollPermissionsList;
	}
    
    @XmlTransient
    public List<RollPermission> getUserUpdaterRollPermissionsList() {
		return userUpdaterRollPermissionsList;
	}
    
    @XmlTransient
    public List<JobPermission> getUserUpdaterJobPermissionsList() {
		return userUpdaterJobPermissionsList;
	}
    
    @XmlTransient
    public List<JobPermission> getUserCreatorJobPermissionsList() {
		return userCreatorJobPermissionsList;
	}
    
    @XmlTransient
    public List<JobPermission> getJobPermissionsList() {
		return jobPermissionsList;
	}
    
    @XmlTransient
    public List<Roles> getUserCreatorRolesList() {
		return userCreatorRolesList;
	}
    
    @XmlTransient
    public List<Roles> getUserUpdaterRolesList() {
		return userUpdaterRolesList;
	}
    
    @XmlTransient
    public List<ParameterConfiguratiions> getUserUpdaterParameterConfiguratiionsList() {
		return userUpdaterParameterConfiguratiionsList;
	}
}
