package com.mntsoft.hunterjeans.billing;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.mntsoft.hunterjeans.BaseActivity;
import com.mntsoft.hunterjeans.Config;
import com.mntsoft.hunterjeans.LoginActivity;
import com.mntsoft.hunterjeans.MainActivity;
import com.mntsoft.hunterjeans.R;
import com.mntsoft.hunterjeans.ScanActivity;
import com.mntsoft.hunterjeans.application.AppController;
import com.mntsoft.hunterjeans.customviews.CustomAlertBox;
import com.mntsoft.hunterjeans.helper.HttpCaller;
import com.mntsoft.hunterjeans.model.Clist;
import com.mntsoft.hunterjeans.model.Product;
import com.mntsoft.hunterjeans.utils.AppConstants;
import com.mntsoft.hunterjeans.utils.LOG;
import com.mntsoft.hunterjeans.utils.StringUtils;
import com.mntsoft.hunterjeans.utils.Util;
import com.mntsoft.hunterjeans.views.SharedPreferenceData;
import com.mntsoft.hunterjeans.volley.VolleyController;
import com.mntsoft.hunterjeans.volley.volleyCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProductAddActivity extends BaseActivity {
    public static Product productResponse = null;
    public static Product backUpProduct = null;

    private static final String TAG = "ProductAddActivity";

    private SharedPreferenceData preferenceData;
    private AppCompatTextView h;
    private AppCompatButton next;
    private AppCompatButton cancelBilling;
    private TextView netAmount;
    private RecyclerView productList;
    private LinearLayout scanButton;
    private AppCompatTextView branchName;
    private ProductListAdapter mAdapter;
    private LinearLayout undoLastScan;
    private HttpCaller mHttpCaller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_biling_list, frag_frame);
        preferenceData = new SharedPreferenceData(this);
        next = (AppCompatButton)findViewById(R.id.next);
        cancelBilling = (AppCompatButton)findViewById(R.id.cancel_billing);
        netAmount = (TextView) findViewById(R.id.net_amount);
        productList = (RecyclerView) findViewById(R.id.product_list);
        scanButton = (LinearLayout)findViewById(R.id.scan_button);
        branchName = (AppCompatTextView)findViewById(R.id.branch_name);
        undoLastScan = findViewById(R.id.undo_last_scan);
        cancelBilling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlert();

            }
        });
        undoLastScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                undoLastScanItem();
            }
        });

        mHttpCaller = new HttpCaller(this);

        getUserData();

        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProductAddActivity.this, ScanActivity.class));
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (productResponse != null) {
                    Intent intent = new Intent(ProductAddActivity.this, BillingActivity.class);
                    startActivity(intent);
                } else {
                    Util.showToast(AppController.getInstance(), "Please scan a item ", AppConstants.COLOR_ORANGE, Toast.LENGTH_LONG);
                }
            }
        });
        getBranchName();
        showActivity();
