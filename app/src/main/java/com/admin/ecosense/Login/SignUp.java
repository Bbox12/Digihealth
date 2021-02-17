package com.admin.ecosense.Login;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.admin.ecosense.Activities.MainActivity;
import com.admin.ecosense.BuildConfig;
import com.admin.ecosense.Doctors.Doctorwindow;
import com.admin.ecosense.Questions.Questions;
import com.admin.ecosense.R;
import com.admin.ecosense.helper.Config_URL;
import com.admin.ecosense.helper.PrefManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class SignUp extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = SignUp.class.getSimpleName();
    private static final int MY_PERMISSIONS_REQUEST_USE_CAMERA = 1001;
    private static final int REQUEST_EXTERNAL_STORAGE = 1002;
    private static final int IMAGE_ = 1003;
    private PrefManager pref;
    private String _phoneNo;
    private CoordinatorLayout coordinatorLayout;
    private RelativeLayout afterAnimationView;
    private Button signup,forgot_pwd;
    private ImageView img_profilo;
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
    private String input_id;
    private EditText Otp;
    private TextInputLayout otp1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        pref = new PrefManager(getApplicationContext());
        HashMap<String, String> user = pref.getUserDetails();
        _phoneNo = user.get(PrefManager.KEY_MOBILE);
        coordinatorLayout = findViewById(R.id
                .cor_home_main);
        progressBar=findViewById(R.id.progress_signup);
        forgot_pwd=findViewById(R.id.forgot_pwd);
        signup=findViewById(R.id.signup);
        forgot_pwd.setOnClickListener(this);
        signup.setOnClickListener(this);
        img_profilo=findViewById(R.id.img_profilo);
        img_profilo.setOnClickListener(this);
        input_user_name=findViewById(R.id.input_user_name);
        input_user_password=findViewById(R.id.input_user_password);
        input_user_mobile=findViewById(R.id.input_user_mobile);
        _m0=findViewById(R.id.mo);
        mAuth= FirebaseAuth.getInstance();

        _userBday = findViewById(R.id.input_bday);
        _userBday.setOnClickListener(this);

         Otp = findViewById(R.id.inputOtp_reason);
         otp1=findViewById(R.id._o1);

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                Log.d(TAG, "onVerificationCompleted:" + credential);
                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Log.w(TAG, "onVerificationFailed", e);
                ViewDialogVerify alert = new ViewDialogVerify();
                alert.showDialog(SignUp.this, "Verification failed. Please check mobile no.",false);

            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                Log.d(TAG, "onCodeSent:" + verificationId);
                open_otp(verificationId);
            }
        };

        actionCodeSettings =
                ActionCodeSettings.newBuilder()
                        // URL you want to redirect back to. The domain (www.example.com) for this
                        // URL must be whitelisted in the Firebase Console.
                        .setUrl("https://ecosense.page.link/H3Ed")
                        .setHandleCodeInApp(true)
                        .setAndroidPackageName(
                                "com.admin.ecosense",
                                true, /* installIfNotAvailable */
                                "19"    /* minimumVersion */)
                        .build();
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
                    salonmobileno = "+27" + input_user_mobile.getText().toString();
                    //input_user_mobile.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_phone, 0, R.mipmap.ic_cor, 0);
                    input_user_password.requestFocus();


                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        if(Again){
            if(bitmap!=null) {
                img_profilo.setImageBitmap(bitmap);
            }
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_profilo:
               _clicked=true;
                if (permissionsAllowd) {

                    selectImage();
                } else {
                    checkAndRequestPermissions();
                }

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


                if(valid) {
                    /*  if(!TextUtils.isEmpty(input_user_mobile.getText().toString())) {
                          if ((input_user_mobile.getText().toString().length() >= 10)) {
                              salonmobileno = "+27" + input_user_mobile.getText().toString();
                              PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                      salonmobileno,        // Phone number to verify
                                      60,                 // Timeout duration
                                      TimeUnit.SECONDS,   // Unit of timeout
                                      SignUp.this,               // Activity (for callback binding)
                                      mCallbacks);
                              ViewDialogVerifyWait Firstalert = new ViewDialogVerifyWait();
                              Firstalert.showDialog(SignUp.this, "Verifying phone no. Please wait...", false);
                          }
                      }*/
                    new PostDataTOServer().execute();
                }
                break;
            case R.id.forgot_pwd:
                Intent o = new Intent(SignUp.this, SmsActivity.class);
                o.putExtra("from",1);
                startActivity(o);

                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);

                break;
            default:
                break;
        }
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "task.isSuccessful" + String.valueOf(task.isSuccessful()));
                        if (task.isSuccessful()) {
                            new PostDataTOServer().execute();
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                ViewDialogVerify alert = new ViewDialogVerify();
                                alert.showDialog(SignUp.this, "Verification failed. Please check mobile no.",false);

                            }
                        }
                    }
                });
    }

    private void open_otp(final String verificationId) {
        if(!SignUp.this.isFinishing()) {
            final Dialog dialog = new Dialog(SignUp.this, R.style.ThemeTransparent);
            if(dialogWait!=null){
                dialogWait.dismiss();
            }

            dialog.setContentView(R.layout.custom_dialog_otp);
            final EditText Otp = dialog.findViewById(R.id.inputOtp_ride);
            final TextInputLayout  otp1=dialog.findViewById(R.id.otp1);
            Button Start = dialog.findViewById(R.id.btn_dialog);
            WebView _webview = dialog.findViewById(R.id.webView);
            _webview.setBackgroundColor(Color.TRANSPARENT); //for gif without background
            _webview.loadUrl("file:///android_asset/verify.gif");
            _webview.getSettings().setLoadsImagesAutomatically(true);
            _webview.getSettings().setLoadWithOverviewMode(true);
            _webview.getSettings().setUseWideViewPort(true);
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
                        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, Otp.getText().toString());
                        mAuth.signInWithCredential(credential)
                                .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            dialog.dismiss();
                                            ViewDialogVerify alert = new ViewDialogVerify();
                                            alert.showDialog(SignUp.this, "Verified mobile no. Please proceed.",true);
                                        } else {
                                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                                ViewDialogVerify alert = new ViewDialogVerify();
                                                alert.showDialog(SignUp.this, "Verification failed. Please check mobile no.",false);
                                            }
                                        }
                                    }
                                });
                        dialog.cancel();
                    }

                }
            });


            dialog.show();
        }

    }

    public class ViewDialogVerify {

        public void showDialog(Activity activity, String msg, Boolean noDate) {
            if (!activity.isFinishing()) {
                final Dialog dialog1 = new Dialog(activity);
                dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog1.setCancelable(true);
                dialog1.setContentView(R.layout.custom_dialog_verify);
                WebView _webview = dialog1.findViewById(R.id.webView);
                _webview.setBackgroundColor(Color.TRANSPARENT); //for gif without background
                _webview.loadUrl("file:///android_asset/verify.gif");
                _webview.getSettings().setLoadsImagesAutomatically(true);
                _webview.getSettings().setLoadWithOverviewMode(true);
                _webview.getSettings().setUseWideViewPort(true);
                TextView text = dialog1.findViewById(R.id.text_dialog);
                text.setText(msg);

                Button dialogButton = dialog1.findViewById(R.id.btn_dialog);

                if(noDate){
                    dialogButton.setVisibility(View.VISIBLE);
                }else {
                    dialogButton.setVisibility(View.GONE);
                    signup.setEnabled(false);
                }

                dialogButton.setText("Ok");
                if(dialogWait!=null) {
                    dialogWait.dismiss();
                }
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new PostDataTOServer().execute();
                        dialog1.dismiss();
                    }
                });

                dialog1.show();


            }
        }
    }
    public class ViewDialogVerifyWait {

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
    }
    private void selectImage() {
        if (!SignUp.this.isFinishing()) {
            final CharSequence[] items = {"Take from camera", "Choose from Library",
                    "Cancel"};
            AlertDialog.Builder builder = new AlertDialog.Builder(SignUp.this, R.style.AlertDialogTheme);
            builder.setTitle("Add a Photo!");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {
                    if (items[item].equals("Take from camera")) {
                        dialog.dismiss();
                        cameraIntent();
                    } else if (items[item].equals("Choose from Library")) {
                        dialog.dismiss();
                        galleryIntent();
                    } else if (items[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                }
            });
            builder.show();
        }
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    private void galleryIntent() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }

    }

    private void onSelectFromGalleryResult(Intent data) {

        if (data != null) {
            try {
                bm=Bitmap.createScaledBitmap(MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData()), 220, 220, true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Again=true;
        img_profilo.setImageBitmap(bm);

    }

    private void onCaptureImageResult(Intent data) {
         bm = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        //thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
            filePath = destination.getPath();

        } catch (IOException e) {
            e.printStackTrace();
        }
        Again=true;
        img_profilo.setImageBitmap(bm);

    }

    private void checkAndRequestPermissions() {
        List<String> listPermissionsNeeded = new ArrayList<>();
        int writepermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (writepermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }


        if (listPermissionsNeeded.size()>0) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), MULTIPLE_PERMISSIONS);

        } else {
            permissionsAllowd=true;
            if(_clicked){
                selectImage();
            }
        }

    }
    public static final int MULTIPLE_PERMISSIONS = 10;
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        Log.d(TAG, "Permission callback called-------");
        if (requestCode == MULTIPLE_PERMISSIONS) {
            Map<String, Integer> perms = new HashMap<>();
            perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);

            // Fill with actual results from user
            if (grantResults.length > 0) {
                for (int i = 0; i < permissions.length; i++)
                    perms.put(permissions[i], grantResults[i]);
                // Check for both permissions
                if ( perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                ) {
                    Log.d(TAG, "sms & location services permission granted");
                    // process the normal flow
                    permissionsAllowd=true;
                    if(_clicked){
                        selectImage();
                    }
                } else {
                    Log.d(TAG, "Some permissions are not granted ask again ");
                    explain("You need to give the mandatory permissions to continue");
                }
            }
        }


    }

    private void explain(String msg) {
        new AlertDialog.Builder(SignUp.this)

                .setMessage(msg)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + BuildConfig.APPLICATION_ID)));
                        dialog.cancel();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();
                    }
                }).show();

    }


    private class PostDataTOServer  extends AsyncTask<Void, Integer, String> {


        private int success=0;
        private File destination;
        private String fileImage;
        private MultipartBody requestBody;
        private String IDPatient;


        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            if (!SignUp.this.isFinishing() ) {
                if(dialogWait!=null){
                    dialogWait.dismiss();
                }
                if (mProgressDialog == null) {
                    mProgressDialog = new ProgressDialog(SignUp.this, R.style.Theme_AppCompat_DayNight_Dialog_MinWidth);
                    mProgressDialog.setIcon(R.mipmap.ic_launcher);
                    mProgressDialog.setCancelable(false);
                    mProgressDialog.setTitle("Storing information.");
                    mProgressDialog.setMessage("please wait...");
                    mProgressDialog.setIndeterminate(false);
                    mProgressDialog.show();
                }


            }


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
                            .addFormDataPart("mobile", input_user_mobile.getText().toString())
                            .addFormDataPart("ID", input_id)
                            .addFormDataPart("bday", _userBday.getText().toString().toUpperCase())
                            .addFormDataPart("username", input_user_name.getText().toString().toUpperCase())
                            .addFormDataPart("password", input_user_password.getText().toString().toUpperCase())
                            .build();

                Request request = new Request.Builder()
                        .url(Config_URL.URL_SIGNUP)
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
                    String[] pars_ = par[1].split("false,");
                    JSONObject jObj = new JSONObject("{".concat(pars_[1]));
                    final JSONObject user = jObj.getJSONObject("user");
                        success =1;
                    IDPatient = user.getString("IDPatient");
                }else if (par[1].contains("2")) {
                    success=2;
                }else if (par[1].contains("3")) {
                    success=3;
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
                if(IDPatient!=null) {
                    ViewDialogFailedSuccess alert = new ViewDialogFailedSuccess();
                    alert.showDialog(SignUp.this, "Please use the user ID to signin.", IDPatient);
                }else{
                    ViewDialogFailed alert = new ViewDialogFailed();
                    alert.showDialog(SignUp.this, "Failed to generate ID. Please contact adminnistrator.",true);
                }

            }else if (success==2) {
                signup.setEnabled(true);
                ViewDialogFailed alert = new ViewDialogFailed();
                alert.showDialog(SignUp.this, "ID does not matched! Please input correct ID.",true);
            }
            else if (success==3) {
                signup.setEnabled(true);
                ViewDialogFailed alert = new ViewDialogFailed();
                alert.showDialog(SignUp.this, "ID exist. Please signin.",true);
            }else{
                signup.setEnabled(true);
                ViewDialogFailed alert = new ViewDialogFailed();
                alert.showDialog(SignUp.this, "Failed to store information..",true);
            }

        }


    }

    int getYear(Date date1,Date date2){
        SimpleDateFormat simpleDateformat=new SimpleDateFormat("yyyy");
        Integer.parseInt(simpleDateformat.format(date1));

        return Integer.parseInt(simpleDateformat.format(date2))- Integer.parseInt(simpleDateformat.format(date1));

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

        public void showDialog(Activity activity, String msg, String noDate) {
            if (!activity.isFinishing()) {
                final Dialog dialog1 = new Dialog(activity);
                dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog1.setCancelable(false);
                dialog1.setContentView(R.layout.custom_dialog_success);
                dialog1.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog1.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                TextView text = dialog1.findViewById(R.id.text_dialog);
                TextView patientID = dialog1.findViewById(R.id.patientID);
                TextView userID = dialog1.findViewById(R.id.userID);
                userID.setVisibility(View.VISIBLE);
                patientID.setVisibility(View.VISIBLE);
                patientID.setText(noDate);
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
      /*  if(pref.getResponsibility()==1 ) {
            if(pref.getFirstQuestion()==0) {
                Intent o = new Intent(SignUp.this, Questions.class);
                startActivity(o);
                overridePendingTransition(R.anim.slide_up1, R.anim.slide_down1);

            }else{
                Intent o = new Intent(SignUp.this, MainActivity.class);
                startActivity(o);
                overridePendingTransition(R.anim.slide_up1, R.anim.slide_down1);

            }
        }else if(pref.getResponsibility()==2) {

        }else if(pref.getResponsibility()==3) {
            Intent o = new Intent(SignUp.this, MainActivity.class);
            startActivity(o);
            overridePendingTransition(R.anim.slide_up1, R.anim.slide_down1);

        }*/
        Intent o = new Intent(SignUp.this, SignIn.class);
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
