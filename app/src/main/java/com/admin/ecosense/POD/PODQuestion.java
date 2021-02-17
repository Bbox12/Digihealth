package com.admin.ecosense.POD;

import android.app.Activity;
import android.app.Dialog;
import android.app.UiModeManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
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
import com.admin.ecosense.Activities.Splash_screen;
import com.admin.ecosense.Adapters._questionAdapter;
import com.admin.ecosense.Adapters._symsAdapter;
import com.admin.ecosense.Questions.Questions;
import com.admin.ecosense.R;
import com.admin.ecosense.helper.AppController;
import com.admin.ecosense.helper.Config_URL;
import com.admin.ecosense.helper.Login;
import com.admin.ecosense.helper.PrefManager;
import com.admin.ecosense.helper.Qns;
import com.admin.ecosense.helper.RecyclerTouchListener;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PODQuestion extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {
    private SwipeRefreshLayout swipeRefreshLayout;
    private CoordinatorLayout coordinatorLayout;
    private DatabaseReference mDatabase;
    private PrefManager pref;
    private String _PhoneNo;
    private ViewPager viewPager;
    private PODQuestion.ViewPagerAdapter adapter;
    private TextView _Q1,_Q2,_Q3,waqfno,proceed,iam;
    private AppCompatCheckBox _cashpay,_aid;
    private Button _contactsubmit,patientsubmit;
    private RecyclerView firstrv,secondrv,waqfrv,docrv,searchdocrv;
    private int _first=0,_second=0;
    private int _options=0;
    private String _waqf="";
    private EditText searchdoc,patientno;
    private int _doc;
    private ImageView contactus;
    private ListView listView;
    private String _patientno="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.questions);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Questionnaire");
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        //toolbar.setTitle("Questionnaire");
        coordinatorLayout=findViewById(R.id.coordinatorLayout2);
        pref=new PrefManager(getApplicationContext());
        HashMap<String, String> user = pref.getUserDetails();
        _PhoneNo = pref.getPODmobile();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        swipeRefreshLayout =  findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setRefreshing(true);
        viewPager = findViewById(R.id.viewPagerVertical);
        adapter = new ViewPagerAdapter();
        viewPager.setAdapter(adapter);
        _Q2=findViewById(R.id._q2);
        _cashpay=findViewById(R.id._cashpay);
        _aid=findViewById(R.id._aid);


        _contactsubmit=findViewById(R.id.submit);
        _contactsubmit.setOnClickListener(this);

        patientsubmit=findViewById(R.id.patientsubmit);
        patientsubmit.setOnClickListener(this);

        firstrv=findViewById(R.id.firstrv);
        secondrv=findViewById(R.id.secondrv);
        firstrv.setNestedScrollingEnabled(false);
        secondrv.setNestedScrollingEnabled(false);
        waqfrv=findViewById(R.id.waqfrv);
        waqfrv.setNestedScrollingEnabled(true);
        waqfno=findViewById(R.id.waqfno);

        docrv=findViewById(R.id.docrv);
        docrv.setNestedScrollingEnabled(false);
        searchdoc=findViewById(R.id.searchdoc);

        searchdocrv=findViewById(R.id.searchdocrv);
        searchdocrv.setNestedScrollingEnabled(false);
        listView=findViewById(R.id.listView);

        contactus=findViewById(R.id.contactus);
        contactus.setOnClickListener(this);
        patientno=findViewById(R.id.patientno);

        proceed=findViewById(R.id.proceed);
        proceed.setOnClickListener(this);

        _cashpay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    _aid.setChecked(false);
                    pref.setPaid(1);
                    viewPager.setCurrentItem(5);
                }
            }
        });
        _aid.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    _cashpay.setChecked(false);
                    viewPager.setCurrentItem(5);
                    pref.setPaid(2);
                }
            }
        });


        swipeRefreshLayout.post(
                new Runnable() {
                    @Override
                    public void run() {
                        getInbox();
                    }
                }
        );

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);


            }
        });
       iam=findViewById(R.id.iam);
       iam.setText("Patient is:");

    }

    @Override
    protected void onResume() {
        super.onResume();
        getInbox();
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.submit:
                checkEmail();
                break;
            case R.id.proceed:
                _first=4;
                viewPager.setCurrentItem(5);
                break;

            case R.id.patientsubmit:
                if(!TextUtils.isEmpty(patientno.getText().toString())){
                    _patientno=patientno.getText().toString();
                }else{
                    patientno.setError("Empty");
                }
                break;

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
                    resId = R.id.first_pager;
                    break;
                case 1:
                    resId = R.id.second_pager_;
                    break;
                case 2:
                    resId = R.id.third_pager_;
                    break;
                case 3:
                    resId = R.id.fourth_pager_;
                    break;
                case 4:
                    resId = R.id.fifth_pager_;
                    break;
                case 5:
                    resId = R.id.sixth_pager_;
                    break;

            }
            return findViewById(resId);
        }
    }
    private void getInbox() {


        StringRequest eventoReq = new StringRequest(com.android.volley.Request.Method.POST, Config_URL.GET_LOGIN,
                new Response.Listener<String>() {
                    private String _userBday;

                    @Override
                    public void onResponse(String response) {

                        Log.w("details", response);

                        final ArrayList<Qns> mFirst=new ArrayList<Qns>();
                        final ArrayList<Qns> mSecond=new ArrayList<Qns>();
                        final ArrayList<Login> mOptions=new ArrayList<Login>();
                        final ArrayList<Login> mDoc=new ArrayList<Login>();
                        final ArrayList<String> mName=new ArrayList<String>();
                        try {

                            JSONObject jsonObj = new JSONObject(response);
                            JSONArray _Attendence = jsonObj.getJSONArray("login");
                            for (int i = 0; i < _Attendence.length(); i++) {
                                JSONObject c = _Attendence.getJSONObject(i);
                                if (!c.isNull("ID")) {
                                    pref.setWAQF("WAQF-"+String.format("%04d",c.getInt("ID")));
                                    _userBday=c.getString("Date_of_Birth");
                                }

                            }
                            pref.setDOB(_userBday);
                            SimpleDateFormat simpleDateformat=new SimpleDateFormat("yyyy");
                            try {
                                Date d2=simpleDateformat.parse(pref.getDate());
                                Date d1=simpleDateformat.parse(pref.getDOB());
                                pref.setAge(getYear(d1,d2));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }



                            JSONArray category = jsonObj.getJSONArray("firstlevel");
                            for (int i = 0; i < category.length(); i++) {
                                JSONObject c = category.getJSONObject(i);
                                if (!c.isNull("Question")) {
                                    Qns item=new Qns();
                                    item.setID(c.getInt("ID"));
                                    item.setQuestion(c.getString("Question"));
                                    item.setColors(c.getString("Color"));
                                    mFirst.add(item);
                                }
                            }

                            JSONArray secondlevel = jsonObj.getJSONArray("secondlevel");
                            for (int i = 0; i < secondlevel.length(); i++) {
                                JSONObject c = secondlevel.getJSONObject(i);
                                if (!c.isNull("Question")) {
                                    Qns item=new Qns();
                                    item.setID(c.getInt("ID"));
                                    item.setQuestion(c.getString("Question"));
                                    mSecond.add(item);
                                }
                            }

                            JSONArray nightingle = jsonObj.getJSONArray("nightingle");
                            for (int i = 0; i < nightingle.length(); i++) {
                                JSONObject c = nightingle.getJSONObject(i);
                                if (!c.isNull("Options")) {
                                    Login item=new Login();
                                    item.setID(c.getInt("ID"));
                                    item.setName(c.getString("Options"));
                                    mOptions.add(item);
                                }
                            }

                            JSONArray doctors = jsonObj.getJSONArray("doctors");
                            for (int i = 0; i < doctors.length(); i++) {
                                JSONObject c = doctors.getJSONObject(i);
                                if (!c.isNull("Name") ) {
                                    Login item=new Login();
                                    item.setID(c.getInt("ID"));
                                    item.setName(c.getString("Name"));
                                    mName.add(c.getString("Name"));
                                    item.setPractisenumber(c.getString("practisenumber"));
                                    mDoc.add(item);
                                }
                            }


                            getDoc(mDoc);

                            if (mName.size() != 0) {
                                Set<String> set = new HashSet<>(mName);
                                mName.clear();
                                mName.addAll(set);
                            }

                            if(pref.getWAQF()!=null){
                                waqfno.setText(pref.getWAQF());
                            }
                            final ArrayAdapter<String> serarchadapter = new ArrayAdapter<String>(PODQuestion.this, R.layout.list_item, R.id.product_name, mName);
                            searchdoc.addTextChangedListener(new TextWatcher() {

                                @Override
                                public void onTextChanged(CharSequence s, int start,
                                                          int before, int count) {
                                    if(s.toString().length()>1) {
                                        docrv.setVisibility(View.GONE);
                                        serarchadapter.getFilter().filter(s);
                                        listView.setAdapter(serarchadapter);
                                        listView.setVisibility(View.VISIBLE);

                                    }else{
                                        docrv.setVisibility(View.VISIBLE);
                                        listView.setVisibility(View.GONE);
                                        searchdocrv.setVisibility(View.GONE);
                                    }
                                }

                                @Override
                                public void afterTextChanged(Editable editable) {

                                }

                                @Override
                                public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                                }


                            });


                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {



                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    final ArrayList<Login> filteredModelList = new ArrayList<>();
                                    searchdoc.clearFocus();
                                    InputMethodManager in = (InputMethodManager)getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                                    in.hideSoftInputFromWindow(searchdoc.getWindowToken(), 0);
                                    listView.setVisibility(View.GONE);
                                    for (int i = 0; i < mDoc.size(); i++) {
                                        Login model = mDoc.get(i);
                                        final String text = model.getName(i).toLowerCase();
                                        String value = (String) parent.getItemAtPosition(position);
                                        if (text.contains(value.toLowerCase())) {
                                            filteredModelList.add(model);
                                        }
                                    }

                                    if(filteredModelList.size()!=0) {
                                        getDoc(filteredModelList);
                                    }


                                }
                            });


                            if (mOptions.size() > 0) {
                                _symsAdapter sAdapter = new _symsAdapter(PODQuestion.this, mOptions);
                                sAdapter.notifyDataSetChanged();
                                waqfrv.setVisibility(View.VISIBLE);
                                waqfrv.setItemAnimator(new DefaultItemAnimator());
                                waqfrv.setAdapter(sAdapter);
                                waqfrv.setHasFixedSize(true);
                                StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(getSpan(), StaggeredGridLayoutManager.VERTICAL);
                                waqfrv.setLayoutManager(mLayoutManager);
                                waqfrv.addOnItemTouchListener(
                                        new RecyclerTouchListener(PODQuestion.this, waqfrv,
                                                new RecyclerTouchListener.OnTouchActionListener() {


                                                    @Override
                                                    public void onClick(View view, final int position) {
                                                        Log.w("gallery", String.valueOf(position));
                                                        if(position>=0) {
                                                            if (mOptions.size() != 0 && mOptions.get(position).getName(position) != null) {
                                                                viewPager.setCurrentItem(5);
                                                                _options=mOptions.get(position).getID(position);
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

                            if (mFirst.size() > 0) {
                                _questionAdapter sAdapter = new _questionAdapter(PODQuestion.this, mFirst);
                                sAdapter.notifyDataSetChanged();
                                firstrv.setVisibility(View.VISIBLE);
                                firstrv.setItemAnimator(new DefaultItemAnimator());
                                firstrv.setAdapter(sAdapter);
                                firstrv.setHasFixedSize(true);
                                LinearLayoutManager mHorizontal = new LinearLayoutManager(PODQuestion.this, LinearLayoutManager.VERTICAL, false);
                                firstrv.setLayoutManager(mHorizontal);
                                firstrv.addOnItemTouchListener(
                                        new RecyclerTouchListener(PODQuestion.this, firstrv,
                                                new RecyclerTouchListener.OnTouchActionListener() {


                                                    @Override
                                                    public void onClick(View view, final int position) {
                                                        Log.w("gallery", String.valueOf(position));
                                                        if(position>=0) {
                                                            if (mFirst.size() != 0 && mFirst.get(position).getQuestion(position) != null) {

                                                                _first=mFirst.get(position).getID(position);
                                                                if(_first==1){
                                                                    viewPager.setCurrentItem(2);
                                                                }else if(_first==2){
                                                                    viewPager.setCurrentItem(3);
                                                                }else if(_first==3){
                                                                    viewPager.setCurrentItem(4);
                                                                }else if(_first==4){
                                                                    viewPager.setCurrentItem(1);
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
                                _questionAdapter sAdapter = new _questionAdapter(PODQuestion.this, mSecond);
                                sAdapter.notifyDataSetChanged();
                                secondrv.setVisibility(View.VISIBLE);
                                secondrv.setItemAnimator(new DefaultItemAnimator());
                                secondrv.setAdapter(sAdapter);
                                secondrv.setHasFixedSize(true);
                                LinearLayoutManager mHorizontal = new LinearLayoutManager(PODQuestion.this, LinearLayoutManager.VERTICAL, false);
                                secondrv.setLayoutManager(mHorizontal);
                                secondrv.addOnItemTouchListener(
                                        new RecyclerTouchListener(PODQuestion.this, secondrv,
                                                new RecyclerTouchListener.OnTouchActionListener() {


                                                    @Override
                                                    public void onClick(View view, final int position) {
                                                        Log.w("gallery", String.valueOf(position));
                                                        if(position>=0) {
                                                            if (mSecond.size() != 0 && mSecond.get(position).getQuestion(position) != null) {
                                                                _second=mSecond.get(position).getID(position);
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

                                swipeRefreshLayout.setRefreshing(false);

                            }
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
                params.put("_mId", _PhoneNo);
                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(eventoReq);
    }

    private void getDoc(final ArrayList<Login> filteredModelList) {
        if (filteredModelList.size() > 0) {
            _symsAdapter sAdapter = new _symsAdapter(PODQuestion.this, filteredModelList);
            sAdapter.notifyDataSetChanged();
            sAdapter.setFrom(2);
            docrv.setVisibility(View.VISIBLE);
            docrv.setItemAnimator(new DefaultItemAnimator());
            docrv.setAdapter(sAdapter);

            docrv.setHasFixedSize(true);
            LinearLayoutManager mHorizontal = new LinearLayoutManager(PODQuestion.this, LinearLayoutManager.VERTICAL, false);
            docrv.setLayoutManager(mHorizontal);
            docrv.addOnItemTouchListener(
                    new RecyclerTouchListener(PODQuestion.this, waqfrv,
                            new RecyclerTouchListener.OnTouchActionListener() {


                                @Override
                                public void onClick(View view, final int position) {
                                    Log.w("gallery", String.valueOf(position));
                                    if(position>=0) {
                                        if (filteredModelList.size() != 0 && filteredModelList.get(position).getName(position) != null) {
                                            viewPager.setCurrentItem(5);
                                            _doc=filteredModelList.get(position).getID(position);
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
    }

    int getYear(Date date1,Date date2){
        SimpleDateFormat simpleDateformat=new SimpleDateFormat("yyyy");
        Integer.parseInt(simpleDateformat.format(date1));

        return Integer.parseInt(simpleDateformat.format(date2))- Integer.parseInt(simpleDateformat.format(date1));

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


    public int getSpan() {
        int orientation = getScreenOrientation(PODQuestion.this);
        if (isTV(PODQuestion.this)) return 4;
        if (isTablet(PODQuestion.this))
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


    private void checkEmail() {
        if(_first==1){
            _waqf=pref.getWAQF();

        }else{
            pref.setWAQF(null);
        }

        StringRequest eventoReq = new StringRequest(com.android.volley.Request.Method.POST, Config_URL.URL_STORE_QUESTIONS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.w("kk", response.toString());


                        String[] par = response.split("error");
                        if (par[1].contains("false")) {
                            pref.setFirstQuestion(_first);
                            PODQuestion.ViewDialogFailedSuccess alert = new PODQuestion.ViewDialogFailedSuccess();
                            alert.showDialog(PODQuestion.this, "Success!", false);
                            mDatabase.child("Patient").child(String.valueOf(_PhoneNo)).child("Mobile").setValue(_PhoneNo);
                            mDatabase.child("Patient").child(String.valueOf(_PhoneNo)).child("first").setValue(String.valueOf(_first));
                            mDatabase.child("Patient").child(String.valueOf(_PhoneNo)).child("RegID").setValue(pref.getReferalCode());
                        }else{
                            PODQuestion.ViewDialogFailed alert = new PODQuestion.ViewDialogFailed();
                            alert.showDialog(PODQuestion.this, "Please check the information provided!", true);
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
                params.put("first", String.valueOf(_first));
                params.put("second", String.valueOf(_second));
                params.put("waqf", _waqf);
                params.put("option", String.valueOf(_options));
                params.put("pmode", String.valueOf(pref.getPaid()));
                params.put("_doc", String.valueOf(_doc));
                params.put("patientfile", String.valueOf(_patientno));
                return params;
            }

        };

        // Añade la peticion a la cola
        AppController.getInstance().addToRequestQueue(eventoReq);

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
                        dialog1.cancel();
                        if(_second!=0) {
                            Intent i = new Intent(PODQuestion.this, PODConsultation.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);

                            overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                        }else{
                            Intent o = new Intent(PODQuestion.this, MainActivity.class);
                            startActivity(o);
                            overridePendingTransition(R.anim.slide_up1, R.anim.slide_down1);

                        }


                    }
                });

                dialog1.show();


            }
        }
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
            if(viewPager.getCurrentItem()==0) {
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