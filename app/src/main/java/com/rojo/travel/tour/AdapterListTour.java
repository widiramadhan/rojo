package com.rojo.travel.tour;

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
 * Created by kangyasin on 19/11/2017.
 */

public class AdapterListTour extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<ListTourDataSet> list;

    public AdapterListTour(Activity activity, List<ListTourDataSet> list) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_row_tour, null);
            ImageView imagetour = (ImageView) convertView.findViewById(R.id.imagetour);
            ImageView imageRate = (ImageView) convertView.findViewById(R.id.rate);
            TextView judulTour = (TextView) convertView.findViewById(R.id.judultour);
            TextView harga = (TextView) convertView.findViewById(R.id.harga);
            final ListTourDataSet pds = list.get(position);
            judulTour.setText(pds.getJudul());
            Glide.with(activity).load(pds.getImage()).asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.mipmap.ic_launcher)
                    .into(imagetour);
            Glide.with(activity).load(pds.getRate()).asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.mipmap.ic_launcher)
                    .into(imageRate);
            harga.setText(pds.getHarga());
            String price = pds.getHarga();
    //        String hrg = price.substring(0, price.indexOf("."));
            int val = Integer.parseInt(price);
            String s = NumberFormat.getIntegerInstance(Locale.GERMAN).format(val);
            harga.setText(s);

            //Toast.makeText(activity, pds.getDeskripsi(), Toast.LENGTH_LONG).show();

        //Toast.makeText((), "Under Development", Toast.LENGTH_LONG).show();

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, DetailTour.class);
                    intent.putExtra("id_paket", pds.getId());
                    intent.putExtra("judul", pds.getJudul());
                    intent.putExtra("deskripsi", pds.getDeskripsi());
                    intent.putExtra("include", pds.getInclude());
                    intent.putExtra("exclude", pds.getExclude());
                    intent.putExtra("term", pds.getTerm());
                    intent.putExtra("image", pds.getImage());
                    intent.putExtra("harga", pds.getHarga());
                    intent.putExtra("harga_family", pds.getHargadouble());
                    intent.putExtra("harga_group", pds.getHargagroup());
                    activity.startActivity(intent);
                    activity.overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                }

            });


            return convertView;
    }
}
