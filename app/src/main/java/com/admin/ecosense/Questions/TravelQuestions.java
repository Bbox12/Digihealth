package com.admin.ecosense.Questions;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.admin.ecosense.Activities.Account_settings;
import com.admin.ecosense.Activities.MainActivity;
import com.admin.ecosense.Activities.Splash_screen;
import com.admin.ecosense.R;
import com.admin.ecosense.helper.AppController;
import com.admin.ecosense.helper.Config_URL;
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
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TravelQuestions extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {
    private SwipeRefreshLayout swipeRefreshLayout;
    private CoordinatorLayout coordinatorLayout;
    private DatabaseReference mDatabase;
    private PrefManager pref;
    private String _PhoneNo;
    private TextInputEditText country,region,flightdetails,seat,arrivaldate,departuredate,accomodation,otherdetails,carrier,seat2,departedfrom,arrivein
            ,otherdetails2;
    private Button Submit;
    private AppCompatCheckBox flightyes,othertravel;
    private LinearLayout _L1,_L2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.travelquestions);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        toolbar.setTitle("Questionnaire");
        coordinatorLayout=findViewById(R.id.coordinatorLayout2);
        pref=new PrefManager(getApplicationContext());
        HashMap<String, String> user = pref.getUserDetails();
        _PhoneNo = user.get(PrefManager.KEY_MOBILE);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        swipeRefreshLayout =  findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setRefreshing(true);
        country=findViewById(R.id.country);
        region=findViewById(R.id.region);
        flightdetails=findViewById(R.id.flightdetails);
        seat=findViewById(R.id.seat);
        arrivaldate=findViewById(R.id.arrivaldate);
        departuredate=findViewById(R.id.departuredate);
        accomodation=findViewById(R.id.accomodation);
        otherdetails=findViewById(R.id.otherdetails);
        carrier=findViewById(R.id.carrier);
        seat2=findViewById(R.id.seat2);
        departedfrom=findViewById(R.id.departedfrom);
        arrivein=findViewById(R.id.arrivein);
        otherdetails2=findViewById(R.id.otherdetails2);
        Submit=findViewById(R.id.submit);
        Submit.setOnClickListener(this);

        flightyes=findViewById(R.id._flightyes);
        othertravel=findViewById(R.id.othertravel);

        _L1=findViewById(R.id._L1);
        _L2=findViewById(R.id._L2);
        othertravel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    carrier.setEnabled(true);
                    seat2.setEnabled(true);
                    departedfrom.setEnabled(true);
                    arrivein.setEnabled(true);
                    otherdetails2.setEnabled(true);

                }else{
                    carrier.setEnabled(false);
                    seat2.setEnabled(false);
                    departedfrom.setEnabled(false);
                    arrivein.setEnabled(false);
                    otherdetails2.setEnabled(false);
                }
            }
        });

        flightyes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                 flightdetails.setEnabled(true);
                 seat.setEnabled(true);
                 arrivaldate.setEnabled(true);
                 departuredate.setEnabled(true);
                 accomodation.setEnabled(true);
                 otherdetails.setEnabled(true);
                }else{
                    flightdetails.setEnabled(false);
                    seat.setEnabled(false);
                    arrivaldate.setEnabled(false);
                    departuredate.setEnabled(false);
                    accomodation.setEnabled(false);
                    otherdetails.setEnabled(false);
                }
            }
        });


        swipeRefreshLayout.post(
                new Runnable() {
                    @Override
                    public void run() {
                        getInbox();
                    }
                }
        );
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);


            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();






    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.submit:
                if(!TextUtils.isEmpty(country.getText().toString()) && !TextUtils.isEmpty(region.getText().toString())  ){
                    String _flightdetails,_seat1,_arrivaldate,_depaturedate,_accomodation,_otherdetails1,Carrier,seat2,Departurefrom,arrivein,_otherdetails2;

                    StringRequest eventoReq = new StringRequest(com.android.volley.Request.Method.POST, Config_URL.TRAVELQUESTIONS,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    Log.w("details", response);

                                    final ArrayList<Qns> mLinks=new ArrayList<Qns>();
                                    try {

                                        JSONObject jsonObj = new JSONObject(response);


                                        JSONArray category = jsonObj.getJSONArray("questions");
                                        for (int i = 0; i < category.length(); i++) {
                                            JSONObject c = category.getJSONObject(i);
                                            if (!c.isNull("Question")) {
                                                Qns item=new Qns();
                                                item.setImp(c.getString("Category"));
                                                item.setVersion(c.getString("Question"));
                                                mLinks.add(item);
                                            }
                                        }


                                        if (mLinks.size() > 2) {
                                            swipeRefreshLayout.setRefreshing(false);
                                        }else{
                                            Snackbar snackbar = Snackbar
                                                    .make(coordinatorLayout, "No information found!", Snackbar.LENGTH_SHORT)
                                                    ;
                                            snackbar.setActionTextColor(Color.RED);
                                            snackbar.show();
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
                            params.put("_mId", "1");
                            return params;
                        }

                    };
                    AppController.getInstance().addToRequestQueue(eventoReq);
                }
                break;


        }
    }

    private void getInbox() {


        StringRequest eventoReq = new StringRequest(com.android.volley.Request.Method.POST, Config_URL.GET_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.w("details", response);

                        final ArrayList<Qns> mLinks=new ArrayList<Qns>();
                        try {

                            JSONObject jsonObj = new JSONObject(response);


                            JSONArray category = jsonObj.getJSONArray("questions");
                            for (int i = 0; i < category.length(); i++) {
                                JSONObject c = category.getJSONObject(i);
                                if (!c.isNull("Question")) {
                                    Qns item=new Qns();
                                    item.setImp(c.getString("Category"));
                                    item.setVersion(c.getString("Question"));
                                    mLinks.add(item);
                                }
                            }


                            if (mLinks.size() > 2) {
                                swipeRefreshLayout.setRefreshing(false);
                            }else{
                                Snackbar snackbar = Snackbar
                                        .make(coordinatorLayout, "No information found!", Snackbar.LENGTH_SHORT)
                                        ;
                                snackbar.setActionTextColor(Color.RED);
                                snackbar.show();
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
                params.put("_mId", "1");
                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(eventoReq);
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
        if (item.getItemId() == R.id.action_notification) {
            Intent o = new Intent(TravelQuestions.this, Account_settings.class);
            startActivity(o);
            overridePendingTransition(R.anim.slide_up1, R.anim.slide_down1);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        // swipe refresh is performed, fetch the messages again
        getInbox();
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