package com.rojo.travel.functions;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.util.Base64;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.rojo.travel.akun.Register;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Fungsi {

    public String getDeviceUniqueID(Context context){
        String device_unique_id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        return device_unique_id;
    }


    public String getdatalogin(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String datalogin = prefs.getString("email", null);
        return datalogin;
    }

    public String getEmail(Context context){
        String email = null;
        Pattern emailPattern = Patterns.EMAIL_ADDRESS;
        Account accounts[] = AccountManager.get(context).getAccounts();
        for(Account acc : accounts){
            if(emailPattern.matcher(acc.name).matches()){
                email = acc.name;
            }
        }
        return email;
    }

    public String formatIntRupiah(String angka) {
        String hrg = angka.substring(0, angka.indexOf("."));
        int val = Integer.parseInt(hrg);
        String result = NumberFormat.getIntegerInstance(Locale.GERMAN).format(val);
        return result;
    }

    public String ubahTgl(String data){

        DateFormat inputFormat = new SimpleDateFormat("dd/MMM/yyyy");
        DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
        String inputDateStr=data;
        Date date = null;
        try {
            date = inputFormat.parse(inputDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String outputDateStr = outputFormat.format(date);

        return outputDateStr;
    };

    public static String imageToString(Bitmap bm){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos); //bm is the bitmap object
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    public static Bitmap stringToImage(String input) {
        byte[] decodedBytes = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    public final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9+._%-+]{1,256}" +
                    "@" +
                    "[a-zA-Z0-9][a-zA-Z0-9-]{0,64}" +
                    "(" +
                    "." +
                    "[a-zA-Z0-9][a-zA-Z0-9-]{0,25}" +
                    ")+"
    );

    public final Pattern PHONE_PATTERN = Pattern.compile(
            "\\(?(?:\\+62|62|0)(?:\\d{2,3})?\\)?[ .-]?\\d{2,4}[ .-]?\\d{2,4}[ .-]?\\d{2,4}"
    );

    public String validate(String checkemail, String pass,  String cpass, String checknama) {
        String temp="Sukses";

        if(checknama == ""){
            temp = "Please fill name!";
        }
        else if(!EMAIL_ADDRESS_PATTERN.matcher(checkemail).matches()){
            temp = "Invalid email address!";

        }else if(!pass.equals(cpass)){
            temp = "Password not match!";

        }
        return temp;
    }

    public String validemail(String checkemail) {
        String temp="Sukses";
        if(!EMAIL_ADDRESS_PATTERN.matcher(checkemail).matches()){
            temp = "Invalid email address!";
        }
        return temp;
    }

    public String validMobile(String phone) {
        String temp = "Sukses";
        if(!PHONE_PATTERN.matcher(phone).matches()) {
            temp = "Invalid phone number!";
        }
        return temp;
    }

    public String ubahharga(String prc){
        int value = Integer.parseInt(prc);
        String num = NumberFormat.getIntegerInstance(Locale.GERMAN).format(value);
        return num;
    }


    public String capitalize(String capString){
        StringBuffer capBuffer = new StringBuffer();
        Matcher capMatcher = Pattern.compile("([a-z])([a-z]*)", Pattern.CASE_INSENSITIVE).matcher(capString);
        while (capMatcher.find()){
            capMatcher.appendReplacement(capBuffer, capMatcher.group(1).toUpperCase() + capMatcher.group(2).toLowerCase());
        }
        return capMatcher.appendTail(capBuffer).toString();
    }


    public void showSnackbar(View view, String message) {
        // Create snackbar
        int duration = Snackbar.LENGTH_INDEFINITE;
        final Snackbar snackbar = Snackbar.make(view, message, duration);
        // Set an action on it, and a handler
        snackbar.setAction("DISMISS", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });
        snackbar.show();
    }

}
