package com.admin.ecosense.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.admin.ecosense.Activities.Wb1_access;
import com.admin.ecosense.R;
import com.admin.ecosense.helper.Qns;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * Created by parag on 22/09/16.
 */
public class generallnfoRv extends RecyclerView.Adapter<generallnfoRv.ViewHolder> {

    // The items to display in your RecyclerView
    private ArrayList<Qns> mItems;
    private LayoutInflater layoutInflater;
    private Context mContext;
    private String Mobile;
    private double My_lat = 0, My_long = 0;
    private String Mode;
    private int _from=0;


    public generallnfoRv(Context aContext, ArrayList<Qns> mItems) {
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
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        //More to come
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.class_rv, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        final Qns album_pos = mItems.get(position);


        if (album_pos.getPhoto(position) != null && !TextUtils.isEmpty(album_pos.getPhoto(position))
                ) {
            viewHolder._Image1.setVisibility(View.VISIBLE);
            if(album_pos.getIsVideo(position)==1){
                viewHolder._Image1.setBackground(mContext.getResources().getDrawable(R.mipmap.ic_video));
            }else{
                viewHolder._Image1.setBackground(mContext.getResources().getDrawable(R.mipmap.ic_doc));
            }

        } else {
            viewHolder._Image1.setVisibility(View.GONE);
        }


        if (album_pos.getCategory(position) != null && !TextUtils.isEmpty(album_pos.getCategory(position))
        ) {
            viewHolder.title.setText( album_pos.getCategory(position).toUpperCase());
        } else {
            viewHolder.title.setVisibility(View.GONE);
        }

        if (album_pos.getQuestion(position) != null && !TextUtils.isEmpty(album_pos.getQuestion(position))
        ) {
            viewHolder.description.setText(album_pos.getQuestion(position));
        } else {
            viewHolder.description.setVisibility(View.GONE);
        }


            viewHolder._date.setVisibility(View.VISIBLE);
            viewHolder._date.setText(parseDateToETR(album_pos.getDate(position)+album_pos.getTime(position)));



             viewHolder._Image1.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     Intent askwho = new Intent(mContext, Wb1_access.class);
                     askwho.putExtra("from",5);
                     askwho.putExtra("video",album_pos.getIsVideo(position));
                     askwho.putExtra("links",album_pos.getPhoto(position));
                     ((Activity)mContext).startActivity(askwho);
                     ((Activity)mContext).overridePendingTransition(R.anim.slide_up1, R.anim.slide_down1);
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


        private ImageView _Image1;
        private TextView title,description;
        private RadioButton _date;
        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            _Image1 = itemView.findViewById(R.id._image_1);
            _date = itemView.findViewById(R.id.date);
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





