package com.example.praisecontacts;

import android.app.Activity;
import android.os.Bundle;

public class ActivityEditContact extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_contact);
		getActionBar().hide();
	}
	
}
