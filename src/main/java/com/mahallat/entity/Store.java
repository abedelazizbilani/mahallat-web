package com.mahallat.entity;
// Generated Aug 15, 2018 4:23:34 AM by Hibernate Tools 5.1.8.Final

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
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
 * Store generated by hbm2java
 */
@Entity
@Table(name = "store", catalog = "mahallat")
public class Store implements java.io.Serializable {

	@JsonIgnore

	private Integer id;
	private Category category;
	private User user;
	private String name;
	private byte active;
	private String description;
	private double longitude;
	private double latitude;
	private Date openHour;
	private Date closeHour;
	private String image;
	private Date createdAt;
	private Date updatedAt;
	private Set<StoreRating> storeRatings = new HashSet<StoreRating>(0);
	private Set<StoreLike> storeLikes = new HashSet<StoreLike>(0);
	private Set<Product> products = new HashSet<Product>(0);

	public Store() {
	}

	public Store(Category category, User user, String name, byte active, double longitude, double latitude,
			Date openHour, Date closeHour, String image) {
		this.category = category;
		this.user = user;
		this.name = name;
		this.active = active;
		this.longitude = longitude;
		this.latitude = latitude;
		this.openHour = openHour;
		this.closeHour = closeHour;
		this.image = image;
	}

	public Store(Category category, User user, String name, byte active, String description, double longitude,
			double latitude, Date openHour, Date closeHour, String image, Date createdAt, Date updatedAt,
			Set<StoreRating> storeRatings, Set<StoreLike> storeLikes, Set<Product> products) {
		this.category = category;
		this.user = user;
		this.name = name;
		this.active = active;
		this.description = description;
		this.longitude = longitude;
		this.latitude = latitude;
		this.openHour = openHour;
		this.closeHour = closeHour;
		this.image = image;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.storeRatings = storeRatings;
		this.storeLikes = storeLikes;
		this.products = products;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	@JsonManagedReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id", nullable = false)
	public Category getCategory() {
		return this.category;
	}

	public void setCategory(Category category) {
		this.category = category;
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

	@Column(name = "name", nullable = false)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "active", nullable = false)
	public byte getActive() {
		return this.active;
	}

	public void setActive(byte active) {
		this.active = active;
	}

	@Column(name = "description", length = 500)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "longitude", nullable = false, precision = 22, scale = 0)
	public double getLongitude() {
		return this.longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	@Column(name = "latitude", nullable = false, precision = 22, scale = 0)
	public double getLatitude() {
		return this.latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	@Temporal(TemporalType.TIME)
	@Column(name = "open_hour", nullable = false, length = 8)
	public Date getOpenHour() {
		return this.openHour;
	}

	public void setOpenHour(Date openHour) {
		this.openHour = openHour;
	}

	@Temporal(TemporalType.TIME)
	@Column(name = "close_hour", nullable = false, length = 8)
	public Date getCloseHour() {
		return this.closeHour;
	}

	public void setCloseHour(Date closeHour) {
		this.closeHour = closeHour;
	}

	@Column(name = "image", nullable = false)
	public String getImage() {
		return this.image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "created_at", length = 10)
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
	@JsonManagedReference
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "store")
	public Set<StoreRating> getStoreRatings() {
		return this.storeRatings;
	}

	public void setStoreRatings(Set<StoreRating> storeRatings) {
		this.storeRatings = storeRatings;
	}
	
	@JsonBackReference
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "store")
	public Set<StoreLike> getStoreLikes() {
		return this.storeLikes;
	}

	public void setStoreLikes(Set<StoreLike> storeLikes) {
		this.storeLikes = storeLikes;
	}
	
	@JsonBackReference
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "store")
	public Set<Product> getProducts() {
		return this.products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}

}