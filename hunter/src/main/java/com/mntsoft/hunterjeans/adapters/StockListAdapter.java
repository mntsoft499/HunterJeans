package com.mntsoft.hunterjeans.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mntsoft.hunterjeans.ItemsListActivity;
import com.mntsoft.hunterjeans.R;
import com.mntsoft.hunterjeans.views.PojoValues;
import com.mntsoft.hunterjeans.views.StockCnts;

import java.util.ArrayList;

/**
 * Created by root on 2/1/18.
 */
public class StockListAdapter extends BaseAdapter {
    ProgressDialog progressDialog = null;
    int mYear, mMonth, mDay;
    private ArrayList<PojoValues> packitemlist;
    private Activity context;
    private ArrayList<StockCnts> roomslist;
    private LayoutInflater infalInflater;
    String boardingactive;
    String coupleactive;
    String logerror, Getactivityid, Getdevicename, Getnetworktype, Getdeviceversion, Getdeviceid, Getappversion, Getserverresponse, Getlogerror, Getfcmid,Getauthid;

    public StockListAdapter(Activity activity, ArrayList<StockCnts> rooms) {
        this.context = activity;
        this.roomslist = rooms;

        infalInflater = (LayoutInflater) this.context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return roomslist.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return roomslist.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @SuppressLint({"InflateParams", "ResourceAsColor"})
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        convertView = infalInflater.inflate(R.layout.adapter_stocklist, null, true);

        final ViewHolder viewHolder;

        viewHolder = new ViewHolder();

        viewHolder.txt_stockdate = (TextView) convertView.findViewById(R.id.txt_stockdate);

        final StockCnts pojovalues = (StockCnts) getItem(position);


        viewHolder.txt_stockdate.setText(pojovalues.getAllocateddate());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itemint = new Intent(context,ItemsListActivity.class);
                itemint.putExtra("RECEIVEID",pojovalues.getReceiveid());
                itemint.putExtra("BRANCHID",pojovalues.getBranchid());
                context.startActivity(itemint);
            }
        });


      /*  viewHolder.show.setOnClickListener(new View.OnClickListener() {
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
        });*/


        return convertView;
    }

    private class ViewHolder {

        private TextView txt_stockdate;


    }
}
