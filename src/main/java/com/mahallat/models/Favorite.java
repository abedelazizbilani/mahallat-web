package com.mahallat.models;

import javax.validation.constraints.NotNull;

public class Favorite {

	@NotNull
	private int productId;

	public int getProductId() {
		return this.productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}
}
