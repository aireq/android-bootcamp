package com.example.yamba;

import winterwell.jtwitter.Twitter;
import winterwell.jtwitter.TwitterException;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.EditText;
import android.widget.Toast;

public class StatusActivity extends Activity {

	static final String LOG_TAG = "StatusActivity";

	EditText status_editText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// starts method tracing
		//Debug.startMethodTracing("Yamba");

		// this line uses R to lookup activity_status.xml
		// reads XML and creates java classes and set's properties
		// after this line all widgets (buttons and controls) are in memory
		setContentView(R.layout.activity_status);

		// gets references to widgets
		status_editText = (EditText) findViewById(R.id.editStatusText);

	}

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

	@Override
	protected void onStop() {
		// good to use onStop because this can be controls
		// onStop happens when pressing back button or switching to another
		// activity

		super.onStop();

		//Debug.stopMethodTracing();
	}

	public void onClick(View arg0) {

		// TODO Auto-generated method stub
		final String statusText = status_editText.getText().toString();
		Log.d(LOG_TAG, "Posting new status: " + statusText);

		new PostToTwitter().execute(statusText);

	}

	// <params, progress, result>
	class PostToTwitter extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... statusText) {

			// this happens on alternate thread

			try {
				String user = "student";
				String pass = "password";
				Twitter twitter = new Twitter(user, pass);
				twitter.setAPIRootUrl("http://yamba.marakana.com/api");
				twitter.setStatus(statusText[0]);

				Log.d(LOG_TAG, "Successfuly posted status: " + statusText[0]);

				return "Posted Status Succeeded";

			} catch (TwitterException e) {

				Log.e(LOG_TAG, "Error posting status", e);

				return "Posting Status Failed!";

			}

		}

		@Override
		protected void onPostExecute(String result) {
			// this happens on UI Thread

			Toast.makeText(StatusActivity.this, result, Toast.LENGTH_SHORT)
					.show();
		}

	}

}
