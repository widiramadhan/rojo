package com.rojo.travel.kereta;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.rojo.travel.OpenBrowser;
import com.rojo.travel.R;

/**
 * Created by limadawai on 11/01/18.
 */

public class CheckOutKereta extends AppCompatActivity {

    Intent intent = getIntent();
    String token, pemesan_id, order_id, delete_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(CheckOutKereta.this);
        builder.setTitle("Checkout Order");
        builder.setMessage("Submit untuk pemesanan tiket anda");
        builder.setIcon(R.drawable.crown);
        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences pref = getApplicationContext().getSharedPreferences("checkoutData", 0);
                token = pref.getString("token", null);
                Toast.makeText(CheckOutKereta.this, token, Toast.LENGTH_LONG).show();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(CheckOutKereta.this, OpenBrowser.class);
                //intent.putExtra("uri", );
                startActivity(intent);
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
