package com.rojo.travel.tour;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.rojo.travel.R;
import com.rojo.travel.functions.VolleyArrayResult;
import com.rojo.travel.functions.VolleyArrayService;
import com.rojo.travel.functions.VolleyObjectResult;
import com.rojo.travel.functions.VolleyObjectService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by kangyasin on 31/10/2017.
 */

public class ActivityTour extends AppCompatActivity {
    private EditText tujuan, cariCountry, paket, provinsiID, cariPaket, paketid, kelas, type, kelasvalue, typevalue;
    private NumberPicker nPaket;
    private ListView listCountry, listPaket, listType, listKelas;
    private ProgressBar progressBar;
    int nmPaket;
    private List<CountryDataSet> list = new ArrayList<CountryDataSet>();
    private List<PaketDataSet> listp = new ArrayList<PaketDataSet>();
    private List<TypeDataSet> listt = new ArrayList<TypeDataSet>();
    private List<KelasDataSet> listk = new ArrayList<KelasDataSet>();
    Button cari;
    VolleyObjectResult volleyObjectResult, vor = null;
    VolleyObjectService volleyObjectService, vos;
    VolleyArrayService vas;
    VolleyArrayResult var = null;

    String lb, apc, lbp, apcp, lt, apt, lk, apk;
    ActionBar actionBar;

    private CountryAdapter countryAdapter;
    private PaketAdapter paketAdapter;
    private KelasAdapter kelasAdapter;
    private TypeAdapter typeAdapter;


