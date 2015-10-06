package com.bxcalculator.thecalculator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHandler extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "test2.db";
    private static final String TABLE_NAME = "test";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TIME = "time";
    private static final String COLUMN_INPUT = "input";
    private static final String COLUMN_RESULT = "result";

    SQLiteDatabase db;
    String dbString;

    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TIME + " TEXT, " +
                COLUMN_INPUT + " TEXT, " +
                COLUMN_RESULT + " TEXT " +
                ");";
        db.execSQL(query);
        this.db = db;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(query);
        onCreate(db);
    }

    public void saveResult(Result result){
        //This is a list of values

        /*String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        int count = cursor.getCount();*/
        //values.put(COLUMN_ID, count);

        ContentValues values = new ContentValues();
        values.put(COLUMN_TIME, result.get_time());
        values.put(COLUMN_INPUT, result.get_input());
        values.put(COLUMN_RESULT, result.get_result());
        db = this.getWritableDatabase();

        //Insert a new product or row into your database
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public String databaseToString(){
        dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        //* means select every column (or everything), 1 means every row
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE 1";

        //Cursor points to a location in your results
        Cursor c = db.rawQuery(query, null);

        //Move to first row in your results
        c.moveToFirst();

        while(!c.isAfterLast()){
            if(c.getString(c.getColumnIndex("input")) != null){

                dbString += c.getString(c.getColumnIndex("time")) + ", " + c.getString(c.getColumnIndex("input")) +
                        " = " + c.getString(c.getColumnIndex("result"));
                dbString += "\n";
            }
            c.moveToNext();
        }
        c.close();
        db.close();
        return dbString;
    }
}
