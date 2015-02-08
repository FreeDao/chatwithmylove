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
import android.widget.EditText;
import android.widget.Toast;

public class AddCommentActivity extends Activity {
	
	private EditText  content;
	private AddCommentTask mAddCommentTask;
	private SharedPreferences sp;
	private String diaryid;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.makecomment);
		content = (EditText) findViewById(R.id.editText_comment);
		Intent intent = getIntent();
		diaryid = intent.getStringExtra("diaryid");
		
	}
	
	public void onAddCommentClick(View v) {
				
		if(TextUtils.isEmpty(content.getText().toString())) {
			content.setError(getString(R.string.no_empty_content));
			return;
		}	
	
		sp = getSharedPreferences("userinfo", Context.MODE_PRIVATE);  
		RequestParam requestParam = new RequestParam();
		requestParam.setUserName(sp.getString("name", ""));
		requestParam.setPassword(sp.getString("password", ""));
		requestParam.setRequestType(requestParam.COMMENT);
		requestParam.setRandomKey("1234");
		requestParam.setParams(new String[]{"addComment",diaryid,sp.getString("name", "").toString(),content.getText().toString()});

		
		mAddCommentTask = new AddCommentTask();
		mAddCommentTask.execute(requestParam);
	}
	
	@Override
	protected void onDestroy() {
		if(mAddCommentTask != null && mAddCommentTask.getStatus() == Status.RUNNING) {
			mAddCommentTask.cancel(true);
		}
		super.onDestroy();
	}
	
	public class AddCommentTask extends AsyncTask<RequestParam, Integer, Boolean> {

		private ProgressDialog dialog;
	    private int responsemsg;
	  
		@Override
		protected void onPreExecute() {
			dialog = ProgressDialog.show(AddCommentActivity.this, "",
					getText(R.string.diarywritting));
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
				
           if((responsemsg=jsonObject.getInt("result")) == 1){
        	   
        	  
        	   return true;
           }
         
           
			
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
			
			return false;
		}

		@Override
		protected void onPostExecute(Boolean result) {			
			
			dialog.dismiss();
			
			if (result) {
				
				content.setText(null);
				Toast.makeText(getApplicationContext(), "发表评论成功:"+responsemsg,Toast.LENGTH_SHORT).show();
				Intent intent = new Intent();
				intent.putExtra("diaryid", diaryid);
		        intent.setClass(AddCommentActivity.this, ShowComment.class);
		        AddCommentActivity.this.startActivity(intent);
		        
		        AddCommentActivity.this.finish();
				
			} else {
				Toast.makeText(getApplicationContext(), "发表评论失败:"+responsemsg,Toast.LENGTH_SHORT).show();
			}
			super.onPostExecute(result);
			
		}

	}	

}
