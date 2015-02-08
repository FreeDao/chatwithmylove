package com.cwml.RequestParam;

public class RequestParam {

	 
	private String userName;
	
	 
	private String password;
	
	 
	private String randomkey;
	 
	private String requestType;
	
	 
	private String[] params;

	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRandomkey() {
		return randomkey;
	}

	public void setRandomkey(String randomkey) {
		this.randomkey = randomkey;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public String[] getParams() {
		return params;
	}

	public void setParams(String[] params) {
		this.params = params;
	}
	
	@Override
	public RequestParam clone() {
		
		try {
			return (RequestParam) super.clone();
		} catch (CloneNotSupportedException e) {
			return new RequestParam();
		}
	}
}
