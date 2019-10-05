package com.mntsoft.hunterjeans;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mntsoft.hunterjeans.adapters.BankAdapter;
import com.mntsoft.hunterjeans.views.ApiController;
import com.mntsoft.hunterjeans.views.PojoValues;
import com.mntsoft.hunterjeans.views.SharedPreferenceData;
import com.mntsoft.hunterjeans.volley.VolleyController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static android.content.ContentValues.TAG;

/**
 * Created by root on 11/15/17.
 */
public class BankDeposit extends BaseActivity {
    String Gbranchid,GSdate,Gedate,Gbankname,Gamount,Gremarks,Guserid,Gimage;
    private ArrayList<PojoValues> sliderimageslist;
    ProgressDialog barProgressDialog;
    SharedPreferenceData sharedPreferenceData;
    private Spinner spin_bankname;
    private EditText edt_amount,edt_remarks;
    private TextView txt_date,txt_submit,txt_preview,txt_capture;
    BankAdapter stateAdapter;
    private int mYear, mMonth, mDay, i;

    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    public static final int MEDIA_TYPE_IMAGE = 1;

    // directory name to store captured images and videos
    private static final String IMAGE_DIRECTORY_NAME = "Denim Camera";

    private Uri fileUri; // file url to store image/video

    private ImageView imgPreview;
    private String encodedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_bankdeposit, frag_frame);
        sharedPreferenceData = new SharedPreferenceData(this);
        logout.setVisibility(View.INVISIBLE);
        barProgressDialog = new ProgressDialog(BankDeposit.this);
        barProgressDialog.setMessage("Loading please wait..");
        spin_bankname = (Spinner)findViewById(R.id.spin_bankname);
        txt_date = (TextView)findViewById(R.id.txt_date);
        edt_amount = (EditText)findViewById(R.id.edt_amount);
        edt_remarks = (EditText)findViewById(R.id.edt_remarks);
        txt_submit = (TextView)findViewById(R.id.txt_submit);
        txt_preview = (TextView)findViewById(R.id.txt_preview);
        txt_capture = (TextView)findViewById(R.id.txt_capture);
        imgPreview = (ImageView)findViewById(R.id.imgPreview);


        Gbranchid = getIntent().getStringExtra("BRANCHID");

        requestRuntimePermission();

        bankname("http://denimhub.mysalesinfo.co.in/services/bankDetails.htm",spin_bankname);

        txt_capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureImage();
            }
        });

        txt_preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // Checking camera availability
        if (!isDeviceSupportCamera()) {
            Toast.makeText(getApplicationContext(),
                    "Sorry! Your device doesn't support camera",
                    Toast.LENGTH_LONG).show();
            // will close the app if the device does't have camera
            finish();
        }
        spin_bankname.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                PojoValues pojoValues = (PojoValues) sliderimageslist.get(i);
                Gbankname = pojoValues.getBankName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        txt_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog dpd = new DatePickerDialog(BankDeposit.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                c.set(year, monthOfYear, dayOfMonth);
                                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                                String dateString = dateFormat.format(c.getTime());
                                c.add(Calendar.DAY_OF_YEAR, 0);
                                Date nextdate = c.getTime();
                                String nextd = dateFormat.format(nextdate);

                                txt_date.setText(dateString);

                            }
                        }, mYear, mMonth, mDay);

                dpd.show();
            }
        });



        txt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // check network and api call

                GSdate = txt_date.getText().toString().trim();
                Guserid = sharedPreferenceData.getUserId();
                Gremarks = edt_remarks.getText().toString();
                Gamount = edt_amount.getText().toString();
                Gimage = encodedImage;


                if (Gbankname.equalsIgnoreCase("") || Gbankname.equalsIgnoreCase("null") || Gbankname.equalsIgnoreCase("-Select-") && GSdate.equalsIgnoreCase("") || GSdate.equalsIgnoreCase("null") &&
                        Gamount.equalsIgnoreCase("") || Gamount.equalsIgnoreCase("null") && Gremarks.equalsIgnoreCase("") || Gremarks.equalsIgnoreCase("null") &&
                        Guserid.equalsIgnoreCase("") || Guserid.equalsIgnoreCase("null")) {
                    Toast.makeText(BankDeposit.this, "Please Enter All Fields!....", Toast.LENGTH_SHORT).show();
                } else if (Gbankname.equalsIgnoreCase("") || Gbankname.equalsIgnoreCase("null") || Gbankname.equalsIgnoreCase("-Select-")) {
                    Toast.makeText(BankDeposit.this, "Please Select Your Deposit Bank!...", Toast.LENGTH_SHORT).show();
                } else if (GSdate.equalsIgnoreCase("") || GSdate.equalsIgnoreCase("null")) {
                    Toast.makeText(BankDeposit.this, "Please Select Your Deposit Date", Toast.LENGTH_SHORT).show();
                } else if (Gamount.equalsIgnoreCase("") || Gamount.equalsIgnoreCase("null")) {
                    Toast.makeText(BankDeposit.this, "Please Enter Your Amount!....", Toast.LENGTH_SHORT).show();
                } else if (Gremarks.equalsIgnoreCase("") || Gremarks.equalsIgnoreCase("null")) {
                    Toast.makeText(BankDeposit.this, "Please Enter Your Remarks", Toast.LENGTH_SHORT).show();
                } else if (Guserid.equalsIgnoreCase("") || Guserid.equalsIgnoreCase("null")) {
                    Toast.makeText(BankDeposit.this, "Invalid UserId", Toast.LENGTH_SHORT).show();
                } else {
                    postdeposit(ApiController.BankDeposit , Gbranchid, Gbankname, GSdate, Gamount, Gremarks, Guserid, Gimage);
                }
            }
        });


    }

    /**
     * Checking device has camera hardware or not
     * */
    private boolean isDeviceSupportCamera() {
        if (getApplicationContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

    /*
	 * Capturing Camera Image will lauch camera app requrest image capture
	 */
    private void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        // start the image capture Intent
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }

    /*
	 * Here we store the file url as it will be null after returning from camera
	 * app
	 */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save file url in bundle as it will be null on scren orientation
        // changes
        outState.putParcelable("file_uri", fileUri);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // get the file url
        fileUri = savedInstanceState.getParcelable("file_uri");
    }



    /**
     * Receiving activity result method will be called after closing the camera
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // if the result is capturing Image
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // successfully captured the image
                // display it in image view
                previewCapturedImage();
            } else if (resultCode == RESULT_CANCELED) {
                // user cancelled Image capture
                Toast.makeText(getApplicationContext(),
                        "User cancelled image capture", Toast.LENGTH_SHORT)
                        .show();
            } else {
                // failed to capture image
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    /*
     * Display image from a path to ImageView
     */
    private void previewCapturedImage() {
        try {

            imgPreview.setVisibility(View.VISIBLE);

            // bimatp factory
            BitmapFactory.Options options = new BitmapFactory.Options();

            // downsizing image as it throws OutOfMemory Exception for larger
            // images
            options.inSampleSize = 8;

            final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(),
                    options);

            imgPreview.setImageBitmap(bitmap);

            //Getting the Bitmap from Gallery
            //bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);
            byte[] imageBytes = baos.toByteArray();
            encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * ------------ Helper Methods ----------------------
     * */

	/*
	 * Creating file uri to store image/video
	 */
    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    public void requestRuntimePermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
    }

    /*
     * returning image / video
     */
    private static File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else {
            return null;
        }

        return mediaFile;
    }

    protected void postdeposit(final String url,final String Gbranchid, final String Gbankname, final String GSdate, final String Gamount, final String Gremarks, final String Guserid,final String Gimage) {
        barProgressDialog.show();
        //String finalurl = url+"&bankName="+Gbankname.replace(" ","%20")+"&depositDate="+GSdate+"&amount="+Gamount+"&remarks="+Gremarks.replace(" ","%20")+"&userId="+Guserid+"&image=";
        String finalurl = url;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, finalurl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Success :Bank Deposit Details has been saved.
                        if(response.length()!=0){
                            if(response.equalsIgnoreCase("Success :Bank Deposit Details has been saved.")){
                                final Dialog d = new Dialog(BankDeposit.this, android.R.style.Theme_DeviceDefault_Dialog_MinWidth);
                                d.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                d.setCancelable(false);
                                d.setContentView(R.layout.dialog_network);
                                d.show();
                                //final EditText edt_branchid = (EditText)d.findViewById(R.id.edt_branchid);
                                TextView dia_calender = (TextView) d.findViewById(R.id.alertMessage);
                                dia_calender.setText("Success :Bank Deposit Details has been saved.");
                                Button networkCancel = (Button) d.findViewById(R.id.networkCancel);
                                networkCancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        d.dismiss();
                                        finish();
                                    }
                                });
                            }
                            if(response.equalsIgnoreCase("Error ! :Bank Deposit details has not been saved.")){
                                final Dialog d = new Dialog(BankDeposit.this, android.R.style.Theme_DeviceDefault_Dialog_MinWidth);
                                d.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                d.setCancelable(false);
                                d.setContentView(R.layout.dialog_network);
                                d.show();
                                //final EditText edt_branchid = (EditText)d.findViewById(R.id.edt_branchid);
                                TextView dia_calender = (TextView) d.findViewById(R.id.alertMessage);
                                dia_calender.setText("Error ! :Bank Deposit details has not been saved.");
                                Button networkCancel = (Button) d.findViewById(R.id.networkCancel);
                                networkCancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        d.dismiss();
                                    }
                                });
                            }
                            if(response.equalsIgnoreCase("Error ! :Sale has not done. Please check Bills.")){
                                final Dialog d = new Dialog(BankDeposit.this, android.R.style.Theme_DeviceDefault_Dialog_MinWidth);
                                d.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                d.setCancelable(false);
                                d.setContentView(R.layout.dialog_network);
                                d.show();
                                //final EditText edt_branchid = (EditText)d.findViewById(R.id.edt_branchid);
                                TextView dia_calender = (TextView) d.findViewById(R.id.alertMessage);
                                dia_calender.setText("Error ! :Sale has not done. Please check Bills.");
                                Button networkCancel = (Button) d.findViewById(R.id.networkCancel);
                                networkCancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        d.dismiss();
                                    }
                                });
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        barProgressDialog.dismiss();
                        Log.e("error", "error--->" + error);
                    }
                }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("branchId",Gbranchid);
                MyData.put("bankName",Gbankname);
                MyData.put("depositDate",GSdate);
                MyData.put("amount",Gamount);
                MyData.put("remarks",Gremarks);
                MyData.put("userId",Guserid);
                MyData.put("image",Gimage);

                Log.e("MyData","MyData--->"+MyData);
                return MyData;
            }
        };

        /*RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.setRetryPolicy(new DefaultRetryPolicy(
                60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);*/

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleyController.getInstance().addToRequestQueue(stringRequest);


    }

    private void bankname(String statesUrl, final Spinner spinner) {
        barProgressDialog.show();
        sliderimageslist = new ArrayList<PojoValues>();
        JsonArrayRequest req = new JsonArrayRequest(statesUrl,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());

                        // Parsing json response and iterate over each JSON object
                        //Log.d(TAG, "data:" + responseText);
                        try {
                            sliderimageslist = new ArrayList<PojoValues>();

                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonobject = response.getJSONObject(i);
                                String bankId = jsonobject.getString("id");
                                String bankName = jsonobject.getString("name");

                                PojoValues villageconstants = new PojoValues();
                                villageconstants.setBankid(bankId);
                                villageconstants.setBankName(bankName);
                                sliderimageslist.add(villageconstants);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(BankDeposit.this,
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                        barProgressDialog.dismiss();
                        stateAdapter = new BankAdapter(BankDeposit.this, sliderimageslist);
                        spinner.setAdapter(stateAdapter);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(BankDeposit.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }

        });
        RequestQueue requestQueue = Volley.newRequestQueue(BankDeposit.this);
        requestQueue.add(req);
    }

}
