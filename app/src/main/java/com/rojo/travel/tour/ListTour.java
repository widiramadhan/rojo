package com.rojo.travel.tour;

import android.content.Intent;
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
import com.rojo.travel.R;
import com.rojo.travel.functions.VolleyObjectResult;
import com.rojo.travel.functions.VolleyObjectService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kangyasin on 19/11/2017.
 */

public class ListTour extends AppCompatActivity {

    ListView listTour;
    VolleyObjectResult vor;
    VolleyObjectService vos;
    JSONObject data = null;
    String url = "http://m.rojo.id/Api/paketWisata";
    String token;
    private List<ListTourDataSet> list = new ArrayList<ListTourDataSet>();
    private AdapterListTour tourAdapter;
    ActionBar actionBar;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_tour);
        actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_USE_LOGO | ActionBar.DISPLAY_SHOW_TITLE);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#029a48")));
        listTour = (ListView) findViewById(R.id.listTour);

        Bundle extras = getIntent().getExtras();
        try {
            data = new JSONObject(extras.getString("json"));
            //Toast.makeText(ListTour.this, data.toString(), Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        vor = new VolleyObjectResult() {
            @Override
            public void resSuccess(String requestType, JSONObject response) {

                //Toast.makeText(ListTour.this, response.toString(), Toast.LENGTH_LONG).show();
                progressBar = (ProgressBar) findViewById(R.id.proBar);
                try {
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    JSONArray jsonArray = response.getJSONArray("paket");
                    int id_count = 0;
                    if(jsonArray.length() > id_count) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            ListTourDataSet dtour = new ListTourDataSet();
                            dtour.setId(jsonObject.getString("id"));
                            dtour.setImage(jsonObject.getString("image"));
                            dtour.setJudul(jsonObject.getString("nama"));
                            dtour.setRate(jsonObject.getString("rating"));
                            dtour.setHarga(jsonObject.getString("harga"));
                            dtour.setHargadouble(jsonObject.getString("harga_family"));
                            dtour.setHargagroup(jsonObject.getString("harga_group"));
                            dtour.setDeskripsi(jsonObject.getString("desc"));
                            dtour.setInclude(jsonObject.getString("include"));
                            dtour.setExclude(jsonObject.getString("exclude"));
                            dtour.setTerm(jsonObject.getString("term"));
                            list.add(dtour);
                        }
                        tourAdapter = new AdapterListTour(ListTour.this, list);
                        tourAdapter.notifyDataSetChanged();
                        listTour.setAdapter(tourAdapter);
                    }else {
                        Thread.sleep(2000);
                        Toast.makeText(ListTour.this, "Maaf, data tour tdk ada.\n Silahkan ubah pencarian", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(ListTour.this, ActivityTour.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void resError(String requestType, VolleyError error) {
                Toast.makeText(ListTour.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        };
        vos = new VolleyObjectService(vor, ListTour.this);
        vos.postJsonObject("POSTCALL", url, data);

    }

    public void onBackPressed()
    {
        // code here to show dialog
        super.onBackPressed();  // optional depending on your needs
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

}
