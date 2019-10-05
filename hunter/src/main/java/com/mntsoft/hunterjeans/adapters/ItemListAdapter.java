package com.mntsoft.hunterjeans.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mntsoft.hunterjeans.R;
import com.mntsoft.hunterjeans.views.PojoValues;
import com.mntsoft.hunterjeans.views.StockCnts;

import java.util.ArrayList;

/**
 * Created by root on 2/1/18.
 */
public class ItemListAdapter extends BaseAdapter {
    ProgressDialog progressDialog = null;
    int mYear, mMonth, mDay;
    private ArrayList<PojoValues> packitemlist;
    private Activity context;
    private ArrayList<StockCnts> roomslist;
    private LayoutInflater infalInflater;
    String boardingactive;
    String coupleactive;
    String logerror, Getactivityid, Getdevicename, Getnetworktype, Getdeviceversion, Getdeviceid, Getappversion, Getserverresponse, Getlogerror, Getfcmid,Getauthid;

    public ItemListAdapter(Activity activity, ArrayList<StockCnts> rooms) {
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
        convertView = infalInflater.inflate(R.layout.adapter_itemlist, null, true);

        final ViewHolder viewHolder;

        viewHolder = new ViewHolder();

        viewHolder.txt_receivedqnty = (TextView) convertView.findViewById(R.id.txt_receivedqnty);
        viewHolder.txt_allocatedqty = (TextView) convertView.findViewById(R.id.txt_allocatedqty);
        viewHolder.txt_barcode = (TextView) convertView.findViewById(R.id.txt_barcode);


        final StockCnts pojovalues = (StockCnts) getItem(position);

        viewHolder.txt_receivedqnty.setText(pojovalues.getAllocatedqty());
        viewHolder.txt_allocatedqty.setText(pojovalues.getAllocatedqty());
        viewHolder.txt_barcode.setText(pojovalues.getBarcode());

        return convertView;
    }

    private class ViewHolder {

        private TextView txt_receivedqnty,txt_allocatedqty,txt_barcode;


    }


}
