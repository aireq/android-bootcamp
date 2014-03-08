package com.example.yamba;

import java.util.List;

import winterwell.jtwitter.Twitter.Status;
import winterwell.jtwitter.TwitterException;
import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class RefreshService extends IntentService {

	static final String LOG_TAG = "RefreshService";

	public RefreshService() {
		super(LOG_TAG);
	}

	@Override
	protected void onHandleIntent(Intent intent) {

		try {
		
			List<Status> timeLine = ((YambaApp)getApplication()).getTwitter().getPublicTimeline();

			
			StatusData statusData = ((YambaApp)getApplication()).statusData;
			

			for (Status status : timeLine) {
				Log.d(LOG_TAG,
						String.format("%s: %s", status.user.name, status.text));
				
				statusData.insert(status);
			}
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			Log.e(LOG_TAG, "Failed to accces twitter service", e);
		}

		Log.d(LOG_TAG, "onHandleIntent");

	}

	@Override
	public void onCreate() {

		super.onCreate();

		Log.d(LOG_TAG, "onCreate");
	}

	@Override
	public void onDestroy() {

		super.onDestroy();

		Log.d(LOG_TAG, "onDestroy");
	}

}
