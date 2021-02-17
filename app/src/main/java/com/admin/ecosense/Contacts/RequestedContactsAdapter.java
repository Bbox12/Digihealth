package com.admin.ecosense.Contacts;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.recyclerview.widget.RecyclerView;

import com.admin.ecosense.R;
import com.admin.ecosense.helper.Config_URL;

import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class RequestedContactsAdapter extends RecyclerView.Adapter<RequestedContactsAdapter.ViewHolder> {

    // The items to display in your RecyclerView
    private ArrayList<Contacts> mItems;
    private Context mContext;
    private String _PhoneNo;
    private double My_lat = 0, My_long = 0;
    private int _from=0;


    public RequestedContactsAdapter(Context aContext, ArrayList<Contacts> mItems) {
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

    public void setMobile(String _phone) {
        _PhoneNo = _phone;
    }

    public void setMyLat(double my_lat, double my_long) {
        My_lat = my_lat;
        My_long = my_long;
    }

    @Override
    public RequestedContactsAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        //More to come
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.sosrv, viewGroup, false);
        RequestedContactsAdapter.ViewHolder viewHolder = new RequestedContactsAdapter.ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RequestedContactsAdapter.ViewHolder viewHolder, final int position) {
        final Contacts album_pos = mItems.get(position);
        if (album_pos.getName(position) != null && !TextUtils.isEmpty(album_pos.getName(position))) {
            viewHolder.Sosname.setText(album_pos.getName(position));

        }
        if (album_pos.getContacts(position) != null && !TextUtils.isEmpty(album_pos.getContacts(position))) {
            viewHolder.Soscontacts.setText(album_pos.getContacts(position));

        }

        if(_from==1){
            viewHolder.Sosdelete.setVisibility(View.GONE);
            viewHolder.Soscontacts.setVisibility(View.GONE);
        }




        viewHolder.Sosdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!((Activity)mContext).isFinishing()) {
                    new androidx.appcompat.app.AlertDialog.Builder(mContext)
                            .setTitle("Delete contact")
                            .setMessage(album_pos.getName(position))
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    new PostDeleteContact().execute(String.valueOf(album_pos.getID(position)));
                                    dialog.cancel();
                                }
                            })
                            .show();
                }
            }
        });


        viewHolder._yehome.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                if(!((Activity)mContext).isFinishing()) {
                    new androidx.appcompat.app.AlertDialog.Builder(mContext)
                            .setTitle("Requesting live location of " + album_pos.getName(position))
                            .setMessage("Note: you can uncheck it any time to disable sharing")
                            .setPositiveButton("Allow", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    new PostAllow().execute(String.valueOf(album_pos.getID(position)));
                                    dialog.cancel();
                                }
                            })
                            .show();
                }
                }else{
                    new PostDeleteContact().execute(String.valueOf(album_pos.getID(position)));
                }
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


        private TextView Sosname, Soscontacts;
        private ImageView Sosdelete;
        private AppCompatCheckBox _yehome;

        public ViewHolder(View itemView) {
            super(itemView);

            Sosname = itemView.findViewById(R.id.sosname);
            Soscontacts = itemView.findViewById(R.id.soscontact);
            Sosdelete = itemView.findViewById(R.id.sosdel);
            _yehome = itemView.findViewById(R.id.yeshome);

        }

    }


    private class PostAllow extends AsyncTask<String, Integer, String> {
        private boolean success;

        protected void onPreExecute() {
            // Runs on the UI thread before doInBackground
            // Good for toggling visibility of a progress indicator

        }

        protected String doInBackground(String... strings) {
            // Some long-running task like downloading an image.
            String name = strings[0];
            return uploadFile(name);

        }

        private String uploadFile(String name) {
            // TODO Auto-generated method stub
            String res = null;

            try {

                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("mobile", _PhoneNo)
                        .addFormDataPart("ID", name)
                        .build();

                Request request = new Request.Builder()
                        .url(Config_URL.REQUEST_ALLOW)
                        .post(requestBody)
                        .build();
                OkHttpClient client = new OkHttpClient();
                okhttp3.Response response = client.newCall(request).execute();
                res = response.body().string();
                String[] pars = res.split("error");
                success = pars[1].contains("false");
                Log.e("TAG", "Response : " + res);

                return res;

            } catch (UnknownHostException | UnsupportedEncodingException e) {
                Log.e("TAG", "Error: " + e.getLocalizedMessage());
            } catch (Exception e) {
                Log.e("TAG", "Other Error: " + e.getLocalizedMessage());
                if(!((Activity)mContext).isFinishing()) {
                    new androidx.appcompat.app.AlertDialog.Builder(mContext)
                            .setTitle("Failed to store! Please try again.")
                            .setPositiveButton("Allow", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            })
                            .show();
                }
            }


            return res;

        }


        protected void onPostExecute(String result) {

            if (success) {
                if(!((Activity)mContext).isFinishing()) {
                    new androidx.appcompat.app.AlertDialog.Builder(mContext)
                            .setTitle("Success")
                            .setPositiveButton("Allow", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            })
                            .show();
                }

            }else {
                if(!((Activity)mContext).isFinishing()) {
                    new androidx.appcompat.app.AlertDialog.Builder(mContext)
                            .setTitle("Failed to store! Please try again.")
                            .setPositiveButton("Allow", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            })
                            .show();
                }
            }


        }
    }

    private class PostDeleteContact extends AsyncTask<String, Integer, String> {
        private boolean success;

        protected void onPreExecute() {
            // Runs on the UI thread before doInBackground
            // Good for toggling visibility of a progress indicator

        }

        protected String doInBackground(String... strings) {
            // Some long-running task like downloading an image.
            String name = strings[0];
            return uploadFile(name);

        }

        private String uploadFile(String name) {
            // TODO Auto-generated method stub
            String res = null;

            try {

                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("mobile", _PhoneNo)
                        .addFormDataPart("ID", name)
                        .build();

                Request request = new Request.Builder()
                        .url(Config_URL.REQUEST_DELETE)
                        .post(requestBody)
                        .build();
                OkHttpClient client = new OkHttpClient();
                okhttp3.Response response = client.newCall(request).execute();
                res = response.body().string();
                String[] pars = res.split("error");
                success = pars[1].contains("false");
                Log.e("TAG", "Response : " + res);

                return res;

            } catch (UnknownHostException | UnsupportedEncodingException e) {
                Log.e("TAG", "Error: " + e.getLocalizedMessage());
            } catch (Exception e) {
                Log.e("TAG", "Other Error: " + e.getLocalizedMessage());
            }


            return res;

        }


        protected void onPostExecute(String result) {

            if (success) {
                if(!((Activity)mContext).isFinishing()) {
                    new androidx.appcompat.app.AlertDialog.Builder(mContext)
                            .setTitle("Success")
                            .setPositiveButton("Allow", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            })
                            .show();
                }

            }else {
                if(!((Activity)mContext).isFinishing()) {
                    new androidx.appcompat.app.AlertDialog.Builder(mContext)
                            .setTitle("Failed to store! Please try again.")
                            .setPositiveButton("Allow", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            })
                            .show();
                }
            }


        }
    }
}





