package com.rojo.travel.order;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rojo.travel.R;

import java.util.List;

/**
 * Created by limadawai on 15/04/18.
 */

class ListKeretaAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<ListKeretaDataSet> list;

    public ListKeretaAdapter(Context context, List<ListKeretaDataSet> listKereta) {
        this.context = context;
        this.list = listKereta;
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
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_pesanan_kereta, null);
        TextView namaKereta = (TextView) convertView.findViewById(R.id.namaKereta);
        TextView tglBrgkt = (TextView) convertView.findViewById(R.id.tglBrgkt);
        TextView tujuan = (TextView) convertView.findViewById(R.id.tujuan);
        final ListKeretaDataSet lkds = list.get(position);
        namaKereta.setText(lkds.getNamaKereta());
        tglBrgkt.setText(lkds.getTglBrgkt());
        tujuan.setText(lkds.getTujuan());
        return convertView;
    }
}
