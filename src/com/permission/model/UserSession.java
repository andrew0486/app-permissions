/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.permission.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author ahuertas
 */
@Entity
@Table(name = "sessions")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserSession.findAll", query = "SELECT s FROM UserSession s"),
    @NamedQuery(name = "UserSession.findByUsernameAndPassword", query = "SELECT s FROM UserSession s WHERE s.username = :username AND s.password = :password")})
public class UserSession implements Serializable {
    private static final long serialVersionUID = 1L;
   
    @Id
    @Basic(optional = false)
    @Column(name = "username")
    @Getter @Setter
    private String username;
    
    @Basic(optional = false)
    @Column(name = "password")
    @Getter @Setter
    private String password;
    
    @Basic(optional = false)
    @Column(name = "last_login")
    @Getter @Setter
    private Timestamp lastLogin;
    
    @OneToOne(mappedBy = "userSession")
    @Getter @Setter
    private User user;

    //CONSTRUCTORES
    /**
     * DEFAULT
     */
    public UserSession() {
    }

    /**
     * Constructor con todos los campos
     * @param username nombre de usuario de la sesion
     * @param password contrasenia de la sesion
     * @param loginPassword 
     */
    public UserSession(String username, String password, Timestamp loginPassword) {
        this.username = username;
        this.password = password;
        this.lastLogin = loginPassword;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (username != null ? username.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof UserSession)) {
            return false;
        }
        UserSession other = (UserSession) object;
        if ((this.username == null && other.username != null) || (this.username != null && !this.username.equals(other.username))) {
            return false;
        }
        return true;
    }

}
