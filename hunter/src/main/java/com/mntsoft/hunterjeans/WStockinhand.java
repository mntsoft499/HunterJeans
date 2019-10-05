package com.mntsoft.hunterjeans;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.mntsoft.hunterjeans.adapters.WStockadapter;
import com.mntsoft.hunterjeans.model.BStockResponse;
import com.mntsoft.hunterjeans.utils.AppConstants;
import com.mntsoft.hunterjeans.utils.StringUtils;
import com.mntsoft.hunterjeans.utils.Util;
import com.mntsoft.hunterjeans.volley.VolleyController;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by root on 11/4/17.
 */
public class WStockinhand extends BaseActivity {
    private static final String TAG = "WStockinhand";
//    String Gbranchid,GSdate,Gedate;
    private ArrayList<BStockResponse> sliderimageslist;
    private TextView totalbills,txt_footer;
    ProgressDialog barProgressDialog;
    Double total= 0.00;
    private RecyclerView recyclerView;
    private Gson gson;
    private String position;
    private boolean isProductWiseFilter;
    private EditText selectCategory;
    private LinearLayout product_container, category_container,titlesContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_billingreport, frag_frame);
        logout.setVisibility(View.INVISIBLE);
        barProgressDialog = new ProgressDialog(WStockinhand.this);
        barProgressDialog.setMessage("Loading Bills please wait..");
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        totalbills = (TextView)findViewById(R.id.totalbills);
        txt_footer = (TextView)findViewById(R.id.txt_footer);
        txt_footer.setText("Total Quantity ");
        titlesContainer = findViewById(R.id.titles_container);
        product_container = findViewById(R.id.product_wise_container);
        category_container = findViewById(R.id.category_wise_container);

        LinearLayout branchPickerContainer = findViewById(R.id.branch_picker_container);
        branchPickerContainer.setWeightSum(1.0f);
        findViewById(R.id.input_layout_select_branch).setVisibility(View.GONE);

        selectCategory= (EditText) findViewById(R.id.select_filter);

        position = (String) getIntent().getStringExtra("position");

        String title = StringUtils.isEqualsIgnoreCase(position, "4") ? "WH1 Stock in Hand" : "WH2 Stock in Hand";
        ((TextView)findViewById(R.id.heading)).setText(title);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        final String[] filterList = {"Category Wise","Product Wise"};

        selectCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(WStockinhand.this)
                        .setTitle("Please select option")
                        .setCancelable(false)
                        .setSingleChoiceItems(filterList, -1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                isProductWiseFilter = which != 0;
                                selectCategory.setText(filterList[which]);
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });

        findViewById(R.id.txt_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringUtils.isEmpty(selectCategory.getText().toString().trim())) {
                    Util.showToast(WStockinhand.this, "Field can't be empty", AppConstants.COLOR_RED);
                    return;
                }
                titlesContainer.setVisibility(View.GONE);
                gson = new Gson();
                String URL;
                if (StringUtils.isEqualsIgnoreCase(position, "4")) {
                    URL = isProductWiseFilter ? Config.WH1_STOCK_REPORT_PRODUCT_URL : Config.WH1_STOCK_REPORT_CATEGORY_URL;
                } else {
                    URL = isProductWiseFilter ? Config.WH2_STOCK_REPORT_PRODUCT_URL : Config.WH2_STOCK_REPORT_CATEGORY_URL;
                }
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(WStockinhand.this));
                postBillDetails(URL);
            }
        });

//        postBillDetails(Config.W_STOCK_REPORT_URL);

    }

    protected void postBillDetails(String url) {
        barProgressDialog.show();
        sliderimageslist = new ArrayList<>();
        JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());

                        // Parsing json response and iterate over each JSON object
                        try {
                            if (response.length() == 0) {
                                Util.showToast(WStockinhand.this, "No Record Found !", AppConstants.COLOR_ORANGE, Toast.LENGTH_LONG);
                            }
                            total = 0.0;
                            for (int i = 0; i < response.length(); i++) {
                                sliderimageslist.add(gson.fromJson(response.getJSONObject(i).toString(), BStockResponse.class));
                                total = total + Double.parseDouble(sliderimageslist.get(i).getQuantity());
                            }
                            totalbills.setText(String.valueOf(total));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                        barProgressDialog.dismiss();
                        titlesContainer.setVisibility(View.VISIBLE);
                        if (isProductWiseFilter) {
                            product_container.setVisibility(View.VISIBLE);
                            category_container.setVisibility(View.GONE);
                        } else {
                            product_container.setVisibility(View.GONE);
                            category_container.setVisibility(View.VISIBLE);
                        }

                        recyclerView.setAdapter(new WStockadapter(WStockinhand.this, sliderimageslist,isProductWiseFilter));

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(WStockinhand.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }

        });
        VolleyController.getInstance().addToRequestQueue(req);
    }
}
