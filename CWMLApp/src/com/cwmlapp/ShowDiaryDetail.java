package com.cwmlapp;


import org.json.JSONException;
import org.json.JSONObject;

import com.cwml.network.utils.Request;
import com.cwml.network.utils.RequestParam;
import com.cwmlapp.AddDiaryActivity.AddDiaryTask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.AsyncTask.Status;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ShowDiaryDetail extends Activity {
	private DetailDiaryTask mDetailDiaryTask;
	private CommentNumTask mCommentNumTask;
	private SharedPreferences sp;
	private TextView showTitle,showName,showTime,showContent,showCommentNum;
	private String idnum;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.diarycontent);
		Intent intent = getIntent();
		idnum = intent.getStringExtra("idnum");
		
		
		showTitle = (TextView)super.findViewById(R.id.textView_showtitle);
		showName = (TextView)super.findViewById(R.id.textView_showname);
		showTime = (TextView)super.findViewById(R.id.textView_showtime);
		showContent = (TextView)super.findViewById(R.id.textView_showcontent);
		showCommentNum = (TextView)super.findViewById(R.id.textView_showcommentnum);
		
		
		
		
		
		mDetailDiaryTask = new DetailDiaryTask();
		mDetailDiaryTask.execute(idnum);
		getcommentnum();
		
		
	}
	public void getcommentnum(){
		sp = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
		RequestParam requestParam = new RequestParam();
		requestParam.setUserName(sp.getString("name", ""));
		requestParam.setPassword(sp.getString("password", ""));
		requestParam.setRequestType(requestParam.COMMENT);
		requestParam.setRandomKey("1234");
		requestParam.setParams(new String[]{"getCommentNum",idnum});
		
		mCommentNumTask = new CommentNumTask();
		mCommentNumTask.execute(requestParam);
	}
	public void onAddCommentClick(View v) {
		Intent intent = new Intent();
		intent.putExtra("diaryid", idnum);
        intent.setClass(ShowDiaryDetail.this, AddCommentActivity.class);
        ShowDiaryDetail.this.startActivity(intent);
        
        /*animate*/
        overridePendingTransition(R.anim.in_from_buttom, R.anim.out_to_up);
        this.finish();
	}
	
	public void onShowCommentClick(View v) {
		Intent intent = new Intent();
		intent.putExtra("diaryid", idnum);
        intent.setClass(ShowDiaryDetail.this, ShowComment.class);
        ShowDiaryDetail.this.startActivity(intent);
        
	}
	@Override
	public void onResume() {
	    super.onResume();  // Always call the superclass method first
	    // Get the Camera instance as the activity achieves full user focus
	   // getDiary();
	    getcommentnum();
	}
	@Override
	protected void onDestroy() {
		if(mDetailDiaryTask != null && mDetailDiaryTask.getStatus() == Status.RUNNING) {
			mDetailDiaryTask.cancel(true);
		}
		if(mCommentNumTask != null && mCommentNumTask.getStatus() == Status.RUNNING) {
			mCommentNumTask.cancel(true);
		}
		super.onDestroy();
	}
	
	public class DetailDiaryTask extends AsyncTask<String, Integer, Boolean> {

		private ProgressDialog dialog;
	    private int responsemsg;
	    

		@Override
		protected void onPreExecute() {
			dialog = ProgressDialog.show(ShowDiaryDetail.this, "",
					getText(R.string.diarywaiting));
			super.onPreExecute();
		}

		@Override
		protected Boolean doInBackground(String... param) {
			// TODO Auto-generated method stub
			String dairyid = param[0];
			
			
			sp = getSharedPreferences("dairyinfo", Context.MODE_PRIVATE);  	
          
           if(sp.getString("id", "").equals(dairyid)){
        	  showTitle.setText(sp.getString("title", ""));
        	  showTime.setText(sp.getString("time", ""));
        	  showName.setText(sp.getString("name", ""));
        	  showContent.setText(sp.getString("content", ""));
        	   
        	  System.out.println("no here?");
        	   return true;
           }
           
			
			 
			
			
			return false;
		}

		@Override
		protected void onPostExecute(Boolean result) {			
			
			dialog.dismiss();
			//StringBuilder diaryresult = new StringBuilder() ;
			if (!result) {
				Toast.makeText(getApplicationContext(), "更新日志失败:"+responsemsg,Toast.LENGTH_SHORT).show();
			}
			super.onPostExecute(result);
			
		}

	}	
	
	public class CommentNumTask extends AsyncTask<RequestParam, Integer, Boolean> {

		
	    private String responsemsg;
	    

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Boolean doInBackground(RequestParam... param) {
			// TODO Auto-generated method stub
			RequestParam requestParam = param[0];
			String res = Request.request(requestParam.getJSON());
			JSONObject jsonObject;
			 	
          
			try {
				jsonObject = new JSONObject(res);
				responsemsg = jsonObject.getString("content");
			  
           if(jsonObject.getInt("result") == 3 ){
        	   
        	   
        	   return true;
           }} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
           
			
			 
			
			
			return false;
		}

		@Override
		protected void onPostExecute(Boolean result) {			
			
			
			if (result){
				showCommentNum.setText(responsemsg+"条评论");
	        	System.out.println("number:"+responsemsg);
			}
			else {
				Toast.makeText(getApplicationContext(), "获取评论数失败:",Toast.LENGTH_SHORT).show();
			}
			super.onPostExecute(result);
			
		}

	}	
}
