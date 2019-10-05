package com.mntsoft.hunterjeans.customviews;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;

/**
 * Created by Bikash Sahani on 22/3/19.
 */

public class CustomAlertBox {
    private final AlertDialog.Builder mCustomAlertBox;
    public View view;
    Activity context;

    public CustomAlertBox(Activity context) {
        this.context = context;
        mCustomAlertBox = new AlertDialog.Builder(context);
        mCustomAlertBox.setCancelable(false);

    }

    public void show() {
        mCustomAlertBox.show();
    }

    public void setTitle(String title) {
        mCustomAlertBox.setTitle(title);
    }

    public void setTitleIcon(int id) {
        mCustomAlertBox.setIcon(id);
    }

    public void setPositiveButton(String text, DialogInterface.OnClickListener listener) {
        mCustomAlertBox.setPositiveButton(text, listener);
    }

    public void setNegativeButton(String text, DialogInterface.OnClickListener listener) {
        mCustomAlertBox.setNegativeButton(text, listener);
    }

    public void setMessage(String message) {
        mCustomAlertBox.setMessage(message);
    }
}
