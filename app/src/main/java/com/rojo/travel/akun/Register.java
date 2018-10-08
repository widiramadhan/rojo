package com.rojo.travel.akun;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by kangyasin on 17/11/2017.
 */

public class Register extends AppCompatActivity {
    Spinner spinTitle;
    String[] title = {"Mr","Mrs"};
    EditText namadepan, email, password, confirmpassword, alamat, notelp, nationality, cariNegara, negaraid, username, namabelakang;
    Button register;
    String uid, checkemail, pass, cpass, checknama, idnegara, negaranama, titles, namadepans,namabelakangs,alamats,negaraids,emails,usernames,passwords,notelps;;
    Fungsi fn = new Fungsi();


    private ListView listNegara;
    Calendar calendar = Calendar.getInstance();
    VolleyObjectResult volleyObjectResult = null;
    VolleyObjectService volleyObjectService;
    VolleyArrayService vas;
    VolleyArrayResult var = null;

    VolleyObjectResult vor;
    VolleyObjectService vos;
    private ProgressBar progressBar;
    private CoordinatorLayout coordinatorLayout;
    /*String url = "http://m.rojo.id/api/getNegara";
    final String registerurl = "http://m.rojo.id/apimember/register";*/
    String url="";
    String registerurl="";
    String url_funct_negara = "api/getNegara";
    String url_funct = "apimember/register";
    JSONObject dt = new JSONObject();

    private List<NeagaraDataSet> list = new ArrayList<NeagaraDataSet>();
    private NegaraAdapter negaraAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        MyApplication application=(MyApplication)getApplication();
        String PathWS=application.sgPathSrv;
        url=PathWS+url_funct_negara;
        registerurl=PathWS+url_funct;

