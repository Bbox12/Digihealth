package com.admin.ecosense.POD;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.admin.ecosense.R;
import com.admin.ecosense.helper.AppController;
import com.admin.ecosense.helper.Config_URL;
import com.admin.ecosense.helper.Login;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class L3Report extends Fragment {

    private PrefManager pref;
    private String _PhoneNo;
    private RecyclerView Sosrv;
    private ProgressBar progressBar;
    private CoordinatorLayout coordinatorLayout;
    private HorizontalScrollView _L2;
    private TextView textView101;

    public L3Report() {
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
        _L2=V.findViewById(R.id._L3);
        textView101=V.findViewById(R.id.textView101);
        return V;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onResume() {
        super.onResume();
        go();
    }

    private void go() {
        final ArrayList<Login> mItems = new ArrayList<Login>();
        progressBar.setVisibility(View.VISIBLE);
        StringRequest eventoReq = new StringRequest(com.android.volley.Request.Method.POST, Config_URL.GET_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.w("ccc", response.toString());
                        progressBar.setVisibility(View.GONE);
                        try{
                            JSONObject jsonObj = new JSONObject(response);
                            JSONArray Contacts = jsonObj.getJSONArray("podl3report");

                            if(Contacts.length()>0){
                                for (int j = 0; j < Contacts.length(); j++) {

                                    JSONObject c = Contacts.getJSONObject(j);
                                    Login item = new Login();
                                    item.setID(c.getInt("ID"));
                                    item.setOxygen(c.getDouble("Oxygen"));
                                    item.setBreaths(c.getInt("Breaths"));
                                    item.setTemperature(c.getDouble("temperature"));
                                    item.setPulse(c.getInt("pulse"));
                                    item.setSpeech(c.getInt("Issues"));
                                    item.setDate(c.getString("Date"));
                                    item.setTime(c.getString("Time"));
                                    mItems.add(item);

                                }



                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (mItems.size() != 0) {
                            textView101.setVisibility(View.GONE);
                            _L2.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                            Sosrv.setVisibility(View.VISIBLE);
                            L3Adapter sAdapter = new L3Adapter(getActivity(), mItems);
                            sAdapter.notifyDataSetChanged();
                            Sosrv.setAdapter(sAdapter);
                            LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                            mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                            Sosrv.setLayoutManager(mLayoutManager);

                        } else {
                            textView101.setVisibility(View.VISIBLE);
                            _L2.setVisibility(View.GONE);
                            progressBar.setVisibility(View.GONE);
                            Sosrv.setVisibility(View.GONE);
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
