package com.jr.userrooms.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.NonNull;

import com.jr.userrooms.database.dao.NotesDao;
import com.jr.userrooms.database.dao.UserDao;
import com.jr.userrooms.database.entities.User;
import com.jr.userrooms.database.entities.UserNotes;

@Database(entities = {User.class, UserNotes.class}, version = 4, exportSchema = false)
public abstract class UserRoomDatabase extends RoomDatabase {

    private static final String DB_NAME = "UserRoom.db";
    private static volatile UserRoomDatabase instance;

    public abstract UserDao getUserDao();
    public abstract NotesDao getUserNotesDao();

    public static synchronized UserRoomDatabase getInstance(Context context) {
        if(instance == null) {
            instance = createRoom(context);
        }

        return instance;
    }

    private static UserRoomDatabase createRoom(Context context) {
        return Room.databaseBuilder(context, UserRoomDatabase.class, DB_NAME).addMigrations(MIGRATION_2_3).build();
    }

    private static final Migration MIGRATION_2_3 = new Migration(3, 4) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE user_notes ADD COLUMN creator_name TEXT");
        }
    };

}
