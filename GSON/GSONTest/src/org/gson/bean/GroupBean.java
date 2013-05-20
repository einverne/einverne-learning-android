package org.gson.bean;

import java.util.List;
import java.util.Map;

/**
 * 包含Arrylist和HashMap,里面保持UserBean
 */
public class GroupBean {
	private String id;
	private Map<String, UserBean> userMap;
	private List<UserBean> userList;

	public String getId() {
		return id;
	}

	public List<UserBean> getUserList() {
		return userList;
	}

	public Map<String, UserBean> getUserMap() {
		return userMap;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setUserList(List<UserBean> userList) {
		this.userList = userList;
	}

	public void setUserMap(Map<String, UserBean> userMap) {
		this.userMap = userMap;
	}

	@Override
	public String toString() {
		return "GroupBean [id=" + id + ", userList=" + userList + ", userMap="
				+ userMap + "]";
	}

}
