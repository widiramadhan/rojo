package com.rojo.travel.akun;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.rojo.travel.R;
import com.rojo.travel.functions.Fungsi;

public class ActivityAkun extends Fragment {
    Button daftar, login, logout;
    Fungsi fn = new Fungsi();
    TextView nama, alamat, email, negara, telp;
    public static ActivityAkun newInstance() {
        ActivityAkun fragment = new ActivityAkun();
        return fragment;

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        String datauser = fn.getdatalogin(getContext());
        //Toast.makeText(getContext(), datauser, Toast.LENGTH_LONG).show();
        if(datauser != null) {
            View v = inflater.inflate(R.layout.activity_login_exist, container, false);
            final SharedPreferences sesdata = PreferenceManager
                    .getDefaultSharedPreferences(getContext());

            nama = (TextView) v.findViewById(R.id.isinama);
            alamat = (TextView) v.findViewById(R.id.isialamat);
            email = (TextView) v.findViewById(R.id.isiemail);
            negara = (TextView) v.findViewById(R.id.isinegara);
            telp = (TextView) v.findViewById(R.id.isitelp);

            nama.setText(sesdata.getString("nama", null));
            alamat.setText(sesdata.getString("alamat", null));
            email.setText(sesdata.getString("email", null));
            negara.setText(sesdata.getString("kewarganegaraan", null));
            telp.setText(sesdata.getString("nomor_telepon", null));

            logout = (Button) v.findViewById(R.id.submiton);
            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences.Editor lds = sesdata.edit();
                    lds.putString("email", null);
                    lds.commit();

                    Intent masuk = new Intent(getActivity(), Login.class);
                    getActivity().startActivity(masuk);
                }
            });
            return v;
        }else {

            View v = inflater.inflate(R.layout.activity_akun, container, false);

            daftar = (Button) v.findViewById(R.id.daftar);
            login = (Button) v.findViewById(R.id.masuk);

            daftar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent dft = new Intent(getActivity(), Register.class);
                    getActivity().startActivity(dft);
                }
            });

            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent masuk = new Intent(getActivity(), Login.class);
                    getActivity().startActivity(masuk);
                }
            });
            return v;

        }




    }
}
