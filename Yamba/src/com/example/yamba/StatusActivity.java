package com.example.yamba;

import winterwell.jtwitter.Twitter;
import winterwell.jtwitter.TwitterException;
import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.EditText;
import android.widget.Toast;

public class StatusActivity extends Activity implements LocationListener {

	static final String TAG = "StatusActivity";
	static final String PROVIDER = LocationManager.GPS_PROVIDER;

	EditText status_editText;
	LocationManager locationManager;
	Location location;

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	
		Log.d(TAG,"onPause removing location updates");
		
		locationManager.removeUpdates(this);
	}



	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		
		Log.d(TAG,"onResume requesting locating upates");
		
		locationManager.requestLocationUpdates(PROVIDER, 30, 1000, this);
		
		
	}



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
		
		
		locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
		
		location = locationManager.getLastKnownLocation(PROVIDER);
		
		

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
		Log.d(TAG, "Posting new status: " + statusText);

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

				Log.d(TAG, "Successfuly posted status: " + statusText[0]);

				return "Posted Status Succeeded";

			} catch (TwitterException e) {

				Log.e(TAG, "Error posting status", e);

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

	@Override
	public void onLocationChanged(Location location) {
		this.location = location;
		Log.d(TAG,"onLocationChanged"+location.toString());
	}



	@Override
	public void onProviderDisabled(String provider) {
	
		
	}



	@Override
	public void onProviderEnabled(String provider) {
	
	}



	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		
	}

}
