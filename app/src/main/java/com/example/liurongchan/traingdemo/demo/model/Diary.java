package com.example.liurongchan.traingdemo.demo.model;

import android.database.Cursor;

import com.example.liurongchan.traingdemo.demo.dao.DiaryDataHelper;

import java.util.HashMap;

/**
 * Created by liurongchan on 14/10/26.
 */
public class Diary {

    private static final HashMap<Long, Diary> CACHE = new HashMap<Long, Diary>();

    public long _id;

    public String title;

    public String content;

    public String create_at;

    public String modify_at;

    public String pic_url;

    public Diary(long _id, String title, String content, String create_at, String modify_at, String pic_url) {
        this._id = _id;
        this.title = title;
        this.content = content;
        this.create_at = create_at;
        this.modify_at = modify_at;
        this.pic_url = pic_url;
    }

    private static void addToCache(Diary diary) {
        CACHE.put(diary._id, diary);
    }

    private static Diary getFromCache(long _id) {
        return CACHE.get(_id);
    }

    public static Diary fromCursor(Cursor cursor) {
        long _id = cursor.getInt(cursor.getColumnIndex(DiaryDataHelper.DiaryDB._ID));
        Diary diary = getFromCache(_id);
        if (diary == null) {
            return diary;
        }
        diary = new Diary(_id, cursor.getString(cursor.getColumnIndex(DiaryDataHelper.DiaryDB.TITLE)),
                cursor.getString(cursor.getColumnIndex(DiaryDataHelper.DiaryDB.CONTENT)),
                cursor.getString(cursor.getColumnIndex(DiaryDataHelper.DiaryDB.CREATE_AT)),
                cursor.getString(cursor.getColumnIndex(DiaryDataHelper.DiaryDB.MODIFY_AT)),
                cursor.getString(cursor.getColumnIndex(DiaryDataHelper.DiaryDB.PIC_URL)));
        addToCache(diary);
        return diary;
    }

}
