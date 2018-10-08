package com.rojo.travel.mobil;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.rojo.travel.MainActivity;
import com.rojo.travel.R;
import com.rojo.travel.functions.VolleyObjectResult;
import com.rojo.travel.functions.VolleyObjectService;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kangyasin on 11/12/2017.
 */

public class ReturnMobil extends AppCompatActivity {

    VolleyObjectResult vor;
    VolleyObjectService vos;
    JSONObject data = null;
    String urlmobil = "http://m.rojo.id/Api/insertPemesanan";
    String status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();try {
            data = new JSONObject(extras.getString("json"));
            //Toast.makeText(ReturnMobil.this, data.toString(), Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Toast.makeText(ReturnMobil.this, urlmobil.toString(), Toast.LENGTH_LONG).show();

        vor = new VolleyObjectResult() {
            @Override
            public void resSuccess(String requestType, JSONObject response) {
                //Toast.makeText(ReturnMobil.this, response.toString(), Toast.LENGTH_LONG).show();
                try {
                    status = response.getString("status");
                    setContentView(R.layout.mobil_ready);
                    Button back = (Button) findViewById(R.id.btnback);
                    back.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(ReturnMobil.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    });
                    //JSONArray jsonArray = response.getJSONArray("status");
                    //Toast.makeText(ReturnMobil.this, status.toString(), Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    setContentView(R.layout.mobil_unready);
                    Button back = (Button) findViewById(R.id.btnback);
                    back.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(ReturnMobil.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    });
                    e.printStackTrace();
                }
            }
            @Override
            public void resError(String requestType, VolleyError error) {
                Toast.makeText(ReturnMobil.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        };
        vos = new VolleyObjectService(vor, ReturnMobil.this);
        vos.postJsonObject("POSTCALL", urlmobil, data);

    }

    public void onBackPressed(){
        finish();
        Intent intent = new Intent(ReturnMobil.this, MainActivity.class);
        startActivity(intent);
    }

}
