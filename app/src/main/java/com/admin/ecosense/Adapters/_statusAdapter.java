package com.admin.ecosense.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.admin.ecosense.Activities.CovidTest;
import com.admin.ecosense.Activities.statusReport;
import com.admin.ecosense.Login.ServiceOffer;
import com.admin.ecosense.R;
import com.admin.ecosense.helper.Login;
import com.admin.ecosense.helper.PrefManager;
import com.android.volley.toolbox.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;

public class _statusAdapter extends RecyclerView.Adapter<_statusAdapter.ViewHolder> {

    private ArrayList<Login> mItems;
    private Context mContext;
    private ImageLoader imageLoader;
    private int _from = 0;
    private CoordinatorLayout coordinatorLayout;
    private String _phoneNo;
    private PrefManager pref;
    private String _PhoneNo;


    public _statusAdapter(Context aContext, ArrayList<Login> mItems) {
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
                .inflate(R.layout.statusrv, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        final Login album_pos = mItems.get(position);


        if(album_pos.getName(position)!=null && !TextUtils.isEmpty(album_pos.getName(position))){
            viewHolder.yeshome.setText(album_pos.getName(position));
        }

        if(_from!=0){
            viewHolder._image.setVisibility(View.GONE);
        }
        pref=new PrefManager(mContext);
        HashMap<String, String> user = pref.getUserDetails();
        _PhoneNo = user.get(PrefManager.KEY_MOBILE);
        if (album_pos.getID(position) == 3) {
            viewHolder._image.setVisibility(View.GONE);
        }

        viewHolder.yeshome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(_PhoneNo!=null) {
                    if (album_pos.getID(position) == 1) {
                        Intent eme = new Intent(mContext, statusReport.class);
                        eme.putExtra("name", album_pos.getName(position));
                        ((Activity) mContext).startActivity(eme);
                        ((Activity) mContext).overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                    }
                    if (album_pos.getID(position) == 3) {
                        viewHolder._image.setVisibility(View.GONE);
                        if(pref.getClockIn()==0) {
                            if (!((Activity) mContext).isFinishing()) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.AlertDialogTheme)
                                        .setIcon(R.mipmap.ic_launcher)
                                        .setTitle("Covid test report not submitted.")
                                        .setMessage("Please submit test report")
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent eme = new Intent(mContext, CovidTest.class);
                                                ((Activity) mContext).startActivity(eme);
                                                ((Activity) mContext).overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
                                                dialog.cancel();
                                            }
                                        })
                                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                            }
                                        });
                                builder.show();
                            }
                        }
                    }
                    if (album_pos.getID(position) == 2) {
                        Toast.makeText(mContext,"Comming Soon",Toast.LENGTH_SHORT).show();
                    }

                    if (album_pos.getID(position) == 4) {
                        Toast.makeText(mContext,"Comming Soon",Toast.LENGTH_SHORT).show();
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

    public void setFrom(int i) {
        _from=i;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        private TextView yeshome;
        private ImageView _image;


        public ViewHolder(View itemView) {
            super(itemView);
            yeshome = itemView.findViewById(R.id.yeshome);
            _image = itemView.findViewById(R.id._image);
        }

    }


}






