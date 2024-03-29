package com.admin.ecosense.Questions;

import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.admin.ecosense.Activities.Account_settings;
import com.admin.ecosense.Activities.MainActivity;
import com.admin.ecosense.Activities.NotificationAll;
import com.admin.ecosense.Adapters._statusAdapter;
import com.admin.ecosense.Login.ServiceOffer;
import com.admin.ecosense.R;
import com.admin.ecosense.helper.Config_URL;
import com.admin.ecosense.helper.Login;
import com.admin.ecosense.helper.PrefManager;
import com.admin.ecosense.helper.Qns;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class GetMyStatus extends AppCompatActivity {

    private static final String TAG = GetMyStatus.class.getSimpleName();
    private static final int RESULT_PICK_CONTACT = 101;
    private Toolbar toolbar;
    private double My_lat = 0, My_long = 0;
    private PrefManager pref;
    private String _PhoneNo;
    private String phoneNo;
    private String name;
    private RecyclerView Sosrv;
    private ArrayList<Qns> mItems = new ArrayList<Qns>();
    private String mobileIp;
    private ProgressBar progressBar;
    public boolean success;
    private CoordinatorLayout coordinatorLayout;
    private DatabaseReference mDatabase;
    private String _familyPhone;
    private EditText _t1;
    private String _Name;
    private TextView textView101,textView102,covidstatus;
    EditText _nameText;
    EditText _userName;
    EditText _bday;
    EditText PatientID;
    private EditText _userBday;
    private EditText _userGender;
    private LinearLayout _L1;

    public static String getMobileIPAddress() {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        return addr.getHostAddress();
                    }
                }
            }
        } catch (Exception ex) {
        } // for now eat exceptions
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.getstatusnote);
        coordinatorLayout=findViewById(R.id.em);
        toolbar = findViewById(R.id.toolbar_later_sos);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        _userName = findViewById(R.id.input_user_name);
        _userGender = findViewById(R.id.input_gender);
        _userBday = findViewById(R.id.input_bday);
        PatientID = findViewById(R.id.PatientID);
        Sosrv = findViewById(R.id.sosrv);
