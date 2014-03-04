package com.example.yamba;

import java.util.List;

import winterwell.jtwitter.Twitter;
import winterwell.jtwitter.Twitter.Status;
import winterwell.jtwitter.TwitterException;
import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class RefreshService extends IntentService {

	static final String LOG_TAG = "RefreshService";
	Twitter twitter;

	public RefreshService() {
		super(LOG_TAG);
	}

	@Override
	protected void onHandleIntent(Intent intent) {

		try {
			twitter = new Twitter("student", "password");
			twitter.setAPIRootUrl("http://yamba.marakana.com/api");

			List<Status> timeLine = twitter.getPublicTimeline();

			for (Status status : timeLine) {
				Log.d(LOG_TAG,
						String.format("%s: %s", status.user.name, status.text));
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

		twitter = new Twitter("student", "password");
		twitter.setAPIRootUrl("http://yamba.marakana.com/api");

		Log.d(LOG_TAG, "onCreate");
	}

	@Override
	public void onDestroy() {

		super.onDestroy();

		Log.d(LOG_TAG, "onDestroy");
	}

}
