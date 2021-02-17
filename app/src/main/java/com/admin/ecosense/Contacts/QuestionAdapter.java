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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.admin.ecosense.R;
import com.admin.ecosense.helper.Config_URL;
import com.admin.ecosense.helper.PrefManager;
import com.admin.ecosense.helper.Qns;
import com.google.firebase.database.DatabaseReference;

import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by parag on 28/02/18.
 */

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {

    // The items to display in your RecyclerView
    private ArrayList<Qns> mItems;
    private Context mContext;
    private String _PhoneNo;
    private double My_lat = 0, My_long = 0;
    private int _from=0;
    private DatabaseReference mDatabase;
    private PrefManager pref;
    private String _Name;


    public QuestionAdapter(Context aContext, ArrayList<Qns> mItems) {
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
    public QuestionAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        //More to come
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.questionsadapter, viewGroup, false);
        QuestionAdapter.ViewHolder viewHolder = new QuestionAdapter.ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final QuestionAdapter.ViewHolder viewHolder, final int position) {
        final Qns album_pos = mItems.get(position);
        if (album_pos.getCategory(position) != null && !TextUtils.isEmpty(album_pos.getCategory(position))) {
            viewHolder.title.setText(album_pos.getCategory(position));

        }
        pref=new PrefManager(mContext);
        HashMap<String, String> user = pref.getUserDetails();
        if(user.get(PrefManager.KEY_MOBILE)!=null) {
            _PhoneNo = getLastTen(user.get(PrefManager.KEY_MOBILE));
            _Name = user.get(PrefManager.KEY_NAME);
        }

        if (album_pos.getQuestion(position) != null && !TextUtils.isEmpty(album_pos.getQuestion(position))) {
            viewHolder.description.setText(album_pos.getQuestion(position));

        }


        viewHolder._submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(viewHolder._ans.getText().toString())){
                    new PostRequest().execute(String.valueOf(album_pos.getID(position)),viewHolder._ans.getText().toString());

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

    public void setDatabase(DatabaseReference mDatabase1) {
        mDatabase=mDatabase1;
    }

    public void setPref(PrefManager pref1) {
        pref=pref1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        private TextView title, description;
        private EditText _ans;
        private Button _submit;


        public ViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            _submit=itemView.findViewById(R.id.submit);
            _ans=itemView.findViewById(R.id._ans);
        }

    }



    private class PostRequest extends AsyncTask<String, Integer, String> {
        private boolean success;
        private String ans,ID;
        private String desc;

        protected void onPreExecute() {
            // Runs on the UI thread before doInBackground
            // Good for toggling visibility of a progress indicator

        }

        protected String doInBackground(String... strings) {
            // Some long-running task like downloading an image.
             ID = strings[0];
            desc = strings[1];
            return uploadFile(ID,ans,desc);

        }

        private String uploadFile(String ID,String ans,String desc) {
            // TODO Auto-generated method stub
            String res = null;

            try {

                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("mobile", _PhoneNo)
                        .addFormDataPart("ID", ID)
                        .addFormDataPart("desc", desc)
                        .build();

                Request request = new Request.Builder()
                        .url(Config_URL.REQUEST_REQUEST)
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
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
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

    private class PostRequestDelete extends AsyncTask<String, Integer, String> {
        private boolean success;
        private String pname;
        private String mobile;

        protected void onPreExecute() {
            // Runs on the UI thread before doInBackground
            // Good for toggling visibility of a progress indicator

        }

        protected String doInBackground(String... strings) {
            // Some long-running task like downloading an image.
            String name = strings[0];
            pname = strings[1];
            mobile = strings[2];
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
                pref.setSiteName(null);
                if(!((Activity)mContext).isFinishing()) {
                    new androidx.appcompat.app.AlertDialog.Builder(mContext)
                            .setTitle("Success")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
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


    public String getLastTen(String str) {
        str=str.replaceAll("\\s+","");
        return str.length() <= 10 ? str : str.substring(str.length() - 10);
    }

}





