package com.mntsoft.hunterjeans.utils;

import android.util.Log;

import com.mntsoft.hunterjeans.Config;

public class LOG {
    public static void e(String TAG, String Value) {
        if (Config.DEVELOPMENT) {
            Log.e(TAG, Value);
        }
    }
    public static void i(String TAG, String Value) {
        if (Config.DEVELOPMENT) {
            Log.i(TAG, Value);
        }
    }
    public static void d(String TAG, String Value) {
        if (Config.DEVELOPMENT) {
            Log.d(TAG, Value);
        }
    }
}
