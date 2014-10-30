package com.example.liurongchan.traingdemo.demo.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.swipe.adapters.CursorSwipeAdapter;
import com.example.liurongchan.traingdemo.demo.App;
import com.example.liurongchan.traingdemo.demo.DiaryActivity;
import com.example.liurongchan.traingdemo.demo.R;
import com.example.liurongchan.traingdemo.demo.dao.DiaryDataHelper;
import com.example.liurongchan.traingdemo.demo.model.Diary;
import com.r0adkll.postoffice.PostOffice;
import com.r0adkll.postoffice.model.Design;

/**
 * Created by liurongchan on 14/10/28.
 */
public class DiaryAdapter extends CursorSwipeAdapter {

    Context mContext;
    Diary diary;
    FragmentManager fragmentManager;
    DiaryDataHelper helper;
    DateChangedListener dateChangedListener;
    final String  DELETE_TAG = "delete_tag";

    public DiaryAdapter(Context context, Cursor cursor, boolean autoRequery, FragmentManager fragmentManager, DateChangedListener dateChangedListener){
        super(context, cursor, autoRequery);
        mContext = context;
        this.fragmentManager = fragmentManager;
        this.dateChangedListener = dateChangedListener;
        helper = new DiaryDataHelper(App.getContext());

    }

    public DiaryAdapter(Context context, Cursor cursor, FragmentManager fragmentManager, DateChangedListener dateChangedListener) {
        this(context, cursor, true, fragmentManager, dateChangedListener);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(mContext).inflate(R.layout.item_content, null);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        dealView(view, context, cursor);
        RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.diplay);
        layout.setOnClickListener(new View.OnClickListener() {
            private Diary d = diary;
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DiaryActivity.class);
                intent.putExtra("_id", d._id);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    class Holder {
        TextView title;
        TextView content;
        TextView time;
        ImageView img;
        ImageView delete;
        public Holder(TextView title, TextView content, TextView time, ImageView img, ImageView delete) {
            this.title = title;
            this.content = content;
            this.time = time;
            this.img = img;
            this.delete = delete;
        }

    }

    protected void dealView(View view, Context context, final Cursor cursor) {
        Holder holder = (Holder) view.getTag();
        if (holder == null) {
            TextView title = (TextView) view.findViewById(R.id.title);
            TextView content = (TextView) view.findViewById(R.id.content);
            TextView time = (TextView) view.findViewById(R.id.time);
            ImageView img = (ImageView) view.findViewById(R.id.img);
            ImageView delete = (ImageView) view.findViewById(R.id.delete);
            holder = new Holder(title, content, time, img, delete);
        }
        diary = Diary.fromCursor(cursor);
        holder.title.setText(diary.content);
        holder.content.setText(diary.title);
        holder.time.setText(diary.create_at);
        if (diary.pic_url != null && !diary.pic_url.equals("")) {
            holder.img.setVisibility(View.VISIBLE);
            holder.img.setImageURI(getFileUri(diary.pic_url.split(" ")[1]));
        } else {
            holder.img.setVisibility(View.GONE);
        }
        holder.delete.setOnClickListener(new View.OnClickListener() {
            private Diary d = diary;
            @Override
            public void onClick(final View view) {
                PostOffice.newMail(mContext)
                        .setTitle(R.string.dialog_title)
                        .setMessage(R.string.dialog_content)
                        .setDesign(Design.MATERIAL_LIGHT)
                        .setButton(Dialog.BUTTON_NEGATIVE, R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                dialog.dismiss();
                            }
                        })
                        .setButton(Dialog.BUTTON_POSITIVE, R.string.dialog_ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                dialog.dismiss();
                                helper.delete(d);
                                dateChangedListener.onDateChanged();
                                closeItem(cursor.getPosition() - 1);
                            }
                        })
                        .show(fragmentManager, DELETE_TAG);
            }
        });
    }

    protected Uri getFileUri(String path) {
        return Uri.parse(path);
    }

    public interface DateChangedListener {

        public void onDateChanged();
    }
}