    String url = "http://m.rojo.id/Api/masterDestination";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.noActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour);
        actionBar = getSupportActionBar();
        //actionBar.setDisplayOptions(ActionBar.DISPLAY_USE_LOGO | ActionBar.DISPLAY_SHOW_TITLE);
        //actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#029a48")));
        tujuan = (EditText) findViewById(R.id.tujuan);
        provinsiID = (EditText) findViewById(R.id.provinsiID);
        paket = (EditText) findViewById(R.id.paket);
        paketid = (EditText) findViewById(R.id.idPaketwisata);

        kelas = (EditText) findViewById(R.id.kelas);
        kelasvalue = (EditText) findViewById(R.id.kelasvalue);

        type = (EditText) findViewById(R.id.type);
        typevalue = (EditText) findViewById(R.id.typevalue);

        kelasvalue.setVisibility(View.GONE);
        typevalue.setVisibility(View.GONE);
        paketid.setVisibility(View.GONE);

        cari = (Button) findViewById(R.id.submiton);

        cari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (tujuan.getText().toString().equals("")) {
                    tujuan.setError("Tujuan tdk boleh kosong!");
                } else if (paket.getText().toString().equals("")) {
                    paket.setError("Paket tdk boleh kosong!");
                } else if (type.getText().toString().equals("")) {
                    type.setError("Type wisata tdk boleh kosong");
                } else if (kelas.getText().toString().equals("")) {
                    kelas.setError("Kelas tdk boleh kosong");
                }

                String tuj = provinsiID.getText().toString();
                String pkt = paketid.getText().toString();
                String cls = kelasvalue.getText().toString();
                String typ = typevalue.getText().toString();

                HashMap<String, String> dt = new HashMap<String, String>();
                dt.put("id_provinsi", tuj);
                dt.put("jenis_paket", pkt);
                dt.put("id_kelas", cls);
                dt.put("id_type", typ);

                JSONObject data = new JSONObject(dt);

                Intent intent = new Intent(ActivityTour.this, ListTour.class);
                intent.putExtra("json", data.toString());
                startActivity(intent);
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            }
        });

        tujuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.clear();
                AlertDialog.Builder builder = new AlertDialog.Builder(ActivityTour.this, R.style.DialogTheme);
                LayoutInflater inflater = ActivityTour.this.getLayoutInflater();
                View view = inflater.inflate(R.layout.list_country, null, false);
                cariCountry = (EditText) view.findViewById(R.id.cariCountry);
                listCountry = (ListView) view.findViewById(R.id.listCountry);
                progressBar = (ProgressBar) view.findViewById(R.id.proBar);
                builder.setView(view);

                volleyObjectResult = new VolleyObjectResult() {
                    @Override
                    public void resSuccess(String requestType, JSONObject response) {
                        progressBar.setVisibility(View.GONE);
                        try {
                            JSONArray jsonArray = response.getJSONArray("destinasi");
                            for (int i=0; i<jsonArray.length(); i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                CountryDataSet lts = new CountryDataSet();
                                lts.setNama_provinsi(jsonObject.getString("nama_kota_kabupaten"));
                                lts.setId_provinsi(jsonObject.getString("paket_wisata_tujuan"));
                                list.add(lts);
                            }
                            countryAdapter = new CountryAdapter(ActivityTour.this, list);
                            countryAdapter.notifyDataSetChanged();
                            listCountry.setAdapter(countryAdapter);
                            //========================= Action Listener EditText Cari Country =====================
                            cariCountry.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                                @Override
                                public void onTextChanged(CharSequence s, int start, int before, int count) {}
                                @Override
                                public void afterTextChanged(Editable s) {
                                    String text = cariCountry.getText().toString().toLowerCase(Locale.getDefault());
                                    countryAdapter.filter(text);
                                }
                            });
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void resError(String requestType, VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                };
                volleyObjectService = new VolleyObjectService(volleyObjectResult, ActivityTour.this);
                volleyObjectService.getJsonObject("GETCALL", url);
                progressBar.setVisibility(View.VISIBLE);
                final AlertDialog alertDialog = builder.create();
                Window window = alertDialog.getWindow();
                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                alertDialog.show();
                listCountry.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        lb = list.get(position).getNama_provinsi();
                        apc = list.get(position).getId_provinsi();
                        tujuan.setText(lb);
                        provinsiID.setText(apc);
                        alertDialog.dismiss();
                    }
                });
            }
        });


        type.setOnClickListener(new View.OnClickListener() {

            private String[] namatype={"Individual","Group"};
            private String[] typev={"1","2"};

            @Override
            public void onClick(View v) {

                listt.clear();

                AlertDialog.Builder builder = new AlertDialog.Builder(ActivityTour.this, R.style.DialogTheme);
                LayoutInflater inflater = ActivityTour.this.getLayoutInflater();
                View view = inflater.inflate(R.layout.list_type, null, false);
                listType = (ListView) view.findViewById(R.id.listType);
                builder.setView(view);

                for (int i=0; i<namatype.length; i++){
                    TypeDataSet sds = new TypeDataSet();
                    sds.setId_type(typev[i]);
                    sds.setNama_type(namatype[i]);
                    listt.add(sds);
                }

                typeAdapter = new TypeAdapter(ActivityTour.this, listt);
                typeAdapter.notifyDataSetChanged();
                listType.setAdapter(typeAdapter);

                final AlertDialog alertDialog = builder.create();
                Window window = alertDialog.getWindow();
                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                alertDialog.show();
                listType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        lt = listt.get(position).getNama_type();
                        apt = listt.get(position).getId_type();
                        type.setText(lt);
                        typevalue.setText(apt);
                        alertDialog.dismiss();
                    }
                });

            }
        });

        kelas.setOnClickListener(new View.OnClickListener(){

            private String[] namakelas={"Gold","Silver", "Bronze"};
            private String[] kelasval={"1","2","3"};

            @Override
            public void onClick(View v) {
                listk.clear();

                AlertDialog.Builder builder = new AlertDialog.Builder(ActivityTour.this, R.style.DialogTheme);
                LayoutInflater inflater = ActivityTour.this.getLayoutInflater();
                View view = inflater.inflate(R.layout.list_kelas, null, false);
                listKelas = (ListView) view.findViewById(R.id.listKelas);
                builder.setView(view);

                for (int i=0; i<namakelas.length; i++){
                    KelasDataSet kds = new KelasDataSet();
                    kds.setId_kelas(kelasval[i]);
                    kds.setNama_kelas(namakelas[i]);
                    listk.add(kds);
                }

                kelasAdapter = new KelasAdapter(ActivityTour.this, listk);
                kelasAdapter.notifyDataSetChanged();
                listKelas.setAdapter(kelasAdapter);

                final AlertDialog alertDialog = builder.create();
                Window window = alertDialog.getWindow();
                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                alertDialog.show();
                listKelas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        lk = listk.get(position).getNama_kelas();
                        apk = listk.get(position).getId_kelas();
                        kelas.setText(lk);
                        kelasvalue.setText(apk);
                        alertDialog.dismiss();
                    }
                });

            }
        });

        paket.setOnClickListener(new View.OnClickListener() {
            private String[] namapaket={"Open Trip","Normal Package", "Private", "Seat in coach", "Free and easy"};
            private String[] idpaket={"1","2","3","4","5"};
            @Override
            public void onClick(View v) {
                listp.clear();
                AlertDialog.Builder builder = new AlertDialog.Builder(ActivityTour.this, R.style.DialogTheme);
                LayoutInflater inflater = ActivityTour.this.getLayoutInflater();
                View view = inflater.inflate(R.layout.list_paket, null, false);
                listPaket = (ListView) view.findViewById(R.id.listPaket);
                builder.setView(view);
                //Toast.makeText(ActivityTour.this, namapaket[0],Toast.LENGTH_LONG).show();
                for (int i=0; i<namapaket.length; i++){
                    PaketDataSet pds = new PaketDataSet();
                    pds.setId_paket(idpaket[i]);
                    //Toast.makeText(ActivityTour.this, idpaket[i],Toast.LENGTH_LONG).show();
                    pds.setNama_paket(namapaket[i]);
                    listp.add(pds);
                }

                paketAdapter = new PaketAdapter(ActivityTour.this, listp);
                paketAdapter.notifyDataSetChanged();
                listPaket.setAdapter(paketAdapter);

                final AlertDialog alertDialog = builder.create();
                Window window = alertDialog.getWindow();
                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                alertDialog.show();
                listPaket.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        lbp = listp.get(position).getNama_paket();
                        apcp = listp.get(position).getId_paket();
                        //Toast.makeText(ActivityTour.this, apcp,Toast.LENGTH_LONG).show();
                        paket.setText(lbp);
                        paketid.setText(apcp);
                        alertDialog.dismiss();
                    }
                });



            }
        });
    }

    public void onBackPressed()
    {
        // code here to show dialog
        super.onBackPressed();  // optional depending on your needs
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }


}
