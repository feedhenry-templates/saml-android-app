package org.feedhenry.saml;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.feedhenry.sdk.FH;
import com.feedhenry.sdk.FHActCallback;
import com.feedhenry.sdk.FHResponse;

public class LoadingFragment extends Fragment {

    private static final String TAG = LoadingFragment.class.getName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_loading, null);

        FH.init(getContext(), new FHActCallback() {
            @Override
            public void success(FHResponse fhResponse) {
                Log.d(TAG, "FH.init - success");
            }

            @Override
            public void fail(FHResponse fhResponse) {
                Log.d(TAG, "FH.init - fail");
                Log.e(TAG, fhResponse.getErrorMessage(), fhResponse.getError());
                Toast.makeText(getContext(),
                        fhResponse.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

}
