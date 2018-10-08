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

public class FragmentOrderKereta extends Fragment {

    VolleyObjectResult vor;
    VolleyObjectService vos;
    JSONObject data = null;
    Fungsi fn = new Fungsi();
    String token, domestik;
    SharedPreferences preferences, pref;
    ActionBar actionBar;
    ProgressBar progressBar;
    private ListKeretaAdapter listKeretaAdapter;
    View rootView, rootViewNull;
    ListView listView;
    private List<ListKeretaDataSet> listKereta = new ArrayList<ListKeretaDataSet>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        String email = fn.getEmail(getActivity());
        String uid = fn.getDeviceUniqueID(getActivity());
        String url = "http://m.rojo.id/apimember/getTrainOrder?uid="+uid+"&email="+email;
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
                        ListKeretaDataSet lkds = new ListKeretaDataSet();
                        lkds.setNamaKereta(job.getString(""));
                        lkds.setTglBrgkt(job.getString(""));
                        lkds.setTujuan(job.getString(""));
                        listKereta.add(lkds);
                    }
                    listKeretaAdapter = new ListKeretaAdapter(getActivity(), listKereta);
                    listKeretaAdapter.notifyDataSetChanged();
                    listView.setAdapter(listKeretaAdapter);
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
