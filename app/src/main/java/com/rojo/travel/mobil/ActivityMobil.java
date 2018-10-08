package com.rojo.travel.mobil;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.rojo.travel.MainActivity;
import com.rojo.travel.R;
import com.rojo.travel.functions.Fungsi;
import com.rojo.travel.functions.VolleyArrayResult;
import com.rojo.travel.functions.VolleyArrayService;
import com.rojo.travel.functions.VolleyObjectResult;
import com.rojo.travel.functions.VolleyObjectService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


/**
 * Created by kangyasin on 06/11/2017.
 */

public class ActivityMobil extends AppCompatActivity {

    private EditText tglsewa, tujuan, cariMobil, mobil, idMobil, namalengkap, email, notelp, permintaan, idTuj;
    Calendar calendar = Calendar.getInstance();

    private Button pesanmobil;

    private NumberPicker nPaket;
    private ListView listMobil, listTujuan;
    private ProgressBar progressBar;
    int nmPaket;
    private List<MobilDataSet> list = new ArrayList<MobilDataSet>();
    private List<TujuanDataSet> listp = new ArrayList<TujuanDataSet>();
    Button cari;
    VolleyObjectResult volleyObjectResult, vor = null;
    VolleyObjectService volleyObjectService, vos;
    VolleyArrayService vas;
    VolleyArrayResult var = null;

    String lb, apc, lbp, apcp, idDest;
    ActionBar actionBar;

