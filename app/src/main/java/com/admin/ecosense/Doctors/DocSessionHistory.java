package com.admin.ecosense.Doctors;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.admin.ecosense.Activities.Account_settings;
import com.admin.ecosense.Adapters.docsessionAdapter;
import com.admin.ecosense.Login.ServiceOffer;
import com.admin.ecosense.R;
import com.admin.ecosense.helper.AppController;
import com.admin.ecosense.helper.Config_URL;
import com.admin.ecosense.helper.Login;
import com.admin.ecosense.helper.PrefManager;
import com.admin.ecosense.helper.Qns;
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
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class DocSessionHistory extends AppCompatActivity {

    private static final String TAG = StartSession.class.getSimpleName();
    private static final int RESULT_PICK_CONTACT = 101;
    private static final int REQUEST_CAMERA = 1011;
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
    private TextView textView101,textView102;
    private static final int SELECT_FILE = 102;
    private Uri selectedImageUri;
    private String selectedVideoPath="";
    private TextView _filename;
    private ProgressDialog mProgressDialog;
    private RequestQueue rQueue;
    private TextView _filenames;
    private Button _upload2;
    private Button _cancel;
    private boolean _tpermission=false,permissionsAllowd=false;
    public static final int MULTIPLE_PERMISSIONS = 101;
    private RelativeLayout layoutBottomSheet2;
    BottomSheetBehavior sheetBehavior2;
    private  TextView _fileupload,date;
    private AppCompatEditText description,_date_submit,_time_submit;
    private Button _choose,upload,cacel;
    SimpleDateFormat simpleDateFormat =
            new SimpleDateFormat("yyy-MM-dd");
    private EditText Search_2;
    private ListView listView;
    private FrameLayout fabhistory;
    private ArrayList<Login> mDocs = new ArrayList<Login>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.sessionhistory);
        coordinatorLayout=findViewById(R.id.em);
        toolbar = findViewById(R.id.toolbar_later_sos);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Sosrv = findViewById(R.id.sosrv);
        _t1=findViewById(R.id._t1);
        Search_2=findViewById(R.id.search);
        Search_2.setHint("Search by patient ID");

        progressBar=findViewById(R.id.progressBaremergency);
        progressBar.setVisibility(View.GONE);

        pref = new PrefManager(getApplicationContext());
        HashMap<String, String> user = pref.getUserDetails();
        _PhoneNo = user.get(PrefManager.KEY_MOBILE);
        _Name = user.get(PrefManager.KEY_NAME);

        textView101=findViewById(R.id.textView101);
        textView102=findViewById(R.id.textView102);

        textView101.setText("Session ");
        textView102.setText("History");

        listView=findViewById(R.id.listView);

        fabhistory =  findViewById(R.id.fabhistory);
        fabhistory.setVisibility(View.VISIBLE);

        fabhistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent o = new Intent(DocSessionHistory.this, DocAllAptientz.class);
                o.putExtra("from", 1);
                startActivity(o);
                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
            }
        });


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
        new GetUser().execute();
        go2(0);

        Search_2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence cs, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.toString().length()>1) {
                    listView.setVisibility(View.VISIBLE);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            final ArrayList<Login> filteredModelList = new ArrayList<>();
                            Search_2.clearFocus();
                            InputMethodManager in = (InputMethodManager)getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                            in.hideSoftInputFromWindow(Search_2.getWindowToken(), 0);
                            listView.setVisibility(View.GONE);
                            for (int i = 0; i < mItems.size(); i++) {
                                Login model = mDocs.get(i);
                                final String text = model.getName(i).toLowerCase();
                                String value = (String) parent.getItemAtPosition(position);
                                if (text.contains(value.toLowerCase())) {
                                    go2(model.getIDPatient(i));
                                }
                            }


                        }
                    });
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }


    private class GetUser extends AsyncTask<Void, Integer, String> {



        final ArrayList<String> mName=new ArrayList<String>();
        private String _ID;


        protected String doInBackground(Void... args) {
            return uploadFile();
        }

        private String uploadFile() {
            // TODO Auto-generated method stub
            String res = null;

            try {

                mDocs.clear();
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
                try {
                    JSONObject jsonObj = new JSONObject(res);


                    JSONArray consultation = jsonObj.getJSONArray("patienthistory");
                    if(consultation.length()>0) {
                        for (int i = 0; i < consultation.length(); i++) {
                            JSONObject c = consultation.getJSONObject(i);
                            if (!c.isNull("Name")) {
                                Login item=new Login();
                                item.setID(c.getInt("ID"));
                                mName.add(c.getString("Name"));
                                item.setName(c.getString("Name"));
                                item.setPatientID(c.getString("IDPatient"));
                                mDocs.add(item);
                            }

                        }
                    }


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
            if (mName.size() != 0) {
                Set<String> set = new HashSet<>(mName);
                mName.clear();
                mName.addAll(set);
            }


            final ArrayAdapter<String> serarchadapter = new ArrayAdapter<String>(DocSessionHistory.this, R.layout.list_item, R.id.product_name, mName);
            listView.setAdapter(serarchadapter);


        }

    }


    private void go2(final int Patient) {
        mItems.clear();
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
                            if(Patient!=0) {
                                JSONArray Contacts = jsonObj.getJSONArray("docstartsessionhistory");

                                if (Contacts.length() > 0) {

                                    for (int j = 0; j < Contacts.length(); j++) {

                                        JSONObject c = Contacts.getJSONObject(j);
                                        Qns item = new Qns();
                                        item.setID(c.getInt("ID"));
                                        item.setDescription(c.getString("Description"));
                                        item.setNextDate(c.getString("NextDate"));
                                        item.setNextTime(c.getString("NextTime"));
                                        item.setPhoto(c.getString("Photo"));
                                        item.setDate(c.getString("Date"));
                                        item.setTime(c.getString("Time"));
                                        item.setName(c.getString("Name"));
                                        mItems.add(item);
                                    }
                                }
                            }else{
                                JSONArray Contacts = jsonObj.getJSONArray("docsessiondefaults");

                                if (Contacts.length() > 0) {

                                    for (int j = 0; j < Contacts.length(); j++) {

                                        JSONObject c = Contacts.getJSONObject(j);
                                        Qns item = new Qns();
                                        item.setID(c.getInt("ID"));
                                        item.setDescription(c.getString("Description"));
                                        item.setNextDate(c.getString("NextDate"));
                                        item.setNextTime(c.getString("NextTime"));
                                        item.setPhoto(c.getString("Photo"));
                                        item.setDate(c.getString("Date"));
                                        item.setTime(c.getString("Time"));
                                        item.setName(c.getString("Name"));
                                        mItems.add(item);
                                    }
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (mItems.size() != 0) {
                            Sosrv.setVisibility(View.VISIBLE);
                            _t1.setVisibility(View.GONE);
                            docsessionAdapter sAdapter = new docsessionAdapter(DocSessionHistory.this, mItems);
                            sAdapter.notifyDataSetChanged();
                            sAdapter.setFrom(1);
                            Sosrv.setAdapter(sAdapter);
                            LinearLayoutManager mLayoutManager = new LinearLayoutManager(DocSessionHistory.this);
                            mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                            Sosrv.setLayoutManager(mLayoutManager);

                        } else {
                            Sosrv.setVisibility(View.GONE);
                            _t1.setVisibility(View.VISIBLE);
                            _t1.setText("No session found!");

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
                                    recreate();
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
                                    recreate();
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
                                    recreate();
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
                                    recreate();
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
                                    recreate();
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
                params.put("_mId",_PhoneNo);
                params.put("patient", String.valueOf(Patient));
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
        if (item.getItemId() == R.id.action_notification) {
            if(_PhoneNo!=null) {
                Intent o = new Intent(DocSessionHistory.this, Account_settings.class);
                startActivity(o);

                overridePendingTransition(R.anim.slide_up1, R.anim.slide_down1);
            }else {
                Intent o = new Intent(DocSessionHistory.this, ServiceOffer.class);
                startActivity(o);

                overridePendingTransition(R.anim.slide_up1, R.anim.slide_down1);
            }
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

