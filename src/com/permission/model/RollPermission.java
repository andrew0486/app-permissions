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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

import com.permission.model.enums.Status;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author ahuertas
 */
@Entity
@Table(name = "roll_permissions")
@XmlRootElement
public class RollPermission implements Serializable {
   
	private static final long serialVersionUID = 6647715054568311857L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "roll_perm_id")
	@Getter @Setter
    private Long rollPermId;
   
    @Basic(optional = false)
    @Column(name = "roll_perm_name")
    @Getter @Setter
    private String rollPermName;
    
    @Lob
    @Column(name = "roll_perm_description")
    @Getter @Setter
    private String rollPermDescription;
    
    @Column(name = "roll_perm_status")
    @Enumerated(EnumType.STRING)
    @Getter @Setter
    private Status rollPermStatus;
    
    @JoinTable(name = "roll_perm_associate", joinColumns = {
        @JoinColumn(name = "roll_perm_id", referencedColumnName = "roll_perm_id")}, inverseJoinColumns = {
        @JoinColumn(name = "roll_id", referencedColumnName = "roll_id")})
    @ManyToMany
    @Setter
    private List<Roles> rolesList;
    
    @JoinColumn(name = "user_creator", referencedColumnName = "user_id")
    @ManyToOne(optional = false)
    @Getter @Setter
    private User userCreator;
    
    @JoinColumn(name = "user_updater", referencedColumnName = "user_id")
    @ManyToOne
    @Getter @Setter
    private User userUpdater;

    //CONSTRUCTORES
    /**
     * default sin parametros
     */
    public RollPermission() {
    }

    /**
     * Constructor con campos obligatorios
     * @param rollPermName nombre del permiso de rol
     * @param rollPermStatus estado del permiso de rol
     */
    public RollPermission(String rollPermName, Status rollPermStatus) {
		this.rollPermName = rollPermName;
		this.rollPermStatus = rollPermStatus;
	}
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rollPermId != null ? rollPermId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RollPermission)) {
            return false;
        }
        RollPermission other = (RollPermission) object;
        if ((this.rollPermId == null && other.rollPermId != null) || (this.rollPermId != null && !this.rollPermId.equals(other.rollPermId))) {
            return false;
        }
        return true;
    }
    
    @XmlTransient
    public List<Roles> getRolesList() {
		return rolesList;
	}

}
