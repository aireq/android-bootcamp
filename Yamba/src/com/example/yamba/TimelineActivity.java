package com.example.yamba;

import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.view.Menu;
import android.widget.TextView;

public class TimelineActivity extends Activity {

	TextView textOut;
	Cursor cursor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);

		textOut = (TextView) findViewById(R.id.timeline_textview);

		cursor = ((YambaApp) getApplication()).statusData.query();

		while (cursor.moveToNext()) {

			String user = cursor.getString(cursor.getColumnIndex(StatusData.C_USER));
			String message = cursor.getString(cursor.getColumnIndex(StatusData.C_TEXT));

			textOut.append(String.format("\n%s: %s", user, message));

		}

	}

}
