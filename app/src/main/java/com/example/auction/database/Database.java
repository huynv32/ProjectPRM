package com.example.auction.database;

import android.content.ContentValues;
import android.content.Context;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.auction.model.AlarmModel;

import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

import java.util.ArrayList;


//@androidx.room.Database(entities = {User.class, Product.class}, version = 1)
public class Database extends SQLiteOpenHelper {
    public static final String DBNAME = "Alarm.sqlite";

    public static final String TABLE_NAME = "Alarm";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "time";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_VALUE = "value";

    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, @Nullable DatabaseErrorHandler errorHandler) {
        super(context, DBNAME, factory, 1, errorHandler);
    }


    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        String sqlCreateTable =
                "CREATE TABLE " + TABLE_NAME + "(" +
                        COLUMN_ID + " INTEGER PRIMARY KEY," +
                        COLUMN_NAME + " TEXT," +
                        COLUMN_DESCRIPTION + " TEXT," +
                        COLUMN_VALUE + " INTEGER );";
        System.out.println(sqlCreateTable);
        MyDB.execSQL(sqlCreateTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long insertDB(AlarmModel alarmModel) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("time", alarmModel.getTime());
        contentValues.put("description", alarmModel.getDescription());
        if (alarmModel.isValue() == true) {
            contentValues.put("value", 1);
        } else {
            contentValues.put("value", 0);
        }
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.insert(TABLE_NAME, null, contentValues);
        db.close();
        return result;
    }


    public ArrayList<AlarmModel> selectAll() {
        ArrayList<AlarmModel> ret = new ArrayList();
        try {

            SQLiteDatabase db = this.getWritableDatabase();
            String sql = "Select * from " + TABLE_NAME;
            Cursor cursor = db.rawQuery(sql, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    AlarmModel dic = new AlarmModel();
                    dic.setId(Integer.parseInt(cursor.getString(0)));
                    dic.setTime(cursor.getString(1));
                    dic.setDescription(cursor.getString(2));
                    if (Integer.parseInt(cursor.getString(3)) == 1) {
                        dic.setValue(true);
                    } else {
                        dic.setValue(false);
                    }
                    ret.add(dic);
                    cursor.moveToNext();
                } while (!cursor.isAfterLast());
                cursor.close();
                db.close();
            }
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public Boolean Login(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from users where email =? and password= ?", new String[]{email, password});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean UpdateClock(int id, int b) {
        SQLiteDatabase db = this.getWritableDatabase();
        String countQuery = "Update Alarm set  value = " + b + " where id = " + id;
        SQLiteStatement updateStatement = db.compileStatement("Update Alarm set `value`=? WHERE `id`=?");
        updateStatement.bindLong(1, b);
        updateStatement.bindLong(2, id);
        return true;
    }

}
