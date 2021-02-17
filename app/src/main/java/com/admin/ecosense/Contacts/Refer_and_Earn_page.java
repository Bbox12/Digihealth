package com.admin.ecosense.Contacts;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.admin.ecosense.Activities.MainActivity;
import com.admin.ecosense.R;
import com.admin.ecosense.helper.PrefManager;
import com.google.android.gms.appinvite.AppInviteInvitation;
import com.google.android.material.snackbar.Snackbar;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * Created by parag on 28/02/18.
 */

public class Refer_and_Earn_page extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = Refer_and_Earn_page.class.getSimpleName();
    private static final int REQUEST_INVITED = 101;
    private Toolbar toolbar;
    private PrefManager pref;
    private String _PhoneNo;
    private double My_lat = 0, My_long = 0;
    private ProgressBar progressBar;
    private ImageView WhatsApp, Messenger, Email, Message, Twitter, Facebook;
    private Button Refer;
    private String strShareMessage;
    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.refer_earn);

        progressBar = findViewById(R.id.progressBarRefer);

        WhatsApp = findViewById(R.id.whatsapp);
        Messenger = findViewById(R.id.messenger);
        Email = findViewById(R.id.email);
        Message = findViewById(R.id.message);
        Twitter = findViewById(R.id.twitter);
        Facebook = findViewById(R.id.facebok);
        Refer = findViewById(R.id.buttonRefer);

        coordinatorLayout = findViewById(R.id.coordinatorLayout);
        toolbar = findViewById(R.id.toolbardd);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        pref = new PrefManager(getApplicationContext());
        HashMap<String, String> user = pref.getUserDetails();
        _PhoneNo = user.get(PrefManager.KEY_MOBILE);

 //       strShareMessage = "\nSign up with my code " + pref.getReferalCode() +" to avail disounts! Only on " + "https://play.google.com/store/apps/details?id=" + getPackageName();


        strShareMessage= "\nLet me check your whereabouts. Please download DigiHealth  " +Html.fromHtml("https://ecosense.page.link/H3Ed");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
            }
        });


        WhatsApp.setOnClickListener(this);
        Message.setOnClickListener(this);
        Messenger.setOnClickListener(this);
        Email.setOnClickListener(this);
        Twitter.setOnClickListener(this);
        Facebook.setOnClickListener(this);
        Refer.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();

    }



    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.whatsapp:
                sendAppMsg(v);
                break;
            case R.id.messenger:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);

                sendIntent
                        .putExtra(Intent.EXTRA_TEXT,
                                strShareMessage);
                sendIntent.setType("text/plain");
                sendIntent.setPackage("com.facebook.orca");
                try {
                    startActivity(sendIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getApplicationContext(), "Please Install Facebook Messenger", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.message:
                Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                smsIntent.setType("vnd.android-dir/mms-sms");
                smsIntent.putExtra("address", "");

                smsIntent.putExtra("sms_body", strShareMessage);
                startActivity(smsIntent);
                break;
            case R.id.email:
                sendEmail(strShareMessage);
                break;
            case R.id.twitter:
                try {
                    // Check if the Twitter app is installed on the phone.
                    getApplicationContext().getPackageManager().getPackageInfo("com.twitter.android", 0);
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setClassName("com.twitter.android", "com.twitter.android.composer.ComposerActivity");
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_TEXT, "Your text");
                    startActivity(intent);

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Twitter is not installed on this device", Toast.LENGTH_LONG).show();

                }
                break;
            case R.id.facebok:
                sendAppFacebook(v);
                break;

            case R.id.buttonRefer:
                onInviteClicked();
                break;

            default:
                break;

        }
    }

    public void sendAppFacebook(View view) {

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        String text = strShareMessage;
        // change with required  application package

        intent.setPackage("com.facebook.katana");
        intent.putExtra(Intent.EXTRA_TEXT, text);//
        startActivity(Intent.createChooser(intent, text));
    }

    public void sendAppMsg(View view) {

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        String text = strShareMessage;
        // change with required  application package

        intent.setPackage("com.whatsapp");
        intent.putExtra(Intent.EXTRA_TEXT, text);//
        startActivity(Intent.createChooser(intent, text));
    }

    private void onInviteClicked() {
        Intent intent = new AppInviteInvitation.IntentBuilder("Send mail or SMS")
                .setMessage(strShareMessage)
                .setDeepLink(Uri.parse("groom://use/" + "=" + _PhoneNo ))
                .setCustomImage(Uri.parse("http://139.59.38.160/Corona/ic_launcherweb.png"))
                .setCallToActionText("Join")
                .build();
        startActivityForResult(intent, REQUEST_INVITED);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            //checking for our ColorSelectorActivity using request code

            case REQUEST_INVITED:
                if (resultCode == RESULT_OK) {

                    String[] ids = AppInviteInvitation.getInvitationIds(resultCode, data);


                    if (ids.length < 1) {
                        Toast.makeText(getApplicationContext(), "Please invite friends to join your community", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Thank you!", Toast.LENGTH_SHORT).show();




                    }

                    break;
                }

            default:
                break;
        }

    }

    private String urlEncode(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Log.wtf(TAG, "UTF-8 should always be supported", e);
            return "";
        }
    }

    public void sendEmail(String s) {

        Intent i = new Intent(Intent.ACTION_SENDTO);
        i.setType("text/plain");
        i.setData(Uri.parse("mailto:"));
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{"info@covered1-9.com"});
        i.putExtra(Intent.EXTRA_SUBJECT, "Download DigiHealth ");
        i.putExtra(Intent.EXTRA_TEXT, s);
        try {
            startActivity(Intent.createChooser(i, "Email us.."));
        } catch (android.content.ActivityNotFoundException ex) {

            Snackbar snackbar = Snackbar
                    .make(getWindow().getDecorView().getRootView(), "There are no email clients installed.", Snackbar.LENGTH_LONG);
            View sbView = snackbar.getView();
            TextView textView = sbView.findViewById(R.id.snackbar_text);
            textView.setTextColor(Color.WHITE);
            snackbar.show();
        }

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);

        }
        return true;
    }

}
