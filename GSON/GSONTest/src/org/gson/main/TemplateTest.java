package org.gson.main;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.gson.bean.UserBean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * ƒ£∞Â∑¥…‰å¶œÛBean
 * 
 * @author yansheng
 */

public class TemplateTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<UserBean> testBeanList = new ArrayList<UserBean>();
		UserBean testBean = new UserBean();
		testBean.setId("1");
		testBean.setName("name");
		testBeanList.add(testBean);

		Gson gson = new Gson();
		Type type = new TypeToken<List<UserBean>>() {
		}.getType();

		String beanListToJson = gson.toJson(testBeanList, type);
		System.out.println(beanListToJson);
		// prints [{"id":"id","name":"name"}]

		List<UserBean> testBeanListFromJson = gson.fromJson(beanListToJson,
				type);
		System.out.println(testBeanListFromJson);
		// prints [TestBean@1ea5671[id=id,name=name,birthday=<null>]]

	}

}
