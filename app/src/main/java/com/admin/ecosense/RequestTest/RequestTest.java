package com.admin.ecosense.RequestTest;

import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
import com.admin.ecosense.Adapters._brandAdapter;
import com.admin.ecosense.Login.ServiceOffer;
import com.admin.ecosense.R;
import com.admin.ecosense.helper.AppController;
import com.admin.ecosense.helper.Config_URL;
import com.admin.ecosense.helper.Login;
import com.admin.ecosense.helper.PrefManager;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestTest extends AppCompatActivity  {

    private static final String TAG = RequestTest.class.getSimpleName();
    private static final int RESULT_PICK_CONTACT = 101;
    private Toolbar toolbar;
    private double My_lat = 0, My_long = 0;
    private PrefManager pref;
    private String _PhoneNo;
    private String phoneNo;
    private String name;
    private RecyclerView Sosrv;
    private String mobileIp;
    private ProgressBar progressBar;
    public boolean success;
    private CoordinatorLayout coordinatorLayout;
    private DatabaseReference mDatabase;
    private String _familyPhone;
    private EditText _t1;
    private String _Name;
    private TextView textView101,textView102;
    EditText _nameText;
    EditText _userName;
    EditText _bday;
    EditText PatientID;
    private EditText _userBday;
    private EditText _userGender;
    private TextView _T1;
    private Button Submit;
    private String _text;
    private LinearLayout _L1,_L2;

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
        setContentView(R.layout.sicknote);
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

        progressBar=findViewById(R.id.progressBaremergency);
        progressBar.setVisibility(View.VISIBLE);

        pref = new PrefManager(getApplicationContext());
        HashMap<String, String> user = pref.getUserDetails();
        _PhoneNo = user.get(PrefManager.KEY_MOBILE);
        _Name = user.get(PrefManager.KEY_NAME);

        textView101=findViewById(R.id.textView101);
        textView102=findViewById(R.id.textView102);


        _L1=findViewById(R.id._L1);
        _L1.setVisibility(View.VISIBLE);

        _L2=findViewById(R.id._L2);
        _L2.setVisibility(View.GONE);
        textView101.setText("Submit a request ");
        textView102.setVisibility(View.GONE);

        _T1=findViewById(R.id._t1);
        _T1.setVisibility(View.GONE);


        Submit=findViewById(R.id.Submit);
        Submit.setVisibility(View.GONE);

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
        go();
    }

    private void go() {
        if(_PhoneNo==null){
            _PhoneNo="9999999999";
        }
        progressBar.setVisibility(View.VISIBLE);
         final ArrayList<Login> mItems = new ArrayList<Login>();
         final ArrayList<Login>mRequest=new ArrayList<>();
         final ArrayList<Login>mSyms=new ArrayList<>();
        StringRequest eventoReq = new StringRequest(com.android.volley.Request.Method.POST, Config_URL.GET_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        JSONObject jsonObj = null;
                        try {
                            jsonObj = new JSONObject(response);



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

                            if(mRequest.size()>0) {
                                _brandAdapter sAdapter = new _brandAdapter(RequestTest.this, mRequest);
                                sAdapter.notifyDataSetChanged();
                                Sosrv.setVisibility(View.VISIBLE);
                                Sosrv.setItemAnimator(new DefaultItemAnimator());
                                Sosrv.setAdapter(sAdapter);
                                Sosrv.setHasFixedSize(true);
                                LinearLayoutManager mHorizontal = new LinearLayoutManager(RequestTest.this, LinearLayoutManager.VERTICAL, false);
                                Sosrv.setLayoutManager(mHorizontal);

                            }
                            if (mItems.size() != 0) {
                                _userName.setText(mItems.get(0).getName(0));
                                _userGender.setText(mItems.get(0).getGender(0));
                                _userBday.setText(mItems.get(0).getBday(0));
                                PatientID.setText(mItems.get(0).getPatientID(0));
                            }

                            progressBar.setVisibility(View.GONE);

                        } catch (JSONException ex) {
                            ex.printStackTrace();
                        }

                    }



                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("HI", "Error: " + error.getMessage());
                Snackbar.make(getWindow().getDecorView().getRootView(), "Error! Please try later", Snackbar.LENGTH_LONG).show();

            }

        }){


            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("_mId", _PhoneNo);

                return params;
            }

        };

        // Añade la peticion a la cola
        AppController.getInstance().addToRequestQueue(eventoReq);





    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_white, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_refresh1) {

            overridePendingTransition(0, 0);
            startActivity(getIntent());
        }
        if(_PhoneNo!=null) {
            Intent o = new Intent(RequestTest.this, Account_settings.class);
            startActivity(o);
            overridePendingTransition(R.anim.slide_up1, R.anim.slide_down1);
        }else{
            Intent o = new Intent(RequestTest.this, ServiceOffer.class);
            startActivity(o);

            overridePendingTransition(R.anim.slide_up1, R.anim.slide_down1);
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

