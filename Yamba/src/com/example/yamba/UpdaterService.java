package com.example.yamba;

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

	@Override
	public void onCreate() {
	
		super.onCreate();
		
		Log.d(LOG_TAG, "onCreate");
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		Log.d(LOG_TAG, "onStartCommand");
		
		return super.onStartCommand(intent, flags, startId);
		
	}

	@Override
	public void onDestroy() {

		super.onDestroy();
		
		Log.d(LOG_TAG, "onDestroy");
	}


}
