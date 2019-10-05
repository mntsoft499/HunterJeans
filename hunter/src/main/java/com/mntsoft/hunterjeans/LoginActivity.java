package com.mntsoft.hunterjeans;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.mntsoft.hunterjeans.helper.HttpCaller;
import com.mntsoft.hunterjeans.utils.AppConstants;
import com.mntsoft.hunterjeans.utils.LOG;
import com.mntsoft.hunterjeans.utils.StringUtils;
import com.mntsoft.hunterjeans.utils.Util;
import com.mntsoft.hunterjeans.views.SharedPreferenceData;
import com.mntsoft.hunterjeans.volley.VolleyController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by root on 7/12/17.
 */
public class LoginActivity extends BaseActivity{
    private static final String TAG = LoginActivity.class.getSimpleName();
    private Button btn_login;
    private TextView txt_loginhere;
    String URL = "http://localhost:8080/DeniumHubWS/rest/LoginWS/login";
    private EditText edt_employeeid,edt_password;
    private String getempid,getpassword;
    private SharedPreferenceData session;
    HttpCaller mHttpCaller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getLayoutInflater().inflate(R.layout.activity_login, frag_frame);
        setContentView(R.layout.activity_login);
        session = new SharedPreferenceData(LoginActivity.this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
//        logout.setVisibility(View.INVISIBLE);
//        help.setVisibility(View.INVISIBLE);

//        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT);
//        layoutParams.gravity= Gravity.CENTER;
//        img_toolicon.setLayoutParams(layoutParams);
//        barProgressDialog = new ProgressDialog(LoginActivity.this);
//        barProgressDialog.setMessage("Loading please wait..");
        btn_login = (Button)findViewById(R.id.btn_login);
        edt_employeeid = (EditText)findViewById(R.id.input_userId);
        edt_password = (EditText)findViewById(R.id.input_password);

        mHttpCaller = new HttpCaller(this);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(LoginActivity.this,MainActivity.class));
                getempid = edt_employeeid.getText().toString().trim();
                getpassword = edt_password.getText().toString().trim();
                if (StringUtils.isEmpty(getempid) ) {
                    edt_employeeid.setError("UserID can't be empty");
                    Util.showToast(LoginActivity.this,"UserId or Password can't be empty !",AppConstants.COLOR_RED);
                } else if (StringUtils.isEmpty(getpassword)) {
                    edt_password.setError("Password can't be empty");
                    Util.showToast(LoginActivity.this,"UserId or Password can't be empty !",AppConstants.COLOR_RED);
                } else {
                    getempid = edt_employeeid.getText().toString();
                    getpassword = edt_password.getText().toString();
                    LOG.d(TAG, "UserId => " + getempid + ", Password => " + getpassword);
                    checkAuthentication();
                }

            }
        });
    }

    /*private void checkAuthentication() {

        HashMap hashMap = new HashMap();
        hashMap.put("username", getempid);
        hashMap.put("password", getpassword);

        mHttpCaller.callServer("http://hunterretail.com/hunterservices/getlogininfo__V1.htm", hashMap, new volleyCallback() {
            @Override
            public void onSuccess(String result) {
                Log.e("result",result);
            }

            @Override
            public void onError(String error) {
                Log.e("error",error);
            }
        });
    }*/

    private void checkAuthentication() {

//        barProgressDialog.show();
        showProgressDialog();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        LOG.d(TAG, "onResponse() : " + response);
//                        barProgressDialog.dismiss();
                        dismissProgressDialog();
                        if (response.length() != 0) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String authentication = jsonObject.optString("authentication","false");

                                if(StringUtils.isEqualsIgnoreCase(authentication,"false")){
                                    Util.showToast(LoginActivity.this, getString(R.string.invalid_usernam_pass), AppConstants.COLOR_GRAY);
                                }else {
                                    String userId = jsonObject.getString("userId");
                                    String clientId = jsonObject.getString("clientId");
                                    String branchId = jsonObject.getString("branchId");
                                    String branchName = jsonObject.getString("branchName");
                                    String roleId = jsonObject.getString("roleId");
                                    String username = jsonObject.getString("username");
                                    String password = jsonObject.getString("password");
                                    String firstName = jsonObject.getString("firstname");
                                    String lastName = jsonObject.getString("lname");
                                    String roleName = jsonObject.getString("roleName");

                                    session.setUserId(userId);
                                    session.setRollId(roleId);
                                    session.setLogged(true);
                                    session.setRegistered(true);
                                    session.setUser(username);
                                    session.setFirstName(firstName);
                                    session.setLastName(lastName);
                                    session.setPassword(password);
                                    Intent mainint = new Intent(LoginActivity.this, OTPVerification.class);
                                    startActivity(mainint);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                                LOG.e(TAG, "onResponse() : JSONException : " + e.toString());
                                Util.showToast(LoginActivity.this, "Error: " + e.getMessage(), AppConstants.COLOR_GRAY);
                            }
                        }else{
                            LOG.e(TAG, "onResponse() : Response is empty ");
                            Util.showToast(LoginActivity.this, getString(R.string.invalid_usernam_pass), AppConstants.COLOR_RED);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        barProgressDialog.dismiss();
                        dismissProgressDialog();
                        LOG.e(TAG, "onErrorResponse() : " + error.getLocalizedMessage());
                        Util.showToast(LoginActivity.this, "Server Not Responding !..."+error.toString(), AppConstants.COLOR_RED);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                final Map<String, String> params = new HashMap<>();
                params.put("username", getempid);
                params.put("password", getpassword);
                LOG.i(TAG, "getParams() : " + params);
                return params;
            }
        };

        //setting up the retry policy for slower connections
        int socketTimeout = 120000;//120000 milli seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);

        VolleyController.getInstance().addToRequestQueue(stringRequest);
    }
}