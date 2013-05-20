package org.gson.main;

import java.lang.reflect.Type;
import java.util.Date;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * ´®ÐÐData
 */

public class UtilDateSerializer implements JsonSerializer<java.util.Date> {

	@Override
	public JsonElement serialize(Date src, Type typeOfSrc,
			JsonSerializationContext context) {
		// TODO Auto-generated method stub
		return new JsonPrimitive(src.getTime());
	}

}
