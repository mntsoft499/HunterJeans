package com.mntsoft.hunterjeans.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mntsoft.hunterjeans.R;
import com.mntsoft.hunterjeans.model.SalesReportResponse;
import com.mntsoft.hunterjeans.utils.StringUtils;

import java.util.ArrayList;

/**
 * Created by surya on 12/1/16.
 */

public class BillReportAdapter extends RecyclerView.Adapter<BillReportAdapter.BillReportViewHolder> {
    private Activity context;
    private ArrayList<SalesReportResponse> roomslist;
    private LayoutInflater infalInflater;

    public BillReportAdapter(Activity activity, ArrayList<SalesReportResponse> rooms) {
        this.context = activity;
        this.roomslist = rooms;

        infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public BillReportViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new BillReportViewHolder(infalInflater.inflate(R.layout.adapter_billreport, viewGroup, false));
    }
    @Override
    public void onBindViewHolder(@NonNull BillReportViewHolder viewHolder, int i) {


        final SalesReportResponse pojovalues = (SalesReportResponse) roomslist.get(i);
        viewHolder.txt_branch.setText(StringUtils.isEmpty(pojovalues.getBranchname()) ? "----" : pojovalues.getBranchname());
        viewHolder.txt_billno.setText(StringUtils.isEmpty(pojovalues.getBillproxyid()) ? "----" : pojovalues.getBillproxyid());
        viewHolder.txt_date.setText(StringUtils.isEmpty(pojovalues.getBilldate()) ? "----" : pojovalues.getBilldate());
        viewHolder.txt_totalamount.setText(StringUtils.isEmpty(pojovalues.getNetamount()) ? "----" : pojovalues.getNetamount());

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

    class BillReportViewHolder extends RecyclerView.ViewHolder {

        private TextView txt_branch,txt_billno,txt_date,txt_totalamount,txt_customername,txt_contactnumber,txt_barcode,txt_description,
                txt_quantity,txt_rate,txt_discount,txt_amountwithdiscount,txt_grandtotal;
        private TextView show,hide;
        private LinearLayout llhide;


        public BillReportViewHolder(@NonNull View convertView) {
            super(convertView);
            llhide = (LinearLayout) convertView.findViewById(R.id.llhide);
            txt_branch = (TextView) convertView.findViewById(R.id.txt_branch);
            txt_billno = (TextView) convertView.findViewById(R.id.txt_billno);
            txt_date = (TextView) convertView.findViewById(R.id.txt_date);
            txt_totalamount = (TextView) convertView.findViewById(R.id.txt_totalamount);
        }
    }


}
