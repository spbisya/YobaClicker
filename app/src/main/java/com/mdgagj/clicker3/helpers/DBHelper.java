package com.mdgagj.clicker3.helpers;

/**
 * Project YobaClicker. Created by gwa on 7/23/16.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mdgagj.clicker3.models.Achievement;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    //Achievements
    public static final String DATABASE_NAME = "Ach.db";
    public static final String ACHIEVEMENTS_TABLE_NAME = "achievements";
    public static final String ACHIEVEMENTS_COLUMN_ID = "id";
    public static final String ACHIEVEMENTS_COLUMN_NAME = "name";
    public static final String ACHIEVEMENTS_COLUMN_DESCRIPTION = "description";
    public static final String ACHIEVEMENTS_COLUMN_IMAGE = "image";
    public static final String ACHIEVEMENTS_COLUMN_STATUS = "status";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL("create table " + ACHIEVEMENTS_TABLE_NAME + " " +
                "(id integer primary key, name text, description text, image integer, status text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS " + ACHIEVEMENTS_TABLE_NAME);
        onCreate(db);
    }

    public boolean insertAchievement(Achievement achievement) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ACHIEVEMENTS_COLUMN_NAME, achievement.getName());
        contentValues.put(ACHIEVEMENTS_COLUMN_DESCRIPTION, achievement.getDescription());
        contentValues.put(ACHIEVEMENTS_COLUMN_IMAGE, achievement.getImage());
        contentValues.put(ACHIEVEMENTS_COLUMN_STATUS, achievement.getStatus());
        db.insert(ACHIEVEMENTS_TABLE_NAME, null, contentValues);
        return true;
    }

    public boolean updateAchievement(Achievement achievement) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ACHIEVEMENTS_COLUMN_NAME, achievement.getName());
        contentValues.put(ACHIEVEMENTS_COLUMN_DESCRIPTION, achievement.getDescription());
        contentValues.put(ACHIEVEMENTS_COLUMN_IMAGE, achievement.getImage());
        contentValues.put(ACHIEVEMENTS_COLUMN_STATUS, achievement.getStatus());
        db.update(ACHIEVEMENTS_TABLE_NAME, contentValues, "id = ? ", new String[]{Integer.toString(achievement.getId())});
        return true;
    }

    public ArrayList<Achievement> getAllAchievements() {
        ArrayList<Achievement> array_list = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + ACHIEVEMENTS_TABLE_NAME, null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            Achievement achievement = new Achievement();
            achievement.setId(res.getInt(res.getColumnIndex(ACHIEVEMENTS_COLUMN_ID)));
            achievement.setName(res.getString(res.getColumnIndex(ACHIEVEMENTS_COLUMN_NAME)));
            achievement.setDescription(res.getString(res.getColumnIndex(ACHIEVEMENTS_COLUMN_DESCRIPTION)));
            achievement.setImage(res.getInt(res.getColumnIndex(ACHIEVEMENTS_COLUMN_IMAGE)));
            achievement.setStatus(res.getString(res.getColumnIndex(ACHIEVEMENTS_COLUMN_STATUS)));
            array_list.add(achievement);
            res.moveToNext();
        }
        return array_list;
    }

}