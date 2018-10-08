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

class ListWisataAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<ListWisataDataSet> list;

    public ListWisataAdapter(Context context, List<ListWisataDataSet> listWisata) {
        this.context = context;
        this.list = listWisata;
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
            convertView = inflater.inflate(R.layout.list_pesanan_wisata, null);
        TextView invoice = (TextView) convertView.findViewById(R.id.invoice);
        TextView tglBrgkt = (TextView) convertView.findViewById(R.id.tglBrgkt);
        TextView tujuan = (TextView) convertView.findViewById(R.id.tujuan);
        final ListWisataDataSet lwds = list.get(position);
        invoice.setText(lwds.getKode_invoice_pemesanan());
        tglBrgkt.setText(lwds.getPaket_wisata_tanggal_berangkat());
        tujuan.setText(lwds.getPaket_wisata_nama());
        return convertView;
    }
}
