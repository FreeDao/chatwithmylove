package com.cwmlapp;

import org.json.JSONException;
import org.json.JSONObject;

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

import com.cwml.network.utils.Request;
import com.cwml.network.utils.RequestParam;

public class AddDiaryActivity extends Activity{
	
	private EditText title, content;
	private AddDiaryTask mAddDiaryTask;
	private SharedPreferences sp;

	//private LoginTask mLoginTask;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.adddiary);
		title = (EditText) findViewById(R.id.editText_diarytitle);
		content = (EditText) findViewById(R.id.editText_diarycontent);

		
	}
	public void onAddDiaryClick(View v) {
		if(TextUtils.isEmpty(title.getText().toString())) {
			title.setError(getString(R.string.no_empty_title));
			return;
		}
		
		if(TextUtils.isEmpty(content.getText().toString())) {
			content.setError(getString(R.string.no_empty_content));
			return;
		}	
		
		sp = getSharedPreferences("userinfo", Context.MODE_PRIVATE);  
		RequestParam requestParam = new RequestParam();
		requestParam.setUserName(sp.getString("name", ""));
		requestParam.setPassword(sp.getString("password", ""));
		requestParam.setRequestType(requestParam.DIARY);
		requestParam.setRandomKey("1234");
		requestParam.setParams(new String[]{"addDiary",title.getText().toString(),content.getText().toString(),"zan"});

		mAddDiaryTask = new AddDiaryTask();
		mAddDiaryTask.execute(requestParam);
	}
	
	@Override
	protected void onDestroy() {
		if(mAddDiaryTask != null && mAddDiaryTask.getStatus() == Status.RUNNING) {
			mAddDiaryTask.cancel(true);
		}
		super.onDestroy();
	}
	
	public class AddDiaryTask extends AsyncTask<RequestParam, Integer, Boolean> {

		private ProgressDialog dialog;
	    private int responsemsg;
	  
		@Override
		protected void onPreExecute() {
			dialog = ProgressDialog.show(AddDiaryActivity.this, "",
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
				title.setText(null);
				content.setText(null);
				Toast.makeText(getApplicationContext(), "发表日志成功:"+responsemsg,Toast.LENGTH_SHORT).show();
				startActivity(new Intent(AddDiaryActivity.this, MainActivity.class));
				
				AddDiaryActivity.this.finish();
				
			} else {
				Toast.makeText(getApplicationContext(), "发表日志失败:"+responsemsg,Toast.LENGTH_SHORT).show();
			}
			super.onPostExecute(result);
			
		}

	}	

}
