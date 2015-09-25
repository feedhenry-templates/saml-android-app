package org.feedhenry.saml;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.feedhenry.sdk.Device;
import com.feedhenry.sdk.FH;
import com.feedhenry.sdk.FHActCallback;
import com.feedhenry.sdk.FHResponse;
import com.feedhenry.sdk.api.FHCloudRequest;

import org.json.fh.JSONObject;

public class RetrieveUserDataFragment extends Fragment {

    private static final String TAG = RetrieveUserDataFragment.class.getName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loading, null);

        try {

            JSONObject params = new JSONObject();
            params.put("token", Device.getDeviceId(getContext()));

            FHCloudRequest request = FH.buildCloudRequest("sso/session/valid", "POST", null, params);
            request.executeAsync(new FHActCallback() {
                @Override
                public void success(FHResponse res) {
                    User user = parseUserData(res.getJson());
                    Log.d(TAG, user.toString());

                    MainActivity activity = (MainActivity) getActivity();
                    activity.displayUserData(user);
                }

                @Override
                public void fail(FHResponse res) {
                    Log.e(TAG, res.getErrorMessage(), res.getError());
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    private User parseUserData(JSONObject json) {
        User user = new User();
        user.setFirstName(json.getString("first_name"));
        user.setLastName(json.getString("last_name"));
        user.setEmail(json.getString("email"));
        user.setExpires(json.getString("expires"));
        return user;
    }

}
