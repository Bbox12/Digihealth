package com.admin.ecosense.POD;

import android.content.Context;
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
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.admin.ecosense.Adapters._symsAdapter;
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

public class L2Report extends Fragment implements View.OnClickListener {

    private PrefManager pref;
    private String _PhoneNo;
    private RecyclerView Sosrv;
    private ProgressBar progressBar;
    private CoordinatorLayout coordinatorLayout;
    private AutoCompleteTextView _date,_time;
    private TextInputLayout _tDate,_tTime;
    private TextView textView101;

    public L2Report() {
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
        Sosrv.setNestedScrollingEnabled(false);
        progressBar=V.findViewById(R.id.progressBaremergency);
        coordinatorLayout=V.findViewById(R.id.em);
        _date=V.findViewById(R.id.autodate);
        _time=V.findViewById(R.id.autotime);
        _tDate=V.findViewById(R.id.textinputdate);
        _tTime=V.findViewById(R.id.textinputtime);
        _tDate.setVisibility(View.VISIBLE);
        _tTime.setVisibility(View.VISIBLE);
        _date.setOnClickListener(this);
        _time.setOnClickListener(this);
        textView101=V.findViewById(R.id.textView101);
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
                            JSONArray Contacts = jsonObj.getJSONArray("podl2report");
                            if(Contacts.length()>0){
                                for (int j = 0; j < Contacts.length(); j++) {

                                    JSONObject c = Contacts.getJSONObject(j);
                                    Qns item = new Qns();
                                    item.setID(c.getInt("ID"));
                                    item.setSymID(c.getInt("symID"));
                                    item.setSymptoms(c.getString("Symptoms"));
                                    item.setDate(c.getString("Date"));
                                    mDates.add(c.getString("Date"));
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
                                            if (mFilter.size() != 0) {
                                                progressBar.setVisibility(View.GONE);
                                                Sosrv.setVisibility(View.VISIBLE);
                                                L2Adapter sAdapter = new L2Adapter(getActivity(), mFilter);
                                                sAdapter.notifyDataSetChanged();
                                                Sosrv.setAdapter(sAdapter);
                                                LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                                                mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                                                Sosrv.setLayoutManager(mLayoutManager);

                                            } else {
                                                textView101.setVisibility(View.VISIBLE);
                                                progressBar.setVisibility(View.GONE);
                                                Sosrv.setVisibility(View.GONE);
                                            }
                                        }
                                    });
                                }
                            });
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
