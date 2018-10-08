package com.rojo.travel;

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
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.rojo.travel.pesawat.ActivityPesawat;
import com.rojo.travel.pesawat.ListPenerbangan;

@SuppressLint({"NewApi", "SetJavaScriptEnabled"})

public class OpenBrowser extends AppCompatActivity {

    private WebView view;
    String uri;
    ProgressBar proBar;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_browser);

        Intent intent = getIntent();
        uri = intent.getStringExtra("uri");
        view = (WebView)findViewById(R.id.webview);
        ButtonClickJavascriptInterface myJavaScriptInterface = new ButtonClickJavascriptInterface(this);

        view.addJavascriptInterface(myJavaScriptInterface, "WebPush");
        view.getSettings().setJavaScriptEnabled(true);
        view.setWebViewClient(new WebViewClient());
        view.loadUrl(uri);
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
                    progressDialog = new ProgressDialog(OpenBrowser.this);
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

//        view.setOnKeyListener(new View.OnKeyListener() {
//
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if (keyCode == KeyEvent.KEYCODE_BACK
//                        && event.getAction() == MotionEvent.ACTION_UP
//                        && view.canGoBack()) {
//                    view.goBack();
//                    return true;
//                }
//                return false;
//            }
//        });

    }

    public void onBackPressed () {
        AlertDialog.Builder aBuilder = new AlertDialog.Builder(OpenBrowser.this);
        aBuilder.setTitle(Html.fromHtml("<small>Transaction Exit</small>"));
        aBuilder.setMessage(Html.fromHtml("<small>Anda yakin akan keluar dari transaksi?</small>"))
                .setCancelable(false)
                .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(OpenBrowser.this, MainActivity.class);
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
        alertDialog.show();
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
                Intent intent = new Intent(OpenBrowser.this, MainActivity.class);
                startActivity(intent);
            }
        }
    }
}


