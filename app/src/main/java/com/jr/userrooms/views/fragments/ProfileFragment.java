package com.jr.userrooms.views.fragments;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jr.userrooms.R;
import com.jr.userrooms.config.Constants;
import com.jr.userrooms.database.UserRoomDatabase;
import com.jr.userrooms.database.entities.User;
import com.jr.userrooms.utils.SharedPrefsUtils;
import com.jr.userrooms.viewmodels.UserViewModel;
import com.jr.userrooms.views.activities.HomeActivity;
import com.jr.userrooms.views.activities.LoginActivity;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {

    private TextView tv_name;
    private TextView tv_email;
    private LinearLayout ll_edit_view;
    private LinearLayout ll_profile_view;
    private MaterialButton btn_edit_save;
    private TextInputEditText ti_name;
    private TextInputEditText ti_email;
    private UserViewModel userViewModel;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);
    }

    private void init(View view) {
        tv_name = view.findViewById(R.id.tv_name);
        tv_email = view.findViewById(R.id.tv_email);
        ll_profile_view = view.findViewById(R.id.ll_profile_view);
        ll_edit_view = view.findViewById(R.id.ll_edit_view);
        btn_edit_save = view.findViewById(R.id.btn_edit_save);
        ti_name = view.findViewById(R.id.ti_name);
        ti_email = view.findViewById(R.id.ti_email);

        btn_edit_save.setOnClickListener(this);

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        getUserDetails();
    }

    private void getUserDetails() {
        userViewModel.getUserById(SharedPrefsUtils.getIntegerPreference(getContext(), Constants.USER_ID, 0))
                .observe(this, user -> {
                    tv_name.setText(user.getName());
                    ti_name.setText(user.getName());
                    tv_email.setText(user.getEmail());
                    ti_email.setText(user.getEmail());
                });
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_edit_save) {
            if(btn_edit_save.getText().equals(getString(R.string.save))) {
                updateUserProfileDetails();
            } else {
                ll_profile_view.setVisibility(View.VISIBLE);
                ll_edit_view.setVisibility(View.GONE);
                btn_edit_save.setText(getString(R.string.save));
            }
        }
    }

    private void updateUserProfileDetails() {
        if(!TextUtils.isEmpty(ti_email.getText().toString().trim())
                && !TextUtils.isEmpty(ti_name.getText().toString().trim())){
            userViewModel.updateUserProfile(SharedPrefsUtils.getIntegerPreference(getContext(), Constants.USER_ID, 0), ti_name.getText().toString().trim(), ti_email.getText().toString().trim());

            ll_profile_view.setVisibility(View.GONE);
            ll_edit_view.setVisibility(View.VISIBLE);
            btn_edit_save.setText(getString(R.string.edit_profile));
        }
    }
}
