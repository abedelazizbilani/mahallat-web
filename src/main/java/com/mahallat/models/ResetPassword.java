package com.mahallat.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class ResetPassword {

	@NotEmpty
	@Email
	private String email;
	
	public String getEmail() {
		return this.email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
}
