package com.rojo.travel.tour;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rojo.travel.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HargaTour extends AppCompatActivity {


    ListView listView;
    List<HargaDataSet> hargaList;
    Bundle bundle = new Bundle();
    private HargaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_harga_tour);

        listView = (ListView) findViewById(R.id.listView);
        hargaList = new ArrayList<>();
        bundle = getIntent().getExtras();

        final String namaTour = bundle.getString("nama_tour");

        final String JSON_URL = "http://m.rojo.id/Api/detailPaketWisataget?id_paket="+bundle.getString("id_paket");
        loadDataHarga(JSON_URL, namaTour);
    }

    private void loadDataHarga(final String JSON_URL, final  String namaTour) {

        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.INVISIBLE);
                        if (response != null) {

                            try {
                                JSONObject jsonObj = new JSONObject(response);
                                JSONObject jsonResult = jsonObj.getJSONObject("paket_detail");
                                JSONArray hargaDetail = jsonResult.getJSONArray("harga_paket");
                                for (int i = 0; i < hargaDetail.length(); i++) {
                                    JSONObject hargaObject = hargaDetail.getJSONObject(i);
                                    //Toast.makeText(getApplicationContext(),hargaObject.toString(),Toast.LENGTH_LONG).show();
                                    HargaDataSet hargas = new HargaDataSet();
                                            hargas.setNama_tour(namaTour);
                                            hargas.setId_paket(hargaObject.getString("id_paket"));
                                            hargas.setId_paket_wisata_kelas_paket(hargaObject.getString("id_paket_wisata_kelas_paket"));
                                            hargas.setPaket_wisata_jenis_paket(hargaObject.getString("paket_wisata_jenis_paket"));
                                            hargas.setPaket_wisata_kelas_paket(hargaObject.getString("paket_wisata_kelas_paket"));
                                            hargas.setHarga_individual(hargaObject.getString("harga_individual"));
                                            hargas.setHarga_family(hargaObject.getString("harga_family"));
                                            hargas.setHarga_group(hargaObject.getString("harga_group"));
                                            hargas.setMarket_paket(hargaObject.getString("namamarket"));
                                    hargaList.add(hargas);
                                }
                                adapter = new HargaAdapter(hargaList, getApplicationContext());
                                adapter.notifyDataSetChanged();
                                listView.setAdapter(adapter);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }else{
                            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(HargaTour.this);
        requestQueue.add(stringRequest);
    }

    public void onBackPressed()
    {
        // code here to show dialog
        super.onBackPressed();  // optional depending on your needs
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }
}
