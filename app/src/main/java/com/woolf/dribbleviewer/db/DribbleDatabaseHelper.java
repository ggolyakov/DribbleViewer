package com.woolf.dribbleviewer.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.woolf.dribbleviewer.models.ShotData;

import java.util.List;

import rx.Observable;


public class DribbleDatabaseHelper extends SQLiteOpenHelper implements DatabaseKeys {

    private static final String DATABASE_NAME = "dribble.db";
    private static final int DATABASE_VERSION = 1;

    private static DribbleDatabaseHelper mInstance;

    public static DribbleDatabaseHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DribbleDatabaseHelper(context);
        }
        return mInstance;
    }

    private DribbleDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        createItemsTable(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int j) {

    }

    private void createItemsTable(SQLiteDatabase db) {
        db.execSQL(CREATE_SHOTS_TABLE);
    }


    public void addValues(List<ShotData> shotDataList){
         DribbleDatabaseManager.fillDatabaseFromList(mInstance, shotDataList);
    }

    public Observable<List<ShotData>> loadFromDatabase(){
        return DribbleDatabaseManager.getList(mInstance);
    }

    public void clearShotsTable(){
         DribbleDatabaseManager.clearTable(mInstance);
    }



    public static void destroy(){
        mInstance = null;
    }


}
