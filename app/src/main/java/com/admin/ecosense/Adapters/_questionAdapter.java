package com.admin.ecosense.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.recyclerview.widget.RecyclerView;

import com.admin.ecosense.R;
import com.admin.ecosense.helper.Qns;

import java.util.ArrayList;

public class _questionAdapter extends RecyclerView.Adapter<_questionAdapter.ViewHolder> {

    private ArrayList<Qns> mItems;
    private Context mContext;
    private int selectedPosition=-1;


    public _questionAdapter(Context aContext, ArrayList<Qns> mItems) {
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
                .inflate(R.layout.questionsrv, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        final Qns album_pos = mItems.get(position);


        if(album_pos.getQuestion(position)!=null && !TextUtils.isEmpty(album_pos.getQuestion(position))){
            viewHolder.yeshome.setText(album_pos.getQuestion(position));
            if(album_pos.getColors(position)!=null && !TextUtils.isEmpty(album_pos.getColors(position))) {
                viewHolder.yeshome.setBackgroundColor(Color.parseColor(album_pos.getColors(position)));
                viewHolder.yeshome.setTextColor(Color.WHITE);
            }
        }


        if (album_pos.isSelected(position)) {
            viewHolder.yeshome.setChecked(true);
            selectedPosition = position;
        } else {
            viewHolder.yeshome.setChecked(false);
        }

        viewHolder.yeshome.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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


    public class ViewHolder extends RecyclerView.ViewHolder {


        private AppCompatCheckBox yeshome;

        public ViewHolder(View itemView) {
            super(itemView);
            yeshome = itemView.findViewById(R.id.yeshome);

        }

    }







}






