package com.rojo.travel.home;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.rojo.travel.MyApplication;
import com.rojo.travel.OpenBrowser;
import com.rojo.travel.R;
import com.rojo.travel.functions.Fungsi;
import com.rojo.travel.functions.VolleyArrayResult;
import com.rojo.travel.functions.VolleyArrayService;
import com.rojo.travel.functions.VolleyObjectResult;
import com.rojo.travel.functions.VolleyObjectService;
import com.rojo.travel.kereta.ActivityKereta;
import com.rojo.travel.mobil.ActivityMobil;
import com.rojo.travel.pesawat.ActivityPesawat;
import com.rojo.travel.tour.ActivityTour;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ActivityAwal extends Fragment implements View.OnClickListener {

    private ViewPager viewPager;
    RequestQueue rq;
    List<SliderUtil> sliderImg;
//    String requestUrl = "http://192.168.254.1:8080/__ROJO/Rojos/Api/getBanner";
//    String requestUrl2 = "http://192.168.254.1:8080/__ROJO/Rojos/Api/paketWisataRecommend";
//    String requestUrl3 = "http://192.168.254.1:8080/__ROJO/Rojos/Api/paketWisata";

    String requestUrl_Funct = "Api/getBanner";
    String requestUrl2_Funct = "Api/paketWisataRecommend";
    String requestUrl3_Funct = "Api/paketWisata";

    String requestUrl = "";
    String requestUrl2 = "";
    String requestUrl3 = "";


    private static RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView, recyclerView2;
    List<MyData> data_list;
    List<MyDataTour> data_list_car;
    private CustomAdapter customAdapter;
    private CustomAdapterTour customAdapterTour;
    Fungsi fn = new Fungsi();

    VolleyArrayResult volleyArrayResult = null;
    VolleyArrayService volleyArrayService;
    VolleyObjectResult volleyObjectResult, volleyObjectResult2 = null;
    VolleyObjectService volleyObjectService, volleyObjectService2;

    public static ActivityAwal newInstance() {
        ActivityAwal fragment = new ActivityAwal();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    Button btnpesawat, btnkereta, btntrip, btnwisata, btnmobil;
    ImageView rojo, fbrojo, twrojo, igrojo;
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_awal, container, false);

        MyApplication application=(MyApplication)getActivity().getApplication();
        String PathWS=application.sgPathSrv;
        requestUrl=PathWS+requestUrl_Funct;
        requestUrl2=PathWS+requestUrl2_Funct;
        requestUrl3=PathWS+requestUrl3_Funct;

        viewPager = (ViewPager)view.findViewById(R.id.vPager);

        btnpesawat = (Button) view.findViewById(R.id.btn_pesawat);
        btnkereta = (Button) view.findViewById(R.id.btn_kereta);
        //btntrip = (Button) view.findViewById(R.id.btn_trip);
        btnwisata = (Button) view.findViewById(R.id.btn_wisata);
        btnmobil = (Button) view.findViewById(R.id.btn_mobil);

        rojo = (ImageView) view.findViewById(R.id.ivLogo);
        fbrojo = (ImageView) view.findViewById(R.id.ivFacebook);
        twrojo = (ImageView) view.findViewById(R.id.ivTwitter);
        igrojo = (ImageView) view.findViewById(R.id.ivInstagram);

        btnpesawat.setOnClickListener(this);
        btnkereta.setOnClickListener(this);
        btnmobil.setOnClickListener(this);
        //btntrip.setOnClickListener(this);
        btnwisata.setOnClickListener(this);

        rojo.setOnClickListener(this);
        fbrojo.setOnClickListener(this);
        twrojo.setOnClickListener(this);
        igrojo.setOnClickListener(this);

        rq = Volley.newRequestQueue(this.getActivity());
        sliderImg = new ArrayList<>();
        data_list = new ArrayList<>();
        data_list_car = new ArrayList<>();
        //========================================== Image Slider Atas ==============================
        volleyArrayResult = new VolleyArrayResult() {
            @Override
            public void resSuccess(String requestType, JSONArray response) {
                //Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_LONG).show();
                for(int i=0; i<response.length(); i++){
                    SliderUtil sliderUtil = new SliderUtil();
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        sliderUtil.setSliderImgUrl(jsonObject.getString("image"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    sliderImg.add(sliderUtil);
                }
                HomeSliderPagerAdapter hspa = new HomeSliderPagerAdapter(sliderImg, getActivity());
                hspa.notifyDataSetChanged();
                viewPager.setAdapter(hspa);
            }
            @Override
            public void resError(String requestType, VolleyError error) {
                Toast.makeText(getActivity(), "Network connection problem", Toast.LENGTH_LONG).show();
            }
        };
        volleyArrayService = new VolleyArrayService(volleyArrayResult, getContext());
        volleyArrayService.getArrayRequest("GETCALL", requestUrl);

        //========================================== Image Slider Recommended Tour ==============================
        volleyObjectResult2 = new VolleyObjectResult() {
            @Override
            public void resSuccess(String requestType, JSONObject response) {
                //Toast.makeText(getContext(), response.toString(), Toast.LENGTH_LONG).show();
                try {
                    JSONArray jsonArray = response.getJSONArray("paket");
                    for (int i=0; i<jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        String judul = jsonObject.getString("nama");
                        String image = jsonObject.getString("image");
                        String harga = jsonObject.getString("harga");
                        String term = jsonObject.getString("term");
                        String deskripsi = jsonObject.getString("desc");
                        String rate = jsonObject.getString("rating");
                        String include = jsonObject.getString("include");
                        String exclude = jsonObject.getString("exclude");
                        String harga_family = jsonObject.getString("harga_family");
                        String harga_group = jsonObject.getString("harga_group");
                        MyData myData = new MyData(id, image, deskripsi, judul, harga, rate, include, exclude, term, harga_family, harga_group);
                        data_list.add(myData);
                    }
                } catch (Exception e) {

                }
                //================================= Horizontal Scroll View Atas =================================
                recyclerView = (RecyclerView)view.findViewById(R.id.my_recycler_view);
                recyclerView.setHasFixedSize(true);

                layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                customAdapter = new CustomAdapter(getActivity(), data_list);
                customAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(customAdapter);
            }
            @Override
            public void resError(String requestType, VolleyError error) {
                Toast.makeText(getActivity(), "Network connection problem", Toast.LENGTH_LONG).show();
            }
        };
        volleyObjectService2 = new VolleyObjectService(volleyObjectResult2, getContext());
        volleyObjectService2.getJsonObject("GETCALL", requestUrl2);

        //========================================== Image Slider City Tour ==============================
        volleyObjectResult = new VolleyObjectResult() {
            @Override
            public void resSuccess(String requestType, JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("paket");
                    for (int i=0; i<jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        String judul = jsonObject.getString("nama");
                        String image = jsonObject.getString("image");
                        String harga = jsonObject.getString("harga");
                        String term = jsonObject.getString("term");
                        String deskripsi = jsonObject.getString("desc");
                        String rate = jsonObject.getString("rating");
                        String include = jsonObject.getString("include");
                        String exclude = jsonObject.getString("exclude");
                        String harga_family = jsonObject.getString("harga_family");
                        String harga_group = jsonObject.getString("harga_group");
                        MyDataTour myDataTour = new MyDataTour(id, image, deskripsi, judul, harga, rate, include, exclude, term, harga_family, harga_group);
                        data_list_car.add(myDataTour);
                    }

                    //================================= Horizontal Scroll View Bawah =================================
                    recyclerView2 = (RecyclerView)view.findViewById(R.id.my_recycler_view2);
                    recyclerView2.setHasFixedSize(true);

                    layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                    recyclerView2.setLayoutManager(layoutManager);

                    recyclerView2.setItemAnimator(new DefaultItemAnimator());
                    customAdapterTour = new CustomAdapterTour(getActivity(), data_list_car);
                    customAdapterTour.notifyDataSetChanged();
                    recyclerView2.setAdapter(customAdapterTour);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void resError(String requestType, VolleyError error) {
                Toast.makeText(getActivity(), "Network connection problem", Toast.LENGTH_LONG).show();
            }
        };
        volleyObjectService = new VolleyObjectService(volleyObjectResult, getContext());
        volleyObjectService.getJsonObject("GETCALL", requestUrl3);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new myTimerTask(), 1000, 4000);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_pesawat:
                Intent intentPesawat = new Intent(getActivity(), ActivityPesawat.class);
                getActivity().startActivity(intentPesawat);
                break;
            case R.id.btn_kereta:
                Intent intentKereta = new Intent(getActivity(), ActivityKereta.class);
                getActivity().startActivity(intentKereta);
                break;
            case R.id.btn_mobil:
                Intent intentMobil = new Intent(getActivity(), ActivityMobil.class);
                getActivity().startActivity(intentMobil);
                break;
            case R.id.btn_wisata:
                Intent intentWisata = new Intent(getActivity(), ActivityTour.class);
                getActivity().startActivity(intentWisata);
                break;
            case R.id.ivFacebook:
                Intent ifb = new Intent(getContext(), OpenBrowser.class);
                ifb.putExtra("uri", "http://www.facebook.com");
                startActivity(ifb);
                break;
            case R.id.ivInstagram:
                Intent ig = new Intent(getContext(), OpenBrowser.class);
                ig.putExtra("uri", "http://www.instagram.com");
                startActivity(ig);
                break;
            case R.id.ivLogo:
                Intent ilogo = new Intent(getContext(), OpenBrowser.class);
                ilogo.putExtra("uri", "http://www.rojo.id");
                startActivity(ilogo);
                break;
            case R.id.ivTwitter:
                Intent itw = new Intent(getContext(), OpenBrowser.class);
                itw.putExtra("uri", "http://www.twitter.com");
                startActivity(itw);
                break;
            default:
                break;
        }

    }
    //======================================================================================================

    public class myTimerTask extends TimerTask {
        @Override
        public void run() {
            if (getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (viewPager.getCurrentItem() < sliderImg.size() - 1) {
                            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                        } else {
                            viewPager.setCurrentItem(0);
                        }
                    }
                });
            };
        }
    }


}
