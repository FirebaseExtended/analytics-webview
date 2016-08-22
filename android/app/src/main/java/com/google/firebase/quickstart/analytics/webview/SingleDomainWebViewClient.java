package com.google.firebase.quickstart.analytics.webview;

import android.net.Uri;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * WebViewClient that restricts URLs to a single domain.
 */
@SuppressWarnings("deprecation")
public class SingleDomainWebViewClient extends WebViewClient {

    private static final String TAG = "SingleDomain";

    private Uri mDomainUrl;

    public SingleDomainWebViewClient(String domainUrl) {
        mDomainUrl = Uri.parse(domainUrl);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView webView, String url) {
        Uri targetUrl = Uri.parse(url);
        if (!targetUrl.getHost().equals(mDomainUrl.getHost())) {
            // The WebView is trying to load a URL outside the specified domain,
            // override and ignore this request for security.
            Log.d(TAG, "Blocking request to " + url);

            // Reload home page
            webView.loadUrl(mDomainUrl.toString());
            return true;
        }

        return false;
    }


}
