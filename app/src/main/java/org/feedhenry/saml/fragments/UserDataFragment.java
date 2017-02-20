/**
 * Copyright 2015 Red Hat, Inc., and individual contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
            name.setText(user.getName());

            TextView email = (TextView) view.findViewById(R.id.email);
            email.setText(user.getEmail());

            TextView session = (TextView) view.findViewById(R.id.session);
            session.setText(user.getExpires());

        }

        return view;
    }

}
