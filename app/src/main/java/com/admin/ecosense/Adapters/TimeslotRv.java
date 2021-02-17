package com.admin.ecosense.Adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.recyclerview.widget.RecyclerView;

import com.admin.ecosense.R;
import com.admin.ecosense.helper.Qns;

import java.util.ArrayList;

public class TimeslotRv extends RecyclerView.Adapter<TimeslotRv.ViewHolder> {

    // The items to display in your RecyclerView
    private ArrayList<Qns> mItems;
    private Context mContext;
    private Button submit1;
    private int selectedPosition=-1;


    public TimeslotRv(Context aContext, ArrayList<Qns> mItems) {
        this.mItems = mItems;
        this.mContext=aContext;

    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public TimeslotRv.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        //More to come
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.timeslotrv, viewGroup, false);
        ViewHolder viewHolder = new TimeslotRv.ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final TimeslotRv.ViewHolder viewHolder, final int position) {
        final Qns album_pos = mItems.get(position);
        if (album_pos.getQuestion(position) != null && !TextUtils.isEmpty(album_pos.getQuestion(position))) {
            viewHolder._chck.setText(album_pos.getQuestion(position));
        }



        if (album_pos.isSelected(position)) {
            viewHolder._chck.setChecked(true);
            selectedPosition = position;
        } else {
            viewHolder._chck.setChecked(false);
        }

        viewHolder._chck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    album_pos.setSelected(true);
                    if (selectedPosition >= 0) {
                        mItems.get(selectedPosition).setSelected(false);
                        notifyItemChanged(selectedPosition);
                    }
                    selectedPosition = position;
                } else {
                    album_pos.setSelected(false);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void setButton(Button submit2) {
        submit1=submit2;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {



        private RelativeLayout _rl;
        private AppCompatCheckBox _chck;

        public ViewHolder(View itemView) {
            super(itemView);

            _rl = itemView.findViewById(R.id.rl);
            _chck = itemView.findViewById(R.id.slots);
        }

    }



}





