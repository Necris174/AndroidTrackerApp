package ru.locarus.androidtrackerapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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
                + Constants.LATITUDE + " REAL NOT NULL,"
                + Constants.LONGITUDE + " REAL NOT NULL,"
                + Constants.SPEED + " REAL NOT NULL,"
                + Constants.TIME + " REAL NOT NULL,"
                + Constants.ALTITUDE + " REAL NOT NULL" + ")";
        db.execSQL(CREATE_POINTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Constants.DATABASE_NAME);
        onCreate(db);
    }
    public void addPoint (Point point){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.LATITUDE, point.getLatitude());
        contentValues.put(Constants.LONGITUDE, point.getLongitude());
        contentValues.put(Constants.SPEED, point.getSpeed());
        contentValues.put(Constants.TIME, point.getTime());
        contentValues.put(Constants.ALTITUDE, point.getAltitude());
        db.insert(Constants.TABLE_NAME,null,contentValues);
        db.close();
    }
    public Point getPoint(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Constants.TABLE_NAME, new String[] {Constants._ID, Constants.LATITUDE,
                        Constants.LONGITUDE,Constants.SPEED,Constants.TIME,Constants.ALTITUDE }, Constants._ID + "=?", new String[] {String.valueOf(id)},
                null, null,
                null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        cursor.close();
        return new Point(Integer.parseInt(cursor.getString(0)),
                Double.parseDouble(cursor.getString(1)),
                Float.parseFloat(cursor.getString(2)),
                Double.parseDouble(cursor.getString(3)),
                Double.parseDouble(cursor.getString(4)));
    }

}
