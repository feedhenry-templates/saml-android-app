package org.feedhenry.saml;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.feedhenry.sdk.Device;
import com.feedhenry.sdk.FH;
import com.feedhenry.sdk.FHActCallback;
import com.feedhenry.sdk.FHResponse;
import com.feedhenry.sdk.api.FHCloudRequest;

import org.feedhenry.saml.fragments.LoadingFragment;
import org.feedhenry.saml.fragments.MainFragment;
import org.feedhenry.saml.fragments.UserDataFragment;
import org.feedhenry.saml.fragments.WebViewFragment;
import org.feedhenry.saml.model.User;
import org.json.fh.JSONObject;

public class SAMLActivity extends AppCompatActivity {

    private static final String TAG = SAMLActivity.class.getName();
    private static final String TOKEN = "token";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saml);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        init();
    }

    private void init() {

        displayLoading();

        FH.init(getApplicationContext(), new FHActCallback() {
            @Override
            public void success(FHResponse fhResponse) {
                Log.d(TAG, "FH.init - success");
                SAMLActivity.this.displayMainScreen();
            }

            @Override
            public void fail(FHResponse fhResponse) {
                Log.d(TAG, "FH.init - fail");
                Log.e(TAG, fhResponse.getErrorMessage(), fhResponse.getError());
                Toast.makeText(getApplicationContext(),
                        fhResponse.getErrorMessage(), Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }

    public void retrieveSSOUrl() {

        displayLoading();

        try {

            JSONObject params = new JSONObject();
            params.put(TOKEN, Device.getDeviceId(getApplicationContext()));

            FHCloudRequest request = FH.buildCloudRequest(
                    "sso/session/login_host", "POST", null, params);
            request.executeAsync(new FHActCallback() {
                @Override
                public void success(FHResponse res) {
                    Log.d(TAG, "FHCloudRequest (login_host) - success");

                    String ssoStringURL = res.getJson().getString("sso");
                    Log.d(TAG, "SSO URL = " + ssoStringURL);
                    SAMLActivity.this.displayWebView(ssoStringURL);
                }

                @Override
                public void fail(FHResponse res) {
                    Log.d(TAG, "FHCloudRequest (login_host) - fail");
                    Log.e(TAG, res.getErrorMessage(), res.getError());
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void retrieveUserData() {

        displayLoading();

        try {

            JSONObject params = new JSONObject();
            params.put(TOKEN, Device.getDeviceId(getApplicationContext()));

            FHCloudRequest request = FH.buildCloudRequest(
                    "sso/session/valid", "POST", null, params);
            request.executeAsync(new FHActCallback() {
                @Override
                public void success(FHResponse res) {
                    Log.d(TAG, "FHCloudRequest (valid) - success");

                    User user = new User();
                    user.setFirstName(res.getJson().getString("first_name"));
                    user.setLastName(res.getJson().getString("last_name"));
                    user.setEmail(res.getJson().getString("email"));
                    user.setExpires(res.getJson().getString("expires"));

                    Log.d(TAG, user.toString());

                    SAMLActivity.this.displayUserData(user);
                }

                @Override
                public void fail(FHResponse res) {
                    Log.d(TAG, "FHCloudRequest (valid) - fail");
                    Log.e(TAG, res.getErrorMessage(), res.getError());
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void displayContent(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, fragment)
                .commit();
    }

    private void displayLoading() {
        displayContent(new LoadingFragment());
    }

    private void displayMainScreen() {
        displayContent(new MainFragment());
    }

    private void displayWebView(String ssoStringURL) {
        Bundle bundle = new Bundle();
        bundle.putString(WebViewFragment.URL, ssoStringURL);

        Fragment fragment = new WebViewFragment();
        fragment.setArguments(bundle);

        displayContent(fragment);
    }

    private void displayUserData(User user) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(UserDataFragment.USER, user);

        Fragment fragment = new UserDataFragment();
        fragment.setArguments(bundle);

        displayContent(fragment);
    }

}
