package com.example.praisecontacts;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.CallLog.Calls;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class FragmentCheckRecord extends Fragment {
	
	private TextView TVrecordtimeago;
	private TextView TVtophone;
	private TextView TVtoaddress;
	private TextView TVtostatus;
	
	private ListView recordListView;
	private Cursor mcursor;
	private SharedPreferences phoneNumShare;
	
	private ArrayList<Map<String, String>>  recordList;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.check_callrecord, container,
				false);
	
		phoneNumShare = getActivity().getSharedPreferences("phoneNumShare",0);
		recordListView=(ListView) view.findViewById(R.id.call_record);
		recordListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				TextView TVphone=(TextView) arg1.findViewById(R.id.to_phone);
				Intent phoneIntent = new Intent(
						"android.intent.action.CALL", Uri.parse("tel:"
								+ TVphone.getText().toString()));

				// ����

				startActivity(phoneIntent);
				
			}
		});
		return view;
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		String phone=phoneNumShare.getString("PHONENUM", null);
		Log.e("phone", phone);
		ContentResolver mresolver=getActivity().getContentResolver();
		Cursor cursor = mresolver.query(CallLog.Calls.CONTENT_URI,                            
				new String[]{Calls.NUMBER,Calls.TYPE,Calls.DATE,Calls.CACHED_NAME,Calls.DURATION},"Calls.NUMBER=?",new String[]{phone},null); 
		recordList=new ArrayList<Map<String, String>>();
	
		if(cursor.moveToFirst()){                                                                                
			do{  
				
				Map<String, String> map=new HashMap<String,String>();
				CallLog calls =new CallLog();                                                                  
				//����                                                                                             
				String number = cursor.getString(cursor.getColumnIndex(Calls.NUMBER));   
				String duration = cursor.getString(cursor.getColumnIndexOrThrow(Calls.DURATION));    
				//��������                                                                                           
				String type;                                                                                     
				switch (Integer.parseInt(cursor.getString(cursor.getColumnIndex(Calls.TYPE)))) {                 
				case Calls.INCOMING_TYPE:                                                                        
					type = "����"; 
					map.put("to_status", type+""+duration+"��");
					break;                                                                                       
				case Calls.OUTGOING_TYPE:                                                                        
					type = "����";
					map.put("to_status", type+""+duration+"��");
					break;                                                                                       
				case Calls.MISSED_TYPE:                                                                          
					type = "δ��";     
					map.put("to_status", type);
					break;                                                                                       
				default:                                                                                         
					type = "�Ҷ�";//Ӧ���ǹҶ�.�������ֻ������жϳ���        
					map.put("to_status", type);
					break;                                                                                       
				}                                                                                                
				SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");                              
				Date date = new Date(Long.parseLong(cursor.getString(cursor.getColumnIndexOrThrow(Calls.DATE))));
				//����ʱ��                                                                                           
				String time = sfd.format(date);                                                                  
				//��ϵ��                                                                                            
				String name = cursor.getString(cursor.getColumnIndexOrThrow(Calls.CACHED_NAME));                 
				//ͨ��ʱ��,��λ:s                                                                                      
				
				map.put("record_timeago",time);
				map.put("to_phone",number);
				
				//map.put("to_duration","ͨ��ʱ��:"+ duration);
				recordList.add(map);
				
			}while(cursor.moveToNext());  
		recordListView.setAdapter(new SimpleAdapter(getActivity(), recordList,
				R.layout.call_record_item, new String[]{"record_timeago","to_phone","to_status"},
				new int[]{R.id.record_timeago,R.id.to_phone,R.id.to_status}));	
			
			                                                                                                     
		}
		
		else{recordListView.setVisibility(View.GONE);}
		
		
		
	
	}
	
	
	
}
