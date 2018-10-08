package com.rojo.travel.kereta;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
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
 * Created by kangyasin on 30/10/2017.
 */

public class ListKereta extends AppCompatActivity {

    ListView listKereta;
    VolleyObjectResult vor;
    VolleyObjectService vos;
    JSONObject data = null;
    String url = "http://m.rojo.id/api/getjadwalkereta";
    String token, dateDep, dateArr;
    private List<KeretaDataSet> list = new ArrayList<KeretaDataSet>();
    private KeretaAdapter keretaAdapter;
    SharedPreferences prefserencess, prefs;
    ActionBar actionBar;
    Fungsi fungsi = new Fungsi();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_kereta);
        setTitle("Pilih Kereta");
        actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_USE_LOGO | ActionBar.DISPLAY_SHOW_TITLE);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#029a48")));
        actionBar.setIcon(R.drawable.ic_trainin);

        listKereta = (ListView) findViewById(R.id.KeretaList);
        final KeretaDataSet pds = new KeretaDataSet();
        prefs = getApplicationContext().getSharedPreferences("jsons", 0);
        try {
            data = new JSONObject(prefs.getString("datas", null));
            pds.setDateDep(data.getString("departureDate"));
            pds.setDateArr(data.getString("ReturnDate"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Toast.makeText(ListKereta.this, data.toString(), Toast.LENGTH_LONG).show();
        prefserencess = getApplicationContext().getSharedPreferences("jumPenumpangs", 0);
        if (prefserencess.getInt("returnDates", 0) == 0) {
            vor = new VolleyObjectResult() {
                @Override
                public void resSuccess(String requestType, JSONObject response) {
                    //Toast.makeText(ListKereta.this, response.toString(), Toast.LENGTH_LONG).show();
                    try {
                        token = response.getString("token");
                        dateDep = response.getString("tgl_berangkat");
                        dateArr = response.getString("tgl_pulang");
                       //Toast.makeText(ListKereta.this, response.toString(), Toast.LENGTH_LONG).show();
                        JSONArray jsonArray = response.getJSONArray("data_train_dep");
                        //Toast.makeText(ListKereta.this, jsonArray.toString(), Toast.LENGTH_LONG).show();
                        for (int i=0; i<jsonArray.length(); i++) {
                            JSONObject object =  jsonArray.getJSONObject(i);
                            KeretaDataSet pds = new KeretaDataSet();
                            pds.setTrain_name(object.getString("train_name"));
                            pds.setArr_city(object.getString("arrival_station"));
                            pds.setDep_city(object.getString("departure_station"));
                            pds.setPrice_total(object.getString("price_total_clean"));
                            pds.setDeparture_time(object.getString("departure_time"));
                            pds.setArrival_time(object.getString("arrival_time"));
                            pds.setArrival_station(object.getString("arrival_station"));
                            pds.setDeparture_station(object.getString("departure_station"));
                            pds.setDetail_availability(object.getString("detail_availability"));
                            pds.setTrain_id(object.getString("train_id"));
                            pds.setId_kereta(object.getString("id"));
                            pds.setSubClass(object.getString("subclass_name"));
                            pds.setFormatted_schedule_date(object.getString("formatted_schedule_date"));
                            pds.setToken(token);
                            pds.setDateDep(dateDep);
                            pds.setDateArr(dateArr);
                            list.add(pds);
                        }
                        keretaAdapter = new KeretaAdapter(ListKereta.this, list);
                        keretaAdapter.notifyDataSetChanged();
                        listKereta.setAdapter(keretaAdapter);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void resError(String requestType, VolleyError error) {
                    View view = findViewById(R.id.coordinator);
                    String message = "Maaf kereta tidak tersedia silahkan ganti tanggal perjalanan anda.";
                    fungsi.showSnackbar(view, message);
                    //Toast.makeText(ListKereta.this, "Maaf kereta tidak tersedia silahkan ganti tanggal perjalanan anda.", Toast.LENGTH_LONG).show();
                }
            };
            vos = new VolleyObjectService(vor, ListKereta.this);
            vos.postJsonObject("POSTCALL", url, data);
        } else if (prefserencess.getInt("returnDates", 0) == 1) {
            setTitle("Berangkat");
            vor = new VolleyObjectResult() {
                @Override
                public void resSuccess(String requestType, JSONObject response) {
                    //Toast.makeText(ListKereta.this, response.toString(), Toast.LENGTH_LONG).show();
                    try {
                        token = response.getString("token");
                        //Toast.makeText(ListKereta.this, response.toString(), Toast.LENGTH_LONG).show();
                        JSONArray jsonArray = response.getJSONArray("data_dep");
                        for (int i=0; i<jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            KeretaDataSet pds = new KeretaDataSet();
                            pds.setTrain_name(jsonObject.getString("train_name"));
                            pds.setArr_city(jsonObject.getString("arrival_station"));
                            pds.setDep_city(jsonObject.getString("departure_station"));
                            pds.setPrice_total(jsonObject.getString("price_total_clean"));
                            pds.setDeparture_time(jsonObject.getString("departure_time"));
                            pds.setArrival_time(jsonObject.getString("arrival_time"));
                            pds.setArrival_station(jsonObject.getString("arrival_station"));
                            pds.setDeparture_station(jsonObject.getString("departure_station"));
                            pds.setDetail_availability(jsonObject.getString("detail_availability"));
                            pds.setTrain_id(jsonObject.getString("train_id"));
                            pds.setId_kereta(jsonObject.getString("id"));
                            pds.setSubClass(jsonObject.getString("subclass_name"));
                            pds.setFormatted_schedule_date(jsonObject.getString("formatted_schedule_date"));
                            pds.setToken(token);
                            list.add(pds);
                        }
                        keretaAdapter = new KeretaAdapter(ListKereta.this, list);
                        keretaAdapter.notifyDataSetChanged();
                        listKereta.setAdapter(keretaAdapter);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void resError(String requestType, VolleyError error) {
                    Toast.makeText(ListKereta.this, error.toString(), Toast.LENGTH_LONG).show();
                }
            };
            vos = new VolleyObjectService(vor, ListKereta.this);
            vos.postJsonObject("POSTCALL", url, data);
        }

        //Toast.makeText(ListKereta.this, url+"/n"+data, Toast.LENGTH_LONG).show();
    }

}
