package com.admin.ecosense.Contacts;

import android.content.Intent;
import android.graphics.Color;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.admin.ecosense.Activities.Account_settings;
import com.admin.ecosense.Activities.MainActivity;
import com.admin.ecosense.Login.ServiceOffer;
import com.admin.ecosense.R;
import com.admin.ecosense.helper.AppController;
import com.admin.ecosense.helper.Config_URL;
import com.admin.ecosense.helper.PrefManager;
import com.admin.ecosense.helper.RecyclerTouchListener;
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

public class RequestedContacts extends AppCompatActivity {

    private static final String TAG = RequestedContacts.class.getSimpleName();
    private static final int RESULT_PICK_CONTACT = 101;
    private Toolbar toolbar;
    private double My_lat = 0, My_long = 0;
    private PrefManager pref;
    private String _PhoneNo;
    private String phoneNo;
    private String name;
    private RecyclerView Sosrv;
    private ArrayList<Contacts> mItems = new ArrayList<Contacts>();
    private String mobileIp;
    private ProgressBar progressBar;
    public boolean success;
    private CoordinatorLayout coordinatorLayout;
    private DatabaseReference mDatabase;
    private String _familyPhone;

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
        setContentView(R.layout.requested_contacts);
        coordinatorLayout=findViewById(R.id.em);
        toolbar = findViewById(R.id.toolbar_later_sos);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        Sosrv = findViewById(R.id.sosrv);

        progressBar=findViewById(R.id.progressBaremergency);
        progressBar.setVisibility(View.VISIBLE);

        pref = new PrefManager(getApplicationContext());
        HashMap<String, String> user = pref.getUserDetails();
        _PhoneNo = user.get(PrefManager.KEY_MOBILE);

        Intent i = getIntent();
        My_lat = i.getDoubleExtra("mylat", 0);
        My_long = i.getDoubleExtra("mylong", 0);
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
        mItems.clear();
        progressBar.setVisibility(View.VISIBLE);
        StringRequest eventoReq = new StringRequest(com.android.volley.Request.Method.POST,Config_URL.GET_CONTACTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.w("ccc", response.toString());
                        progressBar.setVisibility(View.GONE);
                        try{
                            JSONObject jsonObj = new JSONObject(response);

                            // Getting JSON Array node

                            JSONArray Contacts = jsonObj.getJSONArray("requested");

                            for (int j = 0; j < Contacts.length(); j++) {

                                JSONObject c = Contacts.getJSONObject(j);
                                Contacts item = new Contacts();
                                item.setID(c.getInt("ID"));
                                item.setContacts(c.getString("PhoneNo"));
                                item.setName(c.getString("Name"));
                                mItems.add(item);



                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (mItems.size() != 0) {
                            Sosrv.setVisibility(View.VISIBLE);
                            RequestedContactsAdapter sAdapter = new RequestedContactsAdapter(RequestedContacts.this, mItems);
                            sAdapter.notifyDataSetChanged();
                            sAdapter.setMobile(_PhoneNo);
                            sAdapter.setMyLat(My_lat, My_long);
                            Sosrv.setAdapter(sAdapter);
                            LinearLayoutManager mLayoutManager = new LinearLayoutManager(RequestedContacts.this);
                            mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                            Sosrv.setLayoutManager(mLayoutManager);

                            Sosrv.addOnItemTouchListener(
                                    new RecyclerTouchListener(RequestedContacts.this, Sosrv,
                                            new RecyclerTouchListener.OnTouchActionListener() {


                                                @Override
                                                public void onClick(View view, final int position) {
                                                    Log.w("gallery", String.valueOf(position));
                                                    if(mItems.get(position)!=null) {
                                                        _familyPhone=getLastTen(mItems.get(position).getContacts(position));;
                                                       // mDatabase.child(_familyPhone).addListenerForSingleValueEvent(new FirebaseDataListenerFamily());
                                                    }
                                                }

                                                @Override
                                                public void onRightSwipe(View view, int position) {

                                                }

                                                @Override
                                                public void onLeftSwipe(View view, int position) {

                                                }
                                            }));

                        } else {
                            Sosrv.setVisibility(View.GONE);

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
                params.put("_mId", _PhoneNo);
                return params;
            }
        };

        // Añade la peticion a la cola
        AppController.getInstance().addToRequestQueue(eventoReq);
    }


    public String getLastTen(String str) {

        return str.length() <= 10 ? str : str.substring(str.length() - 10);
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
            Intent o = new Intent(RequestedContacts.this, Account_settings.class);
            startActivity(o);
            overridePendingTransition(R.anim.slide_up1, R.anim.slide_down1);
        }else{
            Intent o = new Intent(RequestedContacts.this, ServiceOffer.class);
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
