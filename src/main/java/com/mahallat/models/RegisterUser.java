package com.mahallat.models;

import javax.validation.constraints.NotNull;

public class RegisterUser {

	@NotNull
	private String username;
	@NotNull
	private String name;
	@NotNull
	private String password;
	@NotNull
	private String email;
	@NotNull
	private String lastname;

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
