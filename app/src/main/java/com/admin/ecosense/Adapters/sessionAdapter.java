package com.admin.ecosense.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.admin.ecosense.Activities.Wb1_access;
import com.admin.ecosense.R;
import com.admin.ecosense.helper.PrefManager;
import com.admin.ecosense.helper.Qns;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class sessionAdapter extends RecyclerView.Adapter<sessionAdapter.ViewHolder> {

    private static final int SELECT_FILE = 102;
    private LayoutInflater ashit;
    private ArrayList<Qns> mService;
    private PrefManager pref;
    private TextView _iValue;
    private int _ID=0;
    private LinearLayout L1;
    private Context mContext;
    private CoordinatorLayout coordinatorLayout;
    private LinearLayout layoutBottomSheet;
    private TextView _filenames;
    private Uri selectedImageUri;
    private String selectedVideoPath;
    private int _from=0;

    public sessionAdapter(Context acontext, ArrayList<Qns> mService){
        this.mService=mService;
        this.mContext=acontext;



    }
    @Override
    public sessionAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        //More to come
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.sessionrv, viewGroup, false);
        sessionAdapter.ViewHolder viewHolder = new sessionAdapter.ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final sessionAdapter.ViewHolder viewHolder, final int position) {
        final Qns movie=mService.get(position);
        if(_from==0) {
            if (movie.getDate(position) != null) {
                viewHolder.date.setText(parseDateToETR(movie.getDate(position) + movie.getTime(position)));
            }
        }else{
            if(movie.getDate(position)!=null){
                viewHolder.date.setText(parseDateToETR(movie.getDate(position)+movie.getTime(position))+" Doctor "+movie.getName(position));
            }
        }

        if(movie.getDescription(position)!=null && !TextUtils.isEmpty(movie.getDescription(position))){
            viewHolder.description.setText(movie.getDescription(position));
        }
        if(movie.getPhoto(position)!=null && !TextUtils.isEmpty(movie.getPhoto(position)) ){
            viewHolder._Image1.setVisibility(View.VISIBLE);

        }else{
            viewHolder._Image1.setVisibility(View.GONE);
        }

        if(movie.getID(position)==0){
            if(movie.getNextDate(position)!=null && !TextUtils.isEmpty(movie.getNextDate(position)) ){
                viewHolder.date.setText(parseDateToETR(movie.getNextDate(position)+movie.getNextTime(position)));
                viewHolder.date.setTextColor(mContext.getResources().getColor(R.color.bg_screen2));

            }
            viewHolder.description.setText("Next session");
            viewHolder._Image1.setVisibility(View.GONE);
            viewHolder._v.setVisibility(View.GONE);

        }


        viewHolder._Image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent askwho = new Intent(mContext, Wb1_access.class);
                askwho.putExtra("from",5);
                askwho.putExtra("video",0);
                askwho.putExtra("links",movie.getPhoto(position));
                ((Activity)mContext).startActivity(askwho);
                ((Activity)mContext).overridePendingTransition(R.anim.slide_up1, R.anim.slide_down1);
            }
        });


    }


    @Override
    public int getItemCount() {
        return mService == null ? 0 : mService.size();
    }


    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public void setCoordinatorlayout(CoordinatorLayout coordinatorLayout1) {
        coordinatorLayout=coordinatorLayout1;
    }

    public void setFrom(int i) {
        _from=i;

    }


    public class ViewHolder extends RecyclerView.ViewHolder {


        private EditText description;
        private RadioButton date;
        private ImageView _Image1;
        private View _v;
        private RadioButton end;
        private RecyclerView messages_view;

        public ViewHolder(View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
            description= itemView.findViewById(R.id.description);
            _Image1 = itemView.findViewById(R.id._image_1);
            _v = itemView.findViewById(R.id._v);
            messages_view=itemView.findViewById(R.id.messages_view);


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
            String inputPattern1 = "yyyy-MM-dd";
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

