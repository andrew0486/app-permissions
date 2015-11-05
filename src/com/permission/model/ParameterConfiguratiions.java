/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.permission.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ahuertas
 */
@Entity
@Table(name = "parameter_configuratiions")
@XmlRootElement
public class ParameterConfiguratiions implements Serializable {
    private static final long serialVersionUID = 1L;
   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "param_id")
    private Long paramId;
    
    @Basic(optional = false)
    @Column(name = "param_name")
    private String paramName;
   
    @Basic(optional = false)
    @Column(name = "param_value")
    private String paramValue;
   
    @JoinColumn(name = "user_updater", referencedColumnName = "user_id")
    @ManyToOne
    private User userUpdater;

    public ParameterConfiguratiions() {
    }

    public ParameterConfiguratiions(Long paramId) {
        this.paramId = paramId;
    }

    public ParameterConfiguratiions(Long paramId, String paramName, String paramValue) {
        this.paramId = paramId;
        this.paramName = paramName;
        this.paramValue = paramValue;
    }

    public Long getParamId() {
        return paramId;
    }

    public void setParamId(Long paramId) {
        this.paramId = paramId;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    public User getUserUpdater() {
        return userUpdater;
    }

    public void setUserUpdater(User userUpdater) {
        this.userUpdater = userUpdater;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (paramId != null ? paramId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ParameterConfiguratiions)) {
            return false;
        }
        ParameterConfiguratiions other = (ParameterConfiguratiions) object;
        if ((this.paramId == null && other.paramId != null) || (this.paramId != null && !this.paramId.equals(other.paramId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.ParameterConfiguratiions[ paramId=" + paramId + " ]";
    }
    
}
