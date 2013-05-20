package org.gson.bean;

import java.util.Date;

import com.google.gson.annotations.Expose;

/**
 * ��ͨ��UserBean
 */
public class UserBean {
	@Expose
	// ������java��׼��ע�⣬���ǹȸ�GSON��ע�⣬��ʾ����ע����ֶβ�֧�����л���
	private String id;
	@Expose
	private String name;

	private Date birthday;

	public Date getBirthday() {
		return birthday;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "UserBean [birthday=" + birthday + ", id=" + id + ", name="
				+ name + "]";
	}

}
