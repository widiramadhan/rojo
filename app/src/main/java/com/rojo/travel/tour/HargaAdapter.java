package com.rojo.travel.tour;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.rojo.travel.R;
import com.rojo.travel.functions.Fungsi;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

/**
 * Created by kangyasin on 06/01/2018.
 */

public class HargaAdapter extends ArrayAdapter<HargaDataSet> {

    //the harga list that will be displayed
    private List<HargaDataSet> hargaList;

    //the context object
    private Context mCtx;
    String paketwisata;
    Fungsi fungsi = new Fungsi();
    //here we are getting the hargalist and context
    //so while creating the object of this adapter class we need to give hargalist and context
    public HargaAdapter(List<HargaDataSet> hargaList, Context mCtx) {
        super(mCtx, R.layout.list_harga, hargaList);
        this.hargaList = hargaList;
        this.mCtx = mCtx;
    }

    //this method will return the list item
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //getting the layoutinflater
        final LayoutInflater inflater = LayoutInflater.from(mCtx);

        //creating a view with our xml layout
        final View listViewItem = inflater.inflate(R.layout.list_harga, null, true);

        //getting text views
        TextView namapaketharga = listViewItem.findViewById(R.id.namapaketharga);
        TextView namaindividual = listViewItem.findViewById(R.id.namaindividual);
        TextView hargaindividual = listViewItem.findViewById(R.id.hargai);

        TextView namafamily = listViewItem.findViewById(R.id.namafamily);
        TextView hargafamily = listViewItem.findViewById(R.id.hargaf);

        TextView namagroup = listViewItem.findViewById(R.id.namagroup);
        TextView hargagroup = listViewItem.findViewById(R.id.hargag);

        Button pilihindividual = listViewItem.findViewById(R.id.pilihindividual);
        Button pilihfamily = listViewItem.findViewById(R.id.pilihfamily);
        Button pilihgroup = listViewItem.findViewById(R.id.pilihgroup);


        final EditText id_paket = listViewItem.findViewById(R.id.id_paket);
        final EditText id_kelas_harga = listViewItem.findViewById(R.id.id_kelas_harga);
        final EditText nama_tour = listViewItem.findViewById(R.id.nama_tour);
        final EditText jenisindividual = listViewItem.findViewById(R.id.jenisindividual);
        final EditText jenisfamily = listViewItem.findViewById(R.id.jenisfamily);
        final EditText jenisgroup = listViewItem.findViewById(R.id.jenisgroup);

        //Getting the harga for the specified position
        final HargaDataSet harga = hargaList.get(position);
        //setting harga values to textviews
        namapaketharga.setText(harga.getPaket_wisata_kelas_paket()+" - "+harga.getMarket_paket());
        namaindividual.setText("Harga Individual");
        namafamily.setText("Harga Family");
        namagroup.setText("Harga Group");
        id_paket.setText(harga.getId_paket());
        id_kelas_harga.setText(harga.getId_paket_wisata_kelas_paket());
        hargaindividual.setText(fungsi.ubahharga(harga.getHarga_individual()));
        hargafamily.setText(fungsi.ubahharga(harga.getHarga_family()));
        hargagroup.setText(fungsi.ubahharga(harga.getHarga_group()));
        nama_tour.setText(harga.getNama_tour());



        pilihindividual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> dt = new HashMap<String, String>();
                dt.put("id_paket", id_paket.getText().toString());
                dt.put("nama_tour", nama_tour.getText().toString());
                dt.put("jenis_paket", jenisindividual.getText().toString());
                dt.put("info_paket", harga.getPaket_wisata_kelas_paket()+" - "+harga.getMarket_paket());
                dt.put("harga", fungsi.ubahharga(harga.getHarga_individual()));

                JSONObject data = new JSONObject(dt);

                Intent intent = new Intent(getContext(), DataPemesanTour.class);
                intent.putExtra("dataPesanan", data.toString());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mCtx.startActivity(intent);
            }
        });

        pilihfamily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> dt = new HashMap<String, String>();
                dt.put("id_paket", id_paket.getText().toString());
                dt.put("nama_tour", nama_tour.getText().toString());
                dt.put("jenis_paket", jenisfamily.getText().toString());
                dt.put("info_paket", harga.getPaket_wisata_kelas_paket()+" - "+harga.getMarket_paket());
                dt.put("harga", fungsi.ubahharga(harga.getHarga_family()));

                JSONObject data = new JSONObject(dt);

                Intent intent = new Intent(getContext(), DataPemesanTour.class);
                intent.putExtra("dataPesanan", data.toString());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mCtx.startActivity(intent);
            }
        });

        pilihgroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> dt = new HashMap<String, String>();
                dt.put("id_paket", id_paket.getText().toString());
                dt.put("nama_tour", nama_tour.getText().toString());
                dt.put("jenis_paket", jenisgroup.getText().toString());
                dt.put("info_paket", harga.getPaket_wisata_kelas_paket()+" - "+harga.getMarket_paket());
                dt.put("harga", fungsi.ubahharga(harga.getHarga_group()));

                JSONObject data = new JSONObject(dt);

                Intent intent = new Intent(getContext(), DataPemesanTour.class);
                intent.putExtra("dataPesanan", data.toString());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                mCtx.startActivity(intent);
            }
        });





        //returning the listitem
        return listViewItem;
    }
}
