package com.rojo.travel.akun;

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
 * Created by kangyasin on 04/02/2018.
 */

public class NegaraAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<NeagaraDataSet> list;
    private ArrayList<NeagaraDataSet> arrayList;

    public NegaraAdapter(Activity activity, List<NeagaraDataSet> list) {
        this.activity = activity;
        this.list = list;
        this.arrayList = new ArrayList<NeagaraDataSet>();
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
        convertView = inflater.inflate(R.layout.list_row_negara, null);
        TextView tvLocationName = (TextView) convertView.findViewById(R.id.nama_negara);
        TextView negaraID = (TextView) convertView.findViewById(R.id.negara_id);
        NeagaraDataSet nds = list.get(position);
        tvLocationName.setText(nds.getNegara_nama_1());
        negaraID.setText(nds.getNegara_id());
        return convertView;
    }

    //====================================== Fungsi Filtering EditText Cari Bandara ================================
    public void filter(String text) {
        text = text.toLowerCase(Locale.getDefault());
        list.clear();
        if (text.length() == 0){
            list.addAll(arrayList);
        } else {
            for (NeagaraDataSet nds : arrayList){
                if (nds.getNegara_nama_1().toLowerCase(Locale.getDefault()).contains(text)){
                    list.add(nds);
                }
            }
        }
        notifyDataSetChanged();
    }




}
