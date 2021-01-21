package ru.locarus.androidtrackerapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class PointsDbOpenHelper extends SQLiteOpenHelper {
    public PointsDbOpenHelper( Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_POINTS_TABLE = "CREATE TABLE " + Constants.TABLE_NAME + "("
                + Constants._ID + " INTEGER PRIMARY KEY,"
                + Constants.LATITUDE + " DOUBLE NOT NULL,"
                + Constants.LONGITUDE + " DOUBLE NOT NULL,"
                + Constants.SPEED + " FLOAT NOT NULL,"
                + Constants.TIME + " DOUBLE NOT NULL,"
                + Constants.ALTITUDE + " DOUBLE NOT NULL" + ")";
        db.execSQL(CREATE_POINTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Constants.DATABASE_NAME);
        onCreate(db);
    }
}
