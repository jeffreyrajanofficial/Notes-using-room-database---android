package com.jr.userrooms.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.jr.userrooms.database.entities.User;
import com.jr.userrooms.repositories.UserRepository;

public class UserViewModel extends AndroidViewModel {

    private UserRepository userRepository;

    public UserViewModel(Application application) {
        super(application);
        userRepository = new UserRepository(application);
    }

    public LiveData<User> getUser(String email, String password) {
        return userRepository.getUser(email, password);
    }

    public void addUser(User user) {
        userRepository.addUser(user);
    }

    public LiveData<User> getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    public void updateUserProfile(int id, String name, String email){
        userRepository.updateUserProfile(id, name, email);
    }

    public LiveData<User> getUserById(int id) {
        return userRepository.getUserByIdInLiveData(id);
    }
}
