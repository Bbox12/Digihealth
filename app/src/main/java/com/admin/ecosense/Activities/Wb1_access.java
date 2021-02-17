package com.admin.ecosense.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.admin.ecosense.Login.ServiceOffer;
import com.admin.ecosense.R;
import com.admin.ecosense.helper.PrefManager;
import com.google.android.material.appbar.AppBarLayout;

import java.util.HashMap;


public class Wb1_access extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private WebView webView;
    private Toolbar toolbar;
    private String postUrl;
    private int _from=0;
    private PrefManager pref;
    private String _links;
    private ProgressBar progressBar;
    private String _PhoneNo;
    private int video=0;
    private VideoView cameraView;
    private AppBarLayout appBar;

    @SuppressLint({"SetJavaScriptEnabled", "SourceLockedOrientationActivity"})
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pref=new PrefManager(getApplicationContext());
        HashMap<String, String> user = pref.getUserDetails();
        _PhoneNo = user.get(PrefManager.KEY_MOBILE);
        Intent i=getIntent();

        _from=i.getIntExtra("from",0);
        _links=i.getStringExtra("links");
        video=i.getIntExtra("video",0);


        setContentView(R.layout.web_access);
        toolbar =findViewById(R.id.toolbardd);
        appBar=findViewById(R.id.app_bar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        toolbar.setTitle(getResources().getString(R.string.app_name));
        progressBar =  findViewById(R.id.progressBar);
        webView=findViewById(R.id.webView);
        WebSettings webSetting = webView.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setDisplayZoomControls(true);
        cameraView =  findViewById(R.id.CameraView);

        if(_from==0){
            postUrl =pref.getLinks5();
        }else if(_from==1){
            postUrl = pref.getLinks1();
        }else if(_from==2){
            postUrl =pref.getLinks2();
        }else if(_from==3){
            postUrl =pref.getLinks3();
        }else if(_from==4){
            postUrl =pref.getLinks6();
        }else if(_from==5){
            if(video==0) {
                if (_links.contains(".pdf")) {
                    postUrl = "https://drive.google.com/viewerng/viewer?embedded=true&url=" + _links;
                } else {
                    postUrl = _links;
                }
            }else{
                appBar.setVisibility(View.GONE);
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                progressBar.setVisibility(View.GONE);
                webView.setVisibility(View.GONE);
                cameraView.setVisibility(View.VISIBLE);
                cameraView.setVideoPath(_links);
                cameraView.setMediaController(new MediaController(this));
                cameraView.requestFocus();
                final int height = getResources().getDisplayMetrics().heightPixels;
                final int width = getResources().getDisplayMetrics().widthPixels;
                cameraView.resolveAdjustedSize(width,height);
                cameraView.start();
            }
        }else if(_from==6){
            postUrl =pref.getLinks4();
        }else if(_from==7){
            postUrl ="http://139.59.38.160/Corona/lancet.pdf";
        }else if(_from==8){
            if (_links.contains(".pdf")) {
                postUrl = "https://drive.google.com/viewerng/viewer?embedded=true&url=" + _links;
            } else {
                postUrl = _links;
            }

        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);



            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_white, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_refresh1) {

            overridePendingTransition(0, 0);
            startActivity(getIntent());
        }
        if (item.getItemId() == R.id.action_notification) {
            if (_PhoneNo != null) {
                Intent o = new Intent(Wb1_access.this, Account_settings.class);
                startActivity(o);
                overridePendingTransition(R.anim.slide_up1, R.anim.slide_down1);
            } else {
                Intent o = new Intent(Wb1_access.this, ServiceOffer.class);
                startActivity(o);

                overridePendingTransition(R.anim.slide_up1, R.anim.slide_down1);
            }
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {
        super.onResume();
        webView.loadUrl(postUrl);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                progressBar.setVisibility(View.GONE);
                webView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(Wb1_access.this, "Error:Please try again.",Toast.LENGTH_SHORT ).show();

            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }

    @Override
    protected void onPause() {
        super.onPause();


    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);

    }


    @Override
    public void onRefresh() {
        if(webView!=null){
            webView.loadUrl(postUrl);
        }

    }



}
