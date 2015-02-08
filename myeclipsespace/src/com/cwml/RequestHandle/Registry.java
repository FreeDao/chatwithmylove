package com.cwml.RequestHandle;

import java.util.List;

import com.cwml.daowrapper.UserDaoWrapper;
import com.cwml.hibernate.User;

public class Registry implements HandleRequest {
	
	String responseParam = null;
	public String getRequestType() {
		// TODO Auto-generated method stub
		return "Registry";
	}

	public String getResponseParam() {
		// TODO Auto-generated method stub
		return responseParam==null?"":responseParam;
	}

	public int handleRequest(String name, String password, String... param) {
		// TODO Auto-generated method stub
		UserDaoWrapper mUserDaoWrapper = UserDaoWrapper.getInstance();
		User user = mUserDaoWrapper.getUserByUserName(name);
		int result = -1;
		if( !(user==null) ){           //判断是否存在已注册的用户
			result = -2;
			responseParam = "already have this name!";
			}
		else{
			
			List allusers = mUserDaoWrapper.queryAllUser();
			int uid = allusers.size()+1;
			mUserDaoWrapper.addUser(new User(name,uid,password, param[0],param[1]));
			
			User newUser = (User)mUserDaoWrapper.getVipByUserId(uid).get(0);
			if(newUser.getUsername().equals(name)){
				result = 1;
				responseParam = "Signin success!";	
			}
			else{
				result = 0;
				responseParam = "Signin failed!";
			}
			
		}
		return result;
	}

}
