package com.mntsoft.hunterjeans.adapters;

/**
 * Created by root on 4/19/17.
 */

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.mntsoft.hunterjeans.BStockinhand;
import com.mntsoft.hunterjeans.BankDeposit;
import com.mntsoft.hunterjeans.BillingReport;
import com.mntsoft.hunterjeans.BranchDetailsListActivity;
import com.mntsoft.hunterjeans.Config;
import com.mntsoft.hunterjeans.LoginActivity;
import com.mntsoft.hunterjeans.R;
import com.mntsoft.hunterjeans.SalesReport;
import com.mntsoft.hunterjeans.StockReceive;
import com.mntsoft.hunterjeans.billing.ProductAddActivity;
import com.mntsoft.hunterjeans.helper.HttpCaller;
import com.mntsoft.hunterjeans.views.Helper;
import com.mntsoft.hunterjeans.views.PojoValues;
import com.mntsoft.hunterjeans.views.SharedPreferenceData;
import com.mntsoft.hunterjeans.views.TableCnsts;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class CustomAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    ArrayList<TableCnsts> result;
    ArrayList<String> gridColor;
    Context context;
    ArrayList<String> imageId;
    int width, height;
    BranchAdapter stateAdapter;
    ProgressDialog barProgressDialog;
    String Branchid, Branchname;
    SharedPreferenceData sharedPreferenceData;
    private int mYear, mMonth, mDay, i;
    private ArrayList<PojoValues> sliderimageslist;
    private HttpCaller mHttpCaller;
    private SharedPreferenceData mPreferenceData;

    public CustomAdapter(Context mainActivity, ArrayList<TableCnsts> prgmNameList) {
        // TODO Auto-generated constructor stub
        result = prgmNameList;
        context = mainActivity;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mHttpCaller = new HttpCaller(context);
        mPreferenceData = new SharedPreferenceData(context);

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder = new Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.product_item, null);
        holder.tv = (TextView) rowView.findViewById(R.id.textView1);
        //holder.tv1 = (TextView) rowView.findViewById(R.id.textView2);
        holder.img = (TextView) rowView.findViewById(R.id.imageView1);
        sharedPreferenceData = new SharedPreferenceData(context);
        final TableCnsts pojovalues = (TableCnsts) result.get(position);
        Typeface typeface = (Typeface.createFromAsset(context.getAssets(), "fontawesome.ttf"));
        Typeface fontface = (Typeface.createFromAsset(context.getAssets(), "Roboto.ttf"));
        holder.tv.setTypeface(fontface);

        String icon = pojovalues.getMenuicon();

        Log.e("icon", "icon-->" + icon);
        holder.tv.setText(pojovalues.getMenuName());
        holder.img.setText(Html.fromHtml(icon));
        holder.img.setTypeface(typeface);
        Drawable drawable = rowView.getBackground();
        drawable.setColorFilter(Color.parseColor(pojovalues.getItemcolor()), PorterDuff.Mode.MULTIPLY);
        rowView.setBackground(drawable);

