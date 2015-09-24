package org.feedhenry.saml;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        displayLoading();
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

}
