package com.rojo.travel.order;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.rojo.travel.MainActivity;
import com.rojo.travel.MyApplication;
import com.rojo.travel.OpenBrowser;
import com.rojo.travel.R;

@SuppressLint({"NewApi", "SetJavaScriptEnabled"})

public class HistoryPesananPesawat  extends AppCompatActivity {

    private WebView view;
    String orderID;
    String url;
    ProgressBar proBar;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_pesanan_pesawat);

        MyApplication application=(MyApplication)getApplication();
        String PathWS=application.sgPathSrv;
        url=PathWS+"Api/payment_success/pesawat/";

        Intent intent = getIntent();
        orderID = intent.getStringExtra("OrderID");
        view = (WebView)findViewById(R.id.wv_history_pesanan);
        HistoryPesananPesawat.ButtonClickJavascriptInterface myJavaScriptInterface = new HistoryPesananPesawat.ButtonClickJavascriptInterface(this);

        view.addJavascriptInterface(myJavaScriptInterface, "WebPush");
        view.getSettings().setJavaScriptEnabled(true);
        view.setWebViewClient(new WebViewClient());
        view.loadUrl(url + orderID);
        view.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }
        });

        view.setWebViewClient(new WebViewClient() {
            ProgressDialog progressDialog;

            //Show loader on url load
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                if (progressDialog == null) {
                    //// in standard case YourActivity.this
                    progressDialog = new ProgressDialog(HistoryPesananPesawat.this);
                    progressDialog.setMessage("Loading...");
                    progressDialog.show();
                }
            }

            public void onPageFinished(WebView view, String url) {
                try {
                    progressDialog.dismiss();
                    progressDialog = null;
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                //
            }
        });
    }

    public void onBackPressed () {
        /*AlertDialog.Builder aBuilder = new AlertDialog.Builder(HistoryPesananPesawat.this);
        aBuilder.setTitle(Html.fromHtml("<small>Transaction Exit</small>"));
        aBuilder.setMessage(Html.fromHtml("<small>Anda yakin akan keluar dari transaksi?</small>"))
                .setCancelable(false)
                .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(HistoryPesananPesawat.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = aBuilder.create();
        alertDialog.show();*/
        finish();
    }

    public class ButtonClickJavascriptInterface {
        Context mContext;
        ButtonClickJavascriptInterface(Context c) {
            mContext = c;
        }

        @JavascriptInterface
        public void onButtonClick(String strParam1, String strParam2, String strParam3, String strParam4) {
            //Toast.makeText(mContext, strParam1 + strParam2 + strParam3 + strParam4 + strParam5, Toast.LENGTH_SHORT).show();

            if(strParam1.equals("Hm")) {
                Intent intent = new Intent(HistoryPesananPesawat.this, MainActivity.class);
                startActivity(intent);
            }
        }
    }
}
