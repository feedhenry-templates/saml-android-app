/**
 * Copyright (c) 2015 FeedHenry Ltd, All Rights Reserved.
 *
 * Please refer to your contract with FeedHenry for the software license agreement.
 * If you do not have a contract, you do not have a license to use this software.
 */
package org.feedhenry.saml.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.feedhenry.saml.R;
import org.feedhenry.saml.SAMLActivity;

public class MainFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        final SAMLActivity activity = (SAMLActivity) getActivity();

        View view = View.inflate(getActivity(), R.layout.fragment_main, null);

        Button signinButton = (Button) view.findViewById(R.id.signin);
        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.retrieveSSOUrl();
            }
        });

        return view;

    }

}
