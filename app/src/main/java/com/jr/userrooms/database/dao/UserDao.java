package com.jr.userrooms.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.jr.userrooms.database.entities.User;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM users")
    List<User> getAllUser();

    @Insert
    void addUser(User... user);

    @Query("SELECT * FROM users WHERE email=:email")
    LiveData<User> getUser(String email);

    @Query("SELECT * FROM users WHERE email=:email AND password=:password")
    LiveData<User> loginWithEmailAndPassword(String email, String password);

    @Query("SELECT * FROM users WHERE id=:id")
    User getUserById(int id);

    @Query("SELECT * FROM users WHERE id=:id")
    LiveData<User> getUserByIdInLiveData(int id);

    @Query("UPDATE users SET name= :name, email= :email WHERE id=:id")
    void updateUserById(int id, String name, String email);
}
