package com.example.liurongchan.traingdemo.demo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.text.style.TypefaceSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.liurongchan.traingdemo.demo.dao.DiaryDataHelper;
import com.example.liurongchan.traingdemo.demo.model.Diary;

import java.io.File;

/**
 * Created by liurongchan on 14/10/29.
 */
public class DiaryActivity extends ActionBarActivity {

    private Diary diary;

    private DiaryDataHelper helper;

    private static final String FILE_TAG = "file://";

    public static final String FILE_URI = "file_uri";

    private  android.support.v7.widget.ShareActionProvider mShareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        setDiary();
        display();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.diary, menu);
        MenuItem share = menu.findItem(R.id.share);
        mShareActionProvider = (android.support.v7.widget.ShareActionProvider) MenuItemCompat.getActionProvider(share);
        mShareActionProvider.setShareIntent(getDefaultIntent());
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return true;
    }

    private Intent getDefaultIntent() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        String shareTitle = getString(R.string.share_title);
        shareTitle = String.format(shareTitle, diary.title);
        String shareContent = getString(R.string.shae_content);
        intent.setType("image/*");
        shareContent = String.format(shareContent, diary.create_at,
                diary.content);
        intent.putExtra(Intent.EXTRA_SUBJECT, shareTitle);
        intent.putExtra(Intent.EXTRA_TEXT, shareContent);
        File file = getShareFile();
        if (file != null) {
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
        }

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    private File getShareFile() {
        if(diary.pic_url == null || diary.pic_url.equals("")) {
            return null;
        }
        File file = new File(diary.pic_url.split(" ")[1]);
        if (file.exists()) {
            return file;
        } else {
            return null;
        }
    }

    private void setDiary() {
        helper = new DiaryDataHelper(App.getContext());
        Intent intent = getIntent();
        diary = helper.query(intent.getLongExtra("_id", 0));
    }

    private void display() {
        TextView title = (TextView) findViewById(R.id.title);
        TextView content = (TextView) findViewById(R.id.content);
        TextView time = (TextView) findViewById(R.id.time);
        ImageView img = (ImageView) findViewById(R.id.img);
        SpannableString msp = new SpannableString(diary.title);
        msp.setSpan(new TypefaceSpan("monospace"), 0, diary.title.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        msp.setSpan(new StyleSpan(Typeface.BOLD_ITALIC), 0, diary.title.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        title.setText(msp);
        time.setTextColor(Color.GRAY);
        time.setText(diary.create_at);
        content.setTextColor(Color.GRAY);
        content.setText(diary.content);

        if (diary.pic_url != null && !diary.pic_url.equals("")) {
            final String path = diary.pic_url.split(" ")[1];
            final String uri = FILE_TAG + path;
            img.setVisibility(View.VISIBLE);
            img.setImageBitmap(handleBitmap(path));
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(DiaryActivity.this, ImageViewActivity.class);
                    intent.putExtra(FILE_URI, uri);
                    startActivity(intent);
                }
            });
        } else {
            img.setVisibility(View.GONE);
        }

    }

    private Bitmap handleBitmap(String uri){
        float target_width;
        float target_height;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(uri, options);
        int height = options.outHeight;
        int width = options.outWidth;
        float ratio = (float) height / (float) width;

        int screen_width = px2dip(this, getResources().getDisplayMetrics().widthPixels) - 10;
        if(width > screen_width) {
            target_width = screen_width;
            target_height = target_width * ratio;
        } else {
            target_height = height;
            target_width = width;
        }
        options.outHeight = (int) target_height;
        options.outWidth = (int) target_width;
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(uri, options);
    }

    private static int px2dip(Context context, float pxValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(pxValue / scale + 0.5f);
    }


}
