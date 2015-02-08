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

public class LoginActivity extends Activity {

	
	private EditText name, password;
	private LoginTask mLoginTask;
	private SharedPreferences sp;

	//private LoginTask mLoginTask;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		name = (EditText) findViewById(R.id.login_name);
		password = (EditText) findViewById(R.id.login_password);
		sp = getSharedPreferences("userinfo", Context.MODE_PRIVATE);  
		name.setText(sp.getString("name", ""));
		password.setText(sp.getString("password", ""));
		
	}
	public void onLoginClick(View v) {
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
		requestParam.setRequestType(requestParam.LOGIN);
		requestParam.setRandomKey("1234");
		requestParam.setParams(new String[]{""});

		mLoginTask = new LoginTask();
		mLoginTask.execute(requestParam);
		/*testXutils tx = new testXutils();
		tx.connect(requestParam.getJSON());*/
		
	}
	public void onSigninClick(View v) {
		startActivity(new Intent(LoginActivity.this, RegistryActivity.class));
	}
	@Override
	protected void onDestroy() {
		if(mLoginTask != null && mLoginTask.getStatus() == Status.RUNNING) {
			mLoginTask.cancel(true);
		}
		super.onDestroy();
	}
	
	public class LoginTask extends AsyncTask<RequestParam, Integer, Boolean> {

		private ProgressDialog dialog;
	    

		@Override
		protected void onPreExecute() {
			dialog = ProgressDialog.show(LoginActivity.this, "",
					getText(R.string.waiting));
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
			  
           if(jsonObject.getInt("result") == 1){
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
				
				startActivity(new Intent(LoginActivity.this, MainActivity.class));
				finish();
			} else {
				Toast.makeText(getApplicationContext(), "µÇÂ¼Ê§°Ü",Toast.LENGTH_SHORT).show();
			}
			super.onPostExecute(result);
			
		}

	}	
	
	
	
}
