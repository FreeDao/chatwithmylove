package com.cwml.RequestParam;

import java.io.PrintWriter;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

 
public class RequestParamAnalysis {
	
	private static final RequestParamAnalysis analysis = new RequestParamAnalysis();
	private RequestParam requestParam;

	private RequestParamAnalysis() {
		this.requestParam = new RequestParam();
	}
	
	public static RequestParamAnalysis getInstance() {
		return RequestParamAnalysis.analysis;
	}
	
 
	public RequestParam analysisRequestParam(String json) {
		
		RequestParam request = this.requestParam.clone();
		
		JSONObject jsonObject = JSONObject.fromObject(json);
		request.setUserName(jsonObject.getString("userName"));
		request.setPassword(jsonObject.getString("password"));
		request.setRandomkey(jsonObject.getString("randomKey") );
		request.setRequestType(jsonObject.getString("requestType"));
		JSONArray jsonArray = jsonObject.getJSONArray("params");
		String[] params = new String[jsonArray.size()];
		params = (String[]) jsonArray.toArray(params);
		request.setParams(params);
		
		return request;
	}
	
	
}
