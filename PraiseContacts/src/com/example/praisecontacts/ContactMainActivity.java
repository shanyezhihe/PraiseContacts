package com.example.praisecontacts;


import java.util.ArrayList;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TabHost.OnTabChangeListener;


 public class ContactMainActivity extends FragmentActivity implements OnClickListener {
	

	private Fragment callFragment;
	private Fragment contactsFragment;
	private Fragment weatherFragment;
	private FragmentManager myFragmentManager;
	private FragmentTransaction myFragmentTransaction;
	private ArrayList<Fragment>  fragmentList;
	private ViewPager myViewPager;
	private TextView TVcall;
	private TextView TVcontacts;
	private TextView TVweather;
	private LinearLayout contacttitle;
	
	private  SharedPreferences phoneNumShare;
	
	private int COLOR_GRAY = 0xFF000000;
	private int COLOR_GREEN = 0xFFFFFFFF;
	private int COLOR_BLUE=0XFF0065a1;
	private int COLOR_YELLO=0XFFFFD39B;
	private int COLOR_QING=0XFF76EEC6;
	private int COLOR_BLACK=0xFF000000;
	
	  private MyServiceReceiver mReceiver01;
      private MyServiceReceiver mReceiver02;
      
      private static final String[] PHONES_PROJECTION = new String[] {  
          Phone.DISPLAY_NAME, Phone.NUMBER }; 
      
      private static final int DATABASE_VERSION = 1;
  	private static final String ContactDATABASE_NAME = "ContactsDB.db";
  	public static final String ContactTABLE_NAME = "ContactsInfoTable";
  	private MyContactsDataBaseHelper mContactHelper;
  	private SQLiteDatabase contactDB;
  	private Cursor mcursor;
  	
  	/**联系人显示名称**/  
    private static final int PHONES_DISPLAY_NAME_INDEX = 0;  
      
    /**电话号码**/  
    private static final int PHONES_NUMBER_INDEX = 1;  
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_main);
        getActionBar().hide();
        
        mContactHelper=new MyContactsDataBaseHelper(ContactMainActivity.this, ContactDATABASE_NAME);
		contactDB=mContactHelper.getReadableDatabase();
        
        phoneNumShare = this.getSharedPreferences("phoneNumShare",0);
        
        IntentFilter mFilter01;  
        mFilter01 = new IntentFilter("SMS_SEND_ACTIOIN");  
        mReceiver01 = new MyServiceReceiver();  
        registerReceiver(mReceiver01, mFilter01);  
          
        /* 自定义IntentFilter为DELIVERED_SMS_ACTION Receiver */  
        mFilter01 = new IntentFilter("SMS_DELIVERED_ACTION");  
        mReceiver02 = new MyServiceReceiver();  
        registerReceiver(mReceiver02, mFilter01);  
        
        contacttitle=(LinearLayout) findViewById(R.id.contacttitle);
        
        callFragment=new CallFrament();
        contactsFragment=new ContactsFragment();
        weatherFragment=new WeartherFrament();
        
        fragmentList=new ArrayList<Fragment>();
        fragmentList.add( callFragment);
        fragmentList.add( contactsFragment);
        fragmentList.add( weatherFragment);
        
        TVcall=(TextView) findViewById(R.id.call);
        TVcontacts=(TextView) findViewById(R.id.contacts);
        TVweather=(TextView) findViewById(R.id.wearther);
        myViewPager=(ViewPager) findViewById(R.id.id_viewpager);
      
        
        
      
        TVcall.setOnClickListener(this);
        TVcontacts.setOnClickListener(this);
        TVweather.setOnClickListener(this);
       
       myFragmentManager = getSupportFragmentManager();
  	   myFragmentTransaction = myFragmentManager.beginTransaction();
        
  	   TVcontacts.setTextSize(18);
 	   myFragmentManager=getSupportFragmentManager();
 	  myFragmentTransaction=myFragmentManager.beginTransaction();
 	  
 	    myViewPager.setCurrentItem(1);
 	//  myFragmentTransaction.replace(R.id.id_content, contactsFragment).commit();
        
        myViewPager.setAdapter(new MyFragmentPagerAadpter(getSupportFragmentManager(), fragmentList));
        myViewPager.setCurrentItem(1);
        myViewPager.setOnPageChangeListener(new MyPageChangeListener());
    }
    private  class MyPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {
			
				int index = myViewPager.getCurrentItem();
				colorChange(index);
			
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageSelected(int index) {
		}

	}
    
   
    
 private   class myViewPageAdapter extends FragmentPagerAdapter
    {
	   private ArrayList<Fragment> mFragmentList;
	public myViewPageAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}
	public myViewPageAdapter(FragmentManager fm,ArrayList<Fragment> fragments) {
		super(fm);
		this.mFragmentList=fragments;
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		return mFragmentList.get(arg0);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mFragmentList.size();
	}
    	
		
    	
    }
    
   public void  colorChange(int indext)
   {
//   	ContactMainActivity.this.myFragmentManager = getSupportFragmentManager();
// 	   ContactMainActivity.this.myFragmentTransaction = myFragmentManager.beginTransaction();
 		
   	if(indext==0)
		{	myViewPager.setCurrentItem(0);
			//myFragmentTransaction.replace(R.id.id_content,callFragment).commit();
			TVcall.setTextColor(COLOR_GREEN);
			TVcontacts.setTextColor(COLOR_GRAY);
			TVweather.setTextColor(COLOR_GRAY);
			//contacttitle.setBackgroundColor(COLOR_QING);
			TVcontacts.setTextSize(12);
			TVweather.setTextSize(12);
			TVcall.setTextSize(20);
		}
		if(indext==1)
		{	myViewPager.setCurrentItem(1);
			//myFragmentTransaction.replace(R.id.id_content,contactsFragment).commit();
			TVcall.setTextColor(COLOR_GRAY);
			TVcontacts.setTextColor(COLOR_GREEN);
			TVweather.setTextColor(COLOR_GRAY);
			//contacttitle.setBackgroundColor(COLOR_BLUE);
			TVcontacts.setTextSize(20);
			TVweather.setTextSize(12);
			TVcall.setTextSize(12);
		}
		if(indext==2)
		{	myViewPager.setCurrentItem(2);
			//myFragmentTransaction.replace(R.id.id_content,weatherFragment).commit();
			TVcall.setTextColor(COLOR_GRAY);
			TVcontacts.setTextColor(COLOR_GRAY);
			TVweather.setTextColor(COLOR_GREEN);
			//contacttitle.setBackgroundColor(COLOR_YELLO);
			TVweather.setTextSize(20);
			TVcontacts.setTextSize(12);
			TVcall.setTextSize(12);
		}
//		myFragmentTransaction.commit();
   }
   
   
   @Override
	public void onClick(View v) {
	 
	  
	 ContactMainActivity.this.myFragmentManager = getSupportFragmentManager();
	   ContactMainActivity.this.myFragmentTransaction = myFragmentManager.beginTransaction();
		
 	if(v.getId()==R.id.call)
		{	myViewPager.setCurrentItem(0);
			//myFragmentTransaction.replace(R.id.id_content,callFragment).commit();
			TVcall.setTextColor(COLOR_GREEN);
			TVcontacts.setTextColor(COLOR_GRAY);
			TVweather.setTextColor(COLOR_GRAY);
			//contacttitle.setBackgroundColor(COLOR_QING);
			TVcontacts.setTextSize(12);
			TVweather.setTextSize(12);
			TVcall.setTextSize(20);
		}
		if(v.getId()==R.id.contacts)
		{	myViewPager.setCurrentItem(1);
			//myFragmentTransaction.replace(R.id.id_content,contactsFragment).commit();
			TVcall.setTextColor(COLOR_GRAY);
			TVcontacts.setTextColor(COLOR_GREEN);
			TVweather.setTextColor(COLOR_GRAY);
			//contacttitle.setBackgroundColor(COLOR_BLUE);
			TVcontacts.setTextSize(20);
			TVweather.setTextSize(12);
			TVcall.setTextSize(12);
		}
		if(v.getId()==R.id.wearther)
		{	myViewPager.setCurrentItem(2);
			//myFragmentTransaction.replace(R.id.id_content,weatherFragment).commit();
			TVcall.setTextColor(COLOR_GRAY);
			TVcontacts.setTextColor(COLOR_GRAY);
			TVweather.setTextColor(COLOR_GREEN);
			//contacttitle.setBackgroundColor(COLOR_YELLO);
			TVweather.setTextSize(20);
			TVcontacts.setTextSize(12);
			TVcall.setTextSize(12);
		}
	
		myFragmentTransaction.commit();
	}


   private class MyServiceReceiver extends BroadcastReceiver {  
 	  @Override  
 	  public void onReceive(Context context, Intent intent) {  
 	    // TODO Auto-generated method stub  
 	   try {  
 	    /* android.content.BroadcastReceiver.getResultCode()方法 */  
 	    switch (getResultCode()) {  
 	    case Activity.RESULT_OK:  
 	     /* 发送短信成功 */  
 	    	String str=phoneNumShare.getString("PHONENUM", null)+"已成功接收信息";
 	    Toast.makeText(ContactMainActivity.this, str, Toast.LENGTH_SHORT).show();
 	     Log.d("lmn", 
 	        "----发送短信成功---------------------------");  
 	     break;  
 	    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:  
 	     /* 发送短信失败 */  
 	    case SmsManager.RESULT_ERROR_RADIO_OFF:  
 	    case SmsManager.RESULT_ERROR_NULL_PDU:  
 	    default: 
 	    	String ss=phoneNumShare.getString("PHONENUM", null)+"接收信息失败！";
 	    	 Toast.makeText(ContactMainActivity.this, ss, Toast.LENGTH_SHORT).show();
 	     Log.d("lmn", 
 	        "----发送短信失败---------------------------");  
 	     break;  
 	    }  
 	   } catch (Exception e) {  
 	    e.getStackTrace();  
 	   }  
 	  }  
 	 }  
   
