package com.mntsoft.hunterjeans.customviews;

import android.content.Context;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mntsoft.hunterjeans.R;

public class CustomToast extends Toast {

    private Context context;
    private String toastString;

    public CustomToast(Context context, String toastString, int colorCode) {
        super(context);
        this.toastString = toastString;
        this.context = context;
        setToastView(getColor(colorCode));
    }

    private void setToastView(int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_toast, null, false);
        view.getBackground().setColorFilter(i, PorterDuff.Mode.SRC_IN );
        TextView toast = view.findViewById(R.id.toastTextView);
        toast.setText(toastString);
        setView(view);
    }

    private int getColor(int colorCode) {
        switch (colorCode) {
            case 1:
                return context.getResources().getColor(R.color.FireBrick);
            case 2:
                return context.getResources().getColor(R.color.green_dark);
            case 3:
                return context.getResources().getColor(R.color.orange_dark);
            case 4:
                return context.getResources().getColor(R.color.black);
            case 5:
                return context.getResources().getColor(R.color.gray_dark);
            case 6:
                return context.getResources().getColor(R.color.colorAccent);

        }
        return context.getResources().getColor(R.color.colorAccent);
    }
}
