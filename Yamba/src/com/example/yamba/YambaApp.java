package com.example.yamba;

import winterwell.jtwitter.Twitter;
import android.app.Application;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.preference.PreferenceManager;
import android.util.Log;

public class YambaApp extends Application implements OnSharedPreferenceChangeListener {

	static final String LOG_TAG = "YambaApp";
	private Twitter twitter;

	SharedPreferences prefs;
	StatusData statusData;
	

	public Twitter getTwitter() {

		if (twitter == null) {
			
			String user = prefs.getString("username", "");
			String password = prefs.getString("password", "");
			String server = prefs.getString("server", "");

			twitter = new Twitter(user, password);

			twitter.setAPIRootUrl(server);

		}
		return twitter;
	}

	@Override
	public void onCreate() {

		super.onCreate();
		

		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		
		prefs.registerOnSharedPreferenceChangeListener(this);
		
		statusData = new StatusData(this);

		Log.d(LOG_TAG, "onCreate");
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		twitter = null;		
		
		Log.d(LOG_TAG,"onSharedPreferenceChanged for key:"+key);
	}

}
