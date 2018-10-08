package com.rojo.travel.order;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rojo.travel.MyApplication;
import com.rojo.travel.R;
import com.rojo.travel.functions.Fungsi;
import com.rojo.travel.functions.VolleyObjectResult;
import com.rojo.travel.functions.VolleyObjectService;
import com.rojo.travel.pesawat.DetailPemesanan;
import com.rojo.travel.pesawat.ListPenerbangan;
import com.rojo.travel.sqlite.DatabaseHandler;
import com.rojo.travel.sqlite.temp_akun;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FragmentOrderPesawat extends Fragment {

    VolleyObjectResult vor;
    VolleyObjectService vos;
    JSONObject data = null;
    Fungsi fn = new Fungsi();
    String token, domestik, Email_Exist;
    SharedPreferences preferences, pref;
    ActionBar actionBar;
    ProgressBar progressBar;
    private ListPesawatAdapter listPesawatAdapter;
    View rootView, rootViewNull;
    ListView listView;
    private List<ListPesawatDataSet> listPesawat = new ArrayList<ListPesawatDataSet>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        String uid = fn.getDeviceUniqueID(getActivity());
        String email = fn.getdatalogin(getActivity());
       /* String url = "http://m.rojo.id/apimember/getFlightOrder?uid="+uid+"&email="+email;
        String url2 = "http://m.rojo.id/apimember/getFlightOrder?uid="+uid;
*/
        //====SQL Lite
        DatabaseHandler databaseHandler=new DatabaseHandler(getActivity());
        SQLiteDatabase db=databaseHandler.getReadableDatabase();
        Cursor cursor=db.query("tbl_akun", new String[]{"id","akun_username","akun_email"},
                null, null, null, null, null);
        //Log.d("tes","Cursor Count : " + cursor.getCount());

        if(cursor.getCount()>0){
            //cursor.moveToFirst();
            temp_akun tempAkun=databaseHandler.findOne(1);
            Email_Exist = tempAkun.getAkun_email();
        }else {
            Toast.makeText(getActivity(), "No Data", Toast.LENGTH_SHORT).show();
        }

        //Toast.makeText(getActivity(), Email_Exist, Toast.LENGTH_LONG).show();

        String url2, url;
        //String url = "http://192.168.254.1:8080/__ROJO/Rojos/apimember/getFlightOrder?uid="+uid+"&email="+Email_Exist;
        //String url2 = "http://192.168.254.1:8080/__ROJO/Rojos/apimember/getFlightOrder?uid="+uid;
        //String url2 = "http://192.168.254.1:8080/__ROJO/Rojos/apimember/getFlightOrder?uid="+uid+"&email="+Email_Exist;
        String url_funct = "/apimember/getFlightOrder?uid="+uid+"&email="+Email_Exist;
        String url2_funct = "/apimember/getFlightOrder?uid="+uid+"&email="+Email_Exist;

        MyApplication application=(MyApplication)getActivity().getApplication();
        String PathWS=application.sgPathSrv;
        url2=PathWS+url2_funct;
        url=PathWS+url_funct;

        rootView = inflater.inflate(R.layout.activity_pesanan_pesawat, container, false);
        rootViewNull = inflater.inflate(R.layout.activity_pesanan_pesawat_null, container, false);
        listView = (ListView) rootView.findViewById(R.id.list);
        vor = new VolleyObjectResult() {
            @Override
            public void resSuccess(String requestType, JSONObject response) {
                //Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_LONG).show();
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for (int i=0; i<jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        ListPesawatDataSet lds = new ListPesawatDataSet();
                        lds.setTiketId(jsonObject.getString("trx_pemesanan_tiket_id"));
                        lds.setTiketInvoice(jsonObject.getString("trx_pemesanan_tiket_kode_invoice"));
                        lds.setTotalPrice(jsonObject.getString("trx_pemesanan_tiket_total_order_price"));
                        lds.setFlightDepCityName(jsonObject.getString("trx_flight_departure_city_name"));
                        lds.setArrCityName(jsonObject.getString("trx_flight_arrival_city_name"));
                        lds.setFlightDateStr(jsonObject.getString("trx_flight_departure_flight_date_str"));
                        lds.setDepTime(jsonObject.getString("trx_flight_simple_departure_time"));
                        lds.setAirlinesName(jsonObject.getString("trx_flight_airlines_name"));
                        lds.setDepCityCode(jsonObject.getString("trx_pemesanan_tiket_departure_city_code"));
                        lds.setDepCityName(jsonObject.getString("trx_pemesanan_tiket_departure_city_name"));
                        lds.setOrderId(jsonObject.getString("trx_pemesanan_tiket_order_id"));
                        //listPesawat.clear();
                        listPesawat.add(lds);
                    }
                    listPesawatAdapter = new ListPesawatAdapter(getActivity(), listPesawat);
                    listPesawatAdapter.notifyDataSetChanged();
                    listView.setAdapter(listPesawatAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void resError(String requestType, VolleyError error) {
                //Toast.makeText(getActivity(), "Connection problem!", Toast.LENGTH_LONG).show();
            }
        };
        vos = new VolleyObjectService(vor, getActivity());
        vos.postJsonObject("GETCALL", url2, data);
        return rootView;
    }

}
