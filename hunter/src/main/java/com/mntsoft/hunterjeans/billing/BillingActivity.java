package com.mntsoft.hunterjeans.billing;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.mntsoft.hunterjeans.BaseActivity;
import com.mntsoft.hunterjeans.Config;
import com.mntsoft.hunterjeans.LoginActivity;
import com.mntsoft.hunterjeans.MainActivity;
import com.mntsoft.hunterjeans.R;
import com.mntsoft.hunterjeans.customviews.CustomAlertBox;
import com.mntsoft.hunterjeans.helper.HttpCaller;
import com.mntsoft.hunterjeans.model.Product;
import com.mntsoft.hunterjeans.model.SwipeMachineBank;
import com.mntsoft.hunterjeans.utils.AppConstants;
import com.mntsoft.hunterjeans.utils.LOG;
import com.mntsoft.hunterjeans.utils.StringUtils;
import com.mntsoft.hunterjeans.utils.Util;
import com.mntsoft.hunterjeans.views.SharedPreferenceData;
import com.mntsoft.hunterjeans.volley.VolleyController;
import com.mntsoft.hunterjeans.volley.volleyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class BillingActivity extends BaseActivity {

    private static final String TAG = BillingActivity.class.getSimpleName();
    private LinearLayout progressBarContainer, billDetailsContainer;
    private AppCompatEditText billingMobNumber, billingCash, billingCard;
    private AppCompatButton generateBill;
    private Spinner billingSwipeMachine;
    private AppCompatTextView billingHeading, billingNetAmount, billingPayment, billingReturnChange;
    private String mobileNumber;
    private String cashAmount;
    private String cardAmount;
    private String bajajCardAmount;
    private String totalPayment;
    private String returnChangeValue;
    private String anyDiscountValue;
    private String netAmount;
    private String selectedBankId = null;
    private List<SwipeMachineBank> swipeMachineBankList = new ArrayList<>();

    private SharedPreferenceData preferenceData;
    private ProgressBar swipeMachinProgressBar;
    private Gson gson;
    private TextView billingStatus;
    private RelativeLayout statusContainer;
    private Product product;
    private RelativeLayout swipeMachineContainer;
    private AppCompatCheckBox cardCheckBx;
    private AppCompatEditText bajajFinserv;
    private AppCompatEditText anyDiscount;
    private float anyDiscountFloutValue;
    private HttpCaller mHttpCaller;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_biling, frag_frame);
        preferenceData = new SharedPreferenceData(this);
        billingHeading = (AppCompatTextView) findViewById(R.id.billing_heading);
        billingMobNumber = (AppCompatEditText) findViewById(R.id.billing_mob_number);
        bajajFinserv = (AppCompatEditText) findViewById(R.id.bajaj_finserv);
        cardCheckBx = (AppCompatCheckBox) findViewById(R.id.card_check_box);
        billingSwipeMachine = (Spinner) findViewById(R.id.billing_swipe_machin);
        billingCash = (AppCompatEditText) findViewById(R.id.billing_cash);
        billingCard = (AppCompatEditText) findViewById(R.id.billing_card);
        billingPayment = (AppCompatTextView) findViewById(R.id.billing_payment);
        swipeMachineContainer = (RelativeLayout) findViewById(R.id.swipe_machin_container);
        billingReturnChange = (AppCompatTextView) findViewById(R.id.billing_return_change);
        billingNetAmount = (AppCompatTextView) findViewById(R.id.billing_net_amount);
        generateBill = (AppCompatButton) findViewById(R.id.generate_bill);
        billDetailsContainer = (LinearLayout) findViewById(R.id.bill_details_container);
        progressBarContainer = (LinearLayout) findViewById(R.id.progressbar_container);
        swipeMachinProgressBar = (ProgressBar) findViewById(R.id.swipe_machin_progress_bar);
        statusContainer = (RelativeLayout) findViewById(R.id.status_container);
        billingStatus = (TextView) findViewById(R.id.billing_status);
        anyDiscount = (AppCompatEditText) findViewById(R.id.any_discount);
        mHttpCaller = new HttpCaller(this);

        anyDiscount.addTextChangedListener(new SmartCardTextWatcher());

        getUserData();

        cardCheckBx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    billingCard.setVisibility(View.VISIBLE);
                } else {
                    showCardAlert();
                }
            }
        });
        findViewById(R.id.goBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BillingActivity.this, MainActivity.class));
                finish();
            }
        });
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (progressBarContainer.getVisibility() == View.GONE) {
                    if (billDetailsContainer.getVisibility() == View.GONE) {
                        startActivity(new Intent(BillingActivity.this, MainActivity.class));
                        finish();
                    } else {
                        onBackPressed();
                    }
                }
            }
        });
        billingCard.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    swipeMachineContainer.setVisibility(View.VISIBLE);
                } else {
                    swipeMachineContainer.setVisibility(View.GONE);
                    billingSwipeMachine.setSelection(0);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        generateBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.hideKeyboard(BillingActivity.this);
                if (!checkRestrictions()) {
                    return;
                }
                new AlertDialog.Builder(BillingActivity.this)
                        .setTitle("Are you sure you want to Generate Bill ?")
                        .setMessage(
                                "\nMob       : " + mobileNumber + "\n" +
                                        "Payment   : " + totalPayment + "\n" +
                                        "NetAmount : " + netAmount + "\n" +
                                        "Discount  : " + anyDiscountFloutValue)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                generateBillRequest();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
            }
        });

        billingCash.addTextChangedListener(new SmartCardTextWatcher());
        billingCard.addTextChangedListener(new SmartCardTextWatcher());
        bajajFinserv.addTextChangedListener(new SmartCardTextWatcher());
        billingMobNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { mobileNumber = s.toString().trim(); }
            @Override
            public void afterTextChanged(Editable s) { }
        });
        gson = new Gson();
        product = ProductAddActivity.productResponse == null ? new Product() : ProductAddActivity.productResponse;
        setBillValues();
        showSwipeMachineList();

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

    @Override
    public void onBackPressed() {
        if (progressBarContainer.getVisibility() == View.GONE) {
            super.onBackPressed();
        }
    }

    private boolean checkRestrictions() {
        mobileNumber = billingMobNumber.getText().toString().trim();
        totalPayment = billingPayment.getText().toString().trim();
        float netAmnt = Util.getFloatFromString(product.getNetamount());
        float payment = Util.getFloatFromString(totalPayment);
        if (StringUtils.isEmpty(mobileNumber)) {
            Util.showToast(BillingActivity.this, "Mobile number can't  be empty", AppConstants.COLOR_RED, Toast.LENGTH_LONG);
            return false;
        }
        if (mobileNumber.length() != 10) {
            Util.showToast(BillingActivity.this, "Please enter valid mobile number", AppConstants.COLOR_RED, Toast.LENGTH_LONG);
            return false;
        }
        if (netAmnt > payment) {
            Util.showToast(BillingActivity.this, "Payment  amount can't be less than Net amount", AppConstants.COLOR_ORANGE, Toast.LENGTH_LONG);
            return false;
        }
        selectedBankId = null;
        if (cardCheckBx.isChecked() && Math.round(Util.getFloatFromString(cardAmount)) != 0) {
            if (billingSwipeMachine.getSelectedItemPosition() != 0) {
                for (SwipeMachineBank machineBank : swipeMachineBankList) {
                    if (StringUtils.isEqualsIgnoreCase((String) billingSwipeMachine.getSelectedItem(), machineBank.getBankName())) {
                        selectedBankId = machineBank.getBankId();
                    }
                }
            } else {
                Util.showToast(BillingActivity.this, "Please select Swipe Machine", AppConstants.COLOR_ORANGE);
                return false;
            }
        }

        return true;
    }

    private void generateBillRequest() {
        billDetailsContainer.setVisibility(View.GONE);
        progressBarContainer.setVisibility(View.VISIBLE);
        Product product = ProductAddActivity.productResponse;
        product.setUserid(preferenceData.getUserId());
        product.setMobilenumber(mobileNumber);
        product.setTotalpayment(totalPayment);
        product.setCashpayment(cashAmount);
        product.setBajajpayment(bajajCardAmount);
        if (anyDiscountFloutValue > 0) {
            product.setTotaldisocountpercent(String.valueOf(anyDiscountFloutValue));
        }
        LOG.e(TAG, "Any Discount : " + product.getTotaldiscountpercent());
        if (cardCheckBx.isChecked() && Math.round(Util.getFloatFromString(cardAmount)) != 0) {
            product.setSwipebankid(selectedBankId);
            product.setCardpayment(cardAmount);
        }

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject = new JSONObject(gson.toJson(product));
            LOG.e(TAG, "generateBillRequest() : " + jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final JSONObject jsonObject1 = jsonObject;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.GENERATE_BILL_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                LOG.e(TAG, "generateBillRequest() : onResponse() : " + response);
                JSONObject jsonResponse = new JSONObject();
                try {
                    jsonResponse = new JSONObject(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                progressBarContainer.setVisibility(View.GONE);
                statusContainer.setVisibility(View.VISIBLE);
                String status = jsonResponse.optString("status", "Status not available");
                billingStatus.setText(StringUtils.isEqualsIgnoreCase("success", status) ? "Bill is successfully generated and bill copy is sent to this mobile number - "+mobileNumber+"\n Thank you" : status);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                LOG.e(TAG, "generateBillRequest() : onErrorResponse() : " + error.getMessage());
                progressBarContainer.setVisibility(View.GONE);
                statusContainer.setVisibility(View.VISIBLE);
                billingStatus.setText("Error while generating bill");
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                LOG.e(TAG, "generateBillRequest() : getBody() : " + jsonObject1.toString());
                try {
                    return jsonObject1.toString().getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    LOG.e(TAG, "Unsupported Encoding while trying to get the bytes of %s using %s" + uee.getMessage());
                    return null;
                }
            }
        };
        VolleyController.getInstance().addToRequestQueue(stringRequest);
    }

    private void setBillValues() {
        billingNetAmount.setText(String.valueOf(Math.round(Util.getFloatFromString(product.getNetamount()))));
        updateChangedAmount(false);
    }

    private void updateChangedAmount(boolean show) {
        cashAmount = billingCash.getText().toString().trim();
        cardAmount = billingCard.getText().toString().trim();
        bajajCardAmount = bajajFinserv.getText().toString().trim();
        netAmount = billingNetAmount.getText().toString().trim();
        anyDiscountValue = anyDiscount.getText().toString().trim();
        LOG.e(TAG, "AnyDiscountValue : " + anyDiscountValue);
        LOG.e(TAG, "AnyDiscountValue : " + Util.getFloatFromString(anyDiscountValue));

        // discount should be less than max discount
        anyDiscountFloutValue = Util.getFloatFromString(anyDiscountValue);
        Product product = ProductAddActivity.productResponse;

        if (anyDiscountFloutValue < 0) {
            float totalMRP = Util.getFloatFromString(product.getTotalmrp());
            float netAmountFloatValue = Util.getFloatFromString(netAmount);
            float difference = totalMRP - netAmountFloatValue;
            LOG.e(TAG, "Discount Diff : " + difference + "..." + anyDiscountFloutValue);
            if (difference >= 50.0) {
                difference = 50.0f;
            }
            difference=(-1) * difference;
            if (difference == 0 && anyDiscountFloutValue < difference) {
                Util.showToast(BillingActivity.this, "You are exceeding the max discount value", AppConstants.COLOR_ORANGE);
                anyDiscount.setText("");
                anyDiscountFloutValue = 0;
                return;
            } else if (anyDiscountFloutValue < difference) {
                Util.showToast(BillingActivity.this, "You are exceeding the max discount value", AppConstants.COLOR_ORANGE);
                anyDiscount.setText("");
                anyDiscountFloutValue = 0;
                return;
            } else {

            }

        } else {
            if (anyDiscountFloutValue > Util.getFloatFromString(product.getMaxremainingdiscount())) {
                Util.showToast(BillingActivity.this, "Discount should be less than max discount", AppConstants.COLOR_ORANGE);
                anyDiscount.setText("");
                anyDiscountFloutValue = 0;
                return;
            }
        }

        float payment = Util.getFloatFromString(cashAmount) + Util.getFloatFromString(cardAmount) + Util.getFloatFromString(bajajCardAmount);
        billingPayment.setText(String.valueOf(payment));
        float returnChange = payment - Util.getFloatFromString(netAmount);
        if (anyDiscountFloutValue > 0) {
            returnChange = returnChange + anyDiscountFloutValue;
            billingReturnChange.setText(String.valueOf(returnChange));
        }else if (returnChange >= 0) {
            billingReturnChange.setText(String.valueOf(returnChange));
        } else/* if (show)*/{
            billingReturnChange.setText("0");
//            Util.showToast(BillingActivity.this, "Payment amount can't be less than Net amount", AppConstants.COLOR_RED);
        }
    }

    private void showSwipeMachineList() {
        JsonArrayRequest stringRequest = new JsonArrayRequest(Request.Method.GET, Config.GET_SWIPE_MACHINE_URL + "?branchid=" + preferenceData.getbranchid(), null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        LOG.e(TAG, "showSwipeMachineList() : onResponse() : " + response);
                        final String[] swipeMachineBankNames = new String[response.length() + 1];
                        swipeMachineBankNames[0] = "Select";
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                SwipeMachineBank swipeMachineBank = gson.fromJson(jsonObject.toString(), SwipeMachineBank.class);
                                swipeMachineBankNames[i + 1] = swipeMachineBank.getBankName();
                                swipeMachineBankList.add(swipeMachineBank);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(BillingActivity.this, android.R.layout.simple_expandable_list_item_1, swipeMachineBankNames);

                        billingSwipeMachine.setAdapter(spinnerArrayAdapter);
                        swipeMachinProgressBar.setVisibility(View.GONE);
                        billingSwipeMachine.setVisibility(View.VISIBLE);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                LOG.e(TAG, "showSwipeMachineList() : onErrorResponse() : " + error.getMessage());
            }
        });
        VolleyController.getInstance().addToRequestQueue(stringRequest);
    }

    public void showCardAlert() {
        CustomAlertBox builder = new CustomAlertBox(this);
        builder.setMessage("Are you sure you want to unselect card ?");
        builder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        billingCard.setVisibility(View.GONE);
                        billingSwipeMachine.setSelection(0);
                        billingCard.setText("");
                    }
                });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                cardCheckBx.setChecked(true);
            }
        });
        builder.show();
    }

    private class SmartCardTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            updateChangedAmount(true);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}
