package com.mahallat.generatedenteties;
// Generated Aug 13, 2018 2:54:44 AM by Hibernate Tools 5.1.8.Final

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Category generated by hbm2java
 */
@Entity
@Table(name = "category", catalog = "mahallat")
public class Category implements java.io.Serializable {

	private int id;
	private String name;
	private String code;
	private String createdAt;
	private String updatedAt;
	private Set<Store> stores = new HashSet<Store>(0);

	public Category() {
	}

	public Category(int id) {
		this.id = id;
	}

	public Category(int id, String name, String code, String createdAt, String updatedAt, Set<Store> stores) {
		this.id = id;
		this.name = name;
		this.code = code;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.stores = stores;
	}

	@Id

	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "name", length = 45)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "code", length = 45)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "created_at", length = 45)
	public String getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	@Column(name = "updated_at", length = 45)
	public String getUpdatedAt() {
		return this.updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
	public Set<Store> getStores() {
		return this.stores;
	}

	public void setStores(Set<Store> stores) {
		this.stores = stores;
	}

}
