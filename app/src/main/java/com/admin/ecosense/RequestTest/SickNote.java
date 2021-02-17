package com.admin.ecosense.RequestTest;

import android.app.UiModeManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.admin.ecosense.Activities.Account_settings;
import com.admin.ecosense.Activities.MainActivity;
import com.admin.ecosense.Adapters._symsAdapter;
import com.admin.ecosense.Login.ServiceOffer;
import com.admin.ecosense.R;
import com.admin.ecosense.helper.Config_URL;
import com.admin.ecosense.helper.Login;
import com.admin.ecosense.helper.PrefManager;
import com.admin.ecosense.helper.Qns;
import com.google.android.material.textfield.TextInputLayout;
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

public class SickNote extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = SickNote.class.getSimpleName();
    private static final int RESULT_PICK_CONTACT = 101;
    private static final int ALARM_REQUEST_CODE = 102;
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
    EditText _nameText;
    EditText _userName;
    EditText _bday;
    EditText PatientID;
    private EditText _userBday,reason,title;
    private EditText _userGender;
    private TextView _T1;
    private Button Submit;
    private String _text;
    private EditText oxygen;
    private EditText breaths;
    private AutoCompleteTextView issue;
    private String otherreasin;
    private TextInputLayout other;
    private EditText otherissues,temperature,pulse;
    private AppCompatCheckBox greater;
    private int _from=0;
    private LinearLayout L2;


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
        oxygen = findViewById(R.id.oxygen);
        breaths = findViewById(R.id.breaths);
        issue=findViewById(R.id.issue);
        issue.setOnClickListener(this);
        temperature=findViewById(R.id.temperature);
        pulse=findViewById(R.id.pulse);


        progressBar=findViewById(R.id.progressBaremergency);
        progressBar.setVisibility(View.VISIBLE);

        pref = new PrefManager(getApplicationContext());
        HashMap<String, String> user = pref.getUserDetails();
        if(pref.getResponsibility()==3) {
            if(pref.getPODmobile()!=null) {
                _PhoneNo = pref.getPODmobile();
            }else{
                _PhoneNo = user.get(PrefManager.KEY_MOBILE);
            }

        }else{
            _PhoneNo = user.get(PrefManager.KEY_MOBILE);
        }
        _Name = user.get(PrefManager.KEY_NAME);

        textView101=findViewById(R.id.textView101);
        textView102=findViewById(R.id.textView102);

        other=findViewById(R.id.other);
        otherissues=findViewById(R.id.otherissues);
        greater=findViewById(R.id.greater);
        greater.setVisibility(View.VISIBLE);

        L2=findViewById(R.id._L2);
        L2.setVisibility(View.GONE);




        textView101.setText(_text);
        textView102.setVisibility(View.GONE);

        textView101.setText("Symptoms ");

        _T1=findViewById(R.id._t1);
        _T1.setText("Please select  symptoms ");

        reason=findViewById(R.id.reason);
        title=findViewById(R.id.title);

        Submit=findViewById(R.id.Submit);


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
        Intent i=getIntent();
        _text=i.getStringExtra("name");
        _from=i.getIntExtra("from",0);
        go();
    }

    private void go() {
        mItems.clear();
        progressBar.setVisibility(View.VISIBLE);
        new GetUser().execute();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.issue:
                issue.showDropDown();
                break;
            default:break;
        }


    }


    private class GetUser extends AsyncTask<Void, Integer, String> {


        private ArrayList<Login> mItems = new ArrayList<Login>();
        private ArrayList<Login>mRequest=new ArrayList<>();
        private ArrayList<Login>mSyms=new ArrayList<>();
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            if(_PhoneNo==null){
                _PhoneNo="9999999999";
            }
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
                    JSONArray stats = jsonObj.getJSONArray("symtoms");
                    for (int i = 0; i < stats.length(); i++) {
                        JSONObject c = stats.getJSONObject(i);
                        if (!c.isNull("ID")) {

                            Login item = new Login();
                            item.setID(c.getInt("ID"));
                            item.setName(c.getString("Name"));
                            if(_from==0) {
                                if (!c.isNull("Client")) {
                                    item.setClient(c.getInt("Client"));
                                }
                            }
                        if(!c.isNull("otherreason") && !TextUtils.isEmpty("otherreason")){
                            otherreasin=c.getString("otherreason");
                        }
                            mSyms.add(item);


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
            if (mItems.size() != 0) {
                progressBar.setVisibility(View.GONE);
                _userName.setText(mItems.get(0).getName(0));
                _userGender.setText(mItems.get(0).getGender(0));
                _userBday.setText(mItems.get(0).getBday(0));
                PatientID.setText(mItems.get(0).getPatientID(0));
            }
            if(otherreasin!=null){
                other.setVisibility(View.VISIBLE);
                otherissues.setText(otherreasin);
            }else{
                other.setVisibility(View.GONE);
            }

            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                    getBaseContext(), android.R.layout.simple_list_item_1, getResources()
                    .getStringArray(R.array.issue));
            final String[] selection1 = new String[1];
            issue.setAdapter(arrayAdapter);
            issue.setCursorVisible(false);
            issue.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    selection1[0] = (String) parent.getItemAtPosition(position);

                }
            });

            if(mSyms.size()>0) {
                    _symsAdapter sAdapter = new _symsAdapter(SickNote.this, mSyms);
                    sAdapter.notifyDataSetChanged();
                    sAdapter.setEvery(_from);
                    sAdapter.setSubmit(Submit);
                    sAdapter.setGreater(greater);
                    sAdapter.setProgress(progressBar);
                    Sosrv.setVisibility(View.VISIBLE);
                    Sosrv.setItemAnimator(new DefaultItemAnimator());
                    Sosrv.setAdapter(sAdapter);
                    Sosrv.setHasFixedSize(true);
                    StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(getSpan(), StaggeredGridLayoutManager.VERTICAL);
                    Sosrv.setLayoutManager(mLayoutManager);
                }
        }

    }

    public int getSpan() {
        int orientation = getScreenOrientation(SickNote.this);
        if (isTV(SickNote.this)) return 4;
        if (isTablet(SickNote.this))
            return orientation == Configuration.ORIENTATION_PORTRAIT ? 2 : 2;
        return orientation == Configuration.ORIENTATION_PORTRAIT ? 2 : 2;
    }
    public static int getScreenOrientation(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels <
                context.getResources().getDisplayMetrics().heightPixels ?
                Configuration.ORIENTATION_PORTRAIT : Configuration.ORIENTATION_LANDSCAPE;
    }

    public static boolean isTV(Context context) {
        return ((UiModeManager) context
                .getSystemService(Context.UI_MODE_SERVICE))
                .getCurrentModeType() == Configuration.UI_MODE_TYPE_TELEVISION;
    }
    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
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
            Intent o = new Intent(SickNote.this, Account_settings.class);
            startActivity(o);
            overridePendingTransition(R.anim.slide_up1, R.anim.slide_down1);
        }else{
            Intent o = new Intent(SickNote.this, ServiceOffer.class);
            startActivity(o);

            overridePendingTransition(R.anim.slide_up1, R.anim.slide_down1);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode== KeyEvent.KEYCODE_BACK)   {
            finish();
            overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
        }
        return true;
    }



}

