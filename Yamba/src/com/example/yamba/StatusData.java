package com.example.yamba;

import java.util.List;

import winterwell.jtwitter.Twitter.Status;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class StatusData {
	// C_ means column

	static final String TAG = "StatusData";
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

	public StatusData(Context context) {
		this.context = context;

		dbHelper = new DbHelper(context);

	}

	public void insert(Status status) {

		// this is bad, will allow sync injection
		// also slow because
		// String sql = "Insert into status () values ()"
		// db.execSQL(sql);

		ContentValues values = new ContentValues();
		values.put(C_ID, status.id);

		// stores values as milliseconds since 1/1/1970
		values.put(C_CREATED_AT, status.createdAt.getTime());

		values.put(C_USER, status.user.name);
		values.put(C_TEXT, status.text);

		context.getContentResolver().insert(StatusProvider.CONTENT_URI, values);

	}

	public Cursor query() {

		return context.getContentResolver().query(StatusProvider.CONTENT_URI,
				null, null, null, StatusData.C_CREATED_AT + " DESC");

	}

	// this is an inner class because it doesn't needs to be exposed outside
	// StatusData

}
