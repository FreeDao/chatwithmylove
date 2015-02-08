package com.cwml.hibernate;

/**
 * AbstractUser entity provides the base persistence definition of the User
 * entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractUser implements java.io.Serializable {

	// Fields

	private String username;
	private Integer uid;
	private String password;
	private String sex;
	private String age;

	// Constructors

	/** default constructor */
	public AbstractUser() {
	}

	/** minimal constructor */
	public AbstractUser(String username, Integer uid, String password) {
		this.username = username;
		this.uid = uid;
		this.password = password;
	}

	/** full constructor */
	public AbstractUser(String username, Integer uid, String password,
			String sex, String age) {
		this.username = username;
		this.uid = uid;
		this.password = password;
		this.sex = sex;
		this.age = age;
	}

	// Property accessors

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getUid() {
		return this.uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAge() {
		return this.age;
	}

	public void setAge(String age) {
		this.age = age;
	}

}