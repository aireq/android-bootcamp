package com.example.yamba;

import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;

import android.util.Log;

import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class TimelineActivity extends Activity {

	ListView list;
	Cursor cursor;

	static final String[] FROM = { StatusData.C_USER, StatusData.C_TEXT };
	static final int[] TO = { android.R.id.text1, android.R.id.text2 };
	static final String TAG = "TimelineActivity";

	SimpleCursorAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);

		try {
			list = (ListView) findViewById(R.id.item_list);
			cursor = ((YambaApp) getApplication()).statusData.query();

			adapter = new SimpleCursorAdapter(this,
					android.R.layout.two_line_list_item, cursor, FROM, TO);

			list.setAdapter(adapter);
		} catch (Exception e) {

			Log.e(TAG, "Error opening timeline", e);

		}

	}
}
