package com.mntsoft.hunterjeans.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mntsoft.hunterjeans.R;
import com.mntsoft.hunterjeans.model.BStockResponse;
import com.mntsoft.hunterjeans.utils.StringUtils;

import java.util.ArrayList;

/**
 * Created by surya on 12/1/16.
 */

public class WStockadapter extends RecyclerView.Adapter<WStockadapter.WStockViewHolder> {
    private final boolean isProductWiseList;
    private Activity context;
    private ArrayList<BStockResponse> roomslist;
    private LayoutInflater infalInflater;

    public WStockadapter(Activity activity, ArrayList<BStockResponse> rooms,boolean isProductWiseList) {
        this.context = activity;
        this.roomslist = rooms;
        this.isProductWiseList = isProductWiseList;
        infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public WStockViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new WStockViewHolder(infalInflater.inflate(R.layout.bstock_row, viewGroup, false));
    }
    @Override
    public void onBindViewHolder(@NonNull WStockViewHolder viewHolder, int position) {
        final BStockResponse pojovalues = (BStockResponse) roomslist.get(position);
        if (position % 2 == 0) {
            viewHolder.view.setBackgroundColor(context.getResources().getColor(R.color.white));
        }else {
            viewHolder.view.setBackgroundColor(context.getResources().getColor(R.color.LightGrey));
        }
        if (isProductWiseList) {
            viewHolder.product_container.setVisibility(View.VISIBLE);
            viewHolder.category_container.setVisibility(View.GONE);
            viewHolder.barcode.setText(StringUtils.isEmpty(pojovalues.getBarcode()) ? "----" : pojovalues.getBarcode());
            viewHolder.productQuantity.setText(StringUtils.isEmpty(pojovalues.getQuantity()) ? "----" : pojovalues.getQuantity());
            viewHolder.description.setText(StringUtils.concatWithDots(StringUtils.isEmpty(pojovalues.getProductName()) ? "----" : (position + 1) + ". " + pojovalues.getProductName(), 16));
        } else {
            viewHolder.product_container.setVisibility(View.GONE);
            viewHolder.category_container.setVisibility(View.VISIBLE);
            viewHolder.categoryName.setText(StringUtils.concatWithDots(StringUtils.isEmpty(pojovalues.getCategory()) ? "----" : (position + 1) + ". " + pojovalues.getCategory(), 16));
            viewHolder.categoryQuantity.setText(StringUtils.isEmpty(pojovalues.getQuantity()) ? "----" : pojovalues.getQuantity());
        }

        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isProductWiseList) {
                    new AlertDialog.Builder(context)
                            .setTitle("Product")
                            .setMessage("Desc : " + pojovalues.getProductName() + "\n" +
                                    "Barcode : " + pojovalues.getBarcode() + "\n" +
                                    "Quantity : " + pojovalues.getQuantity())
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();
                } else {
                    new AlertDialog.Builder(context)
                            .setTitle("Category")
                            .setMessage("Name : " + pojovalues.getCategory() + "\n" + "Quantity : " + pojovalues.getQuantity())
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();
                }
            }
        });
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public int getItemCount() {
        return roomslist.size();
    }

    class WStockViewHolder extends RecyclerView.ViewHolder {

        public TextView description,barcode,productQuantity,categoryName,categoryQuantity;
        public LinearLayout product_container,category_container,view;

        public WStockViewHolder(@NonNull View convertView) {
            super(convertView);
            barcode = (TextView) convertView.findViewById(R.id.barcode);
            description = (TextView) convertView.findViewById(R.id.description);
            productQuantity = (TextView) convertView.findViewById(R.id.product_wise_quantity);
            categoryName = (TextView) convertView.findViewById(R.id.category_name);
            categoryQuantity = (TextView) convertView.findViewById(R.id.category_quantity);
            product_container = (LinearLayout) convertView.findViewById(R.id.product_wise_container);
            category_container = (LinearLayout) convertView.findViewById(R.id.category_wise_container);
            view = (LinearLayout) convertView.findViewById(R.id.bstock_row_container);
        }
    }
}
