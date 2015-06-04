package com.example.praisecontacts;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class ActivitySearchContact extends Activity implements OnClickListener {
	private RelativeLayout back_layout;
	private EditText autoEdit;
	private ListView search_list;
	private List<Map<String,String>> searchcontactNameList;
	
	private static final int DATABASE_VERSION = 1;
	private static final String ContactDATABASE_NAME = "ContactsDB.db";
	public static final String ContactTABLE_NAME = "ContactsInfoTable";
	private MyContactsDataBaseHelper mContactHelper;
	private SQLiteDatabase contactDB;
	private Cursor cursor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_contacts);
		getActionBar().hide();
		back_layout=(RelativeLayout) findViewById(R.id.search_back);
		back_layout.setOnClickListener(this);
		autoEdit=( EditText) findViewById(R.id.id_autocomplete);
		search_list=(ListView) findViewById(R.id.seach_list);
		
	}
	
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mContactHelper=new MyContactsDataBaseHelper(ActivitySearchContact.this, ContactDATABASE_NAME);
		contactDB=mContactHelper.getReadableDatabase();
		cursor=contactDB.rawQuery("select * from ContactsInfoTable", null);
		autoEdit.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
			String searchcontent=autoEdit.getText().toString();
			if(cursor.moveToFirst())
			{	searchcontactNameList=new ArrayList<Map<String, String>>();
					do {
						if(cursor.getString(0).equals("?"+searchcontent+"?"))
						{
							Log.e("sdjl;k", searchcontent);
						}
					} while (cursor.moveToNext());
			}
			}
		});
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
