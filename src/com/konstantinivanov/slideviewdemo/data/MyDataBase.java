package com.konstantinivanov.slideviewdemo.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.konstantinivanov.slideviewdemo.activities.MainActivity;

public class MyDataBase {
	
	private static final int DB_VER = 1;
	private static final String DB_NAME = "MyDataBase.db";
	
	public static final String TABLE_NAME = "mTable";
	public static final String COLUMN_NAME = "title";
	public static final String COLUMN_URL = "url";
	public static final String ID= "_id";
	private static final String Create_Table1 = "create table " +
            TABLE_NAME +  " ( "  + ID + " integer PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME + " TEXT NOT NULL, "
			                   + COLUMN_URL + " TEXT NOT NULL);";
	
	private final Context mContext;
	
	private DBHelper mDBHelper;
	private SQLiteDatabase mDB;
	
	public MyDataBase(Context context) {
		mContext = context;
	}
	
	
	public void open() {
		mDBHelper = new DBHelper(mContext, DB_NAME, null, DB_VER);
		mDB = mDBHelper.getWritableDatabase();
	}
	
	public void close() {
		if (mDBHelper!=null) mDBHelper.close();
	}
	
	public Cursor getAllData() {
		return mDB.query(TABLE_NAME, null, null, null , null, null, null);
	}
	
	public void AddRec(String title, String url) {
		ContentValues cv = new ContentValues();
		cv.put(COLUMN_NAME, title);
		cv.put(COLUMN_URL, url);
		mDB.insert(TABLE_NAME, null, cv);
	}
	
	public void DelRec(long id) {
		mDB.delete(DB_NAME, ID + " = " + id, null);
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
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
			onCreate(db);
		}
		
	}
}
