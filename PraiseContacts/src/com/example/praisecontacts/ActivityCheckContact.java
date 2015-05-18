package com.example.praisecontacts;

import java.util.ArrayList;











import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class ActivityCheckContact extends FragmentActivity implements OnClickListener {
	private TextView TVdetail;
	private TextView TVrecord;
	
	private int COLOR_BLACK=0xFF000000;
	private int COLOR_WHITE=0xFF7A7A7A;
	
	private ViewPager checkViewPager;
	
	private Fragment detailFragment;
	private Fragment recordFragment;
	
	private FragmentManager myFragmentManager;
	private FragmentTransaction myFragmentTransaction;
	
	private ArrayList<Fragment> checkFragmentList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.check_contact);
		getActionBar().hide();
		TVdetail=(TextView) findViewById(R.id.detail_id);
		TVrecord=(TextView) findViewById(R.id.record_id);
		TVdetail.setOnClickListener(this);
		TVrecord.setOnClickListener(this);
		
		detailFragment=new FramentCheckDetail();
		recordFragment=new FragmentCheckRecord();
		checkFragmentList=new ArrayList<Fragment>();
		
		checkFragmentList.add(recordFragment);
		checkFragmentList.add(detailFragment);
		
		
		checkViewPager=(ViewPager) findViewById(R.id.id_check_viewpager);
		checkViewPager.setCurrentItem(1);
		checkViewPager.setAdapter(new myViewPageAdapter(getSupportFragmentManager(),checkFragmentList));
		checkViewPager.setOnPageChangeListener(new MyPageChangeListener());
		
		
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
	
		}
		myFragmentTransaction.commit();
	}

	
		
}