//        _t1=findViewById(R.id._t1);

        progressBar=findViewById(R.id.progressBaremergency);
        progressBar.setVisibility(View.VISIBLE);

        pref = new PrefManager(getApplicationContext());
        HashMap<String, String> user = pref.getUserDetails();
        _PhoneNo = user.get(PrefManager.KEY_MOBILE);
        _Name = user.get(PrefManager.KEY_NAME);

        textView101=findViewById(R.id.textView101);
        textView102=findViewById(R.id.textView102);

        textView101.setText("My ");
        textView102.setText("Status");
        covidstatus=findViewById(R.id.covidstatus);
        _L1=findViewById(R.id._L1);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);

            }
        });
        mobileIp = getMobileIPAddress();
        if (TextUtils.isEmpty(mobileIp)) {
            mobileIp = getWifiIPAddress();
        }
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public String getWifiIPAddress() {
        WifiManager wifiMgr = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
        int ip = wifiInfo.getIpAddress();
        return Formatter.formatIpAddress(ip);
    }



    @Override
    protected void onResume() {
        super.onResume();
        if(_PhoneNo==null){
            _PhoneNo="9999999999";
        }

        if(pref.getClockIn()==1){
           _L1.setVisibility(View.VISIBLE);
        }else {
            _L1.setVisibility(View.GONE);
        }
        go();
    }

    private void go() {
        mItems.clear();
        new GetUser().execute();

    }


    private class GetUser extends AsyncTask<Void, Integer, String> {


        private ArrayList<Login> mItems = new ArrayList<Login>();
        private ArrayList<Login>mRequest=new ArrayList<>();
        private int _Status=0;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);

        }

        protected String doInBackground(Void... args) {
            return uploadFile();
        }

        private String uploadFile() {
            // TODO Auto-generated method stub
            String res = null;

            try {


                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("_mId", _PhoneNo)
                        .build();

                Request request = new Request.Builder()
                        .url(Config_URL.GET_LOGIN)
                        .post(requestBody)
                        .build();
                OkHttpClient client = new OkHttpClient();
                okhttp3.Response response = client.newCall(request).execute();
                res = response.body().string();
                if (res != null) {
                    try {
                        JSONObject jsonObj = new JSONObject(res);
                        JSONArray _Attendence = jsonObj.getJSONArray("login");
                        for (int i = 0; i < _Attendence.length(); i++) {
                            JSONObject c = _Attendence.getJSONObject(i);
                            if (!c.isNull("PhoneNo")) {
                                Login item = new Login();
                                item.setName(c.getString("Name"));
                                item.set_Phone_no(c.getString("PhoneNo"));
                                item.setEmail(c.getString("Email"));
                                item.setGender(c.getString("Gender"));
                                item.setBday(c.getString("Date_of_Birth"));
                                item.setPatientID(c.getString("IDPatient"));
                                mItems.add(item);
                            }

                        }

                        JSONArray shops = jsonObj.getJSONArray("request");
                        for (int i = 0; i < shops.length(); i++) {
                            JSONObject c = shops.getJSONObject(i);

                            if (!c.isNull("Name")) {
                                Login item=new Login();
                                item.setID(c.getInt("ID"));
                                item.setName(c.getString("Name"));
                                mRequest.add(item);
                            }
                        }

                        JSONArray covid = jsonObj.getJSONArray("covid");
                        for (int i = 0; i < covid.length(); i++) {
                            JSONObject c = covid.getJSONObject(i);

                            if (!c.isNull("Status")) {
                                _Status=c.getInt("Status");
                            }else{
                                pref.setClockIn(0);
                            }
                        }


                    } catch (final JSONException e) {
                        Log.e("HI", "Json parsing error: " + e.getMessage());


                    }

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
            progressBar.setVisibility(View.GONE);
            if (mItems.size() != 0) {
                progressBar.setVisibility(View.GONE);
                _userName.setText(mItems.get(0).getName(0));
                _userGender.setText(mItems.get(0).getGender(0));
                _userBday.setText(mItems.get(0).getBday(0));
                PatientID.setText(mItems.get(0).getPatientID(0));
                if(mRequest.size()>0) {
                    _statusAdapter sAdapter = new _statusAdapter(GetMyStatus.this, mRequest);
                    sAdapter.notifyDataSetChanged();
                    Sosrv.setVisibility(View.VISIBLE);
                    Sosrv.setItemAnimator(new DefaultItemAnimator());
                    Sosrv.setAdapter(sAdapter);
                    Sosrv.setHasFixedSize(true);
                    LinearLayoutManager mHorizontal = new LinearLayoutManager(GetMyStatus.this, LinearLayoutManager.VERTICAL, false);
                    Sosrv.setLayoutManager(mHorizontal);
                }

                if(_Status==1){
                    covidstatus.setText("Pending");
                }else  if(_Status==2){
                    covidstatus.setText("Pending Test");
                }else  if(_Status==3){
                    covidstatus.setText("Pending lab result");
                }else  if(_Status==4){
                    covidstatus.setText("Positive");
                }else  if(_Status==5){
                    covidstatus.setText("Negative");
                }

            }
        }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_white, menu);
        return true;

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
                    Intent o = new Intent(GetMyStatus.this, Account_settings.class);
                    startActivity(o);
                    overridePendingTransition(R.anim.slide_up1, R.anim.slide_down1);
                } else {
                    Intent o = new Intent(GetMyStatus.this, ServiceOffer.class);
                    startActivity(o);

                    overridePendingTransition(R.anim.slide_up1, R.anim.slide_down1);
                }
                break;
            case R.id.action_noti:
                Intent o = new Intent(GetMyStatus.this, NotificationAll.class);
                startActivity(o);

                overridePendingTransition(R.anim.slide_up1, R.anim.slide_down1);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        //replaces the default 'Back' button action
        if(keyCode== KeyEvent.KEYCODE_BACK)   {

            finish();
            overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);

        }
        return true;
    }



}

