package com.admin.ecosense.Login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.admin.ecosense.R;
import com.admin.ecosense.helper.PrefManager;
import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;

public class ServiceOffer extends AppCompatActivity implements View.OnClickListener {

    private PrefManager pref;
    private String _phoneNo;
    private CoordinatorLayout coordinatorLayout;
    private RelativeLayout afterAnimationView;
    private Button bookservice,offerservice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_offer);
        pref = new PrefManager(getApplicationContext());
        HashMap<String, String> user = pref.getUserDetails();
        _phoneNo = user.get(PrefManager.KEY_MOBILE);
        coordinatorLayout = findViewById(R.id
                .cor_home_main);
        offerservice=findViewById(R.id.offerservice);
        bookservice=findViewById(R.id.bookservice);
        offerservice.setOnClickListener(this);
        bookservice.setOnClickListener(this);



    }


    @Override
    public void onClick(View view) {
     switch (view.getId()){
         case R.id.offerservice:
                 Intent o = new Intent(ServiceOffer.this, SignIn.class);
                 startActivity(o);
                 overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);


             break;
         case R.id.bookservice:
             Intent ou = new Intent(ServiceOffer.this, SignUp.class);
             startActivity(ou);
             overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);

             break;

             default:
                 break;
     }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        //replaces the default 'Back' button action
        if(keyCode== KeyEvent.KEYCODE_BACK)   {
            Snackbar snackbar1 = Snackbar
                    .make(coordinatorLayout, "Are you Sure to exit?", Snackbar.LENGTH_LONG)
                    .setAction("Exit", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            finish();
                            overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);

                        }
                    });
            snackbar1.setActionTextColor(Color.RED);
            snackbar1.show();
        }
        return true;
    }

}
