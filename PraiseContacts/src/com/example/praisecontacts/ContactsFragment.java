package com.example.praisecontacts;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;






import android.R.raw;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class ContactsFragment extends Fragment implements OnClickListener{
	
	private SharedPreferences itemShare;
	private Editor itemEditor;
	
	private LinearLayout TVbuildContact;
	private LinearLayout TVsearchContact;
	SharedPreferences detailShare;
	private Editor detailedit;
	private ListView phonecontactslist;
	private ListView familycontactslist;
	private ListView friendcontactslist;
	private ListView classcontactslist;
	private ListView colleaguecontactslist;
	private ListView othercontactslist;
	
	private ImageView phonecontactshow;
	private ImageView familycontactshow;
	private ImageView friendcontactshow;
	private ImageView classcontactshow;
	private ImageView colleaguecontactshow;
	private ImageView othercontactshow;
	
	private boolean isshowphone=false;
	private boolean isshowfamily=false;
	private boolean isshowfriend=false;
	private boolean isshowclass=false;
	private boolean isshowcolleague=false;
	private boolean isshowother=false;
	
	private static final int DATABASE_VERSION = 1;
	private static final String ContactDATABASE_NAME = "ContactsDB.db";
	public static final String ContactTABLE_NAME = "ContactsInfoTable";
	private MyContactsDataBaseHelper mContactHelper;
	private SQLiteDatabase contactDB;
	private Cursor cursor;
	private List<Map<String,String>> phonecontactNameList;
	private List<Map<String,String>> familycontactNameList;
	private List<Map<String,String>> friendcontactNameList;
	private List<Map<String,String>> classcontactNameList;
	private List<Map<String,String>> colleaguecontactNameList;
	private List<Map<String,String>> othercontactNameList;
	
	TextView name_text;
	int i;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_contacts, container, false);
		
		 detailShare=getActivity().getSharedPreferences("SAVEDETAIL", 0);
		 detailedit=detailShare.edit();
		itemShare=getActivity().getSharedPreferences("ITEMSHARE", 0);
		itemEditor=itemShare.edit();
		
		
		phonecontactNameList=new ArrayList<Map<String, String>>();
		familycontactNameList=new ArrayList<Map<String, String>>();
		friendcontactNameList=new ArrayList<Map<String, String>>();
		classcontactNameList=new ArrayList<Map<String, String>>();
		colleaguecontactNameList=new ArrayList<Map<String, String>>();
		othercontactNameList=new ArrayList<Map<String, String>>();
		
		phonecontactslist=(ListView) view.findViewById(R.id.phonecontacts_list);
		phonecontactslist.setVisibility(View.GONE);
		familycontactslist=(ListView) view.findViewById(R.id.familycontacts_list);
		familycontactslist.setVisibility(View.GONE);
		friendcontactslist=(ListView) view.findViewById(R.id.friendcontacts_list);
		friendcontactslist.setVisibility(View.GONE);
		classcontactslist=(ListView) view.findViewById(R.id.classcontacts_list);
		classcontactslist.setVisibility(View.GONE);
		colleaguecontactslist=(ListView) view.findViewById(R.id.colleaguecontacts_list);
		colleaguecontactslist.setVisibility(View.GONE);
		othercontactslist=(ListView) view.findViewById(R.id.othercontacts_list);
		othercontactslist.setVisibility(View.GONE);
		
		phonecontactshow=(ImageView) view.findViewById(R.id.btn_showphonecontact);
		phonecontactshow.setOnClickListener(this);
		familycontactshow=(ImageView) view.findViewById(R.id.btn_showfamilycontact);
		familycontactshow.setOnClickListener(this);
		friendcontactshow=(ImageView) view.findViewById(R.id.btn_showfriendcontact);
		friendcontactshow.setOnClickListener(this);
		classcontactshow=(ImageView) view.findViewById(R.id.btn_showclasscontact);
		classcontactshow.setOnClickListener(this);
		colleaguecontactshow=(ImageView) view.findViewById(R.id.btn_showcolleaguecontact);
		colleaguecontactshow.setOnClickListener(this);
		othercontactshow=(ImageView) view.findViewById(R.id.btn_showothercontact);
		othercontactshow.setOnClickListener(this);
		
		
		mContactHelper=new MyContactsDataBaseHelper(getActivity(), ContactDATABASE_NAME);
		contactDB=mContactHelper.getReadableDatabase();
		cursor=contactDB.rawQuery("select * from ContactsInfoTable", null);
		if(cursor.moveToFirst())
		{do{
			String tempname=cursor.getString(cursor.getColumnIndex("name"));
			String tempgroupname=cursor.getString(cursor.getColumnIndex("groupNum"));
			String tempType=cursor.getString(cursor.getColumnIndex("Type"));
			Map<String, String> item=new HashMap<String, String>();
			item.put("name", tempname);
			if(tempType=="1")
			phonecontactNameList.add(item);
			if(tempgroupname.equals("家人"))
				familycontactNameList.add(item);
			if(tempgroupname.equals("朋友"))
				friendcontactNameList.add(item);
			if(tempgroupname.equals("同学"))
				classcontactNameList.add(item);
			if(tempgroupname.equals("同事"))
				colleaguecontactNameList.add(item);
			if(tempgroupname.equals("其他"))
				othercontactNameList.add(item);
		}
		while
			(cursor.moveToNext());
		}
	
		phonecontactslist.setAdapter(new SimpleAdapter(getActivity(),
				phonecontactNameList, R.layout.list_item, new String[]{"name"},new int[]{R.id.id_name} ));
		familycontactslist.setAdapter(new SimpleAdapter(getActivity(),
				familycontactNameList, R.layout.list_item, new String[]{"name"},new int[]{R.id.id_name} ));
		friendcontactslist.setAdapter(new SimpleAdapter(getActivity(),
				friendcontactNameList, R.layout.list_item, new String[]{"name"},new int[]{R.id.id_name} ));
		classcontactslist.setAdapter(new SimpleAdapter(getActivity(),
				classcontactNameList, R.layout.list_item, new String[]{"name"},new int[]{R.id.id_name} ));
		colleaguecontactslist.setAdapter(new SimpleAdapter(getActivity(),
				colleaguecontactNameList, R.layout.list_item, new String[]{"name"},new int[]{R.id.id_name} ));
		othercontactslist.setAdapter(new SimpleAdapter(getActivity(),
				othercontactNameList, R.layout.list_item, new String[]{"name"},new int[]{R.id.id_name} ));
		
		
		phonecontactslist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				TextView name_text=(TextView) view.findViewById(R.id.id_name);
				detailedit.putString("DETAILNAME", name_text.getText().toString()).commit();
				Intent checkItent=new Intent(getActivity(),ActivityCheckContact.class);
				startActivity(checkItent);
			}
		});
		
		familycontactslist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				TextView name_text=(TextView) view.findViewById(R.id.id_name);
				detailedit.putString("DETAILNAME", name_text.getText().toString()).commit();
				Intent checkItent=new Intent(getActivity(),ActivityCheckContact.class);
				startActivity(checkItent);
			}
		});
		
		friendcontactslist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				TextView name_text=(TextView) view.findViewById(R.id.id_name);
				detailedit.putString("DETAILNAME", name_text.getText().toString()).commit();
				Intent checkItent=new Intent(getActivity(),ActivityCheckContact.class);
				startActivity(checkItent);
			}
		});
		
		classcontactslist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				TextView name_text=(TextView) view.findViewById(R.id.id_name);
				detailedit.putString("DETAILNAME", name_text.getText().toString()).commit();
				Intent checkItent=new Intent(getActivity(),ActivityCheckContact.class);
				startActivity(checkItent);
			}
		});
		
		colleaguecontactslist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				TextView name_text=(TextView) view.findViewById(R.id.id_name);
				detailedit.putString("DETAILNAME", name_text.getText().toString()).commit();
				Intent checkItent=new Intent(getActivity(),ActivityCheckContact.class);
				startActivity(checkItent);
			}
		});
		
		othercontactslist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				 name_text=(TextView) view.findViewById(R.id.id_name);
				detailedit.putString("DETAILNAME", name_text.getText().toString()).commit();
				Intent checkItent=new Intent(getActivity(),ActivityCheckContact.class);
				startActivity(checkItent);
				i=1;
			}
		});
		
		
		
		
		TVbuildContact=(LinearLayout) view.findViewById(R.id.build_contacts);
		TVsearchContact=(LinearLayout) view.findViewById(R.id.search_contacts);
		
		TVbuildContact.setOnClickListener(this);
		TVsearchContact.setOnClickListener(this);
		
		
		return view;
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		contactDB.close();
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		int i=1;
		phonecontactNameList=new ArrayList<Map<String, String>>();
		familycontactNameList=new ArrayList<Map<String, String>>();
		friendcontactNameList=new ArrayList<Map<String, String>>();
		classcontactNameList=new ArrayList<Map<String, String>>();
		colleaguecontactNameList=new ArrayList<Map<String, String>>();
		othercontactNameList=new ArrayList<Map<String, String>>();
		mContactHelper=new MyContactsDataBaseHelper(getActivity(), ContactDATABASE_NAME);
		contactDB=mContactHelper.getReadableDatabase();
		cursor=contactDB.rawQuery("select * from ContactsInfoTable", null);
		if(cursor.moveToFirst())
		{do{
			String tempname=cursor.getString(cursor.getColumnIndex("name"));
			String tempgroupname=cursor.getString(cursor.getColumnIndex("groupNum"));
			String tempType=cursor.getString(cursor.getColumnIndex("Type"));
			Map<String, String> item=new HashMap<String, String>();
			item.put("name", tempname);
			if(tempType=="1")
			phonecontactNameList.add(item);
			if(tempgroupname.equals("家人"))
				familycontactNameList.add(item);
			if(tempgroupname.equals("朋友"))
				friendcontactNameList.add(item);
			if(tempgroupname.equals("同学"))
				classcontactNameList.add(item);
			if(tempgroupname.equals("同事"))
				colleaguecontactNameList.add(item);
			if(tempgroupname.equals("其他"))
				othercontactNameList.add(item);
		}
		while
			(cursor.moveToNext());
		}
	
		phonecontactslist.setAdapter(new SimpleAdapter(getActivity(),
				phonecontactNameList, R.layout.list_item, new String[]{"name"},new int[]{R.id.id_name} ));
		familycontactslist.setAdapter(new SimpleAdapter(getActivity(),
				familycontactNameList, R.layout.list_item, new String[]{"name"},new int[]{R.id.id_name} ));
		friendcontactslist.setAdapter(new SimpleAdapter(getActivity(),
				friendcontactNameList, R.layout.list_item, new String[]{"name"},new int[]{R.id.id_name} ));
		classcontactslist.setAdapter(new SimpleAdapter(getActivity(),
				classcontactNameList, R.layout.list_item, new String[]{"name"},new int[]{R.id.id_name} ));
		colleaguecontactslist.setAdapter(new SimpleAdapter(getActivity(),
				colleaguecontactNameList, R.layout.list_item, new String[]{"name"},new int[]{R.id.id_name} ));
		othercontactslist.setAdapter(new SimpleAdapter(getActivity(),
				othercontactNameList, R.layout.list_item, new String[]{"name"},new int[]{R.id.id_name} ));
		
