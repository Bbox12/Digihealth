package com.admin.ecosense.POD;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.admin.ecosense.R;
import com.admin.ecosense.helper.Qns;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class L1Adapter extends RecyclerView.Adapter<L1Adapter.ViewHolder> {

    // The items to display in your RecyclerView
    private ArrayList<Qns> mItems;
    private LayoutInflater layoutInflater;
    private Context mContext;
    private String Mobile;
    private double My_lat = 0, My_long = 0;
    private String Mode;
    private int _from=0;


    public L1Adapter(Context aContext, ArrayList<Qns> mItems) {
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

    public void setMode(String mode) {
        Mode = mode;
    }

    @Override
    public L1Adapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        //More to come
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.lrv, viewGroup, false);
        L1Adapter.ViewHolder viewHolder = new L1Adapter.ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final L1Adapter.ViewHolder viewHolder, final int position) {
        final Qns album_pos = mItems.get(position);

        viewHolder.L1.setVisibility(View.VISIBLE);

        if (album_pos.getQuestion(position) != null && !TextUtils.isEmpty(album_pos.getQuestion(position))
        ) {
            viewHolder.title.setText( album_pos.getQuestion(position).toUpperCase());
        } else {
            viewHolder.title.setVisibility(View.GONE);
        }
        if(album_pos.getRisk(position)!=7) {
            if (album_pos.getRiskFactors(position) != null && !TextUtils.isEmpty(album_pos.getRiskFactors(position))
            ) {
                viewHolder.description.setText(album_pos.getRiskFactors(position));
            } else {
                viewHolder.description.setVisibility(View.GONE);
            }
        }else{
            if (album_pos.getOtherFactors(position) != null && !TextUtils.isEmpty(album_pos.getOtherFactors(position))
            ) {
                viewHolder.description.setText(album_pos.getOtherFactors(position));
            } else {
                viewHolder.description.setVisibility(View.GONE);
            }
        }


        viewHolder.date.setVisibility(View.VISIBLE);
        viewHolder.date.setText(parseDateToETR(album_pos.getDate(position)+album_pos.getTime(position)));





    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void setFrom(int i) {
        _from=i;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {


        private TextView date,title,description;
        private LinearLayout L1;
        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.question);
            description = itemView.findViewById(R.id.risk);
            date = itemView.findViewById(R.id.date);
            L1 = itemView.findViewById(R.id._L1);
        }

    }
    public String parseDateToETR(String time) {
        String inputPattern = "yyyy-MM-ddHH:mm:ss";
        String outputPattern = "dd MMM yy hh:mm aa";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            //e.printStackTrace();
            String inputPattern1 = "MM-dd-yyyy";
            String outputPattern1 = "dd MMM yy";
            SimpleDateFormat inputFormat1 = new SimpleDateFormat(inputPattern1);
            SimpleDateFormat outputFormat1 = new SimpleDateFormat(outputPattern1);

            Date date1 = null;

            try {
                date1 = inputFormat1.parse(time);
                str = outputFormat1.format(date1);
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
        }
        return str;
    }


}





