package com.cwml;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cwml.RequestHandle.HandleRequest;
import com.cwml.RequestHandle.HandleRequestFactory;
import com.cwml.RequestParam.RequestParam;
import com.cwml.RequestParam.RequestParamAnalysis;
import com.cwml.ResponseParam.BulidResponseParam;
import com.cwml.utils.Utils;


public class cwml extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		try {
			
			String json = request.getParameter("requestParam");
			json = new String(json.getBytes("ISO8859_1"), "utf-8");
			//System.out.println(json+"#");
			

			
			if(json != null){
				
				 
				RequestParam requestParam = RequestParamAnalysis.getInstance().analysisRequestParam(json);
				
				 
				String name = requestParam.getUserName(); 
				String password = requestParam.getPassword(); 
				String requestType = requestParam.getRequestType();
				String[] params = requestParam.getParams();
				
				 /*System.out.println(name);
				 System.out.println(password);
				 System.out.println(requestType);
				 System.out.println(params[0].toString());*/
				
				String responseParam = "";
				if(requestType.equals("Login")){
					// System.out.println("** here");
					HandleRequest handle = HandleRequestFactory.getHandleRequestInstance(requestType);
					int result = handle.handleRequest((Utils.isNull(name)?"null":name), password, params);
					String param = handle.getResponseParam();
					responseParam = BulidResponseParam.getInstance().bulidParam(result, requestType, param);
				}
				else if(requestType.equals("Registry")){
					HandleRequest handle = HandleRequestFactory.getHandleRequestInstance(requestType);
					int result = handle.handleRequest((Utils.isNull(name)?"null":name), password, params);
					String param = handle.getResponseParam();
					responseParam = BulidResponseParam.getInstance().bulidParam(result, requestType, param);
				}
				else if(requestType.equals("DiaryHandle")){
					HandleRequest handle = HandleRequestFactory.getHandleRequestInstance(requestType);
					int result = handle.handleRequest((Utils.isNull(name)?"null":name), password, params);
					String param = handle.getResponseParam();
					responseParam = BulidResponseParam.getInstance().bulidParam(result, requestType, param);
				}
				else if(requestType.equals("CommentHandle")){
					HandleRequest handle = HandleRequestFactory.getHandleRequestInstance(requestType);
					int result = handle.handleRequest((Utils.isNull(name)?"null":name), password, params);
					String param = handle.getResponseParam();
					responseParam = BulidResponseParam.getInstance().bulidParam(result, requestType, param);
				}
				response.setContentType("text/html");
				response.setCharacterEncoding( "utf-8" );
				
				System.out.println("cwml-response:"+responseParam.toString());
				
				PrintWriter out = response.getWriter();
				out.println(responseParam);
				out.flush();
				out.close();
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("book errer:" + e.toString());
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
	
}
