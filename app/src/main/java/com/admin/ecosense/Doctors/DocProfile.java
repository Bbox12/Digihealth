package com.admin.ecosense.Doctors;

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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.admin.ecosense.Login.ServiceOffer;
import com.admin.ecosense.R;
import com.admin.ecosense.helper.Config_URL;
import com.admin.ecosense.helper.Login;
import com.admin.ecosense.helper.PrefManager;
import com.admin.ecosense.helper.Qns;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
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

public class DocProfile extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = DocProfile.class.getSimpleName();
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
    private String _newPlace = "", _newBday = "", _newGender = "", _newEmail="", _newName="",_patientID="";
    private TextView textView101,textView102,covidstatus;
    EditText _nameText;
    EditText _userName;
    EditText _bday;
    EditText PatientID;
    private EditText _userBday,input_email;
    private EditText _userGender;
    private LinearLayout _L1;
    private TextInputLayout _Nightingale;
    private  EditText Nightingale,input_passport,input_user_mobile;

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
        setContentView(R.layout.docprofile);
        coordinatorLayout=findViewById(R.id.coll);
        toolbar = findViewById(R.id.toolbar_mode);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        _userName = findViewById(R.id.input_user_name);
        _userGender = findViewById(R.id.input_gender);
        _userBday = findViewById(R.id.input_bday);
        PatientID = findViewById(R.id.PatientID);
        input_user_mobile=findViewById(R.id.input_user_mobile);
        Sosrv = findViewById(R.id.sosrv);
//        _t1=findViewById(R.id._t1);
        input_passport=findViewById(R.id.input_passport);
        Nightingale=findViewById(R.id.Nightingale);
        _Nightingale=findViewById(R.id._Nightingale);

        progressBar=findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.VISIBLE);

        pref = new PrefManager(getApplicationContext());
        HashMap<String, String> user = pref.getUserDetails();
        _PhoneNo = user.get(PrefManager.KEY_MOBILE);
        _Name = user.get(PrefManager.KEY_NAME);

        input_email=findViewById(R.id.input_email);

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


        go();
    }

    private void go() {
        mItems.clear();
        new DocProfile.GetUser().execute();

    }

    @Override
    public void onClick(View v) {
    if(v.getId()==R.id.submit){
        boolean ok = true;
        if (!TextUtils.isEmpty(_userGender.getText().toString())) {
            _newGender = _userGender.getText().toString();
        }else{
            ok = false;
            _userGender.setError("Empty");
        }



        if (!TextUtils.isEmpty(_userBday.getText().toString())) {
            _newBday = _userBday.getText().toString();
        }else{
            ok = false;
            _userBday.setError("Empty");
        }


        if (!TextUtils.isEmpty(_userName.getText().toString())) {
            _newName = _userName.getText().toString();
        } else {
            ok = false;
            _userName.setError("Empty");
        }

        if (!TextUtils.isEmpty(PatientID.getText().toString())) {
            _patientID = PatientID.getText().toString();
        } else {
            ok = false;
            PatientID.setError("Empty");
        }


        if (!TextUtils.isEmpty(input_passport.getText().toString())) {
        } else {
            ok = false;
            input_passport.setError("Empty");
        }

        if (ok) {
            new PostSignUpData().execute();
        }

    }
    }

    public String getLastTen(String str) {
        str=str.replaceAll("\\s+","");
        return str.length() <= 10 ? str : str.substring(str.length() - 10);
    }

    private class PostSignUpData extends AsyncTask<Void, Integer, String> {


        private boolean success;
        private File destination;
        private String _fileImage;
        private String ten;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            ten=getLastTen(input_user_mobile.getText().toString());
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
                        .addFormDataPart("name", _newName)
                        .addFormDataPart("email", _newEmail)
                        .addFormDataPart("gender", _newGender)
                        .addFormDataPart("patient", _patientID)
                        .addFormDataPart("passport", input_passport.getText().toString())
                        .addFormDataPart("bday", _newBday)
                        .addFormDataPart("mobile", ten)
                        .addFormDataPart("IP",mobileIp)
                        .build();

                Request request = new Request.Builder()
                        .url(Config_URL.UPDATE_PROFILE)
                        .post(requestBody)
                        .build();
                OkHttpClient client = new OkHttpClient();
                okhttp3.Response response = client.newCall(request).execute();
                res = response.body().string();
                String[] pars = res.split("error");
                success = pars[1].contains("false");

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
            if (success) {
                Toast.makeText(getApplicationContext(),"Successfully registered",Toast.LENGTH_SHORT).show();
                if(!TextUtils.isEmpty(_newBday)) {
                    pref.setDOB(_newBday);
                }

                overridePendingTransition(0, 0);
                startActivity(getIntent());
            } else {
                Snackbar.make(getWindow().getDecorView().getRootView(), "Server Error!Please try later.", Snackbar.LENGTH_LONG).show();

            }

        }

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
                            item.setInput_passport(c.getString("Passport"));
                            item.setUniqueID(c.getString("UniqueID"));
                            mItems.add(item);
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
            progressBar.setVisibility(View.GONE);
            if (mItems.size() != 0) {
                progressBar.setVisibility(View.GONE);
                input_user_mobile.setText(mItems.get(0).get_Phone_no(0));
                if (!mItems.get(0).getEmail(0).contains("null") && !TextUtils.isEmpty(mItems.get(0).getEmail(0))) {
                    input_email.setText(mItems.get(0).getEmail(0));
                }
               // input_email.setText(mItems.get(0).getEmail(0));
                input_passport.setText(mItems.get(0).getUniqueID(0));
                _userName.setText(mItems.get(0).getName(0));
                _userGender.setText(mItems.get(0).getGender(0));
                _userBday.setText(mItems.get(0).getBday(0));
                Nightingale.setText(mItems.get(0).getPatientID(0));
                Nightingale.setEnabled(false);

            }
        }

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

