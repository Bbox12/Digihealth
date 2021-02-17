package com.admin.ecosense.POD;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.admin.ecosense.Activities.Wb1_access;
import com.admin.ecosense.Doctors.PatientDetails;
import com.admin.ecosense.R;
import com.admin.ecosense.helper.AppController;
import com.admin.ecosense.helper.Config_URL;
import com.admin.ecosense.helper.PrefManager;
import com.admin.ecosense.helper.Qns;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class L4Report extends Fragment implements View.OnClickListener {

    private PrefManager pref;
    private String _PhoneNo;
    private RecyclerView Sosrv;
    private ProgressBar progressBar;
    private CoordinatorLayout coordinatorLayout;
    private AutoCompleteTextView _date,_time;
    private TextInputLayout _tDate,_tTime;
    private EditText ldh, crp, Ferritin, Lymphocytes, count, dimer, interleukin, PCT;
    private LinearLayout _L4;
    private TextView textView101,image_1;
    private String _report;

    public L4Report() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View V = inflater.inflate(R.layout.l1report, container, false);
        pref = new PrefManager(getActivity());
        HashMap<String, String> user = pref.getUserDetails();
        _PhoneNo = user.get(PrefManager.KEY_MOBILE);
        Sosrv = V.findViewById(R.id.all_fragment_rv2);
        Sosrv.setVisibility(View.GONE);
        progressBar=V.findViewById(R.id.progressBaremergency);
        coordinatorLayout=V.findViewById(R.id.em);
        _date=V.findViewById(R.id.autodate);
        _time=V.findViewById(R.id.autotime);
        _tDate=V.findViewById(R.id.textinputdate);
        _tTime=V.findViewById(R.id.textinputtime);
        _tDate.setVisibility(View.VISIBLE);
        _tTime.setVisibility(View.VISIBLE);
        _L4=V.findViewById(R.id._L4);
        _date.setOnClickListener(this);
        _time.setOnClickListener(this);
        ldh = V.findViewById(R.id.ldh);
        crp = V.findViewById(R.id.crp);
        Ferritin = V.findViewById(R.id.Ferritin);
        Lymphocytes = V.findViewById(R.id.Lymphocytes);
        count = V.findViewById(R.id.count);
        dimer = V.findViewById(R.id.dimer);
        interleukin = V.findViewById(R.id.interleukin);
        PCT = V.findViewById(R.id.PCT);
        textView101=V.findViewById(R.id.textView101);
        image_1=V.findViewById(R.id.report);
        image_1.setOnClickListener(this);
        return V;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.autodate:
                _date.showDropDown();
                break;
            case R.id.autotime:
                _time.showDropDown();
                break;

            case R.id.report:
                if(_report!=null) {
                    Intent askwho = new Intent(getActivity(), Wb1_access.class);
                    askwho.putExtra("from", 5);
                    askwho.putExtra("links", _report);
                    startActivity(askwho);

                }

            default:break;
        }

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onPause() {
        super.onPause();
        _date.setText("");
        _time.setText("");
    }

    @Override
    public void onStop() {
        super.onStop();
        _date.setText("");
        _time.setText("");
    }

    @Override
    public void onResume() {
        super.onResume();
        go();
    }

    private void go() {
        final ArrayList<Qns> mItems = new ArrayList<Qns>();
        final ArrayList<String> mDates = new ArrayList<String>();
        final ArrayList<String> mTimes = new ArrayList<String>();
        final ArrayList<Qns> mFilter = new ArrayList<Qns>();
        progressBar.setVisibility(View.VISIBLE);
        StringRequest eventoReq = new StringRequest(com.android.volley.Request.Method.POST, Config_URL.GET_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.w("ccc", response.toString());
                        progressBar.setVisibility(View.GONE);
                        try{
                            JSONObject jsonObj = new JSONObject(response);
                            JSONArray Contacts = jsonObj.getJSONArray("podl4report");

                            if(Contacts.length()>0){
                                for (int j = 0; j < Contacts.length(); j++) {

                                    JSONObject c = Contacts.getJSONObject(j);
                                    Qns item = new Qns();
                                    item.setID(c.getInt("ID"));
                                    item.setLDH(c.getDouble("LDH"));
                                    item.setCRP(c.getDouble("CRP"));
                                    item.setFerritin(c.getDouble("Ferritin"));
                                    item.setLymphocytes(c.getDouble("Lymphocytes"));
                                    item.setCount(c.getDouble("Count"));
                                    item.setDDimer(c.getDouble("DDimer"));
                                    item.setPCT(c.getDouble("PCT"));
                                    item.setLDH(c.getDouble("LDH"));
                                    item.setDate(c.getString("Date"));
                                    mDates.add(c.getString("Date"));
                                    item.setPhoto(c.getString("Photo"));
                                    item.setTime(c.getString("Time"));
                                    mItems.add(item);

                                }
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if(mDates.size()>0) {
                            textView101.setVisibility(View.GONE);
                            progressBar.setVisibility(View.GONE);
                            Set<String> set1 = new HashSet<>(mDates);
                            mDates.clear();
                            mDates.addAll(set1);
                            mDates.trimToSize();
                            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                                    getActivity(), android.R.layout.simple_list_item_1, mDates);
                            _date.setAdapter(arrayAdapter);
                            _date.setCursorVisible(false);
                            _date.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                private String selecteddate,selectedtime;

                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    selecteddate= (String) parent.getItemAtPosition(position);
                                    for(int i=0;i<mItems.size();i++){

                                        if(mItems.get(i).getDate(i).contains(selecteddate))
                                            mTimes.add(mItems.get(i).getTime(i));
                                    }
                                    Set<String> set1 = new HashSet<>(mTimes);
                                    mTimes.clear();
                                    mTimes.addAll(set1);
                                    mTimes.trimToSize();
                                    final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                                            getActivity(), android.R.layout.simple_list_item_1, mTimes);
                                    _time.setAdapter(arrayAdapter);
                                    _time.setCursorVisible(false);
                                    _time.showDropDown();
                                    _time.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            selectedtime = (String) parent.getItemAtPosition(position);
                                            mFilter.clear();
                                            for(int i=0;i<mItems.size();i++){
                                                if(mItems.get(i).getDate(i).contains(selecteddate) && mItems.get(i).getTime(i).contains(selectedtime))
                                                    mFilter.add(mItems.get(i));
                                            }
                                            if(mFilter.size()>0){
                                                _L4.setVisibility(View.VISIBLE);
                                                ldh.setText(String.valueOf(mFilter.get(0).getLDH(0)));
                                                crp.setText(String.valueOf(mFilter.get(0).getCRP(0)));
                                                Ferritin.setText(String.valueOf(mFilter.get(0).getFerritin(0)));
                                                Lymphocytes.setText(String.valueOf(mFilter.get(0).getLymphocytes(0)));
                                                count.setText(String.valueOf(mFilter.get(0).getCount(0)));
                                                dimer.setText(String.valueOf(mFilter.get(0).getDDimer(0)));
                                                interleukin.setText(String.valueOf(mFilter.get(0).getInterleukin(0)));
                                                PCT.setText(String.valueOf(mFilter.get(0).getPCT(0)));
                                                interleukin.setText(String.valueOf(mFilter.get(0).getInterleukin(0)));
                                                if(!TextUtils.isEmpty(mFilter.get(0).getPhoto(0))) {
                                                    _report =mFilter.get(0).getPhoto(0);
                                                }else{
                                                    image_1.setVisibility(View.GONE);
                                                }
                                            }else{
                                                _L4.setVisibility(View.GONE);
                                            }
                                        }
                                    });
                                }
                            });
                        }else{
                            textView101.setVisibility(View.VISIBLE);
                        }


                    }



                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                vollyError(error);
            }

        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("_mId", pref.getPODmobile());
                return params;
            }
        };

        // Añade la peticion a la cola
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


}
