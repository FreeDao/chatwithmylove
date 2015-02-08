package com.cwml.RequestHandle;

/**
 *  
 * @author Administrator
 *
 */
public class HandleRequestFactory {
	
	// AddTopic
	public static HandleRequest getHandleRequestInstance(String requestType){
		HandleRequest handleRequest = null;
		/* 
		 *  
		 */
		try {
			 
			handleRequest = (HandleRequest) Class.forName(
							"com.cwml.RequestHandle." 
							+ requestType)
							.newInstance();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return handleRequest;
	}
}
