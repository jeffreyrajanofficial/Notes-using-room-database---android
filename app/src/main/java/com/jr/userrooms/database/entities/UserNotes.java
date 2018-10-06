package com.jr.userrooms.database.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "user_notes", indices = {@Index("userId")}, foreignKeys = @ForeignKey(entity = User.class,
        parentColumns = "id",
        childColumns = "userId",
        onDelete = CASCADE))
public class UserNotes {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String description;
    @ColumnInfo(name = "userId")
    private int userId;
    @ColumnInfo(name = "creator_name")
    private String creator_name;

    public String getCreator_name() {
        return creator_name;
    }

    public void setCreator_name(String creator_name) {
        this.creator_name = creator_name;
    }




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
