package com.admin.ecosense.Activities;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.UiModeManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
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
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.admin.ecosense.Adapters._symsAdapter;
import com.admin.ecosense.BuildConfig;
import com.admin.ecosense.Login.ServiceOffer;
import com.admin.ecosense.R;
import com.admin.ecosense.RequestTest.RequestTest;
import com.admin.ecosense.geofence_21.MapFragment;
import com.admin.ecosense.helper.AppController;
import com.admin.ecosense.helper.Config_URL;
import com.admin.ecosense.helper.DownloadTask;
import com.admin.ecosense.helper.DrawingView;
import com.admin.ecosense.helper.Login;
import com.admin.ecosense.helper.PrefManager;
import com.admin.ecosense.helper.Qns;
import com.admin.ecosense.helper.RecyclerTouchListener;
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
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class CovidTest extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = CovidTest.class.getSimpleName();

    private static final int REQUEST_RESOLVE_ERROR = 1001;
    private static final String DIALOG_ERROR = "dialog_error";
    public static final int MULTIPLE_PERMISSIONS = 10;
    private static final int SELECT_FILE = 102;
    private boolean permissionsAllowd=false;
    private static final int REQUEST_CHECK_SETTINGS = 0x1;
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(25.00000, 91.00000), new LatLng(27.99999, 91.99999));
    private static final float POLYLINE_WIDTH = 8;
    private GoogleApiClient mGoogleApiClient;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest mLocationRequest;
    private PendingResult<LocationSettingsResult> result;
    private LocationSettingsRequest.Builder builder;
    private boolean mResolvingError = false;
    private static final int RESULT_PICK_CONTACT = 101;
    private Toolbar toolbar;
    private double My_lat = 0, My_long = 0;
    private PrefManager pref;
    private String _PhoneNo;
    private String phoneNo;
    private String name;
    private RecyclerView Sosrv,sampletype,indication;
    private ArrayList<Qns> mItems = new ArrayList<Qns>();
    private String mobileIp;
    private ProgressBar progressBar;
    public boolean success;
    private CoordinatorLayout coordinatorLayout;
    private DatabaseReference mDatabase;
    private String _familyPhone;
    private EditText _t1;
    private String _Name;
    private TextView textView101,textView102;
    EditText _nameText;
    EditText _userName;
    EditText _bday;
    EditText PatientID;
    private EditText _userBday,reason,title;
    private EditText _userGender;
    private TextView _T1;
    private Button Submit;
    private String _text;
    private EditText physicaladdress,country1,ddate1,rdate1,ddate2,rdate2,coutry2;
    private boolean _tpermission;
    private LocationCallback mLocationCallback;
    private boolean drag;
    ArrayList<String> TotalSample = new ArrayList<String>();
    ArrayList<String> TotalIndication = new ArrayList<String>();
    private AppCompatCheckBox yeshome;
    private int _sars;
    private LinearLayout _L1;
    private EditText _status,_date,_time;
    DrawingView dv ;
    private Paint mPaint;
    private boolean _sub=false;
    private String filePath_;
    private ImageView _Image1;
    private Button _download,_choose;
    private int _d=0;
    private Uri selectedImageUri;
    private String selectedVideoPath;
    private TextView _filename;
    private Button _upload,_cancel;
    private ProgressDialog mProgressDialog;
    private RequestQueue rQueue;
    private LinearLayout download,upload;
    private int _from=0;
    private String url;

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
        setContentView(R.layout.covidnote);
        coordinatorLayout=findViewById(R.id.em);
        toolbar = findViewById(R.id.toolbar_later_sos);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        _userName = findViewById(R.id.input_user_name);
        _userGender = findViewById(R.id.input_gender);
        _userBday = findViewById(R.id.input_bday);
        PatientID = findViewById(R.id.PatientID);
        Sosrv = findViewById(R.id.sosrv);
        sampletype=findViewById(R.id.sampletype);
        indication=findViewById(R.id.indication);
        download=findViewById(R.id.download);
        upload=findViewById(R.id.upload);

        _L1=findViewById(R.id._L1);
        _status=findViewById(R.id.status);
        _date=findViewById(R.id.date);
        _time=findViewById(R.id.time);

        progressBar=findViewById(R.id.progressBaremergency);


        pref = new PrefManager(getApplicationContext());
        HashMap<String, String> user = pref.getUserDetails();
        _PhoneNo = user.get(PrefManager.KEY_MOBILE);
        _Name = user.get(PrefManager.KEY_NAME);

        textView101=findViewById(R.id.textView101);
        textView102=findViewById(R.id.textView102);

        physicaladdress=findViewById(R.id.physicaladdress);
        country1=findViewById(R.id.country1);
        coutry2=findViewById(R.id.country2);
        ddate1=findViewById(R.id.ddate1);
        ddate2=findViewById(R.id.ddate2);
        rdate1=findViewById(R.id.rdate1);
        rdate2=findViewById(R.id.rdate2);

        ddate1.setOnClickListener(this);
        ddate2.setOnClickListener(this);

        rdate1.setOnClickListener(this);
        rdate2.setOnClickListener(this);

        yeshome=findViewById(R.id.yeshome);

        Intent i=getIntent();
        _text=i.getStringExtra("name");
        _from=i.getIntExtra("from",0);



        reason=findViewById(R.id.reason);
        title=findViewById(R.id.title);

        Submit=findViewById(R.id.Submit);
        Submit.setOnClickListener(this);

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
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        createLocationRequest();
        buildLocationSettingsRequest();
        rebuildGoogleApiClient();
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            onConnected(null);
        }

        _download=findViewById(R.id._download);
        _Image1=findViewById(R.id._image1);
        _Image1.setOnClickListener(this);
        _download.setOnClickListener(this);

        _choose=findViewById(R.id._upload);
        _choose.setOnClickListener(this);
        _filename=findViewById(R.id._filename);

        if(_from==1){
            textView101.setText("Sick Note");
            download.setVisibility(View.GONE);
        }else if(_from==2){
            textView101.setText("Biomarker and clotting test form");
            _filename.setText("Please upload biomarker and clotting test report ");
          //  download.setVisibility(View.GONE);
        }else if(_from==3){
            textView101.setText("Swab form");
            _filename.setText("Please upload Swab report ");
            download.setVisibility(View.VISIBLE);
        }else if(_from==4){
            textView101.setText("X-ray form");
            _filename.setText("Please upload X-ray report ");
           // download.setVisibility(View.GONE);
        }else if(_from==5){
            textView101.setText("Antibody test form");
            _filename.setText("Please upload Antibody test form ");
            // download.setVisibility(View.GONE);
        }




        _upload=findViewById(R.id.btnUpload);
        _upload.setOnClickListener(this);
        _cancel=findViewById(R.id.btnCancel);
        _cancel.setOnClickListener(this);


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
        yeshome.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    _sars = 1;
                }else{
                    _sars = 0;
                }
            }
        });
        go();
    }



    private void go() {
        mItems.clear();
     // GetUser();

    }



    public boolean openPdfDocument(File file) {
        Intent target = new Intent(Intent.ACTION_VIEW);
        target.setDataAndType(Uri.fromFile(file), "application/pdf");
        target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        try {
            startActivity(target);
            return true;
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this,"No PDF reader found",Toast.LENGTH_LONG).show();
            return false;
        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id._image1:

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://139.59.38.160/Corona/lancet.pdf"));
                startActivity(browserIntent);
                break;
            case R.id._download:
                _d=1;
                if(checkAndRequestPermissions()){
                    if(_from==2){
                        new DownloadTask(CovidTest.this,"http://139.59.38.160/Corona/BiomarkerForm.pdf");
                    }else if(_from==3){
                        new DownloadTask(CovidTest.this,"http://139.59.38.160/Corona/lancet.pdf");
                    }else if(_from==4){
                        if(!CovidTest.this.isFinishing()){

                            final CharSequence[] items = {"ASCOT PARK HOSPITAL", "ROWIN RAGHUBIR RADIOGRAPHY"};

                            AlertDialog.Builder builder = new AlertDialog.Builder(CovidTest.this);
                            builder.setTitle("Please download X-ray form from");
                            builder.setIcon(R.mipmap.ic_launcher);
                            builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int item) {
                                    if(item==0){
                                        url="http://139.59.38.160/Corona/ASCOT_PARK_HOSPITAL.pdf";
                                    }else{
                                        url="http://139.59.38.160/Corona/ROWIN_RAGHUBIR_RADIOGRAPHY.pdf";
                                    }
                                   // Toast.makeText(getApplicationContext(), String.valueOf(item), Toast.LENGTH_SHORT).show();
                                }
                            });

                            builder.setPositiveButton("Yes",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            if(url!=null){
                                                new DownloadTask(CovidTest.this,url);
                                            }else{
                                                Toast.makeText(CovidTest.this, "Please select a form", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                            builder.setNegativeButton("No",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });
                            AlertDialog alert = builder.create();
                            alert.show();
                        }

                    }else if(_from==5){
                        new DownloadTask(CovidTest.this,"http://139.59.38.160/Corona/L2909_SARS-CoV-2_AntibodyTest.pdf");
                    }


                }



                break;

            case R.id._upload:
                if(checkAndRequestPermissions()){
                    galleryIntent();
                }
                break;
            case R.id.btnCancel:
               _choose.setVisibility(View.VISIBLE);
                _upload.setVisibility(View.GONE);
                _cancel.setVisibility(View.GONE);
                _filename.setText("Choose the Covid test file to upload");

                break;
            case R.id.btnUpload:
                uploadPDF(selectedVideoPath, selectedImageUri);
                break;


            case R.id.ddate1:
                try {
                    open_date(ddate1);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                break;
            case R.id.ddate2:
                try {
                    open_date(ddate2);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                break;
            case R.id.rdate1:
                try {
                    open_date(rdate1);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                break;
            case R.id.rdate2:
                try {
                    open_date(rdate2);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                break;
            case R.id.Submit:
                   if(TotalIndication.size()!=0 && TotalSample.size()!=0){
                       try {
                           signature();
                       } catch (ParseException e) {
                           e.printStackTrace();
                       }
                   }
                break;

        }

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
                _filename.setText(selectedVideoPath);
                _upload.setVisibility(View.VISIBLE);
                _cancel.setVisibility(View.VISIBLE);
                _choose.setVisibility(View.GONE);
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


    private void uploadPDF(final String pdfname, Uri pdffile){
        mProgressDialog = new ProgressDialog(CovidTest.this, R.style.Theme_AppCompat_Light_Dialog_MinWidth);
        mProgressDialog.setIcon(R.mipmap.ic_launcher);
        mProgressDialog.setTitle("Uploading");
        mProgressDialog.setMessage("please wait...");
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
        InputStream iStream = null;
        try {

            iStream = getContentResolver().openInputStream(pdffile);
            final byte[] inputData = getBytes(iStream);

            VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(com.android.volley.Request.Method.POST, Config_URL.FILE_UPLOAD_URL,
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
                                if (success) {
                                    pref.setClockIn(1);
                                    ViewDialogFailedSuccess alert = new ViewDialogFailedSuccess();
                                    alert.showDialog(CovidTest.this, "Succesfully uploaded.",false);
                                } else {
                                    ViewDialogFailed alert = new ViewDialogFailed();
                                    alert.showDialog(CovidTest.this, "Failed to store file!", true);

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
                    params.put("address",physicaladdress.getText().toString());
                    params.put("from", String.valueOf(_from));
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
            rQueue = Volley.newRequestQueue(CovidTest.this);
            rQueue.add(volleyMultipartRequest);

        } catch (IOException e) {
            e.printStackTrace();
            if(mProgressDialog!=null){
                mProgressDialog.dismiss();
            }
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

    public void view(View v)
    {
        File pdfFile = new File(Environment.getExternalStorageDirectory() + "/euphileni/" + "Covidtest.pdf");  // -> filename = maven.pdf
        Uri path = Uri.fromFile(pdfFile);
        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
        pdfIntent.setDataAndType(path, "application/pdf");
        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        try{
            startActivity(pdfIntent);
        }catch(ActivityNotFoundException e){
            Toast.makeText(CovidTest.this, "No Application available to view PDF", Toast.LENGTH_SHORT).show();
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
                        Intent test = new Intent(CovidTest.this, RequestTest.class);
                        startActivity(test);
                        overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);


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

    private void signature() throws ParseException {
        final Dialog dialog1 = new Dialog(CovidTest.this, R.style.Theme_AppCompat_DayNight_DialogWhenLarge);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setCancelable(true);
        dialog1.setContentView(R.layout.dailog_signature);

        Button ok=dialog1.findViewById(R.id.btn_dialog);
        ImageView ivDrawImg = dialog1.findViewById(R.id.sigimage);

        final Bitmap image = Bitmap.createBitmap(640, 480, Bitmap.Config.RGB_565);
        ivDrawImg.draw(new Canvas(image));
        String uri = MediaStore.Images.Media.insertImage(getContentResolver(), image, "title", null);

        final AppCompatCheckBox aggree=dialog1.findViewById(R.id.aggree);
        aggree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    _sub=isChecked;

            }
        });
        ok.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                if(_sub) {
                    try {
                        File folder = new File(Environment.getExternalStorageDirectory() + "/DrawTextOnImg");

                        if (!folder.exists()) {
                            folder.mkdir();
                            //folder.mkdirs();  //For creating multiple directories
                        }
                        File file = new File(Environment.getExternalStorageDirectory()+"/Digidoc/tempImg.png");
                        FileOutputStream stream = new FileOutputStream(file);
                        image.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        final File mediaStorageDir = new File(
                                Environment.getExternalStorageDirectory().getAbsolutePath() + "/");
                        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                                Locale.getDefault()).format(new Date());
                        File destination = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
                        FileOutputStream fo;
                        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                        try {
                            destination.createNewFile();
                            fo = new FileOutputStream(destination);
                            fo.write(bytes.toByteArray());
                            fo.close();
                            filePath_ = destination.getPath();

                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        new PostDataTOServer().execute();
                    } catch (Exception e) {
                        Toast.makeText(CovidTest.this, "Save failed", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }

                    dialog1.cancel();
                }else{
                    Toast.makeText(getApplicationContext(),"please click the checkbox to submit data",Toast.LENGTH_SHORT).show();
                    aggree.requestFocus();
                }

            }
        });

        dialog1.show();

    }

    private class PostDataTOServer extends AsyncTask<Void, Integer, String> {


        private boolean success;
        private File destination;
        private String sample;
        private String indications;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            if (TotalSample.size() > 1) {
                sample = TextUtils.join(",", TotalSample);
            } else {
                sample = String.valueOf(TotalSample.get(0));
            }
            if (TotalIndication.size() > 1) {
                indications = TextUtils.join(",", TotalIndication);
            } else {
                indications = String.valueOf(TotalIndication.get(0));
            }
            progressBar.setVisibility(View.VISIBLE);
        }

        protected String doInBackground(Void... args) {
            return uploadFile();
        }

        private String uploadFile() {
            // TODO Auto-generated method stub
            String res = null;
            final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/*");
            //String filename = filePath_.substring(filePath_.lastIndexOf("/") + 1);

            try {
                File sourceFile = new File(filePath_);
                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("mobile", _PhoneNo)
                        .addFormDataPart("address", physicaladdress.getText().toString())
                        .addFormDataPart("sars", String.valueOf(_sars))
                        .addFormDataPart("sample", sample)
                        .addFormDataPart("indication", indications)
                        .addFormDataPart("country1", country1.getText().toString())
                        .addFormDataPart("ddate1", ddate1.getText().toString())
                        .addFormDataPart("rdate1", rdate1.getText().toString())
                        .addFormDataPart("country2", coutry2.getText().toString())
                        .addFormDataPart("ddate2", ddate2.getText().toString())
                        .addFormDataPart("rdate2", rdate2.getText().toString())
                        .addFormDataPart("image", sourceFile.getName(), RequestBody.create(MEDIA_TYPE_PNG, sourceFile))

                        .build();

                Request request = new Request.Builder()
                        .url(Config_URL.SAVED_COVID)
                        .post(requestBody)
                        .build();
                OkHttpClient client = new OkHttpClient();
                okhttp3.Response response = client.newCall(request).execute();
                res = response.body().string();
                String[] pars = res.split("error");
                if (pars[1].contains("false")) {
                    success = true;

                } else {
                    success = false;
                }
                Log.e("TAG", "Response : " + res);

                return res;

            } catch (UnknownHostException | UnsupportedEncodingException e) {
                Log.e("TAG", "Error: " + e.getLocalizedMessage());
                success=false;
            } catch (Exception e) {
                Log.e("TAG", "Other Error: " + e.getLocalizedMessage());
                success=false;
            }


            return res;

        }

        protected void onPostExecute(String file_url) {
            progressBar.setVisibility(View.GONE);
            if (success) {
                if (destination != null) {
                    File file = new File(destination.getPath());
                    file.delete();
                    if (file.exists()) {
                        try {
                            file.getCanonicalFile().delete();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (file.exists()) {
                            getApplicationContext().deleteFile(file.getName());
                        }
                    }
                }

                    ViewDialogFailedSuccess alert = new ViewDialogFailedSuccess();
                    alert.showDialog(CovidTest.this, "Success! Your Covid test request generated.", false);
                }else {
                    ViewDialogFailed alert = new ViewDialogFailed();
                    alert.showDialog(CovidTest.this, "Failed to generate request!", true);
                }

        }


    }

    private void open_date(final EditText _date) throws ParseException {
        int year;
        int months;
        int day;
        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat("yyy-MM-dd");
        final Dialog dialog1 = new Dialog(CovidTest.this, R.style.ThemeTransparent);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setCancelable(true);
        dialog1.setContentView(R.layout.dailog_date);

        DatePicker date_=dialog1.findViewById(R.id.datePickerExample);
        Button ok=dialog1.findViewById(R.id.button2);
        Calendar currCalendar = Calendar.getInstance();

        // Set the timezone which you want to display time.
        currCalendar.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));

        year = currCalendar.get(Calendar.YEAR);
        months = currCalendar.get(Calendar.MONTH)+1;
        day = currCalendar.get(Calendar.DAY_OF_MONTH-1);


        currCalendar.add(Calendar.HOUR,3);
        Date date=simpleDateFormat.parse(simpleDateFormat.format(currCalendar.getTime()));
        date_.setMaxDate(date.getTime());
        currCalendar.setTime(date);
        date_.init(year , months , day , new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int day) {
                if(month<10){
                    if(day<10){
                        _date.setText(year+"-"+"0"+(month+1)+"-"+"0"+day);
                    }else{
                        _date.setText(year+"-"+"0"+(month+1)+"-"+day);
                    }
                }else{
                    if(day<10){
                        _date.setText(year+"-"+(month+1)+"-"+"0"+day);
                    }else{
                        _date.setText(year+"-"+(month+1)+"-"+day);
                    }
                }

            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.cancel();
            }
        });

        dialog1.show();

    }

    public void GetUser(){
         final ArrayList<Login> mItems = new ArrayList<Login>();
         final ArrayList<Login>mSample=new ArrayList<>();
         final ArrayList<Login>mIndi=new ArrayList<>();
         final ArrayList<Login>mSyms=new ArrayList<>();

        StringRequest eventoReq = new StringRequest(com.android.volley.Request.Method.POST, Config_URL.GET_LOGIN,
                new Response.Listener<String>() {
                    private String _Indication;
                    private String _Sampletype;

                    @Override
                    public void onResponse(String response) {
                        Log.w("kk", response.toString());
                        try {

                            JSONObject jsonObj = new JSONObject(response);
                            JSONArray _Attendence = jsonObj.getJSONArray("login");
                            for (int i = 0; i < _Attendence.length(); i++) {
                                JSONObject c = _Attendence.getJSONObject(i);
                                if (!c.isNull("PhoneNo")) {
                                    Login item = new Login();
                                    item.setName(c.getString("Name"));
                                    item.set_Phone_no(c.getString("PhoneNo"));
                                    item.setEmail(c.getString("Email"));
                                    item.setGender(c.getString("Gender"));
                                    item.setBday(c.getString("Date_of_Birth"));
                                    item.setPatientID(c.getString("IDPatient"));
                                    mItems.add(item);
                                }

                            }

                            JSONArray sampletype = jsonObj.getJSONArray("sampletype");
                            for (int i = 0; i < sampletype.length(); i++) {
                                JSONObject c = sampletype.getJSONObject(i);
                                if (!c.isNull("Name")) {
                                    Login item = new Login();
                                    item.setID(c.getInt("ID"));
                                    item.setName(c.getString("Name"));
                                    mSample.add(item);
                                }

                            }

                            JSONArray stats = jsonObj.getJSONArray("symtoms");
                            for (int i = 0; i < stats.length(); i++) {
                                JSONObject c = stats.getJSONObject(i);
                                if (!c.isNull("ID")) {
                                    if(!c.isNull("Client")){
                                        Login item = new Login();
                                        item.setID(c.getInt("ID"));
                                        item.setName(c.getString("Name"));

                                        item.setClient(c.getInt("Client"));
                                        mSyms.add(item);
                                    }

                                }

                            }


                            JSONArray indication = jsonObj.getJSONArray("indication");
                            for (int i = 0; i < indication.length(); i++) {
                                JSONObject c = indication.getJSONObject(i);
                                if (!c.isNull("Name")) {
                                    Login item = new Login();
                                    item.setID(c.getInt("ID"));
                                    item.setName(c.getString("Name"));
                                    mIndi.add(item);
                                }

                            }

                            JSONArray covid = jsonObj.getJSONArray("covid");
                            for (int i = 0; i < covid.length(); i++) {
                                JSONObject c = covid.getJSONObject(i);
                                if (!c.isNull("ID")) {
                                    Submit.setVisibility(View.GONE);
                                    _L1.setVisibility(View.VISIBLE);
                                    if(c.getInt("Status")==1) {
                                        _status.setText("Pending");
                                    }
                                    if(c.getInt("Status")==2) {
                                        _status.setText("Accept");
                                    }
                                    if(c.getInt("Status")==3) {
                                        _status.setText("Reject");
                                    }
                                    _date.setText(c.getString("Date"));
                                    _time.setText(c.getString("Time"));
                                    physicaladdress.setText(c.getString("Address"));
                                    if(c.getInt("Sars")==1){
                                        yeshome.setChecked(true);
                                    }else{
                                        yeshome.setChecked(false);
                                    }
                                    if(!c.isNull("Sampletype") && !TextUtils.isEmpty(c.getString("Sampletype"))){
                                        _Sampletype=(c.getString("Sampletype"));
                                    }
                                    if(!c.isNull("Indication") && !TextUtils.isEmpty(c.getString("Indication"))){
                                        _Indication=(c.getString("Indication"));
                                    }
                                    if(!c.isNull("Country1") && !TextUtils.isEmpty(c.getString("Country1"))){
                                        country1.setText(c.getString("Country1"));
                                        ddate1.setText(c.getString("Ddate1"));
                                        rdate1.setText(c.getString("Rdate1"));
                                    }

                                    if(!c.isNull("Country2") && !TextUtils.isEmpty(c.getString("Country2"))){
                                        coutry2.setText(c.getString("Country2"));
                                        ddate2.setText(c.getString("Ddate2"));
                                        rdate2.setText(c.getString("Rdate2"));
                                    }
                                }

                            }



                        } catch (final JSONException e) {
                            Log.e("HI", "Json parsing error: " + e.getMessage());


                        }

                        if (mItems.size() != 0) {
                            progressBar.setVisibility(View.GONE);
                            _userName.setText(mItems.get(0).getName(0));
                            _userGender.setText(mItems.get(0).getGender(0));
                            _userBday.setText(mItems.get(0).getBday(0));
                            PatientID.setText(mItems.get(0).getPatientID(0));
                        }

                        if(mSyms.size()>0) {
                            _symsAdapter sAdapter = new _symsAdapter(CovidTest.this, mSyms);
                            sAdapter.notifyDataSetChanged();
                            Sosrv.setVisibility(View.VISIBLE);
                            Sosrv.setItemAnimator(new DefaultItemAnimator());
                            Sosrv.setAdapter(sAdapter);
                            Sosrv.setHasFixedSize(true);
                            LinearLayoutManager mHorizontal = new LinearLayoutManager(CovidTest.this, LinearLayoutManager.VERTICAL, false);
                            Sosrv.setLayoutManager(mHorizontal);

                        }

                        if(mSample.size()>0) {
                            _symsAdapter sAdapter = new _symsAdapter(CovidTest.this, mSample);
                            sAdapter.notifyDataSetChanged();
                            sAdapter.setSample(_Sampletype);
                            sampletype.setVisibility(View.VISIBLE);
                            sampletype.setItemAnimator(new DefaultItemAnimator());
                            sampletype.setAdapter(sAdapter);
                            sampletype.setHasFixedSize(true);
                            StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(getSpan(), StaggeredGridLayoutManager.VERTICAL);
                            sampletype.setLayoutManager(mLayoutManager);
                            sampletype.addOnItemTouchListener(
                                    new RecyclerTouchListener(CovidTest.this, sampletype,
                                            new RecyclerTouchListener.OnTouchActionListener() {


                                                @Override
                                                public void onClick(View view, final int position) {
                                                    Log.w("gallery", String.valueOf(position));
                                                    if(position>=0) {
                                                        if (mSample.size() != 0 && mSample.get(position).getName(position) != null) {
                                                            TotalSample.add(mSample.get(position).getName(position));
                                                        }
                                                    }
                                                }

                                                @Override
                                                public void onRightSwipe(View view, int position) {

                                                }

                                                @Override
                                                public void onLeftSwipe(View view, int position) {

                                                }
                                            }));
                        }
                        if(mIndi.size()>0) {
                            _symsAdapter sAdapter = new _symsAdapter(CovidTest.this, mIndi);
                            sAdapter.notifyDataSetChanged();
                            sAdapter.setIndication(_Indication);
                            indication.setVisibility(View.VISIBLE);
                            indication.setItemAnimator(new DefaultItemAnimator());
                            indication.setAdapter(sAdapter);
                            indication.setHasFixedSize(true);
                            StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(getSpan(), StaggeredGridLayoutManager.VERTICAL);
                            indication.setLayoutManager(mLayoutManager);
                            indication.addOnItemTouchListener(
                                    new RecyclerTouchListener(CovidTest.this, indication,
                                            new RecyclerTouchListener.OnTouchActionListener() {


                                                @Override
                                                public void onClick(View view, final int position) {
                                                    Log.w("gallery", String.valueOf(position));
                                                    if(position>=0) {
                                                        if (mIndi.size() != 0 && mIndi.get(position).getName(position) != null) {
                                                            TotalIndication.add(mIndi.get(position).getName(position));
                                                        }
                                                    }
                                                }

                                                @Override
                                                public void onRightSwipe(View view, int position) {

                                                }

                                                @Override
                                                public void onLeftSwipe(View view, int position) {

                                                }
                                            }));
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
                Map<String, String> params = new HashMap<String, String>();
                params.put("_mId", _PhoneNo);
                return params;
            }

        };

        // Añade la peticion a la cola
        AppController.getInstance().addToRequestQueue(eventoReq);

    }


    private void buildLocationSettingsRequest() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);

    }

    protected synchronized void rebuildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, 0, this)
                .addConnectionCallbacks(this /* ConnectionCallbacks */)
                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(ConnectionResult connectionResult) {
                        googleApiClientConnectionStateChange(true);
                    }
                })
                .addApi(LocationServices.API)
                .build();

    }

    private void googleApiClientConnectionStateChange(final boolean connected) {
        final Context appContext = this.getApplicationContext();



    }



    private boolean checkAndRequestPermissions() {
        int permissionLocation = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int permissionwrite = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (permissionLocation != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (permissionwrite != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[0]), MULTIPLE_PERMISSIONS);

        } else {
            permissionsAllowd=true;
        }
        return permissionsAllowd;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        Log.d(TAG, "Permission callback called-------");
        if (requestCode == MULTIPLE_PERMISSIONS) {
            Map<String, Integer> perms = new HashMap<>();
            perms.put(Manifest.permission.ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);
            perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
            if (grantResults.length > 0) {
                for (int i = 0; i < permissions.length; i++)
                    perms.put(permissions[i], grantResults[i]);
                // Check for both permissions
                if (perms.get(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                        perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                ) {
                    Log.d(TAG, "sms & location services permission granted");
                    if(_d==1){
                        _d=0;
                        permissionsAllowd=true;
                    }else {
                        startLocationUpdat();
                    }

                } else {
                    Log.d(TAG, "Some permissions are not granted ask again ");
                    permissionsAllowd = false;
                    explain("You need to give some mandatory permissions to continue");

                }
            }
        }


    }

    private void explain(String msg) {
        if(!CovidTest.this.isFinishing() && !_tpermission) {
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


    @Override
    public void onConnected(Bundle bundle) {
        builder = new LocationSettingsRequest.Builder().addLocationRequest(createLocationRequest());
        Task<LocationSettingsResponse> task = LocationServices.getSettingsClient(this).checkLocationSettings(builder.build());
        task.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {
                try {
                    LocationSettingsResponse response = task.getResult(ApiException.class);
                    // All location settings are satisfied. The client can initialize location
                    // requests here.
                    startLocationUpdat();
                } catch (ApiException exception) {
                    switch (exception.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            // Location settings are not satisfied. But could be fixed by showing the
                            // user a dialog.
                            try {
                                // Cast to a resolvable exception.
                                ResolvableApiException resolvable = (ResolvableApiException) exception;
                                // Show the dialog by calling startResolutionForResult(),
                                // and check the result in onActivityResult().
                                resolvable.startResolutionForResult(CovidTest.this, REQUEST_CHECK_SETTINGS);
                                break;
                            } catch (IntentSender.SendIntentException e) {
                                // Ignore the error.
                            } catch (ClassCastException e) {
                                // Ignore, should be an impossible error.
                                Log.w(" ClassCastException", "Canont get Address!" + e.getLocalizedMessage());
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            // Location settings are not satisfied. However, we have no way to fix the
                            // settings so we won't show the dialog.

                            break;
                    }
                }
            }
        });
    }

    @Override
    public void onConnectionSuspended(int cause) {
        // Indicate API calls to Google Play services APIs should be halted.
        googleApiClientConnectionStateChange(false);
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        if (mResolvingError) {

        } else if (result.hasResolution()) {
            try {
                mResolvingError = true;
                result.startResolutionForResult(this, REQUEST_RESOLVE_ERROR);
            } catch (IntentSender.SendIntentException e) {
                // There was an error with the resolution intent. Try again.
                mGoogleApiClient.connect();
            }
        } else {
            // Show dialog using GoogleApiAvailability.getErrorDialog()
            showErrorDialog(result.getErrorCode());
            mResolvingError = true;
        }
    }

    private void showErrorDialog(int errorCode) {
        // Create a fragment for the error dialog
        MapFragment.ErrorDialogFragment dialogFragment = new MapFragment.ErrorDialogFragment();
        // Pass the error that should be displayed
        Bundle args = new Bundle();
        args.putInt(DIALOG_ERROR, errorCode);
        dialogFragment.setArguments(args);
        dialogFragment.show(getSupportFragmentManager(), "errordialog");
    }

    public static class ErrorDialogFragment extends DialogFragment {
        public ErrorDialogFragment() {
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Get the error code and retrieve the appropriate dialog
            int errorCode = 0;
            if (this.getArguments() != null) {
                errorCode = this.getArguments().getInt(DIALOG_ERROR);
            }
            return GoogleApiAvailability.getInstance().getErrorDialog(
                    this.getActivity(), errorCode, REQUEST_RESOLVE_ERROR);
        }

        @Override
        public void onDismiss(DialogInterface dialog) {
            dialog.cancel();
        }
    }


    public void onDialogDismissed() {
        mResolvingError = false;
    }



    protected LocationRequest createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(2000);
        mLocationRequest.setFastestInterval(2000);
        mLocationRequest.setSmallestDisplacement(0.1f);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        return mLocationRequest;
    }


    private void startLocationUpdat() {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
            if (checkAndRequestPermissions()) {


                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                if (mFusedLocationClient != null) {
                    mLocationCallback=new LocationCallback() {
                        @Override
                        public void onLocationResult(LocationResult locationResult) {
                            List<Location> locationList = locationResult.getLocations();
                            if (locationList.size() > 0) {
                                Location location = locationList.get(locationList.size() - 1);
                                Log.i("MapsActivity", "Location: " + String.valueOf(location));
                                updateLocationUI(location);
                            }
                        }
                    };
                    Looper myLoop = Looper.myLooper();
                    mFusedLocationClient.requestLocationUpdates(createLocationRequest(),mLocationCallback ,
                            myLoop);

                }
            }

        }
    }


    private void updateLocationUI(Location location) {
        if(!drag) {
            drag=true;
            My_lat = location.getLatitude();
            My_long = location.getLongitude();
            getAddress(My_lat, My_long);

        }

    }







    public void getAddress(double lat, double lng) {
        Geocoder geocoder = new Geocoder(CovidTest.this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            Address obj = addresses.get(0);
            String add = obj.getCountryName();
            Log.v("IGA", "Address" + add);
            if(add!=null){
                physicaladdress.setText(obj.getAddressLine(0));
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            drag=false;
        }
    }

    public int getSpan() {
        int orientation = getScreenOrientation(getApplicationContext());
        if (isTV(getApplicationContext())) return 2;
        if (isTablet(getApplicationContext()))
            return orientation == Configuration.ORIENTATION_PORTRAIT ? 2 : 2;
        return orientation == Configuration.ORIENTATION_PORTRAIT ? 2: 2;
    }

    public static boolean isTV(Context context) {
        return ((UiModeManager) context
                .getSystemService(Context.UI_MODE_SERVICE))
                .getCurrentModeType() == Configuration.UI_MODE_TYPE_TELEVISION;
    }

    public static int getScreenOrientation(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels <
                context.getResources().getDisplayMetrics().heightPixels ?
                Configuration.ORIENTATION_PORTRAIT : Configuration.ORIENTATION_LANDSCAPE;
    }

    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_white, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_refresh1:

                overridePendingTransition(0, 0);
                startActivity(getIntent());
                break;
            case R.id.action_notification:
                if (_PhoneNo != null) {
                    Intent o = new Intent(CovidTest.this, Account_settings.class);
                    startActivity(o);
                    overridePendingTransition(R.anim.slide_up1, R.anim.slide_down1);
                } else {
                    Intent o = new Intent(CovidTest.this, ServiceOffer.class);
                    startActivity(o);

                    overridePendingTransition(R.anim.slide_up1, R.anim.slide_down1);
                }
                break;
            case R.id.action_noti:
                Intent o = new Intent(CovidTest.this, NotificationAll.class);
                startActivity(o);
                overridePendingTransition(R.anim.slide_up1, R.anim.slide_down1);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {

        if(keyCode== KeyEvent.KEYCODE_BACK)   {
            finish();
            overridePendingTransition(R.anim.slide_up1, R.anim.rbounce);

        }
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mFusedLocationClient != null && mLocationCallback!=null) {
            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
        }
    }


    @Override
    protected void onStop() {

        super.onStop();
         if (mFusedLocationClient != null && mLocationCallback!=null) {
            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mFusedLocationClient != null && mLocationCallback!=null) {
            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
        }
    }


}

