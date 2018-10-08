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

public class MobilAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<MobilDataSet> list;
    private ArrayList<MobilDataSet> arrayList;


    public MobilAdapter(Activity activity, List<MobilDataSet> list) {
        this.activity = activity;
        this.list = list;
        this.arrayList = new ArrayList<MobilDataSet>();
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
            convertView = inflater.inflate(R.layout.list_row_mobil, null);
            ImageView mobil_image = (ImageView) convertView.findViewById(R.id.imgMobil);
            TextView mobilName = (TextView) convertView.findViewById(R.id.mobilName);
            TextView mobilSupir = (TextView) convertView.findViewById(R.id.supirMobil);
            TextView mobilInfo = (TextView) convertView.findViewById(R.id.infoMobil);
            TextView mobilKursi = (TextView) convertView.findViewById(R.id.kursiMobil);
            TextView mobilHarga = (TextView) convertView.findViewById(R.id.harga);
            final MobilDataSet mds = list.get(position);
            mobilName.setText(mds.getNama_mobil()+" "+mds.getTahun_mobil());
            mobilSupir.setText(mds.getSupir_mobil());
            mobilInfo.setText(mds.getInfo_mobil());
            mobilKursi.setText(mds.getKursi_mobil()+" Kursi, ");

            String prc = mds.getHarga_mobil();
            int value = Integer.parseInt(prc);
            String num = NumberFormat.getIntegerInstance(Locale.GERMAN).format(value);
            mobilHarga.setText(num);

            //mobilHarga.setText(mds.getHarga_mobil());
            Glide.with(activity).load(mds.getImage_mobil()).asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.mipmap.ic_launcher)
                .into(mobil_image);

            return convertView;
    }

    //====================================== Fungsi Filtering EditText Cari Country ================================
    public void filter(String text) {
        text = text.toLowerCase(Locale.getDefault());
        list.clear();
        if (text.length() == 0){
            list.addAll(arrayList);
        } else {
            for (MobilDataSet mds : arrayList){
                if (mds.getNama_mobil().toLowerCase(Locale.getDefault()).contains(text)){
                    list.add(mds);
                }
            }
        }
        notifyDataSetChanged();
    }
}
