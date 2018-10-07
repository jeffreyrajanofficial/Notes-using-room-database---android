package com.jr.userrooms.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.jr.userrooms.database.UserRoomDatabase;
import com.jr.userrooms.database.dao.NotesDao;
import com.jr.userrooms.database.entities.User;
import com.jr.userrooms.database.entities.UserNotes;

import java.util.List;

public class NotesRepository {
    private NotesDao notesDao;
    private UserRepository userRepository;

    public NotesRepository(Application application) {
        notesDao = UserRoomDatabase.getInstance(application).getUserNotesDao();
        userRepository = new UserRepository(application);
    }

    public LiveData<List<UserNotes>> getAllNotesByUserId(int userId) {
        return notesDao.getAllNotesByUserId(userId);
    }

    public void addNotes(int id, String title, String description) {
        new PrepareNotesAsync(notesDao, userRepository, title, description, id).execute(id);
    }

    private static class PrepareNotesAsync extends AsyncTask<Integer, Void, User> {

        private UserRepository userRepository;
        private String title;
        private String description;
        private int userId;
        private NotesDao notesDao;

        private PrepareNotesAsync(NotesDao notesDao, UserRepository userRepository, String title, String description, int userId){
            this.userRepository = userRepository;
            this.title = title;
            this.description = description;
            this.userId = userId;
            this.notesDao = notesDao;
        }

        @Override
        protected User doInBackground(Integer... ints) {

            return userRepository.getUserByUserId(ints[0]);
        }

        @Override
        protected void onPostExecute(User user) {
            UserNotes userNotes = new UserNotes();
            userNotes.setCreator_name(user.getName());
            userNotes.setUserId(userId);
            userNotes.setTitle(title);
            userNotes.setDescription(description);

            new AddNewNote(notesDao).execute(userNotes);
        }
    }

    private static class AddNewNote extends AsyncTask<UserNotes, Void, Void> {

        private NotesDao notesDao;

        private AddNewNote(NotesDao notesDao){
            this.notesDao = notesDao;
        }

        @Override
        protected Void doInBackground(UserNotes... userNotes) {
            notesDao.addNotes(userNotes[0]);
            return null;
        }
    }
}
