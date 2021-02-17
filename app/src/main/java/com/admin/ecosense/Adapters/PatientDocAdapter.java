package com.admin.ecosense.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.admin.ecosense.Doctors.PatientDetails;
import com.admin.ecosense.POD.StatusHistory;
import com.admin.ecosense.R;
import com.admin.ecosense.helper.Login;
import com.admin.ecosense.helper.PrefManager;

import java.util.ArrayList;


public class PatientDocAdapter extends RecyclerView.Adapter<PatientDocAdapter.ViewHolder>  {

    // The items to display in your RecyclerView
    private ArrayList<Login> mItems;
    private LayoutInflater mshit;

    private Context mContext;
    private String Name_p;
    private String User;
    private String rView;
    private PrefManager pref;
    private CoordinatorLayout coordinatorLayout;
    private int _from;


    public PatientDocAdapter(Context acontext, ArrayList<Login> mItems) {
        //mshit = LayoutInflater.from(acontext);
        this.mItems = mItems;
        this.mContext = acontext;



    }
    public void setCoordinate(CoordinatorLayout coordinatorLayout1) {
        coordinatorLayout=coordinatorLayout1;
    }


    public ArrayList<Login> getmItems() {
        return mItems;
    }

    @Override
    public long getItemId(int position) {

        return super.getItemId(position);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public void setName(String name) {

        Name_p=name;

    }

    public void User(String user) {
        User=user;
    }

    public void Service_name(String s) {

        rView=s;
    }

    public void setPref(PrefManager pref1) {
        pref=pref1;
    }

    public void setFrom(int i) {
        _from=i;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView ID,name,risk,date,queue;
        private LinearLayout _Patient;
        private RadioButton _select;



        public ViewHolder(View itemView) {
            super(itemView);

            ID = itemView.findViewById(R.id.ID);
            name= (TextView) itemView.findViewById(R.id.name);
            risk=itemView.findViewById(R.id.risk);
            date=itemView.findViewById(R.id.gender);
            queue=itemView.findViewById(R.id.queue);
            _Patient=itemView.findViewById(R.id._Patient);
            _select=itemView.findViewById(R.id._select);
        }


    }

    @Override
    public PatientDocAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        //More to come
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.patientdoccard, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final PatientDocAdapter.ViewHolder viewHolder, final int position) {
        final Login movie = mItems.get(position);


        if (movie.getName(position) != null && !TextUtils.isEmpty(movie.getName(position))) {
            viewHolder.name.setText(movie.getName(position));
        }
        if (movie.getPatientID(position) != null && !TextUtils.isEmpty(movie.getPatientID(position))) {
            viewHolder.ID.setText(movie.getPatientID(position));
        }

        if (movie.getDigiCategory(position) != null && !TextUtils.isEmpty(movie.getDigiCategory(position))) {
            viewHolder.risk.setText(movie.getDigiCategory(position));
        }
        if (movie.getDocname(position) != null && !TextUtils.isEmpty(movie.getDocname(position))) {
            if(movie.getDocname(position).toLowerCase().contains("null")){
                viewHolder.date.setText("None");
            }else {
                viewHolder.date.setText(movie.getDocname(position));
            }
        }
        if (movie.getDocMobile(position) != null && !TextUtils.isEmpty(movie.getDocMobile(position))) {
            if(movie.getDocMobile(position).toLowerCase().contains("null")){
                viewHolder.queue.setText("None");
            }else {
                viewHolder.queue.setText(movie.getDocMobile(position));
            }
        }
        pref=new PrefManager(mContext);
        viewHolder._select.setChecked(false);

        viewHolder._select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   if(pref.getResponsibility()==2){
                       Intent eme = new Intent(mContext, PatientDetails.class);
                       pref.setWorkID(movie.getID(position));
                       eme.putExtra("mobile", movie.getDocMobile(position));
                       eme.putExtra("from", 1);
                       ((Activity) mContext).startActivity(eme);
                       ((Activity) mContext).overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                   }else {
                       if (_from == 0) {
                           Intent eme = new Intent(mContext, PatientDetails.class);
                           pref.setWorkID(movie.getID(position));
                           eme.putExtra("from", 1);
                           ((Activity) mContext).startActivity(eme);
                           ((Activity) mContext).overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                       } else {
                           pref.setPODmobile(movie.get_Phone_no(position));
                           Intent eme = new Intent(mContext, StatusHistory.class);
                           ((Activity) mContext).startActivity(eme);
                           ((Activity) mContext).overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                       }
                   }
            }
        });



    }







    @Override
    public int getItemCount() {
        return mItems.size();
    }


}

