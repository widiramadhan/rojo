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

public class FragmentOrderMobil extends Fragment {

    VolleyObjectResult vor;
    VolleyObjectService vos;
    JSONObject data = null;
    Fungsi fn = new Fungsi();
    String token, domestik;
    SharedPreferences preferences, pref;
    ActionBar actionBar;
    ProgressBar progressBar;
    private ListMobilAdapter listMobilAdapter;
    View rootView, rootViewNull;
    ListView listView;
    private List<ListMobilDataSet> listMobil = new ArrayList<ListMobilDataSet>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        String uid = fn.getDeviceUniqueID(getActivity());
        String url = "http://m.rojo.id/Apimember/getRentalOrder?uid="+uid;
        rootView = inflater.inflate(R.layout.activity_pesanan_mobil, container, false);
        //rootViewNull = inflater.inflate(R.layout.activity_pesanan_pesawat_null, container, false);
        listView = (ListView) rootView.findViewById(R.id.list);
        vor = new VolleyObjectResult() {
            @Override
            public void resSuccess(String requestType, JSONObject response) {
                //Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_LONG).show();
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for (int i=0; i<jsonArray.length(); i++) {
                        JSONObject job = jsonArray.getJSONObject(i);
                        ListMobilDataSet lmds = new ListMobilDataSet();
                        lmds.setId(job.getString("id"));
                        lmds.setNama(job.getString("nama"));
                        lmds.setNo_hp(job.getString("no_hp"));
                        lmds.setEmail(job.getString("email"));
                        lmds.setMobil(job.getString("mobil"));
                        lmds.setTujuan(job.getString("tujuan"));
                        lmds.setTanggal(job.getString("tanggal"));
                        lmds.setPermintaan_khusus(job.getString("permintaan_khusus"));
                        lmds.setStatus(job.getString("status"));
                        lmds.setDate_create(job.getString("date_create"));
                        lmds.setUid(job.getString("uid"));
                        listMobil.clear();
                        listMobil.add(lmds);
                    }
                    listMobilAdapter = new ListMobilAdapter(getActivity(), listMobil);
                    listMobilAdapter.notifyDataSetChanged();
                    listView.setAdapter(listMobilAdapter);
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
