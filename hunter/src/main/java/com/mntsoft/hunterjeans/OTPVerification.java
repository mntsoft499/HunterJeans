package com.mntsoft.hunterjeans;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.mntsoft.hunterjeans.utils.AppConstants;
import com.mntsoft.hunterjeans.utils.LOG;
import com.mntsoft.hunterjeans.utils.StringUtils;
import com.mntsoft.hunterjeans.utils.Util;
import com.mntsoft.hunterjeans.views.SharedPreferenceData;
import com.mntsoft.hunterjeans.volley.VolleyController;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class OTPVerification extends BaseActivity {
    private static final String TAG = "OTPVerification";

    private EditText otpVerificationEdit, formName, formNumber, formAddress;
    private AppCompatButton otpVerifyButton, otpGenerateButton;
    private LinearLayout formContainer, formInputContainer;
    private RelativeLayout otpContainer;
    private TextView textDescription;
    private String name, mobile, address,otp;

    private SharedPreferenceData preferenceData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);
        otpVerificationEdit = findViewById(R.id.et_otpVerification);
        otpVerifyButton = findViewById(R.id.btn_otp_verify);
        formName = findViewById(R.id.form_nameInput);
        formAddress = findViewById(R.id.form_addressInput);
        formNumber = findViewById(R.id.form_mobInput);
        formAddress = findViewById(R.id.form_addressInput);
        otpGenerateButton = findViewById(R.id.generate_OTP);
        formContainer = findViewById(R.id.formContainer);
        otpContainer = findViewById(R.id.otp_container);
        formInputContainer = findViewById(R.id.form_input_container);
        formContainer = findViewById(R.id.formContainer);
        textDescription = findViewById(R.id.txt_description);
        otpVerifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringUtils.isEmpty(otpVerificationEdit.getText().toString().trim())) {
                    Util.showToast(OTPVerification.this, "can't be empty", AppConstants.COLOR_GRAY);
                } else if (StringUtils.isEquals(otp, otpVerificationEdit.getText().toString().trim())) {
                    verifyOTPandSubmit();
                } else {
                    Util.showToast(OTPVerification.this, "OTP not matched, please try again !", AppConstants.COLOR_RED);
                }
            }
        });
        otpGenerateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = formName.getText().toString().trim();
                mobile = formNumber.getText().toString().trim();
                address = formAddress.getText().toString().trim();
                if (StringUtils.isEmpty(name) || StringUtils.isEmpty(mobile) || StringUtils.isEmpty(address)) {
                    Util.showToast(OTPVerification.this, OTPVerification.this.getResources().getString(R.string.any_field_cant_empty), AppConstants.COLOR_RED);
                } else if (mobile.length() != 10) {
                    Util.showToast(OTPVerification.this, OTPVerification.this.getResources().getString(R.string.invalid_mobile_number), AppConstants.COLOR_RED);
                } else {
                    requestOTP();
                }
            }
        });
        preferenceData = new SharedPreferenceData(this);
        formInputVisibility(View.VISIBLE);
        otpFormVisibility(View.GONE);
    }

    private void formInputVisibility(int visibility) {
        formInputContainer.setVisibility(visibility);
        if (visibility==View.VISIBLE)
            textDescription.setText(getResources().getString(R.string.please_fill_form_for_admin_approval));
    }

    private void otpFormVisibility(int visibility) {
        otpContainer.setVisibility(visibility);
        if (visibility == View.VISIBLE) {
            textDescription.setText(getResources().getString(R.string.otp_sent_to_mobile));
        }
    }

    private void verifyOTPandSubmit() {
        showProgressDialog();
        final JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("userid", preferenceData.getUserId());
            jsonBody.put("name", name);
            jsonBody.put("mobilenumber", mobile);
            jsonBody.put("address", address);
            jsonBody.put("devicename", Build.MANUFACTURER + "-" + Build.MODEL);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String requestBody = jsonBody.toString();
        LOG.e(TAG, "verifyOTPandSubmit() : requestBody : " + requestBody);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Config.FORM_SUBMIT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        LOG.i(TAG, "onResponse() : " + response);
                        JSONObject jsonObject;
                        try {
                            jsonObject = new JSONObject(response);
                            preferenceData.setKeyApprovalProfileId(jsonObject.optString("id", ""));
                            preferenceData.setKeyApprovalProfileStatus(jsonObject.optString("activestatus", ""));
                            startActivity(new Intent(OTPVerification.this, AdminApprovalStatus.class));
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            LOG.i(TAG, "onResponse() : JSONException : " + e.toString());
                        }
                        dismissProgressDialog();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        LOG.i(TAG, "onErrorResponse() : " + error.toString());

                        dismissProgressDialog();
                    }
                }){
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    LOG.e(TAG, "Unsupported Encoding while trying to get the bytes of %s using %s" + requestBody);
                    return null;
                }
            }
        };
        VolleyController.getInstance().addToRequestQueue(stringRequest);
    }

    private void requestOTP() {
        showProgressDialog();
        String userId = preferenceData.getUserId();
        final JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("userid", userId);
            jsonBody.put("mobilenumber", mobile);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String requestBody = jsonBody.toString();
        LOG.e(TAG, "requestOTP() : requestBody : " + requestBody);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.OTP_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        LOG.i(TAG, "onResponse() : " + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Log.e("otpnum",response);
                            otp = jsonObject.optString("otp","");
                            formInputVisibility(View.GONE);
                            otpFormVisibility(View.VISIBLE);
                            dismissProgressDialog();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        LOG.e(TAG, "onErrorResponse() : " + error.toString());
                        dismissProgressDialog();
                    }
                }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    LOG.e(TAG, "Unsupported Encoding while trying to get the bytes of %s using %s" + requestBody);
                    return null;
                }
            }
        };
        VolleyController.getInstance().addToRequestQueue(stringRequest);
    }
}
