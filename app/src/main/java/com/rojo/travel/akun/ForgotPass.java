package com.rojo.travel.akun;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.rojo.travel.MainActivity;
import com.rojo.travel.R;
import com.rojo.travel.functions.Fungsi;
import com.rojo.travel.functions.VolleyObjectResult;
import com.rojo.travel.functions.VolleyObjectService;

import org.json.JSONObject;

import java.util.HashMap;

public class ForgotPass extends AppCompatActivity {
    EditText email, password;
    Button forgot;
    String checkemail,checkpassword;
    Fungsi fn = new Fungsi();

    VolleyObjectResult vor;
    VolleyObjectService vos;
    JSONObject data = null;
    String token, uid;
    ActionBar actionBar;
    private ProgressBar progressBar;
    final String url = "http://m.rojo.id/Apimember/requestForgetPassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);
        email = (EditText) findViewById(R.id.email);
        forgot = (Button) findViewById(R.id.submiton);

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                uid = fn.getDeviceUniqueID(ForgotPass.this);
                checkemail = email.getText().toString();

                HashMap<String, String> dt = new HashMap<String, String>();
                dt.put("email", checkemail);
                final JSONObject data = new JSONObject(dt);

//                final String valid = fn.validemail(checkemail);
                final View view = findViewById(R.id.coordinator);

                vor = new VolleyObjectResult() {
                    @Override
                    public void resSuccess(String requestType, JSONObject response) {

                        try {
                            String status = response.getString("status");
                            if (status.toString().equals("true")) {
                                String message = response.getString("message");
                                fn.showSnackbar(view, message);
                            } else {
                                String message = response.getString("message");
                                fn.showSnackbar(view, message);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void resError(String requestType, VolleyError error) {
                        Toast.makeText(ForgotPass.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                };
                vos = new VolleyObjectService(vor, ForgotPass.this);
                vos.postJsonObject("POSTCALL", url, data);
            }
        });

    }
}
