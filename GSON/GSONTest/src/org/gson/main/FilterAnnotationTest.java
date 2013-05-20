package org.gson.main;

import org.gson.bean.UserBean;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 
 * ͨ�^�]��^�V�D�Q����
 */

public class FilterAnnotationTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		UserBean user = new UserBean();
		user.setId("1001");
		user.setName("����");

		/**
		 * ����ע�͹��˹��� Gson gson = new GsonBuilder() .registerTypeAdapter(Id.class,
		 * new IdTypeAdapter()) .serializeNulls()
		 * .setDateFormat(DateFormat.LONG)
		 * .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
		 * .setPrettyPrinting() .setVersion(1.0) .create();
		 */
		GsonBuilder builder = new GsonBuilder();
		// ��ת��û�� @Expose ע����ֶ�
		builder.excludeFieldsWithoutExposeAnnotation();
		Gson gson = builder.create();

		// ��JavaBean�ַ���ת��Ϊ JSON
		String sUser = gson.toJson(user);
		System.out.println(sUser);
		// {"id":1001,"name":"����"}

		// ��JSON�ַ���ת��Ϊ JavaBean
		UserBean user2 = gson.fromJson(sUser, UserBean.class);
		System.out.println(user2.getId() + ", " + user2.getName());
		// 1001, ����
	}

}
