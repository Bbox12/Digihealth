package com.admin.ecosense.geofence_21;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

public class GeofenceReceiver extends IntentService {
	public static final int NOTIFICATION_ID = 1;

	public GeofenceReceiver() {
		super("GeofenceReceiver");
	}

	@Override
	protected void onHandleIntent(Intent intent) {

		GeofencingEvent geoEvent = GeofencingEvent.fromIntent(intent);
		if (geoEvent.hasError()) {
			Log.d("Geofence", "Error GeofenceReceiver.onHandleIntent");
		} else {
			Log.d("Geofence", "GeofenceReceiver : Transition -> "
					+ geoEvent.getGeofenceTransition());

			int transitionType = geoEvent.getGeofenceTransition();

			if (transitionType == Geofence.GEOFENCE_TRANSITION_ENTER
					|| transitionType == Geofence.GEOFENCE_TRANSITION_EXIT) {

						String transitionName = "";
					switch (transitionType) {

					case Geofence.GEOFENCE_TRANSITION_ENTER:
						transitionName = "enter";
						break;

					case Geofence.GEOFENCE_TRANSITION_EXIT:
						transitionName = "exit";
						break;
					}


					GeofenceNotification geofenceNotification = new GeofenceNotification(
							this);
					geofenceNotification
							.displayNotification( transitionType);

			}
		}
	}
}
