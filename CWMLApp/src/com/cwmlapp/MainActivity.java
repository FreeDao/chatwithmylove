package com.cwmlapp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.cwml.classcontent.Diary;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	//private EditText showDiary;
	private DiaryTask mDiaryTask;
	private SharedPreferences sp;
	
	private ListView dataList=null;
	private List<Map<String,String>> list=new ArrayList<Map<String,String>>();
	//private SimpleAdapter simpleAdapter=null;
	private MyAdapter mMyAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		//showDiary = (EditText) findViewById(R.id.editText_getdiarycontent);
		this.dataList=(ListView) super.findViewById(R.id.listview_dairyinfo);
		
		
		
		mMyAdapter = new MyAdapter(this);
		getDiary();
		
		
		this.dataList.setOnItemClickListener(new OnItemClickListenerImpl());
	}
	@Override
	public void onResume() {
	    super.onResume();  // Always call the superclass method first
	    // Get the Camera instance as the activity achieves full user focus
	   // getDiary();
	  
	}
	public void getDiary(){
		list.clear();
		sp = getSharedPreferences("userinfo", Context.MODE_PRIVATE);  
		RequestParam requestParam = new RequestParam();
		requestParam.setUserName(sp.getString("name", ""));
		requestParam.setPassword(sp.getString("password", ""));
		requestParam.setRequestType(requestParam.DIARY);
		requestParam.setRandomKey("1234");
		requestParam.setParams(new String[]{"getDiary"});
		mDiaryTask = new DiaryTask();
		mDiaryTask.execute(requestParam);
	}
	
	public void onAddNewDiaryClick(View v) {
		startActivity(new Intent(MainActivity.this, AddDiaryActivity.class));
		this.finish();
	}
	public void onGetAllDiaryClick(View v) {
		this.getDiary();
	}
	public void setAdapter(){
		Collections.reverse(list);
		/*simpleAdapter=new SimpleAdapter(
				this, 
				list, 
				R.layout.diarylist, 
				new String[]{"title","name","time"}, 
				new int[]{R.id.gettitle,R.id.getusername,R.id.gettime}
				);
				dataList.setAdapter(simpleAdapter);*/
		
		dataList.setAdapter(mMyAdapter);
				
	}
	@Override
	protected void onDestroy() {
		if(mDiaryTask != null && mDiaryTask.getStatus() == Status.RUNNING) {
			mDiaryTask.cancel(true);
		}
		list.clear();
		mMyAdapter=null;
		super.onDestroy();
	}
	
	public class DiaryTask extends AsyncTask<RequestParam, Integer, Boolean> {

		private ProgressDialog dialog;
	    private int responsemsg;
	    private List<Diary> diarycontent = new ArrayList<Diary>();

		@Override
		protected void onPreExecute() {
			dialog = ProgressDialog.show(MainActivity.this, "",
					getText(R.string.diarywaiting));
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
				
          
           if((responsemsg=jsonObject.getInt("result")) == 2){
        	  
        	   
        	   JSONArray JA = jsonObject.getJSONArray("content");
        	   
        	  // System.out.println("test JA:"+JA.toString());
        	   
        	   for(int i=0;i<JA.length();i++){
        		   Diary d = new Diary();
        		   JSONObject js = new JSONObject();
        		   js = JA.getJSONObject(i);
        		  d.setId(js.getInt("id"));
        		  d.setTitle(js.getString("title"));
        		  d.setContent(js.getString("content"));
        		  d.setTime(js.getString("time"));
        		  d.setUsername(js.getString("username"));
        		  d.setOther(js.getString("other"));
        		   diarycontent.add(d);
        		  
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
			//StringBuilder diaryresult = new StringBuilder() ;
			if (result) {
				
				for(Object o :diarycontent){
					Diary d = (Diary)o;
		//diaryresult.append(d.getTitle()).append(d.getContent()).append(d.getUsername()).append(d.getTime()).append("\n");
					Map<String,String> map=new HashMap<String, String>();
					map.put("title", d.getTitle());
					map.put("name", d.getUsername());
					map.put("time", d.getTime());
					map.put("id", d.getId().toString());
					map.put("content", d.getContent());
					
					list.add(map);
				}
				//showDiary.setText(diaryresult);
				
				setAdapter();
				
				
			} else {
				Toast.makeText(getApplicationContext(), "更新日志失败:"+responsemsg,Toast.LENGTH_SHORT).show();
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
				convertView = inflater.inflate(R.layout.diarylist, null);
				holder.title = (TextView)convertView.findViewById(R.id.gettitle);
				holder.name = (TextView)convertView.findViewById(R.id.getusername);
				holder.time = (TextView)convertView.findViewById(R.id.gettime);
				
				convertView.setTag(holder);
			}
			else{
				holder = (ViewHolder)convertView.getTag();
			}
			
			holder.title.setText((list.get(position).get("title")).toString());
			holder.name.setText((list.get(position).get("name")).toString());
			holder.time.setText((list.get(position).get("time")).toString());
			
			
			return convertView;
		}
		
	}
	private static class ViewHolder
    {
        TextView title;
        TextView name;
        TextView time;
    }
	private class OnItemClickListenerImpl implements OnItemClickListener {


		
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
		long id) {
			@SuppressWarnings("unchecked")
			Map<String, String> map = (Map<String, String>) MainActivity.this.mMyAdapter
					.getItem(position);
			String idnum = map.get("id");
			System.out.println("input_intent_idnum:"+idnum);
			sp = getSharedPreferences("dairyinfo", Context.MODE_PRIVATE);   
            Editor edit = sp.edit(); 
            edit.putString("id", idnum);  
            edit.putString("name", map.get("name").toString());   
            edit.putString("title", map.get("title").toString()); 
            edit.putString("time", map.get("time").toString()); 
            edit.putString("content", map.get("content").toString());  
            edit.commit();
			
			Intent intent = new Intent();
            //Intent传递参数
            intent.putExtra("idnum", idnum);
            intent.setClass(MainActivity.this, ShowDiaryDetail.class);
            MainActivity.this.startActivity(intent);
			
            
		}

	}
}
