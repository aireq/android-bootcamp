package com.example.yamba;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.util.Log;

public class RefreshScheduleReceiver extends BroadcastReceiver {

	
	static PendingIntent lastOp;
	
	@Override
	public void onReceive(Context context, Intent arg1) {
		// context.startService((new Intent(context,UpdaterService.class)));

		// a pending is intent that you are leaving for somebody in the future
		// creating an intent that is going to be sent in the future
		PendingIntent operation = PendingIntent.getService(context, -1,
				new Intent(YambaApp.ACTION_REFRESH),
				PendingIntent.FLAG_UPDATE_CURRENT);

		// gets the alarm manager
		AlarmManager alarmManger = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);

		// cancel existing alarms
		alarmManger.cancel(lastOp);

		// gets the interval preferences
		long interval = Long.parseLong(PreferenceManager
				.getDefaultSharedPreferences(context).getString("delay",
						"900000"));

		//sets up a new alarm if interval is greater then zero
		if (interval > 0) {
			alarmManger.setInexactRepeating(AlarmManager.ELAPSED_REALTIME,
					System.currentTimeMillis(), interval, operation);

		}
		
		lastOp = operation;

		Log.d("BootReciver", "onRecive: delay: " + interval);

	}

}
