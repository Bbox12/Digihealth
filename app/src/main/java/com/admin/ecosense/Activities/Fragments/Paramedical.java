package com.admin.ecosense.Activities.Fragments;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.admin.ecosense.Activities.HeadingGeneralInfo;
import com.admin.ecosense.Adapters.generallnfoRv;
import com.admin.ecosense.BuildConfig;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
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

import static android.content.Context.WIFI_SERVICE;

public class Paramedical extends Fragment {

    private static final String TAG = HeadingGeneralInfo.class.getSimpleName();
    private PrefManager pref;
    private String _PhoneNo;
    private View _name1;
    private RecyclerView Sosrv;
    private TextView _t1;
    private FloatingActionButton fab;
    private ProgressBar progressBar;
    private CoordinatorLayout coordinatorLayout;
    private boolean _tpermission=false,permissionsAllowd=false;
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
    private String mobileIp;

    public Paramedical() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View V = inflater.inflate(R.layout.clinical, container, false);
        pref = new PrefManager(getActivity());
        HashMap<String, String> user = pref.getUserDetails();
        _PhoneNo = user.get(PrefManager.KEY_MOBILE);
        Sosrv = V.findViewById(R.id.sosrv);
        _t1=V.findViewById(R.id._t1);
        fab = (FloatingActionButton) V.findViewById(R.id.fab);
        progressBar=V.findViewById(R.id.progressBaremergency);
        coordinatorLayout=V.findViewById(R.id.coordinatorLayout);
        mobileIp = getMobileIPAddress();
        if (TextUtils.isEmpty(mobileIp)) {
            mobileIp = getWifiIPAddress();
        }
        return V;
    }


    @Override
    public void onResume() {
        super.onResume();
        if(_PhoneNo==null){
            _PhoneNo="9999999999";
        }
        if(pref.getResponsibility()==2) {
            fab.setVisibility(View.VISIBLE);
        }
        if(pref.getResponsibility()==1) {
            fab.setVisibility(View.GONE);
        }

        go();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!getActivity().isFinishing()){
                    final Dialog dialog = new Dialog(getActivity(),R.style.Theme_AppCompat_DayNight_DialogWhenLarge);

                    dialog.setCancelable(true);
                    dialog.setCanceledOnTouchOutside(true);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.dialogupload);
                    final TextView _fileupload=dialog.findViewById(R.id._filename);
                    final EditText title=dialog.findViewById(R.id.title);
                    final EditText description=dialog.findViewById(R.id.description);
                    final Button _choose=dialog.findViewById(R.id._upload);
                    final Button upload=dialog.findViewById(R.id.btnUpload);
                    final Button cacel=dialog.findViewById(R.id.btnCancel);

                    _filenames=_fileupload;
                    _upload2=upload;
                    _cancel=cacel;

                    _choose.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(checkAndRequestPermissions()){
                                galleryIntent();

                            }else{
                                checkAndRequestPermissions();
                            }
                        }
                    });

                    cacel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.cancel();
                        }
                    });
                    upload.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(!TextUtils.isEmpty(title.getText().toString())){
                                if(!TextUtils.isEmpty(description.getText().toString())){
                                    if(selectedImageUri!=null) {
                                        uploadPDF(selectedVideoPath,selectedImageUri,title.getText().toString(),description.getText().toString());
                                    }else {
                                        checkEmail(title.getText().toString(),description.getText().toString());
                                    }

                                }else{
                                    description.setError("Empty");
                                }
                            }else{
                                title.setError("Empty");
                            }
                            dialog.cancel();
                        }
                    });
                    dialog.show();

                }
            }
        });
    }

    private void checkEmail(final String title,final String description) {
        StringRequest eventoReq = new StringRequest(com.android.volley.Request.Method.POST, Config_URL.FILE_UPLOAD_GENERAL_INFO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.w("kk", response.toString());


                        String[] par = response.split("error");
                        boolean success = par[1].contains("false");
                        if (success) {
                            ViewDialogFailedSuccess alert = new ViewDialogFailedSuccess();
                            alert.showDialog(getActivity(), "Succesfully uploaded.",false);
                        } else {
                            ViewDialogFailed alert = new ViewDialogFailed();
                            alert.showDialog(getActivity(), "Failed to store file!", true);

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
                params.put("heading", String.valueOf(5));
                params.put("title",title);
                params.put("description",description);
                params.put("role", String.valueOf(pref.getResponsibility()));
                params.put("IP",mobileIp);
                return params;
            }

        };

        // Añade la peticion a la cola
        AppController.getInstance().addToRequestQueue(eventoReq);

    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }



    private void go() {
        final ArrayList<Qns> mItems = new ArrayList<Qns>();
        progressBar.setVisibility(View.VISIBLE);
        StringRequest eventoReq = new StringRequest(com.android.volley.Request.Method.POST, Config_URL.GET_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.w("ccc", response.toString());
                        progressBar.setVisibility(View.GONE);
                        try{
                            JSONObject jsonObj = new JSONObject(response);

                            // Getting JSON Array node

                            JSONArray Contacts = jsonObj.getJSONArray("heading");
                            if(Contacts.length()>0){
                                for (int j = 0; j < Contacts.length(); j++) {

                                    JSONObject c = Contacts.getJSONObject(j);
                                    if (c.getInt("IDHeading") == 5) {
                                        Qns item = new Qns();
                                        item.setID(c.getInt("ID"));
                                        item.setCategory(c.getString("Title"));
                                        item.setQuestion(c.getString("Description"));
                                        item.setPhoto(c.getString("Photo"));
                                        item.setIsVideo(c.getInt("isVideo"));
                                        item.setDate(c.getString("Date"));
                                        item.setTime(c.getString("Time"));
                                        mItems.add(item);
                                    }
                                }



                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (mItems.size() != 0) {
                            Sosrv.setVisibility(View.VISIBLE);
                            _t1.setVisibility(View.GONE);
                            generallnfoRv sAdapter = new generallnfoRv(getActivity(), mItems);
                            sAdapter.notifyDataSetChanged();
                            Sosrv.setAdapter(sAdapter);
                            LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                            mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                            Sosrv.setLayoutManager(mLayoutManager);

                        } else {
                            Sosrv.setVisibility(View.GONE);
                            _t1.setVisibility(View.VISIBLE);
                        }

                    }



                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                vollyError(error);
            }

        }){
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



    private boolean checkAndRequestPermissions() {
        int permissionwrite = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        List<String> listPermissionsNeeded = new ArrayList<>();

        if (permissionwrite != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (listPermissionsNeeded.size()>0) {
            ActivityCompat.requestPermissions(getActivity(), listPermissionsNeeded.toArray(new String[0]), MULTIPLE_PERMISSIONS);

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
                    permissionsAllowd = true;

                } else {
                    Log.d(TAG, "Some permissions are not granted ask again ");
                    permissionsAllowd = false;
                    explain("You need to give some mandatory permissions to continue");

                }
            }
        }


    }

    private void explain(String msg) {
        if(!getActivity().isFinishing() && !_tpermission) {
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
                _filenames.setText(selectedVideoPath);
                _upload2.setVisibility(View.VISIBLE);
                _cancel.setVisibility(View.VISIBLE);

            }


        }

    }

    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
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


    private void uploadPDF(final String pdfname, Uri pdffile, final String title, final String description){
        mProgressDialog = new ProgressDialog(getActivity(), R.style.Theme_AppCompat_Light_Dialog_MinWidth);
        mProgressDialog.setIcon(R.mipmap.ic_launcher);
        mProgressDialog.setTitle("Uploading");
        mProgressDialog.setMessage("please wait...");
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
        InputStream iStream = null;
        try {

            iStream = getActivity().getContentResolver().openInputStream(pdffile);
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
                                    ViewDialogFailedSuccess alert = new ViewDialogFailedSuccess();
                                    alert.showDialog(getActivity(), "Succesfully uploaded.",false);
                                } else {
                                    ViewDialogFailed alert = new ViewDialogFailed();
                                    alert.showDialog(getActivity(), "Failed to store file!", true);

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
                    params.put("heading", String.valueOf(5));
                    params.put("title",title);
                    params.put("description",description);
                    params.put("role", String.valueOf(pref.getResponsibility()));
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
            rQueue = Volley.newRequestQueue(getActivity());
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




    public String getWifiIPAddress() {
        WifiManager wifiMgr = (WifiManager) getActivity().getApplicationContext().getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
        int ip = wifiInfo.getIpAddress();
        return Formatter.formatIpAddress(ip);
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
                        go();
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

}
