package com.example.yamba;

import winterwell.jtwitter.Twitter.Status;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class StatusData {
	// C_ means column

	static final String LOG_TAG = "StatusData";
	public static final String C_ID = "_id";
	public static final String C_CREATED_AT = "created_at";
	public static final String C_USER = "username";
	public static final String C_TEXT = "status_text";

	public static final String DB_NAME = "timeline.db";

	public static final int DB_VERSION = 1;

	public static final String TABLE = "status";

	Context context;
	DbHelper dbHelper;
	SQLiteDatabase db;
	
	public StatusData(Context context)
	{
		this.context = context;
		
		dbHelper = new DbHelper();
		
	}
	
	public void insert(Status status)
	{
		
		db = dbHelper.getWritableDatabase();
		
		
		
				
		//this is bad, will allow sync injection
		//also slow because
		//String sql = "Insert into status () values ()"
		//db.execSQL(sql);
		
		
		ContentValues values = new ContentValues();
		values.put(C_ID, status.id);
		
		//stores values as miliseconds since 1/1/1970
		values.put(C_CREATED_AT, status.createdAt.getTime());
		
		
		values.put(C_USER, status.user.name);
		values.put(C_TEXT, status.text);
		
		db.insert(TABLE, null, values);
		
		
		
		
		
	}
	
	
	
	
	

	// this is an inner class because it doesn't needs to be exposed outside
	// StatusData

	class DbHelper extends SQLiteOpenHelper {

		public DbHelper() {

			// Factory is not needed in this case
			super(context, DB_NAME, null, DB_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// This happens only once
			// Called to create a database

			String sql;

			sql = String.format("create table %s"
					+ "(%S int primary key, %s int,%s text, %s text)", TABLE,
					C_ID, C_CREATED_AT, C_USER, C_TEXT);

			Log.d(LOG_TAG, "onCreate");

			try {
				db.execSQL(sql);
				
				Log.d(LOG_TAG,"Created table:"+ sql);
				

			} catch (Exception e) {
				// TODO: handle exception
			}
			

			
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
		{
			//Typicaly ALTER TABLE statement to keep existing data!!!
			
			//Can do this for development
			//Will drop and recreate the table if the database is updated
			db.execSQL("drop if exists "+TABLE);
			onCreate(db);

		}

	}

}
