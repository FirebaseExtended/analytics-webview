package com.google.firebase.quickstart.analytics.webview;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private WebView mWebView;

    @SuppressLint("AddJavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWebView = (WebView) findViewById(R.id.webview);
        mWebView.getSettings().setJavaScriptEnabled(true);

        // TODO(samstern): restrict WebView domain

        // Only add the JavaScriptInterface on API version JELLY_BEAN_MR1 and above, due to
        // security concerns, see link below for more information:
        // https://developer.android.com/reference/android/webkit/WebView.html#addJavascriptInterface(java.lang.Object,%20java.lang.String)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            mWebView.addJavascriptInterface(
                    new AnalyticsWebInterface(this), AnalyticsWebInterface.TAG);
        } else {
            Log.w(TAG, "Not adding JavaScriptInterface, API Version: " + Build.VERSION.SDK_INT);
        }
    }
}
