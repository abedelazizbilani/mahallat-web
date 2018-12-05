package com.mahallat.models;

import javax.validation.constraints.NotNull;

public class StoreRequest {
	@NotNull
	private int storeId;

	public int getStoreId() {
		return this.storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}
}
