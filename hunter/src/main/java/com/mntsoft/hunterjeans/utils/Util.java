package com.mntsoft.hunterjeans.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.mntsoft.hunterjeans.application.AppController;
import com.mntsoft.hunterjeans.customviews.CustomToast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.NumberFormat;
import java.text.ParsePosition;

public class Util {
    private static final String TAG = Util.class.getSimpleName();

    public static SharedPreferences getSharedPreferences(String name) {
        return AppController.getInstance().getSharedPreferences(name, Context.MODE_PRIVATE);
    }
    public static void hideKeyboard(Activity activity) {
        if (activity == null) {
            return;
        }
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    public static String loadJSONFromAsset(String fileName,Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
    public static boolean isOnline(){
        ConnectivityManager cm = (ConnectivityManager)AppController.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()) {
            try {
                URL url = new URL("http://www.google.com/");
                HttpURLConnection urlc = (HttpURLConnection)url.openConnection();
                urlc.setRequestProperty("User-Agent", "test");
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(1000); // mTimeout is in seconds
                urlc.connect();
                if (urlc.getResponseCode() == 200) {
                    return true;
                } else {
                    return false;
                }
            } catch (IOException e) {
                LOG.e(" Util","Error checking internet connection : "+e.getMessage());
                return false;
            }
        }
        return false;
    }

    public static void clearAppData() {
        LOG.i(TAG,"clearAppData() : ");
        getSharedPreferences(AppConstants.LOGIN_CREDENTIALS).edit().clear().apply();
    }

    public static void showToast(Context context, String value, int colorCode,int toastLength) {
        if (context != null) {
            Toast toast = new CustomToast(context, value, colorCode);
            toast.setDuration(toastLength);
            toast.show();
        }
    }

    public static void showToast(Context context, String value, int colorCode) {
        showToast(context, value, colorCode, Toast.LENGTH_SHORT);
    }

    public static float getFloatFromString(String netamount) {
        if (StringUtils.isEmpty(netamount) || StringUtils.isEquals(netamount, "0") || StringUtils.isEqualsIgnoreCase(netamount, "null")) {
            return 0;
        } else if (isNumber(netamount)) {
            return Float.valueOf(netamount);
        }
        return 0;
    }

    public static boolean isNumber(String number) {
        NumberFormat formatter = NumberFormat.getInstance();
        ParsePosition pos = new ParsePosition(0);
        formatter.parse(number, pos);
        return number.length() == pos.getIndex();
    }
}
