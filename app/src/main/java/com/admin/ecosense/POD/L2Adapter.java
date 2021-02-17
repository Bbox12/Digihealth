package com.admin.ecosense.POD;

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
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.recyclerview.widget.RecyclerView;

import com.admin.ecosense.Doctors.PatientDetails;
import com.admin.ecosense.R;
import com.admin.ecosense.RequestTest.OxygenRequest;
import com.admin.ecosense.helper.Config_URL;
import com.admin.ecosense.helper.Qns;
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

public class L2Adapter extends RecyclerView.Adapter<L2Adapter.ViewHolder> {

    private ArrayList<Qns> mItems;
    private Context mContext;
    private PrefManager pref;
    private String _PhoneNo;
    ArrayList<Integer> TotalDays = new ArrayList<Integer>();
    private Button _Submit;
    private ProgressBar _p1;
    private String sampletype;
    private String indication;
    private int _from = 0;
    private String other_reson = "";
    private AppCompatCheckBox greater;
    private boolean _greater = false;
    private int _every = 0;


    public L2Adapter(Context aContext, ArrayList<Qns> mItems) {
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
    public L2Adapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        //More to come
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.symadapter, viewGroup, false);
        L2Adapter.ViewHolder viewHolder = new L2Adapter.ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final L2Adapter.ViewHolder viewHolder, final int position) {
        final Qns album_pos = mItems.get(position);


        if (album_pos.getSymptoms(position) != null && !TextUtils.isEmpty(album_pos.getSymptoms(position))) {
            viewHolder.yeshome.setText(album_pos.getSymptoms(position));
            viewHolder.yeshome.setChecked(true);
        }


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






