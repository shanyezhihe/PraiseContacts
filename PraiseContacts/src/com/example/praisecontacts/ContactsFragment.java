package com.example.praisecontacts;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class ContactsFragment extends Fragment implements OnClickListener, OnItemClickListener {
	private LinearLayout TVbuildContact;
	private LinearLayout TVsearchContact;
	SharedPreferences positionSharp;
	Editor edit;
	private ListView contactslist;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_contacts, container, false);
		
		 positionSharp=getActivity().getSharedPreferences("SAVEPOSITION", 0);
		 edit=positionSharp.edit();
		
		TVbuildContact=(LinearLayout) view.findViewById(R.id.build_contacts);
		TVsearchContact=(LinearLayout) view.findViewById(R.id.search_contacts);
		
		TVbuildContact.setOnClickListener(this);
		TVsearchContact.setOnClickListener(this);
		
		contactslist=(ListView) view.findViewById(R.id.contacts_list);
		
		
		
		List<Map<String,Object>> listItems=new ArrayList<Map<String, Object>>();
		for(int i=0;i<100;i++)
		{
			Map<String, Object> item=new HashMap<String, Object>();
			item.put("name", "helloworld"+String.valueOf(i));
			listItems.add(item);
		}
		
		SimpleAdapter  myAdapter=new SimpleAdapter(getActivity(),
				listItems, R.layout.list_item, new String[]{"name"},new int[]{R.id.id_name} );
		contactslist.setAdapter(myAdapter);
		
		contactslist.setOnItemClickListener(this);
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
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		 edit.putInt("POSITION", position);
		 edit.commit();
		 Intent checkIntent=new Intent(getActivity(),ActivityCheckContact.class);
		 startActivity(checkIntent);
		
	}
	
}
