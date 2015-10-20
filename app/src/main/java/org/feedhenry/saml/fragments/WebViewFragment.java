package org.feedhenry.saml.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.feedhenry.saml.SAMLActivity;
import org.feedhenry.saml.R;

public class WebViewFragment extends Fragment {

    public static final String URL = "URL";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        String ssoStringURL = getArguments().getString(URL);

        View view = View.inflate(container.getContext(), R.layout.fragment_webview, null);

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
                    SAMLActivity activity = (SAMLActivity) getActivity();
                    activity.retrieveUserData();
                }
            }
        });
        webView.loadUrl(ssoStringURL);

        return view;
    }

}
