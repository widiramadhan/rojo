package com.rojo.travel;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.rojo.travel.akun.ActivityAkun;
import com.rojo.travel.home.ActivityAwal;
import com.rojo.travel.inbox.ActivityInbox;
import com.rojo.travel.order.ActivityPesanan;

import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bnv;
    Fragment selectedFragment = null;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#029a48")));
        BottomNav();
    }

    private void BottomNav() {
        bnv = (BottomNavigationView)findViewById(R.id.btmNav);
        disableShiftMode(bnv);
        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.action_home:
                        selectedFragment = ActivityAwal.newInstance();
                        break;
                    case R.id.action_pesanan:
                        //selectedFragment = ActivityPesanan.newInstance();
                        selectedFragment = new ActivityPesanan();
                        break;
                    case R.id.action_inbox:
                        selectedFragment = ActivityInbox.newInstance();
                        break;
                    case R.id.action_akun:
                        selectedFragment = ActivityAkun.newInstance();
                        break;
                }
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                return true;
            }
        });

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, ActivityAwal.newInstance());
        //transaction.addToBackStack(null);
        transaction.commit();
    }

    @SuppressLint("RestrictedApi")
    public static void disableShiftMode(BottomNavigationView view){
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                //noinspection RestrictedApi
                item.setShiftingMode(false);
                // set once again checked value, so view will be updated
                //noinspection RestrictedApi
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e("BNVHelper", "Unable to get shift mode field", e);
        } catch (IllegalAccessException e) {
            Log.e("BNVHelper", "Unable to change value of shift mode", e);
        }
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()) {
            case R.id.menu1:
                Toast.makeText(getApplicationContext(), "Menu 1", Toast.LENGTH_LONG).show();
                return true;
            case R.id.menu2:
                Toast.makeText(getApplicationContext(), "Menu 2", Toast.LENGTH_LONG).show();
                return true;
            case R.id.menu3:
                Toast.makeText(getApplicationContext(), "Menu 3", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/

    @Override
    public void onBackPressed() {
        int count = getFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            super.onBackPressed();
        } else {
            getFragmentManager().popBackStack();
        }
    }
}
