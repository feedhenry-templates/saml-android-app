package org.feedhenry.saml;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        displayInit();
    }

    private void displayContent(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, fragment)
                .commit();
    }

    private void displayInit() {
        displayContent(new InitFragment());
    }

    public void displayLoading() {
        displayContent(new LoadingFragment());
    }

    public void displayMainScreen() {
        displayContent(new MainFragment());
    }

    public void displayWebView(String ssoStringURL) {
        Bundle bundle = new Bundle();
        bundle.putString(WebViewFragment.URL, ssoStringURL);

        Fragment fragment = new WebViewFragment();
        fragment.setArguments(bundle);

        displayContent(fragment);
    }

    public void retrieveUserData() {
        displayContent(new RetrieveUserDataFragment());
    }

    public void displayUserData(User user) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ShowUserDataFragment.USER, user);

        Fragment fragment = new ShowUserDataFragment();
        fragment.setArguments(bundle);

        displayContent(fragment);
    }

}
