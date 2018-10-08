package com.rojo.travel.tour;

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

public class CountryAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<CountryDataSet> list;
    private ArrayList<CountryDataSet> arrayList;


    public CountryAdapter(Activity activity, List<CountryDataSet> list) {
        this.activity = activity;
        this.list = list;
        this.arrayList = new ArrayList<CountryDataSet>();
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
            convertView = inflater.inflate(R.layout.list_row_country, null);
            TextView countryName = (TextView) convertView.findViewById(R.id.countryName);
            TextView countryID = (TextView) convertView.findViewById(R.id.countryID);
            TextView provinsiID = (TextView) convertView.findViewById(R.id.provinsiID);
            final CountryDataSet bds = list.get(position);
            countryName.setText(bds.getNama_provinsi());
            provinsiID.setText(bds.getId_provinsi());


            return convertView;
    }

    //====================================== Fungsi Filtering EditText Cari Country ================================
    public void filter(String text) {
        text = text.toLowerCase(Locale.getDefault());
        list.clear();
        if (text.length() == 0){
            list.addAll(arrayList);
        } else {
            for (CountryDataSet bds : arrayList){
                if (bds.getNama_provinsi().toLowerCase(Locale.getDefault()).contains(text)){
                    list.add(bds);
                }
            }
        }
        notifyDataSetChanged();
    }
}
