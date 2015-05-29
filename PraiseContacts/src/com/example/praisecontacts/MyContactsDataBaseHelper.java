package com.example.praisecontacts;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MyContactsDataBaseHelper extends SQLiteOpenHelper {
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "ContactsDB.db";
	public static final String TABLE_NAME = "ContactsInfoTable";

	public MyContactsDataBaseHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
	}
	
	public MyContactsDataBaseHelper(Context context, String name, int version){
		this(context,name,null,version);
	}

	public MyContactsDataBaseHelper(Context context, String name){
		this(context,name,DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		StringBuffer sBuffer = new StringBuffer();

		sBuffer.append("CREATE TABLE [" + TABLE_NAME + "] (");
//		sBuffer.append("[zoneName] TEXT, ");
		sBuffer.append("[name] TEXT, ");
		sBuffer.append("[groupNum] TEXT, ");
		sBuffer.append("[phoneNum] TEXT,");
		sBuffer.append("[EmailNum] TEXT,");
		sBuffer.append("[QQNum] TEXT,");
//		sBuffer.append("[authType] TEXT,");
		sBuffer.append("[WeChatNum] TEXT, ");
		sBuffer.append("[provice] TEXT, ");
		sBuffer.append("[city] TEXT, ");
		sBuffer.append("[distr] TEXT, ");
		sBuffer.append("[Type] TEXT)");
		
		db.execSQL(sBuffer.toString());
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
