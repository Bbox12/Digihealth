package com.admin.ecosense.Activities;

/**
 * Created by parag on 02/02/18.
 */

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.admin.ecosense.Doctors.Doctorwindow;
import com.admin.ecosense.Login.ServiceOffer;
import com.admin.ecosense.POD.MonitorFamilyPOD;
import com.admin.ecosense.Questions.Consultation;
import com.admin.ecosense.R;
import com.admin.ecosense.helper.Config_URL;
import com.admin.ecosense.helper.Login;
import com.admin.ecosense.helper.PrefManager;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;


public class Account_settings extends AppCompatActivity implements View.OnClickListener {
    private static final int MY_PERMISSIONS_REQUEST_USE_CAMERA = 1001;
    private static String TAG = Account_settings.class.getSimpleName();
    EditText _nameText;
    EditText _emailText;
    EditText _userName;
    EditText _userMobile;
    EditText input_passport;
    EditText PatientID;
    private CoordinatorLayout coordinatorLayout;
    private Toolbar toolbar;
    private PrefManager pref;
    private ProgressBar progressBar;
    private AppBarLayout App_bar;
    private CollapsingToolbarLayout Coll;
    private EditText _userBday;
    private AutoCompleteTextView _userGender;
    private String _newPlace = "", _newBday = "", _newGender = "", _newEmail="", _newName="",_patientID="";
    private ArrayList<String> mIns = new ArrayList<String>();
    private String _userPhone;
    private String _url;
    private String mobileIp;
    private Button _submit;
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String vverificationId;
    private int _from=0;
    private  EditText inputemergencyname,inputemergencyno,Nightingale,podofficer,relation;
    private static final int RESULT_PICK_CONTACT = 101;
    private boolean _getImage;
    private TextInputLayout _Nightingale;
    private TextView textView101;

    private static boolean isvalidename(String name) {
        boolean check = false;
        String specialCharacters = " !#$%&'()*+,-./:;<=>?@[]^_`{|}~0123456789";
        String str2[] = name.split("");

        for (int i = 0; i < str2.length; i++) {
            check = !specialCharacters.contains(str2[i]);
        }
        return check;

    }

