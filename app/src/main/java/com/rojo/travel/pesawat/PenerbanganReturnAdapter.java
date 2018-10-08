package com.rojo.travel.pesawat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import java.util.List;
import java.util.Locale;

/**
 * Created by limadawai on 11/9/17.
 */

public class PenerbanganReturnAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<PenerbanganReturnDataSet> list;

    public PenerbanganReturnAdapter(Activity activity, List<PenerbanganReturnDataSet> list) {
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
            convertView = inflater.inflate(R.layout.list_row_pesawat_return, null);
        TextView maskapai = (TextView) convertView.findViewById(R.id.Maskapai);
        ImageView imageMaskapai = (ImageView) convertView.findViewById(R.id.ImageMaskapai);
        TextView kode = (TextView) convertView.findViewById(R.id.kode);
        TextView jam = (TextView) convertView.findViewById(R.id.jam);
        TextView stop = (TextView) convertView.findViewById(R.id.direct);
        TextView harga = (TextView) convertView.findViewById(R.id.harga);
        final PenerbanganReturnDataSet prds = list.get(position);
        maskapai.setText(prds.getAirlines_name());
        Glide.with(activity).load(prds.getImage()).asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.mipmap.ic_launcher)
                .into(imageMaskapai);
        kode.setText(prds.getFlight_number());
        jam.setText(prds.getSimple_departure_time());
        stop.setText(prds.getStop());
        String price = prds.getPrice_value();
        String hrg = price.substring(0, price.indexOf("."));
        int val = Integer.parseInt(hrg);
        String s = NumberFormat.getIntegerInstance(Locale.GERMAN).format(val);
        harga.setText("Rp."+s);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, DetailPemesanan.class);
                intent.putExtra("departure_city_name", prds.getDeparture_city_name());
                intent.putExtra("arrival_city_name", prds.getArrival_city_name());
                intent.putExtra("departure_flight_date_str", prds.getDeparture_flight_date_str());
                intent.putExtra("image", prds.getImage());
                intent.putExtra("flight_number", prds.getFlight_number());
                intent.putExtra("simple_departure_time", prds.getSimple_departure_time());
                intent.putExtra("simple_arrival_time", prds.getSimple_arrival_time());
                intent.putExtra("price_value", prds.getPrice_value());
                intent.putExtra("full_via", prds.getFull_via());
                intent.putExtra("price_adult", prds.getPrice_adult());
                intent.putExtra("price_child", prds.getPrice_child());
                intent.putExtra("price_infant", prds.getPrice_infant());
                intent.putExtra("airlines_name", prds.getAirlines_name());
                intent.putExtra("airlines_real_name", prds.getAirlines_real_name());
                intent.putExtra("flight_id", prds.getFlight_id());
                intent.putExtra("domestik", prds.getDomestik());
                intent.putExtra("departureDate", prds.getDeparture_date());
                intent.putExtra("ReturnDate", prds.getReturn_date());
                activity.startActivity(intent);
            }
        });
        return convertView;
    }
}
