package com.mahallat.models;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.mahallat.entity.Category;
import com.mahallat.entity.User;

public class AddStore {

	private String category;
	private String user;
	private String name;
	private String description;
	private double longitude;
	private double latitude;
	@DateTimeFormat(pattern = "hh:mm aa")
	private Date openHour;
	@DateTimeFormat(pattern = "hh:mm aa")
	private Date closeHour;
	private String image;
	private Date createdAt;
	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
	
}
