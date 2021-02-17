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
import com.admin.ecosense.Doctors.StartSession;
import com.admin.ecosense.R;
import com.admin.ecosense.helper.Login;
import com.admin.ecosense.helper.PrefManager;

import java.util.ArrayList;


public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.ViewHolder>  {

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


    public PatientAdapter(Context acontext, ArrayList<Login> mItems) {
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
            queue=itemView.findViewById(R.id.digi);
            _Patient=itemView.findViewById(R.id._Patient);
            _select=itemView.findViewById(R.id._select);
        }


    }

    @Override
    public PatientAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        //More to come
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.patientcard, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final PatientAdapter.ViewHolder viewHolder, final int position) {
        final Login movie = mItems.get(position);


            if (movie.getName(position) != null && !TextUtils.isEmpty(movie.getName(position))) {
                viewHolder.name.setText(movie.getName(position));
            }
            if (movie.getPatientID(position) != null && !TextUtils.isEmpty(movie.getPatientID(position))) {
                viewHolder.ID.setVisibility(View.VISIBLE);
                viewHolder.ID.setText(String.valueOf(position+1));
            }

        if (movie.getRiskFactor(position) != null && !TextUtils.isEmpty(movie.getRiskFactor(position))) {
            viewHolder.risk.setText(movie.getRiskFactor(position));
        }
        if (movie.getGender(position) != null && !TextUtils.isEmpty(movie.getGender(position))) {
            viewHolder.date.setText(movie.getGender(position));
        }
        viewHolder.queue.setText(movie.getDigiCategory(position));

        viewHolder._select.setChecked(false);

        viewHolder._select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(_from==0) {
                    Intent eme = new Intent(mContext, PatientDetails.class);
                    pref.setWorkID(movie.getID(position));
                    ((Activity) mContext).startActivity(eme);
                    ((Activity) mContext).overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                }else{
                    Intent eme = new Intent(mContext, StartSession.class);
                    pref.setPODmobile(movie.get_Phone_no(position));
                    pref.setWorkID(movie.getID(position));
                    ((Activity) mContext).startActivity(eme);
                    ((Activity) mContext).overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                }

            }
        });



        }







    @Override
    public int getItemCount() {
        return mItems.size();
    }


}

