package com.example.yamba;

import android.app.ListActivity;
import android.content.Intent;
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

	static final String[] FROM = { StatusData.C_USER, StatusData.C_TEXT, StatusData.C_CREATED_AT };
	
	static final int[] TO = {R.id.text_user,R.id.text_text,R.id.text_created_at };
	
	static final String TAG = "TimelineActivity";

	SimpleCursorAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


		try {
			
			//these two lines are equivalent
			//list = (ListView) findViewById(android.R.id.list); 
			ListView list = getListView();
			
			cursor = ((YambaApp) getApplication()).statusData.query();

			
			
			
			
			adapter = new SimpleCursorAdapter(this, R.layout.row, cursor, FROM, TO);
			
			adapter.setViewBinder(VIEW_BINDER);
			
	
			setTitle(R.string.timeline);

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

			startActivity(new Intent(this,PrefsActivity.class));

			return true;
			
		
		case R.id.item_timeline:
			startActivity(new Intent(this,TimelineActivity.class));
			
			return true;
		
			
		

		default:

			return false; // button was not handled

		}

	}
	
}




