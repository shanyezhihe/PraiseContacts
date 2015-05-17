package com.example.praisecontacts;



import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ContactsFragment extends Fragment implements OnClickListener {
	private LinearLayout TVbuildContact;
	private LinearLayout TVsearchContact;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_contacts, container, false);
		TVbuildContact=(LinearLayout) view.findViewById(R.id.build_contacts);
		TVsearchContact=(LinearLayout) view.findViewById(R.id.search_contacts);
		TVbuildContact.setOnClickListener(this);
		TVsearchContact.setOnClickListener(this);
		
		return view;
	}
	@Override
	public void onClick(View v) {
		int btn_id=v.getId();
		switch (btn_id) {
		case R.id.build_contacts:
			Intent buildIntent=new Intent(getActivity(),ActivityBuildContact.class);
			startActivity(buildIntent);
			break;
		case R.id.search_contacts:
			Intent searchIntent=new Intent(getActivity(),ActivitySearchContact.class);
			startActivity(searchIntent);
			break;
		}
		
	}
	
}
