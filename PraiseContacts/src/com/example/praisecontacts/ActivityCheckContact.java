package com.example.praisecontacts;

import java.util.ArrayList;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ActivityCheckContact extends FragmentActivity implements OnClickListener {
	
	private int COLOR_NAME=0xFF8B2323;
	
	private TextView TVdetail;
	private TextView TVrecord;
	private TextView checkcontactname;
	private TextView TVnicheng;
	
	
	private ImageView IVback;
	
	private LinearLayout btn_edit;
	
	private int COLOR_BLACK=0xFF000000;
	private int COLOR_WHITE=0xFF7A7A7A;
	
	private ViewPager checkViewPager;
	
	private Fragment detailFragment;
	private Fragment recordFragment;
	private SharedPreferences detailShare;
	private FragmentManager myFragmentManager;
	private FragmentTransaction myFragmentTransaction;
	
	private ArrayList<Fragment> checkFragmentList;
	
	private MyBroadcastReceiver mreceiver;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.check_contact);
		getActionBar().hide();
		IntentFilter intentfilter=new IntentFilter("finish");
		mreceiver=new MyBroadcastReceiver();
		registerReceiver(mreceiver, intentfilter);
		 detailShare=this.getSharedPreferences("SAVEDETAIL", 0);
		TVdetail=(TextView) findViewById(R.id.detail_id);
		TVrecord=(TextView) findViewById(R.id.record_id);
		IVback=(ImageView) findViewById(R.id.checkbackid);
		checkcontactname=(TextView) findViewById(R.id.check_contact_name);
		checkcontactname.setText(detailShare.getString("DETAILNAME", null));
		TVnicheng=(TextView) findViewById(R.id.nichen);
		TVnicheng.setText(detailShare.getString("DETAILNAME", null).
				substring(detailShare.getString("DETAILNAME", null).length()-1,
						detailShare.getString("DETAILNAME", null).length()));
		btn_edit=(LinearLayout) findViewById(R.id.edit_contact_id);
		btn_edit.setOnClickListener(this);
		TVdetail.setOnClickListener(this);
		TVrecord.setOnClickListener(this);
		IVback.setOnClickListener(this);
		
		detailFragment=new FramentCheckDetail();
		recordFragment=new FragmentCheckRecord();
		checkFragmentList=new ArrayList<Fragment>();
		
		checkFragmentList.add(recordFragment);
		checkFragmentList.add(detailFragment);
		
		
		checkViewPager=(ViewPager) findViewById(R.id.id_check_viewpager);
		checkViewPager.setAdapter(new myViewPageAdapter(getSupportFragmentManager(),checkFragmentList));
		checkViewPager.setCurrentItem(1);
		checkViewPager.setOnPageChangeListener(new MyPageChangeListener());
		
		
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1 && resultCode == 1) {
			setResult(1, data);
			Log.e("dsafjlk", "dsafj;lsdk");
			Bundle bundle = data.getExtras();
			String newname = bundle.getString("newname");
			checkcontactname.setText(newname);
			checkcontactname.setTextSize(23);
			checkcontactname.setTextColor(COLOR_NAME);
			TVnicheng
					.setText(detailShare.getString("DETAILNAME", null)
							.substring(
									detailShare.getString("DETAILNAME", null)
											.length() - 1,
									detailShare.getString("DETAILNAME", null)
											.length()));
		}
	}





	private  class MyPageChangeListener  implements OnPageChangeListener 
	{
		@Override
		public void onPageScrollStateChanged(int arg0) {
			int index=checkViewPager.getCurrentItem();
			if(index==0)
			{	checkViewPager.setCurrentItem(0);
			TVdetail.setTextColor(COLOR_WHITE);
			TVdetail.setTextSize(13);
			TVrecord.setTextSize(20);
			TVrecord.setTextColor(COLOR_BLACK);
			
			}
			if(index==1)
			{	checkViewPager.setCurrentItem(1);
				//myFragmentTransaction.replace(R.id.id_content,contactsFragment).commit();
			
			
			TVdetail.setTextColor(COLOR_BLACK);
			TVdetail.setTextSize(20);
			TVrecord.setTextSize(13);
			TVrecord.setTextColor(COLOR_WHITE);
			}
			
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPageSelected(int arg0) {
			// TODO Auto-generated method stub
			
		}
	}
	
	
	
	private class myViewPageAdapter extends FragmentPagerAdapter {
		private ArrayList<Fragment> mFragmentList;

		public myViewPageAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}

		public myViewPageAdapter(FragmentManager fm,
				ArrayList<Fragment> fragments) {
			super(fm);
			this.mFragmentList = fragments;
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
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(mreceiver);
	}

	@Override
	public void onClick(View v) {
		
		 ActivityCheckContact.this.myFragmentManager = getSupportFragmentManager();
		   ActivityCheckContact.this.myFragmentTransaction = myFragmentManager.beginTransaction();
		
		switch (v.getId()) {
		case R.id.detail_id:
			checkViewPager.setCurrentItem(1);
			TVdetail.setTextColor(COLOR_BLACK);
			TVdetail.setTextSize(20);
			TVrecord.setTextSize(13);
			TVrecord.setTextColor(COLOR_WHITE);
			
			
			break;
		case R.id.record_id:
			checkViewPager.setCurrentItem(0);
			
			TVdetail.setTextColor(COLOR_WHITE);
			TVdetail.setTextSize(13);
			TVrecord.setTextSize(20);
			TVrecord.setTextColor(COLOR_BLACK);
			
			break;
		case R.id.checkbackid:
			ActivityCheckContact.this.finish();
			break;
		case R.id.edit_contact_id:
			Intent editContactIntent=new Intent(ActivityCheckContact.this,ActivityEditContact.class);
			startActivityForResult(editContactIntent, 1);
			break;
		}
		myFragmentTransaction.commit();
	}

	class MyBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			finish();
		}

	}
		
}
