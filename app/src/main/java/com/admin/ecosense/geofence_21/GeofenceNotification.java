package com.admin.ecosense.geofence_21;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.admin.ecosense.FCM.NotificationUtils;
import com.admin.ecosense.R;
import com.admin.ecosense.helper.PrefManager;
import com.google.android.gms.location.Geofence;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GeofenceNotification {
	public static final int NOTIFICATION_ID = 20;

	protected Context context;

	protected NotificationManager notificationManager;
	protected Notification notification;
	private String notificationText;
	private Intent resultIntent;
	private PrefManager pref;
	private DatabaseReference mDatabase;
	private int ID=0;

	public GeofenceNotification(Context context) {
		this.context = context;

		this.notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
	}

	protected void buildNotificaction(
			int transitionType) {

		pref=new PrefManager(context);
		mDatabase = FirebaseDatabase.getInstance().getReference();

		 notificationText = "HOME";
		Object[] notificationTextParams = new Object[] { "HOME"};

		switch (transitionType) {


		case Geofence.GEOFENCE_TRANSITION_ENTER:
				notificationText = String.format(
						context.getString(R.string.geofence_enter),
						notificationTextParams);
			if(pref.getEntered()==1){
				ID=1;
			}else {
				ID=0;
				pref.setEntered(1);
			}
			break;


		case Geofence.GEOFENCE_TRANSITION_EXIT:
				notificationText = String.format(
						context.getString(R.string.geofence_exit),
						notificationTextParams);
				if(pref.getEntered()==2){
					ID=1;
				}else {
					ID=0;
					pref.setEntered(2);
				}


			break;
		}
		Log.d("MainActivity", notificationText);


	//	NotificationUtils notificationUtils = new NotificationUtils(context);
		//notificationUtils.playNotificationSound();
		resultIntent = new Intent(context, MapFragment.class);
		resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);



	}

	public void displayNotification(
			int transitionType) {
		pref= new PrefManager(context);
		if(pref.getMask()==1) {
				buildNotificaction(transitionType);
			if(ID==0) {
				showNotificationMessage(context, "Home", notificationText, "00:00:00", resultIntent);
			}

		}

	}
	private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent) {
		NotificationUtils notificationUtils = new NotificationUtils(context);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
		notificationUtils.showNotificationMessage(title, message, timeStamp, intent);
	}

}
