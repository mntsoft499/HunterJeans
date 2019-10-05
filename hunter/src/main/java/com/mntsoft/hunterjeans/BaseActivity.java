package com.mntsoft.hunterjeans;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mntsoft.hunterjeans.adapters.BranchAdapter;
import com.mntsoft.hunterjeans.views.PojoValues;
import com.mntsoft.hunterjeans.views.SharedPreferenceData;

import java.util.ArrayList;


/**
 * Created by root on 5/19/17.
 */
public class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView  username;
    public ImageView logout;
    public FrameLayout frag_frame;
    //private String[] icons = {"Administration", "Personal Info", "Applications", "Attendance","Documents"};
    Toolbar toolbar;
    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mDrawerToggle;
    ProgressDialog barProgressDialog;
    AlertDialog progressBarDialod;
    String Branchid;
    BranchAdapter stateAdapter;
    SharedPreferenceData sharedPreferenceData;
    ImageView img_toolicon;
    private ArrayList<PojoValues> sliderimageslist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        //getLang(icons);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        frag_frame = (FrameLayout) findViewById(R.id.frag_frame);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.navicon);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        img_toolicon = (ImageView) findViewById(R.id.img_toolicon);
        username = (TextView) findViewById(R.id.username);
        sharedPreferenceData = new SharedPreferenceData(BaseActivity.this);
//        help = (TextView) findViewById(R.id.help);
//        help.setVisibility(View.INVISIBLE);
        logout = (ImageView) findViewById(R.id.logout);
//        Helper.getTypeFace(this, "fontawesome.ttf", help);
//        Helper.getTypeFace(this, "fontawesome.ttf", logout);
        setupDrawer();

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog d = new Dialog(BaseActivity.this, android.R.style.Theme_DeviceDefault_Dialog_MinWidth);
                d.requestWindowFeature(Window.FEATURE_NO_TITLE);
                d.setCancelable(false);
                d.setContentView(R.layout.dialog_login_credentials);
                d.show();
                Button cancel = (Button) d.findViewById(R.id.login_dialog_cancel);
                Button ok = (Button) d.findViewById(R.id.login_dialog_ok);
                TextView login_txt = (TextView) d.findViewById(R.id.login_txt);
                login_txt.setText("Confirm to Logout Now ???..");
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (sharedPreferenceData.isRegistered()) {
                            sharedPreferenceData.setLogged(false);
                            sharedPreferenceData.setRegistered(false);
                            d.dismiss();
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        d.dismiss();
                    }
                });
            }
        });
        username.setText(sharedPreferenceData.getFirstName() + " " + sharedPreferenceData.getLastName());

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
    public void onClick(View v) {

    }

    public void showProgressDialog() {
        int llPadding = 30;
        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.HORIZONTAL);
        ll.setPadding(llPadding, llPadding, llPadding, llPadding);
        ll.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams llParam = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        llParam.gravity = Gravity.CENTER;
        ll.setLayoutParams(llParam);

        ProgressBar progressBar = new ProgressBar(this);
        progressBar.setIndeterminate(false);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Drawable wrapDrawable = DrawableCompat.wrap(progressBar.getIndeterminateDrawable());
            DrawableCompat.setTint(wrapDrawable, getApplicationContext().getResources().getColor(R.color.red_accent));
            progressBar.setIndeterminateDrawable(DrawableCompat.unwrap(wrapDrawable));
        } else {
            progressBar.getIndeterminateDrawable().setColorFilter(getApplicationContext().getResources().getColor(R.color.red_accent), PorterDuff.Mode.SRC_IN);

        }

        progressBar.setPadding(0, 0, llPadding, 0);
        progressBar.setLayoutParams(llParam);

        llParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        llParam.gravity = Gravity.CENTER;
        TextView tvText = new TextView(this);
        tvText.setText(getString(R.string.loading_pls_wait));
        tvText.setTextColor(Color.parseColor("#000000"));
        tvText.setTextSize(20);
        tvText.setLayoutParams(llParam);

        ll.addView(progressBar);
        ll.addView(tvText);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setView(ll);

        progressBarDialod = builder.create();
        progressBarDialod.show();
        Window window = progressBarDialod.getWindow();
        if (window != null) {
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.copyFrom(progressBarDialod.getWindow().getAttributes());
            layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;
            layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
            progressBarDialod.getWindow().setAttributes(layoutParams);
        }
    }

    public void dismissProgressDialog() {
        if (progressBarDialod != null && progressBarDialod.isShowing()) {
            progressBarDialod.dismiss();
            progressBarDialod = null;
        }
    }
}
