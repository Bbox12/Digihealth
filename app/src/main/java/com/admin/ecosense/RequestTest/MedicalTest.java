package com.admin.ecosense.RequestTest;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.UiModeManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.admin.ecosense.Activities.Account_settings;
import com.admin.ecosense.Activities.MainActivity;
import com.admin.ecosense.BuildConfig;
import com.admin.ecosense.Login.Medicalmodel;
import com.admin.ecosense.Login.ServiceOffer;
import com.admin.ecosense.R;
import com.admin.ecosense.helper.AppController;
import com.admin.ecosense.helper.Config_URL;
import com.admin.ecosense.helper.PrefManager;
import com.admin.ecosense.helper.Qns;
import com.admin.ecosense.helper.VolleyMultipartRequest;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class MedicalTest extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MedicalTest.class.getSimpleName();
    private static final int RESULT_PICK_CONTACT = 101;
    private static final int ALARM_REQUEST_CODE = 102;
    private static final int REQUEST_CAMERA = 1001;
    private Toolbar toolbar;
    private PrefManager pref;
    private String _PhoneNo;
    private ArrayList<Qns> mItems = new ArrayList<Qns>();
    private String mobileIp;
    public boolean success;
    private CoordinatorLayout coordinatorLayout;
    private TextView textView101,textView102;
    private int _from=0;
    private boolean _tpermission=false,permissionsAllowd=false;
    public static final int MULTIPLE_PERMISSIONS = 101;
    private static final int SELECT_FILE = 102;
    private Uri selectedImageUri;
    private String selectedVideoPath;
    private ProgressDialog mProgressDialog;
    private RequestQueue rQueue;
    private EditText ldh, crp, Ferritin, Lymphocytes, count, dimer, interleukin, PCT;
    private Button _choose,upload,cacel;
    private TextView _fileupload;


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
        } catch (Exception ex) {
        } // for now eat exceptions
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialogmedical);
        coordinatorLayout=findViewById(R.id.em);
        toolbar = findViewById(R.id.toolbar_later_sos);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ldh = findViewById(R.id.ldh);
        crp = findViewById(R.id.crp);
        Ferritin = findViewById(R.id.Ferritin);
        Lymphocytes = findViewById(R.id.Lymphocytes);
        count = findViewById(R.id.count);
        dimer = findViewById(R.id.dimer);
        interleukin = findViewById(R.id.interleukin);
        PCT = findViewById(R.id.PCT);

         _choose = findViewById(R.id._upload);
         upload = findViewById(R.id.btnUpload);
        cacel = findViewById(R.id.btnCancel);
         _fileupload = findViewById(R.id._filename);

         _choose.setOnClickListener(this);
        upload.setOnClickListener(this);
        cacel.setOnClickListener(this);


        pref = new PrefManager(getApplicationContext());
        HashMap<String, String> user = pref.getUserDetails();
        if(pref.getResponsibility()==3) {
            if(pref.getPODmobile()!=null) {
                _PhoneNo = pref.getPODmobile();
            }else{
                _PhoneNo = user.get(PrefManager.KEY_MOBILE);
            }

        }else{
            _PhoneNo = user.get(PrefManager.KEY_MOBILE);
        }

        textView101=findViewById(R.id.textView101);
        textView102=findViewById(R.id.textView102);




        textView102.setVisibility(View.GONE);

        textView101.setText("Medical Test ");
        




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

    }

    public String getWifiIPAddress() {
        WifiManager wifiMgr = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
        int ip = wifiInfo.getIpAddress();
        return Formatter.formatIpAddress(ip);
    }



    @Override
    protected void onResume() {
        super.onResume();
        go();
    }

    private void go() {
        mItems.clear();
        new GetUser().execute();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id._upload:
                checkAndRequestPermissions();
                break;

            case R.id.btnUpload:
                if (selectedImageUri != null) {
                    if (!MedicalTest.this.isFinishing()) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MedicalTest.this, R.style.AlertDialogTheme)
                                .setIcon(R.mipmap.ic_launcher)
                                .setTitle("Are you sure?")
                                .setMessage("You are sharing  medical report with DigiHealth")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        uploadPDF(selectedVideoPath, selectedImageUri, ldh.getText().toString(), crp.getText().toString()
                                                , Ferritin.getText().toString(), Lymphocytes.getText().toString(), count.getText().toString(), dimer.getText().toString()
                                                , interleukin.getText().toString(), PCT.getText().toString());
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

                } else {
                if (!TextUtils.isEmpty(ldh.getText().toString())) {
                    if (!TextUtils.isEmpty(crp.getText().toString())) {
                        if (!TextUtils.isEmpty(Ferritin.getText().toString())) {
                            if (!TextUtils.isEmpty(Lymphocytes.getText().toString())) {
                                if (!TextUtils.isEmpty(count.getText().toString())) {
                                    if (!TextUtils.isEmpty(dimer.getText().toString())) {
                                        if (!TextUtils.isEmpty(interleukin.getText().toString())) {
                                            if (!TextUtils.isEmpty(PCT.getText().toString())) {
                                                if (!MedicalTest.this.isFinishing()) {
                                                    AlertDialog.Builder builder = new AlertDialog.Builder(MedicalTest.this, R.style.AlertDialogTheme)
                                                            .setIcon(R.mipmap.ic_launcher)
                                                            .setTitle("Are you sure?")
                                                            .setMessage("You are sharing  medical report with DigiHealth")
                                                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialog, int which) {
                                                                    checkEmail(ldh.getText().toString(), crp.getText().toString()
                                                                            , Ferritin.getText().toString(), Lymphocytes.getText().toString(), count.getText().toString(), dimer.getText().toString()
                                                                            , interleukin.getText().toString(), PCT.getText().toString());
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

                                            } else {
                                                PCT.setError("Empty");
                                                PCT.requestFocus();
                                            }
                                        } else {
                                            interleukin.setError("Empty");
                                            interleukin.requestFocus();
                                        }
                                    } else {
                                        dimer.setError("Empty");
                                        dimer.requestFocus();
                                    }
                                } else {
                                    count.setError("Empty");
                                    count.requestFocus();
                                }
                            } else {
                                Ferritin.setError("Empty");
                                Ferritin.requestFocus();
                            }
                        } else {
                            Lymphocytes.setError("Empty");
                            Lymphocytes.requestFocus();
                        }
                    } else {
                        crp.setError("Empty");
                        crp.requestFocus();
                    }
                } else {
                    ldh.setError("Empty");
                    ldh.requestFocus();
                }
                }


                break;

            case R.id.btnCancel:

                overridePendingTransition(0, 0);
                startActivity(getIntent());
                break;


            default:break;
        }


    }


    private void checkAndRequestPermissions() {
        int camerapermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        int writepermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);


        List<String> listPermissionsNeeded = new ArrayList<>();

        if (camerapermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (writepermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), MULTIPLE_PERMISSIONS);

        } else {
            selectImage();
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        Log.d(TAG, "Permission callback called-------");
        switch (requestCode) {
            case MULTIPLE_PERMISSIONS: {

                Map<String, Integer> perms = new HashMap<>();
                // Initialize the map with both permissions
                perms.put(Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);


                // Fill with actual results from user
                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++)
                        perms.put(permissions[i], grantResults[i]);
                    // Check for both permissions
                    if (perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    ) {
                        Log.d(TAG, "sms & location services permission granted");
                        // process the normal flow
                        selectImage();
                    } else {
                        Log.d(TAG, "Some permissions are not granted ask again ");
                        explain("Please provide required permission");
                    }
                }
            }
            break;

        }


    }

    private void explain(String msg) {
        if(!MedicalTest.this.isFinishing() && !_tpermission) {
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

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(MedicalTest.this, R.style.AlertDialogTheme);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    cameraIntent();
                } else if (items[item].equals("Choose from Library")) {
                    galleryIntent();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }



    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("*/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select file"), SELECT_FILE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE) {
                selectedImageUri = data.getData();
                selectedVideoPath=  getFileName(selectedImageUri);
                _fileupload.setText(selectedVideoPath);

            } else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);


        }

    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.PNG, 90, bytes);
        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
            selectedImageUri = Uri.fromFile(destination);
            selectedVideoPath= destination.getPath();
            _fileupload.setText(selectedVideoPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = MedicalTest.this.getContentResolver().query(uri, null, null, null, null);
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



    private void checkEmail(final String LDH,final String CRP,final String Ferritin,
                            final String Lymphocytes,final String Count,final String Dimer,final String Interleukin,final String PCT) {
        StringRequest eventoReq = new StringRequest(com.android.volley.Request.Method.POST, Config_URL.FILE_UPLOAD_MEDICAL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.w("kk", response.toString());


                        String[] pars = response.split("error");
                        boolean success = pars[1].contains("false");
                        JSONObject jObj = null;
                        String[] par = response.split("error");
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
                        if (success) {
                            pref.setMedical(1);
                            ViewDialogFailedSuccess alert = new ViewDialogFailedSuccess();
                            alert.showDialog(MedicalTest.this, "Succesfully uploaded.",false);
                        } else {
                            ViewDialogFailed alert = new ViewDialogFailed();
                            alert.showDialog(MedicalTest.this, "Failed to store information!", true);

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
                params.put("user",_PhoneNo);
                params.put("ldh", LDH);
                params.put("crp", CRP);
                params.put("Ferritin", Ferritin);
                params.put("Lymphocytes", Lymphocytes);
                params.put("Count", Count);
                params.put("dimer", Dimer);
                params.put("interleukin", Interleukin);
                params.put("pct", PCT);
                return params;
            }

        };

        // Añade la peticion a la cola
        AppController.getInstance().addToRequestQueue(eventoReq);

    }


    private void uploadPDF(final String pdfname, Uri pdffile,final String LDH,final String CRP,final String Ferritin,
                           final String Lymphocytes,final String Count,final String Dimer,final String Interleukin,final String PCT){
        mProgressDialog = new ProgressDialog(MedicalTest.this, R.style.Theme_AppCompat_Light_Dialog_MinWidth);
        mProgressDialog.setIcon(R.mipmap.ic_launcher);
        mProgressDialog.setTitle("Uploading");
        mProgressDialog.setMessage("please wait...");
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
        InputStream iStream = null;
        try {

            iStream = MedicalTest.this.getContentResolver().openInputStream(pdffile);
            final byte[] inputData = getBytes(iStream);

            VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(com.android.volley.Request.Method.POST, Config_URL.FILE_UPLOAD_MEDICAL,
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
                                String[] pars = json.split("error");
                                boolean success = pars[1].contains("false");
                                JSONObject jObj = null;

                                String[] par = json.split("error");
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

                                if (success) {
                                    pref.setMedical(1);
                                    ViewDialogFailedSuccess alert = new ViewDialogFailedSuccess();
                                    alert.showDialog(MedicalTest.this, "Succesfully uploaded.",false);
                                } else {
                                    ViewDialogFailed alert = new ViewDialogFailed();
                                    alert.showDialog(MedicalTest.this, "Failed to store file!", true);

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
                            vollyError(error);
                        }
                    }) {




                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("user",_PhoneNo);
                    params.put("ldh", LDH);
                    params.put("crp", CRP);
                    params.put("Ferritin", Ferritin);
                    params.put("Lymphocytes", Lymphocytes);
                    params.put("Count", Count);
                    params.put("dimer", Dimer);
                    params.put("interleukin", Interleukin);
                    params.put("pct", PCT);
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
            rQueue = Volley.newRequestQueue(MedicalTest.this);
            rQueue.add(volleyMultipartRequest);

        } catch (IOException e) {
            e.printStackTrace();
            if(mProgressDialog!=null){
                mProgressDialog.dismiss();
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
                            pref.setMask(1);
                            if (!MedicalTest.this.isFinishing()) {
                                ViewDialogSuccess alert = new ViewDialogSuccess();
                                if(pref.getResponsibility()==3) {
                                    if (pref.getPODmobile() != null) {
                                        alert.showDialog(MedicalTest.this, "Based on  symptoms patient is  placed in  "+category, false);

                                    }else{
                                        alert.showDialog(MedicalTest.this, "Based on your symptoms you are now placed in  "+category, false);

                                    }
                                }else{
                                    alert.showDialog(MedicalTest.this, "Based on your symptoms you are now placed in  "+category, false);

                                }
                                //alert.showDialog(MedicalTest.this, "Based on your symptoms you are now placed in  "+category, false);
                            }

                        }

                        dialog1.dismiss();
                    }
                });

                dialog1.show();


            }
        }
    }

    public class ViewDialogSuccess {

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
                        Intent i = new Intent(MedicalTest.this, MainActivity.class);
                        startActivity(i);

                        overridePendingTransition(R.anim.slide_out2, R.anim.slide_in2);
                        dialog1.dismiss();
                    }
                });

                dialog1.show();


            }
        }
    }

    private void vollyError(VolleyError error) {
        if (error instanceof TimeoutError || error instanceof NoConnectionError) {

            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "Network is unreachable. Please try another time", Snackbar.LENGTH_SHORT)
                    ;
            snackbar.setActionTextColor(Color.RED);
            snackbar.show();

        } else if (error instanceof AuthFailureError) {

            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "Network is unreachable. Please try another time", Snackbar.LENGTH_SHORT)
                    ;
            snackbar.setActionTextColor(Color.RED);
            snackbar.show();

        } else if (error instanceof ServerError) {

            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "Server Error.Please try another time", Snackbar.LENGTH_SHORT)
                    ;
            snackbar.setActionTextColor(Color.RED);
            snackbar.show();

        } else if (error instanceof NetworkError) {

            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "Network Error. Please try another time", Snackbar.LENGTH_SHORT)
                    ;
            snackbar.setActionTextColor(Color.RED);
            snackbar.show();

        } else if (error instanceof ParseError) {

            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "Data Error. Please try another time", Snackbar.LENGTH_SHORT)
                    ;
            snackbar.setActionTextColor(Color.RED);
            snackbar.show();

        }
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


    private class GetUser extends AsyncTask<Void, Integer, String> {


        private ArrayList<Medicalmodel> mItems = new ArrayList<Medicalmodel>();
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(_PhoneNo==null){
                _PhoneNo="9999999999";
            }
        }

        protected String doInBackground(Void... args) {
            return uploadFile();
        }

        private String uploadFile() {
            // TODO Auto-generated method stub
            String res = null;

            try {


                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("_mId", _PhoneNo)
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
                    JSONArray _Attendence = jsonObj.getJSONArray("medical");
                    for (int i = 0; i < _Attendence.length(); i++) {
                        JSONObject c = _Attendence.getJSONObject(i);
                        if (!c.isNull("ID")) {
                            Medicalmodel item = new Medicalmodel();
                            item.setLBH(c.getString("LDH"));
                            item.setCRP(c.getString("CRP"));
                            item.setFerritin(c.getString("Ferritin"));
                            item.setLymphocytes(c.getString("Lymphocytes"));
                            item.setCount(c.getString("Count"));
                            item.setDDimer(c.getString("DDimer"));

                            item.setInterleukin(c.getString("Interleukin"));
                            item.setPCT(c.getString("PCT"));
                            item.setInterleukin(c.getString("Interleukin"));
                            item.setPhoto(c.getString("Photo"));
                            mItems.add(item);
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

            if(mItems.size()>0){
                ldh.setText(mItems.get(0).getLBH(0));
                crp.setText(mItems.get(0).getCRP(0));
                Ferritin.setText(mItems.get(0).getFerritin(0));
                Lymphocytes.setText(mItems.get(0).getLymphocytes(0));
                count.setText(mItems.get(0).getCount(0));
                dimer.setText(mItems.get(0).getDDimer(0));
                interleukin.setText(mItems.get(0).getInterleukin(0));
                PCT.setText(mItems.get(0).getPCT(0));
                interleukin.setText(mItems.get(0).getInterleukin(0));
            }

        }

    }

    public int getSpan() {
        int orientation = getScreenOrientation(MedicalTest.this);
        if (isTV(MedicalTest.this)) return 4;
        if (isTablet(MedicalTest.this))
            return orientation == Configuration.ORIENTATION_PORTRAIT ? 2 : 2;
        return orientation == Configuration.ORIENTATION_PORTRAIT ? 2 : 2;
    }
    public static int getScreenOrientation(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels <
                context.getResources().getDisplayMetrics().heightPixels ?
                Configuration.ORIENTATION_PORTRAIT : Configuration.ORIENTATION_LANDSCAPE;
    }

    public static boolean isTV(Context context) {
        return ((UiModeManager) context
                .getSystemService(Context.UI_MODE_SERVICE))
                .getCurrentModeType() == Configuration.UI_MODE_TYPE_TELEVISION;
    }
    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
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

