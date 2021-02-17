package com.admin.ecosense.Activities;

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
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.admin.ecosense.Adapters._statusAdapter;
import com.admin.ecosense.Login.ServiceOffer;
import com.admin.ecosense.R;
import com.admin.ecosense.helper.Config_URL;
import com.admin.ecosense.helper.Login;
import com.admin.ecosense.helper.PrefManager;
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

public class statusReport extends AppCompatActivity{

    private static final String TAG = statusReport.class.getSimpleName();
    private static final int RESULT_PICK_CONTACT = 101;
    private Toolbar toolbar;
    private double My_lat = 0, My_long = 0;
    private PrefManager pref;
    private String _PhoneNo;
    private String phoneNo;
    private String name;
    private RecyclerView Sosrv;
    private String mobileIp;
    private ProgressBar progressBar;
    public boolean success;
    private CoordinatorLayout coordinatorLayout;
    private DatabaseReference mDatabase;
    private String _familyPhone;
    private EditText _t1;
    private String _Name;
    private TextView textView101,textView102;
    private EditText reason,title;
    private TextView _T1;
    private String _text;
    private int _status=0;

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
        setContentView(R.layout.sickreport);
        coordinatorLayout=findViewById(R.id.em);
        toolbar = findViewById(R.id.toolbar_later_sos);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        Sosrv = findViewById(R.id.sosrv);
//        _t1=findViewById(R.id._t1);

        progressBar=findViewById(R.id.progressBaremergency);
        progressBar.setVisibility(View.VISIBLE);

        pref = new PrefManager(getApplicationContext());
        HashMap<String, String> user = pref.getUserDetails();
        _PhoneNo = user.get(PrefManager.KEY_MOBILE);
        _Name = user.get(PrefManager.KEY_NAME);

        textView101=findViewById(R.id.textView101);
        textView102=findViewById(R.id.textView102);




        Intent i=getIntent();
        _text=i.getStringExtra("name");

        textView101.setText(_text);
        textView102.setVisibility(View.GONE);

        _T1=findViewById(R.id._t1);
        _T1.setText("Symtoms ");

        reason=findViewById(R.id.description);
        title=findViewById(R.id.title);


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
        progressBar.setVisibility(View.VISIBLE);
        new GetUser().execute();

    }



    private class GetUser extends AsyncTask<Void, Integer, String> {


        private ArrayList<Login>mSyms=new ArrayList<>();
        private String _feedback;

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


                    JSONArray stats = jsonObj.getJSONArray("symtoms");
                    for (int i = 0; i < stats.length(); i++) {
                        JSONObject c = stats.getJSONObject(i);
                        if (!c.isNull("ID")) {
                            if(!c.isNull("Client")) {
                                Login item = new Login();
                                item.setID(c.getInt("ID"));
                                item.setName(c.getString("Name"));

                                item.setClient(c.getInt("Client"));
                                _status = c.getInt("Client");

                                if (!c.isNull("feedback")) {
                                    _feedback = c.getString("feedback");
                                }
                                mSyms.add(item);
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
            progressBar.setVisibility(View.GONE);
            if (mSyms.size() != 0) {
                progressBar.setVisibility(View.GONE);
                if(mSyms.size()>0) {
                    _statusAdapter sAdapter = new _statusAdapter(statusReport.this, mSyms);
                    sAdapter.notifyDataSetChanged();
                    sAdapter.setFrom(1);
                    Sosrv.setVisibility(View.VISIBLE);
                    Sosrv.setItemAnimator(new DefaultItemAnimator());
                    Sosrv.setAdapter(sAdapter);
                    Sosrv.setHasFixedSize(true);
                    LinearLayoutManager mHorizontal = new LinearLayoutManager(statusReport.this, LinearLayoutManager.VERTICAL, false);
                    Sosrv.setLayoutManager(mHorizontal);
                    if(_status==1){
                        title.setText("Pending");
                    }else if(_status==2){
                        title.setText("Accept");
                    }else if(_status==3){
                        title.setText("Reject");
                    }
                    if(_feedback!=null){
                        reason.setText(_feedback);
                    }
                }

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
        if (item.getItemId() == R.id.action_refresh1) {

            overridePendingTransition(0, 0);
            startActivity(getIntent());
        }
        if(_PhoneNo!=null) {
            Intent o = new Intent(statusReport.this, Account_settings.class);
            startActivity(o);
            overridePendingTransition(R.anim.slide_up1, R.anim.slide_down1);
        }else{
            Intent o = new Intent(statusReport.this, ServiceOffer.class);
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

