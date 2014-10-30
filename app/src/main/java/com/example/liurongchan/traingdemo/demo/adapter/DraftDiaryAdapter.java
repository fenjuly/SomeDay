package com.example.liurongchan.traingdemo.demo.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.ImageView;

import com.daimajia.swipe.SwipeLayout;
import com.example.liurongchan.traingdemo.demo.EditActivity;
import com.example.liurongchan.traingdemo.demo.R;
import com.example.liurongchan.traingdemo.demo.model.Diary;


/**
 * Created by liurongchan on 14/10/29.
 */
public class DraftDiaryAdapter extends DiaryAdapter {

    public DraftDiaryAdapter(Context context, Cursor cursor, FragmentManager fragmentManager, DateChangedListener dateChangedListener) {
        super(context, cursor, fragmentManager, dateChangedListener);
    }
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        super.dealView(view, context, cursor);
        ImageView edit = (ImageView) view.findViewById(R.id.redo);
        edit.setVisibility(View.VISIBLE);
        edit.setOnClickListener(new SwipeLayout.OnClickListener() {
            private Diary d = diary;
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, EditActivity.class);
                intent.putExtra("title", d.title);
                intent.putExtra("content", d.content);
                intent.putExtra("create_at", d.create_at);
                intent.putExtra("_id", d._id);
                mContext.startActivity(intent);
            }
        });
    }

}
