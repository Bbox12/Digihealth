package com.admin.ecosense.Adapters;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.SparseBooleanArray;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.admin.ecosense.Activities.Wb1_access;
import com.admin.ecosense.R;
import com.admin.ecosense.helper.LruBitmapCache;
import com.admin.ecosense.helper.Message;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;



public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MyViewHolder> {
    private Context mContext;
    private List<Message> messages;
    private SparseBooleanArray selectedItems;

    // array used to perform multiple animation at once
    private SparseBooleanArray animationItemsIndex;
    private boolean reverseAllAnimations = false;

    // index is used to animate only the selected row
    // dirty fix, find a better solution
    private static int currentSelectedIndex = -1;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        public TextView from, subject, message;
        private NetworkImageView imgProfile;
        public LinearLayout messageContainer;
        public RelativeLayout iconContainer,  iconFront,relativeLayout;

        public MyViewHolder(View view) {
            super(view);
            from = (TextView) view.findViewById(R.id.from);
            subject = (TextView) view.findViewById(R.id.txt_primary);
            message = (TextView) view.findViewById(R.id.txt_secondary);
            iconFront = (RelativeLayout) view.findViewById(R.id.icon_front);
            imgProfile = view.findViewById(R.id.icon_profile);
            messageContainer = (LinearLayout) view.findViewById(R.id.message_container);
            iconContainer = (RelativeLayout) view.findViewById(R.id.icon_container);
            relativeLayout=view.findViewById(R.id.relativeLayout);
            view.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View view) {
            view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
            return true;
        }
    }


    public MessagesAdapter(Context mContext, List<Message> messages) {
        this.mContext = mContext;
        this.messages = messages;
        selectedItems = new SparseBooleanArray();
        animationItemsIndex = new SparseBooleanArray();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Message message = messages.get(position);

        // displaying text view data
        holder.from.setText(message.getFrom());
        holder.subject.setText(message.getSubject());
        holder.message.setText(message.getMessage());


        // change the row state to activated
        holder.itemView.setActivated(selectedItems.get(position, false));




        // display profile image
        applyProfilePicture(holder, message);

        // apply click events
        applyClickEvents(holder, position);
    }

    private void applyClickEvents(MyViewHolder holder, final int position) {
        holder.messageContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent askwho = new Intent(mContext, Wb1_access.class);
                askwho.putExtra("from",5);
                askwho.putExtra("links",messages.get(position).getLinks());
                ((Activity)mContext).startActivity(askwho);
                ((Activity)mContext).overridePendingTransition(R.anim.slide_up1, R.anim.slide_down1);
            }
        });

    }

    private void applyProfilePicture(MyViewHolder holder, Message message) {
        if (!TextUtils.isEmpty(message.getPicture())) {
            String url = message.getPicture().replaceAll(" ", "%20");
            ImageLoader imageLoader = LruBitmapCache.getInstance(mContext)
                    .getImageLoader();
            imageLoader.get(url, ImageLoader.getImageListener(holder.imgProfile,
                    R.mipmap.ic_launcher, R.mipmap
                            .ic_launcher));
            holder.imgProfile.setImageUrl(url, imageLoader);
        } else {
            holder.imgProfile.setImageResource(R.drawable.bg_circle);
        }
    }




    @Override
    public long getItemId(int position) {
        return messages.get(position).getId();
    }



    @Override
    public int getItemCount() {
        return messages.size();
    }


}