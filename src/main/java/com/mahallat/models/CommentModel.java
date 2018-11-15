package com.mahallat.models;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;

public class CommentModel {

	@Digits(fraction = 0, integer = 20)
	private int storeId;
	@Digits(fraction = 0, integer = 20)
	private int productId;
	@NotEmpty
	private String text;
	
	public String getText() {
		return this.text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public int  getStoreId() {
		return this.storeId;
	}
	
	
	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}
	
	public int getProductId() {
		return this.productId;
	}
	
	public void setProductId(int productId) {
		this.productId = productId;
	}
}
