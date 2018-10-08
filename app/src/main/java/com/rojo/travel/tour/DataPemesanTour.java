package com.rojo.travel.tour;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.rojo.travel.OpenBrowser;
import com.rojo.travel.R;
import com.rojo.travel.functions.Fungsi;
import com.rojo.travel.functions.VolleyObjectResult;
import com.rojo.travel.functions.VolleyObjectService;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static java.lang.String.valueOf;

public class DataPemesanTour extends AppCompatActivity {

    EditText namadepan, namabelakang, email, phone, tgl, jumlahpar, alamat, jumlahdewasa, jumlahanak;
    Button buttonAdd, lemparData;
    LinearLayout container;
    ActionBar actionBar;
    Spinner spinTitle, spinType;
    String[] title = {"Mr","Mrs"};
    String[] type = {"Adult","Child"};
    Fungsi fungsi = new Fungsi();

    String tgltour, titlepic, typepic, namadepanpic, namabelakangpic, emailpic, uid, alamatpic, phonepic, idpaketpic, jenispaketpic, jumlahanakpic, jumlahdewasapic;
    JSONObject data = new JSONObject();
    JSONObject datas = new JSONObject();
    final Bundle bundle = new Bundle();

    VolleyObjectService vos;
    VolleyObjectResult vor;

    String lempar;
    ArrayList<String> lemper;

