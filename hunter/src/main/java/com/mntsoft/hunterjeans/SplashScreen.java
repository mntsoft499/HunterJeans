package com.mntsoft.hunterjeans;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.mntsoft.hunterjeans.utils.LOG;
import com.mntsoft.hunterjeans.utils.StringUtils;
import com.mntsoft.hunterjeans.views.SharedPreferenceData;
import com.mntsoft.hunterjeans.volley.VolleyController;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by surya on 9/2/16.
 */
public class SplashScreen extends Activity {
    private static final String TAG = "SplashScreen";
    private SharedPreferenceData preferenceData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferenceData = new SharedPreferenceData(this);
        setContentView(R.layout.activity_splashscreen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (preferenceData.isLogged()) {
                    checkAuthentication();
                }else {
                    launchLoginPage();
                }
            }
        }, 1000);
//        startActivity(new Intent(SplashScreen.this, MainActivity.class));
    }

    private void checkAuthentication() {
        LOG.e(TAG, "Model Number :" + Build.MANUFACTURER + " " + Build.MODEL);
        final String username = preferenceData.getUser();
        final String password = preferenceData.getPassword();

        LOG.e(TAG, "checkAuthentication() : username : " + username + ", Paswword : " + password);
        if (username.isEmpty() || password.isEmpty()) {
            launchLoginPage();
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        LOG.e(TAG, "onResponse() : " + response);
                        if (response.length() != 0) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                LOG.e(TAG, "onResponse() : jsonObject--->" + jsonObject.toString());
                                String authentication = jsonObject.optString("authentication","false");

                                if(StringUtils.isEqualsIgnoreCase(authentication,"false")){
                                    launchLoginPage();
                                } else {
                                    preferenceData.setFirstName(jsonObject.getString("firstname"));
                                    preferenceData.setLastName(jsonObject.getString("lname"));
                                    checkApprovalStatus();
                                }
                            } catch (JSONException e) {
                                LOG.e(TAG, "onResponse() : JSONException : " + e.toString());
                                launchLoginPage();
                            }
                        } else {
                            LOG.e(TAG, "onResponse() : response is empty");
                            launchLoginPage();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        LOG.e(TAG, "onErrorResponse() : " + error.toString());
                        launchLoginPage();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                final Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                LOG.e(TAG, "getParams() --->" + params);
                return params;
            }
        };
        VolleyController.getInstance().addToRequestQueue(stringRequest);
    }

    private void checkApprovalStatus() {
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("id", preferenceData.getKeyApprovalProfileId());
            jsonBody.put("userid", preferenceData.getUserId());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final String requestBody = jsonBody.toString();
        LOG.i(TAG, "checkApprovalStatus() : requestBody : " + requestBody);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.APPROVAL_STATUS_CHECK_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        LOG.i(TAG, "onResponse() : " + response);
                        JSONObject jsonObject;
                        try {
                            jsonObject = new JSONObject(response);
//                            preferenceData.setKeyApprovalProfileId(jsonObject.optString("id", ""));
                            String prevStatus = preferenceData.getKeyApprovalProfileStatus();
                            String newStatus = jsonObject.optString("activestatus", "");

                            if (newStatus.equals("ACTIVE")) {
                                startActivity(new Intent(SplashScreen.this, MainActivity.class));
                            } else {
                                preferenceData.setKeyApprovalProfileStatus(newStatus);
                                startActivity(new Intent(SplashScreen.this, AdminApprovalStatus.class));
                            }
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            launchLoginPage();
                            LOG.i(TAG, "onResponse() : JSONException : " + e.toString());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                LOG.e(TAG, "onErrorResponse() : " + error.toString());
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
                } catch (UnsupportedEncodingException e) {
                    LOG.e(TAG, "Unsupported Encoding while trying to get the bytes of %s using %s" + requestBody);
                    return null;
                }
            }
        };
        VolleyController.getInstance().addToRequestQueue(stringRequest);
    }

    private void launchLoginPage() {
        startActivity(new Intent(SplashScreen.this, LoginActivity.class));
        finish();
    }
}
