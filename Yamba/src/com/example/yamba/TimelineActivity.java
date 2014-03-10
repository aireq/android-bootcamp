package com.example.yamba;

import android.os.Bundle;
import android.provider.VoicemailContract.Status;
import android.app.Activity;
import android.database.Cursor;

import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;

import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.SimpleCursorAdapter.ViewBinder;
import android.widget.TextView;

public class TimelineActivity extends Activity {

	ListView list;
	Cursor cursor;

	static final String[] FROM = { StatusData.C_USER, StatusData.C_TEXT, StatusData.C_CREATED_AT };
	
	static final int[] TO = {R.id.text_user,R.id.text_text,R.id.text_created_at };
	
	static final String TAG = "TimelineActivity";

	SimpleCursorAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);

		try {
			list = (ListView) findViewById(R.id.item_list);
			cursor = ((YambaApp) getApplication()).statusData.query();

			
			
			adapter = new SimpleCursorAdapter(this, R.layout.row, cursor, FROM, TO);
			
			adapter.setViewBinder(VIEW_BINDER);

			list.setAdapter(adapter);
		} catch (Exception e) {

			Log.e(TAG, "Error opening timeline", e);

		}

	}
	
	
	static final ViewBinder VIEW_BINDER = new ViewBinder()
	{
		
		@Override
		public boolean setViewValue(View view, Cursor cursor, int columnIdx)
		{
			//this is going to be called for every column
			
			//checks that the view is not equal to created at
			if(view.getId() != R.id.text_created_at) return false;
			else
			{
				
				long time = cursor.getLong(cursor.getColumnIndex(StatusData.C_CREATED_AT));
				CharSequence relTime = DateUtils.getRelativeTimeSpanString(time);
				
				TextView text = (TextView)view;
				
				text.setText(relTime);
				
				
				return true;
			}
			
			
	
		}
		

	};
	
}




