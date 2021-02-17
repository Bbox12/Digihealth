package com.admin.ecosense.geofence_21;

import android.Manifest;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.text.format.DateUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.admin.ecosense.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofenceStatusCodes;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class GeolocationService extends Service implements ConnectionCallbacks,
		OnConnectionFailedListener, LocationListener, ResultCallback<Status>, OnCompleteListener<Void> {
	public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 20000;
	public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS /2;
	protected GoogleApiClient mGoogleApiClient;
	protected LocationRequest mLocationRequest;
	public static final int NOTIFICATION_ID = 1;
	private static final long GEOFENCE_EXPIRATION_IN_HOURS = 12;
	public static final long GEOFENCE_EXPIRATION_IN_MILLISECONDS = GEOFENCE_EXPIRATION_IN_HOURS
			* DateUtils.HOUR_IN_MILLIS;
	private PendingIntent mPendingIntent;
	private String lat,longi;
	static public boolean geofencesAlreadyRegistered = false;
	private GeofencingClient mGeofencingClient;

	@Override
	public void onStart(Intent intent, int startId) {
		buildGoogleApiClient();
		mGoogleApiClient.connect();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();


	}

	protected void registerGeofences() {
		if (geofencesAlreadyRegistered) {
			return;
		}

		Log.d("Geofence", "Registering Geofences");


			GeofencingRequest.Builder geofencingRequestBuilder = new GeofencingRequest.Builder();
			Geofence g = new Geofence.Builder().setRequestId("demo")
					.setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER
							| Geofence.GEOFENCE_TRANSITION_DWELL
							| Geofence.GEOFENCE_TRANSITION_EXIT)
					.setCircularRegion(Double.parseDouble(lat), Double.parseDouble(longi), 20)
					.setExpirationDuration(Geofence.NEVER_EXPIRE)
					.setNotificationResponsiveness(1000)
					.setLoiteringDelay(10000).build();

		    geofencingRequestBuilder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
			geofencingRequestBuilder.addGeofence(g);


			GeofencingRequest geofencingRequest = geofencingRequestBuilder.build();

			mPendingIntent = requestPendingIntent();
			if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
					if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
						// TODO: Consider calling
						//    Activity#requestPermissions
						// here to request the missing permissions, and then overriding
						//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
						//                                          int[] grantResults)
						// to handle the case where the user grants the permission. See the documentation
						// for Activity#requestPermissions for more details.
						return;
					}
				}
				mGeofencingClient = LocationServices.getGeofencingClient(this);
				mGeofencingClient.addGeofences(geofencingRequest, mPendingIntent)
						.addOnCompleteListener(this);
			}

			geofencesAlreadyRegistered = true;

	}

	private PendingIntent requestPendingIntent() {

		if (null != mPendingIntent) {

			return mPendingIntent;
		} else {

			Intent intent = new Intent(this, GeofenceReceiver.class);
			return PendingIntent.getService(this, 0, intent,
					PendingIntent.FLAG_UPDATE_CURRENT);

		}
	}

	public void broadcastLocationFound(Location location) {
		Intent intent = new Intent("com.admin.ecosense.geofence_21.geolocation.service");
		intent.putExtra("latitude", location.getLatitude());
		intent.putExtra("longitude", location.getLongitude());
		intent.putExtra("done", 1);
		sendBroadcast(intent);
	}

	protected void startLocationUpdates() {
		if(mGoogleApiClient!=null && mGoogleApiClient.isConnected()) {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
				if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
					// TODO: Consider calling
					//    Activity#requestPermissions
					// here to request the missing permissions, and then overriding
					//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
					//                                          int[] grantResults)
					// to handle the case where the user grants the permission. See the documentation
					// for Activity#requestPermissions for more details.
					return;
				}
			}
			LocationServices.FusedLocationApi.requestLocationUpdates(
					mGoogleApiClient, mLocationRequest, this);
		}
	}

	protected void stopLocationUpdates() {
		LocationServices.FusedLocationApi.removeLocationUpdates(
				mGoogleApiClient, this);
	}

	@Override
	public void onConnected(Bundle connectionHint) {
		Log.i("GeofenceService", "Connected to GoogleApiClient");
		startLocationUpdates();
	}

	@Override
	public void onLocationChanged(Location location) {
		Log.d("GeofenceService",
				"new location : " + location.getLatitude() + ", "
						+ location.getLongitude() + ". "
						+ location.getAccuracy());
		broadcastLocationFound(location);


		if (!geofencesAlreadyRegistered) {
			registerGeofences();
		}
	}

	@Override
	public void onConnectionSuspended(int cause) {
		Log.i("MainActivity", "Connection suspended");
		if(mGoogleApiClient!=null ) {
			mGoogleApiClient.connect();
		}
	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		Log.i("MainActivity",
				"Connection failed: ConnectionResult.getErrorCode() = "
						+ result.getErrorCode());
	}

	protected synchronized void buildGoogleApiClient() {
		Log.i("MainActivity", "Building GoogleApiClient");
		mGoogleApiClient = new GoogleApiClient.Builder(this)
				.addConnectionCallbacks(this)
				.addOnConnectionFailedListener(this)
				.addApi(LocationServices.API).build();
		createLocationRequest();
	}

	protected void createLocationRequest() {
		mLocationRequest = new LocationRequest();
		mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
		mLocationRequest
				.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
		mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	public void onResult(Status status) {
		if (!status.isSuccess()) {

			geofencesAlreadyRegistered = false;
			String errorMessage = getErrorString(this, status.getStatusCode());
			Toast.makeText(getApplicationContext(), errorMessage,
					Toast.LENGTH_LONG).show();
		}
	}

	public static String getErrorString(Context context, int errorCode) {
		Resources mResources = context.getResources();
		switch (errorCode) {
		case GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE:
			return mResources.getString(R.string.geofence_not_available);
		case GeofenceStatusCodes.GEOFENCE_TOO_MANY_GEOFENCES:
			return mResources.getString(R.string.geofence_too_many_geofences);
		case GeofenceStatusCodes.GEOFENCE_TOO_MANY_PENDING_INTENTS:
			return mResources
					.getString(R.string.geofence_too_many_pending_intents);
		default:
			return mResources.getString(R.string.unknown_geofence_error);
		}
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);
		if(intent!=null) {
			 lat = intent.getStringExtra("lat");
			 longi = intent.getStringExtra("long");
			  geofencesAlreadyRegistered=false;
		}
		return START_STICKY;
	}



	@Override
	public void onComplete(@NonNull Task<Void> task) {
		if (!task.isSuccessful()) {
			geofencesAlreadyRegistered = false;
			String errorMessage =  GeofenceErrorMessages.getErrorString(this, task.getException());
			Toast.makeText(getApplicationContext(), errorMessage,
					Toast.LENGTH_LONG).show();
		}
	}
}
