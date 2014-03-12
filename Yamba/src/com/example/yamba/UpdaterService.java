package com.example.yamba;

import java.util.List;

import winterwell.jtwitter.Twitter.Status;
import winterwell.jtwitter.TwitterException;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class UpdaterService extends Service {

	static final String LOG_TAG = "UpdaterService";

	boolean running = false;

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

		running = true;

		new Thread() {
			public void run() {

				try {

					while (running) {

						((YambaApp) getApplication()).pullAndInsert();

						int delay = Integer
								.parseInt(((YambaApp) getApplication()).prefs
										.getString("delay", "30"));

						Thread.sleep(delay * 1000);
					}
				}

				catch (InterruptedException e) {
					Log.e(LOG_TAG, "Updater interrupted", e);
				}
			}
		}.start();

		Log.d(LOG_TAG, "onStartCommand");
		return super.onStartCommand(intent, flags, startId);

	}

	@Override
	public void onDestroy() {

		super.onDestroy();

		running = false;

		Log.d(LOG_TAG, "onDestroy");
	}

}
