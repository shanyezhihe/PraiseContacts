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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


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
	
	
	
	private int COLOR_GRAY = 0xFF000000;
	private int COLOR_GREEN = 0xFFFFFFFF;
	private int COLOR_BLUE=0XFF0065a1;
	private int COLOR_YELLO=0XFFFFD39B;
	private int COLOR_QING=0XFF76EEC6;
	private int COLOR_BLACK=0xFF000000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_main);
        getActionBar().hide();
        
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
}
