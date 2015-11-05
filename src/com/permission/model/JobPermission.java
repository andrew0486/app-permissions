/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.permission.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import com.permission.model.enums.PermissionStatus;
import com.permission.model.enums.Status;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author ahuertas
 */
@Entity
@Table(name = "job_permissions")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "JobPermission.findAll", query = "SELECT j FROM JobPermission j"),
    @NamedQuery(name = "JobPermission.findByPermId", query = "SELECT j FROM JobPermission j WHERE j.permId = :permId"),
    @NamedQuery(name = "JobPermission.findByPermApplicationDate", query = "SELECT j FROM JobPermission j WHERE j.permApplicationDate = :permApplicationDate"),
    @NamedQuery(name = "JobPermission.findByPermInit", query = "SELECT j FROM JobPermission j WHERE j.permInit = :permInit"),
    @NamedQuery(name = "JobPermission.findByPermEnd", query = "SELECT j FROM JobPermission j WHERE j.permEnd = :permEnd"),
    @NamedQuery(name = "JobPermission.findByPermStatus", query = "SELECT j FROM JobPermission j WHERE j.permStatus = :permStatus")})
public class JobPermission implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 8023392565790129949L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "perm_id")
	@Getter @Setter
	private Long permId;
    
	@Basic(optional = false)
    @Column(name = "perm_application_date")
    @Temporal(TemporalType.DATE)
	@Getter @Setter
    private Date permApplicationDate;
	
    @Basic(optional = false)
    @Column(name = "perm_init")
    @Getter @Setter
    private Timestamp permInit;
    
    @Basic(optional = false)
    @Column(name = "perm_end")
    @Getter @Setter
    private Timestamp permEnd;
    
    @Lob
    @Column(name = "perm_description")
    @Getter @Setter
    private String permDescription;
   
    @Basic(optional = false)
    @Column(name = "perm_status")
    @Enumerated(EnumType.STRING)
    @Getter @Setter
    private PermissionStatus permStatus;
   
    @JoinColumn(name = "user_updater", referencedColumnName = "user_id")
    @ManyToOne
    @Getter @Setter
    private User userUpdater;
   
    @JoinColumn(name = "user_creator", referencedColumnName = "user_id")
    @ManyToOne(optional = false)
    @Getter @Setter
    private User userCreator;
   
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne(optional = false)
    @Getter @Setter
    private User userId;

    //CONSTRUCTORES
    /**
     * DEFAULT
     */
    public JobPermission() {
    }

    /**
     * Con todos los campos
     * @param permApplicationDate fecha en la que se solicita el permiso
     * @param permInit fecha de inicio del permiso
     * @param permEnd fecha de fin del permiso
     * @param permStatus estado del permiso
     */
    public JobPermission(Date permApplicationDate, Timestamp permInit, Timestamp permEnd, PermissionStatus permStatus) {
        this.permApplicationDate = permApplicationDate;
        this.permInit = permInit;
        this.permEnd = permEnd;
        this.permStatus = permStatus;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (permId != null ? permId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof JobPermission)) {
            return false;
        }
        JobPermission other = (JobPermission) object;
        if ((this.permId == null && other.permId != null) || (this.permId != null && !this.permId.equals(other.permId))) {
            return false;
        }
        return true;
    }

}
