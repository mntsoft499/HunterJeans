package com.mntsoft.hunterjeans.views;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.mntsoft.hunterjeans.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by surya on 12/1/16.
 */

public class Helper {
    private static Context context;

    public static void getTypeFace(Context ctx, String fontname, TextView textView) {

        Typeface myTypeface = Typeface.createFromAsset(ctx.getAssets(), fontname);
        textView.setTypeface(myTypeface);
    }

    public static boolean validateEmailAddress(String emailAddress) {
        String expression = "^[\\w\\-]([\\.\\w])+[\\w]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = emailAddress;
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        return matcher.matches();
    }
    public static int getDisplayWidth(Context ctx) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        displayMetrics = ctx.getResources().getDisplayMetrics();
        int width = displayMetrics.widthPixels;
        return width;
    }

    public static int getDisplayHeight(Context ctx) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        displayMetrics = ctx.getResources().getDisplayMetrics();
        int height = displayMetrics.heightPixels;

        return height;
    }

    public static void getNetworkError(Context ctx) {
        final Dialog d = new Dialog(ctx, android.R.style.Theme_DeviceDefault_Dialog_MinWidth);
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.setCancelable(false);
        d.setContentView(R.layout.dialog_network);
        d.show();
        TextView alertmsg = (TextView) d.findViewById(R.id.alertMessage);
        Button cancel = (Button) d.findViewById(R.id.networkCancel);
        alertmsg.setText("NetWork Problem Please Check Your Internet Once ...");
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });
    }

}
