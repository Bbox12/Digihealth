package com.admin.ecosense.POD;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.admin.ecosense.Activities.MainActivity;
import com.admin.ecosense.Doctors.Doctorwindow;
import com.admin.ecosense.Login.SignIn;
import com.admin.ecosense.Questions.Questions;
import com.admin.ecosense.R;
import com.admin.ecosense.helper.Config_URL;
import com.admin.ecosense.helper.PrefManager;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class Addpatient extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = Addpatient.class.getSimpleName();
    private static final int MY_PERMISSIONS_REQUEST_USE_CAMERA = 1001;
    private static final int REQUEST_EXTERNAL_STORAGE = 1002;
    private static final int IMAGE_ = 1003;
    private PrefManager pref;
    private String _phoneNo;
    private CoordinatorLayout coordinatorLayout;
    private RelativeLayout afterAnimationView;
    private Button signup;
    private AppCompatEditText input_user_name,input_user_password,input_user_mobile;
    private boolean Again;
    private Bitmap bitmap;
    private boolean permissionsAllowd;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String filePath;
    private ProgressBar progressBar;
    private int _role=0;
    private Bitmap bm;
    private String salonmobileno;
    private boolean valid;
    private Dialog dialogWait;
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private TextInputLayout _m0;
    private ActionCodeSettings actionCodeSettings;
    private ProgressDialog mProgressDialog;
    private boolean _clicked;
    private EditText _userBday;
    private AutoCompleteTextView input_relationship;
    private EditText Otp;
    private TextInputLayout otp1;
    private String input_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addpatient);
        pref = new PrefManager(getApplicationContext());
        HashMap<String, String> user = pref.getUserDetails();
        _phoneNo = user.get(PrefManager.KEY_MOBILE);
        coordinatorLayout = findViewById(R.id.cor_home_main);
        progressBar=findViewById(R.id.progress_signup);
        signup=findViewById(R.id.signup);
        signup.setOnClickListener(this);
        input_user_name=findViewById(R.id.input_user_name);
        input_user_password=findViewById(R.id.input_user_password);
        input_user_mobile=findViewById(R.id.input_user_mobile);
        _m0=findViewById(R.id.mo);
        _userBday = findViewById(R.id.input_bday);
        _userBday.setOnClickListener(this);
        input_relationship = findViewById(R.id.input_relationship);
        input_relationship.setOnClickListener(this);
        Otp=findViewById(R.id.inputOtp_reason);
        otp1=findViewById(R.id._o1);


    }


    @Override
    protected void onResume() {
        super.onResume();


        input_user_mobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }
            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.toString().length()==10){
                    input_user_password.requestFocus();


                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        new GetUser().execute();

    }


    private class GetUser extends AsyncTask<Void, Integer, String> {


        private ArrayList<String> mItems = new ArrayList<String>();

        protected String doInBackground(Void... args) {
            return uploadFile();
        }

        private String uploadFile() {
            // TODO Auto-generated method stub
            String res = null;

            try {
                if(_phoneNo==null){
                    _phoneNo="999999999";
                }


                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("_mId", _phoneNo)
                        .build();

                Request request = new Request.Builder()
                        .url(Config_URL.GET_LOGIN)
                        .post(requestBody)
                        .build();
                OkHttpClient client = new OkHttpClient();
                okhttp3.Response response = client.newCall(request).execute();
                res = response.body().string();
                try {
                    JSONObject jsonObj = new JSONObject(res);
                    JSONArray _Attendence = jsonObj.getJSONArray("relationship");
                    for (int i = 0; i < _Attendence.length(); i++) {
                        JSONObject c = _Attendence.getJSONObject(i);
                        if (!c.isNull("Relation")) {
                            mItems.add(c.getString("Relation"));
                        }

                    }



                } catch (final JSONException e) {
                    Log.e("HI", "Json parsing error: " + e.getMessage());


                }

                Log.e("TAG", "Response : " + res);

                return res;

            } catch (UnknownHostException | UnsupportedEncodingException e) {
                Log.e("TAG", "Error: " + e.getLocalizedMessage());
            } catch (Exception e) {
                Log.e("TAG", "Other Error: " + e.getLocalizedMessage());
            }


            return res;

        }
        protected void onPostExecute(String file_url) {
            if(mItems.size()>0) {
                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                        getBaseContext(), android.R.layout.simple_list_item_1, mItems);
                final String[] selection1 = new String[1];
                input_relationship.setAdapter(arrayAdapter);
                input_relationship.setCursorVisible(false);
                input_relationship.showDropDown();
                input_relationship.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    input_user_name.requestFocus();

                    }
                });
            }
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.input_relationship:
                input_relationship.showDropDown();
                break;

            case R.id.input_bday:
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                if ((monthOfYear + 1) > 9) {
                                    if (dayOfMonth > 9) {
                                        _userBday.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                                    } else {
                                        _userBday.setText(year + "-" + (monthOfYear + 1) + "-" + "0" + dayOfMonth);
                                    }
                                } else {
                                    if (dayOfMonth > 9) {
                                        _userBday.setText(year + "-0" + (monthOfYear + 1) + "-" + dayOfMonth);
                                    } else {
                                        _userBday.setText(year + "-0" + (monthOfYear + 1) + "-" + "0" + dayOfMonth);
                                    }
                                }

                            }
                        }, mYear - 18, mMonth, mDay);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                datePickerDialog.show();
                break;



            case R.id.signup:


                if( !TextUtils.isEmpty(input_user_mobile.getText().toString())){
                    if( !TextUtils.isEmpty(_userBday.getText().toString())) {
                        if (!TextUtils.isEmpty(input_user_name.getText().toString())) {
                            if (!TextUtils.isEmpty(input_user_password.getText().toString())) {
                                if (input_user_password.getText().toString().length() >= 8) {
                                    if (!TextUtils.isEmpty(Otp.getText().toString())) {
                                        input_id=Otp.getText().toString();
                                        valid = true;
                                    }else{
                                        Otp.setError("Empty");
                                        Otp.requestFocus();
                                    }

                                } else {
                                    Toast.makeText(getApplicationContext(), "Password must be 8 charecter length", Toast.LENGTH_SHORT).show();
                                    input_user_password.requestFocus();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Please input password", Toast.LENGTH_SHORT).show();
                                input_user_password.requestFocus();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Please input name", Toast.LENGTH_SHORT).show();
                            input_user_name.requestFocus();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(),"Please input date of birth",Toast.LENGTH_SHORT).show();
                        final Calendar c1 = Calendar.getInstance();
                        int mYear1 = c1.get(Calendar.YEAR);
                        int mMonth1 = c1.get(Calendar.MONTH);
                        int mDay1 = c1.get(Calendar.DAY_OF_MONTH);

                        DatePickerDialog datePickerDialog1 = new DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog,
                                new DatePickerDialog.OnDateSetListener() {

                                    @Override
                                    public void onDateSet(DatePicker view, int year,
                                                          int monthOfYear, int dayOfMonth) {
                                        if ((monthOfYear + 1) > 9) {
                                            if (dayOfMonth > 9) {
                                                _userBday.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                                            } else {
                                                _userBday.setText(year + "-" + (monthOfYear + 1) + "-" + "0" + dayOfMonth);
                                            }
                                        } else {
                                            if (dayOfMonth > 9) {
                                                _userBday.setText(year + "-0" + (monthOfYear + 1) + "-" + dayOfMonth);
                                            } else {
                                                _userBday.setText(year + "-0" + (monthOfYear + 1) + "-" + "0" + dayOfMonth);
                                            }
                                        }
                                    }
                                }, mYear1 - 1, mMonth1, mDay1);
                        datePickerDialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                        datePickerDialog1.show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"Please input  mobile no",Toast.LENGTH_SHORT).show();
                    input_user_mobile.requestFocus();
                }

                if(valid) {
                    new PostDataTOServer().execute();
                    }


                break;

            default:
                break;
        }
    }




        public void showDialog(Activity activity, String msg, Boolean noDate) {
            if (!activity.isFinishing()) {
                dialogWait = new Dialog(activity);
                dialogWait.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialogWait.setCancelable(true);
                dialogWait.setContentView(R.layout.custom_dialog_verify);
                WebView _webview = dialogWait.findViewById(R.id.webView);
                _webview.setBackgroundColor(Color.TRANSPARENT); //for gif without background
                _webview.loadUrl("file:///android_asset/verify.gif");
                _webview.getSettings().setLoadsImagesAutomatically(true);
                _webview.getSettings().setLoadWithOverviewMode(true);
                _webview.getSettings().setUseWideViewPort(true);
                TextView text = dialogWait.findViewById(R.id.text_dialog);
                text.setText(msg);

                Button dialogButton = dialogWait.findViewById(R.id.btn_dialog);

                if(noDate){
                    dialogButton.setVisibility(View.VISIBLE);
                }else {
                    dialogButton.setVisibility(View.GONE);
                    signup.setEnabled(false);
                }

                dialogButton.setText("Ok");

                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //   new PostDataTOServer().execute();

                        dialogWait.dismiss();
                    }
                });
                if(noDate) {
                    dialogWait.show();
                }else {
                    dialogWait.dismiss();
                }


            }
        }




    private class PostDataTOServer  extends AsyncTask<Void, Integer, String> {


        private int success=0;
        private File destination;
        private String fileImage;
        private MultipartBody requestBody;
        private String _PhoneNo;
        private DialogFragment dialogWait;
        private ProgressDialog mProgressDialog;


        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            if (!Addpatient.this.isFinishing() ) {
                if(dialogWait!=null){
                    dialogWait.dismiss();
                }
                if (mProgressDialog == null) {
                    mProgressDialog = new ProgressDialog(Addpatient.this, R.style.Theme_AppCompat_DayNight_Dialog_MinWidth);
                    mProgressDialog.setIcon(R.mipmap.ic_launcher);
                    mProgressDialog.setCancelable(false);
                    mProgressDialog.setTitle("Storing information.");
                    mProgressDialog.setMessage("please wait...");
                    mProgressDialog.setIndeterminate(false);
                    mProgressDialog.show();
                }


            }
            pref=new PrefManager(getApplicationContext());
            HashMap<String, String> user = pref.getUserDetails();
            _PhoneNo = user.get(PrefManager.KEY_MOBILE);
        }

        protected String doInBackground(Void... args) {
            return uploadFile();
        }

        private String uploadFile() {
            // TODO Auto-generated method stub
            String res = null;
            final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/*");

            try {

                requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("pod", _PhoneNo)
                        .addFormDataPart("relation", input_relationship.getText().toString())
                        .addFormDataPart("mobile", input_user_mobile.getText().toString())
                        .addFormDataPart("ID", input_id)
                        .addFormDataPart("bday", _userBday.getText().toString().toUpperCase())
                        .addFormDataPart("username", input_user_name.getText().toString().toUpperCase())
                        .addFormDataPart("password", input_user_password.getText().toString().toUpperCase())
                        .build();

                Request request = new Request.Builder()
                        .url(Config_URL.URL_SIGNUPPOD)
                        .post(requestBody)
                        .build();
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(120, TimeUnit.SECONDS)
                        .writeTimeout(120, TimeUnit.SECONDS)
                        .readTimeout(120, TimeUnit.SECONDS)
                        .build();
                okhttp3.Response response = client.newCall(request).execute();
                res = response.body().string();
                String[] par = res.split("error");
                if (par[1].contains("false")) {
                    success =1;
                }else if (par[1].contains("2")) {
                    success=2;
                }

                Log.e("TAG", "Response : " + res);

                return res;

            } catch (UnknownHostException | UnsupportedEncodingException e) {
                Log.e("TAG", "Error: " + e.getLocalizedMessage());
            } catch (Exception e) {
                Log.e("TAG", "Other Error: " + e.getLocalizedMessage());
                if(e.getLocalizedMessage()!=null && e.getLocalizedMessage().contains("timeout")){
                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, "Slow connection.", Snackbar.LENGTH_LONG)
                            .setAction("Retry", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    new PostDataTOServer().execute();
                                }
                            });
                    snackbar.setActionTextColor(Color.RED);
                    snackbar.show();
                }
            }


            return res;

        }

        protected void onPostExecute(String file_url) {

            if (mProgressDialog != null) {
                mProgressDialog.dismiss();
            }

            if (success==1) {
                //pref.setPODmobile(input_user_mobile.getText().toString());
                Addpatient.ViewDialogFailedSuccess alert = new Addpatient.ViewDialogFailedSuccess();
                alert.showDialog(Addpatient.this, "Succesfully stored information.",false);
            }else if (success==2) {
                signup.setEnabled(true);
                Addpatient.ViewDialogFailed alert = new Addpatient.ViewDialogFailed();
                alert.showDialog(Addpatient.this, "ID does not matched! Please input correct ID.",true);
            }else{
                signup.setEnabled(true);
                Addpatient.ViewDialogFailed alert = new Addpatient.ViewDialogFailed();
                alert.showDialog(Addpatient.this, "Failed to store information..",true);
            }

        }


    }


    public class ViewDialogFailed {

        public void showDialog(Activity activity, String msg, Boolean noDate) {
            if (!activity.isFinishing()) {
                final Dialog dialog1 = new Dialog(activity);
                dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog1.setCancelable(true);

                dialog1.setContentView(R.layout.custom_dialog_failed);
                dialog1.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog1.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                TextView text = dialog1.findViewById(R.id.text_dialog);
                text.setText(msg);

                Button dialogButton = dialog1.findViewById(R.id.btn_dialog);

                dialogButton.setText("Ok");
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog1.dismiss();
                    }
                });

                dialog1.show();
            }
        }
    }

    public class ViewDialogFailedSuccess {

        public void showDialog(Activity activity, String msg, Boolean noDate) {
            if (!activity.isFinishing()) {
                final Dialog dialog1 = new Dialog(activity);
                dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog1.setCancelable(false);
                dialog1.setContentView(R.layout.custom_dialog_success);
                dialog1.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog1.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                TextView text = dialog1.findViewById(R.id.text_dialog);
                text.setText(msg);


                Button dialogButton = dialog1.findViewById(R.id.btn_dialog);



                dialogButton.setText("Ok");

                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        getInfo();
                        dialog1.dismiss();
                    }
                });

                dialog1.show();


            }
        }
    }


    private void getInfo() {
            Intent o = new Intent(Addpatient.this, MonitorFamilyPOD.class);
            startActivity(o);
            overridePendingTransition(R.anim.slide_up1, R.anim.slide_down1);

    }




    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        //replaces the default 'Back' button action
        if(keyCode== KeyEvent.KEYCODE_BACK)   {
            finish();
            overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);


        }
        return true;
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }
}
