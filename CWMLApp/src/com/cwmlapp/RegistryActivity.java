package com.cwmlapp;

import org.json.JSONException;
import org.json.JSONObject;

import com.cwml.network.utils.Request;
import com.cwml.network.utils.RequestParam;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.AsyncTask.Status;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegistryActivity extends Activity{
	
	private EditText name, password,sex,age;
	private SigninTask mSigninTask;
	private SharedPreferences sp;

	//private LoginTask mLoginTask;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registry);
		name = (EditText) findViewById(R.id.registry_name);
		password = (EditText) findViewById(R.id.registry_password);
		sex = (EditText) findViewById(R.id.registry_sex);
		age = (EditText) findViewById(R.id.registry_age);

		
	}
	public void onSigninAndLoginClick(View v) {
		if(TextUtils.isEmpty(name.getText().toString())) {
			name.setError(getString(R.string.no_empyt_name));
			return;
		}
		
		if(TextUtils.isEmpty(password.getText().toString())) {
			password.setError(getString(R.string.no_empty_password));
			return;
		}	
		
		
		RequestParam requestParam = new RequestParam();
		requestParam.setUserName(name.getText().toString());
		requestParam.setPassword(password.getText().toString());
		requestParam.setRequestType(requestParam.REGISTRY);
		requestParam.setRandomKey("1234");
		requestParam.setParams(new String[]{sex.getText().toString(),age.getText().toString()});

		mSigninTask = new SigninTask();
		mSigninTask.execute(requestParam);
		/*testXutils tx = new testXutils();
		tx.connect(requestParam.getJSON());*/
		
	}

	@Override
	protected void onDestroy() {
		if(mSigninTask != null && mSigninTask.getStatus() == Status.RUNNING) {
			mSigninTask.cancel(true);
		}
		super.onDestroy();
	}
	
	public class SigninTask extends AsyncTask<RequestParam, Integer, Boolean> {

		private ProgressDialog dialog;
	    int responsemsg;

		@Override
		protected void onPreExecute() {
			dialog = ProgressDialog.show(RegistryActivity.this, "",
					getText(R.string.registrywaiting));
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
           }} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
			
			return false;
		}

		@Override
		protected void onPostExecute(Boolean result) {			
			
			dialog.dismiss();
			
			if (result) {
				sp = getSharedPreferences("userinfo", Context.MODE_PRIVATE);   
                Editor edit = sp.edit();   
                edit.putString("name", name.getText().toString());   
                edit.putString("password", password.getText().toString());   
                edit.commit();
                
				startActivity(new Intent(RegistryActivity.this, MainActivity.class));
				finish();
			} else {
				Toast.makeText(getApplicationContext(), "×¢²áÊ§°Ü:"+responsemsg,Toast.LENGTH_SHORT).show();
			}
			super.onPostExecute(result);
			
		}

	}	
	
}
