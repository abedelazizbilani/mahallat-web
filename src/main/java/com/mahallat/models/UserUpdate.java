package com.mahallat.models;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;

public class UserUpdate {
	
	private Integer id;
	private String username;
	
	@NotEmpty(message = "*Please provide your name.")
	private String name;
	
	private String lastname;
	private String image;
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id= id;
	}
	
	@Column(name = "username", nullable = false)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "lastname")
	public String getLastname() {
		return this.lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	@Column(name = "image")
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
}
