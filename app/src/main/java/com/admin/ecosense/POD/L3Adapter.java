package com.admin.ecosense.POD;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.admin.ecosense.R;
import com.admin.ecosense.helper.Login;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class L3Adapter extends RecyclerView.Adapter<L3Adapter.ViewHolder> {

// The items to display in your RecyclerView
private ArrayList<Login> mItems;
private LayoutInflater layoutInflater;
private Context mContext;
private String Mobile;
private double My_lat = 0, My_long = 0;
private String Mode;
private int _from=0;


public L3Adapter(Context aContext, ArrayList<Login> mItems) {
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
public L3Adapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        //More to come
        View v = LayoutInflater.from(viewGroup.getContext())
        .inflate(R.layout.lrv, viewGroup, false);
        L3Adapter.ViewHolder viewHolder = new L3Adapter.ViewHolder(v);

        return viewHolder;
        }

@Override
public void onBindViewHolder(final L3Adapter.ViewHolder viewHolder, final int position) {
final Login album_pos = mItems.get(position);

    viewHolder.H1.setVisibility(View.VISIBLE);
    viewHolder.L2.setVisibility(View.GONE);

        viewHolder.oxygen.setText(String.valueOf(album_pos.getOxygen(position)));
    viewHolder.Breathing.setText(String.valueOf(album_pos.getBreaths(position)));
    viewHolder.temperature.setText(String.valueOf(album_pos.getTemperature(position)));
    viewHolder.pulse.setText(String.valueOf(album_pos.getPulse(position)));


        if(album_pos.getSpeech(position)==1) {

        viewHolder.speech.setText("Yes");

        }else{
            viewHolder.speech.setText("No");
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


    private TextView date,oxygen,Breathing,temperature,pulse,speech;
    private HorizontalScrollView H1;
    private LinearLayout L2;
    public ViewHolder(View itemView) {
        super(itemView);
        date = itemView.findViewById(R.id.date2);
        oxygen = itemView.findViewById(R.id.oxygen);
        Breathing = itemView.findViewById(R.id.Breathing);
        temperature = itemView.findViewById(R.id.temperature);
        pulse = itemView.findViewById(R.id.pulse);
        speech = itemView.findViewById(R.id.speech);
        H1 = itemView.findViewById(R.id._L3);
        L2 = itemView.findViewById(R.id._L1);
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





