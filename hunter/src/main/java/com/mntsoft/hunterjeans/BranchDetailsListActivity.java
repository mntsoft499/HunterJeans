package com.mntsoft.hunterjeans;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.mntsoft.hunterjeans.adapters.BranchReportAdapter;
import com.mntsoft.hunterjeans.model.BranchReportResponse;
import com.mntsoft.hunterjeans.utils.AppConstants;
import com.mntsoft.hunterjeans.utils.LOG;
import com.mntsoft.hunterjeans.utils.Util;
import com.mntsoft.hunterjeans.views.SharedPreferenceData;
import com.mntsoft.hunterjeans.volley.VolleyController;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by surya on 12/1/16.
 */

public class BranchDetailsListActivity extends BaseActivity {

    private static final String TAG = "BranchDetailsListActivity";
    ProgressDialog barProgressDialog;
    String responseText;
    private RecyclerView packageslistview;
    private ArrayList<BranchReportResponse> sliderimageslist;
    private TextView totalbranchs;
    private SharedPreferenceData preferenceData;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_packages, frag_frame);
        logout.setVisibility(View.INVISIBLE);
        barProgressDialog = new ProgressDialog(this);
        barProgressDialog.setMessage("Loading please wait..");
        preferenceData = new SharedPreferenceData(this);

        packageslistview = (RecyclerView) findViewById(R.id.packageslistview);
        packageslistview.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        packageslistview.setLayoutManager(linearLayoutManager);
        totalbranchs = (TextView) findViewById(R.id.totalbranchs);
        gson = new Gson();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getBranchDetailsList();
    }

    private void getBranchDetailsList() {
        barProgressDialog.show();
        LOG.e(TAG, "getBranchDetailsList() : " + "userid=" + preferenceData.getUserId() + "&rollid=" + preferenceData.getRollId());
        sliderimageslist = new ArrayList<>();
        JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, Config.BRANCH_REPORT_URL + "?userid=" + preferenceData.getUserId(), null,
                new Response.Listener<JSONArray>() {
                    @SuppressLint("LongLogTag")
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());    ///"stock_alloc_Id": "",
                        Log.d(TAG, "data:" + responseText);
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                sliderimageslist.add(gson.fromJson(response.getJSONObject(i).toString(), BranchReportResponse.class));
                            }
//                            Collections.sort(sliderimageslist, new Comparator<BranchReportResponse>() {
//                                @Override
//                                public int compare(BranchReportResponse pojoValues1, BranchReportResponse pojoValues2) {
//                                    return pojoValues1.getBranchName().compareTo(pojoValues2.getBranchName());
//                                }
//                            });
                            totalbranchs.setText(String.valueOf(response.length()));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                        barProgressDialog.dismiss();
                        packageslistview.setAdapter(new BranchReportAdapter(BranchDetailsListActivity.this, sliderimageslist));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Util.showToast(BranchDetailsListActivity.this, error.getMessage(), AppConstants.COLOR_RED, Toast.LENGTH_LONG);

            }

        });

        VolleyController.getInstance().addToRequestQueue(req);
    }
}
