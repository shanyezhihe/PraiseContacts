package com.example.praisecontacts;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class MyContactsListAdapter extends BaseAdapter {
	private Context context;
	private int  list_size;
	 public MyContactsListAdapter(Context context,int list_size) {
		super();
		this.context=context;
		this.list_size=list_size;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list_size;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	}

}
