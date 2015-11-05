/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.permission.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author ahuertas
 */
@Entity
@Table(name = "roles")
@XmlRootElement
public class Roles implements Serializable {
    private static final long serialVersionUID = 1L;
  
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "roll_id")
    @Getter @Setter
    private Long rollId;
   
    @Basic(optional = false)
    @Column(name = "roll_name")
    @Getter @Setter
    private String rollName;
   
    @Lob
    @Column(name = "roll_description")
    @Getter @Setter
    private String rollDescription;
   
    @Basic(optional = false)
    @Column(name = "roll_status")
    @Getter @Setter
    private String rollStatus;
   
    @ManyToMany(mappedBy = "rolesList")
    @Setter
    private List<RollPermission> rollPermissionsList;
   
    @JoinTable(name = "user_roll_associate", joinColumns = {
        @JoinColumn(name = "roll_id", referencedColumnName = "roll_id")}, inverseJoinColumns = {
        @JoinColumn(name = "user_id", referencedColumnName = "user_id")})
    @ManyToMany
    @Setter 
    private List<User> usersList;
  
    @JoinColumn(name = "user_creator", referencedColumnName = "user_id")
    @ManyToOne(optional = false)
    @Getter @Setter
    private User userCreator;
   
    @JoinColumn(name = "user_updater", referencedColumnName = "user_id")
    @ManyToOne
    @Getter @Setter
    private User userUpdater;

    public Roles() {
    }

    public Roles(Long rollId) {
        this.rollId = rollId;
    }

    public Roles(Long rollId, String rollName, String rollStatus) {
        this.rollId = rollId;
        this.rollName = rollName;
        this.rollStatus = rollStatus;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rollId != null ? rollId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Roles)) {
            return false;
        }
        Roles other = (Roles) object;
        if ((this.rollId == null && other.rollId != null) || (this.rollId != null && !this.rollId.equals(other.rollId))) {
            return false;
        }
        return true;
    }

    @XmlTransient
    public List<RollPermission> getRollPermissionsList() {
		return rollPermissionsList;
	}
    
    @XmlTransient
    public List<User> getUsersList() {
		return usersList;
	}
    
}
