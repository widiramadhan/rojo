package com.rojo.travel.tour;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.rojo.travel.R;
import com.rojo.travel.functions.Fungsi;
import com.rojo.travel.functions.MyTagHandler;

import java.text.NumberFormat;
import java.util.Locale;

import org.xml.sax.XMLReader;

import android.text.Editable;
import android.text.Html.TagHandler;

/**
 * Created by kangyasin on 10/11/2017.
 */

public class DetailTour extends AppCompatActivity implements TagHandler, View.OnClickListener {


    Button pilih, pilih1, pilih2, pilih3;
    EditText email;
    String uid;
    Fungsi fn = new Fungsi();
    TextView judul, harga, deskripsi, include, exclude, terms, hargadouble, hargagroup, hargasingle;
    ImageView image;
    ActionBar actionBar;
    private View hiddenPanel;
    private boolean isPanelShown;

    RelativeLayout cardView;

    ScrollView sc;

    private static int firstVisibleInListview;
    Bundle bundle = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tour);

        bundle = getIntent().getExtras();
        setTitle(bundle.getString("judul"));
        actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_USE_LOGO | ActionBar.DISPLAY_SHOW_TITLE);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#029a48")));


        judul = (TextView) findViewById(R.id.titleTour);
        harga = (TextView) findViewById(R.id.harga);
        hargasingle = (TextView) findViewById(R.id.harga1);
        hargadouble = (TextView) findViewById(R.id.harga2);
        hargagroup = (TextView) findViewById(R.id.harga3);
        pilih = (Button) findViewById(R.id.pilih);
        pilih1 = (Button) findViewById(R.id.pilih1);
        pilih2 = (Button) findViewById(R.id.pilih2);
        pilih3 = (Button) findViewById(R.id.pilih3);
        image = (ImageView) findViewById(R.id.imgView);
        deskripsi = (TextView) findViewById(R.id.Description);

        include = (TextView) findViewById(R.id.include);
        exclude = (TextView) findViewById(R.id.exclude);
        terms = (TextView) findViewById(R.id.terms);
        cardView = (RelativeLayout) findViewById(R.id.cardView);

        judul.setText(bundle.getString("judul"));
        deskripsi.setText(bundle.getString("deskripsi"));
        include.setText(bundle.getString("include"));
        exclude.setText(bundle.getString("exclude"));
        terms.setText(Html.fromHtml(bundle.getString("term"), null, new MyTagHandler()));
        Glide.with(DetailTour.this).load(bundle.getString("image")).asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.mipmap.ic_launcher)
                .into(image);
        String prc1 = bundle.getString("harga");
        String prc2 = bundle.getString("harga_family");
        String prc3 = bundle.getString("harga_group");
        harga.setText(ubahharga(prc1.toString())+",-");
        hargasingle.setText(ubahharga(prc1.toString())+",-");
        hargadouble.setText(ubahharga(prc2.toString())+",-");
        hargagroup.setText(ubahharga(prc3.toString())+",-");

        hiddenPanel = findViewById(R.id.hidden_panel);
        pilih.setOnClickListener(this);
        pilih1.setOnClickListener(this);
        pilih2.setOnClickListener(this);
        pilih3.setOnClickListener(this);

    }


    @Override
    public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {

    }

    public String ubahharga(String prc){
        int value = Integer.parseInt(prc);
        String num = NumberFormat.getIntegerInstance(Locale.GERMAN).format(value);

        return num;

    }

    public void slideUpDown(final View view) {
        if(!isPanelShown) {
            // Show the panel
            Animation bottomUp = AnimationUtils.loadAnimation(this,
                    R.anim.bottom_up);

            hiddenPanel.startAnimation(bottomUp);
            hiddenPanel.setVisibility(View.VISIBLE);
            cardView.setVisibility(View.GONE);
            isPanelShown = true;
        }else {
            // Hide the Panel
            Animation bottomDown = AnimationUtils.loadAnimation(this,
                    R.anim.bottom_down);

            hiddenPanel.startAnimation(bottomDown);
            cardView.setVisibility(View.VISIBLE);
            hiddenPanel.setVisibility(View.GONE);
            isPanelShown = false;
        }
    }

    private boolean isPanelShown() {
        return hiddenPanel.getVisibility() == View.VISIBLE;
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.pilih:

                Intent intent = new Intent(DetailTour.this, HargaTour.class);
                intent.putExtra("id_paket", bundle.getString("id_paket"));
                intent.putExtra("nama_tour", bundle.getString("judul"));
                startActivity(intent);
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                finish();

                break;

            case R.id.pilih1:
                Intent pilih1 = new Intent(DetailTour.this, DataPemesanTour.class);
                startActivity(pilih1);
                break;

            case R.id.pilih2:
                Intent pilih2 = new Intent(DetailTour.this, DataPemesanTour.class);
                startActivity(pilih2);
                break;

            case R.id.pilih3:
                Intent pilih3 = new Intent(DetailTour.this, DataPemesanTour.class);
                startActivity(pilih3);
                break;

            default:
                break;
        }

    }

    public void onBackPressed()
    {
        // code here to show dialog
        super.onBackPressed();  // optional depending on your needs
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }
}
