package com.jr.userrooms.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.jr.userrooms.database.UserRoomDatabase;
import com.jr.userrooms.database.dao.UserDao;
import com.jr.userrooms.database.entities.User;

public class UserRepository {

    private UserDao userDao;

    public UserRepository(Application application) {
        userDao = UserRoomDatabase.getInstance(application).getUserDao();
    }

    public LiveData<User> getUser(String email, String password) {
        return userDao.loginWithEmailAndPassword(email, password);
    }

    public LiveData<User> getUserByEmail(String email) {
        return userDao.getUser(email);
    }

    public void addUser(User user){
        new AddUserAsyncTask(userDao).execute(user);
    }

    public User getUserByUserId(int id) {
        return userDao.getUserById(id);
    }

    public LiveData<User> getUserByIdInLiveData(int id) {
        return userDao.getUserByIdInLiveData(id);
    }

    public void updateUserProfile(int id, String name, String email) {
        new UpdateUserProfileTask(userDao, name, email).execute(id);
    }

    private static class UpdateUserProfileTask extends AsyncTask<Integer, Void, Void> {
        private UserDao userDao;
        private String name;
        private String email;

        private UpdateUserProfileTask(UserDao userDao, String name, String email) {
            this.userDao = userDao;
            this.name = name;
            this.email = email;
        }

        @Override
        protected Void doInBackground(Integer... ids) {
            userDao.updateUserById(ids[0], name, email);
            return null;
        }
    }

    private static class AddUserAsyncTask extends AsyncTask<User, Void, Void> {

        private UserDao userDao;

        private AddUserAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.addUser(users[0]);
            return null;
        }
    }
}
