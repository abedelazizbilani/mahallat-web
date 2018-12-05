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
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * Product generated by hbm2java
 */
@Entity
@Table(name = "product", catalog = "mahallat")
public class Product implements java.io.Serializable {

	private Integer id;
	private Store store;
	@NotBlank
	private String name;
	private Boolean active;
	private Date createdAt;
	private Date updatedAt;
	private String image;
	@NotNull
	@Min(1)
	private float price;
	private String description;
	private Set<Offers> offerses = new HashSet<Offers>(0);
	private Set<Comment> comments = new HashSet<Comment>(0);
	private Set<ProductRating> productRatings = new HashSet<ProductRating>(0);
	private Set<ProductLike> productLikes = new HashSet<ProductLike>(0);
	public int likeCount;
	public double averageRating;
	public boolean liked;
	public boolean rated;
	public boolean favorited;
	public int rate;

	public Product() {
	}

	public Product(Store store, String name, Boolean active, Date createdAt, String image, float price,
			String description) {
		this.store = store;
		this.name = name;
		this.active = active;
		this.createdAt = createdAt;
		this.image = image;
		this.price = price;
		this.description = description;
	}

	public Product(Store store, String name, Boolean active, Date createdAt, Date updatedAt, String image, float price,
			String description, Set<Offers> offerses, Set<Comment> comments, Set<ProductRating> productRatings,
			Set<ProductLike> productLikes) {
		this.store = store;
		this.name = name;
		this.active = active;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.image = image;
		this.price = price;
		this.description = description;
		this.offerses = offerses;
		this.comments = comments;
		this.productRatings = productRatings;
		this.productLikes = productLikes;
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
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonManagedReference
	@JoinColumn(name = "store_id", nullable = false)
	public Store getStore() {
		return this.store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	@Column(name = "name", nullable = false)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "active", nullable = false)
	public Boolean getActive() {
		return this.active;
	}

	public void setActive(Boolean active) {
		this.active = active;
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

	@Column(name = "image", nullable = false)
	public String getImage() {
		return this.image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Column(name = "price", nullable = false, precision = 12, scale = 0)
	public float getPrice() {
		return this.price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	@Column(name = "description", nullable = false, length = 500)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
	@JsonBackReference
	public Set<Offers> getOfferses() {
		return this.offerses;
	}

	public void setOfferses(Set<Offers> offerses) {
		this.offerses = offerses;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
	@JsonManagedReference
	public Set<Comment> getComments() {
		return this.comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
	@JsonBackReference
	public Set<ProductRating> getProductRatings() {
		return this.productRatings;
	}

	public void setProductRatings(Set<ProductRating> productRatings) {
		this.productRatings = productRatings;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
	@JsonBackReference
	public Set<ProductLike> getProductLikes() {
		return this.productLikes;
	}

	public void setProductLikes(Set<ProductLike> productLikes) {
		this.productLikes = productLikes;
	}

}