        namadepan = (EditText) findViewById(R.id.namadepan);
        namabelakang = (EditText) findViewById(R.id.namaLast);
        email = (EditText) findViewById(R.id.email);
        email.setText(fn.getEmail(Register.this));
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (fn.validemail(email.getText().toString()).equals("Invalid email address!")) {
                    email.setError("Email tidak valid!");
                }
            }
        });
        password = (EditText) findViewById(R.id.password);
        confirmpassword = (EditText) findViewById(R.id.cpassrowd);
        nationality = (EditText) findViewById(R.id.nationality);
        negaraid = (EditText) findViewById(R.id.negaraid);
        alamat = (EditText) findViewById(R.id.alamat);
        notelp = (EditText) findViewById(R.id.nohp);
        notelp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (fn.validMobile(notelp.getText().toString()).equals("Invalid phone number!")) {
                    notelp.setError("No telpon tidak valid");
                }
            }
        });
        username = (EditText) findViewById(R.id.username);
        register = (Button) findViewById(R.id.submiton);

        spinTitle = (Spinner) findViewById(R.id.spinTitle);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Register.this, android.R.layout.simple_spinner_dropdown_item, title);
        spinTitle.setAdapter(adapter);

        nationality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.clear();
                final AlertDialog.Builder builder = new AlertDialog.Builder(Register.this, R.style.DialogTheme);
                final LayoutInflater inflater = Register.this.getLayoutInflater();
                final View view = inflater.inflate(R.layout.list_negara, null, false);
                cariNegara = (EditText) view.findViewById(R.id.cariNegara);
                listNegara = (ListView) view.findViewById(R.id.listNegara);
                progressBar = (ProgressBar) view.findViewById(R.id.proBar);
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
                                list.add(bds);

                            }
                            negaraAdapter = new NegaraAdapter(Register.this, list);
                            negaraAdapter.notifyDataSetChanged();
                            listNegara.setAdapter(negaraAdapter);
                            //========================= Action Listener EditText Cari Bandara =====================
                            cariNegara.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                                @Override
                                public void onTextChanged(CharSequence s, int start, int before, int count) {}
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
                        Toast.makeText(Register.this, "Pencarian gagal", Toast.LENGTH_LONG).show();
                        finish();
                    }
                };
                volleyObjectService = new VolleyObjectService(volleyObjectResult, Register.this);
                volleyObjectService.getJsonObject("GETCALL", url);
                progressBar.setVisibility(View.VISIBLE);
                final AlertDialog alertDialog = builder.create();
                Window window = alertDialog.getWindow();
                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                alertDialog.show();
                listNegara.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        idnegara = list.get(position).getNegara_id();
                        negaranama = list.get(position).getNegara_nama_1();
                        nationality.setText(negaranama);
                        negaraid.setText(idnegara);
                        alertDialog.dismiss();
                    }
                });
            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String pass = password.getText().toString();
                final String cpass = confirmpassword.getText().toString();

                if (namadepan.getText().toString().equals("")) {
                    namadepan.setError("Nama dpn tdk boleh kosong");
                } else if (namabelakang.getText().toString().equals("")) {
                    namabelakang.setError("Nama blkg tdk boleh kosong");
                } else if (alamat.getText().toString().equals("")) {
                    alamat.setError("Alamat tdk boleh kosong");
                } else if (negaraid.getText().toString().equals("")) {
                    negaraid.setError("ID Negara tdk boleh kosong");
                } else if (email.getText().toString().equals("")) {
                    email.setError("Email tdk boleh kosong");
                } else if (username.getText().toString().equals("")) {
                    username.setError("Username tdk boleh kosong");
                } else if (password.getText().toString().equals("")) {
                    password.setError("Password tdk boleh kosong");
                } else if (cpass.toString().length() > 0 && pass.length() > 0) {
                    if (!cpass.equals(pass)) {
                        confirmpassword.setError("Konfirmasi password tdk sama!");
                    }
                } else if (notelp.getText().toString().equals("")) {
                    notelp.setError("Telepon tdk boleh kosong");
                }

                uid = fn.getDeviceUniqueID(Register.this);
                titles = spinTitle.getSelectedItem().toString();
                namadepans = namadepan.getText().toString();
                namabelakangs = namabelakang.getText().toString();
                alamats = alamat.getText().toString();
                negaraids = negaraid.getText().toString();
                emails = email.getText().toString();
                usernames = username.getText().toString();
                passwords = password.getText().toString();
                notelps = notelp.getText().toString();

                HashMap<String, String> data = new HashMap<String, String>();
                data.put("uid", uid);
                data.put("title", titles);
                data.put("first_name", namadepans);
                data.put("last_name", namabelakangs);
                data.put("address", alamats);
                data.put("nationality", negaraids);
                data.put("email", emails);
                data.put("username", usernames);
                data.put("password", passwords);
                data.put("no_telp", notelps);
                data.put("Content-Type", "application/x-www-form-urlencoded");
                final JSONObject dt = new JSONObject(data);

                //Toast.makeText(Register.this, dt.toString(), Toast.LENGTH_LONG).show();

                vor = new VolleyObjectResult() {
                    @Override
                    public void resSuccess(String requestType, JSONObject response) {
                       // Log.d("cekRegis", "");
                        try {
                            String status = response.getString("status");
                            String message = response.getString("message");
                            if(status.equals("true")) {
                                Toast.makeText(Register.this,"Register berhasil, silahkan login",Toast.LENGTH_LONG).show();
                                Intent i = new Intent(getApplicationContext(), Login.class);
                                startActivity(i);
                            }else{
                                Toast.makeText(Register.this, "Registrasi gagal! - "+message, Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void resError(String requestType, VolleyError error) {
                        Toast.makeText(Register.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                };
                vos = new VolleyObjectService(vor, Register.this);
                vos.postJsonObject("POSTCALL", registerurl, dt);

//                if(valid == "Sukses"){
//                    Toast.makeText(v.getContext(),"Register Success Please Login",Toast.LENGTH_LONG).show();
//                    Intent i = new Intent(getApplicationContext(), Login.class);
//                    startActivity(i);
//                }else{
//                    Toast.makeText(v.getContext(),valid.toString(),Toast.LENGTH_LONG).show();
//                }
            }



        });
    }

}
