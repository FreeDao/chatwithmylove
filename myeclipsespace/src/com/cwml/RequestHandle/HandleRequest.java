package com.cwml.RequestHandle;

public interface HandleRequest {
	
	/**
	 *  
	 * @param name
	 * @param password
	 * @param param
	 * @return
	 */
	public int handleRequest(String name, String password, String... param);
	
	/**
	 *  
	 * @return
	 */
	public String getRequestType();
	
	/**
	 *  
	 * @return
	 */
	public String getResponseParam();
}
