package com.rojo.travel.kereta;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.rojo.travel.OpenBrowser;
import com.rojo.travel.R;
import com.rojo.travel.functions.Fungsi;
import com.rojo.travel.functions.VolleyObjectResult;
import com.rojo.travel.functions.VolleyObjectService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

/**
 * Created by kangyasin on 10/11/2017.
 */

public class DetailPemesanan extends AppCompatActivity {

    Button btnpayment;
    EditText namaCon, namaConLast, emailCon, nohpCon;
    Spinner spinTitle;
    Fungsi fn = new Fungsi();
    TextView krtbrngkt, krttjn, berangkat, trainID, jamBrgkt,
            hargaBrgkt, rute, pulang, trainIDPulang, jamPlg, hargaPlg, rutePulang,
            keretaberangkat, keretatujuan, hargakeretaberangkat, hargakeretatujuan, total, hargatotal;
    ImageView imgMaskapai, imgMaskapaiPulang;
    SharedPreferences preferences, pref;
    RelativeLayout detailPulang, detailPenumpang;
    ActionBar actionBar;
    Fungsi fungsi = new Fungsi();
    String[] title = {"Mr","Mrs"};
    String train_id, token, adult, child, infant, title_con, first_nm, last_nm, email,
            no_tlp, uid;

    JSONObject data = new JSONObject();
    DetailPenumpangDataSet dpds = new DetailPenumpangDataSet();
    DetailPenumpangChildDataSet dpcds = new DetailPenumpangChildDataSet();
    DetailPenumpangInfantDataSet dpids = new DetailPenumpangInfantDataSet();
    String[] titel, nama_dpn, nama_blkg, tgl_lahir;
    String[] namaChild; String[] namaInfant;

    VolleyObjectService vos;
    VolleyObjectResult vor;
    String url = "http://m.rojo.id/Api/submit_order";
    //String url = "http://aslisetup.com/rojo/Api/submit_order";

