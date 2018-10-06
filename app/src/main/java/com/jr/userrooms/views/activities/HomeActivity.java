package com.jr.userrooms.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jr.userrooms.R;
import com.jr.userrooms.views.fragments.NotesFragment;
import com.jr.userrooms.views.fragments.ProfileFragment;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private NotesFragment notesFragment;
    private TextView tv_title;
    private ImageButton ib_last_btn;
    private Toolbar toolbar;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    setFragment(notesFragment);
                    ib_last_btn.setVisibility(View.VISIBLE);
                    setToolbarTitle(getString(R.string.notes));
                    return true;
                case R.id.navigation_dashboard:
                    setFragment(new ProfileFragment());
                    ib_last_btn.setVisibility(View.GONE);
                    setToolbarTitle(getString(R.string.profile));
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        init();
    }

    private void init() {
        BottomNavigationView navigation = findViewById(R.id.navigation);
        toolbar = findViewById(R.id.toolbar);
        tv_title = findViewById(R.id.tv_title);
        ib_last_btn = findViewById(R.id.ib_last_btn);

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        ib_last_btn.setOnClickListener(this);

        notesFragment = new NotesFragment();

        setFragment(notesFragment);
        setToolbar();
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        setToolbarTitle(getString(R.string.notes));
    }

    private void setToolbarTitle(String title) {
        tv_title.setText(title);
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment).commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_last_btn:
                startActivity(new Intent(HomeActivity.this, AddNotesActivity.class));
                break;
            default:
        }
    }
}
