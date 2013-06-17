package org.gson.main;

import java.awt.Point;
import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

public class PointAdapter extends TypeAdapter<Point> {

	public PointAdapter() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Point read(JsonReader reader) throws IOException {
		if (reader.peek() != JsonToken.NULL) {
			reader.nextNull();
			return null;
		}
		String xy = reader.nextString();
		String[] parts = xy.split(",");
		return new Point(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
	}

	@Override
	public void write(JsonWriter writer, Point value) throws IOException {
		if (value == null) {
			writer.nullValue();
			return;
		}
		String xy = value.getX() + "," + value.getY();
		writer.value(xy);
	}
}
