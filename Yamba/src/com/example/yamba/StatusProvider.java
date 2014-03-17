package com.example.yamba;

import winterwell.jtwitter.Twitter.Status;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class StatusProvider extends ContentProvider {

	public static final String AUTHORITY = "content://com.example.yamba.provider";
	public static final Uri CONTENT_URI = Uri.parse(AUTHORITY);
	public static final String C_ID = "_id";
	public static final String C_CREATED_AT = "created_at";
	public static final String C_USER = "username";
	public static final String C_TEXT = "status_text";
	public static final String DB_NAME = "timeline.db";
	public static final int DB_VERSION = 1;
	public static final String TABLE = "status";
	
	

	public static final String TAG = "StatusProvider";
	
	SQLiteDatabase db;
	DbHelper dbHelper;

	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub

		dbHelper = new DbHelper(getContext());

		return false;
	}

	@Override
	public String getType(Uri uri) {

		if (uri.getLastPathSegment() == null) {
			return "vnd.android.cursor.item/vnd.example.yamba.status";
		} else {
			return "vnd.android.cursor.dir/vnd.example.yamba.status";
		}

	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {

		db = dbHelper.getWritableDatabase();

		long id = db.insertWithOnConflict(TABLE, null, values,
				SQLiteDatabase.CONFLICT_IGNORE);

		if (id != -1) {
			return uri.withAppendedPath(uri, Long.toString(id));
		} else {
			return uri;
		}

	}

	@Override
	public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {

		db = dbHelper.getReadableDatabase();

		// SELECT * from status
		Cursor cursor = db.query(TABLE, projection, selection,
				selectionArgs, null, null, sortOrder);

		Log.d(TAG, "Got cursor with columns: ");

		return cursor;

	}
	
	
	
	public static ContentValues statusToValues(Status status)
	{
		ContentValues values = new ContentValues();
		values.put(C_ID, status.id);

		// stores values as milliseconds since 1/1/1970
		values.put(C_CREATED_AT, status.createdAt.getTime());

		values.put(C_USER, status.user.name);
		values.put(C_TEXT, status.text);
		
		
		return values;
	}

}