//        rowView.setBackgroundColor(Color.parseColor(pojovalues.getItemcolor()));
        rowView.setTag(position);

        /*width = Helper.getDisplayWidth(context);
        height = Helper.getDisplayHeight(context);
        //Helper.getTypeFace(context, "barkentina.otf",);
        holder.img.getLayoutParams().width = Helper.getDisplayWidth(context)/3;
        holder.img.getLayoutParams().height = Helper.getDisplayHeight(context)/5;*/

       /* holder.img.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Haiahsbdfahbd", Toast.LENGTH_LONG).show();
                Intent next = new Intent(context, PackagesActivity.class);
                context.startActivity(next);
            }
        });*/

        rowView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub


               /* final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "Haiahsbdfahbd", Toast.LENGTH_LONG).show();
                        Intent next = new Intent(context, TestActivity.class);
                        context.startActivity(next);
                    }
                }, 900);*/
                //int position = (Integer) v.getTag();

                //String position = pojovalues.getMenuId();
                int position = Integer.parseInt(pojovalues.getMenuId());
                Log.e("position", "position-->" + position);
                if (position == 0) {
                    Intent next = new Intent(context, BranchDetailsListActivity.class);
                    context.startActivity(next);
                } else if (position == 1) {
                    ProductAddActivity.productResponse = null;
                    ProductAddActivity.backUpProduct = null;
                    Intent next = new Intent(context, ProductAddActivity.class);
                    context.startActivity(next);
                } else if (position == 2) {
                    context.startActivity(new Intent(context, SalesReport.class));
                } else if (position == 3) {
                    context.startActivity(new Intent(context, BillingReport.class));
                } /*else if (position == 4) {
                    Intent next = new Intent(context, WStockinhand.class);
                    next.putExtra("position", "4");
                    context.startActivity(next);

                }else if (position == 5) {
                    Intent next = new Intent(context, WStockinhand.class);
                    next.putExtra("position", "5");
                    context.startActivity(next);

                }*/ else if (position == 4) {
                    Intent billingint = new Intent(context, BStockinhand.class);
                    context.startActivity(billingint);
                } else if (position == 5) {
                    //Intent next = new Intent(context, PackagesActivity.class);
                    //context.startActivity(next);

                    barProgressDialog = new ProgressDialog(context);
                    barProgressDialog.setMessage("Loading please wait..");
                    final Dialog d = new Dialog(context, android.R.style.Theme_DeviceDefault_Dialog_MinWidth);
                    d.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    d.setCancelable(false);
                    d.setContentView(R.layout.dialog_billingreport);
                    d.show();
                    //final EditText edt_branchid = (EditText)d.findViewById(R.id.edt_branchid);
                    TextView dia_calender = (TextView) d.findViewById(R.id.dia_calender);
                    TextView dia_calender1 = (TextView) d.findViewById(R.id.dia_calender1);
                    TextView txt_submit = (TextView) d.findViewById(R.id.txt_submit);
                    TextView txt_cancle = (TextView) d.findViewById(R.id.txt_cancle);
                    final TextView dia_sdate = (TextView) d.findViewById(R.id.dia_sdate);
                    final TextView dia_edate = (TextView) d.findViewById(R.id.dia_edate);
                    LinearLayout ll_sdate = (LinearLayout) d.findViewById(R.id.ll_sdate);
                    LinearLayout ll_edate = (LinearLayout) d.findViewById(R.id.ll_edate);
                    final Spinner spinner = (Spinner) d.findViewById(R.id.spin_branchname);
                    //getState("http://denimhub.mysalesinfo.co.in/services/getbrancheslist.htm",spinner);
                    getBranch(spinner);
                    Helper.getTypeFace(context, "fontawesome.ttf", dia_calender);
                    Helper.getTypeFace(context, "fontawesome.ttf", dia_calender1);

                    ll_sdate.setVisibility(View.GONE);
                    ll_edate.setVisibility(View.GONE);

                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            PojoValues model = (PojoValues) stateAdapter.getItem(position);
                            Branchid = model.getBranchId();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                    txt_submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String getbranchid = Branchid;
                            if (getbranchid.equalsIgnoreCase("0")) {
                                final Dialog d = new Dialog(context, android.R.style.Theme_DeviceDefault_Dialog_MinWidth);
                                d.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                d.setCancelable(false);
                                d.setContentView(R.layout.dialog_network);
                                d.show();
                                TextView alertmsg = (TextView) d.findViewById(R.id.alertMessage);
                                Button cancel = (Button) d.findViewById(R.id.networkCancel);
                                alertmsg.setText("Please Select Your Branch....");
                                cancel.setText("Ok");
                                cancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        d.dismiss();
                                    }
                                });
                            } else {
                                if (getbranchid.equalsIgnoreCase("")) {
                                    Toast.makeText(context, "Please Enter All Fields...", Toast.LENGTH_SHORT).show();
                                } else {
                                    if (getbranchid.equalsIgnoreCase("0")) {
                                        String newid = "All";
                                        Intent billingint = new Intent(context, StockReceive.class);
                                        billingint.putExtra("BRANCHID", newid);
                                        context.startActivity(billingint);
                                        d.dismiss();
                                    } else {
                                        Intent billingint = new Intent(context, StockReceive.class);
                                        billingint.putExtra("BRANCHID", getbranchid);
                                        context.startActivity(billingint);
                                        d.dismiss();
                                    }
                                }
                            }
                        }
                    });
                    txt_cancle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            d.dismiss();
                        }
                    });
                } else if (position == 8) {
                    barProgressDialog = new ProgressDialog(context);
                    barProgressDialog.setMessage("Loading please wait..");
                    final Dialog d = new Dialog(context, android.R.style.Theme_DeviceDefault_Dialog_MinWidth);
                    d.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    d.setCancelable(false);
                    d.setContentView(R.layout.dialog_billingreport);
                    d.show();
                    //final EditText edt_branchid = (EditText)d.findViewById(R.id.edt_branchid);
                    TextView dia_calender = (TextView) d.findViewById(R.id.dia_calender);
                    TextView dia_calender1 = (TextView) d.findViewById(R.id.dia_calender1);
                    TextView txt_submit = (TextView) d.findViewById(R.id.txt_submit);
                    TextView txt_cancle = (TextView) d.findViewById(R.id.txt_cancle);
                    final TextView dia_sdate = (TextView) d.findViewById(R.id.dia_sdate);
                    final TextView dia_edate = (TextView) d.findViewById(R.id.dia_edate);
                    LinearLayout ll_sdate = (LinearLayout) d.findViewById(R.id.ll_sdate);
                    LinearLayout ll_edate = (LinearLayout) d.findViewById(R.id.ll_edate);
                    final Spinner spinner = (Spinner) d.findViewById(R.id.spin_branchname);
                    //getState("http://denimhub.mysalesinfo.co.in/services/getbrancheslist.htm",spinner);
                    getBranch(spinner);
                    Helper.getTypeFace(context, "fontawesome.ttf", dia_calender);
                    Helper.getTypeFace(context, "fontawesome.ttf", dia_calender1);

                    ll_sdate.setVisibility(View.GONE);
                    ll_edate.setVisibility(View.GONE);

                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            PojoValues model = (PojoValues) stateAdapter.getItem(position);
                            Branchid = model.getBranchId();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                    txt_submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String getbranchid = Branchid;
                            if (getbranchid.equalsIgnoreCase("0")) {
                                final Dialog d = new Dialog(context, android.R.style.Theme_DeviceDefault_Dialog_MinWidth);
                                d.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                d.setCancelable(false);
                                d.setContentView(R.layout.dialog_network);
                                d.show();
                                TextView alertmsg = (TextView) d.findViewById(R.id.alertMessage);
                                Button cancel = (Button) d.findViewById(R.id.networkCancel);
                                alertmsg.setText("Please Select Your Branch....");
                                cancel.setText("Ok");
                                cancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        d.dismiss();
                                    }
                                });
                            } else {
                                if (getbranchid.equalsIgnoreCase("")) {
                                    Toast.makeText(context, "Please Enter All Fields...", Toast.LENGTH_SHORT).show();
                                } else {
                                    if (getbranchid.equalsIgnoreCase("0")) {
                                        String newid = "All";
                                        Intent billingint = new Intent(context, BankDeposit.class);
                                        billingint.putExtra("BRANCHID", newid);
                                        context.startActivity(billingint);
                                        d.dismiss();
                                    } else {
                                        Intent billingint = new Intent(context, BankDeposit.class);
                                        billingint.putExtra("BRANCHID", getbranchid);
                                        context.startActivity(billingint);
                                        d.dismiss();
                                    }
                                }
                            }
                        }
                    });
                    txt_cancle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            d.dismiss();
                        }
                    });
                }


            }
        });

        return rowView;
    }


    private void showDeniedDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setMessage("Please contact Management for app access. ...");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog

                mPreferenceData.setLogged(false);
                context.startActivity(new Intent(context, LoginActivity.class));
            }
        });

        builder.setCancelable(false);
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void getBranch(final Spinner spinner) {
        barProgressDialog.show();
        sliderimageslist = new ArrayList<PojoValues>();
        JsonArrayRequest req = new JsonArrayRequest(Config.BRANCH_LIST + "?userid=" + sharedPreferenceData.getUserId() + "&rollid=" + sharedPreferenceData.getRollId(),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("response", "response--->" + response.toString());

                        // Parsing json response and iterate over each JSON object
                        //Log.d(TAG, "data:" + responseText);
                        try {
                            sliderimageslist = new ArrayList<PojoValues>();
                            PojoValues wedService = new PojoValues();
                            wedService.setBranchId("0");
                            wedService.setBranchName("Select Branch");
                            sliderimageslist.add(wedService);

                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonobject = response.getJSONObject(i);
                                String branchId = jsonobject.getString("branchId");
                                String branchName = jsonobject.getString("branchName");
                                //Country countryObj = new Country(id, country);
                                //countries.add(countryObj);

                                PojoValues villageconstants = new PojoValues();
                                villageconstants.setBranchName(branchName);
                                villageconstants.setBranchId(branchId);

                                sliderimageslist.add(villageconstants);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(context,
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                        barProgressDialog.dismiss();
                        stateAdapter = new BranchAdapter(context, sliderimageslist);
                        spinner.setAdapter(stateAdapter);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
            }

        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(req);
    }

    private void getState(String statesUrl, final Spinner spinner) {
        Log.e("statesUrl", "statesUrl--." + statesUrl);
        barProgressDialog.show();
        sliderimageslist = new ArrayList<PojoValues>();
        JsonArrayRequest req = new JsonArrayRequest(statesUrl,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("response", "response--->" + response.toString());

                        // Parsing json response and iterate over each JSON object
                        //Log.d(TAG, "data:" + responseText);
                        try {
                            sliderimageslist = new ArrayList<PojoValues>();
                            PojoValues wedService = new PojoValues();
                            wedService.setBranchId("0");
                            wedService.setBranchName("All Branchs");
                            sliderimageslist.add(wedService);

                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonobject = response.getJSONObject(i);
                                String branchId = jsonobject.getString("branchId");
                                String branchName = jsonobject.getString("branchName");
                                //Country countryObj = new Country(id, country);
                                //countries.add(countryObj);

                                PojoValues villageconstants = new PojoValues();
                                villageconstants.setBranchName(branchName);
                                villageconstants.setBranchId(branchId);

                                sliderimageslist.add(villageconstants);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(context,
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                        barProgressDialog.dismiss();
                        stateAdapter = new BranchAdapter(context, sliderimageslist);
                        spinner.setAdapter(stateAdapter);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
            }

        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(req);
    }

    public class Holder {
        TextView tv, tv1;
        TextView img;
    }

}
