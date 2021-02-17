package com.admin.ecosense.Doctors;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.admin.ecosense.Activities.Account_settings;
import com.admin.ecosense.Activities.NotificationAll;
import com.admin.ecosense.Activities.Wb1_access;
import com.admin.ecosense.Adapters._docAdapter;
import com.admin.ecosense.Adapters._symsAdapter;
import com.admin.ecosense.Login.Medicalmodel;
import com.admin.ecosense.Login.ServiceOffer;
import com.admin.ecosense.POD.PODConsultation;
import com.admin.ecosense.POD.StatusHistory;
import com.admin.ecosense.Questions.Consultation;
import com.admin.ecosense.R;
import com.admin.ecosense.RequestTest.OxygenRequest;
import com.admin.ecosense.RequestTest.SickNote;
import com.admin.ecosense.helper.Config_URL;
import com.admin.ecosense.helper.Login;
import com.admin.ecosense.helper.PrefManager;
import com.admin.ecosense.helper.Qns;
import com.admin.ecosense.helper.consultation;
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

public class PatientDetails extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = PatientDetails.class.getSimpleName();
    private static final int RESULT_PICK_CONTACT = 101;
    private Toolbar toolbar;
    private double My_lat = 0, My_long = 0;
    private PrefManager pref;
    private String _PhoneNo;
    private String phoneNo;
    private String name;
    private RecyclerView Sosrv,Reports;
    private ArrayList<Qns> mItems = new ArrayList<Qns>();
    private String mobileIp;
    private ProgressBar progressBar;
    public boolean success;
    private CoordinatorLayout coordinatorLayout;
    private DatabaseReference mDatabase;
    private String _familyPhone;
    private TextView _t1;
    private String _Name;
    private TextView textView101,textView102,covidstatus;
    EditText _nameText;
    EditText _userName,Nightingale;
    EditText _bday;
    EditText PatientID;
    private EditText _userBday;
    private EditText _userGender,MobileNo;
    private LinearLayout _L1,_L2;
    private TextInputLayout _Nightingale;
    private TextView reach,question,riskfactor,yourage,paymentmde,waqf,history;
    private Button startasession;
    private int _from=0;
    private EditText oxygen;
    private EditText breaths,issue;
    private EditText temperature,pulse;
    private String otherreasin;
    private TextInputLayout other;
    private EditText otherissues;
    private EditText ldh, crp, Ferritin, Lymphocytes, count, dimer, interleukin, PCT;
    private TextView image_1,details,alldetails;
    private String _report;
    private LinearLayout _L3;

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
        setContentView(R.layout.patientdetails);
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
        Sosrv = findViewById(R.id.sosrv);
        Reports=findViewById(R.id.reports);
        _Nightingale=findViewById(R.id._Nightingale);
        Nightingale=findViewById(R.id.Nightingale);
        progressBar=findViewById(R.id.progressBaremergency);
        progressBar.setVisibility(View.VISIBLE);
        MobileNo=findViewById(R.id.MobileNo);
        startasession=findViewById(R.id.startasession);
        startasession.setOnClickListener(this);

        pref = new PrefManager(getApplicationContext());
        HashMap<String, String> user = pref.getUserDetails();
        _PhoneNo = user.get(PrefManager.KEY_MOBILE);
        _Name = user.get(PrefManager.KEY_NAME);

        textView101=findViewById(R.id.textView101);
        textView102=findViewById(R.id.textView102);

        history=findViewById(R.id.history);
        history.setOnClickListener(this);

        image_1=findViewById(R.id.report);
        image_1.setOnClickListener(this);

        details=findViewById(R.id.details);
        details.setOnClickListener(this);

        alldetails=findViewById(R.id.alldetails);
        alldetails.setOnClickListener(this);

        other=findViewById(R.id.other);
        otherissues=findViewById(R.id.otherissues);


        temperature=findViewById(R.id.temperature);
        pulse=findViewById(R.id.pulse);

        ldh = findViewById(R.id.ldh);
        crp = findViewById(R.id.crp);
        Ferritin = findViewById(R.id.Ferritin);
        Lymphocytes = findViewById(R.id.Lymphocytes);
        count = findViewById(R.id.count);
        dimer = findViewById(R.id.dimer);
        interleukin = findViewById(R.id.interleukin);
        PCT = findViewById(R.id.PCT);

        _L3=findViewById(R.id._L3);



        textView101.setText("Patient ");
        textView102.setText("Status");
        covidstatus=findViewById(R.id.covidstatus);
        covidstatus.setVisibility(View.GONE);
        _L1=findViewById(R.id._L1);
        _L2=findViewById(R.id._L2);
        _L2.setVisibility(View.VISIBLE);

        _t1=findViewById(R.id._t1);

        mobileIp = getMobileIPAddress();
        if (TextUtils.isEmpty(mobileIp)) {
            mobileIp = getWifiIPAddress();
        }
        mDatabase = FirebaseDatabase.getInstance().getReference();
        reach=findViewById(R.id.reach);
        question=findViewById(R.id.question);
        riskfactor=findViewById(R.id.riskfactor);
        yourage=findViewById(R.id.yourage);
        paymentmde=findViewById(R.id.paymentmde);
        waqf=findViewById(R.id.waqf);

        oxygen = findViewById(R.id.oxygen);
        breaths = findViewById(R.id.breaths);
        issue=findViewById(R.id.issue);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);

            }
        });
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
        _from=pref.getResponsibility();

        if(_from==2){
            startasession.setVisibility(View.VISIBLE);
        }if(_from==1 || _from==3){
            startasession.setText("Your Sessions");
            startasession.setVisibility(View.GONE);
        }
        Intent i=getIntent();
        String _docMobile = i.getStringExtra("mobile");
        if(_docMobile!=null) {
            if (!_docMobile.contains(_PhoneNo)) {
                startasession.setVisibility(View.GONE);
            }
        }
        if(pref.getClockIn()==1){
            _L1.setVisibility(View.VISIBLE);
        }else {
            _L1.setVisibility(View.GONE);
        }
        go();
    }

    private void go() {
        mItems.clear();
        new GetUser().execute();

    }

    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.report){
            if(_report!=null) {
                Intent askwho = new Intent(PatientDetails.this, Wb1_access.class);
                askwho.putExtra("from", 5);
                askwho.putExtra("links", _report);
                startActivity(askwho);
                overridePendingTransition(R.anim.slide_up1, R.anim.slide_down1);
            }
        }
        if(v.getId()==R.id.details){
                Intent askwho = new Intent(PatientDetails.this, Account_settings.class);
                askwho.putExtra("from",1);
                askwho.putExtra("mobile",pref.getWorkID());
                startActivity(askwho);
                overridePendingTransition(R.anim.slide_up1, R.anim.slide_down1);
                }

        if(v.getId()==R.id.alldetails){
            pref.setPODmobile(MobileNo.getText().toString());
            Intent askwho = new Intent(PatientDetails.this, StatusHistory.class);
            startActivity(askwho);
            overridePendingTransition(R.anim.slide_up1, R.anim.slide_down1);

        }

     if(v.getId()==R.id.startasession){
         Intent eme = new Intent(PatientDetails.this, StartSession.class);
         startActivity(eme);
         overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
     }
        if(v.getId()==R.id.history){
         selectImage();
        }
    }

    private void selectImage() {
        final CharSequence[] items = {"Oxygen trend", "Breath trend","Temperature trend", "Pulse trend",
                "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(PatientDetails.this, R.style.AlertDialogTheme);
        builder.setTitle("Trends");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Oxygen trend")) {
                    Intent eme = new Intent(PatientDetails.this, History.class);
                    eme.putExtra("from",1);
                    startActivity(eme);
                    overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                } else if (items[item].equals("Breath trend")) {
                    Intent eme = new Intent(PatientDetails.this, History.class);
                    eme.putExtra("from",2);
                    startActivity(eme);
                    overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                }else if (items[item].equals("Temperature trend")) {
                    Intent eme = new Intent(PatientDetails.this, History.class);
                    eme.putExtra("from",3);
                    startActivity(eme);
                    overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                } else if (items[item].equals("Pulse trend")) {
                    Intent eme = new Intent(PatientDetails.this, History.class);
                    eme.putExtra("from",4);
                    startActivity(eme);
                    overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }



    private class GetUser extends AsyncTask<Void, Integer, String> {


        private ArrayList<Login> mItems = new ArrayList<Login>();
        private ArrayList<Login>mRequest=new ArrayList<>();
        private ArrayList<Login>mSyms=new ArrayList<>();
        private ArrayList<consultation> mCons = new ArrayList<consultation>();
        private ArrayList<Medicalmodel> mMedical = new ArrayList<Medicalmodel>();
        private ArrayList<Login>mSyms2=new ArrayList<>();
        private ArrayList<Login>mSyms3=new ArrayList<>();
        private int _Status=0;
        private int ID;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Intent i=getIntent();
            ID=pref.getWorkID();
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
                        .addFormDataPart("pid", String.valueOf(ID))
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
                    JSONArray _Attendence = jsonObj.getJSONArray("plogin");
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
                            item.setWAQF(c.getString("WAQF"));
                            mItems.add(item);
                        }

                    }

                    JSONArray Document = jsonObj.getJSONArray("covidtest");
                    for (int i = 0; i < Document.length(); i++) {
                        JSONObject c = Document.getJSONObject(i);
                        if (!c.isNull("ID")) {
                            Login item = new Login();
                            item.setID(c.getInt("ID"));
                            item.setIDrequests(c.getInt("IDrequests"));
                            item.setPhoto(c.getString("Document"));
                            mSyms3.add(item);
                        }

                    }

                    JSONArray consultation = jsonObj.getJSONArray("pconsultation");
                    for (int i = 0; i < consultation.length(); i++) {
                        JSONObject c = consultation.getJSONObject(i);
                        if (!c.isNull("ID")) {
                            com.admin.ecosense.helper.consultation item = new consultation();
                            item.setID(c.getInt("ID"));
                            item.setBookDate(c.getString("BookDate"));
                            item.setTimeSlot(c.getString("TimeSlot"));
                            item.setQuestion(c.getString("Question"));
                            item.setRiskFactor(c.getString("RiskFactor"));
                            item.setPaymentMode(c.getInt("PaymentMode"));
                            item.setAge(c.getInt("Age"));
                            mCons.add(item);
                        }

                    }

                    JSONArray stats = jsonObj.getJSONArray("userpsymtoms");
                    for (int i = 0; i < stats.length(); i++) {
                        JSONObject c = stats.getJSONObject(i);
                        if (!c.isNull("ID")) {
                            Login item = new Login();
                            item.setID(c.getInt("ID"));
                            item.setName(c.getString("Name"));
                            if(!c.isNull("Client")) {
                                item.setClient(c.getInt("Client"));
                            }
                            if(!c.isNull("otherreason")) {
                                item.setOtherIssue(c.getString("otherreason"));
                            }
                            mSyms.add(item);
                        }

                    }
                    JSONArray stats2 = jsonObj.getJSONArray("psymtoms");
                    for (int i = 0; i < stats2.length(); i++) {
                        JSONObject c = stats2.getJSONObject(i);
                        if (!c.isNull("ID")) {
                            Login item = new Login();
                            item.setID(c.getInt("ID"));
                            item.setOxygen(c.getDouble("Oxygen"));
                            item.setBreaths(c.getInt("Breaths"));
                            item.setTemperature(c.getDouble("temperature"));
                            item.setPulse(c.getInt("pulse"));
                            item.setIssues(c.getString("issues"));
                            mSyms2.add(item);
                        }

                    }

                    JSONArray medical = jsonObj.getJSONArray("medical");
                    for (int i = 0; i < medical.length(); i++) {
                        JSONObject c = medical.getJSONObject(i);
                        if (!c.isNull("ID")) {
                            Medicalmodel item = new Medicalmodel();
                            item.setLBH(c.getString("LDH"));
                            item.setCRP(c.getString("CRP"));
                            item.setFerritin(c.getString("Ferritin"));
                            item.setLymphocytes(c.getString("Lymphocytes"));
                            item.setCount(c.getString("Count"));
                            item.setDDimer(c.getString("DDimer"));

                            item.setInterleukin(c.getString("Interleukin"));
                            item.setPCT(c.getString("PCT"));
                            item.setInterleukin(c.getString("Interleukin"));
                            item.setPhoto(c.getString("Photo"));
                            mMedical.add(item);
                        }

                    }


                    JSONArray covid = jsonObj.getJSONArray("pcovid");
                    for (int i = 0; i < covid.length(); i++) {
                        JSONObject c = covid.getJSONObject(i);

                        if (!c.isNull("Status")) {
                            _Status=c.getInt("Status");
                        }else{
                            pref.setClockIn(0);
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
                _userGender.setText(mItems.get(0).getGender(0));
                _userBday.setText(mItems.get(0).getBday(0));
                MobileNo.setText(mItems.get(0).get_Phone_no(0));
                PatientID.setText(mItems.get(0).getPatientID(0));
                if(!TextUtils.isEmpty(mItems.get(0).getWAQF(0))){
                    Nightingale.setText(mItems.get(0).getWAQF(0));
                }else{
                    _Nightingale.setVisibility(View.GONE);
                }
                if(otherreasin!=null){
                    other.setVisibility(View.VISIBLE);
                    otherissues.setText(otherreasin);
                }else{
                    other.setVisibility(View.GONE);
                }

                if(mSyms.size()>0) {
                    _symsAdapter sAdapter = new _symsAdapter(PatientDetails.this, mSyms);
                    sAdapter.notifyDataSetChanged();
                    Sosrv.setVisibility(View.VISIBLE);
                    Sosrv.setItemAnimator(new DefaultItemAnimator());
                    Sosrv.setAdapter(sAdapter);
                    Sosrv.setHasFixedSize(true);
                    LinearLayoutManager mHorizontal = new LinearLayoutManager(PatientDetails.this, LinearLayoutManager.VERTICAL, false);
                    Sosrv.setLayoutManager(mHorizontal);

                    if(mSyms2.size()>0) {
                        oxygen.setText(String.valueOf(mSyms2.get(0).getOxygen(0)));
                        breaths.setText(String.valueOf(mSyms2.get(0).getBreaths(0)));
                        temperature.setText(String.valueOf(mSyms2.get(0).getTemperature(0)));
                        pulse.setText(String.valueOf(mSyms2.get(0).getPulse(0)));
                        issue.setText(mSyms2.get(0).getIssues(0));
                    }

                    if(mMedical.size()>0){
                        pref.setMedical(1);
                        _L3.setVisibility(View.VISIBLE);
                        if(!TextUtils.isEmpty(mMedical.get(0).getLBH(0))) {
                            ldh.setText(mMedical.get(0).getLBH(0));
                        }else{
                            ldh.setVisibility(View.GONE);
                        }
                        if(!TextUtils.isEmpty(mMedical.get(0).getCRP(0))) {
                            crp.setText(mMedical.get(0).getCRP(0));
                        }else{
                            crp.setVisibility(View.GONE);
                        }
                        if(!TextUtils.isEmpty(mMedical.get(0).getFerritin(0))) {
                            Ferritin.setText(mMedical.get(0).getFerritin(0));
                        }else{
                            Ferritin.setVisibility(View.GONE);
                        }
                        if(!TextUtils.isEmpty(mMedical.get(0).getLymphocytes(0))) {
                            Lymphocytes.setText(mMedical.get(0).getLymphocytes(0));
                        }else{
                            Lymphocytes.setVisibility(View.GONE);
                        }
                        if(!TextUtils.isEmpty(mMedical.get(0).getCount(0))) {
                            count.setText(mMedical.get(0).getCount(0));
                        }else{
                            count.setVisibility(View.GONE);
                        }
                        if(!TextUtils.isEmpty(mMedical.get(0).getDDimer(0))) {
                            dimer.setText(mMedical.get(0).getDDimer(0));
                        }else{
                            dimer.setVisibility(View.GONE);
                        }

                        if(!TextUtils.isEmpty(mMedical.get(0).getInterleukin(0))) {
                            interleukin.setText(mMedical.get(0).getInterleukin(0));
                        }else{
                            interleukin.setVisibility(View.GONE);
                        }
                        if(!TextUtils.isEmpty(mMedical.get(0).getPCT(0))) {
                            PCT.setText(mMedical.get(0).getPCT(0));
                        }else{
                            PCT.setVisibility(View.GONE);
                        }
                        if(!TextUtils.isEmpty(mMedical.get(0).getPhoto(0))) {
                            _report =mMedical.get(0).getPhoto(0);
                        }else{
                            image_1.setVisibility(View.GONE);
                        }
                    }else {
                        _L3.setVisibility(View.GONE);
                    }
                }else{
                    _L2.setVisibility(View.GONE);
                    _t1.setText("Symptoms not shared");
                    if(_from!=2) {
                        if (!PatientDetails.this.isFinishing() && pref.getResponsibility()!=3  && pref.getResponsibility()!=2) {
                            new AlertDialog.Builder(PatientDetails.this, R.style.AlertDialogTheme)
                                    .setIcon(R.mipmap.ic_launcher)
                                    .setTitle("Please completely share information.")
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (pref.getResponsibility() == 3) {
                                                if (pref.getPODmobile() != null) {
                                                    Intent o = new Intent(PatientDetails.this, PODConsultation.class);
                                                    o.putExtra("from", 1);
                                                    startActivity(o);
                                                    overridePendingTransition(R.anim.slide_up1, R.anim.slide_down1);

                                                } else {
                                                    Intent o = new Intent(PatientDetails.this, Consultation.class);
                                                    o.putExtra("from", 1);
                                                    startActivity(o);
                                                    overridePendingTransition(R.anim.slide_up1, R.anim.slide_down1);

                                                }
                                            } else {
                                                Intent o = new Intent(PatientDetails.this, Consultation.class);
                                                o.putExtra("from", 1);
                                                startActivity(o);
                                                overridePendingTransition(R.anim.slide_up1, R.anim.slide_down1);

                                            }

                                            dialog.cancel();
                                        }
                                    })
                                    .show();
                        }
                    }
                }

                if (mCons.size() != 0) {
                    reach.setText(mCons.get(0).getBookDate(0)+" "+mCons.get(0).getTimeSlot(0));
                    question.setText("Statement: "+mCons.get(0).getQuestion(0));
                    riskfactor.setText("Risk factor: "+mCons.get(0).getRiskFactor(0));
                    yourage.setText("Age: "+mCons.get(0).getAge(0));
                    if(mCons.get(0).getPaymentMode(0)==1){
                        paymentmde.setText("Paid now with credit card");
                    } if(mCons.get(0).getPaymentMode(0)==2){
                        paymentmde.setText("Paid now with EFT");
                    }

                }

                    if (mSyms3.size() != 0) {
                        _docAdapter sAdapter = new _docAdapter(PatientDetails.this, mSyms3);
                        sAdapter.notifyDataSetChanged();
                        Reports.setVisibility(View.VISIBLE);
                        Reports.setItemAnimator(new DefaultItemAnimator());
                        Reports.setAdapter(sAdapter);
                        Reports.setHasFixedSize(true);
                        LinearLayoutManager mHorizontal = new LinearLayoutManager(PatientDetails.this, LinearLayoutManager.HORIZONTAL, false);
                        Reports.setLayoutManager(mHorizontal);
                    }



                /*if(_Status==1){
                    covidstatus.setText("Pending");
                }else  if(_Status==2){
                    covidstatus.setText("Pending Test");
                }else  if(_Status==3){
                    covidstatus.setText("Pending lab result");
                }else  if(_Status==4){
                    covidstatus.setText("Positive");
                }else  if(_Status==5){
                    covidstatus.setText("Negative");
                }*/

            }
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

        switch (item.getItemId()){
            case R.id.action_refresh1:

                overridePendingTransition(0, 0);
                startActivity(getIntent());
                break;
            case R.id.action_notification:
                if (_PhoneNo != null) {
                    Intent o = new Intent(PatientDetails.this, Account_settings.class);
                    startActivity(o);
                    overridePendingTransition(R.anim.slide_up1, R.anim.slide_down1);
                } else {
                    Intent o = new Intent(PatientDetails.this, ServiceOffer.class);
                    startActivity(o);

                    overridePendingTransition(R.anim.slide_up1, R.anim.slide_down1);
                }
                break;
            case R.id.action_noti:
                Intent o = new Intent(PatientDetails.this, NotificationAll.class);
                startActivity(o);

                overridePendingTransition(R.anim.slide_up1, R.anim.slide_down1);
                break;
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

