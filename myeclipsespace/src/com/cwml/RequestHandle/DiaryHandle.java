package com.cwml.RequestHandle;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.cwml.daowrapper.DiaryDAOWrapper;
import com.cwml.hibernate.Diary;
import com.cwml.utils.Utils;

public class DiaryHandle implements HandleRequest  {

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
		DiaryDAOWrapper mDiaryDAOWrapper = DiaryDAOWrapper.getInstance();
		int index = mDiaryDAOWrapper.queryAllDiary().size();
		
		if(param[0].equals("addDiary")){
			String title = param[1];
			String content = param[2];
			String time = Utils.getCurrentTime();
			Diary newDiary = new Diary(content, title, time, name, param[3]);
			mDiaryDAOWrapper.addDiary(newDiary);
			int newIndex = mDiaryDAOWrapper.queryAllDiary().size();
			result = ((newIndex-index)==1)?1:-1;
			responseParam = ((newIndex-index)==1)?"add diary success!":"add diary failed!";
		}
		else if(param[0].equals("commentDiary")){
			
		}
		else if (param[0].equals("getDiary")) {
			 List diaryList = mDiaryDAOWrapper.queryAllDiary();
			// JSONObject diarynote = new JSONObject();
			 JSONArray jsonArray = new JSONArray();
			 for(Object o: diaryList){
				 Diary d = (Diary)o;
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
		
		return result;
	}
}