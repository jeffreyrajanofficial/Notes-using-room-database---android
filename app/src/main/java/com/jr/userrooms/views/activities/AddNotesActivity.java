package com.jr.userrooms.views.activities;

import android.arch.lifecycle.ViewModelProviders;
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
import com.jr.userrooms.viewmodels.NotesViewModel;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AddNotesActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_title;
    private EditText et_description;
    private NotesViewModel notesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);

        initView();
    }

    private void initView() {
        MaterialButton btn_save = findViewById(R.id.btn_save);
        et_title = findViewById(R.id.et_title);
        et_description = findViewById(R.id.et_description);

        btn_save.setOnClickListener(this);

        notesViewModel = ViewModelProviders.of(this).get(NotesViewModel.class);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_save) {
            if (!TextUtils.isEmpty(et_title.getText().toString().trim()) &&
                    !TextUtils.isEmpty(et_title.getText().toString().trim())) {
                notesViewModel
                        .addNotes(SharedPrefsUtils
                                        .getIntegerPreference(AddNotesActivity.this,
                                                Constants.USER_ID,
                                                1),
                                et_title.getText().toString().trim(),
                                et_description.getText().toString().trim());
                finish();
            }
        }
    }
}
