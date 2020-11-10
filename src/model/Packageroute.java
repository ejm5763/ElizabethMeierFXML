/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Owner
 */
@Entity
@Table(name = "PACKAGEROUTE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Packageroute.findAll", query = "SELECT p FROM Packageroute p")
    , @NamedQuery(name = "Packageroute.findByRouteid", query = "SELECT p FROM Packageroute p WHERE p.routeid = :routeid")
    , @NamedQuery(name = "Packageroute.findByNumpackages", query = "SELECT p FROM Packageroute p WHERE p.numpackages = :numpackages")})
public class Packageroute implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ROUTEID")
    private Integer routeid;
    @Column(name = "NUMPACKAGES")
    private Integer numpackages;

    public Packageroute() {
    }

    public Packageroute(Integer routeid) {
        this.routeid = routeid;
    }

    public Integer getRouteid() {
        return routeid;
    }

    public void setRouteid(Integer routeid) {
        this.routeid = routeid;
    }

    public Integer getNumpackages() {
        return numpackages;
    }

    public void setNumpackages(Integer numpackages) {
        this.numpackages = numpackages;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (routeid != null ? routeid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Packageroute)) {
            return false;
        }
        Packageroute other = (Packageroute) object;
        if ((this.routeid == null && other.routeid != null) || (this.routeid != null && !this.routeid.equals(other.routeid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Packageroute[ routeid=" + routeid + " ]";
    }
    
}
