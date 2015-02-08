package com.cwml.RequestHandle;

import com.cwml.RequestHandle.HandleRequest;
import com.cwml.daowrapper.UserDaoWrapper;
import com.cwml.hibernate.User;
import com.cwml.hibernate.UserDAO;

public class Login implements HandleRequest {

	String responseParam = null;
	public String getRequestType() {
		// TODO Auto-generated method stub
		return "Login";
	}

	public String getResponseParam() {
		// TODO Auto-generated method stub
		return responseParam==null?"":responseParam;
	}

	public int handleRequest(String name, String password, String... param) {
		
		UserDaoWrapper mUserDaoWrapper = UserDaoWrapper.getInstance();
		User user = mUserDaoWrapper.getUserByUserName(name);
		int result = -1;
		if( user.getPassword().equals(password) ){
			result = 1;
			responseParam = "login success!";
			}
		else{
			result = 0;
			responseParam = "login failed!";
		}
		return result;
		// TODO Auto-generated method stub
		
	}

}
