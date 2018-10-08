package com.rojo.travel.akun;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.rojo.travel.MainActivity;
import com.rojo.travel.MyApplication;
import com.rojo.travel.R;
import com.rojo.travel.functions.Fungsi;
import com.rojo.travel.functions.VolleyObjectResult;
import com.rojo.travel.functions.VolleyObjectService;
import com.rojo.travel.home.ActivityAwal;
import com.rojo.travel.pesawat.DetailPemesanan;
import com.rojo.travel.sqlite.DatabaseHandler;
import com.rojo.travel.sqlite.temp_akun;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by kangyasin on 17/11/2017.
 */

public class Login extends AppCompatActivity{

    EditText email, password;
    TextView lupapass;
    Button login;
    String checkemail,checkpassword;
    Fungsi fn = new Fungsi();

    VolleyObjectResult vor;
    VolleyObjectService vos;
    JSONObject data = null;
    String token, uid;
    ActionBar actionBar;
    ProgressDialog pDialog;
    private ProgressBar progressBar;

    String url="";
    String url_funct = "apimember/login";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String datauser = fn.getdatalogin(Login.this);
        //Toast.makeText(getApplication(), datauser, Toast.LENGTH_LONG).show();

//        TextView forgotpass = (TextView)findViewById(R.id.lupapassword);

        MyApplication application=(MyApplication)getApplication();
        String PathWS=application.sgPathSrv;
        url=PathWS+url_funct;

        if(datauser != null) {
            setContentView(R.layout.activity_login_exist);
        }else{
            setContentView(R.layout.activity_login);


            email = (EditText) findViewById(R.id.email);
            password = (EditText) findViewById(R.id.password);
            login = (Button) findViewById(R.id.submiton);

            progressBar = (ProgressBar) findViewById(R.id.proBar);
            final View view = findViewById(R.id.coordinator);

            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    uid = fn.getDeviceUniqueID(Login.this);
                    checkemail = email.getText().toString();
                    checkpassword = password.getText().toString();

                    HashMap<String, String> dt = new HashMap<String, String>();
                    dt.put("uid", uid);
                    dt.put("email", checkemail);
                    dt.put("password", checkpassword);
                    final JSONObject data = new JSONObject(dt);

                    final String valid = fn.validemail(checkemail);

                    pDialog = new ProgressDialog(Login.this);
                    pDialog.setCancelable(false);
                    pDialog.setMessage("Loading...");
                    showDialog();

                    vor = new VolleyObjectResult() {
                        @Override
                        public void resSuccess(String requestType, JSONObject response) {
                            hideDialog();

                            try {
                                String status = response.getString("status");

                                if (status.toString().equals("true")) {
                                    SharedPreferences prefs = PreferenceManager
                                            .getDefaultSharedPreferences(Login.this);
                                    SharedPreferences.Editor lds = prefs.edit();

                                    lds.putString("email", response.getString("email").toString());
                                    lds.putString("message", response.getString("message").toString());
                                    lds.putString("status", response.getString("status").toString());
                                    lds.putString("uid", response.getString("uid").toString());
                                    lds.putString("tgl_register", response.getString("tgl_register").toString());
                                    lds.putString("nama", response.getString("nama").toString());
                                    lds.putString("alamat", response.getString("alamat").toString());
                                    lds.putString("nomor_telepon", response.getString("nomor_telepon").toString());
                                    lds.putString("kewarganegaraan", response.getString("kewarganegaraan").toString());
                                    //Toast.makeText(Login.this, lds.toString(), Toast.LENGTH_LONG).show();
                                    lds.commit();
//                                        LoginDataSet lds = new LoginDataSet();
//                                        lds.setEmail(response.getJSONObject("email").toString());
//                                        lds.setMessage(response.getJSONObject("message").toString());
//                                        lds.setPassword(response.getJSONObject("password").toString());
//                                        lds.setStatus(response.getJSONObject("status").toString());
//                                        lds.setUid(response.getJSONObject("uid").toString());

                                    DatabaseHandler databaseHandler=new DatabaseHandler(Login.this);
                                    SQLiteDatabase db=databaseHandler.getReadableDatabase();
                                    Cursor cursor=db.query("tbl_akun", new String[]{"id","akun_username","akun_email"},
                                            null, null, null, null, null);
                                    //Log.d("tes","Cursor Count : " + cursor.getCount());

                                    if(cursor.getCount()>0){
                                        temp_akun tempAkun=databaseHandler.findOne(1);
                                        databaseHandler.delete(new temp_akun(null, null));
                                        databaseHandler.save(new temp_akun(response.getString("email").toString(), response.getString("email").toString()));
                                    }else {
                                        databaseHandler.save(new temp_akun(response.getString("email").toString(), response.getString("email").toString()));
                                    }

                                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(i);

                                } else {

                                    String message = "Maaf Email atau Password Salah !!";
                                    fn.showSnackbar(view, message);

                                    //Toast.makeText(Login.this, "Maaf Email atau Password Salah.", Toast.LENGTH_LONG).show();

                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void resError(String requestType, VolleyError error) {
                            Toast.makeText(Login.this, error.toString(), Toast.LENGTH_LONG).show();
                        }
                    };
                    vos = new VolleyObjectService(vor, Login.this);
                    vos.postJsonObject("POSTCALL", url, data);
//                    }else{
//
//                        String message = "Format email salah !!";
//                        fn.showSnackbar(view, message);
//                    }

                    //Toast.makeText(v.getContext(),valid.toString(),Toast.LENGTH_LONG).show();
                    //Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    //startActivity(i);
                    //new CreateNewProduct().execute();
                    // startActivity(new Intent(RegistrationForm.this, Home.class));
                }
            });



        }



    }

    public void forgot_pass(View v)
    {
        Intent fp = new Intent(Login.this, ForgotPass.class);
        startActivity(fp);
    }

    public void onBackPressed(){
        finish();
        Intent intent = new Intent(Login.this, MainActivity.class);
        startActivity(intent);
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
