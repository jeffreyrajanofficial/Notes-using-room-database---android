package com.jr.userrooms.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.jr.userrooms.database.entities.UserNotes;

import java.util.List;

@Dao
public interface NotesDao {

    @Query("SELECT * FROM user_notes")
    List<UserNotes> getAllNotes();

    @Query("SELECT * FROM user_notes WHERE userId=:id")
    List<UserNotes> getAllNotesByUserId(int id);

    @Query("SELECT * FROM user_notes WHERE id=:id")
    List<UserNotes> getNoteById(int id);

    @Insert
    void addNotes(UserNotes... userNotes);
}
