package com.jr.userrooms.views.fragments;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.jr.userrooms.R;
import com.jr.userrooms.adapter.NotesAdapter;
import com.jr.userrooms.config.Constants;
import com.jr.userrooms.database.UserRoomDatabase;
import com.jr.userrooms.database.entities.UserNotes;
import com.jr.userrooms.utils.SharedPrefsUtils;
import com.jr.userrooms.viewmodels.NotesViewModel;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotesFragment extends Fragment {

    private RecyclerView rv_notes;
    private NotesViewModel notesViewModel;


    public NotesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
    }

    @Override
    public void onResume() {
        super.onResume();

//        getAllNotes();
    }

    private void initView(View view) {
        rv_notes = view.findViewById(R.id.rv_notes);

        notesViewModel = ViewModelProviders.of(this).get(NotesViewModel.class);

        notesViewModel.getAllNotesByUserId(SharedPrefsUtils.getIntegerPreference(getActivity(), Constants.USER_ID, 1))
                .observe(this, userNotes -> {
                    rv_notes.setAdapter(new NotesAdapter(getActivity(), userNotes));
                });

       // getAllNotes();
    }

    /*private void getAllNotes() {
        Executor myExecutor = Executors.newSingleThreadExecutor();
        myExecutor.execute(() -> {
            List<UserNotes> userNotes = UserRoomDatabase
                    .getInstance(getActivity())
                    .getUserNotesDao()
                    .getAllNotesByUserId(SharedPrefsUtils.getIntegerPreference(getActivity(), Constants.USER_ID, 1));

            Log.e("myNotes---", new Gson().toJson(userNotes));

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    rv_notes.setAdapter(new NotesAdapter(getActivity(), userNotes));
                }
            });
        });
    }*/
}
