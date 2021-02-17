package com.admin.ecosense.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.admin.ecosense.Activities.Wb1_access;
import com.admin.ecosense.R;
import com.admin.ecosense.helper.Login;
import com.admin.ecosense.helper.LruBitmapCache;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

public class _docAdapter extends RecyclerView.Adapter<_docAdapter.ViewHolder> {

    private ArrayList<Login> mItems;
    private Context mContext;
    private ImageLoader imageLoader;
    private int _from = 0;
    private CoordinatorLayout coordinatorLayout;
    private String _phoneNo;


    public _docAdapter(Context aContext, ArrayList<Login> mItems) {
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
    public _docAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        //More to come
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.reportsrv, viewGroup, false);
        _docAdapter.ViewHolder viewHolder = new _docAdapter.ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final _docAdapter.ViewHolder viewHolder, final int position) {
        final Login album_pos = mItems.get(position);
       _from=album_pos.getIDrequests(position);

            if(_from==2){
                viewHolder._download.setText("Biomarker and clotting test report");
            }else if(_from==3){
                viewHolder._download.setText("Swab report");
            }else if(_from==4){
                viewHolder._download.setText("X-rays report");
            }




        final String url = album_pos.getPhoto(position).replaceAll(" ", "%20");
        viewHolder.Thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent askwho = new Intent(mContext, Wb1_access.class);
                askwho.putExtra("from",5);
                askwho.putExtra("video",0);
                askwho.putExtra("links",url);
                ((Activity)mContext).startActivity(askwho);
                ((Activity)mContext).overridePendingTransition(R.anim.slide_up1, R.anim.slide_down1);
            }
        });


    }


    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        private TextView _download;
        private ImageView Thumbnail;


        public ViewHolder(View itemView) {
            super(itemView);
            _download = itemView.findViewById(R.id._download);
            Thumbnail = itemView.findViewById(R.id._image1);
        }

    }


}






