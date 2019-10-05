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
import com.mntsoft.hunterjeans.model.BranchReportResponse;
import com.mntsoft.hunterjeans.utils.StringUtils;

import java.util.ArrayList;

/**
 * Created by surya on 12/1/16.
 */

public class BranchReportAdapter extends RecyclerView.Adapter<BranchReportAdapter.ViewHolder> {
    private Activity context;
    private ArrayList<BranchReportResponse> roomslist;
    private LayoutInflater infalInflater;

    public BranchReportAdapter(Activity activity, ArrayList<BranchReportResponse> rooms) {
        this.context = activity;
        this.roomslist = rooms;

        infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(infalInflater.inflate(R.layout.test1, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        BranchReportResponse pojovalues = (BranchReportResponse) roomslist.get(i);
        int count = i + 1;
        viewHolder.serialNumber.setText(i+1+"");

        if(StringUtils.isEmpty(pojovalues.getBranchName())){
            viewHolder.city.setText(" ");
        }else{
            viewHolder.city.setText(pojovalues.getBranchName());
        }
        if(StringUtils.isEmpty(pojovalues.getBranchName())){
            viewHolder.branchName.setText("----");
        }else{
            viewHolder.branchName.setText(pojovalues.getBranchName());
        }
        if(StringUtils.isEmpty(pojovalues.getBranchtype())){
            viewHolder.branchType.setText("----");
        }else{
            viewHolder.branchType.setText(pojovalues.getBranchtype());
        }
        if(StringUtils.isEmpty(pojovalues.getState())){
            viewHolder.state.setText("----");
        }else{
            viewHolder.state.setText(pojovalues.getState());
        }
        if(StringUtils.isEmpty(pojovalues.getBranchseries())){
            viewHolder.code.setText("----");
        }else{
            viewHolder.code.setText(pojovalues.getBranchseries());
        }
        if (StringUtils.isEmpty(pojovalues.getAddress1())){
            viewHolder.address.setText("----");
        }else{
            viewHolder.address.setText(pojovalues.getAddress1());
        }
        if(StringUtils.isEmpty(pojovalues.getTinNo())){
            viewHolder.gstin.setText("----");
        }else{
            viewHolder.gstin.setText(pojovalues.getTinNo());
        }
        if(StringUtils.isEmpty(pojovalues.getCreditlimit())){
            viewHolder.creditLimit.setText("----");
        }else{
            viewHolder.creditLimit.setText(pojovalues.getCreditlimit());
        }
        if(StringUtils.isEmpty(pojovalues.getOutstandingamount())){
            viewHolder.outstandingAmount.setText("----");
        }else{
            viewHolder.outstandingAmount.setText(pojovalues.getOutstandingamount());
        }
        if(StringUtils.isEmpty(pojovalues.getStatus())){
            viewHolder.status.setText("----");
        }else{
            viewHolder.status.setText(pojovalues.getStatus());
        }
        viewHolder.llhide.setVisibility(View.GONE);
        viewHolder.show.setVisibility(View.VISIBLE);

        viewHolder.show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.show.setVisibility(View.GONE);
                viewHolder.llhide.setVisibility(View.VISIBLE);
            }
        });

        viewHolder.hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.llhide.setVisibility(View.GONE);
                viewHolder.show.setVisibility(View.VISIBLE);
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
    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView city,branchName,state,branchType,code,address,gstin,creditLimit,outstandingAmount, status,serialNumber;
        private TextView show,hide;
        private LinearLayout llhide;
        private View convertView;

        ViewHolder(View convertView) {
            super(convertView);
            this.convertView = convertView;
            llhide = (LinearLayout) convertView.findViewById(R.id.llhide);
//            ll_branchclick = (LinearLayout) convertView.findViewById(R.id.ll_branchclick);
            city = (TextView) convertView.findViewById(R.id.city);
            branchName = (TextView) convertView.findViewById(R.id.branch_name);
            state = (TextView) convertView.findViewById(R.id.state);
            branchType = (TextView) convertView.findViewById(R.id.branch_type);
            code = (TextView) convertView.findViewById(R.id.code);
            address = (TextView) convertView.findViewById(R.id.address);
            gstin = (TextView) convertView.findViewById(R.id.gstin);
            creditLimit = (TextView) convertView.findViewById(R.id.credit_limit);
            outstandingAmount = (TextView) convertView.findViewById(R.id.outstanding_amount);
            status = (TextView) convertView.findViewById(R.id.status);
            show = (TextView) convertView.findViewById(R.id.show);
            hide = (TextView) convertView.findViewById(R.id.hide);
            serialNumber = (TextView) convertView.findViewById(R.id.serial_number);
        }


    }


}
