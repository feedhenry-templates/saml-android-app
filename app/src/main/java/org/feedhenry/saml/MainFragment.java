package org.feedhenry.saml;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.feedhenry.sdk.Device;
import com.feedhenry.sdk.FH;
import com.feedhenry.sdk.FHActCallback;
import com.feedhenry.sdk.FHResponse;
import com.feedhenry.sdk.api.FHCloudRequest;

public class MainFragment extends Fragment {

    private static final String TAG = MainFragment.class.getName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, null);

        Button signinButton = (Button) view.findViewById(R.id.signin);
        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLoginWebView();
            }
        });

        return view;
    }

    private void openLoginWebView() {

        final MainActivity activity = (MainActivity) getActivity();
        activity.displayLoading();

        try {

            org.json.fh.JSONObject params = new org.json.fh.JSONObject();
            params.put("token", Device.getDeviceId(getContext()));

            FHCloudRequest request = FH.buildCloudRequest("sso/session/login_host", "POST", null, params);
            request.executeAsync(new FHActCallback() {
                @Override
                public void success(FHResponse res) {
                    Log.d(TAG, res.getErrorMessage(), res.getError());

                    String ssoStringURL = res.getJson().getString("sso");
                    Log.d(TAG, "SSO URL = " + ssoStringURL);
                    activity.displayWebView(ssoStringURL);
                }

                @Override
                public void fail(FHResponse res) {
                    Log.e(TAG, res.getErrorMessage(), res.getError());
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
