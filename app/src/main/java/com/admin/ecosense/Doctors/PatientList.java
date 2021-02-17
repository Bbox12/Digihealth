package com.admin.ecosense.Doctors;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.admin.ecosense.Adapters.PatientDocAdapter;
import com.admin.ecosense.R;
import com.admin.ecosense.helper.AppController;
import com.admin.ecosense.helper.Config_URL;
import com.admin.ecosense.helper.Login;
import com.admin.ecosense.helper.PrefManager;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PatientList extends AppCompatActivity {
    private NetworkImageView service_pic;
    private TextView _name1,_details1,_description;
    private ImageView _i4,_arrow;
    private TextView orders,_address;
    private int _from=0;
    private RecyclerView moreRv;
    private PrefManager pref;
    private String _phoneNo;
    private CoordinatorLayout coordinatorLayout;
    private DecimalFormat df = new DecimalFormat("0.00");
    private ArrayList<String> _foods=new ArrayList<String>();
    private ArrayList<Login> MenuArray=new ArrayList<Login>();
    private ShimmerFrameLayout mShimmerViewContainer;
    private int _cost=0;
    private LinearLayout _L1;
    private Toolbar toolbar;
    private AppBarLayout appBarLayout;
    private TextView textView101,textView102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.waitingroom);
        pref=new PrefManager(getApplicationContext());
        HashMap<String, String> user=pref.getUserDetails();
        _phoneNo=user.get(PrefManager.KEY_MOBILE);
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        mShimmerViewContainer.setVisibility(View.VISIBLE);
        mShimmerViewContainer.startShimmerAnimation();

        toolbar = findViewById(R.id.toolbar_later_sos);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        toolbar.setTitle("");
        textView101=findViewById(R.id.textView101);
        textView102=findViewById(R.id.textView102);
        textView101.setText("Patient shared  ");
        textView102.setText("symptoms");


        coordinatorLayout=findViewById(R.id.em);
        moreRv=findViewById(R.id.sosrv);
        moreRv.setNestedScrollingEnabled(false);
        _L1=findViewById(R.id._L1);
        _L1.setVisibility(View.VISIBLE);




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

        checkMainProduct();

    }


    @Override
    public void onPause(){
        mShimmerViewContainer.stopShimmerAnimation();
        super.onPause();

    }

    @Override
    protected void onStop(){
        super.onStop();
        mShimmerViewContainer.stopShimmerAnimation();

    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }




    private void checkMainProduct() {
        Intent i=getIntent();
        _from=i.getIntExtra("from",0);
        final ArrayList<Login>mItems=new ArrayList<Login>();
        StringRequest eventoReq = new StringRequest(com.android.volley.Request.Method.POST, Config_URL.GET_LOGIN,
                new Response.Listener<String>() {
                    private JSONArray Eat;

                    @Override
                    public void onResponse(String response) {
                        Log.w("kk", response.toString());
                        MenuArray.clear();

                        try {
                            JSONObject jsonObj = new JSONObject(response);
                                Eat = jsonObj.getJSONArray("getpatientx");

                            if (Eat.length() != 0) {
                                for (int i = 0; i < Eat.length(); i++) {
                                    JSONObject c = Eat.getJSONObject(i);
                                    if (!c.isNull("Name")) {
                                        Login item = new Login();
                                        item.setID(c.getInt("ID"));
                                        item.setName(c.getString("Name"));
                                        item.set_Phone_no(c.getString("Photo"));
                                        item.setGender(c.getString("Gender"));
                                        item.setEmail(c.getString("Email"));
                                        item.setPhoto(c.getString("Photo"));
                                        item.setBday(c.getString("DOB"));
                                        item.setPatientID(c.getString("IDPatient"));
                                        item.setRiskFactor(c.getString("RiskFactor"));
                                        item.setWAQF(c.getString("WAQF"));
                                        item.setDocname(c.getString("Doc"));
                                        item.setDocMobile(c.getString("DocMobile"));
                                        item.setDigiCategory(c.getString("DigiCategory"));
                                        mItems.add(item);

                                    }
                                }

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(mItems.size()>0) {
                            PatientDocAdapter hadapter = new PatientDocAdapter(PatientList.this, mItems);
                            LinearLayoutManager mLayoutManager = new LinearLayoutManager(PatientList.this);
                            mLayoutManager.setOrientation(RecyclerView.VERTICAL);
                            hadapter.notifyDataSetChanged();
                            hadapter.setPref(pref);
                            moreRv.setVisibility(View.VISIBLE);
                            moreRv.setItemAnimator(new DefaultItemAnimator());
                            moreRv.setAdapter(hadapter);
                            moreRv.setHasFixedSize(true);
                            moreRv.setLayoutManager(mLayoutManager);
                        }
                        populate();
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
                params.put("ID","1");
                params.put("submenu", String.valueOf(_from));
                return params;
            }

        };

        eventoReq.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(eventoReq);

    }


    private void populate() {

        if(mShimmerViewContainer.isAnimationStarted()) {
            mShimmerViewContainer.stopShimmerAnimation();
            mShimmerViewContainer.setVisibility(View.GONE);
        }





    }

    private void vollyError(VolleyError error) {

        if (error instanceof TimeoutError || error instanceof NoConnectionError) {

            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "Network is unreachable. Please try another time", Snackbar.LENGTH_LONG)
                    .setAction("Refresh", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            overridePendingTransition(0, 0);
                            startActivity(getIntent());
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

                            overridePendingTransition(0, 0);
                            startActivity(getIntent());
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

                            overridePendingTransition(0, 0);
                            startActivity(getIntent());
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

                            overridePendingTransition(0, 0);
                            startActivity(getIntent());
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

                            overridePendingTransition(0, 0);
                            startActivity(getIntent());
                        }
                    });
            snackbar.setActionTextColor(Color.RED);
            snackbar.show();
        }


    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);

        }
        return true;
    }

}
