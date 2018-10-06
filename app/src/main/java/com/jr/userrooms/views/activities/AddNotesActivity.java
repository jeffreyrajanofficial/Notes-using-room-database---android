package com.jr.userrooms.views.activities;

import android.support.design.button.MaterialButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.jr.userrooms.R;
import com.jr.userrooms.config.Constants;
import com.jr.userrooms.database.UserRoomDatabase;
import com.jr.userrooms.database.entities.User;
import com.jr.userrooms.database.entities.UserNotes;
import com.jr.userrooms.utils.SharedPrefsUtils;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AddNotesActivity extends AppCompatActivity implements View.OnClickListener {

    private MaterialButton btn_save;
    private EditText et_title;
    private EditText et_description;
    private UserRoomDatabase userRoomDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);

        initView();
    }

    private void initView() {
        btn_save = findViewById(R.id.btn_save);
        et_title = findViewById(R.id.et_title);
        et_description = findViewById(R.id.et_description);

        btn_save.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_save) {
            if (!TextUtils.isEmpty(et_title.getText().toString().trim()) &&
                    !TextUtils.isEmpty(et_title.getText().toString().trim())) {
                Executor myExecutor = Executors.newSingleThreadExecutor();
                myExecutor.execute(() -> {
                    userRoomDatabase = UserRoomDatabase.getInstance(AddNotesActivity.this);
                    User user = userRoomDatabase.getUserDao().getUserById(SharedPrefsUtils.getIntegerPreference(AddNotesActivity.this, Constants.USER_ID, 0));
                    Log.e("login_user==", new Gson().toJson(user));
                    UserNotes userNotes = new UserNotes();
                    userNotes.setTitle(et_title.getText().toString().trim());
                    userNotes.setDescription(et_description.getText().toString().trim());
                    userNotes.setCreator_name(user.getName());
                    userNotes.setUserId(user.getId());
                    userRoomDatabase.getUserNotesDao().addNotes(userNotes);

                    Log.e("Notes====", new Gson().toJson(userRoomDatabase.getUserNotesDao().getAllNotes()));
                    finish();

                });
            }
        }
    }
}
