package com.cwml.hibernate;

/**
 * User entity. @author MyEclipse Persistence Tools
 */
public class User extends AbstractUser implements java.io.Serializable {

	// Constructors

	/** default constructor */
	public User() {
	}

	/** minimal constructor */
	public User(String username, Integer uid, String password) {
		super(username, uid, password);
	}

	/** full constructor */
	public User(String username, Integer uid, String password, String sex,
			String age) {
		super(username, uid, password, sex, age);
	}

}
