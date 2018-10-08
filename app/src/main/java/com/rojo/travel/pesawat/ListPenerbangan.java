package com.rojo.travel.pesawat;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.rojo.travel.MyApplication;
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

public class ListPenerbangan extends AppCompatActivity {

    //ListView listPesawat;
    VolleyObjectResult vor;
    VolleyObjectService vos;
    JSONObject data = null;

    //String url = "http://m.rojo.id/Api/searchFlight";
    String url = "";
    String urlFunct = "Api/searchFlight";
    String token, domestik, tgl_berangkat, tgl_pulang;
    private List<PenerbanganDataSet> list = new ArrayList<PenerbanganDataSet>();
    private PenerbanganAdapter penerbanganAdapter;
    SharedPreferences preferences, pref;
    ActionBar actionBar;
    ProgressBar progressBar;
    ProgressDialog pDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_pesawat);
        setTitle("Pilih Maskapai");
        actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_USE_LOGO | ActionBar.DISPLAY_SHOW_TITLE);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#029a48")));
        actionBar.setIcon(R.drawable.ic_flightout);

        MyApplication application=(MyApplication)getApplication();
        String PathWS=application.sgPathSrv;
        url=PathWS+urlFunct;

        progressBar = (ProgressBar) findViewById(R.id.prgbarlistpesawat);
        final ListView listPesawat = (ListView) findViewById(R.id.listPesawat);
        PenerbanganDataSet pds = new PenerbanganDataSet();
        pref = getApplicationContext().getSharedPreferences("json", 0);
        try {
            data = new JSONObject(pref.getString("data", null));
            pds.setDeparture_date(data.getString("departureDate"));
            pds.setReturn_date(data.getString("ReturnDate"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Toast.makeText(ListPenerbangan.this, data.toString(), Toast.LENGTH_LONG).show();
        preferences = getApplicationContext().getSharedPreferences("jumPenumpang", 0);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Loading...");
        showDialog();
        if (preferences.getInt("returnDate", 0) == 0) {
            vor = new VolleyObjectResult() {
                @Override
                public void resSuccess(String requestType, JSONObject response) {
                    progressBar.setVisibility(View.GONE);
                    hideDialog();
                    //Toast.makeText(ListPenerbangan.this, response.toString(), Toast.LENGTH_LONG).show();
                    if (response != null) {
                        try {
                            token = response.getString("token");
                            domestik = response.getString("domestik");
                            tgl_berangkat = response.getString("tgl_berangkat");
                            tgl_pulang = response.getString("tgl_pulang");
                            JSONArray jsonArray = response.getJSONArray("data_flight_dep");
                            //Toast.makeText(ListPenerbangan.this, jsonArray.toString(), Toast.LENGTH_LONG).show();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                PenerbanganDataSet pds = new PenerbanganDataSet();
                                pds.setFlight_number(jsonObject.getString("flight_number"));
                                pds.setAirlines_name(jsonObject.getString("airlines_name"));
                                pds.setAirlines_real_name(jsonObject.getString("airlines_real_name"));
                                pds.setStop(jsonObject.getString("stop"));
                                pds.setPrice_value(jsonObject.getString("price_value"));
                                pds.setPrice_adult(jsonObject.getString("price_adult"));
                                pds.setPrice_child(jsonObject.getString("price_child"));
                                pds.setPrice_infant(jsonObject.getString("price_infant"));
                                pds.setSimple_departure_time(jsonObject.getString("simple_departure_time"));
                                pds.setSimple_arrival_time(jsonObject.getString("simple_arrival_time"));
                                pds.setDeparture_city_name(jsonObject.getString("departure_city_name"));
                                pds.setArrival_city_name(jsonObject.getString("arrival_city_name"));
                                pds.setFull_via(jsonObject.getString("full_via"));
                                pds.setImage(jsonObject.getString("image"));
                                pds.setDeparture_flight_date_str(jsonObject.getString("departure_flight_date_str"));
                                pds.setFlight_id(jsonObject.getString("flight_id"));
                                pds.setToken(token);
                                pds.setDomestik(domestik);
                                pds.setDeparture_date(tgl_berangkat);
                                pds.setReturn_date(tgl_pulang);
                                list.add(pds);
                            }
                            penerbanganAdapter = new PenerbanganAdapter(ListPenerbangan.this, list);
                            penerbanganAdapter.notifyDataSetChanged();
                            listPesawat.setAdapter(penerbanganAdapter);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                @Override
                public void resError(String requestType, VolleyError error) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(ListPenerbangan.this, "Penerbangan Tidak Tersedia", Toast.LENGTH_SHORT).show();
                    finish();
                }
            };
            vos = new VolleyObjectService(vor, ListPenerbangan.this);
            vos.postJsonObject("POSTCALL", url, data);
            progressBar.setVisibility(View.VISIBLE);
        } else if (preferences.getInt("returnDate", 0) == 1) {
            setTitle("Berangkat");
            vor = new VolleyObjectResult() {
                @Override
                public void resSuccess(String requestType, JSONObject response) {
                    progressBar.setVisibility(View.GONE);
                    hideDialog();
                    //Toast.makeText(ListPenerbangan.this, response.toString(), Toast.LENGTH_LONG).show();
                    try {
                        token = response.getString("token");
                        domestik = response.getString("domestik");
                        tgl_berangkat = response.getString("tgl_berangkat");
                        tgl_pulang = response.getString("tgl_pulang");
                        //Toast.makeText(ListPenerbangan.this, response.toString(), Toast.LENGTH_LONG).show();
                        JSONArray jsonArray = response.getJSONArray("data_flight_dep");
                        for (int i=0; i<jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            PenerbanganDataSet pds = new PenerbanganDataSet();
                            pds.setFlight_number(jsonObject.getString("flight_number"));
                            pds.setStop(jsonObject.getString("stop"));
                            pds.setPrice_value(jsonObject.getString("price_value"));
                            pds.setPrice_adult(jsonObject.getString("price_adult"));
                            pds.setPrice_child(jsonObject.getString("price_child"));
                            pds.setPrice_infant(jsonObject.getString("price_infant"));
                            pds.setAirlines_name(jsonObject.getString("airlines_name"));
                            pds.setAirlines_real_name(jsonObject.getString("airlines_real_name"));
                            pds.setSimple_departure_time(jsonObject.getString("simple_departure_time"));
                            pds.setSimple_arrival_time(jsonObject.getString("simple_arrival_time"));
                            pds.setDeparture_city_name(jsonObject.getString("departure_city_name"));
                            pds.setArrival_city_name(jsonObject.getString("arrival_city_name"));
                            pds.setFull_via(jsonObject.getString("full_via"));
                            pds.setImage(jsonObject.getString("image"));
                            pds.setDeparture_flight_date_str(jsonObject.getString("departure_flight_date_str"));
                            pds.setFlight_id(jsonObject.getString("flight_id"));
                            pds.setToken(token);
                            pds.setDomestik(domestik);
                            pds.setDeparture_date(tgl_berangkat);
                            pds.setReturn_date(tgl_pulang);
                            list.add(pds);
                        }
                        penerbanganAdapter = new PenerbanganAdapter(ListPenerbangan.this, list);
                        penerbanganAdapter.notifyDataSetChanged();
                        listPesawat.setAdapter(penerbanganAdapter);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void resError(String requestType, VolleyError error) {
                    //progressBar.setVisibility(View.GONE);
                    Toast.makeText(ListPenerbangan.this, "Penerbangan Tidak Tersedia", Toast.LENGTH_SHORT).show();
                    finish();
                }
            };
            vos = new VolleyObjectService(vor, ListPenerbangan.this);
            vos.postJsonObject("POSTCALL", url, data);
            //progressBar.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

}
