package com.example.yamba;

import winterwell.jtwitter.Twitter;
import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class YambaApp extends Application
{
	
	static final String LOG_TAG = "YambaApp";
	private Twitter twitter;
	
	SharedPreferences prefs;
	
	public Twitter getTwitter() {
		return twitter;
	}



	@Override
	public void onCreate() {

		super.onCreate();
		
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		
		
		String user = prefs.getString("username", "");
		String password = prefs.getString("password", "");
		String server = prefs.getString("server", "");
		
		twitter = new Twitter(user, password);
		
		twitter.setAPIRootUrl(server);
		
		
		
		
		Log.d(LOG_TAG,"onCreate");
	}
	
	

}
