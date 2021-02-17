package com.admin.ecosense.Doctors;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.admin.ecosense.Activities.Account_settings;
import com.admin.ecosense.Activities.NotificationAll;
import com.admin.ecosense.Login.ServiceOffer;
import com.admin.ecosense.R;
import com.admin.ecosense.helper.Config_URL;
import com.admin.ecosense.helper.Login;
import com.admin.ecosense.helper.PrefManager;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class History extends AppCompatActivity {
    private BarChart chart;
    private PrefManager pref;
    private String _PhoneNo;
    private ProgressBar progressBar;
    private Toolbar toolbar;
    private TextView textView101,textView102;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pref = new PrefManager(getApplicationContext());
        HashMap<String, String> user = pref.getUserDetails();
        _PhoneNo = user.get(PrefManager.KEY_MOBILE);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar_later_sos);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        progressBar = findViewById(R.id.progressBaremergency);
        chart = findViewById(R.id.chart1);


        textView101=findViewById(R.id.textView101);
        textView102=findViewById(R.id.textView102);

        textView101.setText("Patient ");
        textView102.setText("Trends");

        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);

        chart.setDrawBarShadow(false);
        chart.setDrawValueAboveBar(true);
        chart.getDescription().setEnabled(false);
        chart.setMaxVisibleValueCount(15);
        chart.setPinchZoom(false);
        chart.setDrawGridBackground(false);

        new GetUser().execute();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
            }
        });
    }

    private class GetUser extends AsyncTask<Void, Integer, String> {


        private ArrayList<Login>mSyms=new ArrayList<>();
        private int _Status=0;
        private int ID;
        private int _from;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Intent i=getIntent();
            ID=pref.getWorkID();
            Intent ox=getIntent();
            _from=ox.getIntExtra("from",0);
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
                if (res != null) {
                    try {
                        JSONObject jsonObj = new JSONObject(res);


                        JSONArray stats = jsonObj.getJSONArray("Oxygen");
                        for (int i = 0; i < stats.length(); i++) {
                            JSONObject c = stats.getJSONObject(i);
                            if (!c.isNull("Oxygen")) {
                                Login item = new Login();
                                item.setOxygen(c.getDouble("Oxygen"));
                                item.setBreaths(c.getInt("Breaths"));
                                item.setTemperature(c.getDouble("temperature"));
                                item.setPulse(c.getInt("pulse"));
                                mSyms.add(item);
                            }
                        }

                    } catch (final JSONException e) {
                        Log.e("HI", "Json parsing error: " + e.getMessage());
                    }

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

                if(mSyms.size()>0) {
                    setData(mSyms.size(), mSyms,_from);
                    chart.invalidate();
                }

        }

    }

    private void setData(int count, ArrayList<Login> mSyms, int _from) {

        float start = 0f;

        ArrayList<BarEntry> values = new ArrayList<>();

        for (int i = (int) 0; i < count; i++) {
            float val = 0;
            if(_from==1) {
                 val = (float) mSyms.get(i).getOxygen(i);
            }else if (_from == 2) {
                 val = (float) mSyms.get(i).getBreaths(i);
            }else  if (_from == 3) {
                val = (float) mSyms.get(i).getTemperature(i);
            }else if (_from == 4) {
                val = (float) mSyms.get(i).getPulse(i);
            }

            values.add(new BarEntry(i, val));

        }

        BarDataSet set1 = null;

            if (_from == 1) {
                set1 = new BarDataSet(values, "Oxygen trend");
            }else if (_from == 2) {
                set1 = new BarDataSet(values, "Breaths trend");
            }else  if (_from == 3) {
                set1 = new BarDataSet(values, "Temperature trend");
            }else if (_from == 4) {
                set1 = new BarDataSet(values, "Pulse trend");
            }

            set1.setDrawIcons(false);


            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);
            BarData data = new BarData(dataSets);
            data.setValueTextSize(18f);
            data.setBarWidth(0.9f);
            chart.setData(data);

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
                    Intent o = new Intent(History.this, Account_settings.class);
                    startActivity(o);
                    overridePendingTransition(R.anim.slide_up1, R.anim.slide_down1);
                } else {
                    Intent o = new Intent(History.this, ServiceOffer.class);
                    startActivity(o);

                    overridePendingTransition(R.anim.slide_up1, R.anim.slide_down1);
                }
                break;
            case R.id.action_noti:
                Intent o = new Intent(History.this, NotificationAll.class);
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