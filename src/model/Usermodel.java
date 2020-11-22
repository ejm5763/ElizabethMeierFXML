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
@Table(name = "USERTABLE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usermodel.findAll", query = "SELECT u FROM Usermodel u")
    , @NamedQuery(name = "Usermodel.findUserById", query = "SELECT u FROM Usermodel u WHERE u.id = :id")
    , @NamedQuery(name = "Usermodel.findByName", query = "SELECT u FROM Usermodel u WHERE u.name = :name")
    , @NamedQuery(name = "Usermodel.findByEmail", query = "SELECT u FROM Usermodel u WHERE u.email = :email")
    , @NamedQuery(name = "Usermodel.findByAddress", query = "SELECT u FROM Usermodel u WHERE u.address = :address")
    , @NamedQuery(name = "Usermodel.findByPhonenumber", query = "SELECT u FROM Usermodel u WHERE u.phonenumber = :phonenumber")

    , @NamedQuery(name = "Usermodel.findByNameAdvanced", query = "SELECT u FROM Usermodel u WHERE  LOWER(u.name) LIKE  CONCAT('%', LOWER(:name), '%')")})

public class Usermodel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "ADDRESS")
    private String address;
    @Column(name = "PHONENUMBER")
    private Integer phonenumber;

    public Usermodel() {
    }

    public Usermodel(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(Integer phonenumber) {
        this.phonenumber = phonenumber;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usermodel)) {
            return false;
        }
        Usermodel other = (Usermodel) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Usermodel[ id=" + id + " ]";
    }
    
}
