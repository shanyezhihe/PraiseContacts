package com.example.praisecontacts;

import android.app.Activity;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ActivityEditContact extends Activity {
	private TextView btn_save;
	private RelativeLayout btn_back;
	
	private TextView btn_delete;
	
	private EditText namechange;
	private EditText phonechange;
	private EditText emailchange;
	private EditText qqchange;
	private EditText wechatchange;
	private SharedPreferences detailShare;
	private Editor detailedit;
	
	private String  oldName;
	
	private static final String ContactDATABASE_NAME = "ContactsDB.db";
	public static final String ContactTABLE_NAME = "ContactsInfoTable";
	private MyContactsDataBaseHelper mContactHelper;
	private SQLiteDatabase contactDB;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_contact);
		getActionBar().hide();
		btn_save=(TextView) findViewById(R.id.btn_ok);
		btn_back=(RelativeLayout) findViewById(R.id.btn_cancle);
		btn_delete=(TextView) findViewById(R.id.btn_delete);
		
		namechange=(EditText) findViewById(R.id.name_change);
		phonechange=(EditText) findViewById(R.id.phone_change);
		emailchange=(EditText) findViewById(R.id.email_change);
		qqchange=(EditText) findViewById(R.id.qqnum_change);
		wechatchange=(EditText) findViewById(R.id.wechatnum_change);
		
		detailShare=ActivityEditContact.this.getSharedPreferences("SAVEDETAIL", 0);
		 detailedit=detailShare.edit();
		 
		 mContactHelper=new MyContactsDataBaseHelper(ActivityEditContact.this, ContactDATABASE_NAME);
		contactDB=mContactHelper.getReadableDatabase();
		
		Cursor cursor=contactDB.rawQuery("select * from ContactsInfoTable where name=?", 
				new String[]{detailShare.getString("DETAILNAME", null)});
		
		if(cursor.moveToFirst())
		{	oldName=cursor.getString(0);
			namechange.setText(cursor.getString(0));
			phonechange.setText(cursor.getString(2));
			emailchange.setText(cursor.getString(3));
			qqchange.setText(cursor.getString(4));
			wechatchange.setText(cursor.getString(5));
			
			//TVaddress.setText(cursor.getString(6)+cursor.getString(7)+cursor.getString(8));
		}
		
		btn_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		btn_save.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				String name=namechange.getText().toString();
				String phone=phonechange.getText().toString();
				String email=emailchange.getText().toString();
				String qq=qqchange.getText().toString();
				String wechat =wechatchange.getText().toString();
				ContentValues mcontentValue=new ContentValues();
				mcontentValue.put("name",name);
				mcontentValue.put("phoneNum",phone);
				mcontentValue.put("EmailNum", email);
				mcontentValue.put("QQNum", qq);
				mcontentValue.put("WeChatNum", wechat);
				contactDB.updateWithOnConflict(ContactTABLE_NAME, mcontentValue, 
						"name=?", new String[]{oldName}, 
						SQLiteDatabase.CONFLICT_REPLACE);

				finish();
			}
		});
		
		btn_delete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				contactDB.delete(ContactTABLE_NAME, "name=?", new String[]{oldName});
			}
		});
	}
	
}
