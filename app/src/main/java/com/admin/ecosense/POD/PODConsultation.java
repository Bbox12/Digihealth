package com.admin.ecosense.POD;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.UiModeManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
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
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.admin.ecosense.Activities.MainActivity;
import com.admin.ecosense.Adapters.TimeslotRv;
import com.admin.ecosense.Adapters._questionAdapter;
import com.admin.ecosense.Questions.Consultation;
import com.admin.ecosense.R;
import com.admin.ecosense.RequestTest.SickNote;
import com.admin.ecosense.helper.AppController;
import com.admin.ecosense.helper.Config_URL;
import com.admin.ecosense.helper.Login;
import com.admin.ecosense.helper.MyViewPager;
import com.admin.ecosense.helper.PrefManager;
import com.admin.ecosense.helper.Qns;
import com.admin.ecosense.helper.RecyclerTouchListener;
import com.admin.ecosense.helper.consultation;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class PODConsultation extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {
    private CoordinatorLayout coordinatorLayout;
    private DatabaseReference mDatabase;
    private PrefManager pref;
    private String _PhoneNo;
    private MyViewPager viewPager1;
    private ViewPagerAdapter adapter;
    private RecyclerView firstrv,secondrv;
    private AutoCompleteTextView age;
    private CalendarView calendarView;
    private String _date="";
    private int _risk,_timeslot;
    private AppCompatCheckBox _cash;
    private int pmode=0;
    private String _age;
    private AppCompatButton finalsubmit,lastsubmit;
    EditText _emailText;
    EditText _userName;
    EditText _userMobile;
    EditText input_passport;
    EditText PatientID;
    private EditText _userBday;
    private AutoCompleteTextView _userGender;
    private String _newPlace = "", _newBday = "", _newGender = "", _newEmail="", _newName="",_patientID="";
    private String _userPhone;
    private Button _submit,sixsubmit;
    private  EditText inputemergencyname,inputemergencyno;
    private static final int RESULT_PICK_CONTACT = 101;
    private boolean _getImage;
    private String mobileIp;
    private AppCompatCheckBox _cashpay,_aid;
    private TextView reach,question,riskfactor,yourage,paymentmde,waqf;
    private String _Name;
    private int _amount=1;
    private TextView Cost;
    private String other_reson="";
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.consultation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Consultation");
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        coordinatorLayout=findViewById(R.id.coordinatorLayout);
        pref=new PrefManager(getApplicationContext());
        HashMap<String, String> user = pref.getUserDetails();
        _PhoneNo = pref.getPODmobile();
        _Name = user.get(PrefManager.KEY_NAME);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        viewPager1 = findViewById(R.id.viewPagerConsult);
        viewPager1.setVisibility(View.GONE);
        progressBar=findViewById(R.id.progressBaremergency);
        adapter = new ViewPagerAdapter();
        viewPager1.setAdapter(adapter);
        age=findViewById(R.id.age);

        if(pref.getAge()!=0){
            age.setText(String.valueOf(age));
        }else{
            age.setOnClickListener(this);
        }

        firstrv=findViewById(R.id.risk);
        secondrv=findViewById(R.id.timerv);
        firstrv.setNestedScrollingEnabled(false);
        secondrv.setNestedScrollingEnabled(false);

        calendarView = (CalendarView) findViewById(R.id.calendarView);


        finalsubmit=findViewById(R.id.finalsubmit);
        finalsubmit.setOnClickListener(this);
        lastsubmit=findViewById(R.id.lastsubmit);
        lastsubmit.setOnClickListener(this);

        Cost=findViewById(R.id._amount);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);


            }
        });

        _userPhone = pref.getPODmobile();
        _emailText = findViewById(R.id.input_email);
        _userName = findViewById(R.id.input_user_name);
        _userMobile = findViewById(R.id.input_user_mobile);
        _userGender = findViewById(R.id.input_gender);
        _userBday = findViewById(R.id.input_bday);
        _userBday.setOnClickListener(this);
        PatientID = findViewById(R.id.PatientID);
        _submit=findViewById(R.id.submit);
        _submit.setOnClickListener(this);
        input_passport=findViewById(R.id.input_passport);
        _userGender.setOnClickListener(this);
        inputemergencyname=findViewById(R.id.inputemergencyname);
        inputemergencyno=findViewById(R.id.inputemergencyno);
        inputemergencyname.setOnClickListener(this);
        mobileIp = getMobileIPAddress();
        if(TextUtils.isEmpty(mobileIp)){
            mobileIp= getWifiIPAddress();
        }

        _cashpay=findViewById(R.id._cashpay);
        _aid=findViewById(R.id._aid);

        _cash=findViewById(R.id.cash);

        reach=findViewById(R.id.reach);
        question=findViewById(R.id.question);
        riskfactor=findViewById(R.id.riskfactor);
        yourage=findViewById(R.id.yourage);
        paymentmde=findViewById(R.id.paymentmde);
        sixsubmit=findViewById(R.id.sixsubmit);
        sixsubmit.setOnClickListener(this);
        waqf=findViewById(R.id.waqf);



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
    protected void onResume() {
        super.onResume();
        getInbox();
        viewPager1.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }

        });


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull final CalendarView calendarView, int year, int month, int day) {


                if(month<10){
                    if(day<10){
                        _date=(String.valueOf(year)+"-"+"0"+String.valueOf((month+1))+"-"+"0"+String.valueOf(day));
                    }else{
                        _date=(String.valueOf(year)+"-"+"0"+String.valueOf((month+1))+"-"+String.valueOf(day));
                    }
                }else{
                    if(day<10){
                        _date=(String.valueOf(year)+"-"+"0"+String.valueOf((month+1))+"-"+"0"+String.valueOf(day));
                    }else{
                        _date=(String.valueOf(year)+"-"+"0"+String.valueOf((month+1))+"-"+String.valueOf(day));
                    }
                }




            }
        });

        _cash.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    pmode=1;

                }
            }
        });

        _cashpay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    _aid.setChecked(false);
                    pref.setPaid(1);
                    viewPager1.setCurrentItem(3);
                }
            }
        });
        _aid.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    _cashpay.setChecked(false);
                    viewPager1.setCurrentItem(3);
                    pref.setPaid(2);
                }
            }
        });



        if(!_getImage) {
            //    viewPager1.setCurrentItem(1);
            new GetUser().execute();
        }

    }

    private class GetUser extends AsyncTask<Void, Integer, String> {


        private ArrayList<Login> mItems = new ArrayList<Login>();
        private ArrayList<consultation> mCons = new ArrayList<consultation>();
        private String _ID;

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
                            item.setUniqueID(c.getString("UniqueID"));
                            mItems.add(item);
                        }

                    }

                    JSONArray consultation = jsonObj.getJSONArray("consultation");
                    for (int i = 0; i < consultation.length(); i++) {
                        JSONObject c = consultation.getJSONObject(i);
                        if (!c.isNull("ID")) {
                            consultation item = new consultation();
                            item.setID(c.getInt("ID"));
                            item.setBookDate(c.getString("BookDate"));
                            item.setTimeSlot(c.getString("TimeSlot"));
                            item.setQuestion(c.getString("Question"));
                            item.setRiskFactor(c.getString("RiskFactor"));
                            item.setPaymentMode(c.getInt("PaymentMode"));
                            item.setAge(c.getInt("Age"));
                            item.setOtherRiskFactor(c.getString("OtherRiskFactor"));
                            mCons.add(item);
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

                if (!TextUtils.isEmpty(mItems.get(0).getPatientID(0)) && mItems.get(0).getPatientID(0) != null) {
                    PatientID.setText(mItems.get(0).getPatientID(0));
                } else {
                    if (_ID != null) {
                        PatientID.setText(_ID);
                    }
                }


            }else{
                if (_ID != null) {
                    PatientID.setText(_ID);
                }
            }


            if (mCons.size() != 0) {
                //    reach.setText(mCons.get(0).getBookDate(0)+" "+mCons.get(0).getTimeSlot(0));
                question.setText("Your statement: "+mCons.get(0).getQuestion(0));
                if(!TextUtils.isEmpty(mCons.get(0).getOtherRiskFactor(0))) {
                    riskfactor.setText("Risk factor: " + mCons.get(0).getRiskFactor(0)+"\n("+mCons.get(0).getOtherRiskFactor(0)+")");
                }else{
                    riskfactor.setText("Risk factor: " + mCons.get(0).getRiskFactor(0));

                }
                yourage.setText("Age: "+mCons.get(0).getAge(0));
                if(mCons.get(0).getPaymentMode(0)==1){
                    paymentmde.setText("Paid now with credit card");
                } if(mCons.get(0).getPaymentMode(0)==2){
                    paymentmde.setText("Paid now with EFT");
                }else{
                    if(mCons.get(0).getPaymentMode(0)==0){
                        if(pref.getFirstQuestion()==1){
                            paymentmde.setText("Nightingale patient");
                        }

                    }
                }
                if(pref.getFirstQuestion()==1) {
                    if (pref.getWAQF() != null) {
                        waqf.setText("Nightingale reference code: " + pref.getWAQF());
                    }
                }else if(pref.getFirstQuestion()==2) {
                    waqf.setText("You are referred by another doctor");
                }else if(pref.getFirstQuestion()==3) {
                    waqf.setText("You are: Existing patient of Ekuphileni");
                }else if(pref.getFirstQuestion()==3) {
                    waqf.setText("You are: A New individual who wants to join DigiDoc");

                }

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

                    if (!PODConsultation.this.isFinishing()) {
                        new AlertDialog.Builder(PODConsultation.this, R.style.AlertDialogTheme)
                                .setIcon(R.mipmap.ic_launcher)
                                .setTitle("Patient is currently in "+category)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                })
                                .show();
                    }
                }
            }


        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.sixsubmit:
                Intent o = new Intent(PODConsultation.this, SickNote.class);
                o.putExtra("from",1);
                startActivity(o);
                overridePendingTransition(R.anim.slide_up1, R.anim.slide_down1);

                break;

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

            case R.id.inputemergencyname:
                pickContact(view);
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

            case R.id.input_gender:

                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                        getBaseContext(), android.R.layout.simple_list_item_1, getResources()
                        .getStringArray(R.array.Gender_Names));
                final String[] selection1 = new String[1];
                _userGender.setAdapter(arrayAdapter);
                _userGender.setCursorVisible(false);
                _userGender.showDropDown();
                _userGender.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        selection1[0] = (String) parent.getItemAtPosition(position);

                    }
                });

                break;

            case R.id.finalsubmit:
                checkEmail();
                break;
            case R.id.lastsubmit:

                break;

            case R.id.age:
                ArrayList<String>mAge=new ArrayList<>();
                for(int i=21;i<81;i++){
                    mAge.add(String.valueOf(i));
                }
                final ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(
                        getBaseContext(), android.R.layout.simple_list_item_1,mAge);
                age.showDropDown();
                age.setAdapter(arrayAdapter1);
                age.setCursorVisible(false);
                age.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        _age=(String) parent.getItemAtPosition(position);
                        pref.setAge(Integer.parseInt(_age));
                        if( _risk!=0) {
                            int aged = Integer.parseInt(age.getText().toString());
                            if (aged < 50) {
                                _amount = 1;
                            }
                            if (aged >= 60) {
                                _amount = 4;
                            }
                            if (aged > 50 && aged < 60) {
                                _amount = 3;
                            }
                            //Cost.setText("R " + String.valueOf(_amount));
                            viewPager1.setCurrentItem(1);
                        }



                    }
                });

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




    class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return 6;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        public Object instantiateItem(View collection, int position) {

            int resId = 0;
            switch (position) {
                case 0:
                    resId = R.id.first_2;
                    break;
                case 1:
                    resId = R.id.second_2;
                    break;
                case 2:
                    resId = R.id.third_2;
                    break;
                case 3:
                    resId = R.id.fourth_2;
                    break;
                case 4:
                    resId = R.id.fifth_2;
                    break;
                case 5:
                    resId = R.id.sixth_2;
                    break;

            }
            return findViewById(resId);
        }
    }
    private void getInbox() {


        StringRequest eventoReq = new StringRequest(com.android.volley.Request.Method.POST, Config_URL.GET_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.w("details", response);

                        final ArrayList<Qns> mFirst=new ArrayList<Qns>();
                        final ArrayList<Qns> mSecond=new ArrayList<Qns>();
                        final ArrayList<Qns> mThird=new ArrayList<Qns>();
                        try {

                            JSONObject jsonObj = new JSONObject(response);

                            JSONArray _Attendence = jsonObj.getJSONArray("login");
                            for (int i = 0; i < _Attendence.length(); i++) {
                                JSONObject c = _Attendence.getJSONObject(i);
                                if (!c.isNull("date")) {
                                    pref.setDate(c.getString("date"));
                                }

                            }



                            JSONArray category = jsonObj.getJSONArray("risk");
                            for (int i = 0; i < category.length(); i++) {
                                JSONObject c = category.getJSONObject(i);
                                if (!c.isNull("Factors")) {
                                    Qns item=new Qns();
                                    item.setID(c.getInt("ID"));
                                    item.setQuestion(c.getString("Factors"));
                                    mFirst.add(item);
                                }
                            }

                            JSONArray secondlevel = jsonObj.getJSONArray("timeslot");
                            if(secondlevel.length()!=0) {
                                for (int i = 0; i < secondlevel.length(); i++) {
                                    JSONObject c = secondlevel.getJSONObject(i);
                                    if (!c.isNull("Slot")) {
                                        Qns item = new Qns();
                                        item.setID(c.getInt("ID"));
                                        item.setQuestion(c.getString("Slot"));
                                        mSecond.add(item);
                                    }
                                }
                            }else{
                                JSONArray thirdlevel = jsonObj.getJSONArray("secondtimeslot");
                                for (int i = 0; i < thirdlevel.length(); i++) {
                                    JSONObject c = thirdlevel.getJSONObject(i);
                                    if (!c.isNull("Slot")) {
                                        Qns item=new Qns();
                                        item.setID(c.getInt("ID"));
                                        item.setQuestion(c.getString("Slot"));
                                        mThird.add(item);
                                    }
                                }
                            }


                            if(pref.getAge()!=0){
                                age.setText(String.valueOf(pref.getAge()));
                            }

                            if (mFirst.size() > 0) {
                                final _questionAdapter sAdapter = new _questionAdapter(PODConsultation.this, mFirst);
                                sAdapter.notifyDataSetChanged();
                                firstrv.setVisibility(View.VISIBLE);
                                firstrv.setItemAnimator(new DefaultItemAnimator());
                                firstrv.setAdapter(sAdapter);
                                firstrv.setHasFixedSize(true);
                                LinearLayoutManager mHorizontal = new LinearLayoutManager(PODConsultation.this, LinearLayoutManager.VERTICAL, false);
                                firstrv.setLayoutManager(mHorizontal);
                                firstrv.addOnItemTouchListener(
                                        new RecyclerTouchListener(PODConsultation.this, firstrv,
                                                new RecyclerTouchListener.OnTouchActionListener() {


                                                    @Override
                                                    public void onClick(View view, final int position) {
                                                        Log.w("gallery", String.valueOf(position));

                                                        if(position>=0) {
                                                            if (mFirst.size() != 0 && mFirst.get(position).getQuestion(position) != null) {
                                                                _risk = mFirst.get(position).getID(position);

                                                                if(!TextUtils.isEmpty(age.getText().toString())) {
                                                                    if( _risk!=0) {
                                                                        int aged = Integer.parseInt(age.getText().toString());
                                                                        if (aged < 50) {
                                                                            _amount = 1;
                                                                        }
                                                                        if (aged >= 60) {
                                                                            _amount = 4;
                                                                        }
                                                                        if (aged > 50 && aged < 60) {
                                                                            _amount = 3;
                                                                        }
                                                                        if(mFirst.get(position).getQuestion(position).toLowerCase().contains("other")){
                                                                            open_otp();
                                                                        }else{
                                                                            viewPager1.setCurrentItem(1);
                                                                        }
                                                                    }


                                                                }else{
                                                                    age.setError("Empty");
                                                                    age.requestFocus();
                                                                }


                                                            }
                                                        }
                                                    }


                                                    @Override
                                                    public void onRightSwipe(View view, int position) {

                                                    }

                                                    @Override
                                                    public void onLeftSwipe(View view, int position) {

                                                    }
                                                }));
                            }
                            if (mSecond.size() > 0) {
                                TimeslotRv sAdapter = new TimeslotRv(PODConsultation.this, mSecond);
                                sAdapter.notifyDataSetChanged();
                                secondrv.setAdapter(sAdapter);
                                StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(getSpan(), StaggeredGridLayoutManager.VERTICAL);
                                secondrv.setLayoutManager(mLayoutManager);
                                secondrv.addOnItemTouchListener(
                                        new RecyclerTouchListener(PODConsultation.this, secondrv,
                                                new RecyclerTouchListener.OnTouchActionListener() {


                                                    @Override
                                                    public void onClick(View view, final int position) {
                                                        Log.w("gallery", String.valueOf(position));
                                                        if (mSecond.get(position) != null) {
                                                            _timeslot = mSecond.get(position).getID(position);
                                                            // viewPager1.setCurrentItem(2);

                                                        }
                                                    }

                                                    @Override
                                                    public void onRightSwipe(View view, int position) {

                                                    }

                                                    @Override
                                                    public void onLeftSwipe(View view, int position) {

                                                    }
                                                }));

                                SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                                Calendar c = Calendar.getInstance();
                                try{
                                    //Setting the date to the given date
                                    c.setTime(inFormat.parse(pref.getDate()));
                                    String newDate = inFormat.format(c.getTime());
                                    Date date2=inFormat.parse(newDate);
                                    if (date2 != null) {
                                        _date=newDate;
                                        calendarView.setMinDate(date2.getTime());
                                    }
                                    c.add(Calendar.DAY_OF_YEAR, 60);
                                    String newDate2 = inFormat.format(c.getTime());
                                    Date date3=inFormat.parse(newDate2);
                                    calendarView.setMaxDate(date3.getTime());
                                }catch(ParseException e){
                                    e.printStackTrace();
                                }
                            }else{
                                SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                                Calendar c = Calendar.getInstance();
                                try{
                                    //Setting the date to the given date
                                    c.setTime(inFormat.parse(pref.getDate()));
                                    c.add(Calendar.DAY_OF_YEAR, 1);
                                    String newDate = inFormat.format(c.getTime());
                                    Date date2=inFormat.parse(newDate);
                                    if (date2 != null) {
                                        _date=newDate;
                                        calendarView.setMinDate(date2.getTime()+1);
                                    }
                                    c.add(Calendar.DAY_OF_YEAR, 60);
                                    String newDate2 = inFormat.format(c.getTime());
                                    Date date3=inFormat.parse(newDate2);
                                    calendarView.setMaxDate(date3.getTime());
                                }catch(ParseException e){
                                    e.printStackTrace();
                                }


                                if (mThird.size() > 0) {
                                    TimeslotRv sAdapter = new TimeslotRv(PODConsultation.this, mThird);
                                    sAdapter.notifyDataSetChanged();
                                    secondrv.setAdapter(sAdapter);
                                    StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(getSpan(), StaggeredGridLayoutManager.VERTICAL);
                                    secondrv.setLayoutManager(mLayoutManager);
                                    secondrv.addOnItemTouchListener(
                                            new RecyclerTouchListener(PODConsultation.this, secondrv,
                                                    new RecyclerTouchListener.OnTouchActionListener() {


                                                        @Override
                                                        public void onClick(View view, final int position) {
                                                            Log.w("gallery", String.valueOf(position));
                                                            if (mThird.get(position) != null) {
                                                                _timeslot = mThird.get(position).getID(position);
                                                                // viewPager1.setCurrentItem(2);

                                                            }
                                                        }

                                                        @Override
                                                        public void onRightSwipe(View view, int position) {

                                                        }

                                                        @Override
                                                        public void onLeftSwipe(View view, int position) {

                                                        }
                                                    }));



                                }
                            }
                            progressBar.setVisibility(View.GONE);
                            viewPager1.setVisibility(View.VISIBLE);


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
                params.put("_mId",_PhoneNo);
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


    private void open_otp() {
        if(!PODConsultation.this.isFinishing()) {
            final Dialog dialog = new Dialog(PODConsultation.this, R.style.AlertDialogTheme);
            dialog.setContentView(R.layout.otherreason);
            final EditText Otp = dialog.findViewById(R.id.inputOtp_reason);
            final TextInputLayout otp1=dialog.findViewById(R.id._o1);
            Button Start = dialog.findViewById(R.id.btn_dialog);
            dialog.setCancelable(true);

            Otp.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                }

                @Override
                public void onTextChanged(CharSequence s, int start,
                                          int before, int count) {
                    otp1.setError(null);
                    otp1.setErrorEnabled(false);
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });


            Start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (TextUtils.isEmpty(Otp.getText().toString())) {
                        //     Toast.makeText(getApplicationContext(), "Please input OTP", Toast.LENGTH_SHORT).show();
                        otp1.setErrorEnabled(true);
                        otp1.setError("Empty");
                    }else {
                        if(!TextUtils.isEmpty(age.getText().toString())){
                            other_reson=Otp.getText().toString();
                            viewPager1.setCurrentItem(1);
                        }else{
                            age.setError("Empty");
                            age.requestFocus();
                        }

                        dialog.cancel();
                    }

                }
            });


            dialog.show();
        }

    }


    private void checkEmail() {
        StringRequest eventoReq = new StringRequest(com.android.volley.Request.Method.POST, Config_URL.URL_STORE_CONSULTATION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.w("kk", response.toString());


                        String[] par = response.split("error");
                        if (par[1].contains("false")) {
                            pref.setMask(1);
                            pref.setAge(Integer.parseInt(age.getText().toString()));
                            if(pref.getFirstQuestion()==1) {
                                pref.setDigiCategory(_amount);
                                new GetUser().execute();
                                viewPager1.setCurrentItem(5);
                            }else {
                                ViewDialogFailedSuccess alert = new ViewDialogFailedSuccess();
                                alert.showDialog(PODConsultation.this, "Thank you! Our DigiDoc will reach out to you .", false);
                            }
                        }else{
                            ViewDialogFailed alert = new ViewDialogFailed();
                            alert.showDialog(PODConsultation.this, "Please check the information provided!", true);
                        }


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
                params.put("mobile",_PhoneNo);
                params.put("age", age.getText().toString());
                params.put("risk", String.valueOf(_risk));
                params.put("other", other_reson);
                params.put("amount", String.valueOf(_amount));
                params.put("pmode", String.valueOf(pref.getPaid()));
                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(eventoReq);

    }

    public int getSpan() {
        int orientation = getScreenOrientation(PODConsultation.this);
        if (isTV(PODConsultation.this)) return 4;
        if (isTablet(PODConsultation.this))
            return orientation == Configuration.ORIENTATION_PORTRAIT ? 4 : 4;
        return orientation == Configuration.ORIENTATION_PORTRAIT ? 4 : 4;
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

    public class ViewDialogFailed {

        public void showDialog(Activity activity, String msg, Boolean noDate) {
            if (!activity.isFinishing()) {
                final Dialog dialog1 = new Dialog(activity);
                dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog1.setCancelable(false);

                dialog1.setContentView(R.layout.custom_dialog_failed);
                dialog1.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog1.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                TextView text = dialog1.findViewById(R.id.text_dialog);
                text.setText(msg);

                Button dialogButton = dialog1.findViewById(R.id.btn_dialog);

                dialogButton.setText("Ok");
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog1.dismiss();
                    }
                });

                dialog1.show();
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
                        pref.setConsultation(1);
                        new GetUser().execute();
                        viewPager1.setCurrentItem(5);
                        dialog1.dismiss();
                    }
                });

                dialog1.show();


            }
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
            ten=getLastTen(_userMobile.getText().toString());
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
            if (success) {
                if(!TextUtils.isEmpty(_newBday)) {
                    pref.setDOB(_newBday);
                }
                if(pref.getFirstQuestion()==1){
                    checkEmail();
                    //  viewPager1.setCurrentItem(5);
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
    public void onRefresh() {
        // swipe refresh is performed, fetch the messages again
        getInbox();
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        //replaces the default 'Back' button action
        if(keyCode== KeyEvent.KEYCODE_BACK)   {
            if(viewPager1.getCurrentItem()==0) {
                finish();
                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
            }else{

                overridePendingTransition(0, 0);
                startActivity(getIntent());
            }

        }
        return true;
    }


}