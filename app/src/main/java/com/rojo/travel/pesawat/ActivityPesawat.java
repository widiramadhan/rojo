package com.rojo.travel.pesawat;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
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
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.rojo.travel.MyApplication;
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
 * Created by kangyasin on 30/10/2017.
 */

public class ActivityPesawat extends AppCompatActivity {

    private EditText tglBerangkat, tglPulang, bandaraBrgkt, bandaraPlg, cariBandara, jumPenumpang;
    private Button caripesawat;
    private ListView listBandara;
    Calendar calendar = Calendar.getInstance();
    VolleyObjectResult volleyObjectResult = null;
    VolleyObjectService volleyObjectService;
    VolleyArrayService vas;
    VolleyArrayResult var = null;
    String token, lb, apc, idDep, idArr;
    private List<BandaraDataSet> list = new ArrayList<BandaraDataSet>();
    private BandaraAdapter bandaraAdapter;
    private Switch aSwitch;
    private TextView tvRoundTrip;
    private NumberPicker npDewasa, npAnak, npBayi;
    private ImageButton btnUbah;
    int jumPenDewasa, jumPenAnak, jumPenBayi;
    private ProgressBar progressBar;
    private CoordinatorLayout coordinatorLayout;
    //String url = "http://m.rojo.id/Api/getBandara";
    String url="";
    String urlFunct="Api/getBandara";
    Fungsi fungsi = new Fungsi();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.noActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesawat);

        MyApplication application=(MyApplication)getApplication();
        String PathWS=application.sgPathSrv;
        url=PathWS+urlFunct;

        bandaraBrgkt = (EditText) findViewById(R.id.berangkat);
        bandaraPlg = (EditText) findViewById(R.id.pulang);
        tglBerangkat = (EditText) findViewById(R.id.tglberangkat);
        tglPulang = (EditText) findViewById(R.id.tglpulang);
        aSwitch = (Switch) findViewById(R.id.aSwitch);
        tvRoundTrip = (TextView) findViewById(R.id.tvRoundTrip);
        jumPenumpang = (EditText) findViewById(R.id.jumPenumpang);
        caripesawat = (Button) findViewById(R.id.submiton);
        btnUbah = (ImageButton) findViewById(R.id.btnUbah);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator);
        final View.OnClickListener[] onclickListener = {null};

        btnUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnUbah.isSelected()) {
                    String text1 = bandaraBrgkt.getText().toString();
                    String text2 = bandaraPlg.getText().toString();
                    bandaraPlg.setText(text1); bandaraBrgkt.setText(text2);
                } else {
                    String text1 = bandaraBrgkt.getText().toString();
                    String text2 = bandaraPlg.getText().toString();
                    bandaraPlg.setText(text1); bandaraBrgkt.setText(text2);
                }
            }
        });

        //======================================= Pilih Bandara Berangkat =======================================
        bandaraBrgkt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.clear();
                final AlertDialog.Builder builder = new AlertDialog.Builder(ActivityPesawat.this, R.style.DialogTheme);
                final LayoutInflater inflater = ActivityPesawat.this.getLayoutInflater();
                final View view = inflater.inflate(R.layout.list_bandara, null, false);
                cariBandara = (EditText) view.findViewById(R.id.cariBandara);
                listBandara = (ListView) view.findViewById(R.id.listBandara);
                progressBar = (ProgressBar) view.findViewById(R.id.proBar);
                builder.setView(view);
                volleyObjectResult = new VolleyObjectResult() {
                    @Override
                    public void resSuccess(String requestType, JSONObject response) {
                        progressBar.setVisibility(View.GONE);
                        try {
                            token = response.getString("token");
                            JSONArray array = response.getJSONArray("airport");
                            for (int i=0; i<array.length(); i++){
                                JSONObject jsonObject = array.getJSONObject(i);
                                JSONArray jsonArray = jsonObject.getJSONArray("bandara");
                                for (int j=0; j<jsonArray.length(); j++) {
                                    JSONObject object = jsonArray.getJSONObject(j);
                                    BandaraDataSet bds = new BandaraDataSet();
                                    idDep = object.getString("country_id");
                                    bds.setId(object.getString("id"));
                                    bds.setAirport_name(object.getString("airport_name"));
                                    bds.setAirport_code(object.getString("airport_code"));
                                    bds.setCountry_name(object.getString("country_name"));
                                    bds.setCountry_id(object.getString("country_id"));
                                    bds.setLocation_name(object.getString("location_name"));
                                    list.add(bds);
                                }
                            }
                            bandaraAdapter = new BandaraAdapter(ActivityPesawat.this, list);
                            bandaraAdapter.notifyDataSetChanged();
                            listBandara.setAdapter(bandaraAdapter);
                            //========================= Action Listener EditText Cari Bandara =====================
                            cariBandara.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                }
                                @Override
                                public void onTextChanged(CharSequence s, int start, int before, int count) {

                                }
                                @Override
                                public void afterTextChanged(Editable s) {
                                    String text = cariBandara.getText().toString().toLowerCase(Locale.getDefault());
                                    bandaraAdapter.filter(text);
                                }
                            });
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void resError(String requestType, VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Pencarian gagal", Toast.LENGTH_LONG).show();
                        finish();
                    }
                };
                volleyObjectService = new VolleyObjectService(volleyObjectResult, ActivityPesawat.this);
                volleyObjectService.getJsonObject("GETCALL", url);
                progressBar.setVisibility(View.VISIBLE);
                final AlertDialog alertDialog = builder.create();
                Window window = alertDialog.getWindow();
                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                alertDialog.show();
                listBandara.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        lb = list.get(position).getLocation_name();
                        apc = list.get(position).getAirport_code();
                        bandaraBrgkt.setText(lb+" "+"("+apc+")");
                        alertDialog.dismiss();
                    }
                });
            }
        });

        //======================================= Pilih Bandara Pulang =======================================
        bandaraPlg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.clear();
                AlertDialog.Builder builder = new AlertDialog.Builder(ActivityPesawat.this, R.style.DialogTheme);
                LayoutInflater inflater = ActivityPesawat.this.getLayoutInflater();
                View view = inflater.inflate(R.layout.list_bandara, null, false);
                cariBandara = (EditText) view.findViewById(R.id.cariBandara);
                listBandara = (ListView) view.findViewById(R.id.listBandara);
                progressBar = (ProgressBar) view.findViewById(R.id.proBar);
                builder.setView(view);
                volleyObjectResult = new VolleyObjectResult() {
                    @Override
                    public void resSuccess(String requestType, JSONObject response) {
                        progressBar.setVisibility(View.GONE);
                        try {
                            token = response.getString("token");
                            JSONArray array = response.getJSONArray("airport");
                            for (int i=0; i<array.length(); i++){
                                JSONObject jsonObject = array.getJSONObject(i);
                                JSONArray jsonArray = jsonObject.getJSONArray("bandara");
                                for (int j=0; j<jsonArray.length(); j++) {
                                    JSONObject object = jsonArray.getJSONObject(j);
                                    BandaraDataSet bds = new BandaraDataSet();
                                    idArr = object.getString("country_id");
                                    bds.setId(object.getString("id"));
                                    bds.setAirport_name(object.getString("airport_name"));
                                    bds.setAirport_code(object.getString("airport_code"));
                                    bds.setCountry_name(object.getString("country_name"));
                                    bds.setCountry_id(object.getString("country_id"));
                                    bds.setLocation_name(object.getString("location_name"));
                                    list.add(bds);
                                }
                            }
                            bandaraAdapter = new BandaraAdapter(ActivityPesawat.this, list);
                            bandaraAdapter.notifyDataSetChanged();
                            listBandara.setAdapter(bandaraAdapter);
                            cariBandara.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                }
                                @Override
                                public void onTextChanged(CharSequence s, int start, int before, int count) {

                                }
                                @Override
                                public void afterTextChanged(Editable s) {
                                    String text = cariBandara.getText().toString().toLowerCase(Locale.getDefault());
                                    bandaraAdapter.filter(text);
                                }
                            });
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void resError(String requestType, VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Pencarian gagal", Toast.LENGTH_LONG).show();
                        finish();
                    }
                };
                volleyObjectService = new VolleyObjectService(volleyObjectResult, ActivityPesawat.this);
                volleyObjectService.getJsonObject("GETCALL", url);
                progressBar.setVisibility(View.VISIBLE);
                final AlertDialog alertDialog = builder.create();
                Window window = alertDialog.getWindow();
                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                alertDialog.show();
                listBandara.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        lb = list.get(position).getLocation_name();
                        apc = list.get(position).getAirport_code();
                        bandaraPlg.setText(lb+" "+"("+apc+")");
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(ActivityPesawat.this, datePickerBerangkat,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.MONTH, 18);
                datePickerDialog.getDatePicker().setMaxDate(cal.getTimeInMillis());
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(ActivityPesawat.this, datePickerPulang,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.MONTH, 18);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 15000);
                datePickerDialog.getDatePicker().setMaxDate(cal.getTimeInMillis());
                datePickerDialog.show();
            }
        });

        //======================================= Spinner Jumlah Penumpang =======================================
        jumPenumpang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(ActivityPesawat.this);
                LayoutInflater inflater = ActivityPesawat.this.getLayoutInflater();
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

        caripesawat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View view = findViewById(R.id.coordinator);
                String tglB = tglBerangkat.getText().toString();
                String tglP = tglPulang.getText().toString();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy");
                Date brgkt = null;
                Date plg = null;
                try {
                    brgkt = sdf.parse(tglB);
                    plg = sdf.parse(tglP);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (bandaraBrgkt.getText().toString().length() == 0) {
                    String message = "Silahkan Pilih Bandara Keberangkatan";
                    fungsi.showSnackbar(view, message);
                    //bandaraBrgkt.setError( "Silahkan Pilih Bandara Keberangkatan" );
                } else if (bandaraPlg.getText().toString().length() == 0) {
                    //bandaraPlg.setError( "Silahkan Pilih Bandara Tujuan" );
                    String message = "Silahkan Pilih Bandara Tujuan";
                    fungsi.showSnackbar(view, message);
                } else if (bandaraBrgkt.getText().toString().equals(bandaraPlg.getText().toString())) {
                    //bandaraPlg.setError( "Kota keberangkatan dan kota tujuan harus berbeda" );
                    String message = "Bandara keberangkatan dan bandara tujuan harus berbeda";
                    fungsi.showSnackbar(view, message);
                } else if (tglBerangkat.getText().toString().length() == 0) {
                    String message = "Silahkan Pilih Tanggal Berangkat";
                    fungsi.showSnackbar(view, message);
                    //tglBerangkat.setError( "Silahkan Pilih Tanggal Berangkat" );
                } else if (tglPulang.getVisibility() == View.VISIBLE && tglPulang.getText().toString().length() == 0) {
                    String message = "Silahkan Pilih Tanggal Pulang";
                    fungsi.showSnackbar(view, message);
                } else if (tglPulang.getVisibility() == View.VISIBLE && tglPulang.getText().toString().length() != 0 && plg.compareTo(brgkt) < 0) {
                    String message = "Tanggal Pulang Lebih Kecil Dari Tanggal Berangkat";
                    fungsi.showSnackbar(view, message);
                    tglPulang.setText("");
                } else if(jumPenumpang.getText().toString().length() == 0) {
                    //jumPenumpang.setError( "Silahkan pilih penumpang" );
                    String message = "Silahkan pilih penumpang";
                    fungsi.showSnackbar(view, message);
                } else {
                    String jpd = String.valueOf(jumPenDewasa).toString();
                    String jpa = String.valueOf(jumPenAnak).toString();
                    String jpb = String.valueOf(jumPenBayi).toString();
                    String dep = bandaraBrgkt.getText().toString();
                    int firstBracket = dep.indexOf('(');
                    String depContent = dep.substring(firstBracket + 1, dep.indexOf(')', firstBracket));
                    String arr = bandaraPlg.getText().toString();
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
                    dt.put("adult", jpd);
                    dt.put("child", jpa);
                    dt.put("infant", jpb);
                    dt.put("token", token);
                    final JSONObject data = new JSONObject(dt);

                    //=========================== Session data request volley ======================
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("json", 0);
                    SharedPreferences.Editor edit = pref.edit();
                    edit.putString("data", data.toString());
                    edit.commit();

                    //=========================== Session jumlah penumpang & return date ======================
                    SharedPreferences preferences = getApplicationContext().getSharedPreferences("jumPenumpang", 0);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putInt("jumDewasa", Integer.parseInt(jpd));
                    editor.putInt("jumChild", Integer.parseInt(jpa));
                    editor.putInt("jumInfant", Integer.parseInt(jpb));
                    if (tglPulang.getVisibility() == View.VISIBLE) {
                        editor.putInt("returnDate", 1);
                    } else {
                        editor.putInt("returnDate", 0);
                    }
                    editor.putString("idDep", idDep);
                    editor.putString("idArr", idArr);
                    editor.putString("json", data.toString());
                    editor.commit();

                    //Toast.makeText(ActivityPesawat.this, data.toString(), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(ActivityPesawat.this, ListPenerbangan.class);
                    startActivity(intent);
                }
            }
        });
    }


}
