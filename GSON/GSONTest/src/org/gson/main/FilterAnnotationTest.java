package org.gson.main;

import org.gson.bean.UserBean;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 
 * 通過註釋過濾轉換對象
 */

public class FilterAnnotationTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		UserBean user = new UserBean();
		user.setId("1001");
		user.setName("张三");

		/**
		 * 设置注释过滤功能 Gson gson = new GsonBuilder() .registerTypeAdapter(Id.class,
		 * new IdTypeAdapter()) .serializeNulls()
		 * .setDateFormat(DateFormat.LONG)
		 * .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
		 * .setPrettyPrinting() .setVersion(1.0) .create();
		 */
		GsonBuilder builder = new GsonBuilder();
		// 不转换没有 @Expose 注解的字段
		builder.excludeFieldsWithoutExposeAnnotation();
		Gson gson = builder.create();

		// 将JavaBean字符串转换为 JSON
		String sUser = gson.toJson(user);
		System.out.println(sUser);
		// {"id":1001,"name":"张三"}

		// 将JSON字符串转换为 JavaBean
		UserBean user2 = gson.fromJson(sUser, UserBean.class);
		System.out.println(user2.getId() + ", " + user2.getName());
		// 1001, 张三
	}

}
