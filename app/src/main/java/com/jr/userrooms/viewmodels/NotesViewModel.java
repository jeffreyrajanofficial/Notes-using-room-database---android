package com.jr.userrooms.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.jr.userrooms.config.Constants;
import com.jr.userrooms.database.entities.UserNotes;
import com.jr.userrooms.repositories.NotesRepository;
import com.jr.userrooms.repositories.UserRepository;
import com.jr.userrooms.utils.SharedPrefsUtils;
import com.jr.userrooms.views.activities.AddNotesActivity;

import java.util.List;

public class NotesViewModel extends AndroidViewModel {

    private NotesRepository notesRepository;
    private UserRepository userRepository;


    public NotesViewModel(@NonNull Application application) {
        super(application);
        notesRepository = new NotesRepository(application);

    }

    public LiveData<List<UserNotes>> getAllNotesByUserId(int userId) {
        return notesRepository.getAllNotesByUserId(userId);
    }

    public void addNotes(int id, String title, String description) {
        notesRepository.addNotes(id, title, description);
    }

}
