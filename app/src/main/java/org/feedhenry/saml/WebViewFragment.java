package org.feedhenry.saml;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class WebViewFragment extends Fragment {

    public static final String URL = "URL";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_webview, null);

        String ssoStringURL = getArguments().getString(URL);

        WebView webView = (WebView) view.findViewById(R.id.ssoWebView);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // It' logged
                if (url.endsWith("login/ok")) {
                    MainActivity activity = (MainActivity) getActivity();
                    activity.displayUserData();
                }
            }
        });
        webView.loadUrl(ssoStringURL);

        return view;
    }

}
