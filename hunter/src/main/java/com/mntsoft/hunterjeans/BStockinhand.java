package com.mntsoft.hunterjeans;

import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import com.mntsoft.hunterjeans.utils.LOG;
import com.mntsoft.hunterjeans.utils.StringUtils;
import com.mntsoft.hunterjeans.utils.Util;
import com.mntsoft.hunterjeans.volley.VolleyController;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by root on 11/4/17.
 */
public class BStockinhand extends BaseActivity {
    private static final String TAG = "BStockinhand";
    String Gbranchid, GSdate, Gedate;
    private ArrayList<BStockResponse> sliderimageslist;
    private TextView totalbills, txt_footer;
    ProgressDialog barProgressDialog;
    Double total = 0.00;
    private Gson gson;
    private RecyclerView recyclerView;
    private EditText startDate, endDate, selectCategory;
    private TextView submit;
    private boolean isProductWiseFilter;
    private String branchid;
    private LinearLayout titlesContainer;
    public TextView description, barcode, productQuantity, categoryName, categoryQuantity;
    public LinearLayout product_container, category_container;
    private AppCompatAutoCompleteTextView selectBranch;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_billingreport, frag_frame);
        logout.setVisibility(View.INVISIBLE);
        barProgressDialog = new ProgressDialog(BStockinhand.this);
        barProgressDialog.setMessage("Loading Bills please wait..");
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        totalbills = (TextView) findViewById(R.id.totalbills);
        txt_footer = (TextView) findViewById(R.id.txt_footer);
        txt_footer.setText("Total Quantity ");


        final String[] filterList = {"Category Wise", "Product Wise"};

        startDate = (EditText) findViewById(R.id.start_date);
        endDate = (EditText) findViewById(R.id.end_date);

        selectBranch = (AppCompatAutoCompleteTextView) findViewById(R.id.select_branch);
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
                    LOG.e("Bikash", s.length() + "");
                    Util.hideKeyboard(BStockinhand.this);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        selectCategory = (EditText) findViewById(R.id.select_filter);
        submit = (TextView) findViewById(R.id.txt_submit);
        titlesContainer = (LinearLayout) findViewById(R.id.titles_container);
        product_container = findViewById(R.id.product_wise_container);
        category_container = findViewById(R.id.category_wise_container);
        ((TextView) findViewById(R.id.heading)).setText("B-Stock in Hand");
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.hideKeyboard(BStockinhand.this);
                String selectedBranchName = selectBranch.getText().toString();
                for (int i=0;i<MainActivity.branchNameList.length;i++) {
                    if (StringUtils.isEqualsIgnoreCase(selectedBranchName, MainActivity.branchNameList[i])) {
                        branchid = MainActivity.branchIdList[i];
                        break;
                    }
                }
                if (StringUtils.isEmpty(branchid)) {
                    Util.showToast(BStockinhand.this, "Please select valid branch name ", AppConstants.COLOR_ORANGE);
                    return;
                }
                if (StringUtils.isEmpty(selectCategory.getText().toString().trim())) {
                    Util.showToast(BStockinhand.this, "Category field can't be empty ", AppConstants.COLOR_RED);
                    return;
                }
                titlesContainer.setVisibility(View.GONE);
                gson = new Gson();
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(BStockinhand.this));
                postBillDetails((isProductWiseFilter ? Config.B_STOCK_REPORT_PRODUCT_URL : Config.B_STOCK_REPORT_CATEGORY_URL) + "?userid=" + sharedPreferenceData.getUserId() + "&branchId=" + branchid);
            }
        });

//        selectBranch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                new AlertDialog.Builder(BStockinhand.this)
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
        selectCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.hideKeyboard(BStockinhand.this);
                new AlertDialog.Builder(BStockinhand.this)
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
    }

    protected Void postBillDetails(String url) {
        LOG.e(TAG, "postBillDetails() : url : " + url);
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
                                Util.showToast(BStockinhand.this, "No Record Found !", AppConstants.COLOR_ORANGE, Toast.LENGTH_LONG);
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
                        recyclerView.setAdapter(new WStockadapter(BStockinhand.this, sliderimageslist, isProductWiseFilter));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(BStockinhand.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }

        });
        VolleyController.getInstance().addToRequestQueue(req);
        return null;
    }
}
