package com.google.firebase.quickstart.analytics.webview;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;

@SuppressLint({"AddJavascriptInterface", "SetJavaScriptEnabled"})
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize WebView and enable JavaScript
        mWebView = (WebView) findViewById(R.id.webview);
        mWebView.getSettings().setJavaScriptEnabled(true);

        // Restrict requests in the WebView to a single domain (in this case, our Firebase
        // Hosting domain) so that no other websites can call into our Java code.
        String hostingUrl = getHostingUrl();
        mWebView.setWebViewClient(new SingleDomainWebViewClient(hostingUrl));

        // Only add the JavaScriptInterface on API version JELLY_BEAN_MR1 and above, due to
        // security concerns, see link below for more information:
        // https://developer.android.com/reference/android/webkit/WebView.html#addJavascriptInterface(java.lang.Object,%20java.lang.String)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            mWebView.addJavascriptInterface(
                    new AnalyticsWebInterface(this), AnalyticsWebInterface.TAG);
        } else {
            Log.w(TAG, "Not adding JavaScriptInterface, API Version: " + Build.VERSION.SDK_INT);
        }

        // Navigate to site
        mWebView.loadUrl(hostingUrl);
    }

    /**
     * Determine the Firebase Hosting URL for this application by modifying the Firebase Database
     * URL. This constant will be used to limit the URLs that the WebView can display.
     */
    private String getHostingUrl() {
        // Database URl is https://<app-name>.firebaseio.com
        String databaseUrl = getString(R.string.firebase_database_url);

        // Hosting URL is https://<app-name>.firebaseapp.com
        return databaseUrl.replace("firebaseio", "firebaseapp");
    }
}
