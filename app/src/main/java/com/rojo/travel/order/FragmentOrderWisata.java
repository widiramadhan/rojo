package com.rojo.travel.order;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.rojo.travel.R;
import com.rojo.travel.functions.Fungsi;
import com.rojo.travel.functions.VolleyObjectResult;
import com.rojo.travel.functions.VolleyObjectService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by limadawai on 27/12/17.
 */

public class FragmentOrderWisata extends Fragment {

    VolleyObjectResult vor;
    VolleyObjectService vos;
    JSONObject data = null;
    Fungsi fn = new Fungsi();
    String token, domestik;
    SharedPreferences preferences, pref;
    ActionBar actionBar;
    ProgressBar progressBar;
    private ListWisataAdapter listWisataAdapter;
    View rootView, rootViewNull;
    ListView listView;
    private List<ListWisataDataSet> listWisata = new ArrayList<ListWisataDataSet>();

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        String email = fn.getEmail(getActivity());
        String uid = fn.getDeviceUniqueID(getActivity());
        String url = "http://m.rojo.id/Apimember/getWisataOrder?uid="+uid+"&email="+email;
        rootView = inflater.inflate(R.layout.activity_pesanan_wisata, container, false);
        listView = (ListView) rootView.findViewById(R.id.list);
        vor = new VolleyObjectResult() {
            @Override
            public void resSuccess(String requestType, JSONObject response) {
                //Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_LONG).show();
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for (int i=0; i<jsonArray.length(); i++) {
                        JSONObject job = jsonArray.getJSONObject(i);
                        ListWisataDataSet lwds = new ListWisataDataSet();
                        lwds.setKode_invoice_pemesanan(job.getString("kode_invoice_pemesanan"));
                        lwds.setPaket_wisata_nama(job.getString("paket_wisata_nama"));
                        lwds.setJumlah_peserta(job.getString("jumlah_peserta"));
                        lwds.setHarga_paket(job.getString("harga_paket"));
                        lwds.setPaket_wisata_tanggal_berangkat(job.getString("paket_wisata_tanggal_berangkat"));
                        lwds.setTotal_harga_pembayaran_paket(job.getString("total_harga_pembayaran_paket"));
                        //listWisata.clear();
                        listWisata.add(lwds);
                    }
                    listWisataAdapter = new ListWisataAdapter(getActivity(), listWisata);
                    listWisataAdapter.notifyDataSetChanged();
                    listView.setAdapter(listWisataAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void resError(String requestType, VolleyError error) {
                //Toast.makeText(getActivity(), "Connection problem!", Toast.LENGTH_LONG).show();
            }
        };
        vos = new VolleyObjectService(vor, getActivity());
        vos.postJsonObject("GETCALL", url, data);
        return rootView;
    }


}