@Override
protected void onResume() {
	// TODO Auto-generated method stub
	super.onResume();
	
	
}


@Override
protected void onDestroy() {
	// TODO Auto-generated method stub
	super.onDestroy();
	if (mReceiver01 != null && mReceiver02 != null) {  
	    unregisterReceiver(mReceiver01);  
	    unregisterReceiver(mReceiver02);  
	}  
}
   
   
@Override
public boolean onCreatePanelMenu(int arg0, Menu arg1) {
	// TODO Auto-generated method stub
	getMenuInflater().inflate(R.menu.contact_main, arg1);
	return true;
}


@Override
public boolean onMenuItemSelected(int featureId, MenuItem item) {
	// TODO Auto-generated method stub
	switch(item.getItemId())
	{
	case R.id.id_share:
		
		Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        
        sendIntent.putExtra(Intent.EXTRA_TEXT, "我在使用一个很nice的通讯录，你也来试试,下载地址：http://pan.baidu.com/s/1mgtbheK");
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, "赞.通讯录分享"));
		break;
	case R.id.id_about:
		
		break;
		
	case R.id.id_exitport:
		 contactDB.delete(ContactTABLE_NAME,"Type=?", new String[]{"1"});
		 Intent intent=new Intent("delete_action");
		 sendBroadcast(intent);
		break;
	case R.id.id_import:
		ContentValues value=new ContentValues();
		ContentResolver resolver = ContactMainActivity.this.getContentResolver();  
		// 获取手机联系人  
		Cursor phoneCursor = resolver.query(Phone.CONTENT_URI,PHONES_PROJECTION, null, null, null);  
		if (phoneCursor != null) {  
		    while (phoneCursor.moveToNext()) {  
		    String contactName = phoneCursor.getString(PHONES_DISPLAY_NAME_INDEX);  
		    value.put("name", contactName);
		    //得到手机号码  
		    String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);  
		    //当手机号码为空的或者为空字段 跳过当前循环  
		    if (TextUtils.isEmpty(phoneNumber))  
		        continue;  
		    value.put("phoneNum", phoneNumber);
		    value.put("Type", "1");
		    contactDB.insert(ContactTABLE_NAME, null, value);
		    }}
		break;
	}
	return true;
}


@Override
public void onPanelClosed(int featureId, Menu menu) {
	// TODO Auto-generated method stub
	super.onPanelClosed(featureId, menu);
}

   
}
