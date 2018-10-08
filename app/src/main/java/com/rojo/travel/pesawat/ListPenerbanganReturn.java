package com.rojo.travel.pesawat;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
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

public class ListPenerbanganReturn extends AppCompatActivity {

    //String url = "http://m.rojo.id/api/searchflight";
    String url = "";
    String urlFunct = "Api/searchflight";
    ListView listPesawat;
    String token, domestik, tgl_berangkat, tgl_pulang;
    private PenerbanganReturnAdapter penerbanganReturnAdapter;
    private List<PenerbanganReturnDataSet> listReturn = new ArrayList<PenerbanganReturnDataSet>();
    SharedPreferences pref;
    JSONObject data = null;
    VolleyObjectResult vor;
    VolleyObjectService vos;
    ActionBar actionBar;
    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_pesawat);
        setTitle("Pulang");
        actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_USE_LOGO | ActionBar.DISPLAY_SHOW_TITLE);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#029a48")));
        actionBar.setIcon(R.drawable.ic_landing);
        listPesawat = (ListView) findViewById(R.id.listPesawat);

        MyApplication application=(MyApplication)getApplication();
        String PathWS=application.sgPathSrv;
        url=PathWS+urlFunct;


        final PenerbanganReturnDataSet prds = new PenerbanganReturnDataSet();
        pref = getApplicationContext().getSharedPreferences("json", 0);
        try {
            data = new JSONObject(pref.getString("data", null));
            prds.setDeparture_date(data.getString("departureDate"));
            prds.setReturn_date(data.getString("ReturnDate"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Loading...");
        showDialog();
        vor = new VolleyObjectResult() {
            @Override
            public void resSuccess(String requestType, JSONObject response) {
                hideDialog();
                //Toast.makeText(ListPenerbangan.this, response.toString(), Toast.LENGTH_LONG).show();
                try {
                    token = response.getString("token");
                    domestik = response.getString("domestik");
                    tgl_berangkat = response.getString("tgl_berangkat");
                    tgl_pulang = response.getString("tgl_pulang");
                    //Toast.makeText(ListPenerbangan.this, response.toString(), Toast.LENGTH_LONG).show();
                    JSONArray jsonArray = response.getJSONArray("data_flight_ret");
                    for (int i=0; i<jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        final PenerbanganReturnDataSet prds = new PenerbanganReturnDataSet();
                        prds.setAirlines_name(jsonObject.getString("airlines_name"));
                        prds.setFlight_number(jsonObject.getString("flight_number"));
                        prds.setStop(jsonObject.getString("stop"));
                        prds.setPrice_value(jsonObject.getString("price_value"));
                        prds.setPrice_adult(jsonObject.getString("price_adult"));
                        prds.setPrice_child(jsonObject.getString("price_child"));
                        prds.setPrice_infant(jsonObject.getString("price_infant"));
                        prds.setAirlines_real_name(jsonObject.getString("airlines_real_name"));
                        prds.setSimple_departure_time(jsonObject.getString("simple_departure_time"));
                        prds.setSimple_arrival_time(jsonObject.getString("simple_arrival_time"));
                        prds.setDeparture_city_name(jsonObject.getString("departure_city_name"));
                        prds.setArrival_city_name(jsonObject.getString("arrival_city_name"));
                        prds.setFull_via(jsonObject.getString("full_via"));
                        prds.setImage(jsonObject.getString("image"));
                        prds.setDeparture_flight_date_str(jsonObject.getString("departure_flight_date_str"));
                        prds.setFlight_id(jsonObject.getString("flight_id"));
                        prds.setToken(token);
                        prds.setDomestik(domestik);
                        prds.setDeparture_date(tgl_berangkat);
                        prds.setReturn_date(tgl_pulang);
                        listReturn.add(prds);
                    }
                    penerbanganReturnAdapter = new PenerbanganReturnAdapter(ListPenerbanganReturn.this, listReturn);
                    penerbanganReturnAdapter.notifyDataSetChanged();
                    listPesawat.setAdapter(penerbanganReturnAdapter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void resError(String requestType, VolleyError error) {
                Toast.makeText(ListPenerbanganReturn.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        };
        vos = new VolleyObjectService(vor, ListPenerbanganReturn.this);
        vos.postJsonObject("POSTCALL", url, data);

        penerbanganReturnAdapter = new PenerbanganReturnAdapter(ListPenerbanganReturn.this, listReturn);
        penerbanganReturnAdapter.notifyDataSetChanged();
        listPesawat.setAdapter(penerbanganReturnAdapter);
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
