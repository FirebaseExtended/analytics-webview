/**
 * Copyright 2016 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
