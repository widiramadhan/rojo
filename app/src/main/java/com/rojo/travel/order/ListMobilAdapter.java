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
 * Created by limadawai on 14/04/18.
 */

public class ListMobilAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<ListMobilDataSet> list;

    public ListMobilAdapter(Context context, List<ListMobilDataSet> listMobil) {
        this.context = context;
        this.list = listMobil;
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
            convertView = inflater.inflate(R.layout.list_pesanan_mobil, null);
        TextView mobil = (TextView) convertView.findViewById(R.id.mobil);
        TextView tglSewa = (TextView) convertView.findViewById(R.id.tglSewa);
        TextView tujuan = (TextView) convertView.findViewById(R.id.tujuan);
        TextView status = (TextView) convertView.findViewById(R.id.status);
        final ListMobilDataSet lmds = list.get(position);
        mobil.setText(lmds.getMobil());
        tglSewa.setText(lmds.getTanggal());
        if (lmds.getTujuan().equals("1")) {
            tujuan.setText("Dalam Kota");
        } else if (lmds.getTujuan().equals("2")) {
            tujuan.setText("Luar Kota");
        }
        if (lmds.getStatus().equals("0")) {
            status.setText("Belum bayar!");
        } else if (lmds.getStatus().equals("1")) {
            status.setText("Lunas!");
        }
        return convertView;
    }
}
