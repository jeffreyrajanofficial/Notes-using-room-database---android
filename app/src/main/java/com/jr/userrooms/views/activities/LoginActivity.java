
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
import com.jr.userrooms.config.Constants;
import com.jr.userrooms.database.UserRoomDatabase;
import com.jr.userrooms.database.entities.User;
import com.jr.userrooms.utils.SharedPrefsUtils;
import com.jr.userrooms.viewmodels.UserViewModel;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextInputEditText ti_email;
    private TextInputEditText ti_pwd;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
    }

    private void init() {
        TextView tv_register = findViewById(R.id.tv_register);
        MaterialButton btn_login = findViewById(R.id.btn_login);
        ti_email = findViewById(R.id.ti_email);
        ti_pwd = findViewById(R.id.ti_pwd);

        tv_register.setOnClickListener(this);
        btn_login.setOnClickListener(this);

        //Setup the userViewModel
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_register:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;

            case R.id.btn_login:
                userLogin();
                break;

            default:
                break;
        }
    }

    /*
     * Validate all the field, check whether the user is available and then allow the user to login.
     * */
    private void userLogin() {
        if (validate()) {
            userViewModel.getUser(ti_email.getText().toString().trim().toLowerCase(), ti_pwd.getText().toString().trim())
                    .observe(this, user -> {
                        Log.e("user-----", new Gson().toJson(user));
                        if (user == null) {
                            Toast.makeText(LoginActivity.this, getString(R.string.invalid_user), Toast.LENGTH_SHORT).show();
                        } else {
                            SharedPrefsUtils.setIntegerPreference(LoginActivity.this, Constants.USER_ID, user.getId());
                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                        }
                    });
        }
    }

    /*
     * Validating email and password, returns false if any of the validation fails
     * */
    private boolean validate() {
        if (TextUtils.isEmpty(ti_email.getText().toString().trim())) {
            Toast.makeText(this, getString(R.string.invalid_email), Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(ti_pwd.getText().toString().trim())) {
            Toast.makeText(this, getString(R.string.invalid_password), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
