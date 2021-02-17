package com.admin.ecosense.Doctors;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.admin.ecosense.Activities.Account_settings;
import com.admin.ecosense.Activities.MainActivity;
import com.admin.ecosense.BuildConfig;
import com.admin.ecosense.Login.ServiceOffer;
import com.admin.ecosense.R;
import com.admin.ecosense.helper.AppController;
import com.admin.ecosense.helper.Config_URL;
import com.admin.ecosense.helper.PrefManager;
import com.admin.ecosense.helper.Qns;
import com.admin.ecosense.helper.VolleyMultipartRequest;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class sendNotification extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = sendNotification.class.getSimpleName();
    private static final int RESULT_PICK_CONTACT = 101;
    private Toolbar toolbar;
    private double My_lat = 0, My_long = 0;
    private PrefManager pref;
    private String _PhoneNo;
    private String phoneNo;
    private String name;
    private RecyclerView Sosrv;
    private ArrayList<Qns> mItems = new ArrayList<Qns>();
    private String mobileIp;
    private ProgressBar progressBar;
    public boolean success;
    private CoordinatorLayout coordinatorLayout;
    private DatabaseReference mDatabase;
    private String _familyPhone;
    private EditText title,description;
    private String _Name;
    private TextView _fileupload,textView102,textView101,_texttitle;
    private Button _choose,cacel,upload;
    public static final int MULTIPLE_PERMISSIONS = 101;
    private static final int SELECT_FILE = 102;
    private Uri selectedImageUri;
    private String selectedVideoPath;
    private TextView _filename;
    private ProgressDialog mProgressDialog;
    private RequestQueue rQueue;
    private TextView _filenames;
    private Button _upload2;
    private Button _cancel;
    private boolean permissionsAllowd,_tpermission;
    private String _description,_title;
    private int _from;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notificationupload);
        coordinatorLayout=findViewById(R.id.em);
        toolbar = findViewById(R.id.toolbar_later_sos);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        progressBar=findViewById(R.id.progressBaremergency);
        progressBar.setVisibility(View.GONE);

        pref = new PrefManager(getApplicationContext());
        HashMap<String, String> user = pref.getUserDetails();
        _PhoneNo = user.get(PrefManager.KEY_MOBILE);
        _Name = user.get(PrefManager.KEY_NAME);

        textView101=findViewById(R.id.textView101);
        textView102=findViewById(R.id.textView102);

        textView101.setText("Send ");
        textView102.setText("Notification");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
            }
        });
        mobileIp = getMobileIPAddress();
        if (TextUtils.isEmpty(mobileIp)) {
            mobileIp = getWifiIPAddress();
        }
       _fileupload=findViewById(R.id._filename);
        title=findViewById(R.id.title);
         description=findViewById(R.id.description);
         _choose=findViewById(R.id._upload);
         upload=findViewById(R.id.btnUpload);
         cacel=findViewById(R.id.btnCancel);
         _texttitle=findViewById(R.id._texttitle);
         upload.setOnClickListener(this);
         cacel.setOnClickListener(this);
         _choose.setOnClickListener(this);
         Intent k=getIntent();
        _from = k.getIntExtra("from", 0);
        if(_from==1){
            _texttitle.setText("Send notification to Nightiangle patients");
        }else if(_from==2){
            _texttitle.setText("Send notification to refered patients");
        }else if(_from==3){
            _texttitle.setText("Send notification to Ekuphileni patients");
        }else if(_from==4){
            _texttitle.setText("Send notification to New patients");
        }

    }

    public String getWifiIPAddress() {
        WifiManager wifiMgr = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
        int ip = wifiInfo.getIpAddress();
        return Formatter.formatIpAddress(ip);
    }



    @Override
    public void onClick(View v) {
     switch (v.getId()){
         case R.id._upload:
             if(checkAndRequestPermissions()){
                 galleryIntent();

             }else{
                 checkAndRequestPermissions();
             }
             break;
         case R.id.btnCancel:
             Intent o = new Intent(sendNotification.this, Doctorwindow.class);
             o.putExtra("from",1);
             startActivity(o);

             overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);
             break;
         case R.id.btnUpload:
             if(!TextUtils.isEmpty(title.getText().toString())){
                 if(!TextUtils.isEmpty(description.getText().toString())){
                     if(selectedImageUri!=null) {
                         uploadPDF(_from,selectedVideoPath,selectedImageUri,title.getText().toString(),description.getText().toString());

                     }else {
                         checkEmail(_from,title.getText().toString(),description.getText().toString());
                     }
                 }else{
                     description.setError("Empty");
                 }
             }else{
                 title.setError("Empty");
             }
             break;

     }
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    private void checkEmail(final int i, final String title, final String description){
        _title=title;
        _description=description;
        mProgressDialog = new ProgressDialog(sendNotification.this, R.style.Theme_AppCompat_Light_Dialog_MinWidth);
        mProgressDialog.setIcon(R.mipmap.ic_launcher);
        mProgressDialog.setTitle("Uploading");
        mProgressDialog.setMessage("please wait...");
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

        mobileIp = getMobileIPAddress();
        if (TextUtils.isEmpty(mobileIp)) {
            mobileIp = getWifiIPAddress();
        }
        StringRequest eventoReq = new StringRequest(com.android.volley.Request.Method.POST, Config_URL.FILE_UPLOAD_URL_NOTIFICATION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.w("kk", response.toString());
                        if(mProgressDialog!=null){
                            mProgressDialog.dismiss();
                        }

                        String[] par = response.split("error");
                        boolean success = par[1].contains("false");
                        if (success) {

                              ViewDialogFailedSuccess alert = new ViewDialogFailedSuccess();
                              alert.showDialog(sendNotification.this, "Succesfully uploaded.",false);

                        } else {
                            ViewDialogFailed alert = new ViewDialogFailed();
                            alert.showDialog(sendNotification.this, "Failed to store notify!", true);

                        }

                    }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ViewDialogFailed alert = new ViewDialogFailed();
                alert.showDialog(sendNotification.this, "Failed to store notify!", true);
                if(mProgressDialog!=null){
                    mProgressDialog.dismiss();
                }

            }

        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("user",_PhoneNo);
                params.put("title",title);
                params.put("description",description);
                params.put("role", String.valueOf(pref.getResponsibility()));
                params.put("ID", String.valueOf(i));
                params.put("IP",mobileIp);
                return params;
            }

        };
        AppController.getInstance().addToRequestQueue(eventoReq);

    }

    private void uploadPDF(final int i, final String pdfname, Uri pdffile, final String title, final String description){
        _title=title;
        _description=description;
        mProgressDialog = new ProgressDialog(sendNotification.this, R.style.Theme_AppCompat_Light_Dialog_MinWidth);
        mProgressDialog.setIcon(R.mipmap.ic_launcher);
        mProgressDialog.setTitle("Uploading");
        mProgressDialog.setMessage("please wait...");
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

        mobileIp = getMobileIPAddress();
        if (TextUtils.isEmpty(mobileIp)) {
            mobileIp = getWifiIPAddress();
        }

        InputStream iStream = null;
        try {

            iStream = getContentResolver().openInputStream(pdffile);
            final byte[] inputData = getBytes(iStream);

            VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(com.android.volley.Request.Method.POST, Config_URL.FILE_UPLOAD_URL_NOTIFICATION,
                    new Response.Listener<NetworkResponse>() {


                        @Override
                        public void onResponse(NetworkResponse response) {
                            Log.e("ressssssoo",new String(response.data));
                            rQueue.getCache().clear();
                            String json = null;
                            try {
                                if(mProgressDialog!=null){
                                    mProgressDialog.dismiss();
                                }
                                json = new String(
                                        response.data,
                                        HttpHeaderParser.parseCharset(response.headers));

                                String Photo;
                                String[] par = json.split("error");
                                boolean success = par[1].contains("false");
                                if (success) {
                                    String[] pars_ = par[1].split("false,");
                                    JSONObject jObj = null;
                                    try {
                                        jObj = new JSONObject("{".concat(pars_[1]));
                                        JSONObject user = jObj.getJSONObject("user");
                                        Photo=user.getString("Photo");
                                        Intent i = new Intent(sendNotification.this, Wb_access.class);
                                        i.putExtra("ID", _from);
                                        i.putExtra("title", title);
                                        i.putExtra("description",description);
                                        i.putExtra("file", Photo);
                                        startActivity(i);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }


                                    //   ViewDialogFailedSuccess alert = new ViewDialogFailedSuccess();
                                    //  alert.showDialog(sendNotification.this, "Succesfully uploaded.",false);

                                } else {
                                    ViewDialogFailed alert = new ViewDialogFailed();
                                    alert.showDialog(sendNotification.this, "Failed to store notify!", true);

                                }

                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                                if(mProgressDialog!=null){
                                    mProgressDialog.dismiss();
                                }
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if(mProgressDialog!=null){
                                mProgressDialog.dismiss();
                            }
                            ViewDialogFailed alert = new ViewDialogFailed();
                            alert.showDialog(sendNotification.this, "Failed to store notify!", true);
                        }
                    }) {




                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("user",_PhoneNo);
                    params.put("title",title);
                    params.put("description",description);
                    params.put("role", String.valueOf(pref.getResponsibility()));
                    params.put("ID", String.valueOf(i));
                    params.put("IP",mobileIp);
                    return params;
                }

                /*
                 *pass files using below method
                 * */
                @Override
                protected Map<String, DataPart> getByteData() {
                    Map<String, DataPart> params = new HashMap<>();
                    params.put("image", new DataPart(pdfname ,inputData));
                    return params;
                }
            };
            volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(
                    0,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            rQueue = Volley.newRequestQueue(sendNotification.this);
            rQueue.add(volleyMultipartRequest);

        } catch (IOException e) {
            e.printStackTrace();
            if(mProgressDialog!=null){
                mProgressDialog.dismiss();
            }
        }


    }

    public static String getMobileIPAddress() {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        return addr.getHostAddress();
                    }
                }
            }
        } catch (Exception ignored) {
        } // for now eat exceptions
        return null;
    }
    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
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
                        Intent k = new Intent(sendNotification.this, Wb_access.class);
                        k.putExtra("ID", _from);
                        k.putExtra("title", _title);
                        k.putExtra("description",_description);
                        k.putExtra("file", "");
                        startActivity(k);
                        dialog1.dismiss();
                    }
                });

                dialog1.show();


            }
        }
    }


    private boolean checkAndRequestPermissions() {
        int permissionwrite = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        List<String> listPermissionsNeeded = new ArrayList<>();

        if (permissionwrite != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (listPermissionsNeeded.size()>0) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[0]), MULTIPLE_PERMISSIONS);

        } else {
            permissionsAllowd = true;
            //galleryIntent();
        }
        return permissionsAllowd;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        Log.d(TAG, "Permission callback called-------");
        if (requestCode == MULTIPLE_PERMISSIONS) {
            Map<String, Integer> perms = new HashMap<>();
            perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
            if (grantResults.length > 0) {
                for (int i = 0; i < permissions.length; i++)
                    perms.put(permissions[i], grantResults[i]);
                // Check for both permissions
                if (
                        perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                ) {
                    Log.d(TAG, "sms & location services permission granted");
                    galleryIntent();

                } else {
                    Log.d(TAG, "Some permissions are not granted ask again ");
                    permissionsAllowd = false;
                    explain("You need to give some mandatory permissions to continue");

                }
            }
        }


    }

    private void explain(String msg) {
        if(!sendNotification.this.isFinishing() && !_tpermission) {
            _tpermission=true;
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, msg, Snackbar.LENGTH_INDEFINITE)
                    .setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + BuildConfig.APPLICATION_ID)));
                        }
                    });
            snackbar.setActionTextColor(Color.RED);
            snackbar.show(); }



    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE) {
                selectedImageUri = data.getData();
                selectedVideoPath=  getFileName(selectedImageUri);
                _fileupload.setText(selectedVideoPath);
            }


        }

    }



    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
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
            if(_PhoneNo!=null) {
                Intent o = new Intent(sendNotification.this, Account_settings.class);
                startActivity(o);
                overridePendingTransition(R.anim.slide_up1, R.anim.slide_down1);
            }else {
                Intent o = new Intent(sendNotification.this, ServiceOffer.class);
                startActivity(o);

                overridePendingTransition(R.anim.slide_up1, R.anim.slide_down1);
            }
        }
        return super.onOptionsItemSelected(item);
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

