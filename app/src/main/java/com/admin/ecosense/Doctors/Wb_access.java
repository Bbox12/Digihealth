package com.admin.ecosense.Doctors;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.admin.ecosense.R;
import com.admin.ecosense.helper.PrefManager;


public class Wb_access extends AppCompatActivity {

    private WebView webView;
    private String postUrl;
    private double My_lat,My_long;
    private String Car_type;
    private PrefManager pref;
    private String title,description,Photo;
    private int ID;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        ID = i.getIntExtra("ID",0);
        title = i.getStringExtra("title");
        description = i.getStringExtra("description");
        Photo=i.getStringExtra("Photo");
        pref = new PrefManager(getApplicationContext());
        if(postUrl==null){
            postUrl = "http://139.59.38.160/Corona/latlongFCM.php?title="+title+"&description="+description+"&Photo="+Photo+"&ID="+ID;
        }

        setContentView(R.layout.web_access_trans);

        webView=findViewById(R.id.webView);
        webView.setWebViewClient(new CustomWebViewClient());
        WebSettings webSetting = webView.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webView.loadUrl(postUrl);
    }


    private class CustomWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);

        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            //view.loadUrl(url);
            return false;

        }
        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            Toast.makeText(getApplicationContext(), "Failed loading app!, No Internet Connection found.", Toast.LENGTH_SHORT).show();
            recreate();
        }
        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            Toast.makeText(getApplicationContext(), "Notified successfully!.", Toast.LENGTH_SHORT).show();
            Intent o = new Intent(Wb_access.this, Doctorwindow.class);
            o.putExtra("from",1);
            startActivity(o);

            overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);

            super.onPageFinished(view, url);

        }
    }
    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);

    }
}
