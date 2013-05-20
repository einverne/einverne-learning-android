package org.gson.bean;

import java.util.Date;

import com.google.gson.annotations.Expose;

/**
 * 普通的UserBean
 */
public class UserBean {
	@Expose
	// 并不是java标准的注解，而是谷歌GSON的注解，表示它所注解的字段不支持序列化。
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
