package com.admin.ecosense.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.admin.ecosense.R;
import com.admin.ecosense.helper.AppController;
import com.admin.ecosense.helper.Config_URL;
import com.admin.ecosense.helper.Login;
import com.admin.ecosense.helper.PrefManager;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
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

public class All_fragment_adapter extends RecyclerView.Adapter<All_fragment_adapter.ViewHolder> {

    private LayoutInflater ashit;
    private ArrayList<Login> mService;
    private PrefManager pref;
    private TextView _iValue;
    private int _ID=0;
    private LinearLayout L1;
    private Context mContext;
    private CoordinatorLayout coordinatorLayout;
    private LinearLayout layoutBottomSheet;
    private int _from;
    private String _phoneNo;

    public All_fragment_adapter(Context acontext, ArrayList<Login> mService){
        this.mService=mService;
        this.mContext=acontext;



    }
    @Override
    public All_fragment_adapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        //More to come
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.all_fragment_first_rv, viewGroup, false);
        ViewHolder viewHolder = new All_fragment_adapter.ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        viewHolder.hadapter1.setNestedScrollingEnabled(true);



            viewHolder.Name.setText(mService.get(position).getName(position));
            go(mContext,mService.get(position).getID(position),viewHolder.hadapter1,viewHolder.textView102,viewHolder._R1);

        viewHolder.hadapter1.setNestedScrollingEnabled(false);

        
    }



    @Override
    public int getItemCount() {
        return mService == null ? 0 : mService.size();
    }


    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public void setCoordinatorlayout(CoordinatorLayout coordinatorLayout1) {
        coordinatorLayout=coordinatorLayout1;
    }

    public void setFrom(int i) {
        _from=i;

    }


    public class ViewHolder extends RecyclerView.ViewHolder {


        private EditText Name,Pin;
        private TextView textView102;
        private RecyclerView hadapter1;
        private LinearLayout _R1;

        public ViewHolder(View itemView) {
            super(itemView);
            Name = (EditText) itemView.findViewById(R.id.my_service_all);
            hadapter1= (RecyclerView) itemView.findViewById(R.id.all_fragment_rv2);
            _R1 =  itemView.findViewById(R.id._L1);
            textView102=itemView.findViewById(R.id.textView102);

        }

    }





    public void setPref(PrefManager pref1) {
        pref = pref1;
    }

    public void setValues(TextView itemValue) {
        _iValue = itemValue;
    }

    private void go(final Context mContext, final int itemmenu, final RecyclerView hadapter1, final TextView _R1, final LinearLayout _L1) {

        pref=new PrefManager(mContext);
        HashMap<String, String> user=pref.getUserDetails();
        _phoneNo=user.get(PrefManager.KEY_MOBILE);

        final ArrayList<Login>mItems=new ArrayList<Login>();
        StringRequest eventoReq = new StringRequest(com.android.volley.Request.Method.POST, Config_URL.GET_LOGIN,
                new Response.Listener<String>() {
                    private JSONArray Eat;

                    @Override
                    public void onResponse(String response) {
                 mItems.clear();
                        Log.e("submenu", response);
                        try {

                            JSONObject jsonObj = new JSONObject(response);
                            if(_from==0) {
                                 Eat = jsonObj.getJSONArray("getpatient");
                            }else if(_from==1) {
                                Eat = jsonObj.getJSONArray("getpatientssss");
                            }else if(_from==2) {
                                Eat = jsonObj.getJSONArray("futurepatientssss");
                            }
                            if (Eat.length() != 0) {
                                for (int i = 0; i < Eat.length(); i++) {
                                    JSONObject c = Eat.getJSONObject(i);
                                    if (!c.isNull("Name")) {
                                        Login item = new Login();
                                        item.setID(c.getInt("ID"));
                                        item.setName(c.getString("Name"));
                                        item.set_Phone_no(c.getString("pMobile"));
                                        item.setGender(c.getString("Gender"));
                                        item.setEmail(c.getString("Email"));
                                        item.setPhoto(c.getString("Photo"));
                                        item.setBday(c.getString("DOB"));
                                        item.setPatientID(c.getString("IDPatient"));
                                        item.setRiskFactor(c.getString("RiskFactor"));
                                        item.setWAQF(c.getString("WAQF"));
                                        item.setDigiCategory(c.getString("DigiCategory"));
                                         mItems.add(item);
                                    }
                                }

                            }

                         


                        } catch (final JSONException e) {
                            Log.e("HI", "Json parsing error: " + e.getMessage());


                        }

                        if(mItems.size()>0) {
                            _L1.setVisibility(View.VISIBLE);
                            PatientAdapter hadapter = new PatientAdapter(mContext, mItems);
                            hadapter.setFrom(_from);
                            LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext);
                            mLayoutManager.setOrientation(RecyclerView.VERTICAL);
                            hadapter.notifyDataSetChanged();
                            hadapter.setPref(pref);
                            hadapter1.setVisibility(View.VISIBLE);
                            hadapter1.setItemAnimator(new DefaultItemAnimator());
                            hadapter1.setAdapter(hadapter);
                            hadapter1.setLayoutManager(mLayoutManager);
                        }else{
                            hadapter1.setVisibility(View.GONE);
                            _R1.setVisibility(View.VISIBLE);
                            _L1.setVisibility(View.GONE);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("uuu", "Error: " + error.getMessage());
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, "Network is unreachable. Please try another time", Snackbar.LENGTH_INDEFINITE)
                            .setAction("Refresh", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    
                                }
                            });
                    snackbar.setActionTextColor(Color.RED);
                    snackbar.show();
                } else if (error instanceof AuthFailureError) {
                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, "Network is unreachable. Please try another time", Snackbar.LENGTH_INDEFINITE)
                            .setAction("Refresh", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    
                                }
                            });
                    snackbar.setActionTextColor(Color.RED);
                    snackbar.show();
                } else if (error instanceof ServerError) {
                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, "Server Error.Please try another time", Snackbar.LENGTH_INDEFINITE)
                            .setAction("Refresh", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    
                                }
                            });
                    snackbar.setActionTextColor(Color.RED);
                    snackbar.show();
                } else if (error instanceof NetworkError) {
                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, "Network Error. Please try another time", Snackbar.LENGTH_INDEFINITE)
                            .setAction("Refresh", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    
                                }
                            });
                    snackbar.setActionTextColor(Color.RED);
                    snackbar.show();
                } else if (error instanceof ParseError) {
                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, "Data Error. Please try another time", Snackbar.LENGTH_INDEFINITE)
                            .setAction("Refresh", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    
                                }
                            });
                    snackbar.setActionTextColor(Color.RED);
                    snackbar.show();
                }


            }

        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("_mId", _phoneNo);
                params.put("submenu", String.valueOf(itemmenu));
                return params;
            }

        };
        eventoReq.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(eventoReq);

    }



}

