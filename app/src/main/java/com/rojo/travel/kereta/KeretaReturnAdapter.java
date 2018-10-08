package com.rojo.travel.kereta;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rojo.travel.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by limadawai on 11/9/17.
 */

public class KeretaReturnAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<KeretaReturnDataSet> list;

    public KeretaReturnAdapter(Activity activity, List<KeretaReturnDataSet> list) {
        this.activity = activity;
        this.list = list;
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
    public View getView(int position, View convertView, final ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_row_kereta, null);
        TextView namakereta = (TextView) convertView.findViewById(R.id.namakereta);
        TextView kelaskereta = (TextView) convertView.findViewById(R.id.kelaskereta);
        TextView stbrgkt = (TextView) convertView.findViewById(R.id.stbrgkt);
        TextView stujuan = (TextView) convertView.findViewById(R.id.stujuan);
        TextView harga = (TextView) convertView.findViewById(R.id.harga);
        TextView jambrgkt = (TextView) convertView.findViewById(R.id.jambrgkt);
        TextView jamtujuan = (TextView) convertView.findViewById(R.id.jamtujuan);
        TextView kursi = (TextView) convertView.findViewById(R.id.kursi);

        final KeretaReturnDataSet kds = list.get(position);
        namakereta.setText(kds.getTrain_name());
        stbrgkt.setText(kds.getDep_city()+" (" + kds.getDep_station() + ")");
        stujuan.setText(kds.getArr_city()+" (" + kds.getArr_station() + ")");
        kelaskereta.setText(kds.getClass_name());
        jambrgkt.setText("("+kds.getDep_jam()+")");
        jamtujuan.setText("("+kds.getArr_jam()+")");
        kursi.setText("Sisa "+kds.getJumlahkursi()+" kursi");

        String price = kds.getPrice_total();
        String hrg = price.substring(0, price.indexOf("."));
        int val = Integer.parseInt(hrg);
        String s = NumberFormat.getIntegerInstance(Locale.GERMAN).format(val);
        harga.setText("Rp."+s);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(activity, DetailPemesananKereta.class);
                    intent.putExtra("departure_city_name", kds.getDep_city());
                    intent.putExtra("train_name", kds.getTrain_name());
                    intent.putExtra("arrival_city_name", kds.getArr_city());
                    intent.putExtra("depdate", kds.getFormatted_schedule_date());
                    intent.putExtra("train_id", kds.getTrain_id());
                    intent.putExtra("total_price", kds.getPrice_total());
                    activity.startActivity(intent);

            }
        });
        return convertView;
    }
}
