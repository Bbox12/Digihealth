package com.admin.ecosense.Activities;

import android.content.BroadcastReceiver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.admin.ecosense.Doctors.Doctorwindow;
import com.admin.ecosense.Login.ServiceOffer;
import com.admin.ecosense.Questions.Questions;
import com.admin.ecosense.R;
import com.admin.ecosense.helper.AppController;
import com.admin.ecosense.helper.Config_URL;
import com.admin.ecosense.helper.Login;
import com.admin.ecosense.helper.PrefManager;
import com.admin.ecosense.helper.home;
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
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;


/**
 * Created by parag on 30/08/16.
 */
public class Splash_screen extends AppCompatActivity {
    public static final int MULTIPLE_PERMISSIONS = 10;
    private static final String TAG = Splash_screen.class.getSimpleName();

    Boolean isInternetPresent = false;
    Thread splashTread;
    private PrefManager pref;
    private double My_lat = 0, My_long = 0;
    private boolean permissionsAllowd;
    private RelativeLayout Splash;
    private CoordinatorLayout coordinatorLayout;
    private boolean dont = false;
    private int Online;
    private String _phoneNo;
    private boolean launced = false;
    private String regId;
    private double User_review = 0;
    private String User_image, User_name;
    private String User_ID;
    private boolean going = false;
    private int version_ = 3, imp = 0;
    private int version_1 = 0, _imp_1 = 0;
    private TextView _t2;
    private DatabaseReference mDatabase;
    private int isVerified=0;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private boolean _tpermission=false;
    private String token;
    private WebView _webview;
    private ProgressBar progressBar;
    private String _name,_nextdate,_nextTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);

        coordinatorLayout = findViewById(R.id
                .cor_home_main);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        pref = new PrefManager(getApplicationContext());
        HashMap<String, String> user = pref.getUserDetails();
        launced=false;
        _phoneNo = user.get(PrefManager.KEY_MOBILE);
        progressBar=findViewById(R.id.progressBarSplash);
        _webview = findViewById(R.id.webView);
        _webview.setBackgroundColor(getResources().getColor(R.color.blue)); //for gif without background
        _webview.loadUrl("file:///android_asset/splash.gif");
        _webview.getSettings().setLoadsImagesAutomatically(true);
        _webview.getSettings().setLoadWithOverviewMode(true);
        _webview.getSettings().setUseWideViewPort(true);
        mDatabase.child("Version").child("version").setValue("3");
        mDatabase.child("Version").child("imp").setValue("0");
        mDatabase.child("Version").child("dis").setValue("1");
        mDatabase.child("Version").child("rTimer").setValue("3000");
    }




    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    public void onPause() {
        super.onPause();
        //mDatabase.child("Online").removeEventListener(new FirebaseDataListener_online());
    }

    @Override
    protected void onStop() {
        super.onStop();
        //mDatabase.child("Online").removeEventListener(new FirebaseDataListener_online());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
       // mDatabase.child("Online").removeEventListener(new FirebaseDataListener_online());
    }

    @Override
    protected void onResume() {
        super.onResume();

          StartAnimations();
        if(_phoneNo!=null){
            mDatabase.child("Build").child(_phoneNo).setValue("2");
        }



    }

    private void StartAnimations() {

        splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    // Splash screen pause time
                    while (waited < 3500) {
                        sleep(100);
                        waited += 100;
                    }
                    new GetUser().execute();
                } catch (InterruptedException e) {
                    // do nothing
                }

            }
        };
        splashTread.start();
    }


    private class GetUser extends AsyncTask<Void, Integer, String> {


        private ArrayList<Login> mItems = new ArrayList<Login>();
        private String _ID;


        protected String doInBackground(Void... args) {
            return uploadFile();
        }

        private String uploadFile() {
            // TODO Auto-generated method stub
            String res = null;

            try {
                if(_phoneNo==null){
                    _phoneNo="999999999";
                }


                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("_mId", _phoneNo)
                        .build();

                Request request = new Request.Builder()
                        .url(Config_URL.GET_LOGIN)
                        .post(requestBody)
                        .build();
                OkHttpClient client = new OkHttpClient();
                okhttp3.Response response = client.newCall(request).execute();
                res = response.body().string();
                try {
                    JSONObject jsonObj = new JSONObject(res);
                    JSONArray _Attendence = jsonObj.getJSONArray("login");
                    for (int i = 0; i < _Attendence.length(); i++) {
                        JSONObject c = _Attendence.getJSONObject(i);
                        if (!c.isNull("date")) {
                            pref.setPOD(c.getInt("POD"));
                          pref.setDate(c.getString("date"));
                        }

                    }
                    JSONArray medical = jsonObj.getJSONArray("medical");
                    for (int i = 0; i < medical.length(); i++) {
                        JSONObject c = medical.getJSONObject(i);
                        if (!c.isNull("ID")) {
                            pref.setMedical(1);
                        }
                    }

                    JSONArray consultation = jsonObj.getJSONArray("consultation");
                    if(consultation.length()>0) {
                        for (int i = 0; i < consultation.length(); i++) {
                            JSONObject c = consultation.getJSONObject(i);
                            if (!c.isNull("IDDoc")&& c.getInt("IDDoc") !=0) {
                                pref.setConsultation(c.getInt("IDDoc"));
                                pref.setWorkID(c.getInt("IDUser"));
                                pref.setWashHands(1);
                                pref.setSymptoms(1);
                                pref.setOxygen(1);
                                pref.setMedical(1);
                            }else {
                                pref.setConsultation(1);
                                pref.setSymptoms(1);
                                pref.setOxygen(1);
                                pref.setMedical(1);
                                pref.setMask(1);
                                pref.setWashHands(0);
                            }

                        }
                    }else{
                        pref.setMask(0);
                        pref.setWashHands(0);
                        pref.setConsultation(0);
                        pref.setSymptoms(0);
                        pref.setOxygen(0);
                        pref.setMedical(0);
                    }

                   // pref.setFirstQuestion(0);
                } catch (final JSONException e) {
                    Log.e("HI", "Json parsing error: " + e.getMessage());


                }

                Log.e("TAG", "Response : " + res);

                return res;

            } catch (UnknownHostException | UnsupportedEncodingException e) {
                Log.e("TAG", "Error: " + e.getLocalizedMessage());
            } catch (Exception e) {
                Log.e("TAG", "Other Error: " + e.getLocalizedMessage());
            }


            return res;

        }
        protected void onPostExecute(String file_url) {
            go2();
            mDatabase.child("Version").addValueEventListener(new FirebaseDataListener());
        }

    }

    private void go_trough() {
        if (_imp_1 != imp) {
            if (!Splash_screen.this.isFinishing()) {
                new AlertDialog.Builder(Splash_screen.this, R.style.AlertDialogTheme)
                        .setIcon(R.mipmap.ic_launcher)
                        .setTitle("Update DigiHealth")
                        .setMessage("A new version of DigiHealth is available!")
                        .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String url = "https://play.google.com/store/apps/details?id=" + getPackageName();
                                Intent i = new Intent(Intent.ACTION_VIEW);
                                i.setData(Uri.parse(url));
                                startActivity(i);
                                dialog.cancel();
                            }
                        })
                        .show();
            }
        } else {

            if (version_1 != version_) {
                if (!Splash_screen.this.isFinishing()) {
                    new AlertDialog.Builder(Splash_screen.this, R.style.AlertDialogTheme)
                            .setTitle("Its time to update DigiHealth")
                            .setMessage("A new version of DigiHealth is available!")
                            .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String url = "https://play.google.com/store/apps/details?id=" + getPackageName();
                                    Intent i = new Intent(Intent.ACTION_VIEW);
                                    i.setData(Uri.parse(url));
                                    startActivity(i);
                                    dialog.cancel();
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    go_again();

                                    dialog.cancel();
                                }
                            })
                            .show();
                }
            } else {

                go_again();
            }
        }

    }

    private void go_again() {
        pref.setClockIn(0);
        if(!Splash_screen.this.isFinishing()) {
            if(pref.getFirstQuestion()==1){
              go();
            }else{
                if(_phoneNo==null){
                    Intent o = new Intent(Splash_screen.this, ServiceOffer.class);
                    startActivity(o);
                    overridePendingTransition(R.anim.slide_up1, R.anim.slide_down1);

                }else {
                  go();
                }
            }
            }


    }

    private void go() {
        if(pref.getResponsibility()==1) {
            if(pref.getFirstQuestion()==0) {
                Intent o = new Intent(Splash_screen.this, Questions.class);
                startActivity(o);
                overridePendingTransition(R.anim.slide_up1, R.anim.slide_down1);

            }else{
                Intent o = new Intent(Splash_screen.this, MainActivity.class);
                startActivity(o);
                overridePendingTransition(R.anim.slide_up1, R.anim.slide_down1);

            }
        }else if(pref.getResponsibility()==2) {
            Intent o = new Intent(Splash_screen.this, Doctorwindow.class);
            startActivity(o);
            overridePendingTransition(R.anim.slide_up1, R.anim.slide_down1);

        }else if(pref.getResponsibility()==3) {
            Intent o = new Intent(Splash_screen.this, MainActivity.class);
            startActivity(o);
            overridePendingTransition(R.anim.slide_up1, R.anim.slide_down1);

        }else{
            Intent o = new Intent(Splash_screen.this, ServiceOffer.class);
            startActivity(o);
            overridePendingTransition(R.anim.slide_up1, R.anim.slide_down1);

        }
    }


    private class FirebaseDataListener implements ValueEventListener {


        private String key;

        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if (dataSnapshot.getKey() != null) {
                Log.w("hello", "dataSnapshot, " + dataSnapshot.getChildrenCount());
               home item =dataSnapshot.getValue(home.class);


                if (item != null && item.getVersion() != null && item.getImp() != null) {
                    version_1 = Integer.parseInt(item.getVersion());
                    _imp_1 = Integer.parseInt(item.getImp());
                    pref.setDistance(Float.parseFloat(item.getDis()));
                    pref.setRepeatTimer(Long.parseLong(item.getrTimer()));
                    go_trough();
                }

            }
        }

        @Override
        public void onCancelled (@NonNull DatabaseError databaseError){

        }

    }

    private void go2() {
        progressBar.setVisibility(View.VISIBLE);
        StringRequest eventoReq = new StringRequest(com.android.volley.Request.Method.POST, Config_URL.GET_LOGIN,
                new Response.Listener<String>() {
                    private String _nextdate,_nextTime;

                    @Override
                    public void onResponse(String response) {
                        Log.w("ccc", response.toString());
                        progressBar.setVisibility(View.GONE);
                        try{
                            JSONObject jsonObj = new JSONObject(response);
                            JSONArray Contacts = jsonObj.getJSONArray("startsession");

                            if(Contacts.length()>0){

                                for (int j = 0; j < Contacts.length(); j++) {

                                    JSONObject d = Contacts.getJSONObject(j);
                                    if(!d.getString("NextDate").contains("0000-00-00")) {
                                        if(pref.getWashHands()!=d.getInt("ID")) {
                                            pref.setWashHands(d.getInt("ID"));
                                            _name = d.getString("Name");
                                            _nextdate = d.getString("NextDate");
                                            _nextTime = d.getString("NextTime");
                                        }
                                    }else{
                                        pref.setWashHands(0);
                                    }
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(_name!=null){
                            String inputPattern = "yyyy-MM-ddHH:mm:ss";
                            SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);

                            Date date = null;


                            long starttime = 0;
                            try {
                                date = inputFormat.parse(_nextdate+_nextTime);
                                starttime=date.getTime();
                            } catch (ParseException e) {
                                //e.printStackTrace();
                                String inputPattern1 = "yyyy-MM-dd";
                                SimpleDateFormat inputFormat1 = new SimpleDateFormat(inputPattern1);

                                Date date1 = null;

                                try {
                                    date1 = inputFormat1.parse(_nextdate);
                                    starttime=date1.getTime();
                                } catch (ParseException e1) {
                                    e1.printStackTrace();
                                }
                            }
                            long endDate = starttime + 12 * 60 * 60*1000;



                            Intent intent = new Intent(Intent.ACTION_EDIT);
                            intent.setType("vnd.android.cursor.item/event");
                            intent.putExtra(CalendarContract.Events.TITLE, "Appointment with DIGIHEALTH");
                            intent.putExtra(CalendarContract.Events.DESCRIPTION, "You have an appointment scheduled with doctor "+_name);
                            intent.putExtra(CalendarContract.Events.EVENT_LOCATION, "DIGIHEALTH APP");
                            intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, starttime);
                            intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endDate);
                            intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, 1);
                            intent.putExtra(CalendarContract.Events.STATUS, 1);
                            intent.putExtra(CalendarContract.Events.VISIBLE, 1);
                            intent.putExtra(CalendarContract.Events.HAS_ALARM, 1);
                            startActivity(intent);
                        }

                    }



                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, "Network is unreachable. Please try another time", Snackbar.LENGTH_LONG)
                            .setAction("Refresh", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    go();
                                }
                            });
                    snackbar.setActionTextColor(Color.RED);
                    snackbar.show();
                } else if (error instanceof AuthFailureError) {
                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, "Network is unreachable. Please try another time", Snackbar.LENGTH_LONG)
                            .setAction("Refresh", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    go();
                                }
                            });
                    snackbar.setActionTextColor(Color.RED);
                    snackbar.show();
                } else if (error instanceof ServerError) {
                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, "Server Error.Please try another time", Snackbar.LENGTH_LONG)
                            .setAction("Refresh", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    go();
                                }
                            });
                    snackbar.setActionTextColor(Color.RED);
                    snackbar.show();
                } else if (error instanceof NetworkError) {
                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, "Network Error. Please try another time", Snackbar.LENGTH_LONG)
                            .setAction("Refresh", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    go();
                                }
                            });
                    snackbar.setActionTextColor(Color.RED);
                    snackbar.show();
                } else if (error instanceof ParseError) {
                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, "Data Error. Please try another time", Snackbar.LENGTH_LONG)
                            .setAction("Refresh", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    go();
                                }
                            });
                    snackbar.setActionTextColor(Color.RED);
                    snackbar.show();
                }


            }

        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("dID", String.valueOf(pref.getConsultation()));
                params.put("submenu", String.valueOf(pref.getWorkID()));
                return params;
            }
        };

        // Añade la peticion a la cola
        AppController.getInstance().addToRequestQueue(eventoReq);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //replaces the default 'Back' button action
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!Splash_screen.this.isFinishing()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Splash_screen.this, R.style.AlertDialogTheme)
                        .setIcon(R.mipmap.ic_launcher)
                        .setTitle("Are you sure to exit?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finishAffinity();
                                finish();
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
        return true;
    }

}




