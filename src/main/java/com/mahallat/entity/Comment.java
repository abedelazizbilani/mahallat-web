package com.mahallat.entity;
// Generated Aug 15, 2018 4:23:34 AM by Hibernate Tools 5.1.8.Final

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * Comment generated by hbm2java
 */
@Entity
@Table(name = "comment", catalog = "mahallat")
public class Comment implements java.io.Serializable {

	private int id;
	private Store store;
	private Product product;
	private User user;
	private String text;
	private Date createdAt;
	private Date updatedAt;

	public Comment() {
	}

	public Comment(int id, User user, String text, Date createdAt) {
		this.id = id;
		this.user = user;
		this.text = text;
		this.createdAt = createdAt;
	}

	public Comment(int id, Store store, Product product, User user, String text, Date createdAt, Date updatedAt) {
		this.id = id;
		this.store= store;
		this.product = product;
		this.user = user;
		this.text = text;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	@Id
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonBackReference
	@JoinColumn(name = "store_id")
	public Store getStore() {
		return this.store;
	}

	public void setStore(Store store) {
		this.store= store;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonBackReference
	@JoinColumn(name = "product_id")
	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonManagedReference
	@JoinColumn(name = "user_id", nullable = false)
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Column(name = "text", nullable = false, length = 65535)
	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "created_at", nullable = false, length = 10)
	public Date getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "updated_at", length = 10)
	public Date getUpdatedAt() {
		return this.updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
}
