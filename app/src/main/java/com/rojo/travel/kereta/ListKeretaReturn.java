package com.rojo.travel.kereta;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.rojo.travel.R;
import com.rojo.travel.functions.VolleyObjectResult;
import com.rojo.travel.functions.VolleyObjectService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kangyasin on 30/10/2017.
 */

public class ListKeretaReturn extends AppCompatActivity {

    ListView listPesawat;
    String url = "http://m.rojo.id/Api/getJadwalKereta";
    String token;
    private KeretaReturnAdapter keretaReturnAdapter;
    private List<KeretaReturnDataSet> listReturn = new ArrayList<KeretaReturnDataSet>();
    SharedPreferences pref;
    JSONObject data = null;
    VolleyObjectResult vor;
    VolleyObjectService vos;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_kereta);
        setTitle("Pulang");
        actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_USE_LOGO | ActionBar.DISPLAY_SHOW_TITLE);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#029a48")));
        actionBar.setIcon(R.drawable.ic_trainout);
        listPesawat = (ListView) findViewById(R.id.listPesawat);

        pref = getApplicationContext().getSharedPreferences("jsons", 0);
        try {
            data = new JSONObject(pref.getString("datas", null));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        vor = new VolleyObjectResult() {
            @Override
            public void resSuccess(String requestType, JSONObject response) {
                //Toast.makeText(ListPenerbangan.this, response.toString(), Toast.LENGTH_LONG).show();
                try {
                    token = response.getString("token");
                    //Toast.makeText(ListPenerbangan.this, response.toString(), Toast.LENGTH_LONG).show();
                    JSONArray jsonArray = response.getJSONArray("data_flight_ret");
                    for (int i=0; i<jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        KeretaReturnDataSet prds = new KeretaReturnDataSet();
                        prds.setTrain_name(jsonObject.getString("train_name"));
                        prds.setArr_city(jsonObject.getString("arrival_city_name"));
                        prds.setDep_city(jsonObject.getString("departure_city_name"));
                        prds.setPrice_total(jsonObject.getString("getPrice_total"));
                        prds.setTrain_id(jsonObject.getString("train_id"));
                        prds.setFormatted_schedule_date(jsonObject.getString("depdate"));
                        prds.setToken(token);
                        listReturn.add(prds);
                    }
                    keretaReturnAdapter = new KeretaReturnAdapter(ListKeretaReturn.this, listReturn);
                    keretaReturnAdapter.notifyDataSetChanged();
                    listPesawat.setAdapter(keretaReturnAdapter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void resError(String requestType, VolleyError error) {
                Toast.makeText(ListKeretaReturn.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        };
        vos = new VolleyObjectService(vor, ListKeretaReturn.this);
        vos.postJsonObject("POSTCALL", url, data);

        keretaReturnAdapter = new KeretaReturnAdapter(ListKeretaReturn.this, listReturn);
        keretaReturnAdapter.notifyDataSetChanged();
        listPesawat.setAdapter(keretaReturnAdapter);
    }

}
