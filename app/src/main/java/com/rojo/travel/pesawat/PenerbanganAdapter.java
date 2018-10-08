package com.rojo.travel.pesawat;

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.rojo.travel.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by limadawai on 11/9/17.
 */

public class PenerbanganAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<PenerbanganDataSet> list;
    SharedPreferences preferences;

    public PenerbanganAdapter(Activity activity, List<PenerbanganDataSet> list) {
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
        //convertView = inflater.inflate(R.layout.list_row_pesawat, null, false);
        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_row_pesawat, null);
            TextView maskapai = (TextView) convertView.findViewById(R.id.Maskapai);
            ImageView imageMaskapai = (ImageView) convertView.findViewById(R.id.ImageMaskapai);
            TextView kode = (TextView) convertView.findViewById(R.id.kode);
            TextView jam = (TextView) convertView.findViewById(R.id.jam);
            TextView stop = (TextView) convertView.findViewById(R.id.direct);
            TextView harga = (TextView) convertView.findViewById(R.id.harga);
            final PenerbanganDataSet pds = list.get(position);
            maskapai.setText(pds.getAirlines_name());
            Glide.with(activity).load(pds.getImage()).asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.mipmap.ic_launcher)
                    .into(imageMaskapai);
            kode.setText(pds.getFlight_number());
            jam.setText(pds.getSimple_departure_time());
            stop.setText(pds.getStop());
            String price = pds.getPrice_value();
            String hrg = price.substring(0, price.indexOf("."));
            int val = Integer.parseInt(hrg);
            String s = NumberFormat.getIntegerInstance(Locale.GERMAN).format(val);
            harga.setText("Rp." + s);
            //Toast.makeText(convertView.getContext(), pds.getSimple_arrival_time(), Toast.LENGTH_LONG).show();
            preferences = activity.getSharedPreferences("jumPenumpang", 0);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (preferences.getInt("returnDate", 0) == 0) {
                        Intent intent = new Intent(activity, DetailPemesanan.class);
                        intent.putExtra("departure_city_name", pds.getDeparture_city_name());
                        intent.putExtra("arrival_city_name", pds.getArrival_city_name());
                        intent.putExtra("departure_flight_date_str", pds.getDeparture_flight_date_str());
                        intent.putExtra("image", pds.getImage());
                        intent.putExtra("flight_number", pds.getFlight_number());
                        intent.putExtra("simple_departure_time", pds.getSimple_departure_time());
                        intent.putExtra("simple_arrival_time", pds.getSimple_arrival_time());
                        intent.putExtra("price_value", pds.getPrice_value());
                        intent.putExtra("full_via", pds.getFull_via());
                        intent.putExtra("price_adult", pds.getPrice_adult());
                        intent.putExtra("price_child", pds.getPrice_child());
                        intent.putExtra("price_infant", pds.getPrice_infant());
                        intent.putExtra("airlines_name", pds.getAirlines_name());
                        intent.putExtra("airlines_real_name", pds.getAirlines_real_name());
                        intent.putExtra("flight_id", pds.getFlight_id());
                        intent.putExtra("token", pds.getToken());
                        intent.putExtra("domestik", pds.getDomestik());
                        intent.putExtra("departureDate", pds.getDeparture_date());
                        intent.putExtra("ReturnDate", pds.getReturn_date());
                        activity.startActivity(intent);
                    } else if (preferences.getInt("returnDate", 0) == 1) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                        builder.setTitle("Pilih Penerbangan Pulang");
                        builder.setMessage("Lanjut untuk memilih penerbangan");
                        builder.setPositiveButton("Lanjut", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences preferences = activity.getApplication().getSharedPreferences("dataFlight", 0);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("departure_city_name", pds.getDeparture_city_name());
                                editor.putString("arrival_city_name", pds.getArrival_city_name());
                                editor.putString("departure_flight_date_str", pds.getDeparture_flight_date_str());
                                editor.putString("image", pds.getImage());
                                editor.putString("flight_number", pds.getFlight_number());
                                editor.putString("simple_departure_time", pds.getSimple_departure_time());
                                editor.putString("simple_arrival_time", pds.getSimple_arrival_time());
                                editor.putString("price_value", pds.getPrice_value());
                                editor.putString("full_via", pds.getFull_via());
                                editor.putString("price_adult", pds.getPrice_adult());
                                editor.putString("price_child", pds.getPrice_child());
                                editor.putString("price_infant", pds.getPrice_infant());
                                editor.putString("airlines_name", pds.getAirlines_name());
                                editor.putString("airlines_real_name", pds.getAirlines_real_name());
                                editor.putString("flight_id", pds.getFlight_id());
                                editor.putString("token", pds.getToken());
                                editor.putString("domestik", pds.getDomestik());
                                editor.putString("departureDate", pds.getDeparture_date());
                                editor.putString("ReturnDate", pds.getReturn_date());
                                editor.commit();
                                Intent intent = new Intent(activity, ListPenerbanganReturn.class);
                                activity.startActivity(intent);
                            }
                        });
                        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(activity, ActivityPesawat.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                activity.startActivity(intent);
                                Toast.makeText(activity, "Silahkan ulangi pencarian!", Toast.LENGTH_SHORT).show();
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
