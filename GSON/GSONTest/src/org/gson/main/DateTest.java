package org.gson.main;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

import org.gson.bean.UserBean;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 
 * date測試類
 */

public class DateTest {
	/**
	 * 序列化方法
	 * 
	 * @param bean
	 * @param type
	 * @return
	 */
	public static String bean2json(Object bean, Type type) {
		Gson gson = new GsonBuilder()
				.registerTypeAdapter(java.util.Date.class,
						new UtilDateSerializer())
				.setDateFormat(DateFormat.LONG).create();
		return gson.toJson(bean);
	}

	/**
	 * 反序列化方法
	 * 
	 * @param json
	 * @param type
	 * @return
	 */
	public static <T> T json2bean(String json, Type type) {
		Gson gson = new GsonBuilder()
				.registerTypeAdapter(java.util.Date.class,
						new UtilDateDeserializer())
				.setDateFormat(DateFormat.LONG).create();
		return gson.fromJson(json, type);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<UserBean> testBeanList = new ArrayList<UserBean>();
		UserBean testBean = new UserBean();
		testBean.setId("id");
		testBean.setName("name");
		testBean.setBirthday(new java.util.Date());
		testBeanList.add(testBean);

		java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<List<UserBean>>() {
		}.getType();
		String beanListToJson = bean2json(testBeanList, type);
		System.out.println("beanListToJson:" + beanListToJson);
		// prints [{"id":"id","name":"name","birthday":1256531559390}]

		List<UserBean> testBeanListFromJson = json2bean(beanListToJson, type);
		System.out.println(testBeanListFromJson);
		// prints [TestBean@77a7f9[id=id,name=name,birthday=Mon Oct 26 12:39:05
		// CST 2009]]

	}

}
