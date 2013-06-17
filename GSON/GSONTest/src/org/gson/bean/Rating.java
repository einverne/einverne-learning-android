package org.gson.bean;

import com.google.gson.annotations.SerializedName;

public class Rating {
	@SerializedName("max")
	public int max;

	@SerializedName("numRaters")
	public int numRaters;

	@SerializedName("average")
	public float average;

	@SerializedName("min")
	public int min;
}
