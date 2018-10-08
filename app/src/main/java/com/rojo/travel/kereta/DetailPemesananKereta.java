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
import android.util.Log;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by kangyasin on 10/11/2017.
 */

public class DetailPemesananKereta extends AppCompatActivity {

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
    String train_id, token, train_subclass, adult, child, infant, title_con, first_nm, last_nm, email,
            no_tlp, uid, departure, arrival, date_dep, date_arr;

    JSONObject data = new JSONObject();
    DetailPenumpangKeretaDataSet dpkds = new DetailPenumpangKeretaDataSet();
    DetailPenumpangChildDataSet dpcds = new DetailPenumpangChildDataSet();
    DetailPenumpangInfantDataSet dpids = new DetailPenumpangInfantDataSet();
    String[] titel, nama_dpn, no_hp, noKtp, tgl_lahir;
    String[] namaChild; String[] namaInfant;

    VolleyObjectService vos, vosCheckout;
    VolleyObjectResult vor, vorCheckout;
    String url = "http://m.rojo.id/Api/add_order_kereta";
    String urlCheckout = "http://m.rojo.id/Api/checkout_train";

    DatePickerDialog datePickerLahirDewasa;
    Calendar calendar;
    String kalender;
    String departure_date, return_date;
    String tok, dip, diu, latab, lempar;
    List<String> cekotList = new ArrayList<>();

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
        final EditText[] etNoHp = new EditText[jd];
        final EditText[] etIdCardAdult = new EditText[jd];
        final EditText[] tglLahirDewasa = new EditText[jd];
        titel = new String[jd]; nama_dpn = new String[jd]; noKtp = new String[jd];
        no_hp = new String[jd]; tgl_lahir = new String[jd];
        //Variabel utk menyimpan lemparan data penumpang child
        final EditText[] etNamaChild = new EditText[jd];
        namaChild = new String[jc];
        //Variabel utk menyimpan lemparan data penumpang infant
        final EditText[] etNamaInfant = new EditText[ji];
        namaInfant = new String[ji];

        pref = getApplicationContext().getSharedPreferences("dataTrain", 0);

