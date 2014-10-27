package com.example.liurongchan.traingdemo.demo.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

import com.example.liurongchan.traingdemo.demo.dao.database.Column;
import com.example.liurongchan.traingdemo.demo.dao.database.SQLiteTable;
import com.example.liurongchan.traingdemo.demo.model.Diary;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liurongchan on 14/10/26.
 */
public class DiaryDataHelper extends BaseDataHelper {

    public DiaryDataHelper(Context context) {
        super(context);
    }

    @Override
    protected Uri getContentUri() {
        return DataProvider.DIARY_CONTENT_URI;
    }

    private ContentValues getContentValues(Diary diary) {
        ContentValues values = new ContentValues();
        values.put(DiaryDB.TITLE, diary.title);
        values.put(DiaryDB.CONTENT, diary.content);
        values.put(DiaryDB.CREATE_AT, diary.create_at);
        values.put(DiaryDB.MODIFY_AT, diary.modify_at);
        values.put(DiaryDB.PIC_URL, diary.pic_url);
        return values;
    }

    public List<Diary> queryAll() {
        Diary diary = null;
        List<Diary> diaries = new ArrayList<Diary>();
        Cursor cursor = query(null, null, null, null);
        if (cursor.moveToNext()) {
            diary = Diary.fromCursor(cursor);
            diaries.add(diary);
        }
        cursor.close();
        return diaries;
    }

    public Diary query(long _id) {
        Diary diary = null;
        Cursor cursor = query(null, DiaryDB._ID + "=?" ,new String[] {
                String.valueOf(_id)
        }, DiaryDB._ID + "ASC");
        if(cursor.moveToFirst()) {
            diary = Diary.fromCursor(cursor);
        }
        cursor.close();
        return diary;
    }

    public void insert(Diary diary) {
        insert(getContentValues(diary));
    }

    public void delete(Diary diary) {
        delete(getContentUri(), DiaryDB._ID + "=?" , new String[] {
                String.valueOf(diary._id)
        });
    }

    public static final class DiaryDB implements BaseColumns {
        private DiaryDB() {
        }

        public static final String TABLE_NAME = "diary";

        public static final String TITLE = "title";

        public static final String CONTENT = "content";

        public static final String CREATE_AT = "create_at";

        public static final String MODIFY_AT = "modify_at";

        public static final String PIC_URL = "pic_url";

        public static final SQLiteTable TABLE = new SQLiteTable(TABLE_NAME)
                .addColumn(TITLE, Column.DataType.TEXT)
                .addColumn(CONTENT, Column.DataType.TEXT)
                .addColumn(CREATE_AT, Column.DataType.TEXT)
                .addColumn(MODIFY_AT, Column.DataType.TEXT)
                .addColumn(PIC_URL, Column.DataType.TEXT);
    }
}
