package com.rojo.travel.order;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rojo.travel.R;
import com.rojo.travel.pesawat.DetailPemesanan;

import java.util.List;

/**
 * Created by kangyasin on 27/01/2018.
 */

public class ListPesawatAdapter extends BaseAdapter {

    private Activity activity;
    private Context context;
    private LayoutInflater inflater;
    private List<ListPesawatDataSet> list;
    SharedPreferences preferences;

//    public ListPesawatAdapter(Context context, List<ListPesawatDataSet> listPesawat) {
//        this.context = context;
//        this.list = listPesawat;
//    }

    public ListPesawatAdapter(Activity activity, List<ListPesawatDataSet> listPesawat) {
        this.activity = activity;
        this.list = listPesawat;
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
            //inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_pesanan_pesawat, null);
        TextView maskapai = (TextView) convertView.findViewById(R.id.Maskapai);
        ImageView imageMaskapai = (ImageView) convertView.findViewById(R.id.ImageMaskapai);
        TextView tanggal = (TextView) convertView.findViewById(R.id.tanggal);
        TextView bandara = (TextView) convertView.findViewById(R.id.bandara);
        final TextView order_id = (TextView) convertView.findViewById(R.id.order_id);
        final ListPesawatDataSet lds = list.get(position);
        maskapai.setText(lds.getAirlinesName());
        tanggal.setText(lds.getFlightDateStr());
        bandara.setText(lds.getDepCityName()+" - " +lds.getArrCityName());
        order_id.setText(lds.getOrderId());

        //preferences = activity.getSharedPreferences("History", 0);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext().get, pds.getSimple_arrival_time(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(activity, HistoryPesananPesawat.class);
                intent.putExtra("OrderID", order_id.getText());
                activity.startActivity(intent);
            }
        });

        return convertView;
    }


}