        //================================= Detail kereta Berangkat/Atas =================================
        btnpayment = (Button) findViewById(R.id.submiton);
        uid = fn.getDeviceUniqueID(DetailPemesananKereta.this);
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
        Glide.with(DetailPemesananKereta.this).load(pref.getString("image", null)).asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_krt)
                .into(imgMaskapai);
        trainID.setText(pref.getString("train_id", null));
        jamBrgkt.setText(pref.getString("departure_time", null)+" - "+pref.getString("arrival_time", null));
        hargaBrgkt.setText(pref.getString("total_price", null));

        //================================= Detail kereta Pulang/Bawah =================================
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
        Glide.with(DetailPemesananKereta.this).load(bundle.getString("image")).asBitmap()
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
            //================================= Detail kereta Berangkat/Atas =================================
            Bundle extras = getIntent().getExtras();
            krtbrngkt.setText(extras.getString("departure_station", null));
            krttjn.setText(extras.getString("arrival_station", null));
            berangkat.setText(extras.getString("formatted_schedule_date", null));
            Glide.with(DetailPemesananKereta.this).load(extras.getString("image", null)).asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.mipmap.ic_launcher)
                    .into(imgMaskapai);
            trainID.setText(extras.getString("train_name", null));
            jamBrgkt.setText(extras.getString("departure_time", null)+" - "+extras.getString("arrival_time", null));
            hargaBrgkt.setText(extras.getString("total_price", null));
            rute.setText(extras.getString("departure_station", null)+" - "+extras.getString("arrival_station", null));
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
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(DetailPemesananKereta.this, android.R.layout.simple_spinner_dropdown_item, title);
        spinTitle.setAdapter(adapter);
        namaCon = (EditText) findViewById(R.id.nama);
        namaConLast = (EditText) findViewById(R.id.namaLast);
        emailCon = (EditText) findViewById(R.id.email);
        emailCon.setText(fn.getEmail(DetailPemesananKereta.this));
        nohpCon = (EditText) findViewById(R.id.nohp);
        detailPenumpang = (RelativeLayout) findViewById(R.id.detailPenumpang);
        detailPenumpang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(DetailPemesananKereta.this, R.style.DialogPenumpang);
                builder.setIcon(R.drawable.ic_person);
                builder.setTitle("Form Data Penumpang");
                ScrollView scrollView = new ScrollView(DetailPemesananKereta.this);
                scrollView.setFillViewport(true);
                scrollView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                LinearLayout mainLayout = new LinearLayout(DetailPemesananKereta.this);
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
                    layoutDewasa[i] = new LinearLayout(DetailPemesananKereta.this);
                    layoutDewasa[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    layoutDewasa[i].setOrientation(LinearLayout.VERTICAL);
                    layoutDewasa[i].setPadding(6, 6, 6, 6);
                    tvDewasa[i] = new TextView(DetailPemesananKereta.this);
                    tvDewasa[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    tvDewasa[i].setText("Dewasa "+(i+1));
                    tvDewasa[i].setTypeface(null, Typeface.BOLD);
                    layoutWrap[i] = new LinearLayout(DetailPemesananKereta.this);
                    layoutWrap[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    layoutWrap[i].setOrientation(LinearLayout.HORIZONTAL);
                    spin[i] = new Spinner(DetailPemesananKereta.this);
                    spin[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(DetailPemesananKereta.this, android.R.layout.simple_spinner_dropdown_item, title);
                    spin[i].setAdapter(arrayAdapter);
                    etNamaDewasa[i] = new EditText(DetailPemesananKereta.this);
                    etNamaDewasa[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    etNamaDewasa[i].setHintTextColor(getResources().getColor(R.color.graytext));
                    etNamaDewasa[i].setHint("Nama sesuai KTP");
                    etIdCardAdult[i] = new EditText(DetailPemesananKereta.this);
                    etIdCardAdult[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    etIdCardAdult[i].setHintTextColor(getResources().getColor(R.color.graytext));
                    etIdCardAdult[i].setHint("Nomor KTP");
                    etNoHp[i] = new EditText(DetailPemesananKereta.this);
                    etNoHp[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    etNoHp[i].setHintTextColor(getResources().getColor(R.color.graytext));
                    etNoHp[i].setHint("Phone");
                    tglLahirDewasa[i] = new EditText(DetailPemesananKereta.this);
                    tglLahirDewasa[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    tglLahirDewasa[i].setHintTextColor(getResources().getColor(R.color.graytext));
                    tglLahirDewasa[i].setHint("Tanggal lahir");
                    tglLahirDewasa[i].setFocusableInTouchMode(false);
                    tglLahirDewasa[i].setFocusable(false);
                    tglLahirDewasa[i].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final Calendar calendar = Calendar.getInstance();
                            DatePickerDialog dpDewasa = new DatePickerDialog(DetailPemesananKereta.this, new DatePickerDialog.OnDateSetListener() {
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
                    layoutDewasa[i].addView(etIdCardAdult[i]);
                    layoutDewasa[i].addView(etNoHp[i]);
                    layoutDewasa[i].addView(tglLahirDewasa[i]);
                    mainLayout.addView(layoutDewasa[i]);
                }
                //===================== Linear Layout Child ========================
                if (jc > 0) {
                    LinearLayout[] layoutChild = new LinearLayout[jc];
                    TextView[] tvChild = new TextView[jc];
                    for (int i=0; i<layoutChild.length; i++) {
                        layoutChild[i] = new LinearLayout(DetailPemesananKereta.this);
                        layoutChild[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        layoutChild[i].setOrientation(LinearLayout.VERTICAL);
                        layoutChild[i].setPadding(6, 6, 6, 6);
                        tvChild[i] = new TextView(DetailPemesananKereta.this);
                        tvChild[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        tvChild[i].setText("Child "+(i+1));
                        tvChild[i].setTypeface(null, Typeface.BOLD);
                        etNamaChild[i] = new EditText(DetailPemesananKereta.this);
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
                        layoutInfant[i] = new LinearLayout(DetailPemesananKereta.this);
                        layoutInfant[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        layoutInfant[i].setOrientation(LinearLayout.VERTICAL);
                        layoutInfant[i].setPadding(6, 6, 6, 6);
                        tvInfant[i] = new TextView(DetailPemesananKereta.this);
                        tvInfant[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        tvInfant[i].setText("Infant "+(i+1));
                        tvInfant[i].setTypeface(null, Typeface.BOLD);
                        etNamaInfant[i] = new EditText(DetailPemesananKereta.this);
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
                            noKtp[i] = etIdCardAdult[i].getText().toString();
                            no_hp[i] = etNoHp[i].getText().toString();
                            tgl_lahir[i] = tglLahirDewasa[i].getText().toString();
                            dpkds.setTitle_pass(titel);
                            dpkds.setNo_hp(no_hp);
                            dpkds.setIdCardAdult(noKtp);
                            dpkds.setFirst_nm(nama_dpn);
                            dpkds.setTgl_lahir(tgl_lahir);
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
                builder.setView(scrollView);
                final AlertDialog alertDialog = builder.create();
                Window window = alertDialog.getWindow();
                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                alertDialog.show();
            }
        });


        train_id = bundle.getString("train_id");
        token = bundle.getString("token");
        train_subclass = bundle.getString("train_subclass");
        departure = bundle.getString("departure_station");
        arrival = bundle.getString("arrival_station");
        date_dep = bundle.getString("date_dep");
        date_arr = bundle.getString("date_arr");
        btnpayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uid = fungsi.getDeviceUniqueID(DetailPemesananKereta.this);
                title_con = spinTitle.getSelectedItem().toString();
                first_nm = namaCon.getText().toString();
                last_nm = namaConLast.getText().toString();
                email = emailCon.getText().toString();
                no_tlp = nohpCon.getText().toString();

                adult = Integer.toString(preferences.getInt("jumDewasa", 0));
                child = Integer.toString(preferences.getInt("jumChild", 0));
                infant = Integer.toString(preferences.getInt("jumInfant", 0));
                try {
                    data.put("token",token);
                    data.put("train_id", train_id);
                    data.put("train_subclass", train_subclass);
                    data.put("adult", adult);
                    data.put("child", child);
                    data.put("departure", departure);
                    data.put("arrival", arrival);
                    data.put("date_dep", date_dep);
                    data.put("date_arr", date_arr);
                    data.put("uid", uid);;
                    data.put("conSalutation", title_con);
                    data.put("conFirstName", first_nm);
                    data.put("conLastName", last_nm);
                    data.put("conEmailAddress", email);
                    data.put("conPhone", no_tlp);
                    data.put("Content-Type", "application/x-www-form-urlencoded");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JSONObject object = new JSONObject();
                JSONObject objectChild = new JSONObject();
                JSONObject objectInfant = new JSONObject();
                String[] ttl, nmd, nohp, ktp, tgld, nmc, nmi;
                ttl = dpkds.getTitle_pass();
                nmd = dpkds.getFirst_nm();
                nohp = dpkds.getNo_hp();
                ktp = dpkds.getIdCardAdult();
                tgld = dpkds.getTgl_lahir();
                nmc = dpcds.getNamaChild();
                nmi = dpids.getNamaInfant();
                JSONArray dataArray = new JSONArray();
                JSONObject job = new JSONObject();
                //JSONArray dataArrayChild = new JSONArray();
                //JSONArray dataArrayInfant = new JSONArray();
                for (int i=0; i<jd;i++) {
                    try {
                        job.put("salutationAdult", ttl[i]);
                        job.put("nameAdult", nmd[i]);
                        job.put("IdCardAdult", ktp[i]);
                        job.put("noHpAdult", nohp[i]);
                        job.put("birthDateAdult", tgld[i]);
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
                    String tkn, pid, oid, btl;
                    @Override
                    public void resSuccess(String requestType, final JSONObject response) {
                        //Toast.makeText(getApplication(), response.toString(), Toast.LENGTH_LONG).show();
                        /*try {
                            JSONArray jsonArray = response.getJSONArray("dataOrder");
                            String tkn = null, pid, oid, btl;
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }*/
                        AlertDialog.Builder builder = new AlertDialog.Builder(DetailPemesananKereta.this);
                        builder.setTitle("Checkout Order");
                        builder.setMessage("Submit untuk pemesanan tiket anda");
                        //builder.setIcon(R.drawable.crown);
                        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Toast.makeText(DetailPemesananKereta.this, response.toString(), Toast.LENGTH_LONG).show();
                                tok = tokenTiket(response); tkn = tok;
                                dip = pemesanID(response); pid = dip;
                                diu = orderID(response); oid = diu;
                                JSONObject dataCheckout = new JSONObject();
                                try {
                                    dataCheckout.put("order_id", oid);
                                    dataCheckout.put("id_user", pid);
                                    dataCheckout.put("token", tkn);
                                    dataCheckout.put("Content-Type", "application/x-www-form-urlencoded");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                vorCheckout = new VolleyObjectResult() {
                                    String uri;
                                    @Override
                                    public void resSuccess(String requestType, JSONObject response) {
                                        //Toast.makeText(getApplication(), response.toString(), Toast.LENGTH_LONG).show();
                                        lempar = uriCheckout(response);
                                        uri = lempar;
                                        Intent intent = new Intent(DetailPemesananKereta.this, OpenBrowser.class);
                                        intent.putExtra("uri", uri);
                                        startActivity(intent);
                                    }
                                    @Override
                                    public void resError(String requestType, VolleyError error) {

                                    }
                                };
                                vosCheckout = new VolleyObjectService(vorCheckout, DetailPemesananKereta.this);
                                vosCheckout.postJsonObject("POSTCALL", urlCheckout, dataCheckout);
                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                latab = batal(response); btl = latab;
                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                    @Override
                    public void resError(String requestType, VolleyError error) {
                        Toast.makeText(getApplication(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                };
                vos = new VolleyObjectService(vor, DetailPemesananKereta.this);
                vos.postJsonObject("POSTCALL", url, data);
                //tkn = lempar;
                //Toast.makeText(getApplication(), lempar, Toast.LENGTH_LONG).show();
                //Intent intent = new Intent(DetailPemesananKereta.this, CheckOutKereta.class);
                //startActivity(intent);
            }
        });

    }

    private String tokenTiket(JSONObject response) {
        String result = null;
        try {
            result = response.getString("token");
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }
        return result;
    }

    private String pemesanID(JSONObject response) {
        String result = null;
        try {
            result = response.getString("pemesan_id");
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }
        return result;
    }

    private String orderID(JSONObject response) {
        String result = null;
        try {
            result = response.getString("order_id");
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }
        return result;
    }

    private String batal(JSONObject response) {
        String result = null;
        try {
            result = response.getString("delete_url");
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }
        return result;
    }

    private String uriCheckout(JSONObject response) {
        String result = null;
        try {
            result = response.getString("payment_link");
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }
        return result;
    }

}

