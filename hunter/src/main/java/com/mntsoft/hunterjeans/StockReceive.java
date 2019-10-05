package com.mntsoft.hunterjeans;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.mntsoft.hunterjeans.adapters.StockListAdapter;
import com.mntsoft.hunterjeans.views.StockCnts;
import com.mntsoft.hunterjeans.volley.VolleyController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by root on 2/1/18.
 */
public class StockReceive extends BaseActivity {
    String Gbranchid;
    private ArrayList<StockCnts> sliderimageslist;
    private ListView stocklist;
    ProgressDialog barProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_stockreceive, frag_frame);
        barProgressDialog = new ProgressDialog(StockReceive.this);
        barProgressDialog.setMessage("Loading please wait..");
        logout.setVisibility(View.INVISIBLE);
        Gbranchid = getIntent().getStringExtra("BRANCHID");
        stocklist = (ListView)findViewById(R.id.stocklist);

        getstockbydate("http://denimhub.mysalesinfo.co.in/services/getAndriodStockAllocatedDetails.htm",Gbranchid);

    }

    private void getstockbydate(String url, final String Gbranchid) {
        sliderimageslist = new ArrayList<StockCnts>();
        barProgressDialog.show();
        barProgressDialog.setCancelable(false);
        final Map<String, String> postParameters = new HashMap<String, String>();
        //postParameters.put("username", getempid);
        //postParameters.put("password", getpassword);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Response1234==>", response);
                        if (response.length() != 0) {
                            try {

                                JSONArray jsonArray = new JSONArray(response);
                                Log.e("jsonArray","jsonArray--->"+jsonArray);
                                //JSONObject jsonObject = new JSONObject(response);
                                //Log.e("jsonObject","jsonObject--->"+jsonObject.toString());
                                for(int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String receiveid = jsonObject.getString("receiveid");
                                    String branchid = jsonObject.getString("branchid");
                                    String allocateddate = jsonObject.getString("allocateddate");
                                    Log.e("1234","1234===>"+receiveid+"--"+branchid+"--"+allocateddate);

                                    StockCnts stockCnts = new StockCnts();
                                    stockCnts.setReceiveid(receiveid);
                                    stockCnts.setBranchid(branchid);
                                    stockCnts.setAllocateddate(allocateddate);
                                    sliderimageslist.add(stockCnts);

                                }
                                barProgressDialog.dismiss();

                            } catch (JSONException e) {
                                barProgressDialog.dismiss();
                                e.printStackTrace();
                                Toast.makeText(StockReceive.this,
                                        "Error: " + e.getMessage(),
                                        Toast.LENGTH_LONG).show();
                                Log.e("JSONException", "JSONException--->" + e.toString());
                            }
                            stocklist.setAdapter(new StockListAdapter(StockReceive.this, sliderimageslist));
                        }else{
                            barProgressDialog.dismiss();
                            final Dialog d = new Dialog(StockReceive.this, android.R.style.Theme_DeviceDefault_Dialog_MinWidth);
                            d.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            d.setCancelable(false);
                            d.setContentView(R.layout.dialog_network);
                            d.show();
                            TextView alertmsg = (TextView) d.findViewById(R.id.alertMessage);
                            Button cancel = (Button) d.findViewById(R.id.networkCancel);
                            alertmsg.setText("No Records Found!....");
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
                        Toast.makeText(StockReceive.this, "Server Not Responding !..."+error.toString(), Toast.LENGTH_SHORT).show();
                        //Toast.makeText(LoginActivity.this, "Thank U for Shopping ", Toast.LENGTH_LONG).show();
                        finish();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                final Map<String, String> params = new HashMap<>();

                params.put("branchid", Gbranchid);
                //params.put("username", getempid);
                //params.put("password", getpassword);
                Log.e("params", "params--->" + params);
                return params;
            }

        };

        VolleyController.getInstance().addToRequestQueue(stringRequest);
    }
}
