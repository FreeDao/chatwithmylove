package com.cwmlapp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.cwml.classcontent.Diarycomment;
import com.cwml.network.utils.Request;
import com.cwml.network.utils.RequestParam;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.AsyncTask.Status;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ShowComment extends Activity {
	private ShowCommentTask mShowCommentTask;
	private SharedPreferences sp;
	
	
	private ListView dcommentList=null;
	private List<Map<String,String>> list=new ArrayList<Map<String,String>>();
	//private SimpleAdapter simpleAdapter=null;
	private MyAdapter mMyAdapter;
	private String diaryid;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.showcomment);
		Intent intent = getIntent();
		diaryid = intent.getStringExtra("diaryid");
		this.dcommentList=(ListView) super.findViewById(R.id.listview_comment);	
		mMyAdapter = new MyAdapter(this);
		getComment();
		
	}
	@Override
	public void onResume() {
	    super.onResume();  // Always call the superclass method first
	    // Get the Camera instance as the activity achieves full user focus
	   // getDiary();
	   // getComment();
	}
	public void onAddCommentClick2(View v) {
		Intent intent = new Intent();
		intent.putExtra("diaryid", diaryid);
        intent.setClass(ShowComment.this, AddCommentActivity.class);
        ShowComment.this.startActivity(intent);
        
        /*animate*/
        overridePendingTransition(R.anim.in_from_buttom, R.anim.out_to_up);
        
        ShowComment.this.finish();
        
	}
	public void getComment(){
		list.clear();
		sp = getSharedPreferences("userinfo", Context.MODE_PRIVATE);  
		RequestParam requestParam = new RequestParam();
		requestParam.setUserName(sp.getString("name", ""));
		requestParam.setPassword(sp.getString("password", ""));
		requestParam.setRequestType(requestParam.COMMENT);
		requestParam.setRandomKey("1234");
		requestParam.setParams(new String[]{"getComment",diaryid});
		
		//System.out.println("here getcomment:"+diaryid);
		
		mShowCommentTask = new ShowCommentTask();
		mShowCommentTask.execute(requestParam);
	}
	public void setAdapter(){
		Collections.reverse(list);
		
		
		dcommentList.setAdapter(mMyAdapter);
				
	}
	@Override
	protected void onDestroy() {
		if(mShowCommentTask != null && mShowCommentTask.getStatus() == Status.RUNNING) {
			mShowCommentTask.cancel(true);
		}
		list.clear();
		mMyAdapter=null;
		super.onDestroy();
	}
	
	public class ShowCommentTask extends AsyncTask<RequestParam, Integer, Boolean> {

		private ProgressDialog dialog;
	    private int responsemsg;
	    private List<Diarycomment> Commentcontent = new ArrayList<Diarycomment>();

		@Override
		protected void onPreExecute() {
			dialog = ProgressDialog.show(ShowComment.this, "",
					getText(R.string.diarywaiting));
			super.onPreExecute();
		}

		@Override
		protected Boolean doInBackground(RequestParam... param) {
			// TODO Auto-generated method stub
			RequestParam requestParam = param[0];
			
			String res = Request.request(requestParam.getJSON());
			//System.out.println("res:"+res);
			JSONObject jsonObject;
			try {
				jsonObject = new JSONObject(res);
				
          
           if((responsemsg=jsonObject.getInt("result")) == 2){
        	  
        	   
        	   JSONArray JA = jsonObject.getJSONArray("content");
        	   
        	  // System.out.println("test JA:"+JA.toString());
        	   
        	   for(int i=0;i<JA.length();i++){
        		   Diarycomment d = new Diarycomment();
        		   JSONObject js = new JSONObject();
        		   js = JA.getJSONObject(i);
        		  d.setDiaryid(js.getString("diaryid"));
        		  d.setCommentusername(js.getString("commentusername"));
        		  d.setTime(js.getString("time"));
        		  d.setContent(js.getString("content"));
        		 
        		  Commentcontent.add(d);
        		  System.out.println("testcontent:"+d.getContent());
        	//	   System.out.println("test1"+d.getUsername().toString());
        	   }
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
				
				for(Object o :Commentcontent){
					Diarycomment d = (Diarycomment)o;
		//diaryresult.append(d.getTitle()).append(d.getContent()).append(d.getUsername()).append(d.getTime()).append("\n");
					Map<String,String> map=new HashMap<String, String>();
					map.put("commentuser", d.getCommentusername());
					map.put("commenttime", d.getTime());
					map.put("commentcontent", d.getContent());
					
					
					list.add(map);
				}
				
				
				setAdapter();
				
				
			} else {
				Toast.makeText(getApplicationContext(), "¸üÐÂÆÀÂÛÊ§°Ü:"+responsemsg,Toast.LENGTH_SHORT).show();
			}
			super.onPostExecute(result);
			
		}

	}	
	private class MyAdapter extends BaseAdapter{
		
		private LayoutInflater inflater;
		
		public MyAdapter(Context context){
			this.inflater = LayoutInflater.from(context);
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder = null;
			if(convertView==null){
				holder = new ViewHolder();
				convertView = inflater.inflate(R.layout.commentlist, null);
				holder.content = (TextView)convertView.findViewById(R.id.commentcontent);
				holder.name = (TextView)convertView.findViewById(R.id.commentusername);
				holder.time = (TextView)convertView.findViewById(R.id.commenttime);
				
				convertView.setTag(holder);
			}
			else{
				holder = (ViewHolder)convertView.getTag();
			}
			
			holder.name.setText((list.get(position).get("commentuser")).toString());
			holder.content.setText((list.get(position).get("commentcontent")).toString());
			holder.time.setText((list.get(position).get("commenttime")).toString());
			
			
			return convertView;
		}
		
	}
	private static class ViewHolder
    {
        TextView name;
        TextView time;
        TextView content;
    }

}
