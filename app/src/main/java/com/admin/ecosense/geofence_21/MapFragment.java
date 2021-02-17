package com.admin.ecosense.geofence_21;

import android.Manifest;
import android.app.ActivityManager;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Looper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.admin.ecosense.Activities.MainActivity;
import com.admin.ecosense.BuildConfig;
import com.admin.ecosense.FCM.NotificationUtils;
import com.admin.ecosense.R;
import com.admin.ecosense.helper.Marker_custom_infowindow;
import com.admin.ecosense.helper.PrefManager;
import com.admin.ecosense.helper.modelpatient;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MapFragment extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
		GoogleApiClient.OnConnectionFailedListener, View.OnClickListener, OnMapReadyCallback, OnCompleteListener<Void> {
	protected SupportMapFragment mapFragment;
	protected Marker myPositionMarker;
	private PrefManager pref;
	private static final String TAG = MapFragment.class.getSimpleName();
	private static final int REQUEST_RESOLVE_ERROR = 1001;
	private static final String DIALOG_ERROR = "dialog_error";
	public static final int MULTIPLE_PERMISSIONS = 10;
	private boolean permissionsAllowd=false;
	private static final int REQUEST_CHECK_SETTINGS = 0x1;
	private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
			new LatLng(25.00000, 91.00000), new LatLng(27.99999, 91.99999));
	private static final float POLYLINE_WIDTH = 8;
	private GoogleApiClient mGoogleApiClient;
	private FusedLocationProviderClient mFusedLocationClient;
	private LocationRequest mLocationRequest;
	private PendingResult<LocationSettingsResult> result;
	private LocationSettingsRequest.Builder builder;
	private boolean mResolvingError = false;
	private MyCountDownTimer myCountDownTimer;
	private AppCompatCheckBox _yesHome,_noHome;

	private boolean first=false;
	private boolean drag;
	private boolean second=false;
	private int _from=0;
	private Marker[] markers = new Marker[2000];

	private ImageView mySOS,myLocationButton;
	private Toolbar toolbar;
	private double My_lat=0,My_long=0;
	private GoogleMap mgoogleMap;
	private double latitude,longitude;
	private Marker markerCar;
	private DatabaseReference mDatabase;
	private TextView txtName, txtName1;
	private RecyclerView lv;
	private CoordinatorLayout coordinatorLayout;
	private EditText mAutocompleteView;
	private ImageView centermarker;
	private LinearLayout layoutBottomSheet;
	BottomSheetBehavior sheetBehavior;
	private LinearLayout Lhome,Lprogress;
	private int _patient=0;
	private String _PhoneNo;
	private boolean _tpermission=false;
	private boolean third=false;
	private Button familymebers;
	private String _phone;
	private boolean _first=false;
	private FirebaseDataListenerFamily _Family;
	private LocationCallback mLocationCallback;
	private Intent resultIntent;
	private String _Name;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mDatabase = FirebaseDatabase.getInstance().getReference();
		setContentView(R.layout.fragment_map);
		pref=new PrefManager(getApplicationContext());
		HashMap<String, String> user = pref.getUserDetails();
		_PhoneNo = user.get(PrefManager.KEY_MOBILE);
		_Name = user.get(PrefManager.KEY_NAME);
		mySOS = findViewById(R.id.mySOS);
		centermarker = findViewById(R.id.centermarker);
		toolbar = findViewById(R.id.toolbardd);
		coordinatorLayout=findViewById(R.id.cor_home_main);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayShowTitleEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		myLocationButton = findViewById(R.id.myLocationCustomButton);
		mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
		mapFragment.getMapAsync(MapFragment.this);
		mySOS.setOnClickListener(this);
		myLocationButton.setOnClickListener(this);
		familymebers=findViewById(R.id.familymebers);
		familymebers.setOnClickListener(this);


		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(_from!=5) {
					finish();
					overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);

				}


			}
		});


		Intent i=getIntent();
		_from=i.getIntExtra("from",0);
		_phone=i.getStringExtra("_phone");





		mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
		createLocationRequest();
		buildLocationSettingsRequest();
		rebuildGoogleApiClient();
		if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
			onConnected(null);
		}


		layoutBottomSheet = findViewById(R.id.bottom_sheet_explore);
		sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);
		sheetBehavior.setFitToContents(true);
		sheetBehavior.setHideable(true);



		_yesHome=findViewById(R.id.yeshome);
		_noHome=findViewById(R.id.nohome);
        Lhome=findViewById(R.id.lhome);
        Lprogress=findViewById(R.id.lprogress);





	}


	private class FirebaseDataListenerFamily implements ValueEventListener {

		private int _vnear=0;

		@Override
		public void onDataChange(DataSnapshot dataSnapshot) {


			long count = dataSnapshot.getChildrenCount();
			Log.e(TAG, "FirebaseDataListenerFamily: " + String.valueOf(_patient));
				if (count != 0 && _patient == 0) {
					modelpatient item = dataSnapshot.getValue(modelpatient.class);
					if (item.getLat()!= null && item.getLong() != null && item.getName() != null && item.getTracking()!=null) {

						CircleOptions circleOptions1 = new CircleOptions()
								.center(new LatLng(Double.parseDouble(item.getLat()), Double.parseDouble(item.getLong())))
								.radius(1000)
								.strokeColor(Color.BLACK)
								.strokeWidth(2).fillColor(Color.RED);
						mgoogleMap.addCircle(circleOptions1);

						CameraPosition googlePlex;
						googlePlex = CameraPosition.builder()
								.target(new LatLng(Double.parseDouble(item.getLat()), Double.parseDouble(item.getLong())))
								.zoom(18)// Sets the zoom
								.build(); // Crea
						mgoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(googlePlex));

						markerCar = mgoogleMap.addMarker(new MarkerOptions()
								.position(new LatLng(Double.parseDouble(item.getLat()), Double.parseDouble(item.getLong())))
								.icon(BitmapDescriptorFactory.fromResource(R.drawable.homemarker)));
						markerCar.setTitle(item.getName());
						markerCar.showInfoWindow();
						markerCar.setAnchor(0.5f, 0.5f);
						centermarker.setVisibility(View.GONE);


					}else{
						
						Toast.makeText(getApplicationContext(),"You need to create request again!",Toast.LENGTH_SHORT).show();
					}




			}
		}

		@Override
		public void onCancelled(DatabaseError databaseError) {

		}
	}

	private class FirebaseDataListenerPatient implements ValueEventListener {

		private int _vnear=0;

		@Override
		public void onDataChange(DataSnapshot dataSnapshot) {


			long count = dataSnapshot.getChildrenCount();
			Log.e(TAG, "FirebaseDataListenerPatient: " + String.valueOf(count));
			if(count!=0 && _patient==0 ) {

				for(DataSnapshot userSnapshot : dataSnapshot.getChildren()){
					String k=userSnapshot.getKey();
					modelpatient item=userSnapshot.getValue(modelpatient.class);
					if (item.getLat() != null && item.getLong()!= null ) {


						double dist = com.google.maps.android.SphericalUtil.computeDistanceBetween(new LatLng(My_lat, My_long), new LatLng(Double.parseDouble(item.getLat()), Double.parseDouble(item.getLong()))) / 1000;
						  if( !String.valueOf(_PhoneNo).contains(k)){
							  if (dist<pref.getDistance()) {
								  _vnear=+1;
								  _patient+=1;
							  }
							if (dist < 8 ) {
								CircleOptions circleOptions1 = new CircleOptions()
										.center(new LatLng(Double.parseDouble(item.getLat()), Double.parseDouble(item.getLong())))
										.radius(100)
										.strokeColor(Color.BLACK)
										.strokeWidth(2).fillColor(Color.RED);
								mgoogleMap.addCircle(circleOptions1);
							}
							}

					}
				}
				if(_from==4) {
					_from=0;
					if (_vnear != 0) {
						showNotificationMessage(MapFragment.this, "Be Alert", "You are at or within " + String.valueOf(pref.getDistance()) + " Km of a hotspot! Please wear mask", "00:00:00", resultIntent);
					} else {
						showNotificationMessage(MapFragment.this, "No hotspot found", "We have not found any hotspot near you.", "00:00:00", resultIntent);
					}

				}
			}

		}

		@Override
		public void onCancelled(DatabaseError databaseError) {

		}
	}

	private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent) {
		NotificationUtils notificationUtils = new NotificationUtils(context);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
		notificationUtils.showNotificationMessage(title, message, timeStamp, intent);
	}

	private boolean isMyServiceRunning(Class<?> serviceClass) {
		ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		if (manager != null) {
			for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
				if (serviceClass.getName().equals(service.service.getClassName())) {
					Log.i ("isMyServiceRunning?", true+"");
					return true;
				}
			}
		}
		Log.i ("isMyServiceRunning?", false+"");
		return false;
	}

	private void deleteAll() {
		pref.setHomeLat(null);
		pref.setHomeLong(null);
		mgoogleMap.clear();
		_noHome.setChecked(false);
		first=false;
		centermarker.setVisibility(View.VISIBLE);
		Snackbar snackbar = Snackbar
				.make(coordinatorLayout, "No alarm choosen! Please choose one.", Snackbar.LENGTH_LONG)
				.setAction("Ok", new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
						layoutBottomSheet.setVisibility(View.VISIBLE);
						_yesHome.setChecked(false);
						_noHome.setChecked(false);
					}
				});
		snackbar.setActionTextColor(Color.RED);
		snackbar.show();
	}

	private void populateMap() {
		Log.e(TAG, "updateLocationUI: " + String.valueOf(123));
		if(mgoogleMap!=null && pref.getHomeLat()!=null && _from!=5) {
			centermarker.setVisibility(View.GONE);
			mgoogleMap.clear();
			LatLng east = new LatLng(Double.parseDouble(pref.getHomeLat()), Double.parseDouble(pref.getHomeLong())); // Shift 500 meters to the east
			CameraPosition googlePlex;
			googlePlex = CameraPosition.builder()
					.target(east)
					.zoom(18)// Sets the zoom
					.build(); // Crea
			mgoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex));
			CircleOptions circleOptions1 = new CircleOptions()
					.center(east)
					.radius(200)
					.strokeColor(Color.RED)
					.strokeWidth(2).fillColor(R.color.blue);
			mgoogleMap.addCircle(circleOptions1);

				markerCar = mgoogleMap.addMarker(new MarkerOptions()
						.position(east)
						.icon(BitmapDescriptorFactory.fromResource(R.drawable.homemarker)));
				markerCar.setTitle("Home");
				markerCar.showInfoWindow();
				markerCar.setAnchor(0.5f, 0.5f);

			markerCar.setVisible(true);
		}
	}





    public void CustomNotification(String title,String durationo) {
			Uri alarmSound = Uri.parse("android.resource://"
					+ MapFragment.this.getPackageName() + "/" + R.raw.goodmorning);


		NotificationCompat.Builder mBuilder =
					new NotificationCompat.Builder(getApplicationContext(), "notify_001");
			PendingIntent pendingIntent;
				Intent ii = new Intent(getApplicationContext(), MapFragment.class);
				pendingIntent = PendingIntent.getActivity(MapFragment.this, 0, ii, 0);


			NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
			bigText.setBigContentTitle(title);
			bigText.setSummaryText(title);

			mBuilder.setContentIntent(pendingIntent);
			mBuilder.setSmallIcon(R.mipmap.ic_launcher);
			mBuilder.setContentTitle(title);
			mBuilder.setContentText(durationo);
			mBuilder.setPriority(Notification.PRIORITY_MAX);
			mBuilder.setSound(alarmSound);
			mBuilder.setStyle(bigText);

			NotificationManager mNotificationManager = (NotificationManager) MapFragment.this.getSystemService(Context.NOTIFICATION_SERVICE);

			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
				String channelId = "notify_001";
				NotificationChannel channel = new NotificationChannel(
						channelId,
						durationo,
						NotificationManager.IMPORTANCE_HIGH);

				if (mNotificationManager != null) {
					mNotificationManager.createNotificationChannel(channel);
				}

				mBuilder.setChannelId(channelId);

			}
			if (mNotificationManager != null) {
				mNotificationManager.notify(0, mBuilder.build());
			}
		}



	private void buildLocationSettingsRequest() {
		LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
		builder.addLocationRequest(mLocationRequest);

	}

	protected synchronized void rebuildGoogleApiClient() {
		mGoogleApiClient = new GoogleApiClient.Builder(this)
				.enableAutoManage(this, 0, this)
				.addConnectionCallbacks(this /* ConnectionCallbacks */)
				.addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
					@Override
					public void onConnectionFailed(ConnectionResult connectionResult) {
						googleApiClientConnectionStateChange(true);
					}
				})
				.addApi(LocationServices.API)
				.build();

	}

	private void googleApiClientConnectionStateChange(final boolean connected) {
		final Context appContext = this.getApplicationContext();



	}





	@Override
	public void onClick(View view) {
		switch (view.getId()){
			case R.id.myLocationCustomButton:
				if (My_lat != 0) {
					CameraPosition googlePlex;
					googlePlex = CameraPosition.builder()
							.target(new LatLng(My_lat, My_long))
							.zoom(18)// Sets the zoom
							.build(); // Crea
					mgoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex));
					centermarker.setVisibility(View.VISIBLE);
				}
				break;

			case R.id.familymebers:
				pref.setHomeLat(null);
				pref.setHomeLong(null);
				centermarker.setVisibility(View.VISIBLE);
				_yesHome.setChecked(false);
				if(mgoogleMap!=null){
					mgoogleMap.clear();
				}
				Lprogress.setVisibility(View.GONE);
				Lhome.setVisibility(View.VISIBLE);
				break;
		}

	}

	@Override
	public void onMapReady(GoogleMap googleMap) {
		if (googleMap == null) {
			if (!MapFragment.this.isFinishing()) {
				new androidx.appcompat.app.AlertDialog.Builder(MapFragment.this, R.style.AlertDialogTheme)
						.setIcon(R.mipmap.ic_launcher)
						.setTitle("Could not load Google Map")
						.setMessage("May be poor network!Contact customer care for booking offline")
						.setPositiveButton("OK", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.cancel();
							}
						})
						.show();
			}
		} else {
			mgoogleMap = googleMap;

			Marker_custom_infowindow adapter = new Marker_custom_infowindow(MapFragment.this);
			googleMap.setInfoWindowAdapter(adapter);
			mgoogleMap.getUiSettings().setZoomControlsEnabled(true);
			mgoogleMap.getUiSettings().setAllGesturesEnabled(true);
			mgoogleMap.getUiSettings().setMyLocationButtonEnabled(true);

			if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
				// TODO: Consider calling
				//    ActivityCompat#requestPermissions
				// here to request the missing permissions, and then overriding
				//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
				//                                          int[] grantResults)
				// to handle the case where the user grants the permission. See the documentation
				// for ActivityCompat#requestPermissions for more details.
				return;
			}
			mgoogleMap.setMyLocationEnabled(true);
		
			mgoogleMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
				@Override
				public void onCameraIdle() {
                    if(drag){
                    	//first=true;
						LatLng midLatLng = mgoogleMap.getCameraPosition().target;
						My_lat=midLatLng.latitude;
						My_long=midLatLng.longitude;
						if(_from!=3 && _from!=4 && _from!=5 ) {
							sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
							if (pref.getMask() == 0) {
								Lhome.setVisibility(View.VISIBLE);
								Lprogress.setVisibility(View.GONE);
							} else {
							Lhome.setVisibility(View.GONE);
							Lprogress.setVisibility(View.VISIBLE);
							}
						}


					}


				}


			});
			mgoogleMap.setOnCameraMoveStartedListener(new GoogleMap.OnCameraMoveStartedListener() {
				@Override
				public void onCameraMoveStarted(int reason) {
						if (reason == REASON_GESTURE) {
							drag = true;
							if(_from!=3 && _from!=4 ) {
								if (pref.getMask() == 0 ) {
									if (sheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
										sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
									}
								}
							}
						}

				}
			});


			if(pref.getMask()==0 ){
				mgoogleMap.clear();
			}


		}

	}
	@Override
	public void onResume() {
		super.onResume();
      startLocationUpdat();
		if(_from==5){
			if(_phone!=null) {
				pref.setFamilyPhone(null);
				_Family=new FirebaseDataListenerFamily();
				mDatabase.child("Registered").child(_phone).addListenerForSingleValueEvent(_Family);
			}
		}else {
			if(_from==3 || _from==4){
				sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
				layoutBottomSheet.setVisibility(View.GONE);

			}else{
				sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
				layoutBottomSheet.setVisibility(View.VISIBLE);


			}
		}
	}




	private boolean checkAndRequestPermissions() {
		int permissionLocation = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
		List<String> listPermissionsNeeded = new ArrayList<>();
		if (permissionLocation != PackageManager.PERMISSION_GRANTED) {
			listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
		}

		if (!listPermissionsNeeded.isEmpty()) {
			ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[0]), MULTIPLE_PERMISSIONS);

		} else {
			permissionsAllowd=true;
		}
		return permissionsAllowd;
	}

	@Override
	public void onRequestPermissionsResult(int requestCode,
										   String permissions[], int[] grantResults) {
		Log.d(TAG, "Permission callback called-------");
		if (requestCode == MULTIPLE_PERMISSIONS) {
			Map<String, Integer> perms = new HashMap<>();
			perms.put(Manifest.permission.ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);
			if (grantResults.length > 0) {
				for (int i = 0; i < permissions.length; i++)
					perms.put(permissions[i], grantResults[i]);
				// Check for both permissions
				if (perms.get(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
				) {
					Log.d(TAG, "sms & location services permission granted");
					startLocationUpdat();

				} else {
					Log.d(TAG, "Some permissions are not granted ask again ");
					permissionsAllowd = false;
					explain("You need to give some mandatory permissions to continue");

				}
			}
		}


	}

	private void explain(String msg) {
		if(!MapFragment.this.isFinishing() && !_tpermission) {
			_tpermission=true;
			Snackbar snackbar = Snackbar
					.make(coordinatorLayout, msg, Snackbar.LENGTH_INDEFINITE)
					.setAction("OK", new View.OnClickListener() {
						@Override
						public void onClick(View view) {
							startActivity(new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + BuildConfig.APPLICATION_ID)));
						}
					});
			snackbar.setActionTextColor(Color.RED);
			snackbar.show(); }



	}


	@Override
	public void onConnected(Bundle bundle) {
		builder = new LocationSettingsRequest.Builder().addLocationRequest(createLocationRequest());
		Task<LocationSettingsResponse> task = LocationServices.getSettingsClient(this).checkLocationSettings(builder.build());
		task.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
			@Override
			public void onComplete(@NonNull Task<LocationSettingsResponse> task) {
				try {
					LocationSettingsResponse response = task.getResult(ApiException.class);
					// All location settings are satisfied. The client can initialize location
					// requests here.
					startLocationUpdat();
				} catch (ApiException exception) {
					switch (exception.getStatusCode()) {
						case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
							// Location settings are not satisfied. But could be fixed by showing the
							// user a dialog.
							try {
								// Cast to a resolvable exception.
								ResolvableApiException resolvable = (ResolvableApiException) exception;
								// Show the dialog by calling startResolutionForResult(),
								// and check the result in onActivityResult().
								resolvable.startResolutionForResult(MapFragment.this, REQUEST_CHECK_SETTINGS);
								break;
							} catch (IntentSender.SendIntentException e) {
								// Ignore the error.
							} catch (ClassCastException e) {
								// Ignore, should be an impossible error.
								Log.w(" ClassCastException", "Canont get Address!" + e.getLocalizedMessage());
							}
							break;
						case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
							// Location settings are not satisfied. However, we have no way to fix the
							// settings so we won't show the dialog.

							break;
					}
				}
			}
		});
	}

	@Override
	public void onConnectionSuspended(int cause) {
		// Indicate API calls to Google Play services APIs should be halted.
		googleApiClientConnectionStateChange(false);
		mGoogleApiClient.connect();
	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		if (mResolvingError) {

		} else if (result.hasResolution()) {
			try {
				mResolvingError = true;
				result.startResolutionForResult(this, REQUEST_RESOLVE_ERROR);
			} catch (IntentSender.SendIntentException e) {
				// There was an error with the resolution intent. Try again.
				mGoogleApiClient.connect();
			}
		} else {
			// Show dialog using GoogleApiAvailability.getErrorDialog()
			showErrorDialog(result.getErrorCode());
			mResolvingError = true;
		}
	}

	private void showErrorDialog(int errorCode) {
		// Create a fragment for the error dialog
		ErrorDialogFragment dialogFragment = new ErrorDialogFragment();
		// Pass the error that should be displayed
		Bundle args = new Bundle();
		args.putInt(DIALOG_ERROR, errorCode);
		dialogFragment.setArguments(args);
		dialogFragment.show(getSupportFragmentManager(), "errordialog");
	}

	public static class ErrorDialogFragment extends DialogFragment {
		public ErrorDialogFragment() {
		}

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Get the error code and retrieve the appropriate dialog
			int errorCode = 0;
			if (this.getArguments() != null) {
				errorCode = this.getArguments().getInt(DIALOG_ERROR);
			}
			return GoogleApiAvailability.getInstance().getErrorDialog(
					this.getActivity(), errorCode, REQUEST_RESOLVE_ERROR);
		}

		@Override
		public void onDismiss(DialogInterface dialog) {
			dialog.cancel();
		}
	}


	public void onDialogDismissed() {
		mResolvingError = false;
	}



	protected LocationRequest createLocationRequest() {
		mLocationRequest = new LocationRequest();
		mLocationRequest.setInterval(2000);
		mLocationRequest.setFastestInterval(2000);
		mLocationRequest.setSmallestDisplacement(0.1f);
		mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
		return mLocationRequest;
	}


	private void startLocationUpdat() {
		if (mGoogleApiClient != null) {
			mGoogleApiClient.connect();
			if (checkAndRequestPermissions()) {


				if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
					return;
				}
				if (mFusedLocationClient != null) {
					mLocationCallback=new LocationCallback() {
						@Override
						public void onLocationResult(LocationResult locationResult) {
							List<Location> locationList = locationResult.getLocations();
							if (locationList.size() > 0) {
								Location location = locationList.get(locationList.size() - 1);
								Log.i("MapsActivity", "Location: " + String.valueOf(location));
								updateLocationUI(location);
							}
						}
					};
					Looper myLoop = Looper.myLooper();
					mFusedLocationClient.requestLocationUpdates(createLocationRequest(),mLocationCallback ,
							myLoop);

				}
			}

		}
	}


	private void updateLocationUI(Location location) {
        if(!drag) {
			My_lat = location.getLatitude();
			My_long = location.getLongitude();
				if(pref.getFirstQuestion()!=0) {
				mDatabase.child("Patient").child(_PhoneNo).child("Lat").setValue(String.valueOf(My_lat));
				mDatabase.child("Patient").child(_PhoneNo).child("Long").setValue(String.valueOf(My_long));
			}
		}


		if(!first ){
			resultIntent = new Intent(this, MapFragment.class);
			resultIntent.putExtra("from",4);
			resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			CameraPosition googlePlex;
			googlePlex = CameraPosition.builder()
					.target(new LatLng(My_lat,My_long))
					.zoom(18)// Sets the zoom
					.build(); // Crea
			mgoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex));
			mDatabase.child("Patient").addListenerForSingleValueEvent(new FirebaseDataListenerPatient());
		}

	}





	@Override
	public void onComplete(@NonNull Task<Void> task) {
		Log.d(TAG, "tasksuccess-------");
	}


	public void getAddress(double lat, double lng) {
		Geocoder geocoder = new Geocoder(MapFragment.this, Locale.getDefault());
		try {
			List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
			Address obj = addresses.get(0);
			String add = obj.getCountryName();
			Log.v("IGA", "Address" + add);
			if(add!=null){
				pref.setCountry(add);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			first=false;
		}
	}
	public class MyCountDownTimer extends CountDownTimer {


		public MyCountDownTimer(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}


		@Override
		public void onTick(long millisUntilFinished) {


		}

		@Override
		public void onFinish() {
			_patient=0;
			mDatabase.child("Patient").addListenerForSingleValueEvent(new FirebaseDataListenerPatient());
		}
	}


	@Override
	protected void onPause() {
		super.onPause();
		Log.e(TAG, "pause: " + String.valueOf(_patient));
		if (mFusedLocationClient != null && mLocationCallback!=null) {
			mFusedLocationClient.removeLocationUpdates(mLocationCallback);
		}
	}


	@Override
	protected void onStop() {

		super.onStop();
		Log.e(TAG, "Stop: " + String.valueOf(_patient));
		if (mFusedLocationClient != null && mLocationCallback!=null) {
			mFusedLocationClient.removeLocationUpdates(mLocationCallback);
		}
	}



	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mFusedLocationClient != null && mLocationCallback!=null) {
			mFusedLocationClient.removeLocationUpdates(mLocationCallback);
		}
	}



	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		//replaces the default 'Back' button action
		if(keyCode== KeyEvent.KEYCODE_BACK)   {

			if(_from!=5) {
				finish();
				overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
			}

		}
		return true;
	}

}
