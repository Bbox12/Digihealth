package com.admin.ecosense.Contacts;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.admin.ecosense.R;

import java.util.ArrayList;

public class MainSos extends RecyclerView.Adapter<MainSos.ViewHolder> {

    // The items to display in your RecyclerView
    private ArrayList<Contacts> mItems;
    private Context mContext;
    private String _PhoneNo;
    private double My_lat = 0, My_long = 0;
    private int _from=0;


    public MainSos(Context aContext, ArrayList<Contacts> mItems) {
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
    public MainSos.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        //More to come
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.mainsosrv, viewGroup, false);
        MainSos.ViewHolder viewHolder = new MainSos.ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MainSos.ViewHolder viewHolder, final int position) {
        final Contacts album_pos = mItems.get(position);
        if (album_pos.getName(position) != null && !TextUtils.isEmpty(album_pos.getName(position))) {
            viewHolder.Sosname.setText(album_pos.getName(position));

        }

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void setFrom(int i) {
        _from=i;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        private EditText Sosname, Soscontacts;
        private ImageView Sosdelete;

        public ViewHolder(View itemView) {
            super(itemView);

            Sosname = itemView.findViewById(R.id.sosname);
            Soscontacts = itemView.findViewById(R.id.soscontact);
            Sosdelete = itemView.findViewById(R.id.sosdel);


        }

    }

}





