package com.example.praisecontacts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.SimpleAdapter;

public class ActivitySearchContact extends Activity implements OnClickListener {
	private RelativeLayout back_layout;
	private  SearchView autoEdit;
	private ListView search_list;
	private List<Map<String,String>> searchcontactNameList;
	
	private static final int DATABASE_VERSION = 1;
	private static final String ContactDATABASE_NAME = "ContactsDB.db";
	public static final String ContactTABLE_NAME = "ContactsInfoTable";
	private MyContactsDataBaseHelper mContactHelper;
	private SQLiteDatabase contactDB;
	private Cursor cursor;
	
	SharedPreferences detailShare;
	private Editor detailedit;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_contacts);
		getActionBar().hide();
		
		 detailShare=this.getSharedPreferences("SAVEDETAIL", 0);
		 detailedit=detailShare.edit();
		
		back_layout=(RelativeLayout) findViewById(R.id.search_back);
		back_layout.setOnClickListener(this);
		autoEdit=( SearchView) findViewById(R.id.id_autocomplete);
		search_list=(ListView) findViewById(R.id.seach_list);
		search_list.setTextFilterEnabled(true);
	}
	
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mContactHelper=new MyContactsDataBaseHelper(ActivitySearchContact.this, ContactDATABASE_NAME);
		contactDB=mContactHelper.getReadableDatabase();
		cursor=contactDB.rawQuery("select * from ContactsInfoTable", null);
		
		if(cursor.moveToFirst())
		{	searchcontactNameList=new ArrayList<Map<String, String>>();
				do {
					
						Map<String, String > map=new HashMap<String, String>();
						map.put("name",cursor.getString(0));
						map.put("phone",cursor.getString(2));
						searchcontactNameList.add(map);
						
						//Log.e("sdjl;k", searchcontent);
					
				} while (cursor.moveToNext());
		}
		
		if(searchcontactNameList!=null)
		search_list.setAdapter(new SimpleAdapter(ActivitySearchContact.this,
					searchcontactNameList,R.layout.search_listview_item,
					new String[]{"name","phone"},new int[]{R.id.search_name,R.id.search_phone}));
		
		search_list.setVisibility(View.GONE);
		autoEdit.setOnQueryTextListener(new OnQueryTextListener() {
			
			@Override
			public boolean onQueryTextSubmit(String query) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean onQueryTextChange(String newText) {
				// TODO Auto-generated method stub
				
				if (TextUtils.isEmpty(newText)) {
		            // 清除ListView的过滤
		            search_list.clearTextFilter();
		            search_list.setVisibility(View.GONE);
				}
				 else {
			            // 使用用户输入的内容对ListView的列表项进行过滤
			            search_list.setFilterText(newText);
			            search_list.setVisibility(View.VISIBLE);
			        }
			        return true;
			}
		});
		
		
		
		search_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				TextView name_text=(TextView) view.findViewById(R.id.search_name);
				detailedit.putString("DETAILNAME", name_text.getText().toString()).commit();
				Intent checkItent=new Intent(ActivitySearchContact.this,ActivityCheckContact.class);
				startActivity(checkItent);
				
			}
		});
//		autoEdit.addTextChangedListener(new TextWatcher() {
//			
//			@Override
//			public void onTextChanged(CharSequence s, int start, int before, int count) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			@Override
//			public void beforeTextChanged(CharSequence s, int start, int count,
//					int after) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			@Override
//			public void afterTextChanged(Editable s) {
//				// TODO Auto-generated method stub
//				search_list=(ListView) findViewById(R.id.seach_list);
//			    String searchcontent=autoEdit.getText().toString();
//			if(cursor.moveToFirst())
//			{	searchcontactNameList=new ArrayList<Map<String, String>>();
//					do {
//						if((cursor.getString(0).indexOf(searchcontent)!=-1)||
//								(cursor.getString(2).indexOf(searchcontent)!=-1)||
//								(cursor.getString(3).indexOf(searchcontent)!=-1)||
//								(cursor.getString(4).indexOf(searchcontent)!=-1)||
//								(cursor.getString(5).indexOf(searchcontent)!=-1))
//						{
//							Map<String, String > map=new HashMap<String, String>();
//							map.put("name",cursor.getString(0));
//							map.put("phone",cursor.getString(2));
//							searchcontactNameList.add(map);
//							
//							Log.e("sdjl;k", searchcontent);
//						}
//					} while (cursor.moveToNext());
//				if(searchcontactNameList!=null)
//					search_list.setAdapter(new SimpleAdapter(ActivitySearchContact.this,
//							searchcontactNameList,R.layout.search_listview_item,
//							new String[]{"name","phone"},new int[]{R.id.search_name,R.id.search_phone}));
//			}
//			}
//		});
	}



	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		cursor.close();
		contactDB.close();
	}



	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
		case R.id.search_back:
			ActivitySearchContact.this.finish();
			break;
		
		}
	}
	
}
