package com.cwml.RequestHandle;

import java.util.List;

import net.sf.json.JSONArray;

import com.cwml.daowrapper.CommentDAOWrapper;
import com.cwml.daowrapper.DiaryDAOWrapper;
import com.cwml.hibernate.Diary;
import com.cwml.hibernate.Diarycomment;
import com.cwml.utils.Utils;

public class CommentHandle implements HandleRequest  {

	String responseParam = null;
	public String getRequestType() {
		// TODO Auto-generated method stub
		return "Diary";
	}

	public String getResponseParam() {
		// TODO Auto-generated method stub
		return responseParam==null?"":responseParam;
	}

	public int handleRequest(String name, String password, String... param) {
		// TODO Auto-generated method stub
		int result= -1;
		CommentDAOWrapper mCommentDAOWrapper = CommentDAOWrapper.getInstance();
		int index = mCommentDAOWrapper.queryAllDiaryComment().size();
		
		if(param[0].equals("addComment")){
			String diaryid = param[1];
			String commentusername = param[2];
			String content = param[3];
			String time = Utils.getCurrentTime();
			Diarycomment newcomment = new Diarycomment(diaryid, commentusername,time,content);
			System.out.println("test:"+newcomment.toString());
			mCommentDAOWrapper.addComment(newcomment);
			int newIndex = mCommentDAOWrapper.queryAllDiaryComment().size();
			result = ((newIndex-index)==1)?1:-1;
			responseParam = ((newIndex-index)==1)?"add comment success!":"add comment failed!";
		}
		
		else if (param[0].equals("getComment")) {
			 List CommentList = mCommentDAOWrapper.getDiaryCommentByDiaryId(param[1]);
			// JSONObject diarynote = new JSONObject();
			 JSONArray jsonArray = new JSONArray();
			 for(Object o: CommentList){
				 Diarycomment d = (Diarycomment)o;
				 jsonArray.element(d);
				 /*JSONObject JO = new JSONObject();
				 JO.put("Title", d.getTitle());
				 JO.put("Content", d.getContent());
				 JO.put("Time", d.getTime());
				 JO.put("UserName", d.getUsername());
				 JO.put("other", d.getOther());*/
			 }
			// diarynote.put("diarynote", jsonArray);
			 responseParam = jsonArray.toString();
			 result = 2;
		}
		else if (param[0].equals("getCommentNum")) {
			if(mCommentDAOWrapper.queryAllDiaryComment().size() == 0){
				responseParam = "0";
				result = 3;
			}
			else{
			int num = mCommentDAOWrapper.getDiaryCommentByDiaryId(param[1]).size();
			responseParam = ""+num; 
			result = 3;
			}
		}
		return result;
	}
}
