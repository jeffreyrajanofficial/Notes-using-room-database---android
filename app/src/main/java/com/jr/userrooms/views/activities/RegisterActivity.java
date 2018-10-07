package com.jr.userrooms.views.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jr.userrooms.R;
import com.jr.userrooms.database.UserRoomDatabase;
import com.jr.userrooms.database.entities.User;
import com.jr.userrooms.viewmodels.UserViewModel;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private TextInputEditText ti_email;
    private TextInputEditText ti_pwd;
    private TextInputEditText ti_c_pwd;
    private TextInputEditText ti_name;

    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();
    }

    private void init() {
        TextView tv_login = findViewById(R.id.tv_login);
        MaterialButton btn_Register = findViewById(R.id.btn_Register);
        ti_email = findViewById(R.id.ti_email);
        ti_pwd = findViewById(R.id.ti_pwd);
        ti_c_pwd = findViewById(R.id.ti_c_pwd);
        ti_name = findViewById(R.id.ti_name);

        tv_login.setOnClickListener(this);
        btn_Register.setOnClickListener(this);

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_login:
                startActivity(new Intent(RegisterActivity.this, RegisterActivity.class));
                break;

            case R.id.btn_Register:
                if (validateFields()) {
                    registerUser();
                }
                break;

            default:
                break;
        }
    }

    private void registerUser() {
        userViewModel.getUserByEmail(ti_email.getText().toString().trim().toLowerCase())
                .observe(this, user -> {
                    Log.e("user===", new Gson().toJson(user));
                    if (user == null) {
                        User newUser = new User();
                        newUser.setName(ti_name.getText().toString().trim());
                        newUser.setEmail(ti_email.getText().toString().trim().toLowerCase());
                        newUser.setPassword(ti_pwd.getText().toString().trim());
                        userViewModel.addUser(newUser);
                        Toast.makeText(RegisterActivity.this, getString(R.string.reg_success), Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(RegisterActivity.this, getString(R.string.user_exist), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private boolean validateFields() {
        if (TextUtils.isEmpty(ti_name.getText().toString().trim())) {
            Toast.makeText(this, getString(R.string.invalid_name), Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(ti_email.getText().toString().trim())) {
            Toast.makeText(this, getString(R.string.invalid_email), Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(ti_pwd.getText().toString().trim())) {
            Toast.makeText(this, getString(R.string.invalid_password), Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(ti_c_pwd.getText().toString().trim())) {
            Toast.makeText(this, getString(R.string.invalid_confirm_password), Toast.LENGTH_SHORT).show();
            return false;
        } else if (!ti_pwd.getText().toString().trim().matches(ti_c_pwd.getText().toString().trim())) {
            Toast.makeText(this, getString(R.string.password_mismatch), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