    public static String getMobileIPAddress() {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        return  addr.getHostAddress();
                    }
                }
            }
        } catch (Exception ignored) { } // for now eat exceptions
        return null;
    }

    public String getWifiIPAddress() {
        WifiManager wifiMgr = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
        int ip = wifiInfo.getIpAddress();
        return  Formatter.formatIpAddress(ip);
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_settings);


        coordinatorLayout = findViewById(R.id
                .coll);
        progressBar = findViewById(R.id.progressBar2);
        toolbar = findViewById(R.id.toolbar_mode);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        pref = new PrefManager(getApplicationContext());
        HashMap<String, String> user = pref.getUserDetails();
        _userPhone = user.get(PrefManager.KEY_MOBILE);
        _emailText = findViewById(R.id.input_email);
        _userName = findViewById(R.id.input_user_name);
        _userMobile = findViewById(R.id.input_user_mobile);
        _userGender = findViewById(R.id.input_gender);
        _userBday = findViewById(R.id.input_bday);
        _userBday.setOnClickListener(this);
         PatientID = findViewById(R.id.PatientID);
        _submit=findViewById(R.id.submit);
        _submit.setOnClickListener(this);
        inputemergencyname=findViewById(R.id.inputemergencyname);
        inputemergencyno=findViewById(R.id.inputemergencyno);
        inputemergencyname.setOnClickListener(this);
        textView101=findViewById(R.id.textView101);
        input_passport=findViewById(R.id.input_passport);
        Nightingale=findViewById(R.id.Nightingale);
        _Nightingale=findViewById(R.id._Nightingale);
        podofficer=findViewById(R.id.podofficer);
        relation=findViewById(R.id.relation);

        Intent i=getIntent();
        _from=i.getIntExtra("from",0);
        if(_from!=0 ){
            getSupportActionBar().setTitle("");
            toolbar.setTitle("");
            textView101.setText("Details ");
            _submit.setVisibility(View.GONE);
        }else {
            if(pref.getResponsibility()==2){
                Nightingale.setVisibility(View.GONE);
                _Nightingale.setVisibility(View.GONE);
                podofficer.setVisibility(View.GONE);
                relation.setVisibility(View.GONE);
            }
        }



        initCollapsingToolbar();
        _userGender.setOnClickListener(this);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
            }
        });

        mobileIp = getMobileIPAddress();
        if(TextUtils.isEmpty(mobileIp)){
            mobileIp= getWifiIPAddress();
        }






    }

    @Override
    protected void onResume() {
        super.onResume();


            mIns.clear();
        if(!_getImage) {
            new GetUser().execute();
        }


            _userMobile.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start,
                                          int before, int count) {
                    if(s.toString().length()==10){
                    //    _userMobile.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.ic_cor, 0);
                        _emailText.requestFocus();

                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.submit:

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

                    if (!TextUtils.isEmpty(inputemergencyname.getText().toString())) {
                    } else {
                        ok = false;
                        inputemergencyname.setError("Empty");
                    }
                if (!TextUtils.isEmpty(input_passport.getText().toString())) {
                } else {
                    ok = false;
                    input_passport.setError("Empty");
                }

                    if (ok) {
                            new PostSignUpData().execute();
                    }


                break;

            case R.id.input_gender:
                _userGender.showDropDown();
                break;


            case R.id.inputemergencyname:
                pickContact(v);
                break;



            case R.id.input_bday:
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                if (dayOfMonth > 9) {
                                    _userBday.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                                } else {
                                    _userBday.setText(year + "-" + (monthOfYear + 1) + "-" + "0" + dayOfMonth);
                                }
                            }
                        }, mYear - 18, mMonth, mDay);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                datePickerDialog.show();
                break;


            default:
                break;
        }
    }


    public void pickContact(View v) {
        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
        startActivityForResult(contactPickerIntent, RESULT_PICK_CONTACT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // check whether the result is ok
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            // Check for the request code, we might be usign multiple startActivityForReslut
            switch (requestCode) {
                case RESULT_PICK_CONTACT:
                    contactPicked(data);
                    break;
            }
        } else {
            Log.e("MainActivity", "Failed to pick contact");
        }
    }

    /**
     * Query the Uri and read contact details. Handle the picked contact data.
     *
     * @param data
     */
    private void contactPicked(Intent data) {
        Cursor cursor = null;
        try {

            // getData() method will have the Content Uri of the selected contact
            Uri uri = data.getData();
            //Query the content uri
            cursor = getContentResolver().query(uri, null, null, null, null);
            cursor.moveToFirst();
            // column index of the phone number
            int phoneIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            // column index of the contact name
            int nameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            String phoneNo = (cursor.getString(phoneIndex)).trim();
            inputemergencyname.setText((cursor.getString(nameIndex)));
            if (phoneNo.contains("+")) {
                String[] pars = phoneNo.split("\\+");
                phoneNo = pars[1];

            }
            inputemergencyno.setText(phoneNo);
            _getImage=true;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                findViewById(R.id.toolbar_layout);
        AppBarLayout appBarLayout = findViewById(R.id.app_bar_main);
        appBarLayout.setExpanded(true);


    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    public void signup(final String org) {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
        }


    }



    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();


    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String uName = _userName.getText().toString();


        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

          return valid;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Intent i=getIntent();
        _from=i.getIntExtra("from",0);
        if(_from==0) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_logout, menu);
        }
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            if (!Account_settings.this.isFinishing()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Account_settings.this, R.style.AlertDialogTheme)
                        .setIcon(R.mipmap.ic_launcher)
                        .setTitle("Are you sure to logout?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                pref.clearSession();
                                pref.createLogin(null,null);
                                pref.setClockIn(0);
                                pref.setDOB(null);
                                Intent i = new Intent(Account_settings.this, ServiceOffer.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(i);

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

           return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private class GetUser extends AsyncTask<Void, Integer, String> {


        private ArrayList<Login> mItems = new ArrayList<Login>();
        private String _ID;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            Intent i=getIntent();
            _from=i.getIntExtra("from",0);

            if(_from!=0 && pref.getWorkID()!=0){
             _userPhone=String.valueOf(pref.getWorkID());
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
                        .addFormDataPart("_mId", _userPhone)
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
                            item.setEmergencyName(c.getString("Emergencyname"));
                            item.setEmergencyNo(c.getString("Emergencyno"));
                            item.setWAQF(c.getString("WAQF"));
                            item.setUniqueID(c.getString("UniqueID"));
                            item.setPodName(c.getString("PodOwnerName"));
                            item.setRelation(c.getString("Relation"));
                            mItems.add(item);
                        }

                    }

                    JSONArray maxid = jsonObj.getJSONArray("maxid");
                    for (int i = 0; i < maxid.length(); i++) {
                        JSONObject c = maxid.getJSONObject(i);
                        if (!c.isNull("ID")) {
                           _ID=c.getString("ID");
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
                _userName.setText(mItems.get(0).getName(0));
                _userMobile.setText(mItems.get(0).get_Phone_no(0));
                if(!mItems.get(0).getEmail(0).contains("null")) {
                    _emailText.setText(mItems.get(0).getEmail(0));
                }

                if(!mItems.get(0).getUniqueID(0).contains("null") && !TextUtils.isEmpty(mItems.get(0).getUniqueID(0))) {
                    input_passport.setText(mItems.get(0).getUniqueID(0));
                }else {
                    if (!mItems.get(0).getInput_passport(0).contains("null") && !TextUtils.isEmpty(mItems.get(0).getInput_passport(0))) {
                        input_passport.setText(mItems.get(0).getInput_passport(0));
                    }
                }
                if(!mItems.get(0).getBday(0).contains("0000-00-00")) {
                    _userBday.setText(mItems.get(0).getBday(0));
                }
                _userMobile.setEnabled(false);
                _userGender.setText(mItems.get(0).getGender(0));
                inputemergencyname.setText(mItems.get(0).getEmergencyName(0));
                inputemergencyno.setText(mItems.get(0).getEmergencyNo(0));

                if(!mItems.get(0).getPodName(0).contains("null") && !TextUtils.isEmpty(mItems.get(0).getPodName(0))) {
                    podofficer.setText(mItems.get(0).getPodName(0));
                    relation.setText(mItems.get(0).getRelation(0));
                }else {
                    podofficer.setVisibility(View.GONE);
                    relation.setVisibility(View.GONE);
                }



                if (!TextUtils.isEmpty(mItems.get(0).getPatientID(0)) && mItems.get(0).getPatientID(0) != null) {
                    PatientID.setText(mItems.get(0).getPatientID(0));
                } else {
                    if (_ID != null) {
                        PatientID.setText(_ID);
                    }
                }
                if (!TextUtils.isEmpty(mItems.get(0).getWAQF(0)) && mItems.get(0).getWAQF(0) != null) {
                    Nightingale.setText(mItems.get(0).getWAQF(0));
                }else{
                    _Nightingale.setVisibility(View.GONE);
                }


            }else{
                if (_ID != null) {
                    PatientID.setText(_ID);
                }
            }


            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                    getBaseContext(), android.R.layout.simple_list_item_1, getResources()
                    .getStringArray(R.array.Gender_Names));
            final String[] selection1 = new String[1];
            _userGender.setAdapter(arrayAdapter);
            _userGender.setCursorVisible(false);
            _userGender.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    _userGender.showDropDown();
                    selection1[0] = (String) parent.getItemAtPosition(position);

                }
            });

        }

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
             ten=(_userMobile.getText().toString());

            Intent i=getIntent();
            _from=i.getIntExtra("from",0);
            if(_from!=0 && i.getStringExtra("mobile")!=null){
                _userPhone=i.getStringExtra("mobile");
            }
            _newEmail=_emailText.getText().toString();
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
                        .addFormDataPart("emergencyname", inputemergencyname.getText().toString())
                        .addFormDataPart("emergencyno", inputemergencyno.getText().toString())
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
                if(_from==0) {
                    Toast.makeText(getApplicationContext(), "Successfully registered", Toast.LENGTH_SHORT).show();
                    if (!TextUtils.isEmpty(_newBday)) {
                        pref.setDOB(_newBday);
                    }
                    if (pref.getResponsibility() == 1) {
                        Intent o = new Intent(Account_settings.this, MainActivity.class);
                        startActivity(o);
                        overridePendingTransition(R.anim.slide_up1, R.anim.slide_down1);

                    } else if (pref.getResponsibility() == 2) {
                        Intent o = new Intent(Account_settings.this, Doctorwindow.class);
                        startActivity(o);
                        overridePendingTransition(R.anim.slide_up1, R.anim.slide_down1);

                    }else if (pref.getResponsibility() == 3) {
                        Intent o = new Intent(Account_settings.this, MainActivity.class);
                        startActivity(o);
                        overridePendingTransition(R.anim.slide_up1, R.anim.slide_down1);

                    } else {
                        Intent o = new Intent(Account_settings.this, ServiceOffer.class);
                        startActivity(o);
                        overridePendingTransition(R.anim.slide_up1, R.anim.slide_down1);

                    }
                }else{
                    Intent o = new Intent(Account_settings.this, MonitorFamilyPOD.class);
                    startActivity(o);
                    overridePendingTransition(R.anim.slide_up1, R.anim.slide_down1);
                }

            } else {
                Snackbar.make(getWindow().getDecorView().getRootView(), "Server Error!Please try later.", Snackbar.LENGTH_LONG).show();

            }

        }

    }

    public String getLastTen(String str) {
        str=str.replaceAll("\\s+","");
        return str.length() <= 10 ? str : str.substring(str.length() - 10);
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