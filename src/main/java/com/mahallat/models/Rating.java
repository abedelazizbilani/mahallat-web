package com.mahallat.models;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class Rating {
	@NotNull
	@Min(1)
	@Max(5)
	private int rating;

	public int getRating()
	{
		return this.rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}
}
