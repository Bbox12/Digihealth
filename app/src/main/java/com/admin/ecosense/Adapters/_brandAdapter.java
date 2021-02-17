package com.admin.ecosense.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.admin.ecosense.Activities.CovidTest;
import com.admin.ecosense.Login.ServiceOffer;
import com.admin.ecosense.Questions.GetMyStatus;
import com.admin.ecosense.R;
import com.admin.ecosense.RequestTest.MedicalTest;
import com.admin.ecosense.RequestTest.SickNote;
import com.admin.ecosense.helper.Login;
import com.admin.ecosense.helper.PrefManager;
import com.android.volley.toolbox.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;

public class _brandAdapter extends RecyclerView.Adapter<_brandAdapter.ViewHolder> {

    private ArrayList<Login> mItems;
    private Context mContext;
    private ImageLoader imageLoader;
    private int _from = 0;
    private CoordinatorLayout coordinatorLayout;
    private String _phoneNo;
    private PrefManager pref;
    private String _PhoneNo;


    public _brandAdapter(Context aContext, ArrayList<Login> mItems) {
        this.mItems = mItems;
        this.mContext = aContext;

    }


    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        //More to come
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.requesttype, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        final Login album_pos = mItems.get(position);


       if(album_pos.getName(position)!=null && !TextUtils.isEmpty(album_pos.getName(position))){
           viewHolder.yeshome.setText(album_pos.getName(position));
       }
        pref=new PrefManager(mContext);
        HashMap<String, String> user = pref.getUserDetails();
        _PhoneNo = user.get(PrefManager.KEY_MOBILE);

       viewHolder.yeshome.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(_PhoneNo!=null) {
                   if (album_pos.getID(position) == 1) {

                       Intent eme = new Intent(mContext, SickNote.class);
                       eme.putExtra("name", album_pos.getName(position));
                       ((Activity) mContext).startActivity(eme);
                       ((Activity) mContext).overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                   }
                   if (album_pos.getID(position) == 3) {
                           Intent eme = new Intent(mContext, CovidTest.class);
                           eme.putExtra("name", album_pos.getName(position));
                           eme.putExtra("from", 3);
                           ((Activity) mContext).startActivity(eme);
                           ((Activity) mContext).overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                   }

                   if (album_pos.getID(position) == 2) {
                       Intent eme = new Intent(mContext, CovidTest.class);
                       eme.putExtra("name", album_pos.getName(position));
                       eme.putExtra("from", 2);
                       ((Activity) mContext).startActivity(eme);
                       ((Activity) mContext).overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                   }
                   if (album_pos.getID(position) == 5) {
                       Intent eme = new Intent(mContext, CovidTest.class);
                       eme.putExtra("name", album_pos.getName(position));
                       eme.putExtra("from", 5);
                       ((Activity) mContext).startActivity(eme);
                       ((Activity) mContext).overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                   }
                   if (album_pos.getID(position) == 4) {
                           Intent eme = new Intent(mContext, CovidTest.class);
                           eme.putExtra("name", album_pos.getName(position));
                           eme.putExtra("from", 4);
                           ((Activity) mContext).startActivity(eme);
                           ((Activity) mContext).overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                   }
               }else {
                       Intent eme = new Intent(mContext, ServiceOffer.class);
                       ((Activity)mContext).startActivity(eme);
                       ((Activity)mContext).overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                   }

           }
       });



    }


    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        private AppCompatCheckBox yeshome;


        public ViewHolder(View itemView) {
            super(itemView);
            yeshome = itemView.findViewById(R.id.yeshome);
        }

    }


}






