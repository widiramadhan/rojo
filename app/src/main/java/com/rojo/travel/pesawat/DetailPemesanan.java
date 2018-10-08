package com.rojo.travel.pesawat;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.InputType;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.rojo.travel.MyApplication;
import com.rojo.travel.OpenBrowser;
import com.rojo.travel.R;
import com.rojo.travel.akun.NeagaraDataSet;
import com.rojo.travel.akun.NegaraAdapter;
import com.rojo.travel.functions.Fungsi;
import com.rojo.travel.functions.VolleyObjectResult;
import com.rojo.travel.functions.VolleyObjectService;
import com.rojo.travel.sqlite.DatabaseHandler;
import com.rojo.travel.sqlite.temp_akun;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by kangyasin on 10/11/2017.
 */

public class DetailPemesanan extends AppCompatActivity {

    //============= Deklarasi components flight berangkat ===============
    TextView pswbrgkt, pswtjn, berangkat, namapesawatberangkat, kodeberangkat, jamberangkat, hargabrgkt,
            ruteberangkat;
    ImageView imgpp, imgberangkat;

    //============= Deklarasi components flight kembali ===============
    RelativeLayout detailpulang;
    TextView pulang, namapesawatpulang, kodepulang, jampulang, hargapulang, rutepulang;
    ImageView imgpulang;

    //============= Deklarasi components detail penumpang ===============
    Spinner spinTitle;
    EditText nama, namaLast, emailCon, nohp;
    TextView tvDetPenumpang;
    RelativeLayout detailPenumpang;
    ProgressDialog pDialog;

    //================= Deklarasi components rincian harga ===================
    TextView pesawatberangkat, hargapesawatberangkat, pesawatujuan, hargapesawatujuan, hargatotal;

    Button submiton;

