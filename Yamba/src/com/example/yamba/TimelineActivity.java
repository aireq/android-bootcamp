package com.example.yamba;

import android.app.ListActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.SimpleCursorAdapter.ViewBinder;
import android.widget.TextView;

public class TimelineActivity extends ListActivity {

	Cursor cursor;

	static final String[] FROM = { StatusData.C_USER, StatusData.C_TEXT,
			StatusData.C_CREATED_AT };

	static final int[] TO = { R.id.text_user, R.id.text_text,
			R.id.text_created_at };

	static final String TAG = "TimelineActivity";

	SimpleCursorAdapter adapter;
	TimelineReciever receiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		try {

			// these two lines are equivalent
			// list = (ListView) findViewById(android.R.id.list);

			// Sets the Title
			setTitle(R.string.timeline);

			cursor = ((YambaApp) getApplication()).statusData.query();

			// Creates a new SimpleCursorAdapter
			adapter = new SimpleCursorAdapter(this, R.layout.row, cursor, FROM,
					TO);

			// Sets the ViewBinder for the ListAdapter
			adapter.setViewBinder(VIEW_BINDER);

			// List is set to adapter
			ListView list = getListView();
			list.setAdapter(adapter);

		} catch (Exception e) {

			Log.e(TAG, "Error opening timeline", e);

		}

	}

	static final ViewBinder VIEW_BINDER = new ViewBinder() {

		@Override
		public boolean setViewValue(View view, Cursor cursor, int columnIdx) {
			// this is going to be called for every column

			// checks that the view is not equal to created at
			if (view.getId() != R.id.text_created_at)
				return false;
			else {

				long time = cursor.getLong(cursor
						.getColumnIndex(StatusData.C_CREATED_AT));
				CharSequence relTime = DateUtils
						.getRelativeTimeSpanString(time);

				TextView text = (TextView) view;

				text.setText(relTime);

				return true;
			}

		}

	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub

		getMenuInflater().inflate(R.menu.menu, menu);

		return true;

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		// Service intent is created with a context, and a reference to a
		// service class

		Intent intent;

		switch (item.getItemId()) {

		case R.id.item_start_service:

			intent = new Intent(this, UpdaterService.class);

			startService(intent);

			return true; // button was handled

		case R.id.item_stop_service:

			intent = new Intent(this, UpdaterService.class);

			stopService(intent);

			return true; // button was handled

		case R.id.item_refresh:

			intent = new Intent(this, RefreshService.class);

			startService(intent);
			
			

			return true;

		case R.id.item_prefs:

			
			startActivity(new Intent(this, PrefsActivity.class));

			return true;

		case R.id.item_status_update:
			startActivity(new Intent(this, StatusActivity.class));

			return true;

		default:

			return false; // button was not handled

		}

	}
	
	
	
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
		unregisterReceiver(receiver);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		//lazy initialization of receiver
		if(receiver == null) receiver = new TimelineReciever();
				
		
		registerReceiver(receiver, new IntentFilter(YambaApp.ACTION_NEW_STATUS));
		
		
	}




	class TimelineReciever extends BroadcastReceiver 
	{
		@Override
		public void onReceive(Context context, Intent intent) 
		{
			//update the cursor on receive
			cursor = ((YambaApp) getApplication()).statusData.query();
			adapter.changeCursor(cursor);
			
			Log.d(TAG,"TimelineReciver onReceive changeCursor with count "+intent.getIntExtra("count", 0));
		}
		
		
		
		
	}
	
	
	
	
	
	

}
