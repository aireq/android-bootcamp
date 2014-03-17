package com.example.yamba;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class StatusProvider extends ContentProvider {

	public static final String AUTHORITY = "content://com.example.yamba.provider";
	public static final Uri CONTENT_URI = Uri.parse(AUTHORITY);

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

		long id = db.insertWithOnConflict(StatusData.TABLE, null, values,
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
		Cursor cursor = db.query(StatusData.TABLE, projection, selection,
				selectionArgs, null, null, sortOrder);

		Log.d(TAG, "Got cursor with columns: ");

		return cursor;

	}

}
