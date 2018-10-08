package com.rojo.travel.pesawat;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rojo.travel.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by limadawai on 11/2/17.
 */

public class BandaraAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<BandaraDataSet> list;
    private ArrayList<BandaraDataSet> arrayList;


    public BandaraAdapter(Activity activity, List<BandaraDataSet> list) {
        this.activity = activity;
        this.list = list;
        this.arrayList = new ArrayList<BandaraDataSet>();
        this.arrayList.addAll(list);
        this.inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null) {
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        convertView = inflater.inflate(R.layout.list_row_bandara, null);
        TextView tvLocationName = (TextView) convertView.findViewById(R.id.tvLocationName);
        TextView tvAirportCode = (TextView) convertView.findViewById(R.id.tvAirportCode);
        TextView locationName = (TextView) convertView.findViewById(R.id.location);
        TextView countryName = (TextView) convertView.findViewById(R.id.country);
        BandaraDataSet bds = list.get(position);
        tvLocationName.setText(bds.getLocation_name()+", ");
        tvAirportCode.setText(bds.getAirport_code()+" -");
        locationName.setText(bds.getAirport_name());
        countryName.setText(bds.getCountry_name());
        return convertView;
    }

    //====================================== Fungsi Filtering EditText Cari Bandara ================================
    public void filter(String text) {
        text = text.toLowerCase(Locale.getDefault());
        list.clear();
        if (text.length() == 0){
            list.addAll(arrayList);
        } else {
            for (BandaraDataSet bds : arrayList){
                if (bds.getLocation_name().toLowerCase(Locale.getDefault()).contains(text)
                        || bds.getCountry_name().toLowerCase(Locale.getDefault()).contains(text)
                        || bds.getAirport_code().toLowerCase(Locale.getDefault()).contains(text)){
                    list.add(bds);
                }
            }
        }
        notifyDataSetChanged();
    }
}
