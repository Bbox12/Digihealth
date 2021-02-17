package com.admin.ecosense.Adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.admin.ecosense.R;
import com.admin.ecosense.helper.Login;
import com.admin.ecosense.helper.LruBitmapCache;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

public class _CommodityAdapter extends RecyclerView.Adapter<_CommodityAdapter.ViewHolder> {

    private ArrayList<Login> mItems;
    private Context mContext;
    private ImageLoader imageLoader;
    private int _from = 0;
    private CoordinatorLayout coordinatorLayout;
    private String _phoneNo;


    public _CommodityAdapter(Context aContext, ArrayList<Login> mItems) {
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
                .inflate(R.layout.commodityrv, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        final Login album_pos = mItems.get(position);

        if (album_pos.getName(position) != null && !TextUtils.isEmpty(album_pos.getName(position))) {
            viewHolder.Name.setText(album_pos.getName(position));
        }


        String url = album_pos.getPhoto(position).replaceAll(" ", "%20");
        imageLoader = LruBitmapCache.getInstance(mContext)
                .getImageLoader();
        imageLoader.get(url, ImageLoader.getImageListener(viewHolder.Thumbnail,
                R.mipmap.ic_launcher, R.mipmap
                        .ic_launcher));
        viewHolder.Thumbnail.setImageUrl(url, imageLoader);


    }


    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        private TextView Name;
        private NetworkImageView Thumbnail;


        public ViewHolder(View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.primary_text);
            Thumbnail = itemView.findViewById(R.id.service_pic);
        }

    }


}






