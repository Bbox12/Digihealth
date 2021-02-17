package com.admin.ecosense.Login;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.admin.ecosense.Activities.MainActivity;
import com.admin.ecosense.Activities.Splash_screen;
import com.admin.ecosense.Doctors.Doctorwindow;
import com.admin.ecosense.Questions.Questions;
import com.admin.ecosense.R;
import com.admin.ecosense.helper.AppController;
import com.admin.ecosense.helper.Config_URL;
import com.admin.ecosense.helper.PrefManager;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class SignIn extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = SignIn.class.getSimpleName();
    private static final int MY_PERMISSIONS_REQUEST_USE_CAMERA = 1001;
    private static final int REQUEST_EXTERNAL_STORAGE = 1002;
    private static final int IMAGE_ = 1003;
    private PrefManager pref;
    private String _phoneNo;
    private CoordinatorLayout coordinatorLayout;
    private RelativeLayout afterAnimationView;
    private Button signup,forgot_pwd,forgot_user;
    private AppCompatEditText input_user_password,input_user_mobile;
    private String filePath;
    private ProgressBar progressBar;
    private Bitmap bm;
    private String salonmobileno;
    private boolean valid;
    private Dialog dialogWait;
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private ActionCodeSettings actionCodeSettings;
    private int _from=0;
    private String User_image,User_name;
    private int version_=3,imp=1;
    private int version_1=0,_imp_1=1;
    private boolean _got;
    private DatabaseReference mDatabase;
    private boolean _second;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        pref = new PrefManager(getApplicationContext());
        HashMap<String, String> user = pref.getUserDetails();
        _phoneNo = user.get(PrefManager.KEY_MOBILE);
        coordinatorLayout = findViewById(R.id
                .cor_home_main);
        progressBar=findViewById(R.id.progress_signup);
        forgot_pwd=findViewById(R.id.forgot_pwd);
        signup=findViewById(R.id.signup);
        forgot_pwd.setOnClickListener(SignIn.this);
        forgot_user=findViewById(R.id.forgot_user);
        forgot_user.setOnClickListener(this);
        signup.setOnClickListener(this);
        input_user_password=findViewById(R.id.input_user_password);
        input_user_mobile=findViewById(R.id.input_user_mobile);


        mAuth= FirebaseAuth.getInstance();

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                Log.d(TAG, "onVerificationCompleted:" + credential);

                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w(TAG, "onVerificationFailed", e);

                ViewDialogVerify alert = new ViewDialogVerify();
                alert.showDialog(SignIn.this, "Verification failed. Please check mobile no.",false);

            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d(TAG, "onCodeSent:" + verificationId);

                open_otp(verificationId);
            }
        };

        actionCodeSettings =
                ActionCodeSettings.newBuilder()
                        // URL you want to redirect back to. The domain (www.example.com) for this
                        // URL must be whitelisted in the Firebase Console.
                        .setUrl("https://ecosense.page.link/H3Ed")
                        // This must be true
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



        Intent intent = getIntent();
        _from=intent.getIntExtra("from",0);

        input_user_mobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(final CharSequence s, int start,
                                      int before, int count) {

                    if (s.toString().length() >= 10) {
                        if (TextUtils.isDigitsOnly(s)) {
                            if (isValidPhoneNumber(input_user_mobile.getText().toString())) {
                                //    input_user_mobile.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_phone, 0, R.mipmap.ic_cor, 0);
                                input_user_password.requestFocus();
                            }
                        }
                    }

            }

            @Override
            public void afterTextChanged(final Editable editable) {

            }
        });
    }


    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    private static boolean isValidPhoneNumber(String phone) {
        boolean check=false;
        if(!Pattern.matches("[a-zA-Z]+", phone)) {
            String[] str2 =phone.split("");
            int j=0;
            for (String s : str2) {
                if (!TextUtils.isEmpty(s)) {
                    j++;
                }
            }
            check = j == 10 || j == 13;
        }
        return check;

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){


            case R.id.signup:

                if(!TextUtils.isEmpty(input_user_mobile.getText().toString())){
                    if(!TextUtils.isEmpty(input_user_password.getText().toString())){
                        valid=true;
                    }else{
                        Toast.makeText(getApplicationContext(),"Please input password",Toast.LENGTH_SHORT).show();
                        input_user_password.requestFocus();
                    }

                }else{
                    Toast.makeText(getApplicationContext(),"Please input  mobile no",Toast.LENGTH_SHORT).show();
                    input_user_mobile.requestFocus();
                }

                if(valid) {
                        checkEmail();


                }
                break;
            case R.id.forgot_pwd:
                Intent o = new Intent(SignIn.this, SmsActivity.class);
                o.putExtra("from", 1);
                startActivity(o);

                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);

                break;
            case R.id.forgot_user:
                Intent forgot_user = new Intent(SignIn.this, ForGotSignUp.class);
                forgot_user.putExtra("from", 1);
                startActivity(forgot_user);

                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);

                break;
            default:
                break;
        }
    }

    private void checkEmail() {
        StringRequest eventoReq = new StringRequest(com.android.volley.Request.Method.POST, Config_URL.URL_SIGNIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.w("kk", response.toString());


                        try {

                            String[] par = response.split("error");
                            if (par[1].contains("false")) {
                                String[] pars_ = par[1].split("false,");
                                JSONObject jObj = new JSONObject("{".concat(pars_[1]));
                                JSONObject user = jObj.getJSONObject("user");
                                pref.setFirstQuestion(user.getInt("first"));
                                pref.setResponsibility(user.getInt("role"));
                                pref.setConsultation(user.getInt("doc"));
                                pref.setMask(user.getInt("mask"));
                                pref.setAge(user.getInt("Age"));
                                pref.setDOB(user.getString("DOB"));
                                if(pref.getResponsibility()==1 || pref.getResponsibility()==3 ) {
                                    pref.setWorkID(user.getInt("unique"));
                                }
                                pref.createLogin( user.getString("Name"),user.getString("ID"));
                                pref.setWAQF("WAQF-"+String.format("%04d",user.getInt("unique")));
                                pref.setDate(user.getString("date"));
                                SimpleDateFormat simpleDateformat=new SimpleDateFormat("yyyy");
                                try {
                                    Date d2=simpleDateformat.parse(pref.getDate());
                                    Date d1=simpleDateformat.parse(pref.getDOB());
                                    pref.setAge(getYear(d1,d2));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                final String ID=user.getString("ID");
                                final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                                FirebaseInstanceId.getInstance().getInstanceId()
                                        .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                                            private String token;

                                            @Override
                                            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                                if (!task.isSuccessful()) {
                                                    Log.w(TAG, "getInstanceId failed", task.getException());
                                                    return;
                                                }
                                                token = task.getResult().getToken();
                                                mDatabase.child("RegID").child(ID).setValue(token);
                                                pref.setReferalCode(token);
                                            }
                                        });
                                ViewDialogFailedSuccess alert = new ViewDialogFailedSuccess();
                                alert.showDialog(SignIn.this, "Success! Welcome to DigiHealth.", false);
                            }else{
                                ViewDialogFailed alert = new ViewDialogFailed();
                                alert.showDialog(SignIn.this, "Please check the information provided!", true);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }



                    }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                vollyError(error);
            }

        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("role",String.valueOf(0));
                params.put("mobile",input_user_mobile.getText().toString());
                params.put("password", input_user_password.getText().toString());
                return params;
            }

        };

        // Añade la peticion a la cola
        AppController.getInstance().addToRequestQueue(eventoReq);

    }


    int getYear(Date date1,Date date2){
        SimpleDateFormat simpleDateformat=new SimpleDateFormat("yyyy");
        Integer.parseInt(simpleDateformat.format(date1));

        return Integer.parseInt(simpleDateformat.format(date2))- Integer.parseInt(simpleDateformat.format(date1));

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(SignIn.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "task.isSuccessful" + String.valueOf(task.isSuccessful()));
                        if (task.isSuccessful()) {
                            ViewDialogVerify alert = new ViewDialogVerify();
                            alert.showDialog(SignIn.this, "Verified mobile no. Please proceed.",true);

                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                ViewDialogVerify alert = new ViewDialogVerify();
                                alert.showDialog(SignIn.this, "Verification failed. Please check mobile no.",false);

                            }
                        }
                    }
                });
    }

    private void open_otp(final String verificationId) {
        if(!SignIn.this.isFinishing()) {
            final Dialog dialog = new Dialog(SignIn.this, R.style.ThemeTransparent);
            if(dialogWait!=null){
                dialogWait.dismiss();
            }

            dialog.setContentView(R.layout.custom_dialog_otp);
            final EditText Otp = dialog.findViewById(R.id.inputOtp_ride);
            Button Start = dialog.findViewById(R.id.btn_dialog);
            WebView _webview = dialog.findViewById(R.id.webView);
            _webview.setBackgroundColor(Color.TRANSPARENT); //for gif without background
            _webview.loadUrl("file:///android_asset/verify.gif");
            _webview.getSettings().setLoadsImagesAutomatically(true);
            _webview.getSettings().setLoadWithOverviewMode(true);
            _webview.getSettings().setUseWideViewPort(true);
            dialog.setCancelable(true);

            Start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (TextUtils.isEmpty(Otp.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "Please input OTP", Toast.LENGTH_SHORT).show();
                    }else {
                        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, Otp.getText().toString());
                        mAuth.signInWithCredential(credential)
                                .addOnCompleteListener(SignIn.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            dialog.dismiss();
                                            ViewDialogVerify alert = new ViewDialogVerify();
                                            alert.showDialog(SignIn.this, "Verified mobile no. Please proceed.",true);
                                        } else {
                                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {

                                                ViewDialogVerify alert = new ViewDialogVerify();
                                                alert.showDialog(SignIn.this, "Verification failed. Please check mobile no.",false);
                                            }
                                        }
                                    }
                                });

                    }
                    dialog.cancel();
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
                }

                dialogButton.setText("Ok");
                if(dialogWait!=null) {
                    dialogWait.dismiss();
                }
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkEmail();
                        dialog1.dismiss();
                    }
                });

                dialog1.show();


            }
        }
    }


    public class ViewDialogFailed {

        public void showDialog(Activity activity, String msg, Boolean noDate) {
            if (!activity.isFinishing()) {
                final Dialog dialog1 = new Dialog(activity);
                dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog1.setCancelable(false);

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
                TextView text = dialog1.findViewById(R.id.text_dialog);
                text.setText(msg);

                Button dialogButton = dialog1.findViewById(R.id.btn_dialog);



                dialogButton.setText("Ok");

                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog1.dismiss();
                        if(pref.getResponsibility()==1 ) {
                            if(pref.getFirstQuestion()==0) {
                                Intent o = new Intent(SignIn.this, Questions.class);
                                startActivity(o);
                                overridePendingTransition(R.anim.slide_up1, R.anim.slide_down1);

                            }else{
                                Intent o = new Intent(SignIn.this, Splash_screen.class);
                                startActivity(o);
                                overridePendingTransition(R.anim.slide_up1, R.anim.slide_down1);

                            }
                        }else if(pref.getResponsibility()==2) {
                            Intent o = new Intent(SignIn.this, Doctorwindow.class);
                            startActivity(o);
                            overridePendingTransition(R.anim.slide_up1, R.anim.slide_down1);

                        }else if(pref.getResponsibility()==3) {
                            Intent o = new Intent(SignIn.this, Splash_screen.class);
                            startActivity(o);
                            overridePendingTransition(R.anim.slide_up1, R.anim.slide_down1);

                        }
                    }
                });

                dialog1.show();


            }
        }
    }




    private void vollyError(VolleyError error) {
        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "Network is unreachable. Please try another time", Snackbar.LENGTH_LONG)
                    .setAction("Refresh", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            overridePendingTransition(0, 0);
                            startActivity(getIntent());
                        }
                    });
            snackbar.setActionTextColor(Color.RED);
            snackbar.show();
        } else if (error instanceof AuthFailureError) {
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "Network is unreachable. Please try another time", Snackbar.LENGTH_LONG)
                    .setAction("Refresh", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            overridePendingTransition(0, 0);
                            startActivity(getIntent());
                        }
                    });
            snackbar.setActionTextColor(Color.RED);
            snackbar.show();
        } else if (error instanceof ServerError) {
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "Server Error.Please try another time", Snackbar.LENGTH_LONG)
                    .setAction("Refresh", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            overridePendingTransition(0, 0);
                            startActivity(getIntent());
                        }
                    });
            snackbar.setActionTextColor(Color.RED);
            snackbar.show();
        } else if (error instanceof NetworkError) {
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "Network Error. Please try another time", Snackbar.LENGTH_LONG)
                    .setAction("Refresh", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            overridePendingTransition(0, 0);
                            startActivity(getIntent());
                        }
                    });
            snackbar.setActionTextColor(Color.RED);
            snackbar.show();
        } else if (error instanceof ParseError) {
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "Data Error. Please try another time", Snackbar.LENGTH_LONG)
                    .setAction("Refresh", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            overridePendingTransition(0, 0);
                            startActivity(getIntent());
                        }
                    });
            snackbar.setActionTextColor(Color.RED);
            snackbar.show();
        }


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


}
