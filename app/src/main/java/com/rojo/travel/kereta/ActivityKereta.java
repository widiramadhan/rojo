package com.rojo.travel.kereta;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.rojo.travel.R;
import com.rojo.travel.functions.Fungsi;
import com.rojo.travel.functions.VolleyArrayResult;
import com.rojo.travel.functions.VolleyArrayService;
import com.rojo.travel.functions.VolleyObjectResult;
import com.rojo.travel.functions.VolleyObjectService;
import com.rojo.travel.pesawat.ActivityPesawat;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by kangyasin on 05/11/2017.
 */

public class ActivityKereta extends AppCompatActivity {

    private EditText tglBerangkat, tglPulang, stasiunBrgkt, stasiunPlg, cariStasiun, jumPenumpang;
    private Button btnToggle, carikereta;
    private Spinner jumlahPenumpang;
    private ListView listStasiun;
    private ProgressBar progressBar;
    private Switch aSwitch;
    private TextView tvRoundTrip;
    private NumberPicker npDewasa, npAnak, npBayi;
    private ImageButton btnUbah;
    int jumPenDewasa, jumPenAnak, jumPenBayi;
    Calendar calendar = Calendar.getInstance();
    VolleyObjectResult volleyObjectResult, vor = null;
    VolleyObjectService volleyObjectService, vos;
    VolleyArrayService vas;
    VolleyArrayResult var = null;
    String token, lb, apc;
    private List<StasiunDataSet> list = new ArrayList<StasiunDataSet>();
    private StasiunAdapter stasiunAdapter;
    private CoordinatorLayout coordinatorLayout;
    String url = "http://m.rojo.id/Api/getStation";
    Fungsi fungsi = new Fungsi();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.noActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kereta);
        stasiunBrgkt = (EditText) findViewById(R.id.berangat);
        stasiunPlg = (EditText) findViewById(R.id.pulang);
        stasiunPlg = (EditText) findViewById(R.id.pulang);
        tglBerangkat = (EditText) findViewById(R.id.tglberangkat);
        tglPulang = (EditText) findViewById(R.id.tglpulang);
        aSwitch = (Switch) findViewById(R.id.aSwitch);
        tvRoundTrip = (TextView) findViewById(R.id.tvRoundTrip);
        jumPenumpang = (EditText) findViewById(R.id.jumPenumpang);
        btnUbah = (ImageButton) findViewById(R.id.btnUbah);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator);
        carikereta = (Button) findViewById(R.id.submiton);
        final View.OnClickListener[] onclickListener = {null};

        btnUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnUbah.isSelected()) {
                    String text1 = stasiunBrgkt.getText().toString();
                    String text2 = stasiunPlg.getText().toString();
                    stasiunBrgkt.setText(text1); stasiunPlg.setText(text2);
                } else {
                    String text1 = stasiunBrgkt.getText().toString();
                    String text2 = stasiunPlg.getText().toString();
                    stasiunBrgkt.setText(text1); stasiunPlg.setText(text2);
                }
            }
        });


        //======================================= Pilih Bandara Berangkat =======================================
        stasiunBrgkt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ActivityKereta.this, R.style.DialogTheme);
                LayoutInflater inflater = ActivityKereta.this.getLayoutInflater();
                View view = inflater.inflate(R.layout.list_stasiun, null, false);
                cariStasiun = (EditText) view.findViewById(R.id.cariStasiun);
                listStasiun = (ListView) view.findViewById(R.id.listStasiun);
                progressBar = (ProgressBar) view.findViewById(R.id.proBar);
                builder.setView(view);
                volleyObjectResult = new VolleyObjectResult() {
                    @Override
                    public void resSuccess(String requestType, JSONObject response) {
                        progressBar.setVisibility(View.GONE);
                        try {
                            token = response.getString("token");
                            JSONArray jsonArray = response.getJSONArray("station");
                            for (int i=0; i<jsonArray.length(); i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                StasiunDataSet bds = new StasiunDataSet();
                                bds.setStation_name(jsonObject.getString("station_name"));
                                bds.setCity_name(jsonObject.getString("city_name"));
                                bds.setStation_code(jsonObject.getString("station_code"));
                                list.add(bds);
                            }
                            stasiunAdapter = new StasiunAdapter(ActivityKereta.this, list);
                            stasiunAdapter.notifyDataSetChanged();
                            listStasiun.setAdapter(stasiunAdapter);
                            //========================= Action Listener EditText Cari Stasiun =====================
                            cariStasiun.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                }
                                @Override
                                public void onTextChanged(CharSequence s, int start, int before, int count) {

                                }
                                @Override
                                public void afterTextChanged(Editable s) {
                                    String text = cariStasiun.getText().toString().toLowerCase(Locale.getDefault());
                                    stasiunAdapter.filter(text);
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
                volleyObjectService = new VolleyObjectService(volleyObjectResult, ActivityKereta.this);
                volleyObjectService.getJsonObject("GETCALL", url);
                //Toast.makeText(ActivityPesawat.this, token, Toast.LENGTH_LONG).show();
                final AlertDialog alertDialog = builder.create();
                Window window = alertDialog.getWindow();
                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                alertDialog.show();
                listStasiun.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        lb = list.get(position).getStation_name();
                        apc = list.get(position).getStation_code();
                        stasiunBrgkt.setText(lb+" "+"("+apc+")");
                        alertDialog.dismiss();
                    }
                });
            }
        });

        //======================================= Pilih Stasiun Pulang =======================================
        stasiunPlg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ActivityKereta.this, R.style.DialogTheme);
                LayoutInflater inflater = ActivityKereta.this.getLayoutInflater();
                View view = inflater.inflate(R.layout.list_stasiun, null, false);
                cariStasiun = (EditText) view.findViewById(R.id.cariStasiun);
                listStasiun = (ListView) view.findViewById(R.id.listStasiun);
                progressBar = (ProgressBar) view.findViewById(R.id.proBar);
                builder.setView(view);
                volleyObjectResult = new VolleyObjectResult() {
                    @Override
                    public void resSuccess(String requestType, JSONObject response) {
                        progressBar.setVisibility(View.GONE);
                        try {
                            token = response.getString("token");
                            JSONArray jsonArray = response.getJSONArray("station");
                            for (int i=0; i<jsonArray.length(); i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                StasiunDataSet bds = new StasiunDataSet();
                                bds.setStation_name(jsonObject.getString("station_name"));
                                bds.setCity_name(jsonObject.getString("city_name"));
                                bds.setStation_code(jsonObject.getString("station_code"));

                                list.add(bds);
                            }
                            stasiunAdapter = new StasiunAdapter(ActivityKereta.this, list);
                            stasiunAdapter.notifyDataSetChanged();
                            listStasiun.setAdapter(stasiunAdapter);
                            cariStasiun.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                }
                                @Override
                                public void onTextChanged(CharSequence s, int start, int before, int count) {

                                }
                                @Override
                                public void afterTextChanged(Editable s) {
                                    String text = cariStasiun.getText().toString().toLowerCase(Locale.getDefault());
                                    stasiunAdapter.filter(text);
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
                volleyObjectService = new VolleyObjectService(volleyObjectResult, ActivityKereta.this);
                volleyObjectService.getJsonObject("GETCALL", url);
                final AlertDialog alertDialog = builder.create();
                Window window = alertDialog.getWindow();
                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                alertDialog.show();
                listStasiun.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        lb = list.get(position).getStation_name();
                        apc = list.get(position).getStation_code();
                        stasiunPlg.setText(lb+" "+"("+apc+")");
                        alertDialog.dismiss();
                    }
                });
            }
        });

        //======================================= DatePicker tgl berangkat =======================================
        final DatePickerDialog.OnDateSetListener datePickerBerangkat = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "dd/MMM/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
                tglBerangkat.setText(sdf.format(calendar.getTime()));
            }
        };

        tglBerangkat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(ActivityKereta.this, datePickerBerangkat,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });

        aSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(aSwitch.isChecked()){
                    tglPulang.setVisibility(View.VISIBLE);
                    tvRoundTrip.setText("Round Trip?");
                }else {
                    tglPulang.setVisibility(View.GONE);
                    tvRoundTrip.setText("One Way?");
                }
            }
        });

        //======================================= DatePicker tgl pulang =======================================
        final DatePickerDialog.OnDateSetListener datePickerPulang = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "dd/MMM/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
                tglPulang.setText(sdf.format(calendar.getTime()));
            }
        };
        tglPulang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(ActivityKereta.this, datePickerBerangkat,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });

        //======================================= Spinner Jumlah Penumpang =======================================
        jumPenumpang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(ActivityKereta.this);
                LayoutInflater inflater = ActivityKereta.this.getLayoutInflater();
                final View view = inflater.inflate(R.layout.number_picker_passenger, null);
                builder.setView(view);

                npDewasa = (NumberPicker) view.findViewById(R.id.npDewasa);
                npAnak = (NumberPicker) view.findViewById(R.id.npAnak);
                npBayi = (NumberPicker) view.findViewById(R.id.npBayi);
                npDewasa.setMinValue(1); npDewasa.setMaxValue(7);
                npAnak.setMinValue(0); npBayi.setMinValue(0);
                npAnak.setMaxValue(6); npBayi.setMaxValue(1);
                npDewasa.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        if (npDewasa.getValue() == 1) {
                            npAnak.setMaxValue(6); npBayi.setMaxValue(npDewasa.getValue());
                        } else if (npDewasa.getValue() == 2) {
                            npAnak.setMaxValue(5); npBayi.setMaxValue(npDewasa.getValue());
                        } else if (npDewasa.getValue() == 3) {
                            npAnak.setMaxValue(4); npBayi.setMaxValue(npDewasa.getValue());
                        } else if (npDewasa.getValue() == 4) {
                            npAnak.setMaxValue(3); npBayi.setMaxValue(npDewasa.getValue());
                        } else if (npDewasa.getValue() == 5) {
                            npAnak.setMaxValue(2); npBayi.setMaxValue(4);
                        } else if (npDewasa.getValue() == 6) {
                            npAnak.setMaxValue(1); npBayi.setMaxValue(4);
                        } else if (npDewasa.getValue() == 7) {
                            npAnak.setMaxValue(0); npBayi.setMaxValue(4);
                        }
                    }
                });

                builder.setPositiveButton("Select", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        jumPenDewasa = npDewasa.getValue();
                        jumPenAnak = npAnak.getValue();
                        jumPenBayi = npBayi.getValue();
                        if (jumPenAnak == 0 && jumPenBayi == 0){
                            jumPenumpang.setText("Dewasa "+jumPenDewasa);
                        } else if (jumPenAnak != 0 && jumPenBayi == 0) {
                            jumPenumpang.setText("Dewasa "+jumPenDewasa+", Anak-Anak "+jumPenAnak);
                        } else if (jumPenAnak == 0 && jumPenBayi != 0) {
                            jumPenumpang.setText("Dewasa "+jumPenDewasa+", Bayi "+jumPenBayi);
                        } else {
                            jumPenumpang.setText("Dewasa " + jumPenDewasa + ", Anak-Anak " + jumPenAnak + ", Bayi " + jumPenBayi);
                        }
                        dialog.dismiss();
                    }
                });
                onclickListener[0] = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        jumPenumpang.setText("");
                    }
                };
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });

        carikereta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View view = findViewById(R.id.coordinator);

                if(stasiunBrgkt.getText().toString().length() == 0) {
                    String message = "Silahkan Pilih Stasiun Keberangkatan";
                    fungsi.showSnackbar(view, message);
                    //bandaraBrgkt.setError( "Silahkan Pilih Bandara Keberangkatan" );
                } else if(stasiunPlg.getText().toString().length() == 0) {
                    //bandaraPlg.setError( "Silahkan Pilih Bandara Tujuan" );
                    String message = "Silahkan Pilih Stasiun Tujuan";
                    fungsi.showSnackbar(view, message);
                } else if(stasiunBrgkt.getText().toString().equals(stasiunPlg.getText().toString())) {
                    //bandaraPlg.setError( "Kota keberangkatan dan kota tujuan harus berbeda" );
                    String message = "Stasiun keberangkatan dan Stasiun tujuan harus berbeda";
                    fungsi.showSnackbar(view, message);
                } else if(tglBerangkat.getText().toString().length() == 0) {
                    String message = "Silahkan Pilih Tanggal Berangkat";
                    fungsi.showSnackbar(view, message);
                    //tglBerangkat.setError( "Silahkan Pilih Tanggal Berangkat" );
                } else if(jumPenumpang.getText().toString().length() == 0) {
                    //jumPenumpang.setError( "Silahkan pilih penumpang" );
                    String message = "Silahkan pilih penumpang";
                    fungsi.showSnackbar(view, message);
                } else {
                    String jpds = String.valueOf(jumPenDewasa).toString();
                    String jpas = String.valueOf(jumPenAnak).toString();
                    String jpbs = String.valueOf(jumPenBayi).toString();
                    StasiunDataSet sds = new StasiunDataSet();
                    String dep = stasiunBrgkt.getText().toString();
                    int firstBracket = dep.indexOf('(');
                    String depContent = dep.substring(firstBracket + 1, dep.indexOf(')', firstBracket));
                    String arr = stasiunPlg.getText().toString();
                    int firstBracketArr = arr.indexOf('(');
                    String arrContent = arr.substring(firstBracketArr + 1, arr.indexOf(')', firstBracketArr));
                    HashMap<String, String> dt = new HashMap<String, String>();
                    dt.put("departure", depContent);
                    dt.put("arrival", arrContent);
                    dt.put("departureDate", fungsi.ubahTgl(tglBerangkat.getText().toString()));
                    if (tglPulang.getVisibility() == View.VISIBLE) {
                        dt.put("ReturnDate", fungsi.ubahTgl(tglPulang.getText().toString()));
                    } else {
                        dt.put("ReturnDate", "");
                    }
                    dt.put("adult", jpds);
                    dt.put("child", jpas);
                    dt.put("infant", jpbs);
                    dt.put("token", token);
                    JSONObject data = new JSONObject(dt);

                    //Toast.makeText(ActivityKereta.this, dt.toString(), Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(ActivityKereta.this, ListKereta.class);
                    startActivity(intent);

                    //=========================== Session data request volley ======================
                    SharedPreferences prefs = getApplicationContext().getSharedPreferences("jsons", 0);
                    SharedPreferences.Editor edit = prefs.edit();
                    edit.putString("datas", data.toString());
                    edit.commit();

                    //=========================== Session jumlah penumpang & return date ======================
                    SharedPreferences prefsprefserencess = getApplicationContext().getSharedPreferences("jumPenumpangs", 0);
                    SharedPreferences.Editor editor = prefsprefserencess.edit();
                    editor.putInt("jumDewasa", Integer.parseInt(jpds));
                    editor.putInt("jumChild", Integer.parseInt(jpas));
                    editor.putInt("jumInfant", Integer.parseInt(jpbs));
                    if (tglPulang.getVisibility() == View.VISIBLE) {
                        editor.putInt("returnDate", 1);
                    } else {
                        editor.putInt("returnDate", 0);
                    }
                    editor.putString("jsons", data.toString());
                    editor.commit();
                }
            }
        });

    }

}

