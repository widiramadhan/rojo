package com.rojo.travel.mobil;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.rojo.travel.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by limadawai on 11/2/17.
 */

public class TujuanAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<TujuanDataSet> list;
    private ArrayList<TujuanDataSet> arrayList;


    public TujuanAdapter(Activity activity, List<TujuanDataSet> list) {
        this.activity = activity;
        this.list = list;
        this.arrayList = new ArrayList<TujuanDataSet>();
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
            convertView = inflater.inflate(R.layout.list_row_tujuan, null);
            TextView idtuj = (TextView) convertView.findViewById(R.id.idTujuan);
            TextView nmtuj = (TextView) convertView.findViewById(R.id.namatujuan);
            final TujuanDataSet mds = list.get(position);
            idtuj.setText(mds.getId_tujuan());
            nmtuj.setText(mds.getNama_tujuan());

            return convertView;
    }

    //====================================== Fungsi Filtering EditText Cari Country ================================
    public void filter(String text) {
        text = text.toLowerCase(Locale.getDefault());
        list.clear();
        if (text.length() == 0){
            list.addAll(arrayList);
        } else {
            for (TujuanDataSet mds : arrayList){
                if (mds.getNama_tujuan().toLowerCase(Locale.getDefault()).contains(text)){
                    list.add(mds);
                }
            }
        }
        notifyDataSetChanged();
    }
}
