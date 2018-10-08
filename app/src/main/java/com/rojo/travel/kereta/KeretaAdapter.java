package com.rojo.travel.kereta;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.rojo.travel.R;

import java.util.List;

/**
 * Created by limadawai on 11/9/17.
 */

public class KeretaAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<KeretaDataSet> list;
    SharedPreferences preferencess;

    public KeretaAdapter(Activity activity, List<KeretaDataSet> list) {
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

        final KeretaDataSet kds = list.get(position);

        //Toast.makeText(convertView.getContext(), kds.toString(), Toast.LENGTH_LONG).show();

        namakereta.setText(kds.getTrain_name());
        stbrgkt.setText(kds.getDep_city()+" (" + kds.getDeparture_station() + ")");
        stujuan.setText(kds.getArr_city()+" (" + kds.getArrival_station() + ")");
        kelaskereta.setText(kds.getClass_name_lang());
        jambrgkt.setText("("+kds.getDeparture_time()+") - ");
        jamtujuan.setText("("+kds.getArrival_time()+")");
        kursi.setText("Sisa "+kds.getDetail_availability()+" kursi");

        String price = kds.getPrice_total();
        harga.setText(price);
        preferencess = activity.getSharedPreferences("jumPenumpangs", 0);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (preferencess.getInt("returnDates", 0) == 0) {
                    Intent intent = new Intent(activity, DetailPemesananKereta.class);
                    intent.putExtra("departure_station", kds.getDep_city());
                    intent.putExtra("train_name", kds.getTrain_name());
                    intent.putExtra("arrival_station", kds.getArr_city());
                    intent.putExtra("train_id", kds.getTrain_id());
                    intent.putExtra("total_price", kds.getPrice_total());
                    intent.putExtra("formatted_schedule_date", kds.getFormatted_schedule_date());
                    intent.putExtra("departure_time", kds.getDeparture_time());
                    intent.putExtra("arrival_time", kds.getArrival_time());
                    intent.putExtra("ReturnDate", kds.getDateArr());
                    intent.putExtra("token", kds.getToken());
                    intent.putExtra("train_subclass", kds.getSubClass());
                    intent.putExtra("date_dep", kds.getDateDep());
                    intent.putExtra("date_arr", kds.getDateArr());
                    activity.startActivity(intent);

                } else if (preferencess.getInt("returnDates", 0) == 1) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    builder.setTitle("Pilih Kereta Pulang");
                    builder.setMessage("Lanjut untuk memilih kereta");
                    builder.setPositiveButton("Lanjut", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SharedPreferences preferencess = activity.getApplication().getSharedPreferences("dataTrain", 0);
                            SharedPreferences.Editor editor = preferencess.edit();
                            editor.putString("departure_station", kds.getDep_city());
                            editor.putString("train_name", kds.getTrain_name());
                            editor.putString("arrival_station", kds.getArr_city());
                            editor.putString("train_id", kds.getTrain_id());
                            editor.putString("total_price", kds.getPrice_total());
                            editor.putString("formatted_schedule_date", kds.getFormatted_schedule_date());
                            editor.putString("departure_time", kds.getDeparture_time());
                            editor.putString("arrival_time", kds.getArrival_time());
                            editor.putString("ReturnDate", kds.getDateArr());
                            editor.putString("token", kds.getToken());
                            editor.putString("train_subclass", kds.getSubClass());
                            editor.putString("date_dep", kds.getDateDep());
                            editor.putString("date_arr", kds.getDateArr());
                            editor.commit();
                            Intent intent = new Intent(activity, ListKeretaReturn.class);
                            activity.startActivity(intent);
                        }
                    });
                    builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(activity, ActivityKereta.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            activity.startActivity(intent);
                            Toast.makeText(activity, "Silahkan ulangi pencarian!", Toast.LENGTH_LONG).show();
                        }
                    });
                    builder.setIcon(android.R.drawable.ic_dialog_alert);
                    builder.show();
                }
            }
        });
        return convertView;
    }
}
