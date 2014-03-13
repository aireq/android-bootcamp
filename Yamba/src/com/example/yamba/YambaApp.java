package com.example.yamba;

import java.util.List;

import winterwell.jtwitter.Twitter;
import winterwell.jtwitter.Twitter.Status;
import winterwell.jtwitter.TwitterException;
import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.preference.PreferenceManager;
import android.util.Log;

public class YambaApp extends Application implements
		OnSharedPreferenceChangeListener {

	// this is used to identify the broadcast reciver
	public static final String ACTION_NEW_STATUS = "com.example.yamba.NEW_STATUS";

	public static final String ACTION_REFRESH = "com.example.yamba.RefreshService";
	public static final String ACTION_REFRESH_ALARM = "com.example.yamba.RefreshAlarm";

	static final String TAG = "YambaApp";
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

		Log.d(TAG, "onCreate");
	}

	static final Intent refreshAlarm = new Intent(ACTION_REFRESH_ALARM);

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		twitter = null;
		
		sendBroadcast(refreshAlarm);

		Log.d(TAG, "onSharedPreferenceChanged for key:" + key);
	}

	long lastTimeStampSeen = -1;

	public int pullAndInsert() {

		int newStatsuCount = 0;

		long latestTimeStamp = -1;

		try {
			List<Status> timeLine = getTwitter().getPublicTimeline();
			for (Status status : timeLine) {

				statusData.insert(status);

				if (status.createdAt.getTime() > this.lastTimeStampSeen) {
					newStatsuCount++;

					if (status.createdAt.getTime() > latestTimeStamp) {
						latestTimeStamp = status.createdAt.getTime();
					}

				}

				Log.d(TAG,
						String.format("%s: %s", status.user.name, status.text));

			}

		} catch (TwitterException e) {

			Log.e(TAG, "Faiuled to pull statuses", e);
		}

		// broadcasts that new tweets are received
		if (newStatsuCount > 0) {
			sendBroadcast(new Intent(ACTION_NEW_STATUS).putExtra("count",
					newStatsuCount));
		}

		this.lastTimeStampSeen = latestTimeStamp;

		return newStatsuCount;

	}
}
