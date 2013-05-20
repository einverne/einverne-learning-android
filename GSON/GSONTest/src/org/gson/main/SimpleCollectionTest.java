package org.gson.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 簡單的集合使用
 * 
 * @author yansheng
 */

public class SimpleCollectionTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Gson gson = new Gson();

		// 将ArrayList字符串转换为 JSON
		List<String> testList = new ArrayList<String>();
		testList.add("first");
		testList.add("second");

		String listToJson = gson.toJson(testList);
		System.out.println(listToJson);
		// prints ["first","second"]

		// 将JSON字符串转换为 ArrayList
		@SuppressWarnings("unchecked")
		List<String> testList2 = (List<String>) gson.fromJson(listToJson,
				new TypeToken<List<String>>() {
				}.getType());
		System.out.println(testList2);

		// 将HashMap字符串转换为 JSON
		Map<String, String> testMap = new HashMap<String, String>();
		testMap.put("id", "id.first");
		testMap.put("name", "name.second");

		String mapToJson = gson.toJson(testMap);
		System.out.println(mapToJson);
		// prints {"id":"id.first","name":"name.second"}

		// 将JSON字符串转换为 HashMap
		@SuppressWarnings("unchecked")
		Map<String, String> userMap2 = (Map<String, String>) gson.fromJson(
				mapToJson, new TypeToken<Map<String, String>>() {
				}.getType());
		System.out.println(userMap2.get("id"));

	}

}
