package com.admin.ecosense.Doctors;

import android.app.ActivityManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.UiModeManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.admin.ecosense.Activities.Account_settings;
import com.admin.ecosense.Activities.HeadingGeneralInfo;
import com.admin.ecosense.Activities.News;
import com.admin.ecosense.Activities.NotificationAll;
import com.admin.ecosense.Activities.Wb1_access;
import com.admin.ecosense.FCM.NotificationUtils;
import com.admin.ecosense.Login.ServiceOffer;
import com.admin.ecosense.Questions.Questions;
import com.admin.ecosense.R;
import com.admin.ecosense.geofence_21.MapFragment;
import com.admin.ecosense.helper.AppController;
import com.admin.ecosense.helper.Config_URL;
import com.admin.ecosense.helper.PrefManager;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
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

import java.util.HashMap;
import java.util.Map;

public class Doctorwindow extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = Doctorwindow.class.getSimpleName();
    private LinearLayout handsswitch,mystatus,submitarequest,general;
    private SwitchCompat washswitch;
    private AppCompatCheckBox uniregistered,anonymous;
    private CardView test;
    private PrefManager pref;
    private CoordinatorLayout coordinatorLayout;
    private double Mylat,Mylong;
    private TextView noofpatient,patientdetails;
    private Button askwho;
    private LinearLayout familymebers;
    private CardView totalpatient,govtsite;
    private DatabaseReference mDatabase;
    private CardView _n5,n8,_news,videos,questions;
    private Toolbar toolbar;
    private int position=0;
    private RecyclerView _only,Sosrv,_brands,_brands2;
    private TextView smtext;
    private String _PhoneNo;
    private RelativeLayout _R1;
    private NestedScrollView scroller,scroller2;
    private ShimmerFrameLayout mShimmerViewContainer;
    private PendingIntent pendingIntent;
    private static final int ALARM_REQUEST_CODE = 10009;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private boolean _played;
    private String _familyPhone;
    private int curent,nStart=5,nEnd=250;
    private LinearLayout layoutBottomSheet;
    BottomSheetBehavior sheetBehavior;
    private boolean _frst=false;
    private Animation animBlink;
    private ImageView _blinks;
    private RecyclerView sosrv;
    private WebView _webview;
    private TextView nigtingle,refered,existing,_new;
    public static final int MULTIPLE_PERMISSIONS = 101;
    private static final int SELECT_FILE = 102;
    private Uri selectedImageUri;
    private String selectedVideoPath;
    private TextView _filename;
    private ProgressDialog mProgressDialog;
    private RequestQueue rQueue;
    private TextView _filenames;
    private Button _upload2;
    private Button _cancel;
    private boolean permissionsAllowd,_tpermission;
    private String mobileIp;
    private int _nigtingle,_refered,_existing,__new;
    private String _title,_description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        setContentView(R.layout.doclayout);
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        mShimmerViewContainer.setVisibility(View.VISIBLE);
        mShimmerViewContainer.startShimmerAnimation();
        handsswitch=findViewById(R.id._n1);
        handsswitch.setOnClickListener(this);
        general=findViewById(R.id._n2);
        general.setOnClickListener(this);
        pref=new PrefManager(getApplicationContext());
        HashMap<String, String> user = pref.getUserDetails();
        _PhoneNo = user.get(PrefManager.KEY_MOBILE);
        coordinatorLayout=findViewById(R.id.cor_home_main);
        noofpatient=findViewById(R.id.noofpatient);
        noofpatient.setOnClickListener(this);
        totalpatient=findViewById(R.id.totalpatient);
        totalpatient.setOnClickListener(this);
        govtsite=findViewById(R.id.govtsite);
        govtsite.setOnClickListener(this);
        _news=findViewById(R.id._news);
        _news.setOnClickListener(this);
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
        test=findViewById(R.id.test);
        test.setOnClickListener(this);
        familymebers=findViewById(R.id.familymebers);
        //    familymebers.setOnClickListener(this);
        submitarequest=findViewById(R.id.submitarequest);
        mystatus=findViewById(R.id.mystatus);
        submitarequest.setOnClickListener(this);
        mystatus.setOnClickListener(this);
        questions=findViewById(R.id.questions);
        questions.setOnClickListener(this);


        layoutBottomSheet = findViewById(R.id.bottom_sheet_explore);
        sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);
        sheetBehavior.setFitToContents(true);
        sheetBehavior.setHideable(true);

        sosrv=findViewById(R.id.sosrv);
        sosrv.setNestedScrollingEnabled(false);



        nigtingle=findViewById(R.id.nigtingle);
        refered=findViewById(R.id.refered);
        existing=findViewById(R.id.existing);
        _new=findViewById(R.id.newpatient);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("HI", "Des");
        //   unregisterReceiver(receiver);

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




    @Override
    protected void onPause() {
        super.onPause();

    }



    @Override
    protected void onResume() {
        super.onResume();


        mDatabase.child("Patient").addListenerForSingleValueEvent(new Doctorwindow.FirebaseDataListenerPatient());
        StringRequest eventoReq = new StringRequest(com.android.volley.Request.Method.POST, Config_URL.GET_LOGIN,
                new Response.Listener<String>() {


                    @Override
                    public void onResponse(String response) {

                        Log.w("details", response);

                        try {

                            JSONObject jsonObj = new JSONObject(response);

                            JSONArray night = jsonObj.getJSONArray("night");
                            for (int i = 0; i < night.length(); i++) {
                                JSONObject c = night.getJSONObject(i);
                                if (!c.isNull("COUNT")) {
                                    _nigtingle=(c.getInt("COUNT"));
                                }
                            }

                            JSONArray refer = jsonObj.getJSONArray("refer");
                            for (int i = 0; i < refer.length(); i++) {
                                JSONObject c = refer.getJSONObject(i);
                                if (!c.isNull("COUNT")) {
                                    _refered=(c.getInt("COUNT"));
                                }
                            }

                            JSONArray exist = jsonObj.getJSONArray("exist");
                            for (int i = 0; i < exist.length(); i++) {
                                JSONObject c = exist.getJSONObject(i);
                                if (!c.isNull("COUNT")) {
                                    _existing=(c.getInt("COUNT"));
                                }
                            }

                            JSONArray newpatient = jsonObj.getJSONArray("newpatient");
                            for (int i = 0; i < newpatient.length(); i++) {
                                JSONObject c = newpatient.getJSONObject(i);
                                if (!c.isNull("COUNT")) {
                                    __new=(c.getInt("COUNT"));
                                }
                            }





                        } catch (final JSONException e) {
                            Log.e("HI", "Json parsing error: " + e.getMessage());


                        }

                        nigtingle.setText(String.valueOf(_nigtingle));
                        refered.setText(String.valueOf(_refered));
                        existing.setText(String.valueOf(_existing));
                        _new.setText(String.valueOf(__new));
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
                params.put("_mId", "1");
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
        int orientation = getScreenOrientation(Doctorwindow.this);
        if (isTV(Doctorwindow.this)) return 2;
        if (isTablet(Doctorwindow.this))
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

            Log.e("Doctorwindow", "FirebaseDataListenerPatient: " + String.valueOf(999));
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


            case R.id.submitarequest:
                Intent test = new Intent(Doctorwindow.this, DocPatients.class);
                startActivity(test);
                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);


                break;

            case R.id._n2:

                Intent sick = new Intent(Doctorwindow.this, HeadingGeneralInfo.class);
                startActivity(sick);
                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);


                break;

            case R.id.mystatus:
                if(_PhoneNo!=null) {
                    Intent sicks = new Intent(Doctorwindow.this, DocProfile.class);
                    startActivity(sicks);
                    overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                }else {
                    Intent eme = new Intent(Doctorwindow.this, ServiceOffer.class);
                    eme.putExtra("from",1);
                    startActivity(eme);
                    overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                }



                break;

            case R.id._n1:
                if(_PhoneNo!=null) {
                    waitingroom();
                }else {
                    Intent eme = new Intent(Doctorwindow.this, ServiceOffer.class);
                    eme.putExtra("from",1);
                    startActivity(eme);
                    overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                }


                break;

            case R.id.questions:
                notification(3);
                break;



            case R.id.test:
                if(!Doctorwindow.this.isFinishing()) {
                    if (_PhoneNo == null) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(Doctorwindow.this, R.style.AlertDialogTheme)
                                .setIcon(R.mipmap.ic_launcher)
                                .setTitle("Please share your details")
                                .setMessage("We do not share or use your information in any way.")
                                .setCancelable(true)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent o = new Intent(Doctorwindow.this, ServiceOffer.class);
                                        startActivity(o);
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
                    } else {
                        if (pref.getID() != 0) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(Doctorwindow.this, R.style.AlertDialogTheme)
                                    .setIcon(R.mipmap.ic_launcher)
                                    .setTitle("Your request has been processed. We will get back to you shortly.")
                                    .setCancelable(true)
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(final DialogInterface dialog, int which) {
                                            StringRequest eventoReq = new StringRequest(com.android.volley.Request.Method.POST, Config_URL.URL_STORE_TEST,
                                                    new Response.Listener<String>() {
                                                        @Override
                                                        public void onResponse(String response) {
                                                            Log.w("timie", response.toString());

                                                            String[] par = response.split("error");
                                                            if (par[1].contains("false")) {
                                                                pref.setTest(1);
                                                                //  mDatabase.child("Patient").child(_PhoneNo).child("Test").setValue("YES");
                                                                dialog.cancel();
                                                            } else {
                                                                Toast.makeText(getApplicationContext(),"Failed to store information! Please try another time.",Toast.LENGTH_SHORT).show();
                                                            }

                                                        }


                                                    }, new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                    vollyError(error);

                                                }

                                            }) {
                                                @Override
                                                protected Map<String, String> getParams() {
                                                    // Posting parameters to login url
                                                    Map<String, String> params = new HashMap<String, String>();
                                                    params.put("mobile",_PhoneNo);
                                                    params.put("test", String.valueOf(1));
                                                    return params;
                                                }

                                            };

                                            // Añade la peticion a la cola
                                            AppController.getInstance().addToRequestQueue(eventoReq);


                                        }
                                    })
                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            //    mDatabase.child("Patient").child(_PhoneNo).child("Test").setValue("YES");
                                            dialog.cancel();
                                        }
                                    });
                            builder.show();
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(Doctorwindow.this, R.style.AlertDialogTheme)
                                    .setIcon(R.mipmap.ic_launcher)
                                    .setTitle("Please check the symptoms for a test")
                                    .setCancelable(true)
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent o = new Intent(Doctorwindow.this, Questions.class);
                                            startActivity(o);

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

                        }
                    }
                }
                break;

            case R.id.videos:
                notification(4);
                break;


            case R.id._news:
                Intent _news = new Intent(Doctorwindow.this, News.class);
                startActivity(_news);
                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                break;



            case R.id.govtsite:
                Intent _n6 = new Intent(Doctorwindow.this, Wb1_access.class);
                _n6.putExtra("from",3);
                startActivity(_n6);
                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                break;
            case R.id.n8:
                notification(2);
                break;
            case R.id._n5:
                notification(1);

                break;

            case R.id.totalpatient:
                Intent ww = new Intent(Doctorwindow.this, Wb1_access.class);
                startActivity(ww);
                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                break;
            case R.id.noofpatient:
                Intent o = new Intent(Doctorwindow.this, MapFragment.class);
                o.putExtra("from",4);
                startActivity(o);
                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                break;

            case R.id.name_10:
                Intent name_10 = new Intent(Doctorwindow.this, MapFragment.class);
                name_10.putExtra("from",5);
                startActivity(name_10);
                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                break;


        }
    }


    private void waitingroom() {
        if (!Doctorwindow.this.isFinishing()) {
            final CharSequence[] items = {"Waiting room", "Future Consultation",
                    "Cancel"};
            AlertDialog.Builder builder = new AlertDialog.Builder(Doctorwindow.this, R.style.AlertDialogTheme);
            builder.setTitle("Select!");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {
                    if (items[item].equals("Waiting room")) {
                        dialog.dismiss();
                        Intent n1 = new Intent(Doctorwindow.this, WaitingRoom.class);
                        n1.putExtra("from",1);
                        startActivity(n1);
                        overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                    } else if (items[item].equals("Future Consultation")) {
                        dialog.dismiss();
                        Intent n1 = new Intent(Doctorwindow.this, FutureConsultation.class);
                        n1.putExtra("from",1);
                        startActivity(n1);
                        overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                    } else if (items[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                }
            });
            builder.show();
        }

    }

    private void notification(final int i) {
        if (!Doctorwindow.this.isFinishing()) {
            final CharSequence[] items = {"Send notification to all patient", "Details",
                    "Cancel"};
            AlertDialog.Builder builder = new AlertDialog.Builder(Doctorwindow.this, R.style.AlertDialogTheme);
            builder.setTitle("Select!");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {
                    if (items[item].equals("Send notification to all patient")) {
                        dialog.dismiss();
                        sndNotification(i);
                    } else if (items[item].equals("Details")) {
                        dialog.dismiss();
                        sndDetails(i);
                    } else if (items[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                }
            });
            builder.show();
        }

    }

    private void sndDetails(int i) {
        if(i==1){
            if(_nigtingle!=0){
                go(i);
            }else{
               nopatient(i);
            }
        }else if(i==2){
            if(_refered!=0){
                go(i);
            }else{
                nopatient(i);
            }
        }else if(i==3){
            if(_existing!=0){
                go(i);
            }else{
                nopatient(i);
            }
        }else if(i==4){
            if(__new!=0){
                go(i);
            }else{
                nopatient(i);
            }
        }

    }

    private void nopatient(int i) {
        String _texttitle = "None";
        if(i==1){
            _texttitle=("No patient in Nightiangle patients");
        }else if(i==2){
            _texttitle=("No patient in  refered patients");
        }else if(i==3){
            _texttitle=("No patient in  Ekuphileni patients");
        }else if(i==4){
            _texttitle=("No patient in  New patients");
        }

        Toast.makeText(getApplicationContext(),_texttitle,Toast.LENGTH_SHORT).show();
    }

    private void go(int i) {
        Intent o = new Intent(Doctorwindow.this, PatientList.class);
        o.putExtra("from",i);
        startActivity(o);
        overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
    }

    private void sndNotification(final int i) {

        Intent o = new Intent(Doctorwindow.this, sendNotification.class);
        o.putExtra("from",i);
        startActivity(o);
        overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);

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
                    Intent o = new Intent(Doctorwindow.this, Account_settings.class);
                    startActivity(o);
                    overridePendingTransition(R.anim.slide_up1, R.anim.slide_down1);
                } else {
                    Intent o = new Intent(Doctorwindow.this, ServiceOffer.class);
                    startActivity(o);

                    overridePendingTransition(R.anim.slide_up1, R.anim.slide_down1);
                }
                break;
            case R.id.action_noti:
                Intent o = new Intent(Doctorwindow.this, NotificationAll.class);
                startActivity(o);

                overridePendingTransition(R.anim.slide_up1, R.anim.slide_down1);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