//        enableSwipeToDeleteAndUndo();
    }

    public void showAlert() {
        CustomAlertBox builder = new CustomAlertBox(this);
        builder.setMessage("Are you sure you want to cancel billing ?");
        builder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(ProductAddActivity.this, MainActivity.class));
                        productResponse = null;
                        backUpProduct = null;
                    }
                });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    @Override
    public void onBackPressed() {
        showAlert();
    }

    @Override
    protected void onResume() {
        super.onResume();
        showActivity();
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

    private void undoLastScanItem() {
//        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(this) {
//            @Override
//            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
//                final int position = viewHolder.getAdapterPosition();
//                final Clist item = mAdapter.getData().get(position);
//                mAdapter.removeItem(position);
        productResponse = backUpProduct;
        backUpProduct = null;
        showActivity();
                /*Snackbar snackbar = Snackbar
                        .make(coordinatorLayout, "Item was removed from the list.", Snackbar.LENGTH_LONG);
                snackbar.setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        mAdapter.restoreItem(item, position);
                        recyclerView.scrollToPosition(position);
                    }
                });

                snackbar.setActionTextColor(Color.YELLOW);
                snackbar.show();*/

//            }
//        };

//        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
//        itemTouchhelper.attachToRecyclerView(productList);
    }


    private void getBranchName() {
        showProgressDialog();
        StringRequest stringRequest=new StringRequest(Request.Method.GET,Config.BRANCH_INFO_URL + "?userid=" + preferenceData.getUserId() + "&rollid=" + preferenceData.getRollId(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                LOG.i(TAG, "getBranchName() : onResponse() : " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    preferenceData.setbranchName(jsonObject.optString("branchName", ""));
                    preferenceData.setbranchid(jsonObject.optString("branchId", ""));
                } catch (JSONException e) {
                    e.printStackTrace();
                    LOG.e(TAG, "getBranchName() : onResponse() : JSONException : " + e);
                }
                showActivity();
                dismissProgressDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                LOG.i(TAG, "getBranchName() : onResponse() : " + error.getMessage());
            }
        });
        VolleyController.getInstance().addToRequestQueue(stringRequest);

    }

    private void showActivity() {
        LOG.e(TAG, "showActivity() : isProduct response null : " + productResponse);
        if (/*backUpProduct != null*/false) {   /// remove this false and un-comment the if condition to enable undo scan functionality
            Drawable d = undoLastScan.getBackground();
            d.setColorFilter(this.getResources().getColor(R.color.darkgray), PorterDuff.Mode.SRC_IN);
            undoLastScan.setBackground(d);
            undoLastScan.setVisibility(View.VISIBLE);
        }else {
            undoLastScan.setVisibility(View.GONE);
        }

        productList.setHasFixedSize(true);
        productList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ProductListAdapter(this);
        productList.setAdapter(mAdapter);
        branchName.setText(StringUtils.concatWithDots(preferenceData.getBranchName(), 16));
        netAmount.setText(productResponse == null ? "0" : "" + Math.round(Util.getFloatFromString(productResponse.getNetamount())));
    }

    private class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductViewHolder> {

        private Context context;
        private List<Clist> data ;

        ProductListAdapter(Context context) {
            this.context = context;
            data = productResponse == null ? new ArrayList<Clist>() : productResponse.getClist();
            if (data == null) {
                data = new ArrayList<>();
            }
            LOG.e(TAG, "showActivity() : data : " + data);

        }
        class ProductViewHolder extends RecyclerView.ViewHolder{

            public TextView productName,salePrice,quantity, amount;

            public ProductViewHolder(@NonNull View itemView) {
                super(itemView);
                productName = itemView.findViewById(R.id.product_name);
                salePrice = itemView.findViewById(R.id.sale_price);
                quantity = itemView.findViewById(R.id.quantity);
                amount = itemView.findViewById(R.id.amount);
            }
        }

        @NonNull
        @Override
        public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            return new ProductViewHolder(((LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.product_list_row, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ProductViewHolder viewHolder, int i) {
            viewHolder.productName.setText(data.get(i).getProductdescription());
            viewHolder.salePrice.setText(data.get(i).getSaleprice());
            viewHolder.quantity.setText(data.get(i).getQuantity());
//            viewHolder.amount.setText(data.get(i).getAmountwithdiscount());
            viewHolder.amount.setText(String.valueOf(Math.round(Util.getFloatFromString(data.get(i).getAmountwithdiscount()))));
//            viewHolder.amount.setText(String.valueOf(Double.valueOf(new DecimalFormat("#.###").format(data.get(i).getAmountwithdiscount()))));
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
        public void removeItem(int position) {
            data.remove(position);
            notifyItemRemoved(position);
        }

        public void restoreItem(Clist item, int position) {
            data.add(position, item);
            notifyItemInserted(position);
        }

        public List<Clist> getData() {
            return data;
        }
    }
}