    Fungsi fungsi = new Fungsi();
    VolleyObjectService vos, volleyObjectService;
    VolleyObjectResult vor, volleyObjectResult;
    //String url = "http://m.rojo.id/Api/submit_order";
    String url="";
    String urlFunct="Api/submit_order";
    JSONObject data = new JSONObject();
    DetailPenumpangDataSet dpds = new DetailPenumpangDataSet();
    DetailPenumpangChildDataSet dpcds = new DetailPenumpangChildDataSet();
    DetailPenumpangInfantDataSet dpids = new DetailPenumpangInfantDataSet();
    String[] titel, nama_dpn, nama_blkg, tgl_lahir, noKtp,
            nation, bagasi, id_passport, passportExpDate, passIssueCountry, tglPenerbit, content, cont;
    String[] titel_child, nama_dpnChild, nama_blkgChild, tgl_lahirChild, nation_child, bagasi_child, content_child;
    String[] titel_infant, nama_dpnInfant, nama_blkgInfant, tgl_lahirinfant, nation_infant, content_infant;
    String flight_id, flight_code, token, adult, child, infant, title_con, first_nm, last_nm, email,
            no_tlp, uid, Email_Exist;
    String[] titleDetPen = {"Mr","Mrs"};
    String departure_date, return_date;
    //String urlNegara = "http://192.168.254.1:8080/__ROJO/Rojos/Api/getNegara";
    String urlNegara = "";
    String urlFunctNegara = "Api/getNegara";
    private List<NeagaraDataSet> list = new ArrayList<NeagaraDataSet>();
    private NegaraAdapter negaraAdapter;
    //String urlPP = "http://192.168.254.1:8080/__ROJO/Rojos/Api/add_order_pesawat";
    String urlPP = "";
    String urlFunctPP = "Api/add_order_pesawat";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_pemesanan_pesawat);
        setTitle("Detail Pemesanan Tiket");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_USE_LOGO | ActionBar.DISPLAY_SHOW_TITLE);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#029a48")));

        MyApplication application=(MyApplication)getApplication();
        String PathWS=application.sgPathSrv;
        url=PathWS+urlFunct;
        urlNegara=PathWS+urlFunctNegara;
        urlPP=PathWS+urlFunctPP;

        //================ Initiate SharedPreferences jumPenumpang ================
        final SharedPreferences preferences = getApplicationContext().getSharedPreferences("jumPenumpang", 0);

        //================ Inisialisasi components data flight berangkat ================
        pswbrgkt = (TextView) findViewById(R.id.pswbrngkt);
        pswtjn = (TextView) findViewById(R.id.pswtjn);
        imgpp = (ImageView) findViewById(R.id.imgpp);
        berangkat = (TextView) findViewById(R.id.berangkat);
        imgberangkat = (ImageView) findViewById(R.id.imgberangkat);
        namapesawatberangkat = (TextView) findViewById(R.id.namapesawatberangkat);
        kodeberangkat = (TextView) findViewById(R.id.kodeberangkat);
        jamberangkat = (TextView) findViewById(R.id.jamberangkat);
        hargabrgkt = (TextView) findViewById(R.id.hargabrgkt);
        ruteberangkat = (TextView) findViewById(R.id.ruteberangkat);

        //================ Inisialisasi components data flight kembali ================
        detailpulang = (RelativeLayout) findViewById(R.id.detailpulang);
        pulang = (TextView) findViewById(R.id.pulang);
        imgpulang = (ImageView) findViewById(R.id.imgpulang);
        namapesawatpulang = (TextView) findViewById(R.id.namapesawatpulang);
        kodepulang = (TextView) findViewById(R.id.kodepulang);
        jampulang = (TextView) findViewById(R.id.jampulang);
        hargapulang = (TextView) findViewById(R.id.hargapulang);
        rutepulang = (TextView) findViewById(R.id.rutepulang);

        //================ Inisialisasi components detail penumpang ================
        spinTitle = (Spinner) findViewById(R.id.spinTitle);
        String[] title = {"Mr","Mrs"};
        nama = (EditText) findViewById(R.id.nama);
        namaLast = (EditText) findViewById(R.id.namaLast);
        emailCon = (EditText) findViewById(R.id.email);
            emailCon.setText(fungsi.getEmail(DetailPemesanan.this));
        nohp = (EditText) findViewById(R.id.nohp);
        tvDetPenumpang = (TextView) findViewById(R.id.tvDetPenumpang);
        detailPenumpang = (RelativeLayout) findViewById(R.id.detailPenumpang);

        //========================= Variabel utk menyimpan lemparan data penumpang dewasa =========================
        final int jd = preferences.getInt("jumDewasa", 0);
        final int jc = preferences.getInt("jumChild", 0);
        final int ji = preferences.getInt("jumInfant", 0);
        final Spinner[] spin = new Spinner[jd];
        final EditText[] etNamaDewasa = new EditText[jd];
        final EditText[] etLastName = new EditText[jd];
        final EditText[] tglLahirDewasa = new EditText[jd];
        final EditText[] etNoKtp = new EditText[jd];
        final EditText[] etNation = new EditText[jd];
        final EditText[] etBagasi = new EditText[jd];
        final EditText[] idPassport = new EditText[jd];
        final EditText[] etPassportExpDate = new EditText[jd];
        final EditText[] etPassIssueCountry = new EditText[jd];
        final EditText[] etTanggalPenerbit = new EditText[jd];

        titel = new String[jd];
        nama_dpn = new String[jd];
        nama_blkg = new String[jd];
        tgl_lahir = new String[jd];
        noKtp = new String[jd];
        nation = new String[jd];
        bagasi = new String[jd];
        id_passport = new String[jd];
        passportExpDate = new String[jd];
        passIssueCountry = new String[jd];
        tglPenerbit = new String[jd];
        content = new String[jd];
        cont = new String[jd];

        //========================= Variabel utk menyimpan lemparan data penumpang child ===================
        final String[] titelChild = {"Mr","Mrs"};
        final EditText[] etNamaChild = new EditText[jc];
        final EditText[] etNamaBlkgChild = new EditText[jc];
        final EditText[] etTglLahirChild = new EditText[jc];
        final EditText[] etNationChild = new EditText[jc];
        final EditText[] etBagasiChild = new EditText[jc];
        final Spinner[] spinChild = new Spinner[jc];

        titel_child = new String[jc];
        nama_dpnChild = new String[jc];
        nama_blkgChild = new String[jc];
        tgl_lahirChild = new String[jc];
        nation_child = new String[jc];
        bagasi_child = new String[jc];
        content_child = new String[jc];

        //========================== Variabel utk menyimpan lemparan data penumpang infant ===================
        final String[] titelInfant = {"Mr","Mrs"};
        final EditText[] etNamaInfant = new EditText[ji];
        final EditText[] etNamaBlkgInfant = new EditText[ji];
        final EditText[] etTglLahirInfant = new EditText[ji];
        final EditText[] etNationInfant = new EditText[ji];
        final Spinner[] spinInfant = new Spinner[ji];

        titel_infant = new String[ji];
        nama_dpnInfant = new String[ji];
        nama_blkgInfant = new String[ji];
        tgl_lahirinfant = new String[ji];
        nation_infant = new String[ji];
        content_infant = new String[ji];

        //================ Inisialisasi components rincian harga ================
        pesawatberangkat = (TextView) findViewById(R.id.pesawatberangkat);
        hargapesawatberangkat = (TextView) findViewById(R.id.hargapesawatberangkat);
        pesawatujuan = (TextView) findViewById(R.id.pesawatujuan);
        hargapesawatujuan = (TextView) findViewById(R.id.hargapesawatujuan);
        hargatotal = (TextView) findViewById(R.id.hargatotal);

        submiton = (Button) findViewById(R.id.submiton);

        DatabaseHandler databaseHandler2=new DatabaseHandler(DetailPemesanan.this);
        SQLiteDatabase db=databaseHandler2.getReadableDatabase();
        Cursor cursor=db.query("tbl_akun", new String[]{"id","akun_username","akun_email"},
                null, null, null, null, null);
        //Log.d("tes","Cursor Count : " + cursor.getCount());

        if(cursor.getCount()>0){
            temp_akun tempAkun=databaseHandler2.findOne(1);
            Email_Exist = tempAkun.getAkun_email();
            emailCon.setText(Email_Exist);
        }

        //================ Set values to components ================
        if (preferences.getInt("returnDate", 0) == 0) {
            detailpulang.setVisibility(View.GONE);
            pesawatujuan.setVisibility(View.GONE);
            hargapesawatujuan.setVisibility(View.GONE);

            final Bundle bundle = getIntent().getExtras();
            pswbrgkt.setText(bundle.getString("departure_city_name"));
            pswtjn.setText(bundle.getString("arrival_city_name"));
            imgpp.setImageResource(R.drawable.oneway);
            berangkat.setText(bundle.getString("departure_flight_date_str"));
            Glide.with(DetailPemesanan.this).load(bundle.getString("image")).asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.mipmap.ic_launcher)
                    .into(imgberangkat);
            namapesawatberangkat.setText(bundle.getString("airlines_name"));
            kodeberangkat.setText(bundle.getString("flight_number"));
            jamberangkat.setText(bundle.getString("simple_departure_time")+" - "+bundle.getString("simple_arrival_time"));
            hargabrgkt.setText(fungsi.formatIntRupiah(bundle.getString("price_value")));
            ruteberangkat.setText(bundle.getString("full_via"));
            int totalPenumpang = preferences.getInt("jumDewasa", 0)+preferences.getInt("jumChild", 0)+preferences.getInt("jumInfant", 0);
            pesawatberangkat.setText(bundle.getString("airlines_name")+" ("+totalPenumpang+" tiket)");
            hargapesawatberangkat.setText(fungsi.formatIntRupiah(bundle.getString("price_value")));
            hargatotal.setText(fungsi.formatIntRupiah(bundle.getString("price_value")));
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(DetailPemesanan.this, android.R.layout.simple_spinner_dropdown_item, title);
            spinTitle.setAdapter(arrayAdapter);
            detailPenumpang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
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
                    final LinearLayout[] layoutDewasa = new LinearLayout[jd];
                    TextView[] tvDewasa = new TextView[jd];
                    TextView[] tvTambahBagasi = new TextView[jd];
                    LinearLayout[]  layoutWrap = new LinearLayout[jd];
                    LinearLayout[]  layoutWrap2 = new LinearLayout[jd];
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
                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(DetailPemesanan.this, android.R.layout.simple_spinner_dropdown_item, titleDetPen);
                        spin[i].setAdapter(arrayAdapter);
                        etNamaDewasa[i] = new EditText(DetailPemesanan.this);
                        etNamaDewasa[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        etNamaDewasa[i].setHintTextColor(getResources().getColor(R.color.graytext));
                        etNamaDewasa[i].setHint(Html.fromHtml("<small>"+"Nama depan"+"</small>"));
                        etNamaDewasa[i].setInputType(InputType.TYPE_CLASS_TEXT);
                        etLastName[i] = new EditText(DetailPemesanan.this);
                        etLastName[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        etLastName[i].setHintTextColor(getResources().getColor(R.color.graytext));
                        etLastName[i].setHint(Html.fromHtml("<small>"+"Nama belakang"+"</small>"));
                        etLastName[i].setInputType(InputType.TYPE_CLASS_TEXT);
                        tglLahirDewasa[i] = new EditText(DetailPemesanan.this);
                        tglLahirDewasa[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        tglLahirDewasa[i].setHintTextColor(getResources().getColor(R.color.graytext));
                        tglLahirDewasa[i].setHint(Html.fromHtml("<small>"+"Tanggal lahir"+"</small>"));
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
                                        String kalender = String.valueOf(year)+"-"+mth+"-"+date;
                                        tglLahirDewasa[x].setText(kalender);
                                    }
                                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
                                dpDewasa.show();
                            }
                        });
                        etNoKtp[i] = new EditText(DetailPemesanan.this);
                        etNoKtp[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        etNoKtp[i].setHintTextColor(getResources().getColor(R.color.graytext));
                        etNoKtp[i].setHint(Html.fromHtml("<small>"+"No KTP/SIM/Kartu Pelajar"+"</small>"));
                        etNoKtp[i].setInputType(InputType.TYPE_CLASS_NUMBER);
                        etNation[i] = new EditText(DetailPemesanan.this);
                        etNation[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        etNation[i].setHintTextColor(getResources().getColor(R.color.graytext));
                        etNation[i].setHint(Html.fromHtml("<small>"+"Kebangsaan"+"</small>"));
                        etNation[i].setFocusableInTouchMode(false);
                        etNation[i].setFocusable(false);
                        etNation[i].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                list.clear();
                                final AlertDialog builder = new AlertDialog.Builder(DetailPemesanan.this, R.style.DialogTheme).create();
                                final LayoutInflater inflater = DetailPemesanan.this.getLayoutInflater();
                                final View view = inflater.inflate(R.layout.list_negara, null, false);
                                final EditText cariNegara = (EditText) view.findViewById(R.id.cariNegara);
                                final ListView listNegara = (ListView) view.findViewById(R.id.listNegara);
                                final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.proBar);
                                builder.setView(view);
                                volleyObjectResult = new VolleyObjectResult() {
                                    @Override
                                    public void resSuccess(String requestType, JSONObject response) {
                                        progressBar.setVisibility(View.GONE);
                                        try {
                                            JSONArray array = response.getJSONArray("negara");
                                            for (int i=0; i<array.length(); i++){
                                                JSONObject object = array.getJSONObject(i);
                                                NeagaraDataSet bds = new NeagaraDataSet();
                                                bds.setNegara_id(object.getString("negara_id"));
                                                bds.setNegara_nama_1(object.getString("negara_nama_1"));
                                                bds.setNegara_kode_alpha_2(object.getString("negara_kode_alpha_2"));
                                                list.add(bds);
                                            }
                                            negaraAdapter = new NegaraAdapter(DetailPemesanan.this, list);
                                            negaraAdapter.notifyDataSetChanged();
                                            listNegara.setAdapter(negaraAdapter);
                                            //========================= Action Listener EditText Cari Negara =====================
                                            cariNegara.addTextChangedListener(new TextWatcher() {
                                                @Override
                                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                                }
                                                @Override
                                                public void onTextChanged(CharSequence s, int start, int before, int count) {

                                                }
                                                @Override
                                                public void afterTextChanged(Editable s) {
                                                    String text = cariNegara.getText().toString().toLowerCase(Locale.getDefault());
                                                    negaraAdapter.filter(text);
                                                }
                                            });
                                        } catch (Exception e){
                                            e.printStackTrace();
                                        }
                                    }
                                    @Override
                                    public void resError(String requestType, VolleyError error) {
                                        Toast.makeText(getApplicationContext(), "Pencarian gagal", Toast.LENGTH_LONG).show();
                                        builder.dismiss();
                                    }
                                };
                                volleyObjectService = new VolleyObjectService(volleyObjectResult, DetailPemesanan.this);
                                volleyObjectService.getJsonObject("GETCALL", urlNegara);
                                progressBar.setVisibility(View.VISIBLE);
                                builder.show();
                                listNegara.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        String negaranama = list.get(position).getNegara_nama_1();
                                        String kodeNegara = list.get(position).getNegara_kode_alpha_2();
                                        etNation[x].setText(negaranama+" ("+kodeNegara+")");
                                        builder.dismiss();
                                    }
                                });
                            }
                        });
                        //========================== Layout tambah bagasi =========================
                        layoutWrap2[i] = new LinearLayout(DetailPemesanan.this);
                        layoutWrap2[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        layoutWrap2[i].setOrientation(LinearLayout.HORIZONTAL);
                        etBagasi[i] = new EditText(DetailPemesanan.this);
                        etBagasi[i].setLayoutParams(new ViewGroup.LayoutParams(200, ViewGroup.LayoutParams.WRAP_CONTENT));
                        etBagasi[i].setHintTextColor(getResources().getColor(R.color.graytext));
                        etBagasi[i].setHint(Html.fromHtml("<small>"+"Bagasi tambahan"+"</small>"));
                        etBagasi[i].setInputType(InputType.TYPE_CLASS_NUMBER);
                        etBagasi[i].setGravity(Gravity.RIGHT);
                        etBagasi[i].setText("0");
                        tvTambahBagasi[i] = new TextView(DetailPemesanan.this);
                        tvTambahBagasi[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        tvTambahBagasi[i].setText(" kg");

                        int maxLength3 = 1;
                        InputFilter[] fArray = new InputFilter[1];
                        fArray[0] = new InputFilter.LengthFilter(maxLength3);
                        etBagasi[i].setFilters(fArray);

                        idPassport[i] = new EditText(DetailPemesanan.this);
                        idPassport[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        idPassport[i].setHintTextColor(getResources().getColor(R.color.graytext));
                        idPassport[i].setHint(Html.fromHtml("<small>"+"No passport"+"</small>"));
                        idPassport[i].setInputType(InputType.TYPE_CLASS_TEXT);
                        etPassportExpDate[i] = new EditText(DetailPemesanan.this);
                        etPassportExpDate[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        etPassportExpDate[i].setHintTextColor(getResources().getColor(R.color.graytext));
                        etPassportExpDate[i].setHint(Html.fromHtml("<small>"+"Expire date passport"+"</small>"));
                        etPassportExpDate[i].setFocusableInTouchMode(false);
                        etPassportExpDate[i].setFocusable(false);
                        etPassportExpDate[i].setOnClickListener(new View.OnClickListener() {
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
                                        String kalender = String.valueOf(year)+"-"+mth+"-"+date;
                                        etPassportExpDate[x].setText(kalender);
                                    }
                                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
                                dpDewasa.show();
                            }
                        });
                        etPassIssueCountry[i] = new EditText(DetailPemesanan.this);
                        etPassIssueCountry[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        etPassIssueCountry[i].setHintTextColor(getResources().getColor(R.color.graytext));
                        etPassIssueCountry[i].setHint(Html.fromHtml("<small>"+"Passport issued country"+"</small>"));
                        etPassIssueCountry[i].setFocusableInTouchMode(false);
                        etPassIssueCountry[i].setFocusable(false);
                        etPassIssueCountry[i].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                list.clear();
                                final AlertDialog builder = new AlertDialog.Builder(DetailPemesanan.this, R.style.DialogTheme).create();
                                final LayoutInflater inflater = DetailPemesanan.this.getLayoutInflater();
                                final View view = inflater.inflate(R.layout.list_negara, null, false);
                                final EditText cariNegara = (EditText) view.findViewById(R.id.cariNegara);
                                final ListView listNegara = (ListView) view.findViewById(R.id.listNegara);
                                final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.proBar);
                                builder.setView(view);
                                volleyObjectResult = new VolleyObjectResult() {
                                    @Override
                                    public void resSuccess(String requestType, JSONObject response) {
                                        progressBar.setVisibility(View.GONE);
                                        try {
                                            JSONArray array = response.getJSONArray("negara");
                                            for (int i=0; i<array.length(); i++){
                                                JSONObject object = array.getJSONObject(i);
                                                NeagaraDataSet bds = new NeagaraDataSet();
                                                bds.setNegara_id(object.getString("negara_id"));
                                                bds.setNegara_nama_1(object.getString("negara_nama_1"));
                                                bds.setNegara_kode_alpha_2(object.getString("negara_kode_alpha_2"));
                                                list.add(bds);
                                            }
                                            negaraAdapter = new NegaraAdapter(DetailPemesanan.this, list);
                                            negaraAdapter.notifyDataSetChanged();
                                            listNegara.setAdapter(negaraAdapter);
                                            //========================= Action Listener EditText Cari Negara =====================
                                            cariNegara.addTextChangedListener(new TextWatcher() {
                                                @Override
                                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                                }
                                                @Override
                                                public void onTextChanged(CharSequence s, int start, int before, int count) {

                                                }
                                                @Override
                                                public void afterTextChanged(Editable s) {
                                                    String text = cariNegara.getText().toString().toLowerCase(Locale.getDefault());
                                                    negaraAdapter.filter(text);
                                                }
                                            });
                                        } catch (Exception e){
                                            e.printStackTrace();
                                        }
                                    }
                                    @Override
                                    public void resError(String requestType, VolleyError error) {
                                        Toast.makeText(getApplicationContext(), "Pencarian gagal", Toast.LENGTH_LONG).show();
                                        builder.dismiss();
                                    }
                                };
                                volleyObjectService = new VolleyObjectService(volleyObjectResult, DetailPemesanan.this);
                                volleyObjectService.getJsonObject("GETCALL", urlNegara);
                                progressBar.setVisibility(View.VISIBLE);
                                builder.show();
                                listNegara.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        String negaranama = list.get(position).getNegara_nama_1();
                                        String kodeNegara = list.get(position).getNegara_kode_alpha_2();
                                        etPassIssueCountry[x].setText(negaranama+" ("+kodeNegara+")");
                                        builder.dismiss();
                                    }
                                });
                            }
                        });
                        etTanggalPenerbit[i] = new EditText(DetailPemesanan.this);
                        etTanggalPenerbit[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        etTanggalPenerbit[i].setHintTextColor(getResources().getColor(R.color.graytext));
                        etTanggalPenerbit[i].setHint(Html.fromHtml("<small>"+"Tanggal penerbitan pasport"+"</small>"));
                        etTanggalPenerbit[i].setFocusableInTouchMode(false);
                        etTanggalPenerbit[i].setFocusable(false);
                        etTanggalPenerbit[i].setOnClickListener(new View.OnClickListener() {
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
                                        String kalender = String.valueOf(year)+"-"+mth+"-"+date;
                                        etTanggalPenerbit[x].setText(kalender);
                                    }
                                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
                                dpDewasa.show();
                            }
                        });
                        //================== Add components ke dalam layout ==================
                        layoutDewasa[i].addView(tvDewasa[i]);
                        layoutWrap[i].addView(spin[i]); layoutWrap[i].addView(etNamaDewasa[i]);
                        layoutDewasa[i].addView(layoutWrap[i]);
                        layoutDewasa[i].addView(etLastName[i]);
                        layoutDewasa[i].addView(tglLahirDewasa[i]);
                        //layoutDewasa[i].addView(etNoKtp[i]);
                        if (bundle.getString("airlines_name").equals("AIRASIA") ||
                                bundle.getString("airlines_name").equals("CITILINK") ||
                                bundle.getString("airlines_name").equals("TIGER") ||
                                bundle.getString("airlines_name").equals("MANDALA")) {
                            layoutDewasa[i].addView(etNation[i]);
                        }
                        layoutWrap2[i].addView(etBagasi[i]); layoutWrap2[i].addView(tvTambahBagasi[i]);
                        if (bundle.getString("airlines_name").equals("AIRASIA") ||
                                bundle.getString("airlines_name").equals("TIGER") ||
                                bundle.getString("airlines_name").equals("MANDALA")) {
                            layoutDewasa[i].addView(layoutWrap2[i]);
                        }
                        if (bundle.getString("domestik").equals("0")) {
                            layoutDewasa[i].addView(idPassport[i]);
                            layoutDewasa[i].addView(etPassportExpDate[i]);
                            layoutDewasa[i].addView(etPassIssueCountry[i]);
                            layoutDewasa[i].addView(etTanggalPenerbit[i]);
                        }
                        mainLayout.addView(layoutDewasa[i]);
                    }
                    //===================== Linear Layout Child ========================
                    if (jc > 0) {
                        LinearLayout[] layoutChild = new LinearLayout[jc];
                        LinearLayout[] layoutWrapChild = new LinearLayout[jc];
                        LinearLayout[] layoutWrapBagasiChild = new LinearLayout[jc];
                        TextView[] tvChild = new TextView[jc];
                        TextView[] tvTambahBagasiChild = new TextView[jc];
                        for (int i=0; i<layoutChild.length; i++) {
                            final int x = i;
                            layoutChild[i] = new LinearLayout(DetailPemesanan.this);
                            layoutChild[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            layoutChild[i].setOrientation(LinearLayout.VERTICAL);
                            layoutChild[i].setPadding(6, 6, 6, 6);
                            tvChild[i] = new TextView(DetailPemesanan.this);
                            tvChild[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            tvChild[i].setText("Child "+(i+1));
                            tvChild[i].setTypeface(null, Typeface.BOLD);
                            layoutWrapChild[i] = new LinearLayout(DetailPemesanan.this);
                            layoutWrapChild[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            layoutWrapChild[i].setOrientation(LinearLayout.HORIZONTAL);
                            spinChild[i] = new Spinner(DetailPemesanan.this);
                            spinChild[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(DetailPemesanan.this, android.R.layout.simple_spinner_dropdown_item, titelChild);
                            spinChild[i].setAdapter(arrayAdapter);
                            etNamaChild[i] = new EditText(DetailPemesanan.this);
                            etNamaChild[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            etNamaChild[i].setHintTextColor(getResources().getColor(R.color.graytext));
                            etNamaChild[i].setHint(Html.fromHtml("<small>"+"Nama depan"+"</small>"));
                            etNamaBlkgChild[i] = new EditText(DetailPemesanan.this);
                            etNamaBlkgChild[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            etNamaBlkgChild[i].setHintTextColor(getResources().getColor(R.color.graytext));
                            etNamaBlkgChild[i].setHint(Html.fromHtml("<small>"+"Nama belakang"+"</small>"));
                            etTglLahirChild[i] = new EditText(DetailPemesanan.this);
                            etTglLahirChild[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            etTglLahirChild[i].setHintTextColor(getResources().getColor(R.color.graytext));
                            etTglLahirChild[i].setHint(Html.fromHtml("<small>"+"Tanggal lahir"+"</small>"));
                            etTglLahirChild[i].setFocusableInTouchMode(false);
                            etTglLahirChild[i].setFocusable(false);
                            etTglLahirChild[i].setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    final Calendar calendar = Calendar.getInstance();
                                    DatePickerDialog dpChild = new DatePickerDialog(DetailPemesanan.this, new DatePickerDialog.OnDateSetListener() {
                                        @Override
                                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                            String date, mth;
                                            if (month < 10) mth = "0"+(month+1);
                                            else mth = String.valueOf(month+1);
                                            if (dayOfMonth < 10) date = "0"+dayOfMonth;
                                            else date = String.valueOf(dayOfMonth);
                                            String kalender = String.valueOf(year)+"-"+mth+"-"+date;
                                            etTglLahirChild[x].setText(kalender);
                                        }
                                    }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
                                    dpChild.show();
                                }
                            });
                            etNationChild[i] = new EditText(DetailPemesanan.this);
                            etNationChild[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            etNationChild[i].setHintTextColor(getResources().getColor(R.color.graytext));
                            etNationChild[i].setHint(Html.fromHtml("<small>"+"Kebangsaan"+"</small>"));
                            etNationChild[i].setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    list.clear();
                                    final AlertDialog builder = new AlertDialog.Builder(DetailPemesanan.this, R.style.DialogTheme).create();
                                    final LayoutInflater inflater = DetailPemesanan.this.getLayoutInflater();
                                    final View view = inflater.inflate(R.layout.list_negara, null, false);
                                    final EditText cariNegara = (EditText) view.findViewById(R.id.cariNegara);
                                    final ListView listNegara = (ListView) view.findViewById(R.id.listNegara);
                                    final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.proBar);
                                    builder.setView(view);
                                    volleyObjectResult = new VolleyObjectResult() {
                                        @Override
                                        public void resSuccess(String requestType, JSONObject response) {
                                            progressBar.setVisibility(View.GONE);
                                            try {
                                                JSONArray array = response.getJSONArray("negara");
                                                for (int i=0; i<array.length(); i++){
                                                    JSONObject object = array.getJSONObject(i);
                                                    NeagaraDataSet bds = new NeagaraDataSet();
                                                    bds.setNegara_id(object.getString("negara_id"));
                                                    bds.setNegara_nama_1(object.getString("negara_nama_1"));
                                                    bds.setNegara_kode_alpha_2(object.getString("negara_kode_alpha_2"));
                                                    list.add(bds);
                                                }
                                                negaraAdapter = new NegaraAdapter(DetailPemesanan.this, list);
                                                negaraAdapter.notifyDataSetChanged();
                                                listNegara.setAdapter(negaraAdapter);
                                                //========================= Action Listener EditText Cari Negara =====================
                                                cariNegara.addTextChangedListener(new TextWatcher() {
                                                    @Override
                                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                                    }
                                                    @Override
                                                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                                                    }
                                                    @Override
                                                    public void afterTextChanged(Editable s) {
                                                        String text = cariNegara.getText().toString().toLowerCase(Locale.getDefault());
                                                        negaraAdapter.filter(text);
                                                    }
                                                });
                                            } catch (Exception e){
                                                e.printStackTrace();
                                            }
                                        }
                                        @Override
                                        public void resError(String requestType, VolleyError error) {
                                            Toast.makeText(getApplicationContext(), "Pencarian gagal", Toast.LENGTH_LONG).show();
                                            builder.dismiss();
                                        }
                                    };
                                    volleyObjectService = new VolleyObjectService(volleyObjectResult, DetailPemesanan.this);
                                    volleyObjectService.getJsonObject("GETCALL", urlNegara);
                                    progressBar.setVisibility(View.VISIBLE);
                                    builder.show();
                                    listNegara.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            String negaranama = list.get(position).getNegara_nama_1();
                                            String kodeNegara = list.get(position).getNegara_kode_alpha_2();
                                            etNationChild[x].setText(negaranama+" ("+kodeNegara+")");
                                            builder.dismiss();
                                        }
                                    });
                                }
                            });
                            layoutWrapBagasiChild[i] = new LinearLayout(DetailPemesanan.this);
                            layoutWrapBagasiChild[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            layoutWrapBagasiChild[i].setOrientation(LinearLayout.HORIZONTAL);
                            etBagasiChild[i] = new EditText(DetailPemesanan.this);
                            etBagasiChild[i].setLayoutParams(new ViewGroup.LayoutParams(200, ViewGroup.LayoutParams.WRAP_CONTENT));
                            etBagasiChild[i].setHintTextColor(getResources().getColor(R.color.graytext));
                            etBagasiChild[i].setHint(Html.fromHtml("<small>"+"Bagasi tambahan"+"</small>"));
                            etBagasiChild[i].setGravity(Gravity.RIGHT);
                            etBagasiChild[i].setText("0");
                            tvTambahBagasiChild[i] = new TextView(DetailPemesanan.this);
                            tvTambahBagasiChild[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            tvTambahBagasiChild[i].setText(" kg");

                            int maxLength4 = 1;
                            InputFilter[] fArray = new InputFilter[1];
                            fArray[0] = new InputFilter.LengthFilter(maxLength4);
                            etBagasiChild[i].setFilters(fArray);

                            //================== Add components ke dalam layout ==================
                            layoutChild[i].addView(tvChild[i]);
                            layoutWrapChild[i].addView(spinChild[i]); layoutWrapChild[i].addView(etNamaChild[i]);
                            layoutChild[i].addView(layoutWrapChild[i]);
                            layoutChild[i].addView(etNamaBlkgChild[i]);
                            layoutChild[i].addView(etTglLahirChild[i]);
                            layoutWrapBagasiChild[i].addView(etBagasiChild[i]); layoutWrapBagasiChild[i].addView(tvTambahBagasiChild[i]);
                            if (bundle.getString("airlines_name").equals("AIRASIA") ||
                                    bundle.getString("airlines_name").equals("CITILINK") ||
                                    bundle.getString("airlines_name").equals("TIGER") ||
                                    bundle.getString("airlines_name").equals("MANDALA")) {
                                layoutChild[i].addView(etNationChild[i]);
                                layoutChild[i].addView(layoutWrapBagasiChild[i]);
                            }
                            mainLayout.addView(layoutChild[i]);
                        }
                    }
                    //===================== Linear Layout Infant ========================
                    if (ji > 0) {
                        LinearLayout[] layoutInfant = new LinearLayout[ji];
                        TextView[] tvInfant = new TextView[ji];
                        LinearLayout[] layoutWrapInfant = new LinearLayout[ji];
                        for (int i=0; i<layoutInfant.length; i++) {
                            final int x = i;
                            layoutInfant[i] = new LinearLayout(DetailPemesanan.this);
                            layoutInfant[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            layoutInfant[i].setOrientation(LinearLayout.VERTICAL);
                            layoutInfant[i].setPadding(6, 6, 6, 6);
                            tvInfant[i] = new TextView(DetailPemesanan.this);
                            tvInfant[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            tvInfant[i].setText("Infant "+(i+1));
                            tvInfant[i].setTypeface(null, Typeface.BOLD);
                            layoutWrapInfant[i] = new LinearLayout(DetailPemesanan.this);
                            layoutWrapInfant[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            layoutWrapInfant[i].setOrientation(LinearLayout.HORIZONTAL);
                            spinInfant[i] = new Spinner(DetailPemesanan.this);
                            spinInfant[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(DetailPemesanan.this, android.R.layout.simple_spinner_dropdown_item, titelInfant);
                            spinInfant[i].setAdapter(arrayAdapter);
                            etNamaInfant[i] = new EditText(DetailPemesanan.this);
                            etNamaInfant[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            etNamaInfant[i].setHintTextColor(getResources().getColor(R.color.graytext));
                            etNamaInfant[i].setHint(Html.fromHtml("<small>"+"Nama depan"+"</small>"));
                            etNamaBlkgInfant[i] = new EditText(DetailPemesanan.this);
                            etNamaBlkgInfant[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            etNamaBlkgInfant[i].setHintTextColor(getResources().getColor(R.color.graytext));
                            etNamaBlkgInfant[i].setHint(Html.fromHtml("<small>"+"Nama belakang"+"</small>"));
                            etTglLahirInfant[i] = new EditText(DetailPemesanan.this);
                            etTglLahirInfant[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            etTglLahirInfant[i].setHintTextColor(getResources().getColor(R.color.graytext));
                            etTglLahirInfant[i].setHint(Html.fromHtml("<small>"+"Tanggal lahir"+"</small>"));
                            etTglLahirInfant[i].setFocusableInTouchMode(false);
                            etTglLahirInfant[i].setFocusable(false);
                            etTglLahirInfant[i].setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    final Calendar calendar = Calendar.getInstance();
                                    DatePickerDialog dpInfant = new DatePickerDialog(DetailPemesanan.this, new DatePickerDialog.OnDateSetListener() {
                                        @Override
                                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                            String date, mth;
                                            if (month < 10) mth = "0"+(month+1);
                                            else mth = String.valueOf(month+1);
                                            if (dayOfMonth < 10) date = "0"+dayOfMonth;
                                            else date = String.valueOf(dayOfMonth);
                                            String kalender = String.valueOf(year)+"-"+mth+"-"+date;
                                            etTglLahirInfant[x].setText(kalender);
                                        }
                                    }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
                                    dpInfant.show();
                                }
                            });
                            etNationInfant[i] = new EditText(DetailPemesanan.this);
                            etNationInfant[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            etNationInfant[i].setHintTextColor(getResources().getColor(R.color.graytext));
                            etNationInfant[i].setHint(Html.fromHtml("<small>"+"Kebangsaan"+"</small>"));
                            etNationInfant[i].setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    list.clear();
                                    final AlertDialog builder = new AlertDialog.Builder(DetailPemesanan.this, R.style.DialogTheme).create();
                                    final LayoutInflater inflater = DetailPemesanan.this.getLayoutInflater();
                                    final View view = inflater.inflate(R.layout.list_negara, null, false);
                                    final EditText cariNegara = (EditText) view.findViewById(R.id.cariNegara);
                                    final ListView listNegara = (ListView) view.findViewById(R.id.listNegara);
                                    final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.proBar);
                                    builder.setView(view);
                                    volleyObjectResult = new VolleyObjectResult() {
                                        @Override
                                        public void resSuccess(String requestType, JSONObject response) {
                                            progressBar.setVisibility(View.GONE);
                                            try {
                                                JSONArray array = response.getJSONArray("negara");
                                                for (int i=0; i<array.length(); i++){
                                                    JSONObject object = array.getJSONObject(i);
                                                    NeagaraDataSet bds = new NeagaraDataSet();
                                                    bds.setNegara_id(object.getString("negara_id"));
                                                    bds.setNegara_nama_1(object.getString("negara_nama_1"));
                                                    bds.setNegara_kode_alpha_2(object.getString("negara_kode_alpha_2"));
                                                    list.add(bds);
                                                }
                                                negaraAdapter = new NegaraAdapter(DetailPemesanan.this, list);
                                                negaraAdapter.notifyDataSetChanged();
                                                listNegara.setAdapter(negaraAdapter);
                                                //========================= Action Listener EditText Cari Negara =====================
                                                cariNegara.addTextChangedListener(new TextWatcher() {
                                                    @Override
                                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                                    }
                                                    @Override
                                                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                                                    }
                                                    @Override
                                                    public void afterTextChanged(Editable s) {
                                                        String text = cariNegara.getText().toString().toLowerCase(Locale.getDefault());
                                                        negaraAdapter.filter(text);
                                                    }
                                                });
                                            } catch (Exception e){
                                                e.printStackTrace();
                                            }
                                        }
                                        @Override
                                        public void resError(String requestType, VolleyError error) {
                                            Toast.makeText(getApplicationContext(), "Pencarian gagal", Toast.LENGTH_LONG).show();
                                            builder.dismiss();
                                        }
                                    };
                                    volleyObjectService = new VolleyObjectService(volleyObjectResult, DetailPemesanan.this);
                                    volleyObjectService.getJsonObject("GETCALL", urlNegara);
                                    progressBar.setVisibility(View.VISIBLE);
                                    builder.show();
                                    listNegara.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            String negaranama = list.get(position).getNegara_nama_1();
                                            String kodeNegara = list.get(position).getNegara_kode_alpha_2();
                                            etNationInfant[x].setText(negaranama+" ("+kodeNegara+")");
                                            builder.dismiss();
                                        }
                                    });
                                }
                            });

                            //================== Add components ke dalam layout ==================
                            layoutInfant[i].addView(tvInfant[i]);
                            layoutWrapInfant[i].addView(spinInfant[i]); layoutWrapInfant[i].addView(etNamaInfant[i]);
                            layoutInfant[i].addView(layoutWrapInfant[i]);
                            layoutInfant[i].addView(etNamaBlkgInfant[i]);
                            layoutInfant[i].addView(etTglLahirInfant[i]);
                            if (bundle.getString("airlines_name").equals("AIRASIA") ||
                                    bundle.getString("airlines_name").equals("CITILINK") ||
                                    bundle.getString("airlines_name").equals("TIGER") ||
                                    bundle.getString("airlines_name").equals("MANDALA")) {
                                layoutInfant[i].addView(etNationInfant[i]);
                            }
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
                                //noKtp[i] = etNoKtp[i].getText().toString();
                                nation[i] = etNation[i].getText().toString();
                                if (nation[i].length() > 0) {
                                    int firstBracket = nation[i].indexOf('(');
                                    content[i] = nation[i].substring(firstBracket + 1, nation[i].indexOf(')', firstBracket));
                                }
                                bagasi[i] = etBagasi[i].getText().toString();
                                id_passport[i] = idPassport[i].getText().toString();
                                passportExpDate[i] = etPassportExpDate[i].getText().toString();
                                passIssueCountry[i] = etPassIssueCountry[i].getText().toString();
                                if (passIssueCountry[i].length() > 0) {
                                    int firstBracket = nation[i].indexOf('(');
                                    cont[i] = nation[i].substring(firstBracket + 1, nation[i].indexOf(')', firstBracket));
                                }
                                tglPenerbit[i] = etTanggalPenerbit[i].getText().toString();

                                dpds.setTitle_pass(titel);
                                dpds.setFirst_nm(nama_dpn);
                                dpds.setLast_nm(nama_blkg);
                                dpds.setTgl_lahir(tgl_lahir);
                                //dpds.setNo_ktp(noKtp);
                                dpds.setPassportNationality(content);
                                dpds.setdCheckinBaggage(bagasi);
                                dpds.setId_passport(id_passport);
                                dpds.setExpPassportDate(passportExpDate);
                                dpds.setPassportIssuing(cont);
                                dpds.setPassportIssuedDate(tglPenerbit);

                                if (nama_dpn[i].length() == 0) {
                                    String message = "Nama Depan Penumpang Harus Diisi";
                                    fungsi.showSnackbar(v, message);
                                    tvDetPenumpang.setText("Klik disini untuk input data penumpang!");
                                } else if (nama_blkg[i].length() == 0) {
                                    String message = "Nama Belakang Penumpang Harus Diisi";
                                    fungsi.showSnackbar(v, message);
                                    tvDetPenumpang.setText("Klik disini untuk input data penumpang!");
                                } else if (tgl_lahir[i].length() == 0) {
                                    String message = "Tanggal Lahir Penumpang Harus Diisi";
                                    fungsi.showSnackbar(v, message);
                                    tvDetPenumpang.setText("Klik disini untuk input data penumpang!");
                                } else {
                                    tvDetPenumpang.setText("Data Penumpang OK!");
                                }
                            }
                            if (jc > 0) {
                                for (int i=0; i<etNamaChild.length; i++) {
                                    titel_child[i] = spinChild[i].getSelectedItem().toString();
                                    nama_dpnChild[i] = etNamaChild[i].getText().toString();
                                    nama_blkgChild[i] = etNamaBlkgChild[i].getText().toString();
                                    tgl_lahirChild[i] = etTglLahirChild[i].getText().toString();
                                    nation_child[i] = etNationChild[i].getText().toString();
                                    if (nation_child[i].length() > 0) {
                                        int firstBracket = nation_child[i].indexOf('(');
                                        content_child[i] = nation_child[i].substring(firstBracket + 1, nation_child[i].indexOf(')', firstBracket));
                                    }
                                    bagasi_child[i] = etBagasiChild[i].getText().toString();

                                    dpcds.setTitelChild(titel_child);
                                    dpcds.setNamaChild(nama_dpnChild);
                                    dpcds.setNamaBlkgChild(nama_blkgChild);
                                    dpcds.setTglLahirChild(tgl_lahirChild);
                                    dpcds.setPassportnationality(content_child);
                                    dpcds.setBagage(bagasi_child);

                                    DateFormat dtf = new SimpleDateFormat("yyyy-MM-dd");
                                    long selisih = 0;
                                    try {
                                        Date dateChild = dtf.parse(tgl_lahirChild[i]);
                                        Date dateNow = new Date();
                                        dateNow = dtf.parse(dtf.format(dateNow));
                                        long dif = dateNow.getTime() - dateChild.getTime();
                                        selisih = TimeUnit.DAYS.convert(dif, TimeUnit.MILLISECONDS);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    //Toast.makeText(DetailPemesanan.this, String.valueOf(selisih), Toast.LENGTH_LONG).show();
                                    if (nama_dpnChild[i].length() == 0) {
                                        String message = "Nama Depan Anak Harus Diisi";
                                        fungsi.showSnackbar(v, message);
                                        tvDetPenumpang.setText("Klik disini untuk input data penumpang!");
                                    } else if (nama_blkgChild[i].length() == 0) {
                                        String message = "Nama Belakang Anak Harus Diisi";
                                        fungsi.showSnackbar(v, message);
                                        tvDetPenumpang.setText("Klik disini untuk input data penumpang!");
                                    } else if (tgl_lahirChild[i].length() == 0) {
                                        String message = "Tanggal Lahir Anak Harus Diisi";
                                        fungsi.showSnackbar(v, message);
                                        tvDetPenumpang.setText("Klik disini untuk input data penumpang!");
                                    } else if(selisih < 730 || selisih > 4015) {
                                        String message = "Umur anak harus 2thn - 11thn";
                                        fungsi.showSnackbar(v, message);
                                        tvDetPenumpang.setText("Klik disini untuk input data penumpang!");
                                    } else {
                                        tvDetPenumpang.setText("Data Penumpang OK!");
                                    }
                                }
                            }
                            if (ji > 0) {
                                for (int i=0; i<etNamaInfant.length; i++) {
                                    titel_infant[i] = spinInfant[i].getSelectedItem().toString();
                                    nama_dpnInfant[i] = etNamaInfant[i].getText().toString();
                                    nama_blkgInfant[i] = etNamaBlkgInfant[i].getText().toString();
                                    tgl_lahirinfant[i] = etTglLahirInfant[i].getText().toString();
                                    nation_infant[i] = etNationInfant[i].getText().toString();
                                    if (nation_infant[i].length() > 0) {
                                        int firstBracket = nation_infant[i].indexOf('(');
                                        content_infant[i] = nation_infant[i].substring(firstBracket + 1, nation_infant[i].indexOf(')', firstBracket));
                                    }

                                    dpids.setTitelInfant(titel_infant);
                                    dpids.setNamaInfant(nama_dpnInfant);
                                    dpids.setNama_blkgInfant(nama_blkgInfant);
                                    dpids.setTglLahirInfant(tgl_lahirinfant);
                                    dpids.setPassportnationality(content_infant);

                                    DateFormat dtf = new SimpleDateFormat("yyyy-MM-dd");
                                    long selisih = 0;
                                    try {
                                        Date dateInfant = dtf.parse(tgl_lahirinfant[i]);
                                        Date dateNow = new Date();
                                        dateNow = dtf.parse(dtf.format(dateNow));
                                        long dif = dateNow.getTime() - dateInfant.getTime();
                                        selisih = TimeUnit.DAYS.convert(dif, TimeUnit.MILLISECONDS);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    if (nama_dpnInfant[i].length() == 0) {
                                        String message = "Nama Depan Bayi Harus Diisi";
                                        fungsi.showSnackbar(v, message);
                                        tvDetPenumpang.setText("Klik disini untuk input data penumpang!");
                                    } else if (nama_blkgInfant[i].length() == 0) {
                                        String message = "Nama Belakang Bayi Harus Diisi";
                                        fungsi.showSnackbar(v, message);
                                        tvDetPenumpang.setText("Klik disini untuk input data penumpang!");
                                    } else if (tgl_lahirinfant[i].length() == 0) {
                                        String message = "Tanggal Lahir Bayi Harus Diisi";
                                        fungsi.showSnackbar(v, message);
                                        tvDetPenumpang.setText("Klik disini untuk input data penumpang!");
                                    } else if(selisih >= 730) {
                                        String message = "Umur bayi harus dibawah 2thn";
                                        fungsi.showSnackbar(v, message);
                                        tvDetPenumpang.setText("Klik disini untuk input data penumpang!");
                                    } else {
                                        tvDetPenumpang.setText("Data Penumpang OK!");
                                    }
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
            submiton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    RelativeLayout layout = new RelativeLayout(DetailPemesanan.this);
                    ProgressBar progressBar = new ProgressBar(DetailPemesanan.this,null, android.R.attr.progressBarStyleSmall);
                    progressBar.setIndeterminate(true);
                    progressBar.setVisibility(View.VISIBLE);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(100,100);
                    params.addRule(RelativeLayout.CENTER_IN_PARENT);
                    layout.addView(progressBar,params);
                    setContentView(layout);
                    //============================ UAT Data Coctact ============================
                    if (nama.getText().toString().length() == 0) {
                        String message = "Nama Contact Harus Diisi";
                        fungsi.showSnackbar(v, message);
                    } else if (namaLast.getText().toString().length() == 0) {
                        String message = "Nama Belakang Contact Harus Diisi";
                        fungsi.showSnackbar(v, message);
                    } else if (emailCon.getText().toString().length() == 0) {
                        String message = "Email Contact Harus Diisi";
                        fungsi.showSnackbar(v, message);
                    } else if (nohp.getText().toString().length() == 0) {
                        String message = "No HP Contact Harus Diisi";
                        fungsi.showSnackbar(v, message);
                    } else if (!tvDetPenumpang.getText().toString().equals("Data Penumpang OK!")) {
                        String message = "Data penumpang wajib diisi";
                        fungsi.showSnackbar(v, message);
                    } else {
                        uid = fungsi.getDeviceUniqueID(DetailPemesanan.this);
                        token = bundle.getString("token");
                        title_con = spinTitle.getSelectedItem().toString();
                        first_nm = nama.getText().toString();
                        last_nm = namaLast.getText().toString();
                        email = emailCon.getText().toString();
                        no_tlp = nohp.getText().toString();

                        adult = Integer.toString(preferences.getInt("jumDewasa", 0));
                        child = Integer.toString(preferences.getInt("jumChild", 0));
                        infant = Integer.toString(preferences.getInt("jumInfant", 0));
                        try {
                            data.put("token", token);
                            data.put("flight_id", bundle.getString("flight_id"));
                            data.put("flight_code", bundle.getString("flight_number"));
                            data.put("departure_date", bundle.getString("departureDate"));
                            data.put("airlines_name", bundle.getString("airlines_name"));
                            data.put("adult", adult);
                            data.put("child", child);
                            data.put("infant", infant);
                            data.put("return_date", bundle.getString("ReturnDate"));
                            data.put("uid", uid);
                            data.put("title_con", title_con);
                            data.put("first_nm", first_nm);
                            data.put("last_nm", last_nm);
                            data.put("email", email);
                            data.put("phone", no_tlp);
                            data.put("jenis_penerbangan", "domestik");
                            data.put("Content-Type", "application/x-www-form-urlencoded");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //=================== Declare var data Dewasa ===================
                        String[] ttl, nmd, nmb, tgld, nktp, nat, bag, idpass, passexdt, passiscount, tglp;
                        ttl = dpds.getTitle_pass();
                        nmd = dpds.getFirst_nm();
                        nmb = dpds.getLast_nm();
                        tgld = dpds.getTgl_lahir();
                        //nktp = dpds.getNo_ktp();
                        nat = dpds.getPassportNationality();
                        bag = dpds.getdCheckinBaggage();
                        idpass = dpds.getId_passport();
                        passexdt = dpds.getExpPassportDate();
                        passiscount = dpds.getPassportIssuing();
                        tglp = dpds.getPassportIssuedDate();

                        JSONArray dataArray = new JSONArray();
                        JSONObject job = new JSONObject();
                        for (int i = 0; i < jd; i++) {
                            try {
                                job.put("title_pass", ttl[i]);
                                job.put("first_nm", nmd[i]);
                                job.put("last_nm", nmb[i]);
                                job.put("passportno", idpass[i]);
                                job.put("passportExpiryDate", passexdt[i]);
                                job.put("passportissueddate", tglp[i]);
                                job.put("passportissuing", passiscount[i]);
                                job.put("passportnationality", nat[i]);
                                job.put("dcheckinbaggage", bag[i]);
                                job.put("tgl_lahir", tgld[i]);
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

                        //=================== Put data anak ===================
                        JSONObject objectChild = new JSONObject();
                        JSONArray dataArrayChild = new JSONArray();
                        String[] ttlChild, namaDpnChild, namaBlkgChild, tglLhrChild, natChild, bagChild;
                        ttlChild = dpcds.getTitelChild();
                        namaDpnChild = dpcds.getNamaChild();
                        namaBlkgChild = dpcds.getNamaBlkgChild();
                        tglLhrChild = dpcds.getTglLahirChild();
                        natChild = dpcds.getPassportnationality();
                        bagChild = dpcds.getBagage();

                        if (jc > 0) {
                            for (int i = 0; i < namaDpnChild.length; i++) {
                                try {
                                    objectChild.put("title_pass",ttlChild[i]);
                                    objectChild.put("first_nm", namaDpnChild[i]);
                                    objectChild.put("last_nm", namaBlkgChild[i]);
                                    objectChild.put("tgl_lahir",tglLhrChild[i]);
                                    objectChild.put("passportnationality", natChild[i]);
                                    objectChild.put("dcheckinbaggage", bagChild[i]);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        if (jc > 0) {
                            dataArrayChild.put(objectChild);
                            try {
                                data.put("PenumpangAnak", dataArrayChild);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        //=========================== Put data bayi ===============================
                        JSONObject objectInfant = new JSONObject();
                        JSONArray dataArrayInfant = new JSONArray();
                        String[] ttlInfant, namaDpnInfant, namaBlkgInfant, tglLhrInfant, natInfant;
                        ttlInfant = dpids.getTitelInfant();
                        namaDpnInfant = dpids.getNamaInfant();
                        namaBlkgInfant = dpids.getNama_blkgInfant();
                        tglLhrInfant = dpids.getTglLahirInfant();
                        natInfant = dpids.getPassportnationality();
                        if (ji > 0) {
                            for (int i = 0; i < namaDpnInfant.length; i++) {
                                try {
                                    objectInfant.put("title_pass", ttlInfant[i]);
                                    objectInfant.put("first_nm", namaDpnInfant[i]);
                                    objectInfant.put("last_nm", namaBlkgInfant[i]);
                                    objectInfant.put("tgl_lahir", tglLhrInfant[i]);
                                    objectInfant.put("passportnationality", natInfant[i]);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        if (ji > 0) {
                            dataArrayInfant.put(objectInfant);
                            try {
                                data.put("PenumpangBayi", dataArrayInfant);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        pDialog = new ProgressDialog(DetailPemesanan.this);
                        pDialog.setCancelable(false);
                        pDialog.setMessage("Loading...");
                        showDialog();

                        SpannableStringBuilder respon = new SpannableStringBuilder(data.toString());
                        respon.setSpan(new RelativeSizeSpan(1.35f), 0, data.toString().length(), 0);
                        //Toast.makeText(getApplication(), data.toString(), Toast.LENGTH_LONG).show();
                        vor = new VolleyObjectResult() {
                            String uri, message;
                            @Override
                            public void resSuccess(String requestType, JSONObject response) {
                                hideDialog();
                                //Toast.makeText(getApplication(), response.toString(), Toast.LENGTH_LONG).show();
                                String lempar = getMessageFromServer(response);
                                String error = getErrorMessage(response);
                                uri = lempar;
                                message = error;
                                if (error.length() > 0) {
                                    //Toast.makeText(getApplication(), message, Toast.LENGTH_LONG).show();
                                    fungsi.showSnackbar(v, message);
                                } else {
                                    //====SQL Lite
                                    DatabaseHandler databaseHandler=new DatabaseHandler(DetailPemesanan.this);
                                    SQLiteDatabase db=databaseHandler.getReadableDatabase();
                                    Cursor cursor=db.query("tbl_akun", new String[]{"id","akun_username","akun_email"},
                                            null, null, null, null, null);
                                    //Log.d("tes","Cursor Count : " + cursor.getCount());

                                    if(cursor.getCount()>0){
                                        temp_akun tempAkun=databaseHandler.findOne(1);
                                        databaseHandler.delete(new temp_akun(null, null));
                                        databaseHandler.save(new temp_akun(first_nm, email));
                                    }else {
                                        databaseHandler.save(new temp_akun(first_nm, email));
                                    }

                                    Intent intent = new Intent(DetailPemesanan.this, OpenBrowser.class);
                                    intent.putExtra("uri", uri);
                                    startActivity(intent);
                                }
                            }
                            @Override
                            public void resError(String requestType, VolleyError error) {
                                hideDialog();
                                Toast.makeText(getApplication(), "Proses gagal!", Toast.LENGTH_LONG).show();
                            }
                        };
                        vos = new VolleyObjectService(vor, DetailPemesanan.this);
                        vos.postJsonObject("POSTCALL", url, data);
                    }
                }
            });
//=============================================== Else If Return Flight ===================================================
        } else if (preferences.getInt("returnDate", 0) == 1) {
            final SharedPreferences prefReturn = getApplicationContext().getSharedPreferences("dataFlight", 0);
            final Bundle extras = getIntent().getExtras();

            pswbrgkt.setText(prefReturn.getString("departure_city_name", null));
            pswtjn.setText(prefReturn.getString("arrival_city_name", null));
            imgpp.setImageResource(R.drawable.oneway);
            berangkat.setText(prefReturn.getString("departure_flight_date_str", null));
            Glide.with(DetailPemesanan.this).load(prefReturn.getString("image", null)).asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.mipmap.ic_launcher)
                    .into(imgberangkat);
            namapesawatberangkat.setText(prefReturn.getString("airlines_name", null));
            kodeberangkat.setText(prefReturn.getString("flight_number", null));
            jamberangkat.setText(prefReturn.getString("simple_departure_time", null)+" - "+prefReturn.getString("simple_arrival_time", null));
            hargabrgkt.setText(fungsi.formatIntRupiah(prefReturn.getString("price_value", null)));
            ruteberangkat.setText(prefReturn.getString("full_via", null));

            //==================== Set values ke components flight kembali =====================
            pulang.setText(extras.getString("departure_flight_date_str"));
            Glide.with(DetailPemesanan.this).load(extras.getString("image")).asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.mipmap.ic_launcher)
                    .into(imgpulang);
            namapesawatpulang.setText(extras.getString("airlines_real_name"));
            kodepulang.setText(extras.getString("flight_number"));
            jampulang.setText(extras.getString("simple_departure_time")+" - "+extras.getString("simple_arrival_time"));
            hargapulang.setText(fungsi.formatIntRupiah(extras.getString("price_value")));
            rutepulang.setText(extras.getString("full_via"));

            //================== Set values ke components rincian harga ===================
            int totalPenumpang = preferences.getInt("jumDewasa", 0)+preferences.getInt("jumChild", 0)+preferences.getInt("jumInfant", 0);
            pesawatberangkat.setText(prefReturn.getString("airlines_name", null)+" ("+totalPenumpang+" tiket)");
            hargapesawatberangkat.setText(fungsi.formatIntRupiah(prefReturn.getString("price_value", null)));
            pesawatujuan.setText(extras.getString("airlines_name")+" ("+totalPenumpang+" tiket)");
            hargapesawatujuan.setText(fungsi.formatIntRupiah(extras.getString("price_value")));
            double totalHarga = Double.parseDouble(prefReturn.getString("price_value", null)) + Double.parseDouble(extras.getString("price_value"));
            String totHarga = String.valueOf(totalHarga);
            hargatotal.setText(fungsi.formatIntRupiah(totHarga));
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(DetailPemesanan.this, android.R.layout.simple_spinner_dropdown_item, title);
            spinTitle.setAdapter(arrayAdapter);
            detailPenumpang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
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
                    final LinearLayout[] layoutDewasa = new LinearLayout[jd];
                    TextView[] tvDewasa = new TextView[jd];
                    TextView[] tvTambahBagasi = new TextView[jd];
                    LinearLayout[]  layoutWrap = new LinearLayout[jd];
                    LinearLayout[]  layoutWrap2 = new LinearLayout[jd];
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
                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(DetailPemesanan.this, android.R.layout.simple_spinner_dropdown_item, titleDetPen);
                        spin[i].setAdapter(arrayAdapter);
                        etNamaDewasa[i] = new EditText(DetailPemesanan.this);
                        etNamaDewasa[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        etNamaDewasa[i].setHintTextColor(getResources().getColor(R.color.graytext));
                        etNamaDewasa[i].setHint(Html.fromHtml("<small>"+"Nama depan"+"</small>"));
                        etNamaDewasa[i].setInputType(InputType.TYPE_CLASS_TEXT);
                        etLastName[i] = new EditText(DetailPemesanan.this);
                        etLastName[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        etLastName[i].setHintTextColor(getResources().getColor(R.color.graytext));
                        etLastName[i].setHint(Html.fromHtml("<small>"+"Nama belakang"+"</small>"));
                        etLastName[i].setInputType(InputType.TYPE_CLASS_TEXT);
                        tglLahirDewasa[i] = new EditText(DetailPemesanan.this);
                        tglLahirDewasa[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        tglLahirDewasa[i].setHintTextColor(getResources().getColor(R.color.graytext));
                        tglLahirDewasa[i].setHint(Html.fromHtml("<small>"+"Tanggal lahir"+"</small>"));
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
                                        String kalender = String.valueOf(year)+"-"+mth+"-"+date;
                                        tglLahirDewasa[x].setText(kalender);
                                    }
                                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
                                dpDewasa.show();
                            }
                        });
                        etNoKtp[i] = new EditText(DetailPemesanan.this);
                        etNoKtp[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        etNoKtp[i].setHintTextColor(getResources().getColor(R.color.graytext));
                        etNoKtp[i].setHint(Html.fromHtml("<small>"+"No KTP/SIM/Kartu Pelajar"+"</small>"));
                        etNoKtp[i].setInputType(InputType.TYPE_CLASS_NUMBER);
                        etNation[i] = new EditText(DetailPemesanan.this);
                        etNation[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        etNation[i].setHintTextColor(getResources().getColor(R.color.graytext));
                        etNation[i].setHint(Html.fromHtml("<small>"+"Kebangsaan"+"</small>"));
                        etNation[i].setFocusableInTouchMode(false);
                        etNation[i].setFocusable(false);
                        etNation[i].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                list.clear();
                                final AlertDialog builder = new AlertDialog.Builder(DetailPemesanan.this, R.style.DialogTheme).create();
                                final LayoutInflater inflater = DetailPemesanan.this.getLayoutInflater();
                                final View view = inflater.inflate(R.layout.list_negara, null, false);
                                final EditText cariNegara = (EditText) view.findViewById(R.id.cariNegara);
                                final ListView listNegara = (ListView) view.findViewById(R.id.listNegara);
                                final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.proBar);
                                builder.setView(view);
                                volleyObjectResult = new VolleyObjectResult() {
                                    @Override
                                    public void resSuccess(String requestType, JSONObject response) {
                                        progressBar.setVisibility(View.GONE);
                                        try {
                                            JSONArray array = response.getJSONArray("negara");
                                            for (int i=0; i<array.length(); i++){
                                                JSONObject object = array.getJSONObject(i);
                                                NeagaraDataSet bds = new NeagaraDataSet();
                                                bds.setNegara_id(object.getString("negara_id"));
                                                bds.setNegara_nama_1(object.getString("negara_nama_1"));
                                                bds.setNegara_kode_alpha_2(object.getString("negara_kode_alpha_2"));
                                                list.add(bds);
                                            }
                                            negaraAdapter = new NegaraAdapter(DetailPemesanan.this, list);
                                            negaraAdapter.notifyDataSetChanged();
                                            listNegara.setAdapter(negaraAdapter);
                                            //========================= Action Listener EditText Cari Negara =====================
                                            cariNegara.addTextChangedListener(new TextWatcher() {
                                                @Override
                                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                                }
                                                @Override
                                                public void onTextChanged(CharSequence s, int start, int before, int count) {

                                                }
                                                @Override
                                                public void afterTextChanged(Editable s) {
                                                    String text = cariNegara.getText().toString().toLowerCase(Locale.getDefault());
                                                    negaraAdapter.filter(text);
                                                }
                                            });
                                        } catch (Exception e){
                                            e.printStackTrace();
                                        }
                                    }
                                    @Override
                                    public void resError(String requestType, VolleyError error) {
                                        Toast.makeText(getApplicationContext(), "Pencarian gagal", Toast.LENGTH_LONG).show();
                                        builder.dismiss();
                                    }
                                };
                                volleyObjectService = new VolleyObjectService(volleyObjectResult, DetailPemesanan.this);
                                volleyObjectService.getJsonObject("GETCALL", urlNegara);
                                progressBar.setVisibility(View.VISIBLE);
                                builder.show();
                                listNegara.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        String negaranama = list.get(position).getNegara_nama_1();
                                        String kodeNegara = list.get(position).getNegara_kode_alpha_2();
                                        etNation[x].setText(negaranama+" ("+kodeNegara+")");
                                        builder.dismiss();
                                    }
                                });
                            }
                        });
                        //========================== Layout tambah bagasi =========================
                        layoutWrap2[i] = new LinearLayout(DetailPemesanan.this);
                        layoutWrap2[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        layoutWrap2[i].setOrientation(LinearLayout.HORIZONTAL);
                        etBagasi[i] = new EditText(DetailPemesanan.this);
                        etBagasi[i].setLayoutParams(new ViewGroup.LayoutParams(200, ViewGroup.LayoutParams.WRAP_CONTENT));
                        etBagasi[i].setHintTextColor(getResources().getColor(R.color.graytext));
                        etBagasi[i].setHint(Html.fromHtml("<small>"+"Bagasi tambahan"+"</small>"));
                        etBagasi[i].setInputType(InputType.TYPE_CLASS_NUMBER);
                        etBagasi[i].setGravity(Gravity.RIGHT);
                        etBagasi[i].setText("0");
                        tvTambahBagasi[i] = new TextView(DetailPemesanan.this);
                        tvTambahBagasi[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        tvTambahBagasi[i].setText(" kg");

                        int maxLength = 1;
                        InputFilter[] fArray = new InputFilter[1];
                        fArray[0] = new InputFilter.LengthFilter(maxLength);
                        etBagasi[i].setFilters(fArray);


                        idPassport[i] = new EditText(DetailPemesanan.this);
                        idPassport[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        idPassport[i].setHintTextColor(getResources().getColor(R.color.graytext));
                        idPassport[i].setHint(Html.fromHtml("<small>"+"No passport"+"</small>"));
                        idPassport[i].setInputType(InputType.TYPE_CLASS_TEXT);
                        etPassportExpDate[i] = new EditText(DetailPemesanan.this);
                        etPassportExpDate[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        etPassportExpDate[i].setHintTextColor(getResources().getColor(R.color.graytext));
                        etPassportExpDate[i].setHint(Html.fromHtml("<small>"+"Expire date passport"+"</small>"));
                        etPassportExpDate[i].setFocusableInTouchMode(false);
                        etPassportExpDate[i].setFocusable(false);
                        etPassportExpDate[i].setOnClickListener(new View.OnClickListener() {
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
                                        String kalender = String.valueOf(year)+"-"+mth+"-"+date;
                                        etPassportExpDate[x].setText(kalender);
                                    }
                                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
                                dpDewasa.show();
                            }
                        });
                        etPassIssueCountry[i] = new EditText(DetailPemesanan.this);
                        etPassIssueCountry[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        etPassIssueCountry[i].setHintTextColor(getResources().getColor(R.color.graytext));
                        etPassIssueCountry[i].setHint(Html.fromHtml("<small>"+"Passport issued country"+"</small>"));
                        etPassIssueCountry[i].setFocusableInTouchMode(false);
                        etPassIssueCountry[i].setFocusable(false);
                        etPassIssueCountry[i].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                list.clear();
                                final AlertDialog builder = new AlertDialog.Builder(DetailPemesanan.this, R.style.DialogTheme).create();
                                final LayoutInflater inflater = DetailPemesanan.this.getLayoutInflater();
                                final View view = inflater.inflate(R.layout.list_negara, null, false);
                                final EditText cariNegara = (EditText) view.findViewById(R.id.cariNegara);
                                final ListView listNegara = (ListView) view.findViewById(R.id.listNegara);
                                final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.proBar);
                                builder.setView(view);
                                volleyObjectResult = new VolleyObjectResult() {
                                    @Override
                                    public void resSuccess(String requestType, JSONObject response) {
                                        progressBar.setVisibility(View.GONE);
                                        try {
                                            JSONArray array = response.getJSONArray("negara");
                                            for (int i=0; i<array.length(); i++){
                                                JSONObject object = array.getJSONObject(i);
                                                NeagaraDataSet bds = new NeagaraDataSet();
                                                bds.setNegara_id(object.getString("negara_id"));
                                                bds.setNegara_nama_1(object.getString("negara_nama_1"));
                                                bds.setNegara_kode_alpha_2(object.getString("negara_kode_alpha_2"));
                                                list.add(bds);
                                            }
                                            negaraAdapter = new NegaraAdapter(DetailPemesanan.this, list);
                                            negaraAdapter.notifyDataSetChanged();
                                            listNegara.setAdapter(negaraAdapter);
                                            //========================= Action Listener EditText Cari Negara =====================
                                            cariNegara.addTextChangedListener(new TextWatcher() {
                                                @Override
                                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                                }
                                                @Override
                                                public void onTextChanged(CharSequence s, int start, int before, int count) {

                                                }
                                                @Override
                                                public void afterTextChanged(Editable s) {
                                                    String text = cariNegara.getText().toString().toLowerCase(Locale.getDefault());
                                                    negaraAdapter.filter(text);
                                                }
                                            });
                                        } catch (Exception e){
                                            e.printStackTrace();
                                        }
                                    }
                                    @Override
                                    public void resError(String requestType, VolleyError error) {
                                        Toast.makeText(getApplicationContext(), "Pencarian gagal", Toast.LENGTH_LONG).show();
                                        builder.dismiss();
                                    }
                                };
                                volleyObjectService = new VolleyObjectService(volleyObjectResult, DetailPemesanan.this);
                                volleyObjectService.getJsonObject("GETCALL", urlNegara);
                                progressBar.setVisibility(View.VISIBLE);
                                builder.show();
                                listNegara.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        String negaranama = list.get(position).getNegara_nama_1();
                                        String kodeNegara = list.get(position).getNegara_kode_alpha_2();
                                        etPassIssueCountry[x].setText(negaranama+" ("+kodeNegara+")");
                                        builder.dismiss();
                                    }
                                });
                            }
                        });
                        etTanggalPenerbit[i] = new EditText(DetailPemesanan.this);
                        etTanggalPenerbit[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        etTanggalPenerbit[i].setHintTextColor(getResources().getColor(R.color.graytext));
                        etTanggalPenerbit[i].setHint(Html.fromHtml("<small>"+"Tanggal penerbitan pasport"+"</small>"));
                        etTanggalPenerbit[i].setFocusableInTouchMode(false);
                        etTanggalPenerbit[i].setFocusable(false);
                        etTanggalPenerbit[i].setOnClickListener(new View.OnClickListener() {
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
                                        String kalender = String.valueOf(year)+"-"+mth+"-"+date;
                                        etTanggalPenerbit[x].setText(kalender);
                                    }
                                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
                                dpDewasa.show();
                            }
                        });
                        //================== Add components ke dalam layout ==================
                        layoutDewasa[i].addView(tvDewasa[i]);
                        layoutWrap[i].addView(spin[i]); layoutWrap[i].addView(etNamaDewasa[i]);
                        layoutDewasa[i].addView(layoutWrap[i]);
                        layoutDewasa[i].addView(etLastName[i]);
                        layoutDewasa[i].addView(tglLahirDewasa[i]);
                        //layoutDewasa[i].addView(etNoKtp[i]);
                        if (extras.getString("airlines_name").equals("AIRASIA") || prefReturn.getString("airlines_name", null).equals("AIRASIA") ||
                                extras.getString("airlines_name").equals("CITILINK") || prefReturn.getString("airlines_name", null).equals("CITILINK") ||
                                extras.getString("airlines_name").equals("TIGER") || prefReturn.getString("airlines_name", null).equals("TIGER") ||
                                extras.getString("airlines_name").equals("MANDALA") || prefReturn.getString("airlines_name", null).equals("MANDALA")) {
                            layoutDewasa[i].addView(etNation[i]);
                        }
                        layoutWrap2[i].addView(etBagasi[i]); layoutWrap2[i].addView(tvTambahBagasi[i]);
                        if (extras.getString("airlines_name").equals("AIRASIA") || prefReturn.getString("airlines_name", null).equals("AIRASIA") ||
                                extras.getString("airlines_name").equals("TIGER") || prefReturn.getString("airlines_name", null).equals("TIGER") ||
                                extras.getString("airlines_name").equals("MANDALA") || prefReturn.getString("airlines_name", null).equals("MANDALA")) {
                            layoutDewasa[i].addView(layoutWrap2[i]);
                        }
                        if (extras.getString("domestik").equals("0") || prefReturn.getString("domestik", null).equals("0")) {
                            layoutDewasa[i].addView(idPassport[i]);
                            layoutDewasa[i].addView(etPassportExpDate[i]);
                            layoutDewasa[i].addView(etPassIssueCountry[i]);
                            layoutDewasa[i].addView(etTanggalPenerbit[i]);
                        }
                        mainLayout.addView(layoutDewasa[i]);
                    }
                    //===================== Linear Layout Child ========================
                    if (jc > 0) {
                        LinearLayout[] layoutChild = new LinearLayout[jc];
                        LinearLayout[] layoutWrapChild = new LinearLayout[jc];
                        LinearLayout[] layoutWrapBagasiChild = new LinearLayout[jc];
                        TextView[] tvChild = new TextView[jc];
                        TextView[] tvTambahBagasiChild = new TextView[jc];
                        for (int i=0; i<layoutChild.length; i++) {
                            final int x = i;
                            layoutChild[i] = new LinearLayout(DetailPemesanan.this);
                            layoutChild[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            layoutChild[i].setOrientation(LinearLayout.VERTICAL);
                            layoutChild[i].setPadding(6, 6, 6, 6);
                            tvChild[i] = new TextView(DetailPemesanan.this);
                            tvChild[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            tvChild[i].setText("Child "+(i+1));
                            tvChild[i].setTypeface(null, Typeface.BOLD);
                            layoutWrapChild[i] = new LinearLayout(DetailPemesanan.this);
                            layoutWrapChild[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            layoutWrapChild[i].setOrientation(LinearLayout.HORIZONTAL);
                            spinChild[i] = new Spinner(DetailPemesanan.this);
                            spinChild[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(DetailPemesanan.this, android.R.layout.simple_spinner_dropdown_item, titelChild);
                            spinChild[i].setAdapter(arrayAdapter);
                            etNamaChild[i] = new EditText(DetailPemesanan.this);
                            etNamaChild[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            etNamaChild[i].setHintTextColor(getResources().getColor(R.color.graytext));
                            etNamaChild[i].setHint(Html.fromHtml("<small>"+"Nama depan"+"</small>"));
                            etNamaBlkgChild[i] = new EditText(DetailPemesanan.this);
                            etNamaBlkgChild[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            etNamaBlkgChild[i].setHintTextColor(getResources().getColor(R.color.graytext));
                            etNamaBlkgChild[i].setHint(Html.fromHtml("<small>"+"Nama belakang"+"</small>"));
                            etTglLahirChild[i] = new EditText(DetailPemesanan.this);
                            etTglLahirChild[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            etTglLahirChild[i].setHintTextColor(getResources().getColor(R.color.graytext));
                            etTglLahirChild[i].setHint(Html.fromHtml("<small>"+"Tanggal lahir"+"</small>"));
                            etTglLahirChild[i].setFocusableInTouchMode(false);
                            etTglLahirChild[i].setFocusable(false);
                            etTglLahirChild[i].setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    final Calendar calendar = Calendar.getInstance();
                                    DatePickerDialog dpChild = new DatePickerDialog(DetailPemesanan.this, new DatePickerDialog.OnDateSetListener() {
                                        @Override
                                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                            String date, mth;
                                            if (month < 10) mth = "0"+(month+1);
                                            else mth = String.valueOf(month+1);
                                            if (dayOfMonth < 10) date = "0"+dayOfMonth;
                                            else date = String.valueOf(dayOfMonth);
                                            String kalender = String.valueOf(year)+"-"+mth+"-"+date;
                                            etTglLahirChild[x].setText(kalender);
                                        }
                                    }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
                                    dpChild.show();
                                }
                            });
                            etNationChild[i] = new EditText(DetailPemesanan.this);
                            etNationChild[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            etNationChild[i].setHintTextColor(getResources().getColor(R.color.graytext));
                            etNationChild[i].setHint(Html.fromHtml("<small>"+"Kebangsaan"+"</small>"));
                            etNationChild[i].setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    list.clear();
                                    final AlertDialog builder = new AlertDialog.Builder(DetailPemesanan.this, R.style.DialogTheme).create();
                                    final LayoutInflater inflater = DetailPemesanan.this.getLayoutInflater();
                                    final View view = inflater.inflate(R.layout.list_negara, null, false);
                                    final EditText cariNegara = (EditText) view.findViewById(R.id.cariNegara);
                                    final ListView listNegara = (ListView) view.findViewById(R.id.listNegara);
                                    final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.proBar);
                                    builder.setView(view);
                                    volleyObjectResult = new VolleyObjectResult() {
                                        @Override
                                        public void resSuccess(String requestType, JSONObject response) {
                                            progressBar.setVisibility(View.GONE);
                                            try {
                                                JSONArray array = response.getJSONArray("negara");
                                                for (int i=0; i<array.length(); i++){
                                                    JSONObject object = array.getJSONObject(i);
                                                    NeagaraDataSet bds = new NeagaraDataSet();
                                                    bds.setNegara_id(object.getString("negara_id"));
                                                    bds.setNegara_nama_1(object.getString("negara_nama_1"));
                                                    bds.setNegara_kode_alpha_2(object.getString("negara_kode_alpha_2"));
                                                    list.add(bds);
                                                }
                                                negaraAdapter = new NegaraAdapter(DetailPemesanan.this, list);
                                                negaraAdapter.notifyDataSetChanged();
                                                listNegara.setAdapter(negaraAdapter);
                                                //========================= Action Listener EditText Cari Negara =====================
                                                cariNegara.addTextChangedListener(new TextWatcher() {
                                                    @Override
                                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                                    }
                                                    @Override
                                                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                                                    }
                                                    @Override
                                                    public void afterTextChanged(Editable s) {
                                                        String text = cariNegara.getText().toString().toLowerCase(Locale.getDefault());
                                                        negaraAdapter.filter(text);
                                                    }
                                                });
                                            } catch (Exception e){
                                                e.printStackTrace();
                                            }
                                        }
                                        @Override
                                        public void resError(String requestType, VolleyError error) {
                                            Toast.makeText(getApplicationContext(), "Pencarian gagal", Toast.LENGTH_LONG).show();
                                            builder.dismiss();
                                        }
                                    };
                                    volleyObjectService = new VolleyObjectService(volleyObjectResult, DetailPemesanan.this);
                                    volleyObjectService.getJsonObject("GETCALL", urlNegara);
                                    progressBar.setVisibility(View.VISIBLE);
                                    builder.show();
                                    listNegara.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            String negaranama = list.get(position).getNegara_nama_1();
                                            String kodeNegara = list.get(position).getNegara_kode_alpha_2();
                                            etNationChild[x].setText(negaranama+" ("+kodeNegara+")");
                                            builder.dismiss();
                                        }
                                    });
                                }
                            });
                            layoutWrapBagasiChild[i] = new LinearLayout(DetailPemesanan.this);
                            layoutWrapBagasiChild[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            layoutWrapBagasiChild[i].setOrientation(LinearLayout.HORIZONTAL);
                            etBagasiChild[i] = new EditText(DetailPemesanan.this);
                            etBagasiChild[i].setLayoutParams(new ViewGroup.LayoutParams(200, ViewGroup.LayoutParams.WRAP_CONTENT));
                            etBagasiChild[i].setHintTextColor(getResources().getColor(R.color.graytext));
                            etBagasiChild[i].setHint(Html.fromHtml("<small>"+"Bagasi tambahan"+"</small>"));
                            etBagasiChild[i].setGravity(Gravity.RIGHT);
                            etBagasiChild[i].setText("0");
                            tvTambahBagasiChild[i] = new TextView(DetailPemesanan.this);
                            tvTambahBagasiChild[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            tvTambahBagasiChild[i].setText(" kg");

                            int maxLength2 = 1;
                            InputFilter[] fArray = new InputFilter[1];
                            fArray[0] = new InputFilter.LengthFilter(maxLength2);
                            etBagasiChild[i].setFilters(fArray);

                            //================== Add components ke dalam layout ==================
                            layoutChild[i].addView(tvChild[i]);
                            layoutWrapChild[i].addView(spinChild[i]); layoutWrapChild[i].addView(etNamaChild[i]);
                            layoutChild[i].addView(layoutWrapChild[i]);
                            layoutChild[i].addView(etNamaBlkgChild[i]);
                            layoutChild[i].addView(etTglLahirChild[i]);
                            layoutWrapBagasiChild[i].addView(etBagasiChild[i]); layoutWrapBagasiChild[i].addView(tvTambahBagasiChild[i]);
                            if (extras.getString("airlines_name").equals("AIRASIA") ||
                                    extras.getString("airlines_name").equals("CITILINK") ||
                                    extras.getString("airlines_name").equals("TIGER") ||
                                    extras.getString("airlines_name").equals("MANDALA")) {
                                layoutChild[i].addView(etNationChild[i]);
                                layoutChild[i].addView(layoutWrapBagasiChild[i]);
                            }
                            mainLayout.addView(layoutChild[i]);
                        }
                    }
                    //===================== Linear Layout Infant ========================
                    if (ji > 0) {
                        LinearLayout[] layoutInfant = new LinearLayout[ji];
                        TextView[] tvInfant = new TextView[ji];
                        LinearLayout[] layoutWrapInfant = new LinearLayout[ji];
                        for (int i=0; i<layoutInfant.length; i++) {
                            final int x = i;
                            layoutInfant[i] = new LinearLayout(DetailPemesanan.this);
                            layoutInfant[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            layoutInfant[i].setOrientation(LinearLayout.VERTICAL);
                            layoutInfant[i].setPadding(6, 6, 6, 6);
                            tvInfant[i] = new TextView(DetailPemesanan.this);
                            tvInfant[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            tvInfant[i].setText("Infant "+(i+1));
                            tvInfant[i].setTypeface(null, Typeface.BOLD);
                            layoutWrapInfant[i] = new LinearLayout(DetailPemesanan.this);
                            layoutWrapInfant[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            layoutWrapInfant[i].setOrientation(LinearLayout.HORIZONTAL);
                            spinInfant[i] = new Spinner(DetailPemesanan.this);
                            spinInfant[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(DetailPemesanan.this, android.R.layout.simple_spinner_dropdown_item, titelInfant);
                            spinInfant[i].setAdapter(arrayAdapter);
                            etNamaInfant[i] = new EditText(DetailPemesanan.this);
                            etNamaInfant[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            etNamaInfant[i].setHintTextColor(getResources().getColor(R.color.graytext));
                            etNamaInfant[i].setHint(Html.fromHtml("<small>"+"Nama depan"+"</small>"));
                            etNamaBlkgInfant[i] = new EditText(DetailPemesanan.this);
                            etNamaBlkgInfant[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            etNamaBlkgInfant[i].setHintTextColor(getResources().getColor(R.color.graytext));
                            etNamaBlkgInfant[i].setHint(Html.fromHtml("<small>"+"Nama belakang"+"</small>"));
                            etTglLahirInfant[i] = new EditText(DetailPemesanan.this);
                            etTglLahirInfant[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            etTglLahirInfant[i].setHintTextColor(getResources().getColor(R.color.graytext));
                            etTglLahirInfant[i].setHint(Html.fromHtml("<small>"+"Tanggal lahir"+"</small>"));
                            etTglLahirInfant[i].setFocusableInTouchMode(false);
                            etTglLahirInfant[i].setFocusable(false);
                            etTglLahirInfant[i].setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    final Calendar calendar = Calendar.getInstance();
                                    DatePickerDialog dpInfant = new DatePickerDialog(DetailPemesanan.this, new DatePickerDialog.OnDateSetListener() {
                                        @Override
                                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                            String date, mth;
                                            if (month < 10) mth = "0"+(month+1);
                                            else mth = String.valueOf(month+1);
                                            if (dayOfMonth < 10) date = "0"+dayOfMonth;
                                            else date = String.valueOf(dayOfMonth);
                                            String kalender = String.valueOf(year)+"-"+mth+"-"+date;
                                            etTglLahirInfant[x].setText(kalender);
                                        }
                                    }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
                                    dpInfant.show();
                                }
                            });
                            etNationInfant[i] = new EditText(DetailPemesanan.this);
                            etNationInfant[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            etNationInfant[i].setHintTextColor(getResources().getColor(R.color.graytext));
                            etNationInfant[i].setHint(Html.fromHtml("<small>"+"Kebangsaan"+"</small>"));
                            etNationInfant[i].setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    list.clear();
                                    final AlertDialog builder = new AlertDialog.Builder(DetailPemesanan.this, R.style.DialogTheme).create();
                                    final LayoutInflater inflater = DetailPemesanan.this.getLayoutInflater();
                                    final View view = inflater.inflate(R.layout.list_negara, null, false);
                                    final EditText cariNegara = (EditText) view.findViewById(R.id.cariNegara);
                                    final ListView listNegara = (ListView) view.findViewById(R.id.listNegara);
                                    final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.proBar);
                                    builder.setView(view);
                                    volleyObjectResult = new VolleyObjectResult() {
                                        @Override
                                        public void resSuccess(String requestType, JSONObject response) {
                                            progressBar.setVisibility(View.GONE);
                                            try {
                                                JSONArray array = response.getJSONArray("negara");
                                                for (int i=0; i<array.length(); i++){
                                                    JSONObject object = array.getJSONObject(i);
                                                    NeagaraDataSet bds = new NeagaraDataSet();
                                                    bds.setNegara_id(object.getString("negara_id"));
                                                    bds.setNegara_nama_1(object.getString("negara_nama_1"));
                                                    bds.setNegara_kode_alpha_2(object.getString("negara_kode_alpha_2"));
                                                    list.add(bds);
                                                }
                                                negaraAdapter = new NegaraAdapter(DetailPemesanan.this, list);
                                                negaraAdapter.notifyDataSetChanged();
                                                listNegara.setAdapter(negaraAdapter);
                                                //========================= Action Listener EditText Cari Negara =====================
                                                cariNegara.addTextChangedListener(new TextWatcher() {
                                                    @Override
                                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                                    }
                                                    @Override
                                                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                                                    }
                                                    @Override
                                                    public void afterTextChanged(Editable s) {
                                                        String text = cariNegara.getText().toString().toLowerCase(Locale.getDefault());
                                                        negaraAdapter.filter(text);
                                                    }
                                                });
                                            } catch (Exception e){
                                                e.printStackTrace();
                                            }
                                        }
                                        @Override
                                        public void resError(String requestType, VolleyError error) {
                                            Toast.makeText(getApplicationContext(), "Pencarian gagal", Toast.LENGTH_LONG).show();
                                            builder.dismiss();
                                        }
                                    };
                                    volleyObjectService = new VolleyObjectService(volleyObjectResult, DetailPemesanan.this);
                                    volleyObjectService.getJsonObject("GETCALL", urlNegara);
                                    progressBar.setVisibility(View.VISIBLE);
                                    builder.show();
                                    listNegara.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            String negaranama = list.get(position).getNegara_nama_1();
                                            String kodeNegara = list.get(position).getNegara_kode_alpha_2();
                                            etNationInfant[x].setText(negaranama+" ("+kodeNegara+")");
                                            builder.dismiss();
                                        }
                                    });
                                }
                            });

                            //================== Add components ke dalam layout ==================
                            layoutInfant[i].addView(tvInfant[i]);
                            layoutWrapInfant[i].addView(spinInfant[i]); layoutWrapInfant[i].addView(etNamaInfant[i]);
                            layoutInfant[i].addView(layoutWrapInfant[i]);
                            layoutInfant[i].addView(etNamaBlkgInfant[i]);
                            layoutInfant[i].addView(etTglLahirInfant[i]);
                            if (extras.getString("airlines_name").equals("AIRASIA") || prefReturn.getString("airlines_name", null).equals("AIRASIA") ||
                                    extras.getString("airlines_name").equals("CITILINK") || prefReturn.getString("airlines_name", null).equals("CITILINK") ||
                                    extras.getString("airlines_name").equals("TIGER") || prefReturn.getString("airlines_name", null).equals("TIGER") ||
                                    extras.getString("airlines_name").equals("MANDALA") || prefReturn.getString("airlines_name", null).equals("MANDALA")) {
                                layoutInfant[i].addView(etNationInfant[i]);
                            }
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
                                //noKtp[i] = etNoKtp[i].getText().toString();
                                nation[i] = etNation[i].getText().toString();
                                if (nation[i].length() > 0) {
                                    int firstBracket = nation[i].indexOf('(');
                                    content[i] = nation[i].substring(firstBracket + 1, nation[i].indexOf(')', firstBracket));
                                }
                                bagasi[i] = etBagasi[i].getText().toString();
                                id_passport[i] = idPassport[i].getText().toString();
                                passportExpDate[i] = etPassportExpDate[i].getText().toString();
                                passIssueCountry[i] = etPassIssueCountry[i].getText().toString();
                                if (passIssueCountry[i].length() > 0) {
                                    int firstBracket = nation[i].indexOf('(');
                                    cont[i] = nation[i].substring(firstBracket + 1, nation[i].indexOf(')', firstBracket));
                                }
                                tglPenerbit[i] = etTanggalPenerbit[i].getText().toString();

                                dpds.setTitle_pass(titel);
                                dpds.setFirst_nm(nama_dpn);
                                dpds.setLast_nm(nama_blkg);
                                dpds.setTgl_lahir(tgl_lahir);
                                //dpds.setNo_ktp(noKtp);
                                dpds.setPassportNationality(content);
                                dpds.setdCheckinBaggage(bagasi);
                                dpds.setId_passport(id_passport);
                                dpds.setExpPassportDate(passportExpDate);
                                dpds.setPassportIssuing(cont);
                                dpds.setPassportIssuedDate(tglPenerbit);

                                if (nama_dpn[i].length() == 0) {
                                    String message = "Nama Depan Penumpang Harus Diisi";
                                    fungsi.showSnackbar(v, message);
                                } else if (nama_blkg[i].length() == 0) {
                                    String message = "Nama Belakang Penumpang Harus Diisi";
                                    fungsi.showSnackbar(v, message);
                                } else if (tgl_lahir[i].length() == 0) {
                                    String message = "Tanggal Lahir Penumpang Harus Diisi";
                                    fungsi.showSnackbar(v, message);
                                } else {
                                    tvDetPenumpang.setText("Data Penumpang OK!");
                                }
                            }
                            if (jc > 0) {
                                for (int i=0; i<etNamaChild.length; i++) {
                                    titel_child[i] = spinChild[i].getSelectedItem().toString();
                                    nama_dpnChild[i] = etNamaChild[i].getText().toString();
                                    nama_blkgChild[i] = etNamaBlkgChild[i].getText().toString();
                                    tgl_lahirChild[i] = etTglLahirChild[i].getText().toString();
                                    nation_child[i] = etNationChild[i].getText().toString();
                                    if (nation_child[i].length() > 0) {
                                        int firstBracket = nation_child[i].indexOf('(');
                                        content_child[i] = nation_child[i].substring(firstBracket + 1, nation_child[i].indexOf(')', firstBracket));
                                    }
                                    bagasi_child[i] = etBagasiChild[i].getText().toString();

                                    dpcds.setTitelChild(titel_child);
                                    dpcds.setNamaChild(nama_dpnChild);
                                    dpcds.setNamaBlkgChild(nama_blkgChild);
                                    dpcds.setTglLahirChild(tgl_lahirChild);
                                    dpcds.setPassportnationality(content_child);
                                    dpcds.setBagage(bagasi_child);

                                    DateFormat dtf = new SimpleDateFormat("yyyy-MM-dd");
                                    long selisih = 0;
                                    try {
                                        Date dateChild = dtf.parse(tgl_lahirChild[i]);
                                        Date dateNow = new Date();
                                        dateNow = dtf.parse(dtf.format(dateNow));
                                        long dif = dateNow.getTime() - dateChild.getTime();
                                        selisih = TimeUnit.DAYS.convert(dif, TimeUnit.MILLISECONDS);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    //Toast.makeText(DetailPemesanan.this, String.valueOf(selisih), Toast.LENGTH_LONG).show();
                                    if (nama_dpnChild[i].length() == 0) {
                                        String message = "Nama Depan Anak Harus Diisi";
                                        fungsi.showSnackbar(v, message);
                                        tvDetPenumpang.setText("Klik disini untuk input data penumpang!");
                                    } else if (nama_blkgChild[i].length() == 0) {
                                        String message = "Nama Belakang Anak Harus Diisi";
                                        fungsi.showSnackbar(v, message);
                                        tvDetPenumpang.setText("Klik disini untuk input data penumpang!");
                                    } else if (tgl_lahirChild[i].length() == 0) {
                                        String message = "Tanggal Lahir Anak Harus Diisi";
                                        fungsi.showSnackbar(v, message);
                                        tvDetPenumpang.setText("Klik disini untuk input data penumpang!");
                                    } else if(selisih < 730 || selisih > 4015) {
                                        String message = "Umur anak harus 2thn - 11thn";
                                        fungsi.showSnackbar(v, message);
                                        tvDetPenumpang.setText("Klik disini untuk input data penumpang!");
                                    } else {
                                        tvDetPenumpang.setText("Data Penumpang OK!");
                                    }
                                }
                            }
                            if (ji > 0) {
                                for (int i=0; i<etNamaInfant.length; i++) {
                                    titel_infant[i] = spinInfant[i].getSelectedItem().toString();
                                    nama_dpnInfant[i] = etNamaInfant[i].getText().toString();
                                    nama_blkgInfant[i] = etNamaBlkgInfant[i].getText().toString();
                                    tgl_lahirinfant[i] = etTglLahirInfant[i].getText().toString();
                                    nation_infant[i] = etNationInfant[i].getText().toString();
                                    if (nation_infant[i].length() > 0) {
                                        int firstBracket = nation_infant[i].indexOf('(');
                                        content_infant[i] = nation_infant[i].substring(firstBracket + 1, nation_infant[i].indexOf(')', firstBracket));
                                    }

                                    dpids.setTitelInfant(titel_infant);
                                    dpids.setNamaInfant(nama_dpnInfant);
                                    dpids.setNama_blkgInfant(nama_blkgInfant);
                                    dpids.setTglLahirInfant(tgl_lahirinfant);
                                    dpids.setPassportnationality(content_infant);

                                    DateFormat dtf = new SimpleDateFormat("yyyy-MM-dd");
                                    long selisih = 0;
                                    try {
                                        Date dateInfant = dtf.parse(tgl_lahirinfant[i]);
                                        Date dateNow = new Date();
                                        dateNow = dtf.parse(dtf.format(dateNow));
                                        long dif = dateNow.getTime() - dateInfant.getTime();
                                        selisih = TimeUnit.DAYS.convert(dif, TimeUnit.MILLISECONDS);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    if (nama_dpnInfant[i].length() == 0) {
                                        String message = "Nama Depan Bayi Harus Diisi";
                                        fungsi.showSnackbar(v, message);
                                        tvDetPenumpang.setText("Klik disini untuk input data penumpang!");
                                    } else if (nama_blkgInfant[i].length() == 0) {
                                        String message = "Nama Belakang Bayi Harus Diisi";
                                        fungsi.showSnackbar(v, message);
                                        tvDetPenumpang.setText("Klik disini untuk input data penumpang!");
                                    } else if (tgl_lahirinfant[i].length() == 0) {
                                        String message = "Tanggal Lahir Bayi Harus Diisi";
                                        fungsi.showSnackbar(v, message);
                                        tvDetPenumpang.setText("Klik disini untuk input data penumpang!");
                                    } else if(selisih >= 730) {
                                        String message = "Umur bayi harus dibawah 2thn";
                                        fungsi.showSnackbar(v, message);
                                        tvDetPenumpang.setText("Klik disini untuk input data penumpang!");
                                    } else {
                                        tvDetPenumpang.setText("Data Penumpang OK!");
                                    }
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
            submiton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    //============================ UAT Data Coctact ============================
                    if (nama.getText().toString().length() == 0) {
                        String message = "Nama Contact Harus Diisi";
                        fungsi.showSnackbar(v, message);
                    } else if (namaLast.getText().toString().length() == 0) {
                        String message = "Nama Belakang Contact Harus Diisi";
                        fungsi.showSnackbar(v, message);
                    } else if (emailCon.getText().toString().length() == 0) {
                        String message = "Email Contact Harus Diisi";
                        fungsi.showSnackbar(v, message);
                    } else if (nohp.getText().toString().length() == 0) {
                        String message = "No HP Contact Harus Diisi";
                        fungsi.showSnackbar(v, message);
                    } else if (!tvDetPenumpang.getText().toString().equals("Data Penumpang OK!")) {
                        String message = "Data penumpang wajib diisi";
                        fungsi.showSnackbar(v, message);
                    } else {
                        title_con = spinTitle.getSelectedItem().toString();
                        first_nm = nama.getText().toString();
                        last_nm = namaLast.getText().toString();
                        email = emailCon.getText().toString();
                        no_tlp = nohp.getText().toString();

                        adult = Integer.toString(preferences.getInt("jumDewasa", 0));
                        child = Integer.toString(preferences.getInt("jumChild", 0));
                        infant = Integer.toString(preferences.getInt("jumInfant", 0));
                        try {
                            data.put("token", prefReturn.getString("token", null));
                            data.put("flight_id", prefReturn.getString("flight_id", null));
                            data.put("flight_code", prefReturn.getString("flight_number", null));
                            data.put("departure_date", prefReturn.getString("departureDate", null));
                            data.put("airlines_name", prefReturn.getString("airlines_name", null));
                            data.put("flight_id_return", extras.getString("flight_id"));
                            data.put("flight_code_return", extras.getString("flight_number"));
                            data.put("airlines_name_return", extras.getString("airlines_name"));
                            data.put("adult", adult);
                            data.put("child", child);
                            data.put("infant", infant);
                            data.put("return_date", prefReturn.getString("ReturnDate", null));
                            data.put("uid", fungsi.getDeviceUniqueID(DetailPemesanan.this));
                            data.put("title_con", title_con);
                            data.put("first_nm", first_nm);
                            data.put("last_nm", last_nm);
                            data.put("email", email);
                            data.put("phone", no_tlp);
                            data.put("jenis_penerbangan", "domestik");
                            data.put("Content-Type", "application/x-www-form-urlencoded");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //=================== Declare var data Dewasa ===================
                        String[] ttl, nmd, nmb, tgld, nat, bag, idpass, passexdt, passiscount, tglp;
                        ttl = dpds.getTitle_pass();
                        nmd = dpds.getFirst_nm();
                        nmb = dpds.getLast_nm();
                        tgld = dpds.getTgl_lahir();
                        //nktp = dpds.getNo_ktp();
                        nat = dpds.getPassportNationality();
                        bag = dpds.getdCheckinBaggage();
                        idpass = dpds.getId_passport();
                        passexdt = dpds.getExpPassportDate();
                        passiscount = dpds.getPassportIssuing();
                        tglp = dpds.getPassportIssuedDate();

                        JSONArray dataArray = new JSONArray();
                        JSONObject job = new JSONObject();
                        for (int i = 0; i < jd; i++) {
                            try {
                                job.put("title_pass", ttl[i]);
                                job.put("first_nm", nmd[i]);
                                job.put("last_nm", nmb[i]);
                                job.put("passportno", idpass[i]);
                                job.put("passportExpiryDate", passexdt[i]);
                                job.put("passportissueddate", tglp[i]);
                                job.put("passportissuing", passiscount[i]);
                                job.put("passportnationality", nat[i]);
                                job.put("dcheckinbaggage", bag[i]);
                                job.put("tgl_lahir", tgld[i]);
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

                        //=================== Put data anak ===================
                        JSONObject objectChild = new JSONObject();
                        JSONArray dataArrayChild = new JSONArray();
                        String[] ttlChild, namaDpnChild, namaBlkgChild, tglLhrChild, natChild, bagChild;
                        ttlChild = dpcds.getTitelChild();
                        namaDpnChild = dpcds.getNamaChild();
                        namaBlkgChild = dpcds.getNamaBlkgChild();
                        tglLhrChild = dpcds.getTglLahirChild();
                        natChild = dpcds.getPassportnationality();
                        bagChild = dpcds.getBagage();

                        if (jc > 0) {
                            for (int i = 0; i < namaDpnChild.length; i++) {
                                try {
                                    objectChild.put("title_pass",ttlChild[i]);
                                    objectChild.put("first_nm", namaDpnChild[i]);
                                    objectChild.put("last_nm", namaBlkgChild[i]);
                                    objectChild.put("tgl_lahir",tglLhrChild[i]);
                                    objectChild.put("passportnationality", natChild[i]);
                                    objectChild.put("dcheckinbaggage", bagChild[i]);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        if (jc > 0) {
                            dataArrayChild.put(objectChild);
                            try {
                                data.put("PenumpangAnak", dataArrayChild);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        //=========================== Put data bayi ===============================
                        JSONObject objectInfant = new JSONObject();
                        JSONArray dataArrayInfant = new JSONArray();
                        String[] ttlInfant, namaDpnInfant, namaBlkgInfant, tglLhrInfant, natInfant;
                        ttlInfant = dpids.getTitelInfant();
                        namaDpnInfant = dpids.getNamaInfant();
                        namaBlkgInfant = dpids.getNama_blkgInfant();
                        tglLhrInfant = dpids.getTglLahirInfant();
                        natInfant = dpids.getPassportnationality();
                        if (ji > 0) {
                            for (int i = 0; i < namaDpnInfant.length; i++) {
                                try {
                                    objectInfant.put("title_pass", ttlInfant[i]);
                                    objectInfant.put("first_nm", namaDpnInfant[i]);
                                    objectInfant.put("last_nm", namaBlkgInfant[i]);
                                    objectInfant.put("tgl_lahir", tglLhrInfant[i]);
                                    objectInfant.put("passportnationality", natInfant[i]);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        if (ji > 0) {
                            dataArrayInfant.put(objectInfant);
                            try {
                                data.put("PenumpangBayi", dataArrayInfant);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        pDialog = new ProgressDialog(DetailPemesanan.this);
                        pDialog.setCancelable(false);
                        pDialog.setMessage("Loading...");
                        showDialog();

                        //Toast.makeText(getApplication(), data.toString(), Toast.LENGTH_LONG).show();
                        vor = new VolleyObjectResult() {
                            String uri, message;
                            @Override
                            public void resSuccess(String requestType, JSONObject response) {
                                hideDialog();
                                //Toast.makeText(getApplication(), response.toString(), Toast.LENGTH_LONG).show();
                                String lempar = getMessageFromServer(response);
                                String error = getErrorMessagePP(response);
                                uri = lempar;
                                message = error;
                                if (error.length() > 0) {
                                    //Toast.makeText(getApplication(), message, Toast.LENGTH_LONG).show();
                                    fungsi.showSnackbar(v, message);
                                } else {

                                    //====SQL Lite
                                    DatabaseHandler databaseHandler=new DatabaseHandler(DetailPemesanan.this);
                                    SQLiteDatabase db=databaseHandler.getReadableDatabase();
                                    Cursor cursor=db.query("tbl_akun", new String[]{"id","akun_username","akun_email"},
                                            null, null, null, null, null);
                                    //Log.d("tes","Cursor Count : " + cursor.getCount());

                                    if(cursor.getCount()>0){
                                        temp_akun tempAkun=databaseHandler.findOne(1);
                                        databaseHandler.delete(new temp_akun(null, null));
                                        databaseHandler.save(new temp_akun(first_nm, email));
                                    }else {
                                        databaseHandler.save(new temp_akun(first_nm, email));
                                    }


                                    Intent intent = new Intent(DetailPemesanan.this, OpenBrowser.class);
                                    intent.putExtra("uri", uri);
                                    startActivity(intent);
                                }
                            }
                            @Override
                            public void resError(String requestType, VolleyError error) {
                                hideDialog();
                                Toast.makeText(getApplication(), error.toString(), Toast.LENGTH_LONG).show();
                            }
                        };
                        vos = new VolleyObjectService(vor, DetailPemesanan.this);
                        vos.postJsonObject("POSTCALL", urlPP, data);
                    }
                }
            });
        }

    }

    private String getMessageFromServer(JSONObject response) {
        String result = null;
        try {
            result = response.getString("checkout_link");
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }
        return result;
    }
    private String getErrorMessage(JSONObject response) {
        String result = null;
        try {
            result = response.getString("error_msgs");
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }
        return result;
    }
    private String getErrorMessagePP(JSONObject response) {
        String result = null;
        try {
            result = response.getString("error_pulang");
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }
        return result;
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

