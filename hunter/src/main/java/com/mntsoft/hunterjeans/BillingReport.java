package com.mntsoft.hunterjeans;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.mntsoft.hunterjeans.adapters.BillReportAdapter;
import com.mntsoft.hunterjeans.helper.HttpCaller;
import com.mntsoft.hunterjeans.model.SalesReportResponse;
import com.mntsoft.hunterjeans.utils.AppConstants;
import com.mntsoft.hunterjeans.utils.StringUtils;
import com.mntsoft.hunterjeans.utils.Util;
import com.mntsoft.hunterjeans.views.SharedPreferenceData;
import com.mntsoft.hunterjeans.volley.VolleyController;
import com.mntsoft.hunterjeans.volley.volleyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import static android.content.ContentValues.TAG;

/**
 * Created by root on 11/4/17.
 */
public class BillingReport extends BaseActivity {
    String branchid,startDateValue,endDateValue;
    private ArrayList<SalesReportResponse> sliderimageslist;
    private TextView totalbills;
    ProgressDialog barProgressDialog;
    private Gson gson;
    private RecyclerView recyclerView;
    private EditText startDate, endDate;
    private TextView submit;
    private SharedPreferenceData preferenceData;
    private AppCompatAutoCompleteTextView selectBranch;
    private String selectedBranchName;
    private HttpCaller mHttpCaller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_billingreport, frag_frame);
        logout.setVisibility(View.INVISIBLE);
        barProgressDialog = new ProgressDialog(BillingReport.this);
        barProgressDialog.setMessage("Loading Bills please wait..");
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        totalbills = (TextView)findViewById(R.id.totalbills);

        mHttpCaller = new HttpCaller(this);

        preferenceData = new SharedPreferenceData(this);

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
                    Util.hideKeyboard(BillingReport.this);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        startDate= (EditText) findViewById(R.id.start_date);
        endDate= (EditText) findViewById(R.id.end_date);
        submit= (TextView) findViewById(R.id.txt_submit);
        findViewById(R.id.date_picker_container).setVisibility(View.VISIBLE);
        LinearLayout branchPickerContainer = findViewById(R.id.branch_picker_container);
        branchPickerContainer.setWeightSum(1.0f);
        findViewById(R.id.input_layout_filter).setVisibility(View.GONE);
        ((TextView)findViewById(R.id.heading)).setText("Billing Report");
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
                Util.hideKeyboard(BillingReport.this);
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
                    Util.showToast(BillingReport.this, "Please select valid branch name ", AppConstants.COLOR_ORANGE);
                    return;
                }
                if (StringUtils.isEmpty(startDateValue) || StringUtils.isEmpty(endDateValue)) {
                    Util.showToast(BillingReport.this, "Please enter start or end date ", AppConstants.COLOR_RED);
                    return;
                }

                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(BillingReport.this));
                gson = new Gson();
                postBillDetails(Config.BILL_REPORT_URL + "?userid=" + preferenceData.getUserId() + "&branchid=" + branchid + "&fromdate=" + startDateValue + "&todate=" + endDateValue);
            }
        });
//        selectBranch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                new AlertDialog.Builder(BillingReport.this)
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
                Util.hideKeyboard(BillingReport.this);

                new DatePickerDialog(BillingReport.this, startDatePicker, mYear, mMonth, mDay).show();
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
                Util.hideKeyboard(BillingReport.this);

                new DatePickerDialog(BillingReport.this, endDatePicker,  mYear, mMonth, mDay).show();
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

    protected Void postBillDetails(String url) {
        barProgressDialog.show();
        sliderimageslist = new ArrayList<>();
        JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        try {
                            if (response.length() == 0) {
                                Util.showToast(BillingReport.this, "No Record Found !", AppConstants.COLOR_ORANGE, Toast.LENGTH_LONG);
                            }
                            for (int i = 0; i < response.length(); i++) {
                                sliderimageslist.add(gson.fromJson(response.getJSONObject(i).toString(), SalesReportResponse.class));
                            }
                            totalbills.setText(String.valueOf(response.length()));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                        barProgressDialog.dismiss();

                        recyclerView.setAdapter(new BillReportAdapter(BillingReport.this, sliderimageslist));

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(BillingReport.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }

        });
        VolleyController.getInstance().addToRequestQueue(req);

        return null;
    }
}
