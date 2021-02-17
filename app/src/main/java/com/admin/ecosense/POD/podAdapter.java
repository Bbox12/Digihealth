package com.admin.ecosense.POD;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.admin.ecosense.Activities.Account_settings;
import com.admin.ecosense.Doctors.SessionHistory;
import com.admin.ecosense.Doctors.StartSession;
import com.admin.ecosense.Questions.Questions;
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
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class podAdapter extends RecyclerView.Adapter<podAdapter.ViewHolder> {

    // The items to display in your RecyclerView
    private ArrayList<Qns> mItems;
    private Context mContext;
    private Button submit1;
    private int selectedPosition=-1;
    private PrefManager pref;
    private CoordinatorLayout coordinatorLayout;


    public podAdapter(Context aContext, ArrayList<Qns> mItems) {
        this.mItems = mItems;
        this.mContext=aContext;

    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public podAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        //More to come
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.podadapter, viewGroup, false);
        ViewHolder viewHolder = new podAdapter.ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final podAdapter.ViewHolder viewHolder, final int position) {
        final Qns album_pos = mItems.get(position);
        pref = new PrefManager(mContext);
        if (album_pos.getName(position) != null && !TextUtils.isEmpty(album_pos.getName(position))) {
            viewHolder._chck.setText(album_pos.getName(position));
            viewHolder.servcie_pic.setText(String.valueOf(album_pos.getName(position).charAt(0)));
        }

        go(album_pos.getID(position),viewHolder.title,viewHolder.status,viewHolder.addreport,viewHolder.addsession);


        viewHolder.profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pref.setWorkID(album_pos.getID(position));
                Intent i = new Intent(mContext, Account_settings.class);
                i.putExtra("from",2);
                i.putExtra("mobile",album_pos.getPhoneNo(position));
                mContext.startActivity(i);
                ((Activity) mContext).overridePendingTransition(R.anim.slide_out2, R.anim.slide_in2);

            }
        });

        viewHolder.statusmedical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, StatusHistory.class);
                i.putExtra("from",1);
                pref.setPODmobile(String.valueOf(album_pos.getID(position)));
                mContext.startActivity(i);
                ((Activity) mContext).overridePendingTransition(R.anim.slide_out2, R.anim.slide_in2);

            }
        });

        viewHolder.addreport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent i = new Intent(mContext, PODQuestion.class);
                    pref.setPODmobile(String.valueOf(album_pos.getID(position)));
                    mContext.startActivity(i);
                    ((Activity) mContext).overridePendingTransition(R.anim.slide_out2, R.anim.slide_in2);


            }
        });


        viewHolder.addsession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pref.setWorkID(album_pos.getID(position));
                if(pref.getConsultation()==0){
                    Intent i = new Intent(mContext, SessionHistory.class);
                    mContext.startActivity(i);
                    ((Activity) mContext).overridePendingTransition(R.anim.slide_out2, R.anim.slide_in2);
                }else {
                    Intent i = new Intent(mContext, StartSession.class);
                    mContext.startActivity(i);
                    ((Activity) mContext).overridePendingTransition(R.anim.slide_out2, R.anim.slide_in2);
                }

            }
        });


    }

    private void go(final int ID, final TextView title, final TextView status, final AppCompatCheckBox addreport, final AppCompatCheckBox addsession) {
         final ArrayList<Qns> mDocs=new ArrayList<Qns>();
        StringRequest eventoReq = new StringRequest(com.android.volley.Request.Method.POST, Config_URL.GET_LOGIN,
                new Response.Listener<String>() {
                    private String _nextdate,_nextTime;

                    @Override
                    public void onResponse(String response) {
                        Log.w("ccc", response.toString());
                        try{
                            JSONObject jsonObj = new JSONObject(response);
                            JSONArray Contacts = jsonObj.getJSONArray("getpodtitle");

                            if(Contacts.length()>0){

                                for (int j = 0; j < Contacts.length(); j++) {

                                    JSONObject c = Contacts.getJSONObject(j);
                                    Qns item = new Qns();
                                    item.setDoctor(c.getString("Doctor"));
                                    item.setDigiCategory(c.getString("DigiCategory"));
                                    item.setIDDoc(c.getInt("IDDoc"));
                                    mDocs.add(item);
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(mDocs.size()>0) {
                            if (mDocs.get(0).getDigiCategory(0) != null && !TextUtils.isEmpty(mDocs.get(0).getDigiCategory(0) )) {
                              title.setText("Placed in " + mDocs.get(0).getDigiCategory(0) );
                            }
                            if (mDocs.get(0).getIDDoc(0)  != 0) {
                                pref.setConsultation(mDocs.get(0).getIDDoc(0));
                                addsession.setTextColor(Color.BLACK);
                                addsession.setText("Active Session");
                                addreport.setText("Update health data");
                                status.setText("Consulting Doctor " + mDocs.get(0).getDoctor(0) );
                            } else {
                                status.setText("In waiting room");
                            }
                        }else{
                            title.setVisibility(View.GONE);
                            status.setText("No consultation");
                        }

                    }



                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, "Network is unreachable. Please try another time", Snackbar.LENGTH_LONG)
                            ;
                    snackbar.setActionTextColor(Color.RED);
                    snackbar.show();
                } else if (error instanceof AuthFailureError) {
                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, "Network is unreachable. Please try another time", Snackbar.LENGTH_LONG)
                           ;
                    snackbar.setActionTextColor(Color.RED);
                    snackbar.show();
                } else if (error instanceof ServerError) {
                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, "Server Error.Please try another time", Snackbar.LENGTH_LONG)
                          ;
                    snackbar.setActionTextColor(Color.RED);
                    snackbar.show();
                } else if (error instanceof NetworkError) {
                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, "Network Error. Please try another time", Snackbar.LENGTH_LONG)
                            ;
                    snackbar.setActionTextColor(Color.RED);
                    snackbar.show();
                } else if (error instanceof ParseError) {
                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, "Data Error. Please try another time", Snackbar.LENGTH_LONG)
                           ;
                    snackbar.setActionTextColor(Color.RED);
                    snackbar.show();
                }


            }

        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("_mId", String.valueOf(ID));
                return params;
            }
        };

        // Añade la peticion a la cola
        AppController.getInstance().addToRequestQueue(eventoReq);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void setButton(Button submit2) {
        submit1=submit2;
    }

    public void setCoordinator(CoordinatorLayout coordinatorLayout1) {
        coordinatorLayout=coordinatorLayout1;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {



        private RelativeLayout _rl;
        private TextView _chck,servcie_pic;
        private AppCompatCheckBox profile,statusmedical,addreport,addsession;
        private TextView title,status;

        public ViewHolder(View itemView) {
            super(itemView);
            _chck = itemView.findViewById(R.id.name);
            servcie_pic = itemView.findViewById(R.id.service_pic);
            profile=itemView.findViewById(R.id.profile);
            statusmedical=itemView.findViewById(R.id.statusmedical);
            addreport=itemView.findViewById(R.id.addreport);
            title=itemView.findViewById(R.id.title);
            status=itemView.findViewById(R.id.status);
            addsession=itemView.findViewById(R.id.addsession);
        }

    }



}