//		if(i==1)
//		{
//			name_text.setText(detailShare.getString("DETAILNAME", null));
//			i=0;
//		}
		
		
//		if(itemShare.getString("NEWNAME", null)!=null)
//		{
//			Map<String, String > item=new HashMap<String, String>();
//			item.put("name", itemShare.getString("NEWNAME", null));
//			if(itemShare.getString("NEWGROUPNAME", null).equals("家人"))
//				familycontactNameList.add(item);
//			if(itemShare.getString("NEWGROUPNAME", null).equals("朋友"))
//				friendcontactNameList.add(item);
//			if(itemShare.getString("NEWGROUPNAME", null).equals("同学"))
//				classcontactNameList.add(item);
//			if(itemShare.getString("NEWGROUPNAME", null).equals("同事"))
//				colleaguecontactNameList.add(item);
//			if(itemShare.getString("NEWGROUPNAME", null).equals("其他"))
//				othercontactNameList.add(item);
////			phonecontactNameList.add(m);
//		itemEditor.putString("NEWNAME", null);
//		itemEditor.putString("NEWGROUPNAME", null).commit();
//		phonecontactslist.setAdapter(new SimpleAdapter(getActivity(),
//				phonecontactNameList, R.layout.list_item, new String[]{"name"},new int[]{R.id.id_name} ));
//		familycontactslist.setAdapter(new SimpleAdapter(getActivity(),
//				familycontactNameList, R.layout.list_item, new String[]{"name"},new int[]{R.id.id_name} ));
//		friendcontactslist.setAdapter(new SimpleAdapter(getActivity(),
//				friendcontactNameList, R.layout.list_item, new String[]{"name"},new int[]{R.id.id_name} ));
//		classcontactslist.setAdapter(new SimpleAdapter(getActivity(),
//				classcontactNameList, R.layout.list_item, new String[]{"name"},new int[]{R.id.id_name} ));
//		colleaguecontactslist.setAdapter(new SimpleAdapter(getActivity(),
//				colleaguecontactNameList, R.layout.list_item, new String[]{"name"},new int[]{R.id.id_name} ));
//		othercontactslist.setAdapter(new SimpleAdapter(getActivity(),
//				othercontactNameList, R.layout.list_item, new String[]{"name"},new int[]{R.id.id_name} ));
		

			
	//	}
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
		case R.id.btn_showphonecontact:
			if (!isshowphone) {
				phonecontactshow
						.setImageResource(R.drawable.common_hide_list);
				isshowphone = true;
				phonecontactslist.setVisibility(View.VISIBLE);
			} else {
				phonecontactshow
						.setImageResource(R.drawable.common_show_list);
				isshowphone  = false;

				phonecontactslist.setVisibility(View.GONE);
			}
			break;
		case R.id.btn_showfamilycontact:
			if (!isshowfamily) {
				familycontactshow
						.setImageResource(R.drawable.common_hide_list);
				isshowfamily = true;
				familycontactslist.setVisibility(View.VISIBLE);
			} else {
				familycontactshow
						.setImageResource(R.drawable.common_show_list);
				isshowfamily  = false;

				familycontactslist.setVisibility(View.GONE);
			}
			break;
		case R.id.btn_showfriendcontact:
			if (!isshowfriend) {
				friendcontactshow
						.setImageResource(R.drawable.common_hide_list);
				isshowfriend = true;
				friendcontactslist.setVisibility(View.VISIBLE);
			} else {
				friendcontactshow
						.setImageResource(R.drawable.common_show_list);
				isshowfriend  = false;

				friendcontactslist.setVisibility(View.GONE);
			}
			break;
		case R.id.btn_showclasscontact:
			if (!isshowclass) {
				classcontactshow
						.setImageResource(R.drawable.common_hide_list);
				isshowclass = true;
				classcontactslist.setVisibility(View.VISIBLE);
			} else {
				classcontactshow
						.setImageResource(R.drawable.common_show_list);
				isshowclass  = false;

				classcontactslist.setVisibility(View.GONE);
			}
			break;
		case R.id.btn_showcolleaguecontact:
			if (!isshowcolleague) {
				colleaguecontactshow
						.setImageResource(R.drawable.common_hide_list);
				isshowcolleague = true;
				colleaguecontactslist.setVisibility(View.VISIBLE);
			} else {
				colleaguecontactshow
						.setImageResource(R.drawable.common_show_list);
				isshowcolleague  = false;

				colleaguecontactslist.setVisibility(View.GONE);
			}
			
			break;
		case R.id.btn_showothercontact:
			
			if (!isshowother) {
				othercontactshow
						.setImageResource(R.drawable.common_hide_list);
				isshowother = true;
				othercontactslist.setVisibility(View.VISIBLE);
			} else {
				othercontactshow
						.setImageResource(R.drawable.common_show_list);
				isshowother  = false;

				othercontactslist.setVisibility(View.GONE);
			}
			
			break;
		}
		
	}


	
	
	
}
