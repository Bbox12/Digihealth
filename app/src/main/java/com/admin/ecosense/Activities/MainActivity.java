package com.admin.ecosense.Activities;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.UiModeManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.NotificationCompat;
import androidx.core.widget.NestedScrollView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.admin.ecosense.Adapters._CommodityAdapter;
import com.admin.ecosense.Doctors.Doctorwindow;
import com.admin.ecosense.Doctors.PatientDetails;
import com.admin.ecosense.Doctors.StartSession;
import com.admin.ecosense.FCM.NotificationUtils;
import com.admin.ecosense.Login.ServiceOffer;
import com.admin.ecosense.POD.Addpatient;
import com.admin.ecosense.POD.MonitorFamilyPOD;
import com.admin.ecosense.Questions.Consultation;
import com.admin.ecosense.Questions.GetMyStatus;
import com.admin.ecosense.Questions.Questions;
import com.admin.ecosense.R;
import com.admin.ecosense.RequestTest.MedicalTest;
import com.admin.ecosense.RequestTest.RequestTest;
import com.admin.ecosense.RequestTest.SickNote;
import com.admin.ecosense.geofence_21.MapFragment;
import com.admin.ecosense.helper.AppController;
import com.admin.ecosense.helper.Config_URL;
import com.admin.ecosense.helper.Login;
import com.admin.ecosense.helper.PrefManager;
import com.admin.ecosense.helper.Qns;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int REQUEST_CAMERA = 1001;
    private LinearLayout handsswitch,mystatus,submitarequest,general;
    private PrefManager pref;
    private CoordinatorLayout coordinatorLayout;
    private double Mylat,Mylong;
    private TextView noofpatient,patientdetails;
    private LinearLayout familymebers;
    private DatabaseReference mDatabase;
    private CardView _n5,n8,videos,questions;
    private Toolbar toolbar;
    private int position=0;
    private RecyclerView _only,Sosrv,_brands,_brands2;
    private TextView smtext,noofpodpatient;
    private String _PhoneNo;
    private RelativeLayout _R1;
    private PendingIntent pendingIntent;
    private static final int ALARM_REQUEST_CODE = 10009;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private boolean _played;
    private String _familyPhone;
    private int curent,nStart=5,nEnd=250;
    private boolean _frst=false;
    private Animation animBlink;
    private ImageView _blinks;
    private WebView _webview;
    private LinearLayout sessionhistory,posthealthdata;
    private MyCountDownTimer myCountDownTimer;
    private LinearLayout Lalarm;
    private ProgressBar alarmProgress;
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;
    private int op=0;
    private LinearLayout MonitorFamily;
    private TextView addmember;
    private LinearLayout PODOWNER;
    private WebView _webview2,_webview3;
    private AppCompatCheckBox R1,R2,R3,R4;
    private LinearLayout radiosbutt,treatment;


    public class MyCountDownTimer extends CountDownTimer {


        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }


        @Override
        public void onTick(long millisUntilFinished) {

            op+=1000;
            alarmProgress.setProgress((int) op  / (12000));
        }

        @Override
        public void onFinish() {
            CustomNotification("Alert","Please post  health data");
            alarmProgress.setProgress(0);
            myCountDownTimer = new MyCountDownTimer(3*60*60000, 1000);
            myCountDownTimer.start();
        }
    }



    public void CustomNotification(String title,String durationo) {
        Uri alarmSound = Uri.parse("android.resource://"
                + MainActivity.this.getPackageName() + "/" + R.raw.goodmorning);


        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(getApplicationContext(), "notify_001");
        PendingIntent pendingIntent;
        Intent ii = new Intent(getApplicationContext(), SickNote.class);
        ii.putExtra("from",1);
        pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, ii, 0);


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

        NotificationManager mNotificationManager = (NotificationManager) MainActivity.this.getSystemService(Context.NOTIFICATION_SERVICE);

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        setContentView(R.layout.mainlayout);
        handsswitch=findViewById(R.id._n1);
        handsswitch.setOnClickListener(this);
        noofpodpatient=findViewById(R.id.noofpodpatient);
        general=findViewById(R.id._n2);
        general.setOnClickListener(this);
        pref=new PrefManager(getApplicationContext());
        HashMap<String, String> user = pref.getUserDetails();
        _PhoneNo = user.get(PrefManager.KEY_MOBILE);
        coordinatorLayout=findViewById(R.id.cor_home_main);
        noofpatient=findViewById(R.id.noofpatient);
        noofpatient.setOnClickListener(this);
        videos=findViewById(R.id.videos);
        videos.setOnClickListener(this);
        _n5=findViewById(R.id._n5);
        _n5.setOnClickListener(this);
        n8=findViewById(R.id.n8);
        n8.setOnClickListener(this);
        toolbar =findViewById(R.id.toolbardd);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        toolbar.setTitle(getResources().getString(R.string.app_name));
        _only = findViewById(R.id.onlyservice);
        _only.setNestedScrollingEnabled(false);
        smtext=findViewById(R.id.smtext);
        smtext.setVisibility(View.GONE);
        familymebers=findViewById(R.id.familymebers);
    //    familymebers.setOnClickListener(this);
        submitarequest=findViewById(R.id.submitarequest);
        mystatus=findViewById(R.id.mystatus);
        submitarequest.setOnClickListener(this);
        mystatus.setOnClickListener(this);
        questions=findViewById(R.id.questions);
        questions.setOnClickListener(this);
        posthealthdata=findViewById(R.id.posthealthdata);
     //   posthealthdata.setOnClickListener(this);

        treatment=findViewById(R.id.treatment);
        treatment.setOnClickListener(this);

        PODOWNER=findViewById(R.id.PODOWNER);


        MonitorFamily=findViewById(R.id.MonitorFamily);
        MonitorFamily.setOnClickListener(this);

        addmember=findViewById(R.id.addmember);
        addmember.setOnClickListener(this);


        Lalarm=findViewById(R.id.progressalarm);
        alarmProgress=findViewById(R.id.progressBarAlarm);

        if(pref.getDigiCategory()==4){
            Lalarm.setVisibility(View.VISIBLE);
            if(myCountDownTimer==null) {
                myCountDownTimer = new MyCountDownTimer(3 * 60 * 60000, 1000);
                myCountDownTimer.start();
            }
        }
        if(pref.getResponsibility()==3){
            PODOWNER.setVisibility(View.VISIBLE);
        }else{
            PODOWNER.setVisibility(View.GONE);
        }
        radiosbutt=findViewById(R.id.radios);
        R1=findViewById(R.id.r1);
        R2=findViewById(R.id.r2);
        R3=findViewById(R.id.r3);
        R4=findViewById(R.id.r4);
        if(pref.getResponsibility()==1){
            radiosbutt.setVisibility(View.VISIBLE);
        }else{
            radiosbutt.setVisibility(View.GONE);
        }


        if(pref.getConsultation()!=0){
            R1.setChecked(true);
        }
        if(pref.getSymptoms()!=0){
            R2.setChecked(true);
        }
        if(pref.getOxygen()!=0){
            R3.setChecked(true);
        }

        if(pref.getMedical()!=0){
            R4.setChecked(true);
        }else{
            R4.setClickable(true);
        }


        R4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                R4.setChecked(true);
                Intent eme = new Intent(MainActivity.this, MedicalTest.class);
                eme.putExtra("from",1);
                startActivity(eme);
                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
            }
        });




        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(Config_URL.PUSH_NOTIFICATION)) {
                       String title=intent.getStringExtra("title");
                    String message=intent.getStringExtra("message");
                    CustomNotification(title,message);
                }
            }
        };
        sessionhistory=findViewById(R.id.sessionhistory);



        _webview = findViewById(R.id.webView);
        _webview.setBackgroundColor(getResources().getColor(R.color.blue)); //for gif without background
        _webview.loadUrl("file:///android_asset/call.gif");
        _webview.getSettings().setLoadsImagesAutomatically(true);
        _webview.getSettings().setLoadWithOverviewMode(true);
        _webview.getSettings().setUseWideViewPort(true);

        _webview2= findViewById(R.id.webView2);
        _webview2.setBackgroundColor(getResources().getColor(R.color.blue)); //for gif without background
        _webview2.loadUrl("file:///android_asset/clock.gif");
        _webview2.getSettings().setLoadsImagesAutomatically(true);
        _webview2.getSettings().setLoadWithOverviewMode(true);
        _webview2.getSettings().setUseWideViewPort(true);

        _webview3= findViewById(R.id.webView3);
        _webview3.setBackgroundColor(getResources().getColor(R.color.blue)); //for gif without background
        _webview3.loadUrl("file:///android_asset/alarm.gif");
        _webview3.getSettings().setLoadsImagesAutomatically(true);
        _webview3.getSettings().setLoadWithOverviewMode(true);
        _webview3.getSettings().setUseWideViewPort(true);




        _webview3.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        if (_PhoneNo != null) {
                            if(!MainActivity.this.isFinishing()) {
                                if(pref.getAlarm()==0 || pref.getAlarm()==2) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.AlertDialogTheme)
                                            .setIcon(R.mipmap.ic_launcher)
                                            .setTitle("Post health data after 3 Hours")
                                            .setMessage("An alarm will be set to post your health data after every 3 Hours")
                                            .setCancelable(true)
                                            .setPositiveButton("Set alarm", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    pref.setAlarm(1);
                                                    Intent sick = new Intent(MainActivity.this, SickNote.class);
                                                    sick.putExtra("from",1);
                                                    startActivity(sick);
                                                    overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);

                                                    dialog.cancel();
                                                }
                                            })
                                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.cancel();
                                                }
                                            });
                                    builder.show();
                                }else{
                                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.AlertDialogTheme)
                                            .setIcon(R.mipmap.ic_launcher)
                                            .setTitle("Alarm has been set ")
                                            .setMessage("An alarm has been set to post your health data after every 3 Hours! Cancel alarm?")
                                            .setCancelable(true)
                                            .setPositiveButton("No", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.cancel();
                                                }
                                            })
                                            .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    pref.setAlarm(0);
                                                    if(myCountDownTimer!=null){
                                                        myCountDownTimer.cancel();
                                                    }
                                                    dialog.cancel();
                                                }
                                            });
                                    builder.show();
                                }
                            }
                        }
                        break;
                }
                return false;
            }
        });



        _webview2.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        if (_PhoneNo == null) {
                            Intent eme = new Intent(MainActivity.this, ServiceOffer.class);
                            startActivity(eme);
                            overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);


                        } else {
                            Intent history = new Intent(MainActivity.this, StartSession.class);
                            startActivity(history);
                            overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);

                        }
                        break;
                }
                return false;
            }
        });


        _webview.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        if (_PhoneNo == null) {
                            Intent eme = new Intent(MainActivity.this, ServiceOffer.class);
                            startActivity(eme);
                            overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);


                        } else {
                            if (!MainActivity.this.isFinishing()) {
                                if(pref.getWashHands()!=0) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.AlertDialogTheme)
                                            .setIcon(R.mipmap.ic_launcher)
                                            .setTitle("You have an session still in progress.")
                                            .setMessage("Please check session .")
                                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Intent eme = new Intent(MainActivity.this, StartSession.class);
                                                    startActivity(eme);
                                                    overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                                                    dialog.cancel();
                                                }
                                            })
                                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.cancel();
                                                }
                                            });
                                    builder.show();
                                }else{

                                        Intent eme = new Intent(MainActivity.this, Questions.class);
                                        eme.putExtra("from",1);
                                        startActivity(eme);
                                        overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);

                                }
                            }

                        }
                        break;
                }
                return false;
            }
        });



        if(pref.getResponsibility()==2){
            Intent eme = new Intent(MainActivity.this, Doctorwindow.class);
            startActivity(eme);
            overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);

        }else if(pref.getResponsibility()==1){

            if(!MainActivity.this.isFinishing()) {
                if (pref.getAlarm() == 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.AlertDialogTheme)
                            .setIcon(R.mipmap.ic_launcher)
                            .setTitle("Post health data after 3 Hours")
                            .setMessage("An alarm will be set to post your health data after every 3 Hours")
                            .setCancelable(true)
                            .setPositiveButton("Set alarm", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent sick = new Intent(MainActivity.this, SickNote.class);
                                    sick.putExtra("from", 1);
                                    startActivity(sick);
                                    overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);

                                    dialog.cancel();
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    pref.setAlarm(2);
                                    dialog.cancel();
                                }
                            });
                    builder.show();
                }
            }
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("HI", "Des");
     //   unregisterReceiver(receiver);

    }

    @Override
    protected void onPause() {
        super.onPause();

    }



    @Override
    protected void onResume() {
        super.onResume();

        pref.setPODmobile(null);

        if(_PhoneNo==null){
            _PhoneNo="999999999";
        }
        mDatabase.child("Patient").addListenerForSingleValueEvent(new FirebaseDataListenerPatient());
        StringRequest eventoReq = new StringRequest(com.android.volley.Request.Method.POST, Config_URL.GET_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.w("details", response);

                        final ArrayList<Login> mCategory=new ArrayList<Login>();
                        final ArrayList<Login> mLinks=new ArrayList<Login>();
                        try {

                            JSONObject jsonObj = new JSONObject(response);


                            JSONArray Contacts = jsonObj.getJSONArray("getpodpatient");

                            if(Contacts.length()>0){
                             noofpodpatient.setText(String.valueOf(Contacts.length()));
                            }else{
                                noofpodpatient.setText(String.valueOf(0));
                            }


                            JSONArray links = jsonObj.getJSONArray("links1");
                            for (int i = 0; i < links.length(); i++) {
                                JSONObject c = links.getJSONObject(i);
                                if (!c.isNull("Links")) {
                                   pref.setLinks1(c.getString("Links"));
                                }
                            }

                            JSONArray links2 = jsonObj.getJSONArray("links2");
                            for (int i = 0; i < links2.length(); i++) {
                                JSONObject c = links2.getJSONObject(i);
                                if (!c.isNull("Links")) {
                                    pref.setLinks2(c.getString("Links"));
                                }
                            }

                            JSONArray links3 = jsonObj.getJSONArray("govt");
                            for (int i = 0; i < links3.length(); i++) {
                                JSONObject c = links3.getJSONObject(i);
                                if (!c.isNull("Links")) {
                                    pref.setLinks3(c.getString("Links"));
                                }
                            }

                            JSONArray videos = jsonObj.getJSONArray("videos");
                            for (int i = 0; i < videos.length(); i++) {
                                JSONObject c = videos.getJSONObject(i);
                                if (!c.isNull("Links")) {
                                    pref.setLinks4(c.getString("Links"));
                                }
                            }

                            JSONArray satistics = jsonObj.getJSONArray("satistics");
                            for (int i = 0; i < satistics.length(); i++) {
                                JSONObject c = satistics.getJSONObject(i);
                                if (!c.isNull("Links")) {
                                    pref.setLinks5(c.getString("Links"));
                                }
                            }


                            JSONArray category = jsonObj.getJSONArray("category");
                            for (int i = 0; i < category.length(); i++) {
                                JSONObject c = category.getJSONObject(i);
                                if (!c.isNull("Photo")) {
                                    Login item = new Login();
                                    item.setPhoto(c.getString("Photo"));
                                    item.setName(c.getString("Category"));
                                    mCategory.add(item);
                                }
                            }



                            if (mCategory.size() > 0) {
                                smtext.setVisibility(View.VISIBLE);
                                _only.setVisibility(View.VISIBLE);
                                _CommodityAdapter sAdapter1 = new _CommodityAdapter(MainActivity.this, mCategory);
                                sAdapter1.notifyDataSetChanged();
                                sAdapter1.setHasStableIds(true);
                                _only.setAdapter(sAdapter1);
                                _only.setHasFixedSize(true);
                                StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(getSpan(), StaggeredGridLayoutManager.VERTICAL);
                                _only.setLayoutManager(mLayoutManager);
                                _only.setItemAnimator(new DefaultItemAnimator());


                            }else {
                                smtext.setVisibility(View.GONE);
                                _only.setVisibility(View.GONE);
                            }


                        } catch (final JSONException e) {
                            Log.e("HI", "Json parsing error: " + e.getMessage());


                        }

                    }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("uuu", "Error: " + error.getMessage());
                vollyError(error);

            }

        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("_mId", _PhoneNo);
                params.put("country", String.valueOf(pref.getCountry()));
                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(eventoReq);


        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config_URL.REGISTRATION_COMPLETE));
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config_URL.PUSH_NOTIFICATION));
        NotificationUtils.clearNotifications(getApplicationContext());



    }



    public String getLastTen(String str) {

        return str.length() <= 10 ? str : str.substring(str.length() - 10);
    }



    public static boolean isTV(Context context) {
        return ((UiModeManager) context
                .getSystemService(Context.UI_MODE_SERVICE))
                .getCurrentModeType() == Configuration.UI_MODE_TYPE_TELEVISION;
    }
    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
    public int getSpan() {
        int orientation = getScreenOrientation(MainActivity.this);
        if (isTV(MainActivity.this)) return 2;
        if (isTablet(MainActivity.this))
            return orientation == Configuration.ORIENTATION_PORTRAIT ? 2 : 2;
        return orientation == Configuration.ORIENTATION_PORTRAIT ? 2 : 2;
    }
    public static int getScreenOrientation(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels <
                context.getResources().getDisplayMetrics().heightPixels ?
                Configuration.ORIENTATION_PORTRAIT : Configuration.ORIENTATION_LANDSCAPE;
    }




    private void vollyError(VolleyError error) {
        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
            
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "Network is unreachable. Please try another time", Snackbar.LENGTH_SHORT)
                    ;
            snackbar.setActionTextColor(Color.RED);
            snackbar.show();
            
        } else if (error instanceof AuthFailureError) {
            
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "Network is unreachable. Please try another time", Snackbar.LENGTH_SHORT)
                    ;
            snackbar.setActionTextColor(Color.RED);
            snackbar.show();
            
        } else if (error instanceof ServerError) {
            
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "Server Error.Please try another time", Snackbar.LENGTH_SHORT)
                    ;
            snackbar.setActionTextColor(Color.RED);
            snackbar.show();
            
        } else if (error instanceof NetworkError) {
            
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "Network Error. Please try another time", Snackbar.LENGTH_SHORT)
                    ;
            snackbar.setActionTextColor(Color.RED);
            snackbar.show();
            
        } else if (error instanceof ParseError) {
            
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "Data Error. Please try another time", Snackbar.LENGTH_SHORT)
                    ;
            snackbar.setActionTextColor(Color.RED);
            snackbar.show();
            
        }
    }




    private class FirebaseDataListenerPatient implements ValueEventListener {

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

            Log.e("MainActivity", "FirebaseDataListenerPatient: " + String.valueOf(999));
                long count = dataSnapshot.getChildrenCount();
                noofpatient.setText(String.valueOf(count));

                }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        //replaces the default 'Back' button action
        if(keyCode== KeyEvent.KEYCODE_BACK)   {
            Snackbar snackbar1 = Snackbar
                    .make(coordinatorLayout, "Are you Sure to exit?", Snackbar.LENGTH_LONG)
                    .setAction("Exit", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            overridePendingTransition(R.anim.slide_down1, R.anim.rbounce);
                            finishAffinity();
                            finish();

                        }
                    });
            snackbar1.setActionTextColor(Color.RED);
            snackbar1.show();

        }
        return true;
    }

    @Override
    public void onClick(View view) {
    switch (view.getId()){

        case R.id.MonitorFamily:
            Intent monitor = new Intent(MainActivity.this, MonitorFamilyPOD.class);
            startActivity(monitor);
            overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
            break;

        case R.id.addmember:
            if(!MainActivity.this.isFinishing()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.AlertDialogTheme)
                            .setIcon(R.mipmap.ic_launcher)
                            .setTitle("Add patient")
                            .setMessage("You are creating account for a patient")
                            .setCancelable(true)
                            .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent sick = new Intent(MainActivity.this, Addpatient.class);
                                    sick.putExtra("from", 1);
                                    startActivity(sick);
                                    overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);

                                    dialog.cancel();
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    pref.setAlarm(2);
                                    dialog.cancel();
                                }
                            });
                    builder.show();
            }

            break;




        case R.id.submitarequest:
            Intent test = new Intent(MainActivity.this, RequestTest.class);
            startActivity(test);
            overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);



            break;

        case R.id._n2:

            Intent sick = new Intent(MainActivity.this, HeadingGeneralInfo.class);
            startActivity(sick);
            overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);


            break;

        case R.id.mystatus:
            if(_PhoneNo!=null) {
                if(pref.getResponsibility()==3){
                    pref.setWorkID(Integer.parseInt(_PhoneNo));
                }
                Intent sicks = new Intent(MainActivity.this, PatientDetails.class);
                startActivity(sicks);
                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
            }else {
                Intent eme = new Intent(MainActivity.this, ServiceOffer.class);
                eme.putExtra("from",1);
                startActivity(eme);
                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);

            }



            break;

        case R.id._n1:
            if(_PhoneNo!=null) {
                if(pref.getFirstQuestion()!=0) {
                    Intent n1 = new Intent(MainActivity.this, Consultation.class);
                    n1.putExtra("from", 1);
                    startActivity(n1);
                    overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);

                }else{
                    Intent n1 = new Intent(MainActivity.this, Questions.class);
                    n1.putExtra("from", 1);
                    startActivity(n1);
                    overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);

                }
            }else {
                Intent eme = new Intent(MainActivity.this, ServiceOffer.class);
                eme.putExtra("from",1);
                startActivity(eme);
                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);

            }


        break;

        case R.id.questions:
            if(_PhoneNo!=null) {
                Intent eme = new Intent(MainActivity.this, Questions.class);
                startActivity(eme);
                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);

            }else {
                Intent eme = new Intent(MainActivity.this, ServiceOffer.class);
                eme.putExtra("from",1);
                startActivity(eme);
                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);

            }
            break;




        case R.id.videos:
            Intent videos = new Intent(MainActivity.this, Wb1_access.class);
            videos.putExtra("from",6);
            startActivity(videos);
            overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);

            break;

        case R.id.treatment:
            Intent treatment = new Intent(MainActivity.this, Wb1_access.class);
            treatment.putExtra("from",8);
            treatment.putExtra("links","http://139.59.38.160/Corona/premptive.pdf");
            startActivity(treatment);
            overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
            break;




        case R.id.n8:
            Intent n8 = new Intent(MainActivity.this, Wb1_access.class);
            n8.putExtra("from",2);
            startActivity(n8);
            overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);

            break;
        case R.id._n5:
            Intent _n5 = new Intent(MainActivity.this, Wb1_access.class);
            _n5.putExtra("from",1);
            startActivity(_n5);
            overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
            break;


        case R.id.noofpatient:
            Intent o = new Intent(MainActivity.this, MapFragment.class);
            o.putExtra("from",4);
            startActivity(o);
            overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);

            break;

        case R.id.name_10:
            Intent name_10 = new Intent(MainActivity.this, MapFragment.class);
            name_10.putExtra("from",5);
            startActivity(name_10);
            overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);

            break;


    }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_white, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_refresh1:

                overridePendingTransition(0, 0);
                startActivity(getIntent());
                break;
            case R.id.action_notification:
                if (_PhoneNo != null) {
                    Intent o = new Intent(MainActivity.this, Account_settings.class);
                    startActivity(o);
                    overridePendingTransition(R.anim.slide_up1, R.anim.slide_down1);
                } else {
                    Intent o = new Intent(MainActivity.this, ServiceOffer.class);
                    startActivity(o);

                    overridePendingTransition(R.anim.slide_up1, R.anim.slide_down1);
                }
                break;
            case R.id.action_noti:
                Intent o = new Intent(MainActivity.this, NotificationAll.class);
                startActivity(o);

                overridePendingTransition(R.anim.slide_up1, R.anim.slide_down1);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