    private MobilAdapter mobilAdapter;
    private TujuanAdapter tujuanAdapter;
    String url = "http://m.rojo.id/api/dataMobil";
    Fungsi fungsi = new Fungsi();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobil);
        tglsewa = (EditText) findViewById(R.id.tglsewa);
        tujuan = (EditText) findViewById(R.id.tujuan);
        mobil = (EditText) findViewById(R.id.pilihmobil);
        idMobil = (EditText) findViewById(R.id.idMobil);
        idTuj = (EditText) findViewById(R.id.idTuj);
        namalengkap = (EditText) findViewById(R.id.namalengkap);
        notelp = (EditText) findViewById(R.id.nohp);
        notelp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (fungsi.validMobile(notelp.getText().toString()).equals("Invalid phone number!")) {
                    notelp.setError("No telpon tidak valid");
                }
            }
        });
        email = (EditText) findViewById(R.id.alamatemail);
        email.setText(fungsi.getEmail(ActivityMobil.this));
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (fungsi.validemail(email.getText().toString()).equals("Invalid email address!")) {
                    email.setError("Email tidak valid");
                }
            }
        });
        permintaan = (EditText) findViewById(R.id.permintaan);
        pesanmobil = (Button) findViewById(R.id.submiton);

        //======================================= DatePicker tgl berangkat =======================================
        final DatePickerDialog.OnDateSetListener datePickerBerangkat = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "dd/MMM/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
                tglsewa.setText(sdf.format(calendar.getTime()));
            }
        };

        tglsewa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(ActivityMobil.this, datePickerBerangkat,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        tujuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ActivityMobil.this, R.style.DialogTheme);
            }
        });

        mobil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.clear();
                AlertDialog.Builder builder = new AlertDialog.Builder(ActivityMobil.this, R.style.DialogTheme);
                LayoutInflater inflater = ActivityMobil.this.getLayoutInflater();
                View view = inflater.inflate(R.layout.list_mobil, null, false);
                cariMobil = (EditText) view.findViewById(R.id.cariMobil);
                listMobil = (ListView) view.findViewById(R.id.listMobil);
                progressBar = (ProgressBar) view.findViewById(R.id.proBar);
                builder.setView(view);

                volleyObjectResult = new VolleyObjectResult() {
                    @Override
                    public void resSuccess(String requestType, JSONObject response) {
                        progressBar.setVisibility(View.GONE);
                        try {
                            JSONArray jsonArray = response.getJSONArray("data");
                            for (int i=0; i<jsonArray.length(); i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                MobilDataSet lts = new MobilDataSet();
                                lts.setNama_mobil(jsonObject.getString("nama"));
                                lts.setHarga_mobil(jsonObject.getString("harga"));
                                lts.setImage_mobil(jsonObject.getString("image_mobil"));
                                lts.setTahun_mobil(jsonObject.getString("tahun"));
                                lts.setInfo_mobil(jsonObject.getString("keterangan"));
                                lts.setSupir_mobil(jsonObject.getString("supir"));
                                lts.setKursi_mobil(jsonObject.getString("seats"));
                                list.add(lts);
                            }
                            mobilAdapter = new MobilAdapter(ActivityMobil.this, list);
                            mobilAdapter.notifyDataSetChanged();
                            listMobil.setAdapter(mobilAdapter);
                            //========================= Action Listener EditText Cari Country =====================
                            cariMobil.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                }
                                @Override
                                public void onTextChanged(CharSequence s, int start, int before, int count) {

                                }
                                @Override
                                public void afterTextChanged(Editable s) {
                                    String text = cariMobil.getText().toString().toLowerCase(Locale.getDefault());
                                    mobilAdapter.filter(text);
                                }
                            });
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void resError(String requestType, VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Data tdk ditemukan!", Toast.LENGTH_LONG).show();
                    }
                };
                volleyObjectService = new VolleyObjectService(volleyObjectResult, ActivityMobil.this);
                volleyObjectService.getJsonObject("GETCALL", url);
                progressBar.setVisibility(View.VISIBLE);
                final AlertDialog alertDialog = builder.create();
                Window window = alertDialog.getWindow();
                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                alertDialog.show();
                listMobil.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        lb = list.get(position).getNama_mobil();
                        apc = list.get(position).getId_mobil();
                        mobil.setText(lb);
                        idMobil.setText(apc);
                        alertDialog.dismiss();
                    }
                });
            }
        });
        
        
        tujuan.setOnClickListener(new View.OnClickListener() {
            private String[] namatujaun={"Dalam Kota","Luar Kota"};
            private String[] idtujuan={"1","2"};

            @Override
            public void onClick(View v) {
                listp.clear();
                AlertDialog.Builder builder = new AlertDialog.Builder(ActivityMobil.this, R.style.DialogTheme);
                LayoutInflater inflater = ActivityMobil.this.getLayoutInflater();
                View view = inflater.inflate(R.layout.list_tujuan, null, false);
                listTujuan = (ListView) view.findViewById(R.id.listTujuan);
                builder.setView(view);
                //Toast.makeText(ActivityMobil.this, namapaket[0],Toast.LENGTH_LONG).show();
                for (int i=0; i<namatujaun.length; i++){
                    TujuanDataSet pds = new TujuanDataSet();
                    pds.setId_tujuan(idtujuan[i]);
                    //Toast.makeText(ActivityMobil.this, idpaket[i],Toast.LENGTH_LONG).show();
                    pds.setNama_tujuan(namatujaun[i]);
                    listp.add(pds);
                }

                tujuanAdapter = new TujuanAdapter(ActivityMobil.this, listp);
                tujuanAdapter.notifyDataSetChanged();
                listTujuan.setAdapter(tujuanAdapter);

                final AlertDialog alertDialog = builder.create();
                Window window = alertDialog.getWindow();
                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                alertDialog.show();
                listTujuan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        lbp = listp.get(position).getNama_tujuan();
                        apcp = listp.get(position).getId_tujuan();
                        //Toast.makeText(ActivityMobil.this, apcp,Toast.LENGTH_LONG).show();
                        tujuan.setText(lbp);
                        idTuj.setText(apcp);
                        idDest = apcp;
                        alertDialog.dismiss();
                    }
                });
            }
        });

        pesanmobil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mobil.getText().toString().equals("")) {
                    mobil.setError("Jenis mobil blm dipilih!");
                } else if (namalengkap.getText().toString().equals("")) {
                    namalengkap.setError("Harap masukkan nama lengkap!");
                } else if (notelp.getText().toString().equals("")) {
                    notelp.setError("No telepon harap diisi!");
                } else if (email.getText().toString().equals("")) {
                    email.setError("Email tdk boleh kosong!");
                } else if (tujuan.getText().toString().equals("")) {
                    tujuan.setError("Harap tentukan tujuan!");
                } else if (tglsewa.getText().toString().equals("")) {
                    tglsewa.setError("Harap pilih tanggal!");
                }

                //ListTourDataSet bds = new ListTourDataSet();
                String uid = fungsi.getDeviceUniqueID(ActivityMobil.this);
                String tuj = idTuj.getText().toString();
                String pilmobil = mobil.getText().toString();
                String tgl = tglsewa.getText().toString();
                String req = permintaan.getText().toString();
                String nama = namalengkap.getText().toString();
                String telp = notelp.getText().toString();
                String emailreq = email.getText().toString();


                HashMap<String, String> dt = new HashMap<String, String>();
                dt.put("uid", uid);
                dt.put("tujuan", tuj);
                dt.put("mobil", pilmobil);
                dt.put("tanggal", fungsi.ubahTgl(tgl));
                dt.put("no_hp", telp);
                dt.put("email", emailreq);
                dt.put("nama", nama);
                dt.put("special_request", req);

                JSONObject data = new JSONObject(dt);
                Intent intent = new Intent(ActivityMobil.this, ReturnMobil.class);
                intent.putExtra("json", data.toString());
                startActivity(intent);

            }
        });

    }

    public void onBackPressed(){
        finish();
        Intent intent = new Intent(ActivityMobil.this, MainActivity.class);
        startActivity(intent);
    }
}
