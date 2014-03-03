package com.example.yamba;

import java.util.List;

import winterwell.jtwitter.Twitter;
import winterwell.jtwitter.Twitter.Status;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class UpdaterService extends Service {
	
	static final String LOG_TAG = "UpdaterService";

	@Override
	public IBinder onBind(Intent arg0) {
		
		Log.d(LOG_TAG, "onBind");
		
		return null;
	}
	
	Twitter twitter;

	@Override
	public void onCreate() {
	
		super.onCreate();
		
		twitter = new Twitter("student","password");
		twitter.setAPIRootUrl("http://yamba.marakana.com/api");
		Log.d(LOG_TAG, "onCreate");
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		List<Status> timeLine = twitter.getPublicTimeline();
		
		for(Status status: timeLine)
		{
			Log.d(LOG_TAG,String.format("%s: %s", status.user.name,status.text));
		}
		
		
		
		
		
		

		Log.d(LOG_TAG, "onStartCommand");
		
		return super.onStartCommand(intent, flags, startId);
		
	}

	@Override
	public void onDestroy() {

		super.onDestroy();
		
		Log.d(LOG_TAG, "onDestroy");
	}


}
