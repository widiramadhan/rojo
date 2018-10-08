package com.rojo.travel.kereta;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rojo.travel.R;
import com.rojo.travel.functions.Fungsi;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by limadawai on 11/2/17.
 */

public class StasiunAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<StasiunDataSet> list;
    private ArrayList<StasiunDataSet> arrayList;
    Fungsi fungsi = new Fungsi();


    public StasiunAdapter(Activity activity, List<StasiunDataSet> list) {
        this.activity = activity;
        this.list = list;
        this.arrayList = new ArrayList<StasiunDataSet>();
        this.arrayList.addAll(list);
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
        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_row_stasiun, null);
        TextView stationName = (TextView) convertView.findViewById(R.id.stationname);
        TextView stationCode = (TextView) convertView.findViewById(R.id.stationcode);
        TextView locationName = (TextView) convertView.findViewById(R.id.kota);
        StasiunDataSet bds = list.get(position);
        stationName.setText(fungsi.capitalize(bds.getStation_name())+", ");
        stationCode.setText(bds.getStation_code());
        locationName.setText(bds.getCity_name());
        return convertView;
    }

    //====================================== Fungsi Filtering EditText Cari Bandara ================================
    public void filter(String text) {
        text = text.toLowerCase(Locale.getDefault());
        list.clear();
        if (text.length() == 0){
            list.addAll(arrayList);
        } else {
            for (StasiunDataSet bds : arrayList){
                if (bds.getStation_name().toLowerCase(Locale.getDefault()).contains(text)
                        || bds.getCity_name().toLowerCase(Locale.getDefault()).contains(text)
                        || bds.getStation_code().toLowerCase(Locale.getDefault()).contains(text)){
                    list.add(bds);
                }
            }
        }
        notifyDataSetChanged();
    }
}
