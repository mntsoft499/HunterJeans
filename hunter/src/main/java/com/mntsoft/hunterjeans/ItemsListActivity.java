package com.mntsoft.hunterjeans;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mntsoft.hunterjeans.adapters.ItemListAdapter;
import com.mntsoft.hunterjeans.views.StockCnts;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by root on 2/1/18.
 */
public class ItemsListActivity extends BaseActivity {

    private ListView stocklist;
    private ArrayList<StockCnts> sliderimageslist;
    String Greceiveid,Gbranchid;
    private TextView txt_list,txt_submit;
    ProgressDialog barProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_itemslist, frag_frame);
        barProgressDialog = new ProgressDialog(ItemsListActivity.this);
        barProgressDialog.setMessage("Loading please wait..");
        logout.setVisibility(View.INVISIBLE);
        Greceiveid = getIntent().getStringExtra("RECEIVEID");
        Gbranchid = getIntent().getStringExtra("BRANCHID");

        stocklist = (ListView)findViewById(R.id.stocklist);
        txt_list = (TextView)findViewById(R.id.txt_list);
        txt_submit = (TextView)findViewById(R.id.txt_submit);

        txt_list.setVisibility(View.VISIBLE);
        getstockitems("http://denimhub.mysalesinfo.co.in/services/getChildData.htm", Greceiveid);

        txt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getredeempoints("http://denimhub.mysalesinfo.co.in/services/generateotpforstockreceive.htm", Greceiveid, Gbranchid);
            }
        });
    }

    private void getredeempoints(String statesUrl, final String Greceiveid, final String Gbranchid ) {
        barProgressDialog.show();
        barProgressDialog.setCancelable(false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, statesUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Response", response);
                        if (response.length() != 0) {
                            final String Recvotp = response.toString();
                            sharedPreferenceData.setFCMID(Recvotp);
                            final Dialog d = new Dialog(ItemsListActivity.this, android.R.style.Theme_DeviceDefault_Dialog_MinWidth);
                            d.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            d.setCancelable(false);
                            d.setContentView(R.layout.dialog_itemlist);
                            d.show();
                            final EditText edt_otp = (EditText) d.findViewById(R.id.edt_otp);


                            final TextView txt_submit = (TextView) d.findViewById(R.id.txt_submit);
                            TextView txt_cancle = (TextView) d.findViewById(R.id.txt_close);



                            txt_submit.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String lableopt = edt_otp.getText().toString().trim();
                                    if(lableopt.equals(Recvotp)){
                                        finalsave(" http://denimhub.mysalesinfo.co.in/services/savestockbranch.htm",Gbranchid,Greceiveid);
                                        d.dismiss();
                                    }else{
                                        Toast.makeText(ItemsListActivity.this, "Your OTP Is Not Authorised!...", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });

                            txt_cancle.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    d.dismiss();
                                }
                            });


                        } else {
                            barProgressDialog.dismiss();
                            Toast.makeText(ItemsListActivity.this, "Server Not Responding !...", Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        barProgressDialog.dismiss();
                        Toast.makeText(ItemsListActivity.this, "Server Not Responding !..." + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                final Map<String, String> params = new HashMap<>();

                params.put("receiveid", Greceiveid);
                params.put("branchid", Gbranchid);
                Log.e("params", "params--->" + params);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void finalsave(String url, final String Gbranchid, final String Greceiveid ){
        barProgressDialog.show();
        barProgressDialog.setCancelable(false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Response123436666==>", response);
                        if (response.length() != 0) {
                                String Gresponse = response.toString();
                                if(Gresponse.equalsIgnoreCase("Success")){
                                    final Dialog d = new Dialog(ItemsListActivity.this, android.R.style.Theme_DeviceDefault_Dialog_MinWidth);
                                    d.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                    d.setCancelable(false);
                                    d.setContentView(R.layout.dialog_network);
                                    d.show();
                                    TextView alertmsg = (TextView) d.findViewById(R.id.alertMessage);
                                    Button cancel = (Button) d.findViewById(R.id.networkCancel);
                                    alertmsg.setText("Saving Stock Which You Want To Receive Successfully!...");
                                    cancel.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            d.dismiss();
                                            finish();
                                        }
                                    });
                                }else{
                                    barProgressDialog.dismiss();
                                    final Dialog d = new Dialog(ItemsListActivity.this, android.R.style.Theme_DeviceDefault_Dialog_MinWidth);
                                    d.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                    d.setCancelable(false);
                                    d.setContentView(R.layout.dialog_network);
                                    d.show();
                                    TextView alertmsg = (TextView) d.findViewById(R.id.alertMessage);
                                    Button cancel = (Button) d.findViewById(R.id.networkCancel);
                                    alertmsg.setText("Failed !...");
                                    cancel.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            d.dismiss();
                                            finish();
                                        }
                                    });
                                }

                        }else{
                            barProgressDialog.dismiss();
                            final Dialog d = new Dialog(ItemsListActivity.this, android.R.style.Theme_DeviceDefault_Dialog_MinWidth);
                            d.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            d.setCancelable(false);
                            d.setContentView(R.layout.dialog_network);
                            d.show();
                            TextView alertmsg = (TextView) d.findViewById(R.id.alertMessage);
                            Button cancel = (Button) d.findViewById(R.id.networkCancel);
                            alertmsg.setText("Failed !...");
                            cancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    d.dismiss();
                                    finish();
                                }
                            });
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        barProgressDialog.dismiss();
                        Log.e("error","error--->"+error.toString());
                        Toast.makeText(ItemsListActivity.this, "Server Not Responding !..."+error.toString(), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                final Map<String, String> params = new HashMap<>();
                params.put("branchid", Gbranchid);
                params.put("receiveid", Greceiveid);
                Log.e("params", "params--->" + params);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void getstockitems(String url, final String Greceiveid) {
        sliderimageslist = new ArrayList<StockCnts>();
        barProgressDialog.show();
        barProgressDialog.setCancelable(false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Response123436666==>", response);
                        if (response.length() != 0) {
                            try {

                                JSONArray jsonArray = new JSONArray(response);
                                Log.e("jsonArray","jsonArray--->"+jsonArray);
                                //JSONObject jsonObject = new JSONObject(response);
                                //Log.e("jsonObject","jsonObject--->"+jsonObject.toString());
                                for(int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String allocatedqty = jsonObject.getString("allocatedqty");
                                    String barcode = jsonObject.getString("barcode");

                                    StockCnts stockCnts = new StockCnts();
                                    stockCnts.setAllocatedqty(allocatedqty);
                                    stockCnts.setBarcode(barcode);
                                    sliderimageslist.add(stockCnts);

                                }
                                barProgressDialog.dismiss();

                            } catch (JSONException e) {
                                barProgressDialog.dismiss();
                                e.printStackTrace();
                                Toast.makeText(ItemsListActivity.this,
                                        "Error: " + e.getMessage(),
                                        Toast.LENGTH_LONG).show();
                                Log.e("JSONException", "JSONException--->" + e.toString());
                            }
                            stocklist.setAdapter(new ItemListAdapter(ItemsListActivity.this, sliderimageslist));
                        }else{
                            barProgressDialog.dismiss();
                            final Dialog d = new Dialog(ItemsListActivity.this, android.R.style.Theme_DeviceDefault_Dialog_MinWidth);
                            d.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            d.setCancelable(false);
                            d.setContentView(R.layout.dialog_network);
                            d.show();
                            TextView alertmsg = (TextView) d.findViewById(R.id.alertMessage);
                            Button cancel = (Button) d.findViewById(R.id.networkCancel);
                            alertmsg.setText("Invalid Username Or Password!..,Please Try Again..");
                            cancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    d.dismiss();
                                }
                            });
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        barProgressDialog.dismiss();
                        Toast.makeText(ItemsListActivity.this, "Server Not Responding !..."+error.toString(), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                final Map<String, String> params = new HashMap<>();
                params.put("receiveid", Greceiveid);
                Log.e("params", "params--->" + params);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
