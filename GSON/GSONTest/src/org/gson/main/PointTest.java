package org.gson.main;

import java.awt.Point;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class PointTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Gson gson = new Gson();
		Gson gson1 = new GsonBuilder().registerTypeAdapter(Point.class,
				new PointAdapter()).create();
		System.out.println(gson.toJson(new Point(0, 1)));
		System.out.println(gson1.toJson(new Point(0, 1)));
	}

}