    DatePickerDialog datePickerLahirDewasa;
    Calendar calendar;
    String kalender;
    String lempar, departure_date, return_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_pemesanan_kereta);
        setTitle("Detail Pemesanan Tiket");
        actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_USE_LOGO | ActionBar.DISPLAY_SHOW_TITLE);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#029a48")));

        preferences = getApplicationContext().getSharedPreferences("jumPenumpang", 0);
        int totPenumpang = preferences.getInt("jumDewasa", 0)+preferences.getInt("jumChild", 0)+preferences.getInt("jumInfant", 0);
        final int jd = preferences.getInt("jumDewasa", 0);
        final int jc = preferences.getInt("jumChild", 0);
        final int ji = preferences.getInt("jumInfant", 0);
        //Variabel utk menyimpan lemparan data penumpang dewasa
        final Spinner[] spin = new Spinner[jd];
        final EditText[] etNamaDewasa = new EditText[jd];
        final EditText[] etLastName = new EditText[jd];
        final EditText[] tglLahirDewasa = new EditText[jd];
        titel = new String[jd]; nama_dpn = new String[jd]; nama_blkg = new String[jd]; tgl_lahir = new String[jd];
        //Variabel utk menyimpan lemparan data penumpang child
        final EditText[] etNamaChild = new EditText[jd];
        namaChild = new String[jc];
        //Variabel utk menyimpan lemparan data penumpang infant
        final EditText[] etNamaInfant = new EditText[ji];
        namaInfant = new String[ji];

        pref = getApplicationContext().getSharedPreferences("dataFlight", 0);

        //================================= Detail Flight Berangkat/Atas =================================
        btnpayment = (Button) findViewById(R.id.submiton);
        uid = fn.getDeviceUniqueID(DetailPemesanan.this);
        krtbrngkt = (TextView) findViewById(R.id.krtbrngkt);
        krttjn = (TextView) findViewById(R.id.krttjn);
        berangkat = (TextView) findViewById(R.id.berangkat);
        imgMaskapai = (ImageView) findViewById(R.id.imgberangkat);
        trainID = (TextView) findViewById(R.id.kodeberangkat);
        jamBrgkt = (TextView) findViewById(R.id.jamberangkat);
        hargaBrgkt = (TextView) findViewById(R.id.hargabrgkt);
        rute = (TextView) findViewById(R.id.ruteberangkat);

        krtbrngkt.setText(pref.getString("departure_station", null));
        krttjn.setText(pref.getString("arrival_station", null));
        berangkat.setText(pref.getString("formatted_schedule_date", null));
        Glide.with(DetailPemesanan.this).load(pref.getString("image", null)).asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.mipmap.ic_launcher)
                .into(imgMaskapai);
        trainID.setText(pref.getString("train_id", null));
        jamBrgkt.setText(pref.getString("departure_time", null)+" - "+pref.getString("arrival_time", null));
        hargaBrgkt.setText(pref.getString("total_price", null));

        //================================= Detail Flight Pulang/Bawah =================================
        detailPulang = (RelativeLayout) findViewById(R.id.detailpulang);
        pulang = (TextView) findViewById(R.id.pulang);
        imgMaskapaiPulang = (ImageView) findViewById(R.id.imgpulang);
        trainIDPulang = (TextView) findViewById(R.id.kodepulang);
        jamPlg = (TextView) findViewById(R.id.jampulang);
        hargaPlg = (TextView) findViewById(R.id.hargapulang);
        rutePulang = (TextView) findViewById(R.id.rutepulang);

        Bundle bundle = new Bundle();
        bundle = getIntent().getExtras();
        pulang.setText(bundle.getString("departure_flight_date_str"));
        Glide.with(DetailPemesanan.this).load(bundle.getString("image")).asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.mipmap.ic_launcher)
                .into(imgMaskapaiPulang);
        trainIDPulang.setText(bundle.getString("train_id"));
        jamPlg.setText(bundle.getString("departure_time")+" - "+bundle.getString("arrival_time"));
        hargaPlg.setText(bundle.getString("total_price"));


        //============================= Layout Detail Pulang ===========================
        keretaberangkat = (TextView) findViewById(R.id.keretaberangkat);
        hargakeretaberangkat = (TextView) findViewById(R.id.hargakeretaberangkat);
        keretatujuan = (TextView) findViewById(R.id.keretatujuan);
        hargakeretatujuan = (TextView) findViewById(R.id.hargakeretatujuan);
        total = (TextView) findViewById(R.id.total);
        hargatotal = (TextView) findViewById(R.id.hargatotal);

        if (preferences.getInt("returnDate", 0) == 0) {
            //================================= Detail Flight Berangkat/Atas =================================
            Bundle extras = getIntent().getExtras();
            krtbrngkt.setText(extras.getString("departure_station", null));
            krttjn.setText(extras.getString("arrival_station", null));
            berangkat.setText(extras.getString("departure_flight_date_str", null));
            Glide.with(DetailPemesanan.this).load(extras.getString("image", null)).asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.mipmap.ic_launcher)
                    .into(imgMaskapai);
            trainID.setText(extras.getString("train_id", null));
            jamBrgkt.setText(extras.getString("formatted_schedule_date", null)+" - "+pref.getString("formatted_schedule_date", null));
            hargaBrgkt.setText(extras.getString("price_value", null));
            rute.setText(extras.getString("full_via", null));
            detailPulang.setVisibility(View.GONE);
            keretatujuan.setVisibility(View.GONE);
            hargakeretatujuan.setVisibility(View.GONE);
            keretaberangkat.setText(extras.getString("train_name", null)+" ("+totPenumpang+" tiket)");
            hargakeretaberangkat.setText(extras.getString("total_price", null));
            hargatotal.setText(extras.getString("total_price", null));
            departure_date = extras.getString("departureDate", null);
            return_date = extras.getString("ReturnDate", null);
        } else if (preferences.getInt("returnDate", 0) == 1) {
            detailPulang.setVisibility(View.VISIBLE);
            keretatujuan.setVisibility(View.VISIBLE);
            hargakeretatujuan.setVisibility(View.VISIBLE);
            keretaberangkat.setText(pref.getString("train_name", null)+" ("+totPenumpang+" tiket)");
            hargakeretaberangkat.setText(pref.getString("price_value", null));
            keretatujuan.setText(pref.getString("train_name", null)+" ("+totPenumpang+" tiket)");
            hargakeretatujuan.setText(pref.getString("price_value", null));
            float tot, brgktTot, plgTot;
            brgktTot = Float.parseFloat(pref.getString("total_price", null));
            plgTot = Float.parseFloat(bundle.getString("total_price"));
            tot = brgktTot + plgTot;
            hargatotal.setText(String.valueOf(tot));
            departure_date = pref.getString("ReturnDate", null);
            return_date = pref.getString("ReturnDate", null);
        }

        //============================= Layout Detail Penumpang ===========================
        spinTitle = (Spinner) findViewById(R.id.spinTitle);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(DetailPemesanan.this, android.R.layout.simple_spinner_dropdown_item, title);
        spinTitle.setAdapter(adapter);
        namaCon = (EditText) findViewById(R.id.nama);
        namaConLast = (EditText) findViewById(R.id.namaLast);
        emailCon = (EditText) findViewById(R.id.email);
        emailCon.setText(fn.getEmail(DetailPemesanan.this));
        nohpCon = (EditText) findViewById(R.id.nohp);
        detailPenumpang = (RelativeLayout) findViewById(R.id.detailPenumpang);
        detailPenumpang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(DetailPemesanan.this, R.style.DialogPenumpang);
                builder.setIcon(R.drawable.ic_person);
                builder.setTitle("Form Data Penumpang");
                ScrollView scrollView = new ScrollView(DetailPemesanan.this);
                scrollView.setFillViewport(true);
                scrollView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                LinearLayout mainLayout = new LinearLayout(DetailPemesanan.this);
                mainLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                mainLayout.setOrientation(LinearLayout.VERTICAL);
                mainLayout.setPadding(6, 6, 6, 6);
                //===================== Linear Layout Dewasa ========================
                LinearLayout[] layoutDewasa = new LinearLayout[jd];
                TextView[] tvDewasa = new TextView[jd];
                LinearLayout[]  layoutWrap = new LinearLayout[jd];
                final int k = 0;
                for (int i=0; i<layoutDewasa.length; i++) {
                    final int x = i;
                    layoutDewasa[i] = new LinearLayout(DetailPemesanan.this);
                    layoutDewasa[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    layoutDewasa[i].setOrientation(LinearLayout.VERTICAL);
                    layoutDewasa[i].setPadding(6, 6, 6, 6);
                    tvDewasa[i] = new TextView(DetailPemesanan.this);
                    tvDewasa[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    tvDewasa[i].setText("Dewasa "+(i+1));
                    tvDewasa[i].setTypeface(null, Typeface.BOLD);
                    layoutWrap[i] = new LinearLayout(DetailPemesanan.this);
                    layoutWrap[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    layoutWrap[i].setOrientation(LinearLayout.HORIZONTAL);
                    spin[i] = new Spinner(DetailPemesanan.this);
                    spin[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(DetailPemesanan.this, android.R.layout.simple_spinner_dropdown_item, title);
                    spin[i].setAdapter(arrayAdapter);
                    etNamaDewasa[i] = new EditText(DetailPemesanan.this);
                    etNamaDewasa[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    etNamaDewasa[i].setHintTextColor(getResources().getColor(R.color.blacktext));
                    etNamaDewasa[i].setHint("Nama depan sesuai KTP");
                    etLastName[i] = new EditText(DetailPemesanan.this);
                    etLastName[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    etLastName[i].setHintTextColor(getResources().getColor(R.color.blacktext));
                    etLastName[i].setHint("Nama belakang sesuai KTP");
                    tglLahirDewasa[i] = new EditText(DetailPemesanan.this);
                    tglLahirDewasa[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    tglLahirDewasa[i].setHintTextColor(getResources().getColor(R.color.blacktext));
                    tglLahirDewasa[i].setHint("Tanggal lahir");
                    tglLahirDewasa[i].setFocusableInTouchMode(false);
                    tglLahirDewasa[i].setFocusable(false);
                    tglLahirDewasa[i].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final Calendar calendar = Calendar.getInstance();
                            DatePickerDialog dpDewasa = new DatePickerDialog(DetailPemesanan.this, new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                    String date, mth;
                                    if (month < 10) mth = "0"+(month+1);
                                    else mth = String.valueOf(month+1);
                                    if (dayOfMonth < 10) date = "0"+dayOfMonth;
                                    else date = String.valueOf(dayOfMonth);
                                    kalender = String.valueOf(year)+"-"+mth+"-"+date;
                                    tglLahirDewasa[x].setText(kalender);
                                }
                            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
                            dpDewasa.show();
                        }
                    });
                    layoutDewasa[i].addView(tvDewasa[i]);
                    layoutWrap[i].addView(spin[i]); layoutWrap[i].addView(etNamaDewasa[i]);
                    layoutDewasa[i].addView(layoutWrap[i]);
                    layoutDewasa[i].addView(etLastName[i]);
                    layoutDewasa[i].addView(tglLahirDewasa[i]);
                    mainLayout.addView(layoutDewasa[i]);
                }
                //===================== Linear Layout Child ========================
                if (jc > 0) {
                    LinearLayout[] layoutChild = new LinearLayout[jc];
                    TextView[] tvChild = new TextView[jc];
                    for (int i=0; i<layoutChild.length; i++) {
                        layoutChild[i] = new LinearLayout(DetailPemesanan.this);
                        layoutChild[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        layoutChild[i].setOrientation(LinearLayout.VERTICAL);
                        layoutChild[i].setPadding(6, 6, 6, 6);
                        tvChild[i] = new TextView(DetailPemesanan.this);
                        tvChild[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        tvChild[i].setText("Child "+(i+1));
                        tvChild[i].setTypeface(null, Typeface.BOLD);
                        etNamaChild[i] = new EditText(DetailPemesanan.this);
                        etNamaChild[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        etNamaChild[i].setHintTextColor(getResources().getColor(R.color.graytext));
                        etNamaChild[i].setHint(Html.fromHtml("<small><small><small>"+"Nama lengkap"+"</small></small></small>"));
                        layoutChild[i].addView(tvChild[i]);
                        layoutChild[i].addView(etNamaChild[i]);
                        mainLayout.addView(layoutChild[i]);
                    }
                }
                //===================== Linear Layout Infant ========================
                if (ji > 0) {
                    LinearLayout[] layoutInfant = new LinearLayout[ji];
                    TextView[] tvInfant = new TextView[ji];
                    for (int i=0; i<layoutInfant.length; i++) {
                        layoutInfant[i] = new LinearLayout(DetailPemesanan.this);
                        layoutInfant[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        layoutInfant[i].setOrientation(LinearLayout.VERTICAL);
                        layoutInfant[i].setPadding(6, 6, 6, 6);
                        tvInfant[i] = new TextView(DetailPemesanan.this);
                        tvInfant[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        tvInfant[i].setText("Infant "+(i+1));
                        tvInfant[i].setTypeface(null, Typeface.BOLD);
                        etNamaInfant[i] = new EditText(DetailPemesanan.this);
                        etNamaInfant[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        etNamaInfant[i].setHintTextColor(getResources().getColor(R.color.graytext));
                        etNamaInfant[i].setHint(Html.fromHtml("<small><small><small>"+"Nama lengkap"+"</small></small></small>"));
                        layoutInfant[i].addView(tvInfant[i]);
                        layoutInfant[i].addView(etNamaInfant[i]);
                        mainLayout.addView(layoutInfant[i]);
                    }
                }
                //===================== Pemanggilan komponen kedalam parent ======================
                scrollView.addView(mainLayout);
                builder.setView(scrollView);
                builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (int i=0; i<etNamaDewasa.length; i++) {
                            titel[i] = spin[i].getSelectedItem().toString();
                            nama_dpn[i] = etNamaDewasa[i].getText().toString();
                            nama_blkg[i] = etLastName[i].getText().toString();
                            tgl_lahir[i] = tglLahirDewasa[i].getText().toString();
                            dpds.setTitle_pass(titel);
                            dpds.setFirst_nm(nama_dpn);
                            dpds.setLast_nm(nama_blkg);
                            dpds.setTgl_lahir(tgl_lahir);
                        }
                        if (jc > 0) {
                            for (int i=0; i<etNamaChild.length; i++) {
                                namaChild[i] = etNamaChild[i].getText().toString();
                                dpcds.setNamaChild(namaChild);
                            }
                        }
                        if (ji > 0) {
                            for (int i=0; i<etNamaInfant.length; i++) {
                                namaInfant[i] = etNamaInfant[i].getText().toString();
                                dpids.setNamaInfant(namaInfant);
                            }
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                final AlertDialog alertDialog = builder.create();
                Window window = alertDialog.getWindow();
                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                alertDialog.show();
            }
        });


        train_id = bundle.getString("train_id");
        token = bundle.getString("token");
        btnpayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uid = fungsi.getDeviceUniqueID(DetailPemesanan.this);
                title_con = spinTitle.getSelectedItem().toString();
                first_nm = namaCon.getText().toString();
                last_nm = namaConLast.getText().toString();
                email = emailCon.getText().toString();
                no_tlp = nohpCon.getText().toString();

                adult = Integer.toString(preferences.getInt("jumDewasa", 0));
                child = Integer.toString(preferences.getInt("jumChild", 0));
                infant = Integer.toString(preferences.getInt("jumInfant", 0));
                try {
                    data.put("token",token); data.put("adult", adult); data.put("child", child);
                    data.put("uid", uid); data.put("title_con", title_con);
                    data.put("first_nm", first_nm); data.put("last_nm", last_nm);
                    data.put("email", email); data.put("phone", no_tlp); data.put("infant", infant);
                    data.put("departure_date", departure_date); data.put("return_date", return_date);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JSONObject object = new JSONObject();
                JSONObject objectChild = new JSONObject();
                JSONObject objectInfant = new JSONObject();
                String[] ttl, nmd, nmb, tgld, nmc, nmi;
                ttl = dpds.getTitle_pass();
                nmd = dpds.getFirst_nm();
                nmb = dpds.getLast_nm();
                tgld = dpds.getTgl_lahir();
                nmc = dpcds.getNamaChild();
                nmi = dpids.getNamaInfant();
                JSONArray dataArray = new JSONArray();
                JSONObject job = new JSONObject();
                //JSONArray dataArrayChild = new JSONArray();
                //JSONArray dataArrayInfant = new JSONArray();
                for (int i=0; i<jd;i++) {
                    try {
                        job.put("title_pass", ttl[i]);
                        job.put("first_nm", nmd[i]);
                        job.put("last_nm", nmb[i]);
                        job.put("tgl_lahir", tgld[i]);
                        job.put("ida"+(i+1), "666");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                dataArray.put(job);
                try {
                    data.put("PenumpangDewasa", dataArray);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                /*if (jc > 0) {
                    for (int i=0; i<nmc.length; i++) {
                        try {
                            objectChild.put("nama_child"+(i+1), nmc[i]);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                if (ji > 0) {
                    for (int i=0; i<nmi.length; i++) {
                        try {
                            objectInfant.put("nama_infant"+(i+1), nmi[i]);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                //dataArray.put(object);
                try {
                    data.put("dewasa", dataArray);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (jc > 0) {
                    dataArrayChild.put(objectChild);
                    try {
                        data.put("child", dataArrayChild);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (ji > 0) {
                    dataArrayInfant.put(objectInfant);
                    try {
                        data.put("infant", dataArrayInfant);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }*/
                //Toast.makeText(getApplication(), data.toString(), Toast.LENGTH_LONG).show();
                vor = new VolleyObjectResult() {
                    String uri;
                    @Override
                    public void resSuccess(String requestType, JSONObject response) {
                        //Toast.makeText(getApplication(), uri, Toast.LENGTH_LONG).show();
                        if (response != null) {
                            lempar = getMessageFromServer(response);
                            uri = lempar;
                            Intent intent = new Intent(DetailPemesanan.this, OpenBrowser.class);
                            intent.putExtra("uri", uri);
                            startActivity(intent);
                            startActivity(intent);
                        }
                    }
                    @Override
                    public void resError(String requestType, VolleyError error) {
                        Toast.makeText(getApplication(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                };
                vos = new VolleyObjectService(vor, DetailPemesanan.this);
                vos.postJsonObject("POSTCALL", url, data);
                //Toast.makeText(getApplication(), data.toString(), Toast.LENGTH_LONG).show();
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

