package org.gson.bean;

import com.google.gson.annotations.SerializedName;

public class Image {

	@SerializedName("small")
	public String small;

	@SerializedName("large")
	public String large;

	@SerializedName("medium")
	public String medium;
}
