package org.gson.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gson.bean.GroupBean;
import org.gson.bean.UserBean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 嵌套使用對象Bean
 * 
 * @author yansheng
 */

public class NestedTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		UserBean user1 = new UserBean();
		user1.setId("1001");
		user1.setName("张三");

		UserBean user2 = new UserBean();
		user2.setId("1002");
		user2.setName("李四");

		Map<String, UserBean> userMap = new HashMap<String, UserBean>();
		userMap.put("user1", user1);
		userMap.put("user2", user2);

		List<UserBean> userList = new ArrayList<UserBean>();
		userList.add(user1);
		userList.add(user2);

		GroupBean groupBean = new GroupBean();
		groupBean.setId("1");
		groupBean.setUserMap(userMap);
		groupBean.setUserList(userList);

		Gson gson = new Gson();

		String sGroupBean = gson.toJson(groupBean, new TypeToken<GroupBean>() {
		}.getType());
		System.out.println(sGroupBean);
		// {"id":"1","userMap":{"user2":{"id":"1002","name":"李四"},"user1":{"id":"1001","name":"张三"}},"userList":[{"id":"1001","name":"张三"},{"id":"1002","name":"李四"}]}

		// 将JSON字符串转换为 HashMap
		GroupBean groupBean2 = (GroupBean) gson.fromJson(sGroupBean,
				new TypeToken<GroupBean>() {
				}.getType());
		System.out.println(groupBean2);
		// 张三

	}

}