    String url = "http://m.rojo.id/Api/pesan_paket_wisata";
    Calendar calendar = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_pemesan_tour);

        final EditText idpaket = (EditText)findViewById(R.id.id_paket);
        final EditText jenispaket = (EditText)findViewById(R.id.jenis_paket);

        TextView namatour = (TextView)findViewById(R.id.namatour);
        TextView infopaket = (TextView)findViewById(R.id.infopaket);
        TextView hargapaket = (TextView)findViewById(R.id.hargapaket);

        Bundle extras = getIntent().getExtras();

        try {
            datas = new JSONObject(extras.getString("dataPesanan"));
            final String namaT = datas.getString("nama_tour");
            final String infoT = datas.getString("info_paket");
            final String hargaT = datas.getString("harga");
            final String idT = datas.getString("id_paket");
            final String jenisT = datas.getString("jenis_paket");

            namatour.setText(namaT);
            infopaket.setText(infoT);
            hargapaket.setText(hargaT);
            idpaket.setText(idT);
            jenispaket.setText(jenisT);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        setTitle("Detail Pemesan Tour");
        actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_USE_LOGO | ActionBar.DISPLAY_SHOW_TITLE);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#029a48")));

        namadepan = (EditText)findViewById(R.id.namadepan);
        namabelakang = (EditText)findViewById(R.id.namabelakang);

        jumlahanak = (EditText)findViewById(R.id.jumlahanak);
        jumlahdewasa = (EditText)findViewById(R.id.jumlahdewasa);

        phone = (EditText)findViewById(R.id.phone);
        phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (fungsi.validMobile(phone.getText().toString()).equals("Invalid phone number!")) {
                    phone.setError("No telpon tidak valid");
                }
            }
        });
        email = (EditText)findViewById(R.id.email);
        email.setText(fungsi.getEmail(DataPemesanTour.this));
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (fungsi.validemail(email.getText().toString()).equals("Invalid email address!")) {
                    email.setError("Email tidak valid");
                }
            }
        });
        tgl = (EditText)findViewById(R.id.tgltour);
        alamat = (EditText)findViewById(R.id.alamat);

        spinTitle = (Spinner) findViewById(R.id.spinTitle);
        spinType = (Spinner) findViewById(R.id.spinType);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(DataPemesanTour.this, android.R.layout.simple_spinner_dropdown_item, title);
        spinTitle.setAdapter(adapter);


        ArrayAdapter<String> adapters = new ArrayAdapter<String>(DataPemesanTour.this, android.R.layout.simple_spinner_dropdown_item, type);
        spinType.setAdapter(adapters);

        lemparData = (Button)findViewById(R.id.lempar);

        jumlahpar = (EditText)findViewById(R.id.jumlahpemesan);
        jumlahpar.setText("0");

        final PemesanDataSet sds = new PemesanDataSet();

        final DatePickerDialog.OnDateSetListener datePickerBerangkat = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "dd/MMM/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
                tgl.setText(sdf.format(calendar.getTime()));
            }
        };

        tgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(DataPemesanTour.this, datePickerBerangkat,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DAY_OF_YEAR, 14);
                datePickerDialog.getDatePicker().setMinDate(cal.getTimeInMillis());
                datePickerDialog.show();
            }
        });

        lemparData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (namadepan.getText().toString().length() == 0) {
                    namadepan.setError("Nama dpn tdk boleh kosong!");
                } else if (namabelakang.getText().toString().length() == 0) {
                    namabelakang.setError("Nama blkg tdk boleh kosong!");
                } else if (tgl.getText().toString().length() == 0) {
                    tgl.setError("Tanggal tdk boleh kosong!");
                } else if (phone.getText().toString().length() == 0) {
                    phone.setError("Nomor telpon wajib diisi!");
                } else if (email.getText().toString().length() == 0) {
                    email.setError("Email wajib diisi!");
                } else if (alamat.getText().toString().length() == 0) {
                    alamat.setError("Alamat wajib diisi!!");
                } else if (jumlahdewasa.getText().toString().length() == 0) {
                    jumlahdewasa.setError("Jumlah penumpang dewasa wajib diisi!");
                } else {
                        uid = fungsi.getDeviceUniqueID(DataPemesanTour.this);
                        titlepic = spinTitle.getSelectedItem().toString();
                        namadepanpic = namadepan.getText().toString();
                        namabelakangpic = namabelakang.getText().toString();
                        tgltour = fungsi.ubahTgl(tgl.getText().toString());
                        emailpic = email.getText().toString();
                        typepic = spinType.getSelectedItem().toString();
                        alamatpic = alamat.getText().toString();
                        phonepic = phone.getText().toString();
                        if (jumlahanak.getText().toString().equals("")) {
                            jumlahanakpic = "0";
                        }
                        jumlahdewasapic = jumlahdewasa.getText().toString();
                        idpaketpic = idpaket.getText().toString();
                        jenispaketpic = jenispaket.getText().toString();

                        try {
                            data.put("id_paket", idpaketpic);
                            data.put("jns_paket", jenispaketpic);
                            data.put("tanggal", tgltour);
                            data.put("bayar_dp", "0");
                            data.put("peserta", "1");
                            data.put("phone", phonepic);
                            data.put("uid", uid);
                            data.put("email", emailpic);
                            data.put("firstName", namadepanpic);
                            data.put("LastName", namabelakangpic);
                            data.put("title", titlepic);
                            data.put("alamat", alamatpic);
                            data.put("jml_dewasa", jumlahdewasapic);
                            data.put("jml_anak", jumlahanakpic);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        vor = new VolleyObjectResult() {
                            String uri;
                            @Override
                            public void resSuccess(String requestType, JSONObject response) {
                                lempar = getMessageFromServer(response);
                                uri = lempar;
                                //Toast.makeText(getApplication(), uri, Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(DataPemesanTour.this, OpenBrowser.class);
                                intent.putExtra("uri", uri);
                                startActivity(intent);
                            }
                            @Override
                            public void resError(String requestType, VolleyError error) {
                                Toast.makeText(getApplication(), error.toString(), Toast.LENGTH_LONG).show();
                            }
                        };
                        vos = new VolleyObjectService(vor, DataPemesanTour.this);
                        vos.postJsonObject("POSTCALL", url, data);
                        //Toast.makeText(DataPemesanTour.this, data.toString(),Toast.LENGTH_LONG).show();
                    }

                }
        });
    }

    private String getMessageFromServer(JSONObject response) {
        String result = null;
        try {
            result = response.getString("payment_link");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }
}
