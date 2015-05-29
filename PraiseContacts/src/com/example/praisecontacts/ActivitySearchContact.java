package com.example.praisecontacts;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ActivitySearchContact extends Activity implements OnClickListener {
	private RelativeLayout back_layout;
	private AutoCompleteTextView autoEdit;
	private TextView btn_search;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_contacts);
		getActionBar().hide();
		back_layout=(RelativeLayout) findViewById(R.id.search_back);
		back_layout.setOnClickListener(this);
		autoEdit=(AutoCompleteTextView) findViewById(R.id.id_autocomplete);
		autoEdit.setOnClickListener(this);
		btn_search=(TextView) findViewById(R.id.btn_search);
		 btn_search.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
		case R.id.search_back:
			ActivitySearchContact.this.finish();
			break;
		case R.id.btn_search:
			
		}
	}
	
}
