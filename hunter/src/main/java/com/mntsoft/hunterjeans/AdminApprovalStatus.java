package com.mntsoft.hunterjeans;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mntsoft.hunterjeans.helper.HttpCaller;
import com.mntsoft.hunterjeans.utils.StringUtils;
import com.mntsoft.hunterjeans.views.SharedPreferenceData;
import com.mntsoft.hunterjeans.volley.volleyCallback;

import org.json.JSONException;
import org.json.JSONObject;

public class AdminApprovalStatus extends BaseActivity {

    private AppCompatTextView statusInformer;
    private AppCompatButton openDashboard;
    private SharedPreferenceData preferenceData;
    private TextView statusDescription;
    //Edited by Brahma
    private Button refresh;
    private SharedPreferenceData mPreferenceData;
    HttpCaller mHttpCaller;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_approval_status);
        preferenceData = new SharedPreferenceData(this);
        statusInformer = findViewById(R.id.status_informer);
        statusDescription = findViewById(R.id.status_description);
        openDashboard = findViewById(R.id.openDashboard);
        refresh = findViewById(R.id.refresh);
        mPreferenceData = new SharedPreferenceData(getApplicationContext());

        mHttpCaller = new HttpCaller(this);

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUserData();
            }
        });
        openDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminApprovalStatus.this, MainActivity.class);
//                intent.putExtra("clist", clist);
                startActivity(intent);
                finish();
            }
        });
        setStatusText();
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
                            statusInformer.setText(getString(R.string.active));
                            statusInformer.getBackground().setColorFilter(getResources().getColor(R.color.green_dark), PorterDuff.Mode.SRC);
                            statusDescription.setText(getString(R.string.admin_active_status_description));
                            openDashboard.setVisibility(View.VISIBLE);
                            refresh.setVisibility(View.GONE);
                            break;
                        case "PENDING":

                            setStatusText();

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

                logoutApp();
            }
        });

        builder.setCancelable(false);
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void logoutApp() {
        mPreferenceData.setLogged(false);
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
    }

    private void setStatusText() {
        if (StringUtils.isEqualsIgnoreCase("pending", preferenceData.getKeyApprovalProfileStatus())) {
            statusInformer.setText(getString(R.string.progress));
            statusInformer.getBackground().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC);
            statusDescription.setText(getString(R.string.admin_pending_status_description));
        } else if (StringUtils.isEqualsIgnoreCase("active", preferenceData.getKeyApprovalProfileStatus())) {
            statusInformer.setText(getString(R.string.active));
            statusInformer.getBackground().setColorFilter(getResources().getColor(R.color.green_dark), PorterDuff.Mode.SRC);
            statusDescription.setText(getString(R.string.admin_active_status_description));
            openDashboard.setVisibility(View.VISIBLE);
            refresh.setVisibility(View.GONE);
        } else  {
            statusInformer.setText(getString(R.string.rejected));
            statusInformer.getBackground().setColorFilter(getResources().getColor(R.color.red_darkest), PorterDuff.Mode.SRC);
            statusDescription.setText(getString(R.string.admin_reject_status_description));
        }
    }
}
