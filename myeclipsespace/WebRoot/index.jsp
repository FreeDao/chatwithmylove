<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@page import="com.sun.xml.internal.bind.v2.schemagen.xmlschema.Import"%>
<%@page import="com.cwml.daowrapper.*"%>
<%@page import="com.cwml.hibernate.User"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  <%
   UserDaoWrapper mUserDaoWrapper = UserDaoWrapper.getInstance();
	User user = mUserDaoWrapper.getUserByUserName("dick");
	CommentDAOWrapper mCommentDAOWrapper = CommentDAOWrapper.getInstance();
	int num = -1;
	if(mCommentDAOWrapper.queryAllDiaryComment().size() == 0){
				num =0;
				
			}
			else{
			 num = mCommentDAOWrapper.getDiaryCommentByDiaryId("27").size();
			
			}
   %>
  <body>
    This is my JSP page. my sex = <%= user.getPassword().toString()%>  comment number =<%=num %><br>
  </body>
</html>
