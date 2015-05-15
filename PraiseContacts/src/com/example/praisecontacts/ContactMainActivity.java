package com.example.praisecontacts;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;


 public class ContactMainActivity extends FragmentActivity implements OnClickListener {
	private Fragment callFragment;
	private Fragment contactsFragment;
	private Fragment weatherFragment;
	private FragmentManager myFragmentManager;
	private FragmentTransaction myFragmentTransaction;
	private TextView TVcall;
	private TextView TVcontacts;
	private TextView TVweather;
	
	private int COLOR_GRAY = 0xFF7A7A7A;
	private int COLOR_GREEN = 0xFF006400;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_main);
        getActionBar().hide();
        callFragment=new CallFrament();
        contactsFragment=new ContactsFragment();
        weatherFragment=new WeartherFrament();
        TVcall=(TextView) findViewById(R.id.call);
        TVcontacts=(TextView) findViewById(R.id.contacts);
        TVweather=(TextView) findViewById(R.id.wearther);
        TVcall.setOnClickListener(this);
        TVcontacts.setOnClickListener(this);
        TVweather.setOnClickListener(this);
        initFrament();
    }

    public void initFrament()
    {	TVcontacts.setTextSize(18);
    	myFragmentManager=getSupportFragmentManager();
    	myFragmentTransaction=myFragmentManager.beginTransaction();
    	myFragmentTransaction.replace(R.id.id_content, contactsFragment).commit();
    }
 @Override
	public void onClick(View v) {
	 
	    ContactMainActivity.this.myFragmentManager = getSupportFragmentManager();
	    ContactMainActivity.this.myFragmentTransaction = myFragmentManager.beginTransaction();
		
		if(v.getId()==R.id.call)
		{
			myFragmentTransaction.replace(R.id.id_content,callFragment).commit();
			TVcall.setTextColor(COLOR_GREEN);
			TVcontacts.setTextColor(COLOR_GRAY);
			TVweather.setTextColor(COLOR_GRAY);
			TVcontacts.setTextSize(12);
			TVweather.setTextSize(12);
			TVcall.setTextSize(20);
		}
		if(v.getId()==R.id.contacts)
		{
			myFragmentTransaction.replace(R.id.id_content,contactsFragment).commit();
			TVcall.setTextColor(COLOR_GRAY);
			TVcontacts.setTextColor(COLOR_GREEN);
			TVweather.setTextColor(COLOR_GRAY);
			TVcontacts.setTextSize(20);
			TVweather.setTextSize(12);
			TVcall.setTextSize(12);
		}
		if(v.getId()==R.id.wearther)
		{
			myFragmentTransaction.replace(R.id.id_content,weatherFragment).commit();
			TVcall.setTextColor(COLOR_GRAY);
			TVcontacts.setTextColor(COLOR_GRAY);
			TVweather.setTextColor(COLOR_GREEN);
			TVweather.setTextSize(20);
			TVcontacts.setTextSize(12);
			TVcall.setTextSize(12);
		}
	}
    
}
