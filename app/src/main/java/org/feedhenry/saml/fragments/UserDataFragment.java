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
import android.widget.TextView;

import org.feedhenry.saml.R;
import org.feedhenry.saml.SAMLActivity;
import org.feedhenry.saml.model.User;

public class UserDataFragment extends Fragment {

    public static final String USER = "USER";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        User user = (User) getArguments().getSerializable(USER);

        View view = View.inflate(getActivity(), R.layout.fragment_user_data, null);

        Button siginButton = (Button) view.findViewById(R.id.signin);
        siginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SAMLActivity activity = (SAMLActivity) getActivity();
                activity.retrieveSSOUrl();
            }
        });

        if(user != null) {

            TextView name = (TextView) view.findViewById(R.id.name);
            name.setText(getString(R.string.full_name, user.getFirstName(), user.getLastName()));

            TextView email = (TextView) view.findViewById(R.id.email);
            email.setText(user.getEmail());

            TextView session = (TextView) view.findViewById(R.id.session);
            session.setText(user.getExpires());

        }

        return view;
    }

}
