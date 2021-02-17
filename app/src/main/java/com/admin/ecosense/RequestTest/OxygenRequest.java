package com.admin.ecosense.RequestTest;

import android.app.Activity;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
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
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.admin.ecosense.Activities.Account_settings;
import com.admin.ecosense.Activities.MainActivity;
import com.admin.ecosense.Adapters._symsAdapter;
import com.admin.ecosense.Login.ServiceOffer;
import com.admin.ecosense.POD.MonitorFamilyPOD;
import com.admin.ecosense.POD.PODConsultation;
import com.admin.ecosense.R;
import com.admin.ecosense.helper.Config_URL;
import com.admin.ecosense.helper.Login;
import com.admin.ecosense.helper.PrefManager;
import com.admin.ecosense.helper.Qns;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;

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
import java.util.concurrent.TimeUnit;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class OxygenRequest extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = OxygenRequest.class.getSimpleName();
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
    private PendingIntent pendingIntent;


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
        setContentView(R.layout.oxygenreport);
        coordinatorLayout=findViewById(R.id.em);
        toolbar = findViewById(R.id.toolbar_later_sos);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        _userName = findViewById(R.id.input_user_name);
        _userGender = findViewById(R.id.input_gender);
        _userBday = findViewById(R.id.input_bday);
        PatientID = findViewById(R.id.PatientID);
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





        Submit=findViewById(R.id.Submit);
        Submit.setOnClickListener(this);


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
            case R.id.Submit:
                if(!TextUtils.isEmpty(oxygen.getText().toString())) {
                    if(!TextUtils.isEmpty(breaths.getText().toString())) {
                        if(!TextUtils.isEmpty(temperature.getText().toString())) {
                            if(!TextUtils.isEmpty(pulse.getText().toString())) {
                                if (!OxygenRequest.this.isFinishing()) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(OxygenRequest.this, R.style.AlertDialogTheme)
                                            .setIcon(R.mipmap.ic_launcher)
                                            .setTitle("Are you sure?")
                                            .setMessage("You are sharing  health information with DigiHealth")
                                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    new PostConfirmationOxygen().execute();
                                                    dialog.cancel();
                                                }
                                            })
                                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.cancel();
                                                }
                                            });
                                    builder.show();
                                }

                            }else{
                                pulse.setError("Empty");
                                pulse.requestFocus();
                            }

                        }else{
                            temperature.setError("Empty");
                            temperature.requestFocus();
                        }
                    }else{
                        breaths.setError("Empty");
                        breaths.requestFocus();
                    }

                }else{
                    oxygen.setError("Empty");
                    oxygen.requestFocus();
                }
                break;
            case R.id.issue:
                issue.showDropDown();
                break;
            default:break;
        }


    }


    private class PostConfirmationOxygen extends AsyncTask<Void, Integer, String> {


        private String commaSeparatedValues = "";
        private boolean success = false;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();


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
                        .addFormDataPart("mobile", _PhoneNo)
                        .addFormDataPart("oxygen", oxygen.getText().toString())
                        .addFormDataPart("breaths", breaths.getText().toString())
                        .addFormDataPart("temperature", temperature.getText().toString())
                        .addFormDataPart("pulse", pulse.getText().toString())
                        .addFormDataPart("issue", issue.getText().toString())
                        .build();

                Request request = new Request.Builder()
                        .url(Config_URL.STORE_PULSE)
                        .post(requestBody)
                        .build();
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(120, TimeUnit.SECONDS)
                        .writeTimeout(120, TimeUnit.SECONDS)
                        .readTimeout(120, TimeUnit.SECONDS)
                        .build();
                okhttp3.Response response = client.newCall(request).execute();
                res = response.body().string();
                String[] pars = res.split("error");
                success = pars[1].contains("false");
                JSONObject jObj = null;

                String[] par = res.split("error");
                if (par[1].contains("false")) {
                    String[] pars_ = par[1].split("false,");
                    try {
                        jObj = new JSONObject("{".concat(pars_[1]));
                        JSONObject user = jObj.getJSONObject("user");
                        pref.setDigiCategory(user.getInt("category"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
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
                if(pref.getDigiCategory()!=0){
                    int k=pref.getDigiCategory();
                    String category = null;
                    if(k==1){
                        category="Digisurgery";
                    }else if(k==2){
                        category="Digiclinic";
                    }else if(k==3){
                        category="Digihospital";
                    }else if(k==4){
                        category="DigiICU";
                    }

                    pref.setOxygen(1);

                    if (!OxygenRequest.this.isFinishing()) {
                        ViewDialogFailedSuccess alert = new ViewDialogFailedSuccess();
                        if(pref.getResponsibility()==3) {
                            if (pref.getPODmobile() != null) {
                                alert.showDialog(OxygenRequest.this, "Based on  symptoms patient is  placed in  "+category, false);

                            }else{
                                alert.showDialog(OxygenRequest.this, "Based on your symptoms you are now placed in  "+category, false);

                            }
                        }else{
                            alert.showDialog(OxygenRequest.this, "Based on your symptoms you are now placed in  "+category, false);

                        }
                     //   alert.showDialog(OxygenRequest.this, "Based on your symptoms you are now placed in  "+category, false);
                    }
                }
            }else{
                Toast.makeText(getApplicationContext(),"Failed to store information",Toast.LENGTH_SHORT).show();
            }

        }
    }

    public class ViewDialogFailedSuccess {

        public void showDialog(Activity activity, String msg, Boolean noDate) {
            if (!activity.isFinishing()) {
                final Dialog dialog1 = new Dialog(activity);
                dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog1.setCancelable(false);
                dialog1.setContentView(R.layout.custom_dialog_success);
                TextView text = dialog1.findViewById(R.id.text_dialog);
                text.setText(msg);

                Button dialogButton = dialog1.findViewById(R.id.btn_dialog);



                dialogButton.setText("Ok");

                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(pref.getResponsibility()==3) {
                        }else{
                            pref.setAlarm(1);
                        }

                        Intent i = new Intent(OxygenRequest.this, MedicalTest.class);
                        startActivity(i);

                        overridePendingTransition(R.anim.slide_out2, R.anim.slide_in2);
                        dialog1.dismiss();
                    }
                });

                dialog1.show();


            }
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


