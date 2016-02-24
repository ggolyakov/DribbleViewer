package com.woolf.dribbleviewer.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.woolf.dribbleviewer.models.ShotData;
import com.woolf.dribbleviewer.models.ShotImagesData;

import java.util.ArrayList;
import java.util.List;


public class DribbleDatabaseManager implements DatabaseKeys {

    public static void fillDatabaseFromList(DribbleDatabaseHelper databaseHelper, List<ShotData> dataList) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        try {
            db.beginTransaction();
            ContentValues contentValues = new ContentValues();
            for (ShotData data : dataList) {
                addNewRecord(db, contentValues, data);
                contentValues.clear();
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            databaseHelper.close();
        }

    }

    public static void clearTable(DribbleDatabaseHelper databaseHelper) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.execSQL("DELETE FROM " + SHOTS_TABLE);
        databaseHelper.close();
    }

    public static List<ShotData> fillListFromDatabase(DribbleDatabaseHelper databaseHelper) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        Cursor cursor = db.query(SHOTS_TABLE, null, null, null, null, null, null);
        ArrayList<ShotData> data;
        if (cursor.moveToFirst()) {
            data = new ArrayList<>();
            do {
                data.add(parseCursor(cursor));
            } while (cursor.moveToNext());
        } else {
            return null;
        }
        return data;
    }

    private static ShotData parseCursor(Cursor cursor) {
        ShotData data = new ShotData();

        int idIndex = cursor.getColumnIndex(_ID);
        int idShotIndex = cursor.getColumnIndex(ID_SHOT);
        int titleIndex = cursor.getColumnIndex(TITLE);
        int descriptionIndex = cursor.getColumnIndex(DESCRIPTION);
        int viewsIndex = cursor.getColumnIndex(VIEWS);
        int likesIndex = cursor.getColumnIndex(LIKES);
        int commentsIndex = cursor.getColumnIndex(COMMENTS);
        int imgHDpiIndex = cursor.getColumnIndex(IMG_HI_DPI);
        int imgNormalIndex = cursor.getColumnIndex(IMG_NORMAL);
        int imgTeaserIndex = cursor.getColumnIndex(IMG_TEASER);

        data.setId(cursor.getInt(idShotIndex));
        data.setTitle(cursor.getString(titleIndex));
        data.setDescription(cursor.getString(descriptionIndex));
        data.setViewsCount(cursor.getInt(viewsIndex));
        data.setLikesCount(cursor.getInt(likesIndex));
        data.setCommentsCount(cursor.getInt(commentsIndex));
        data.setImages(new ShotImagesData(
                cursor.getString(imgHDpiIndex)
                , cursor.getString(imgNormalIndex)
                , cursor.getString(imgTeaserIndex)));
        return data;
    }


    private static void addNewRecord(SQLiteDatabase db, ContentValues contentValues, ShotData data) {
        contentValues.put(ID_SHOT, data.getId());
        addContentValues(TITLE, data.getTitle(), contentValues);
        addContentValues(DESCRIPTION, data.getDescription(), contentValues);
        contentValues.put(VIEWS, data.getViewsCount());
        contentValues.put(LIKES, data.getLikesCount());
        contentValues.put(COMMENTS, data.getCommentsCount());

        addContentValues(IMG_HI_DPI, data.getImages().getHiDpi(), contentValues);
        addContentValues(IMG_NORMAL, data.getImages().getNormal(), contentValues);
        addContentValues(IMG_TEASER, data.getImages().getTeaser(), contentValues);

        db.insert(SHOTS_TABLE, null, contentValues);
    }


    private static void addContentValues(String key, String value, ContentValues contentValues) {
        if (value == null) {
            contentValues.putNull(key);
        } else {
            contentValues.put(key, value);
        }
    }
}
