package com.admin.ecosense.Adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.recyclerview.widget.RecyclerView;

import com.admin.ecosense.Activities.CovidTest;
import com.admin.ecosense.Doctors.PatientDetails;
import com.admin.ecosense.POD.PODConsultation;
import com.admin.ecosense.R;
import com.admin.ecosense.RequestTest.OxygenRequest;
import com.admin.ecosense.helper.Config_URL;
import com.admin.ecosense.helper.Login;
import com.admin.ecosense.helper.PrefManager;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class _symsAdapter extends RecyclerView.Adapter<_symsAdapter.ViewHolder> {

    private ArrayList<Login> mItems;
    private Context mContext;
    private PrefManager pref;
    private String _PhoneNo;
    ArrayList<Integer> TotalDays = new ArrayList<Integer>();
    private Button _Submit;
    private ProgressBar _p1;
    private String sampletype;
    private String indication;
    private int _from=0;
    private String other_reson="";
    private AppCompatCheckBox greater;
    private int _greater=0;
    private int _every=0;


    public _symsAdapter(Context aContext, ArrayList<Login> mItems) {
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
                .inflate(R.layout.symadapter, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        final Login album_pos = mItems.get(position);

        pref=new PrefManager(mContext);
        HashMap<String, String> user = pref.getUserDetails();

        if(album_pos.getName(position)!=null && !TextUtils.isEmpty(album_pos.getName(position))){
            viewHolder.yeshome.setText(album_pos.getName(position));

            if(album_pos.getClient(position)>0){
                viewHolder.yeshome.setChecked(true);
            }
        }

        if(sampletype!=null){
            if(sampletype.contains(album_pos.getName(position))){
                viewHolder.yeshome.setChecked(true);
            }
        }

        if(indication!=null){
            if(indication.contains(album_pos.getName(position))){
                viewHolder.yeshome.setChecked(true);
            }
        }

        if(viewHolder.yeshome.isChecked()) {
            TotalDays.add(album_pos.getID(position));
            if(album_pos.getClient(position)==0){
                if (pref.getResponsibility() == 1) {
                    if (album_pos.getName(position).toLowerCase().contains("other")) {
                        if (TextUtils.isEmpty(other_reson)) {
                            open_otp();
                        }
                    }
                }
            }
        }




        if(pref.getResponsibility()==3) {
            if(pref.getPODmobile()!=null) {
                _PhoneNo = pref.getPODmobile();
            }else{
                _PhoneNo = user.get(PrefManager.KEY_MOBILE);
            }

        }else{
            _PhoneNo = user.get(PrefManager.KEY_MOBILE);
        }
        if(_from<2) {
            viewHolder.yeshome.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        TotalDays.add(album_pos.getID(position));
                        if(album_pos.getName(position).toLowerCase().contains("other")){
                                open_otp();
                        }
                    }else{
                        for(int i=0;i<TotalDays.size();i++){
                            if(album_pos.getID(position)==TotalDays.get(i)){
                                TotalDays.remove(i);
                                if(album_pos.getName(position).toLowerCase().contains("other")){
                                    other_reson="";
                                }
                            }
                        }
                    }
                }
            });
        }

        if(greater!=null) {
            greater.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    _greater = 1;
                }
            });
        }

       if(_Submit!=null) {
           _Submit.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   if(_from<2) {
                       if (!((Activity) mContext).isFinishing()) {
                           AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.AlertDialogTheme)
                                   .setIcon(R.mipmap.ic_launcher)
                                   .setTitle("Are you sure?")
                                   .setMessage("You are sharing the symptoms with DigiHealth")
                                   .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                       @Override
                                       public void onClick(DialogInterface dialog, int which) {
                                           new PostConfirmationOxygen().execute();
                                           dialog.cancel();
                                       }
                                   })
                                   .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                       @Override
                                       public void onClick(DialogInterface dialog, int which) {
                                           dialog.cancel();
                                       }
                                   });
                           builder.show();
                       }

                   }
               }
           });
       }
    }


    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void setSubmit(Button submit) {
        _Submit=submit;
    }

    public void setProgress(ProgressBar p1) {
        _p1=p1;
    }

    public void setSample(String sampletype1) {
        sampletype=sampletype1;
    }

    public void setIndication(String indication1) {
        indication=indication1;
    }

    public void setFrom(int i) {
        _from=i;
    }

    public void setGreater(AppCompatCheckBox greater1) {
        greater=greater1;
    }

    public void setEvery(int from) {
        _every=from;
    }




    public class ViewHolder extends RecyclerView.ViewHolder {


        private AppCompatCheckBox yeshome;


        public ViewHolder(View itemView) {
            super(itemView);
            yeshome = itemView.findViewById(R.id.yeshome);
        }

    }


    private class PostConfirmationOxygen extends AsyncTask<Void, Integer, String> {


        private String commaSeparatedValues = "";
        private boolean success = false;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();


        }

        protected String doInBackground(Void... args) {
            return uploadFile();
        }

        private String uploadFile() {
            // TODO Auto-generated method stub
            String res = null;
            if (TotalDays.size() != 0) {
                if (TotalDays.size() > 1) {
                    commaSeparatedValues = TextUtils.join(",", TotalDays);
                } else {
                    commaSeparatedValues = String.valueOf(TotalDays.get(0));
                }
            } else {
                commaSeparatedValues = "Not selected";
            }

            try {

                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("mobile", _PhoneNo)
                        .addFormDataPart("review", commaSeparatedValues)
                        .addFormDataPart("other", other_reson)
                        .addFormDataPart("greater", String.valueOf(_greater))
                        .build();

                Request request = new Request.Builder()
                        .url(Config_URL.STOP_OXYGEN)
                        .post(requestBody)
                        .build();
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(120, TimeUnit.SECONDS)
                        .writeTimeout(120, TimeUnit.SECONDS)
                        .readTimeout(120, TimeUnit.SECONDS)
                        .build();
                okhttp3.Response response = client.newCall(request).execute();
                res = response.body().string();
                String[] pars = res.split("error");
                success = pars[1].contains("false");
                JSONObject jObj = null;

                String[] par = res.split("error");
                if (par[1].contains("false")) {
                    String[] pars_ = par[1].split("false,");
                    try {
                        jObj = new JSONObject("{".concat(pars_[1]));
                        JSONObject user = jObj.getJSONObject("user");
                        pref.setDigiCategory(user.getInt("category"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                return res;

            } catch (UnknownHostException | UnsupportedEncodingException e) {
                Log.e("TAG", "Error: " + e.getLocalizedMessage());


            } catch (Exception e) {
                Log.e("TAG", "Other Error: " + e.getLocalizedMessage());

            }


            return res;

        }

        protected void onPostExecute(String file_url) {
            _p1.setVisibility(View.GONE);
            if (success) {
                if(pref.getDigiCategory()!=0){
                    int k=pref.getDigiCategory();
                    String category = null;
                    if(k==1){
                        category="Digisurgery";
                    }else if(k==2){
                        category="Digiclinic";
                    }else if(k==3){
                        category="Digihospital";
                    }else if(k==4){
                        category="DigiICU";
                    }
                    pref.setSymptoms(1);

                    if (!((Activity) mContext).isFinishing()) {
                        ViewDialogFailedSuccess alert = new ViewDialogFailedSuccess();
                        if(pref.getResponsibility()==3) {
                            if (pref.getPODmobile() != null) {
                                alert.showDialog(((Activity) mContext), "Based on  symptoms patient is  placed in  "+category, false);

                            }else{
                                alert.showDialog(((Activity) mContext), "Based on your symptoms you are now placed in  "+category, false);

                            }
                        }else{
                            alert.showDialog(((Activity) mContext), "Based on your symptoms you are now placed in  "+category, false);

                        }

                                           }
                }
            }else{
                Toast.makeText(mContext,"Failed to store information",Toast.LENGTH_SHORT).show();
            }

        }
    }



    private void open_otp() {
        if(!((Activity) mContext).isFinishing()) {
            final Dialog dialog = new Dialog(mContext, R.style.AlertDialogTheme);
            dialog.setContentView(R.layout.otherreason);
            final EditText Otp = dialog.findViewById(R.id.inputOtp_reason);
            final TextInputLayout otp1=dialog.findViewById(R.id._o1);
            Button Start = dialog.findViewById(R.id.btn_dialog);
            dialog.setCancelable(true);

            Otp.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                }

                @Override
                public void onTextChanged(CharSequence s, int start,
                                          int before, int count) {
                    otp1.setError(null);
                    otp1.setErrorEnabled(false);
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });


            Start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (TextUtils.isEmpty(Otp.getText().toString())) {
                        //     Toast.makeText(getApplicationContext(), "Please input OTP", Toast.LENGTH_SHORT).show();
                        otp1.setErrorEnabled(true);
                        otp1.setError("Empty");
                    }else {
                        other_reson=Otp.getText().toString();
                        dialog.cancel();
                    }

                }
            });


            dialog.show();
        }

    }

    public class ViewDialogFailedSuccess {

        public void showDialog(Activity activity, String msg, Boolean noDate) {
            if (!activity.isFinishing()) {
                final Dialog dialog1 = new Dialog(activity);
                dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog1.setCancelable(false);
                dialog1.setContentView(R.layout.custom_dialog_success);
                TextView text = dialog1.findViewById(R.id.text_dialog);
                text.setText(msg);

                Button dialogButton = dialog1.findViewById(R.id.btn_dialog);



                dialogButton.setText("Ok");

                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(_every==0){
                            if(pref.getMask()==0) {
                                if (!((Activity) mContext).isFinishing()) {
                                    new androidx.appcompat.app.AlertDialog.Builder(mContext,R.style.AlertDialogTheme)
                                            .setTitle("Successfully updated")
                                            .setMessage("Please submit your oxygen status")
                                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Intent i = new Intent(mContext, OxygenRequest.class);
                                                    mContext.startActivity(i);

                                                    ((Activity) mContext).overridePendingTransition(R.anim.slide_out2, R.anim.slide_in2);
                                                    dialog.cancel();
                                                }
                                            })
                                            .show();
                                }
                            }else{
                                if (!((Activity) mContext).isFinishing()) {
                                    if (_from == 0) {
                                        if (!((Activity) mContext).isFinishing()) {
                                            new androidx.appcompat.app.AlertDialog.Builder(mContext,R.style.AlertDialogTheme)
                                                    .setTitle("Thank you!")
                                                    .setMessage("Please submit your oxygen status")
                                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            Intent i = new Intent(mContext, OxygenRequest.class);
                                                            mContext.startActivity(i);

                                                            ((Activity) mContext).overridePendingTransition(R.anim.slide_out2, R.anim.slide_in2);
                                                            dialog.cancel();
                                                        }
                                                    })
                                                    .show();
                                        }
                                    } else {
                                        Intent i = new Intent(mContext, PatientDetails.class);
                                        mContext.startActivity(i);
                                        ((Activity) mContext).overridePendingTransition(R.anim.slide_out2, R.anim.slide_in2);
                                    }
                                }
                            }
                        }else{
                            if (!((Activity) mContext).isFinishing()) {
                                new androidx.appcompat.app.AlertDialog.Builder(mContext,R.style.AlertDialogTheme)
                                        .setTitle("Thank you!")
                                        .setMessage("Please submit your oxygen status")
                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                pref.setAlarm(1);
                                                Intent i = new Intent(mContext, OxygenRequest.class);
                                                mContext.startActivity(i);

                                                ((Activity) mContext).overridePendingTransition(R.anim.slide_out2, R.anim.slide_in2);
                                                dialog.cancel();
                                            }
                                        })
                                        .show();
                            }
                        }
                        dialog1.dismiss();
                    }
                });

                dialog1.show();


            }
        }
    }


}






