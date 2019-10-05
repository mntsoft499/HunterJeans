package com.mntsoft.hunterjeans;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.mntsoft.hunterjeans.helper.HttpCaller;
import com.mntsoft.hunterjeans.model.SalesReportResponse;
import com.mntsoft.hunterjeans.utils.AppConstants;
import com.mntsoft.hunterjeans.utils.StringUtils;
import com.mntsoft.hunterjeans.utils.Util;
import com.mntsoft.hunterjeans.views.SharedPreferenceData;
import com.mntsoft.hunterjeans.volley.VolleyController;
import com.mntsoft.hunterjeans.volley.volleyCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import static android.content.ContentValues.TAG;

/**
 * Created by root on 11/15/17.
 */
public class SalesReport extends BaseActivity {
    private ArrayList<SalesReportResponse> sliderimageslist;
    private ListView packageslistview;
    private TextView totalbills;
    ProgressDialog barProgressDialog;
    private SharedPreferenceData preferenceData;
    private Gson gson;
    private TextView txt_branch;
    private TextView txt_totalsales;
    private TextView txt_totaldiscount;
    private TextView txt_total;
    private EditText startDate,endDate;
    private String branchid,selectedBranchName,startDateValue,endDateValue;
    private TextView submit;
    private AppCompatAutoCompleteTextView selectBranch;
    private HttpCaller mHttpCaller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_salesreport, frag_frame);
        preferenceData = new SharedPreferenceData(this);

        String dta = preferenceData.getKeyApprovalProfileId();
        logout.setVisibility(View.INVISIBLE);
        barProgressDialog = new ProgressDialog(SalesReport.this);
        barProgressDialog.setMessage("Loading Reports please wait..");
        packageslistview = (ListView) findViewById(R.id.packageslistview);
        //totalbills = (TextView)findViewById(R.id.totalbills);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        txt_branch = (TextView) findViewById(R.id.txt_branch);
        txt_totalsales = (TextView) findViewById(R.id.txt_totalsales);
        txt_totaldiscount = (TextView) findViewById(R.id.txt_totaldiscount);
        txt_total = (TextView) findViewById(R.id.txt_total);

        mHttpCaller = new HttpCaller(this);

        getUserData();

        selectBranch= (AppCompatAutoCompleteTextView) findViewById(R.id.select_branch);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, MainActivity.branchNameList);
        selectBranch.setThreshold(0);//will start working from first character
        selectBranch.setAdapter(adapter);

        selectBranch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectBranch.showDropDown();
                selectBranch.requestFocus();
            }
        });

        selectBranch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    Util.hideKeyboard(SalesReport.this);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        startDate= (EditText) findViewById(R.id.start_date);
        endDate= (EditText) findViewById(R.id.end_date);
        submit= (TextView) findViewById(R.id.txt_submit);

        ((TextView)findViewById(R.id.heading)).setText("Sales Report");
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                titlesContainer.setVisibility(View.GONE);
                Util.hideKeyboard(SalesReport.this);
                gson = new Gson();
                startDateValue = startDate.getText().toString().trim();
                endDateValue = endDate.getText().toString().trim();
                selectedBranchName = selectBranch.getText().toString();
                for (int i=0;i<MainActivity.branchNameList.length;i++) {
                    if (StringUtils.isEqualsIgnoreCase(selectedBranchName, MainActivity.branchNameList[i])) {
                        branchid = MainActivity.branchIdList[i];
                        break;
                    }
                }
                if (StringUtils.isEmpty(branchid)) {
                    Util.showToast(SalesReport.this, "Please select valid branch", AppConstants.COLOR_ORANGE);
                    return;
                }
                if (StringUtils.isEmpty(startDateValue) || StringUtils.isEmpty(endDateValue)) {
                    Util.showToast(SalesReport.this, "Please enter start or end date ", AppConstants.COLOR_RED);
                    return;
                }

                postBillDetails(Config.SALES_REPORT_URL + "?userid=" + preferenceData.getUserId() + "&branchid=" + branchid + "&fromdate=" + startDateValue + "&todate=" + endDateValue);

            }
        });
//        selectBranch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                new AlertDialog.Builder(SalesReport.this)
//                        .setTitle("Select Branch")
//                        .setCancelable(false)
//                        .setSingleChoiceItems(MainActivity.branchNameList, -1, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                selectBranch.setText(MainActivity.branchNameList[which]);
//                                branchid = MainActivity.branchIdList[which];
//                                dialog.dismiss();
//                            }
//                        })
//                        .show();
//            }
//        });
        final Calendar myCalendar = Calendar.getInstance();
        final int mYear = myCalendar.get(Calendar.YEAR);
        final int mMonth = myCalendar.get(Calendar.MONTH);
        final int mDay = myCalendar.get(Calendar.DAY_OF_MONTH);
        final DatePickerDialog.OnDateSetListener startDatePicker = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(year,monthOfYear,dayOfMonth);
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
                startDate.setText(sdf.format(myCalendar.getTime()));
            }

        };

        startDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Util.hideKeyboard(SalesReport.this);
                new DatePickerDialog(SalesReport.this, startDatePicker, mYear, mMonth, mDay).show();
            }
        });

        final DatePickerDialog.OnDateSetListener endDatePicker = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(year,monthOfYear,dayOfMonth);
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
                endDate.setText(sdf.format(myCalendar.getTime()));

            }
        };

        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.hideKeyboard(SalesReport.this);
                new DatePickerDialog(SalesReport.this, endDatePicker,  mYear, mMonth, mDay).show();
            }
        });
    }

    private void getUserData() {
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("id", preferenceData.getKeyApprovalProfileId());
            jsonBody.put("userid", preferenceData.getUserId());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mHttpCaller.callJsonServer(Config.APPROVAL_STATUS_CHECK_URL, jsonBody, new volleyCallback() {
            @Override
            public void onSuccess(String result) {

                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(result);
                    String newStatus = jsonObject.optString("activestatus", "");

                    switch (newStatus) {

                        case "ACTIVE":

                            break;

                        case "INACTIVE":

                            showDeniedDialog();

                            break;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void showDeniedDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Please contact Management for app access. ...");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog

                preferenceData.setLogged(false);
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });

        builder.setCancelable(false);
        AlertDialog alert = builder.create();
        alert.show();
    }

    protected void postBillDetails(String url) {
        Log.e("urlasdv","url131===>"+url);
        barProgressDialog.show();
        sliderimageslist = new ArrayList<>();


        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                Log.e("response","response==>"+response.toString());

                if (response.length() == 0) {
                    Util.showToast(SalesReport.this, "No Record Found !", AppConstants.COLOR_ORANGE, Toast.LENGTH_LONG);

                }
                SalesReportResponse salesReportResponse = gson.fromJson(response.toString(), SalesReportResponse.class);
                if(StringUtils.isEmpty(salesReportResponse.getBilloverallamount())){
                    txt_totalsales.setText("null");
                }else{
                    txt_totalsales.setText(salesReportResponse.getBilloverallamount());
                }
                if(StringUtils.isEmpty(salesReportResponse.getOveralldiscount())){
                    txt_totaldiscount.setText("null");
                }else{
                    txt_totaldiscount.setText(salesReportResponse.getOveralldiscount());
                }
                if(StringUtils.isEmpty(salesReportResponse.getNetamount())){
                    txt_total.setText("null");
                }else{
                    txt_total.setText(salesReportResponse.getNetamount());
                }
                barProgressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Log.e(TAG, "Site Info Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        VolleyController.getInstance().addToRequestQueue(req);
    }
}

