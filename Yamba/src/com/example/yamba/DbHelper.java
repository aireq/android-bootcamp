package com.example.yamba;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

class DbHelper extends SQLiteOpenHelper {
	


	public DbHelper(Context context) 
	{
		super(context, StatusData.DB_NAME, null, StatusData.DB_VERSION);
	}

	public static final String TAG = "DbHelper";



	@Override
	public void onCreate(SQLiteDatabase db) {
		// This happens only once
		// Called to create a database

		String sql;

		sql = String.format("create table %s"
				+ "(%s int primary key, %s int,%s text, %s text)", StatusData.TABLE, StatusData.C_ID,
				StatusData.C_CREATED_AT, StatusData.C_USER, StatusData.C_TEXT);

		Log.d(TAG, "onCreate");

		try {
			db.execSQL(sql);

			Log.d(TAG, "Created table:" + sql);

		} catch (Exception e) {
			Log.e(TAG, "Error creating database",e);
		}

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Typicaly ALTER TABLE statement to keep existing data!!!

		// Can do this for development
		// Will drop and recreate the table if the database is updated
		db.execSQL("drop if exists " + StatusData.TABLE);
		onCreate(db);

	}

}
