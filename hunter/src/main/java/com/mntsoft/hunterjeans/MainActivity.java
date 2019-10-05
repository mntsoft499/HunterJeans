package com.mntsoft.hunterjeans;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.mntsoft.hunterjeans.adapters.CustomAdapter;
import com.mntsoft.hunterjeans.application.AppController;
import com.mntsoft.hunterjeans.customviews.CustomAlertBox;
import com.mntsoft.hunterjeans.utils.AppConstants;
import com.mntsoft.hunterjeans.utils.LOG;
import com.mntsoft.hunterjeans.utils.StringUtils;
import com.mntsoft.hunterjeans.utils.Util;
import com.mntsoft.hunterjeans.views.TableCnsts;
import com.mntsoft.hunterjeans.volley.VolleyController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int CAMERA_PERMISSION = 1;
    private static boolean showAlert = false;
    public static String Menulist;
    ArrayList<TableCnsts> arrayList;
    ArrayList<String> arrayList1;
    ArrayList<String> arrayList2;
    ArrayList<String> arrayList3;
    private LinearLayout llmessage, lldocuments, llapplications, llattendance, llpersonalinfo, lladmin;
    private TextView txt_chmessage, txt_quote, fontadmin, fontpersonal, fontattendance, fontapplication, fontdocuments, fontmessage;
    private GridView gv;
    public static String[] branchNameList = new String[1];
    public static String[] branchIdList=new String[1];
    private int backPressCount;

    @Override
    public void onBackPressed() {
        if (backPressCount == 0) {
            backPressCount++;
            Util.showToast(this, "Please click again to exit", AppConstants.COLOR_BLUE);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_main, frag_frame);
        logout.setVisibility(View.INVISIBLE);
        gv = (GridView) findViewById(R.id.gridView1);
        txt_quote = (TextView) findViewById(R.id.txt_quote);
        fontadmin = (TextView) findViewById(R.id.fontadmin);
        fontpersonal = (TextView) findViewById(R.id.fontpersonal);
        fontattendance = (TextView) findViewById(R.id.fontattendance);
        fontapplication = (TextView) findViewById(R.id.fontapplication);
        fontdocuments = (TextView) findViewById(R.id.fontdocuments);
        fontmessage = (TextView) findViewById(R.id.fontmessage);
        lladmin = (LinearLayout) findViewById(R.id.lladmin);
        llpersonalinfo = (LinearLayout) findViewById(R.id.llpersonalinfo);
        llattendance = (LinearLayout) findViewById(R.id.llattendance);
        llapplications = (LinearLayout) findViewById(R.id.llapplications);
        lldocuments = (LinearLayout) findViewById(R.id.lldocuments);
        llmessage = (LinearLayout) findViewById(R.id.llmessage);
        txt_quote.setSelected(true);
        txt_quote.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        txt_quote.setSingleLine(true);
//        Menulist = getIntent().getStringExtra("clist");
        Log.e("Menulist", "Menulist---->" + Menulist);

        //gv.setAdapter(new CustomAdapter(MainActivity.this, prgmNameList, prgmImages, gridColor));


        Typeface typeface = (Typeface.createFromAsset(getAssets(), "fontawesome.ttf"));
        fontadmin.setTypeface(typeface);
        fontpersonal.setTypeface(typeface);
        fontattendance.setTypeface(typeface);
        fontapplication.setTypeface(typeface);
        fontdocuments.setTypeface(typeface);
        fontmessage.setTypeface(typeface);

        try {
            arrayList = new ArrayList<>();
            arrayList1 = new ArrayList<>();
            arrayList2 = new ArrayList<>();
            arrayList3 = new ArrayList<>();
            JSONArray jsonArray = StringUtils.isEmpty(Menulist) ? (JSONArray) new JSONObject(Util.loadJSONFromAsset("dummy_clist.json", getApplicationContext())).get("clist") : new JSONArray(Menulist);
            LOG.i(TAG, "clist : " + jsonArray);
            if (jsonArray != null) {
                for (int i = 0; i < /*jsonArray.length()*/7; i++) {
                    if (!addToList(i)) {
//                        continue;
                    }
                    try {
                        JSONObject job = jsonArray.getJSONObject(i);
                        String menuId = job.getString("menuId");
                        String menuName = job.getString("menuName");
                        String menuicon = job.getString("menuicon");
                        String itemcolor = job.getString("itemcolor");
                        TableCnsts tableCnsts = new TableCnsts();
                        tableCnsts.setMenuId(menuId);
                        tableCnsts.setMenuName(menuName);
                        tableCnsts.setMenuicon(menuicon);
                        tableCnsts.setItemcolor(itemcolor);
                        arrayList.add(tableCnsts);
                        Log.e("List", "List--->" + arrayList);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    gv.setAdapter(new CustomAdapter(MainActivity.this, arrayList));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION);
            }
        }
        showAlert = false;
        getBranch();
    }
    private void getBranch() {
//        barProgressDialog.show();
        JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET,Config.BRANCH_LIST + "?userid=" + sharedPreferenceData.getUserId() + "&rollid=" + sharedPreferenceData.getRollId(),null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        dismissProgressDialog();
                        Log.e("response", "response--->" + response.toString());

                        // Parsing json response and iterate over each JSON object
                        //Log.d(TAG, "data:" + responseText);
                        try {
                            branchNameList = new String[response.length()];
                            branchIdList = new String[response.length()];

                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonobject = response.getJSONObject(i);
                                branchNameList[i] = jsonobject.getString("branchName");
                                branchIdList[i] = jsonobject.getString("branchId");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
//                            Toast.makeText(BStockinhand.this,
//                                    "Error: " + e.getMessage(),
//                                    Toast.LENGTH_LONG).show();
                        }
//                        stateAdapter = new BranchAdapter(BStockinhand.this, sliderimageslist);
//                        spinner.setAdapter(stateAdapter);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(BStockinhand.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }

        });
        VolleyController.getInstance().addToRequestQueue(req);
    }

    private boolean addToList(int i) {
        if (i == 1) {
            if (StringUtils.isEqualsIgnoreCase("R0002", sharedPreferenceData.getRollId())) {
                return false;
            } else if (StringUtils.isEqualsIgnoreCase("R0005", sharedPreferenceData.getRollId())) {
                return false;
            } else if (StringUtils.isEqualsIgnoreCase("R0006", sharedPreferenceData.getRollId())) {
                return false;
            } else
                return !StringUtils.isEqualsIgnoreCase("R0007", sharedPreferenceData.getRollId());
        } else if (i == 4) {
            if (StringUtils.isEqualsIgnoreCase("R0006", sharedPreferenceData.getRollId())) {
                return false;
            } else if (StringUtils.isEqualsIgnoreCase("R0003", sharedPreferenceData.getRollId())) {
                return false;
            } else
                return !StringUtils.isEqualsIgnoreCase("R0004", sharedPreferenceData.getRollId());
        }
        return true;
    }


    void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.app_name, R.string.app_name) {

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        backPressCount = 0;
        if (showAlert) {
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    showAlert = false;
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION);
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Util.showToast(this, "Camera permission granted", AppConstants.COLOR_GREEN, Toast.LENGTH_LONG);
            } else {
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                    CustomAlertBox builder = new CustomAlertBox(this);
                    builder.setMessage("Please allow camera permission to scan barcodes");
                    builder.setPositiveButton("Goto Settings",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    Uri uri = Uri.fromParts("package", AppController.getInstance().getPackageName(), null);
                                    intent.setData(uri);
                                    startActivityForResult(intent, CAMERA_PERMISSION);
                                    showAlert = true;

                                }
                            });
                    builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
                    builder.show();
                } else {
                    CustomAlertBox builder = new CustomAlertBox(this);
                    builder.setMessage("Please allow camera permission to scan barcodes");
                    builder.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION);
                                    }

                                }
                            });
                    builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
                    builder.show();
                }
            }
        }
    }

}
