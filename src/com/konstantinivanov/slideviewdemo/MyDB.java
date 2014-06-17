package com.konstantinivanov.slideviewdemo;

//Класс базы данных

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class MyDB {
	
	private static final int DB_Ver = 1;
	private static final String DB_Name = "MyDataBase0706.db";
	
	public static final String Table_Name  = "mTable";
	public static final String Column_Name = "title";
	public static final String Column_URL = "url";
	public static final String ID= "_id";
	private static final String Create_Table1 = "create table " + 
	                           Table_Name +  " ( "  + ID + " integer PRIMARY KEY AUTOINCREMENT, " + Column_Name + " TEXT NOT NULL, "
			                   + Column_URL  + " TEXT NOT NULL);";
	
	private final Context mContext;
	
	private DBHelper mDBHelper;
	private SQLiteDatabase mDB;
	
	public MyDB(Context cntxt) {
		mContext = cntxt;
	}
	
	
	public void open() {
		DBHelper mDBHelper = new DBHelper(mContext, DB_Name, null, DB_Ver);
		mDB = mDBHelper.getWritableDatabase();
	}
	
	public void close() {
		if (mDBHelper!=null) mDBHelper.close();
	}
	
	public Cursor getAllData() {
		return mDB.query(Table_Name, null, null, null , null, null, null);
	}
	
	public void AddRec(String title, String url) {
		ContentValues cv = new ContentValues();
		cv.put(Column_Name, title);
		cv.put(Column_URL, url);
		mDB.insert(Table_Name, null, cv);
	}
	
	public void DelRec(long id) {
		mDB.delete(DB_Name, ID + " = " + id, null);
	}
	
	
	private class DBHelper extends SQLiteOpenHelper {
		
		
		public DBHelper (Context context, String name, CursorFactory mCursor, int version) {
			
			super(context, name, mCursor, version);
		}
		
		@Override
		public void onCreate(SQLiteDatabase db) {
			
			db.execSQL(Create_Table1);
		}
		
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			
			Log.d(MainActivity.TAG, "Upgrading database. Existing contents will be lost. ["
	                + oldVersion + "]->[" + newVersion + "]");
			db.execSQL("DROP TABLE IF EXISTS " + Table_Name);
			onCreate(db);
		}
		
	}
}
