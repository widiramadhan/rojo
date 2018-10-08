package com.rojo.travel.functions;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by limadawai on 10/30/17.
 */

public class KeyStore {

    public KeyStore() {
        super();
    }

    public void setPref(Context context, String[] data, String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences("PrefKey", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for (int i=0; i<data.length; i++) {
            editor.putString(key, data[i]);
        }
        editor.commit();
    }

    public String getPref(Context context, String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences("PrefKey", Context.MODE_PRIVATE);
        String text = sharedPreferences.getString(key, null);
        return text;
    }

    public void clearPref(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("PrefKey", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.commit();
        editor.clear();
    }

    public void removePref(Context context, String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences("PrefKey", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.commit();
    }

}
