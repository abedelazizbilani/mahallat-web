package com.mahallat.entity;
// Generated Aug 15, 2018 4:23:34 AM by Hibernate Tools 5.1.8.Final


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 * Settings generated by hbm2java
 */
@Entity
@Table(name="settings"
    ,catalog="mahallat"
    , uniqueConstraints = @UniqueConstraint(columnNames="code") 
)
public class Settings  implements java.io.Serializable {


     private int id;
     private String name;
     private String code;
     private String description;
     private String value;
     private Date createdAt;
     private Date updatedAt;

    public Settings() {
    }

	
    public Settings(int id, String name, String code, String value, Date createdAt) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.value = value;
        this.createdAt = createdAt;
    }
    public Settings(int id, String name, String code, String description, String value, Date createdAt, Date updatedAt) {
       this.id = id;
       this.name = name;
       this.code = code;
       this.description = description;
       this.value = value;
       this.createdAt = createdAt;
       this.updatedAt = updatedAt;
    }
   
     @Id 

    
    @Column(name="id", unique=true, nullable=false)
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    
    @Column(name="name", nullable=false)
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    
    @Column(name="code", unique=true, nullable=false, length=45)
    public String getCode() {
        return this.code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }

    
    @Column(name="description")
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    
    @Column(name="value", nullable=false)
    public String getValue() {
        return this.value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="created_at", nullable=false, length=10)
    public Date getCreatedAt() {
        return this.createdAt;
    }
    
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="updated_at", length=10)
    public Date getUpdatedAt() {
        return this.updatedAt;
    }
    
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }




}

