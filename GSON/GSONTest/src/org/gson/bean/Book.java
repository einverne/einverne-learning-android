package org.gson.bean;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Book {

	public String id;

	public String isbn10;

	public String isbn13;

	@SerializedName("title")
	public String title;

	public String originTitle;

	public String altTitle;
	public String subTitle;

	public String url;
	public String alt;
	public String image;
	public Image images;
	public List<String> author;

	public List<String> translator;

	public String publisher;

	public String pubdate;

	public Rating rating;

	public List<Tag> tags;

	public String binding;

	public String price;

	public String pages;

	public String authorIntro;

	public String summary;
}