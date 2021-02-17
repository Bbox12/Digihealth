package com.admin.ecosense.Activities.Services;


import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.admin.ecosense.geofence_21.GeofenceReceiver;
import com.admin.ecosense.helper.PrefManager;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SensorService extends Service implements OnCompleteListener<Void> {
    private static final int REQUEST_CHECK_SETTINGS = 1001;
    private  Context mContext;
    public int counter=0;
    private String _PhoneNo;
    private double My_long=0,My_lat=0;
    private static final String TAG = "MyLocationService";
    private LocationManager mLocationManager = null;
    private static final int LOCATION_INTERVAL = 10000;
    private static final float LOCATION_DISTANCE = 1f;
    private  Location mLastLocation;
    private  Location mCurrentLocation;
    private String Tracking_type="";
    private DatabaseReference mDatabase;
    private WindowManager mWindowManager;
    private View mFloatingView;
    public final static int REQUEST_CODE = -1010101;
    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;
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
    private String ID;
    private GeofencingClient mGeofencingClient;

    public SensorService(Context applicationContext) {
        super();
        mContext=applicationContext;
        Log.i("HERE", "here I am!");
    }

    public SensorService() {

    }

    protected void registerGeofences() {
        if (geofencesAlreadyRegistered) {
            return;
        }

        Log.d("Geofence", String.valueOf(lat));
        PrefManager pref = new PrefManager(this);
        if(pref!=null && pref.getHomeLat()!=null) {
        lat=pref.getHomeLat();
        longi=pref.getHomeLong();


           GeofencingRequest.Builder geofencingRequestBuilder = new GeofencingRequest.Builder();
           Geofence g = new Geofence.Builder().setRequestId("demo")
                   .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER
                           | Geofence.GEOFENCE_TRANSITION_EXIT)
                   .setCircularRegion(Double.parseDouble(lat), Double.parseDouble(longi), 200)
                   .setExpirationDuration(60000)
                   .setNotificationResponsiveness(1000)
                   .setLoiteringDelay(5000).build();

         geofencingRequestBuilder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER | GeofencingRequest.INITIAL_TRIGGER_EXIT);
           geofencingRequestBuilder.addGeofence(g);


           GeofencingRequest geofencingRequest = geofencingRequestBuilder.build();

            mPendingIntent = requestPendingIntent();
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
    }

    private PendingIntent requestPendingIntent() {
        if (null != mPendingIntent) {

            return mPendingIntent;
        } else {

            Log.d("mPendingIntent", "mPendingIntent");

            Intent intent = new Intent(this, GeofenceReceiver.class);
            return PendingIntent.getService(this, 0, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

        }
    }

    @Override
    public void onCreate() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        initializeLocationManager();
        if (Build.VERSION.SDK_INT >= 26) {
            String CHANNEL_ID = "Covered19";
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    "Covered19",
                    NotificationManager.IMPORTANCE_DEFAULT);

            ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);

            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("")
                    .setContentText("").build();

            startForeground(1, notification);
        }
        try {
            mLocationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    LOCATION_INTERVAL,
                    LOCATION_DISTANCE,
                    mLocationListeners[0]
            );

        } catch (SecurityException ex) {
            Log.i(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "network provider does not exist, " + ex.getMessage());
        }
        Log.i(TAG, "onCreate");



    }

    private void initializeLocationManager() {
        Log.e(TAG, "initializeLocationManager - LOCATION_INTERVAL: "+ LOCATION_INTERVAL + " LOCATION_DISTANCE: " + LOCATION_DISTANCE);
        if (mLocationManager == null) {
            mLocationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);

        }
    }

    @Override
    public void onComplete(@NonNull Task<Void> task) {
        if (!task.isSuccessful()) {
            geofencesAlreadyRegistered = false;
        }
    }

    private class LocationListener implements android.location.LocationListener {


        public LocationListener(String provider) {
            Log.d(TAG, "LocationListener " + provider);
            mLastLocation = new Location(provider);

        }

        @Override
        public void onLocationChanged(Location location) {
            Log.e(TAG, "onLocationChanged: " + location);
            if(_PhoneNo!=null) {
                mDatabase.child("Registered").child(_PhoneNo).child("Lat").setValue(String.valueOf(location.getLatitude()));
                mDatabase.child("Registered").child(_PhoneNo).child("Long").setValue(String.valueOf(location.getLongitude()));
            }
            if (!geofencesAlreadyRegistered) {
                registerGeofences();
            }
        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.e(TAG, "onProviderDisabled: " + provider);

        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.e(TAG, "onProviderEnabled: " + provider);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.e(TAG, "onStatusChanged: " + provider);
            //registerGeofences();
        }
    }


    LocationListener[] mLocationListeners = new LocationListener[]{
            new LocationListener(LocationManager.PASSIVE_PROVIDER)
    };
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        if(intent!=null) {
            lat = intent.getStringExtra("lat");
            longi = intent.getStringExtra("long");
            ID = intent.getStringExtra("ID");
            _PhoneNo = intent.getStringExtra("phone");
            geofencesAlreadyRegistered=false;
        }
        return START_STICKY;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("EXIT", "ondestroy!");
        Intent broadcastIntent = new Intent("com.admin.ecosense.Activities.Services.SensorService");
        broadcastIntent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        sendBroadcast(broadcastIntent);


    }


    @Override
    public ComponentName startForegroundService(Intent service) {
        return super.startForegroundService(service);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}