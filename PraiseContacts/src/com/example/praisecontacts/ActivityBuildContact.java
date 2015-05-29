package com.example.praisecontacts;



import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class ActivityBuildContact extends Activity implements OnClickListener {
	private final String DATABASE_NAME = "area.db";
	private final String TABLE_NAME = "tb_core_area";
	
	private SharedPreferences itemShare;
	private Editor itemEditor;
	
	private SharedPreferences provinceShare;
	private Editor proviceEditor;
	
	private SharedPreferences cityShare;
	private Editor cityEditor;
	
	private static final int DATABASE_VERSION = 1;
	private static final String ContactDATABASE_NAME = "ContactsDB.db";
	public static final String ContactTABLE_NAME = "ContactsInfoTable";
	private Spinner provinceSpinner;
	private Spinner citySpinner;
	private Spinner districtSpinner;
	private MyAreaDBHelper myAreaDBHelper;
	private SQLiteDatabase db;
	
	private MyContactsDataBaseHelper mContactHelper;
	private SQLiteDatabase contactDB;
	private String TAG="tag";
	private String[] provinceSet;
	private String[][] citySet;
	private String[][][] districtSet;
	private String[] newprovinceSet;
	private String[][] newcitySet;
	private String[] newdistrictSet;
	private	int maxPlength ;
	private int maxClength ;
	private int maxDlength ;
	ArrayAdapter<String> provinceAdapter = null;
	ArrayAdapter<String> cityAdapter = null;
	ArrayAdapter<String> districtAdapter = null;
	private int provincePosition = 0;
	private int realCityLenth;
	
	private RelativeLayout btn_back;
	private TextView btn_save;
	private Button btn_reset;
	
	private EditText ETname;
	 private  Spinner groupSpinner;
	 private EditText ETphonenum;
	 private EditText ETemail;
	 private EditText ETqq;
	 private EditText ETwechat;
	 
	 private String name;
	 private String groupName;
	 private String PhoneNum;
	 private String EmailNum;
	 private String QQNum;
	 private String WeChatNum;
	 private String provinceName;
	 private String cityName;
	 private String distrcName;
	private ArrayList<String> group;
	 private ContentValues mcontentValue;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.buildnewcontact);
		getActionBar().hide();
		
		itemShare=this.getSharedPreferences("ITEMSHARE", 0);
		itemEditor=itemShare.edit();
		
		provinceShare=this.getSharedPreferences("provinceShare", 0);
		proviceEditor=provinceShare.edit();
		cityShare=this.getSharedPreferences("cityShare", 0);
		cityEditor=cityShare.edit();
		
		mcontentValue=new ContentValues();
		mContactHelper=new MyContactsDataBaseHelper(this, ContactDATABASE_NAME);
		contactDB=mContactHelper.getReadableDatabase();
		
		btn_back=(RelativeLayout) findViewById(R.id.btn_back);
		btn_save =(TextView) findViewById(R.id.btn_save);
		btn_reset=(Button) findViewById(R.id.btn_reset);
		
		btn_back.setOnClickListener(this);
		btn_save.setOnClickListener(this);
		btn_reset.setOnClickListener(this);
		
		ETname=(EditText) findViewById(R.id.name_edit);
		groupSpinner=(Spinner) findViewById(R.id.groupspinner);
		ETphonenum=(EditText) findViewById(R.id.phone_edit);
		ETemail=(EditText) findViewById(R.id.email_edit);
		ETqq=(EditText) findViewById(R.id.qqnum_edit);
		ETwechat=(EditText) findViewById(R.id.wechatnum_edit);
		
		 group=new ArrayList<String>();
		group.add("请选择分组");
		group.add("家人");
		group.add("朋友");
		group.add("同学");
		group.add("同事");
		group.add("其他");
		groupSpinner.setAdapter(new ArrayAdapter<String>
		(this, android.R.layout.simple_list_item_1, group));
		groupSpinner.setSelection(0, true);
		
		
		
		provinceSpinner=(Spinner) findViewById(R.id.provinceSpinner);
		citySpinner=(Spinner) findViewById(R.id.citySpinner);
		districtSpinner=(Spinner) findViewById(R.id.districtSpinner);
		
		ETname.addTextChangedListener(new TextWatcher() {
			
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
				name=ETname.getText().toString();
				
			}
		});
		
		ETemail.addTextChangedListener(new TextWatcher() {
			
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
				EmailNum=ETemail.getText().toString();
			
			}
		});
		
		
		ETphonenum.addTextChangedListener(new TextWatcher() {
			
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
				PhoneNum=ETphonenum.getText().toString();
			
			}
		});
		
		ETqq.addTextChangedListener(new TextWatcher() {
			
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
				QQNum=ETqq.getText().toString();
				
			}
		});
		
		ETwechat.addTextChangedListener(new TextWatcher() {
			
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
				WeChatNum=ETwechat.getText().toString();
			
			}
		});
		
	
		
		myAreaDBHelper=new MyAreaDBHelper(getApplicationContext(), DATABASE_NAME, null, 1);
		db=myAreaDBHelper.getReadableDatabase();
		Cursor mCursorP = db.rawQuery("select * from " + TABLE_NAME, null);
		Cursor mCursorC = db.rawQuery("select * from " + TABLE_NAME, null);
		Cursor mCursorD = db.rawQuery("select * from " + TABLE_NAME, null);
		int provinceIdIndex = mCursorP.getColumnIndex("province_id");
		int cityIdIndex = mCursorP.getColumnIndex("city_id");
		int districtIdIndex = mCursorP.getColumnIndex("district_id");
		maxPlength = 1;
		maxClength = 1;
		maxDlength = 1;
		if (mCursorP.moveToFirst()) {
			int tempPId = mCursorP.getInt(provinceIdIndex);
			while(mCursorP.moveToNext()){
				if (mCursorP.getInt(provinceIdIndex) != tempPId) {
					tempPId = mCursorP.getInt(provinceIdIndex);
					maxPlength++;
				}
			}
			Log.d(TAG,String.valueOf(maxPlength));
			mCursorP.close();
		}
		if(mCursorC.moveToFirst()){
			int tempCcount = 1;
			int tempPId = mCursorC.getInt(provinceIdIndex);
			int tempCId = mCursorC.getInt(cityIdIndex);
			while (mCursorC.moveToNext()) {
				if(mCursorC.getInt(provinceIdIndex) == tempPId
						&& mCursorC.getInt(cityIdIndex) != tempCId){
					tempCId = mCursorC.getInt(cityIdIndex);
					tempCcount++;
				}else if(mCursorC.getInt(provinceIdIndex) != tempPId
						&& mCursorC.getInt(cityIdIndex) != tempCId){
					tempPId = mCursorC.getInt(provinceIdIndex);
					tempCId = mCursorC.getInt(cityIdIndex);
					if(tempCcount > maxClength) {
						maxClength = tempCcount;
					}
					tempCcount = 1;
				}
			}
			Log.d(TAG,String.valueOf(maxClength));
			mCursorC.close();
		}
		if(mCursorD.moveToFirst()){
			int tempDcount = 1;
			int tempPId = mCursorD.getInt(provinceIdIndex);
			int tempCId = mCursorD.getInt(cityIdIndex);
			while (mCursorD.moveToNext()) {
				if(mCursorD.getInt(provinceIdIndex) == tempPId
						&& mCursorD.getInt(cityIdIndex) == tempCId){
					tempDcount++;
				}else if(mCursorD.getInt(provinceIdIndex) == tempPId
						&& mCursorD.getInt(cityIdIndex) != tempCId){
					tempCId = mCursorD.getInt(cityIdIndex);
					if(tempDcount > maxDlength) {
						maxDlength = tempDcount;
					}
					tempDcount = 1;
				}else if(mCursorD.getInt(provinceIdIndex) != tempPId
						&& mCursorD.getInt(cityIdIndex) != tempCId){
					tempPId = mCursorD.getInt(provinceIdIndex);
					tempCId = mCursorD.getInt(cityIdIndex);
					if(tempDcount > maxDlength) {
						maxDlength = tempDcount;
					}
					tempDcount = 1;
				}
			}
			Log.d(TAG,String.valueOf(maxDlength));
			mCursorD.close();
		}
		provinceSet = new String[maxPlength];
		citySet = new String[maxPlength][maxClength];
		districtSet = new String[maxPlength][maxClength][maxDlength];
	
		
		
		int a = 0, b = 0, c = 0;
		Cursor mCursor = db.rawQuery("select * from " + TABLE_NAME, null);
		int provinceIndex = mCursor.getColumnIndex("province_short_name");
		int cityIndex = mCursor.getColumnIndex("city_short_name");
		int disdrictIndex = mCursor.getColumnIndex("district_short_name");
		if(mCursor.moveToFirst()){
			provinceSet[a] = mCursor.getString(provinceIndex);
			citySet[a][b] = mCursor.getString(cityIndex);
			districtSet[a][b][c] = mCursor.getString(disdrictIndex);
			
			while(mCursor.moveToNext()){
				if(mCursor.getString(provinceIndex).equals(provinceSet[a])){
					if(mCursor.getString(cityIndex).equals(citySet[a][b])){
						c++;
						districtSet[a][b][c] = mCursor.getString(disdrictIndex);
					}else{
						c = 0;
						b++;
						citySet[a][b] = mCursor.getString(cityIndex);
						districtSet[a][b][c] = mCursor.getString(disdrictIndex);
					}
				}else{
					b = 0;
					c = 0;
					a++;
					provinceSet[a] = mCursor.getString(provinceIndex);
					citySet[a][b] = mCursor.getString(cityIndex);
					districtSet[a][b][c] = mCursor.getString(disdrictIndex);
				}
			}
		}
		System.out.println(provinceSet[maxPlength-1]);
		System.out.println(citySet[maxPlength-1][2]);
	//	System.out.println(provinceSet[maxPlength-1]);
			setSpinner();
		
		provinceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				int i=0;
				for(int j=0;j<maxClength;j++)
				{
					if(citySet[position][j]!=null)
					{
						i++;
					}
				}	
				newcitySet=new String[maxPlength][i];
				for(int k=0;k<i;k++)
				{
					newcitySet[position][k]=citySet[position][k];
				}
				cityAdapter = new ArrayAdapter<String>(
						getApplicationContext(), android.R.layout.simple_spinner_item, newcitySet[position]);
				citySpinner.setAdapter(cityAdapter);
				provincePosition = position;
				Log.d(TAG, String.valueOf(provincePosition));
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
		
			}
			
		});
		citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				int i=0;
				for(int j=0;j<maxDlength;j++)
				{
					if(districtSet[provincePosition][position][j]!=null)
					{
						i++;
					}
				}	
				newdistrictSet=new String[i];
				
				for(int k=0;k<i;k++)
				{
					newdistrictSet[k]=districtSet[provincePosition][position][k];
				}
				Log.d("log", String.valueOf(provincePosition));
				districtAdapter = new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_spinner_item, newdistrictSet);
				districtSpinner.setAdapter(districtAdapter);
				}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				 
			}
			
		});
		districtSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
 
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				
			}
			
		});
	}
	
	private void setSpinner(){
		

		provinceAdapter = new ArrayAdapter<String>(getApplicationContext(),
				android.R.layout.simple_spinner_item, provinceSet);
		provinceSpinner.setAdapter(provinceAdapter);
//		provinceSpinner.setSelection(0, true);

		cityAdapter = new ArrayAdapter<String>(getApplicationContext(),
				android.R.layout.simple_spinner_item, citySet[0]);
		if(cityAdapter==null){	
			//districtSpinner.setAdapter(districtAdapter);
			System.out.print("cityAdapter==null");
		}
		citySpinner.setAdapter(cityAdapter);
	citySpinner.setSelection(0, true);

		districtAdapter = new ArrayAdapter<String>(getApplicationContext(),
				android.R.layout.simple_spinner_item, districtSet[0][0]);
		districtSpinner.setAdapter(districtAdapter);  
	districtSpinner.setSelection(0, true);
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId()==R.id.btn_back)
		{
			ActivityBuildContact.this.finish();
		}
		if(v.getId()==R.id.btn_save)
			
		{	itemEditor.putString("NEWNAME", name).commit();
			
			mcontentValue.put("name", name);
			groupName=groupSpinner.getSelectedItem().toString();
			Log.e("groupname", groupName);
			mcontentValue.put("groupNum", groupName);
			mcontentValue.put("phoneNum", PhoneNum);
			mcontentValue.put("EmailNum", EmailNum);
			mcontentValue.put("QQNum", QQNum);
			mcontentValue.put("WeChatNum", WeChatNum);
			provinceName=provinceSpinner.getSelectedItem().toString();
			Log.e("groupname", provinceName);
			mcontentValue.put("provice", provinceName);
			cityName=citySpinner.getSelectedItem().toString();
			mcontentValue.put("city",cityName);
			distrcName=districtSpinner.getSelectedItem().toString();
			mcontentValue.put("distr",distrcName);
			mcontentValue.put("Type", "build");
			if(PhoneNum==null)
			{
				Toast.makeText(this, "手机号码不能为空", Toast.LENGTH_SHORT).show();
			}
			else
			{
				contactDB.insert(ContactTABLE_NAME, null, mcontentValue);
				
			}
			
		}
		if(v.getId()==R.id.btn_reset)
		{	
			ETname.setText("");
			ETphonenum.setText("");
			ETemail.setText("");
			ETqq.setText("");
			ETwechat.setText("");
			 provinceSpinner.setSelection(0,true);
			citySpinner.setSelection(0, true);
			districtSpinner.setSelection(0, true);
			groupSpinner.setSelection(0, true);
		}
		
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		contactDB.close();
	}
	
	

	
}
