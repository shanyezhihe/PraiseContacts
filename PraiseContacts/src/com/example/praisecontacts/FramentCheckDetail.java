package com.example.praisecontacts;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.praisecontacts.MyDialog.OnCustomDialogListener;

public class FramentCheckDetail extends Fragment {
	
	private SharedPreferences detailShare;
	private Editor detailedit;
	
	private TextView TVphone;
	private TextView TVqq;
	private TextView TVwechat;
	private TextView TVemail;
	private TextView TVaddress;
	

	private ImageView btn_call;
	private ImageView btn_sms;
	
	
	private static final String ContactDATABASE_NAME = "ContactsDB.db";
	public static final String ContactTABLE_NAME = "ContactsInfoTable";
	private MyContactsDataBaseHelper mContactHelper;
	private SQLiteDatabase contactDB;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view =inflater.inflate(R.layout.check_detail, container,false);
		
		TVphone=(TextView)view.findViewById(R.id.id_phonenum);
		TVqq=(TextView)view.findViewById(R.id.id_qqshow);
		TVwechat=(TextView)view.findViewById(R.id.id_wechatshow);
		TVemail=(TextView)view.findViewById(R.id.id_emailshow);
		TVaddress=(TextView)view.findViewById(R.id.id_addresslshow);
		
		btn_call=(ImageView) view.findViewById(R.id.call_phone);
		btn_sms=(ImageView) view.findViewById(R.id.send_sms);
		
		btn_call.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (TVphone.getText().toString() != null) {

					Intent phoneIntent = new Intent(
							"android.intent.action.CALL", Uri.parse("tel:"
									+ TVphone.getText().toString()));

					// Æô¶¯

					startActivity(phoneIntent);
				}
			}
		});

		btn_sms.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new MyDialog(getActivity(), R.style.add_dialog, null,
						new OnCustomDialogListener() {

							@Override
							public void back(int haveset) {
								// TODO Auto-generated method stub
							}
						}).show();
			}
		});
		
		
		return view;
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		 detailShare=getActivity().getSharedPreferences("SAVEDETAIL", 0);
		 detailedit=detailShare.edit();
		 
		 mContactHelper=new MyContactsDataBaseHelper(getActivity(), ContactDATABASE_NAME);
		contactDB=mContactHelper.getReadableDatabase();
		
		Cursor cursor=contactDB.rawQuery("select * from ContactsInfoTable where name=?", 
				new String[]{detailShare.getString("DETAILNAME", null)});
		if(cursor.moveToFirst())
		{
			TVphone.setText(cursor.getString(2));
			TVqq.setText(cursor.getString(4));
			TVwechat.setText(cursor.getString(5));
			TVemail.setText(cursor.getString(3));
			TVaddress.setText(cursor.getString(6)+cursor.getString(7)+cursor.getString(8));
		}
		SharedPreferences phoneNumShare = getActivity().getSharedPreferences("phoneNumShare",0);
		Editor phoneNumEditor=phoneNumShare.edit();
		phoneNumEditor.putString("PHONENUM",TVphone.getText().toString()).commit();
	} 
	
	
    
}
