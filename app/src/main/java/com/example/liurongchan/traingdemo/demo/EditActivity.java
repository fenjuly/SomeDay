package com.example.liurongchan.traingdemo.demo;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.devspark.appmsg.AppMsg;
import com.example.liurongchan.traingdemo.demo.dao.DiaryDataHelper;
import com.example.liurongchan.traingdemo.demo.model.Diary;
import com.kbeanie.imagechooser.api.ChooserType;
import com.kbeanie.imagechooser.api.ChosenImage;
import com.kbeanie.imagechooser.api.ImageChooserListener;
import com.r0adkll.postoffice.PostOffice;
import com.r0adkll.postoffice.model.Design;
import com.r0adkll.postoffice.styles.ListStyle;
import com.kbeanie.imagechooser.api.ImageChooserManager;

import java.io.File;
import java.text.DateFormat;
import java.util.Date;

/**
 * Created by liurongchan on 14/10/24.
 */
public class EditActivity extends Activity implements ImageChooserListener{

    public final CharSequence[] PHOTO_TYPE = new CharSequence[] {
           "从本地获取",
           "去拍一张吧"
    };

    private final String TAG = "PHOTO";

    private ImageChooserManager imageChooserManager;

    private ImageView thumbnail;
    private EditText title_tv;
    private EditText content_tv;


    private  ProgressDialog progressDialog;


    private int chooserType;

    private String filePath;

    private String picUrl = "";

    private DiaryDataHelper helper;

    private static final String TAG_EXIT = "SURE_EXIT";

    static final int RESULT_CODE = 2;

    private String create_at;
    private long _id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        thumbnail = (ImageView) findViewById(R.id.thumbnail);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        title_tv = (EditText) findViewById(R.id.title_et);
        content_tv = (EditText) findViewById(R.id.content_et);
        helper = new DiaryDataHelper(App.getContext());
        dealIntent();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                beforeLeaveActivity();
                break;
            case R.id.photo:
                PostOffice.newSimpleListMail(this, getResources().getString(R.string.choose_pic_source), Design.MATERIAL_LIGHT, PHOTO_TYPE, new ListStyle.OnItemAcceptedListener<CharSequence>() {
                    @Override
                    public void onItemAccepted(CharSequence item, int position) {
                            switch (position) {
                                case 0:
                                    chooseImage();
                                    break;
                                case 1:
                                    takePicture();
                                    break;
                                default:
                                    throw new IllegalArgumentException(getResources().getString(R.string.position_exception));
                            }
                    }
                }).show(getFragmentManager(), TAG);
                break;
            case R.id.save:
                String title = title_tv.getText().toString();
                String content = content_tv.getText().toString();
                if (title.equals("")) {
                    AppMsg.makeText(this, getResources().getString(R.string.no_title), AppMsg.STYLE_ALERT).show();
                } else if (content.equals("")) {
                    AppMsg.makeText(this, getResources().getString(R.string.no_content), AppMsg.STYLE_ALERT).show();
                } else if (title.length() > 25) {
                    AppMsg.makeText(this, getResources().getString(R.string.title_too_long), AppMsg.STYLE_ALERT).show();
                } else if (content.length() < 5) {
                    AppMsg.makeText(this, getResources().getString(R.string.content_too_short), AppMsg.STYLE_ALERT).show();
                } else {
                    insertOrUpdate(title, content);
                    AppMsg.makeText(this, getResources().getString(R.string.save_success), AppMsg.STYLE_INFO).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                        }
                    }, 2000);
                }
                break;

            case R.id.delete:
                PostOffice.newMail(this)
                        .setTitle(R.string.exit_without_save)
                        .setMessage(R.string.exit_text)
                        .setDesign(Design.MATERIAL_LIGHT)
                        .setButton(Dialog.BUTTON_NEGATIVE, R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                dialog.dismiss();
                            }
                        })
                        .setButton(Dialog.BUTTON_POSITIVE, R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                dialog.dismiss();
                                finish();
                            }
                        })
                        .show(getFragmentManager(), TAG_EXIT);
            default:
                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK
                && (requestCode == ChooserType.REQUEST_PICK_PICTURE || requestCode == ChooserType.REQUEST_CAPTURE_PICTURE)) {
            if (imageChooserManager == null) {
                reinitializeImageChooser();
            }
            imageChooserManager.submit(requestCode, data);
        } else {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onImageChosen(final ChosenImage image) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                progressDialog.dismiss();
                if (image != null) {
                    picUrl = image.getFilePathOriginal().toString() + " " + image.getFileThumbnail();
                    thumbnail.setImageURI(Uri.parse(new File(image
                            .getFileThumbnail()).toString()));
                    thumbnail.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void onError(final String reason) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                progressDialog.dismiss();
                AppMsg.makeText(EditActivity.this, reason,
                        AppMsg.STYLE_ALERT).show();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void reinitializeImageChooser() {
        imageChooserManager = new ImageChooserManager(this, chooserType,
                getResources().getString(R.string.folder), true);
        imageChooserManager.setImageChooserListener(this);
        imageChooserManager.reinitialize(filePath);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("chooser_type", chooserType);
        outState.putString("media_path", filePath);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("chooser_type")) {
                chooserType = savedInstanceState.getInt("chooser_type");
            }

            if (savedInstanceState.containsKey("media_path")) {
                filePath = savedInstanceState.getString("media_path");
            }
        }
        super.onRestoreInstanceState(savedInstanceState);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            beforeLeaveActivity();
        }
        return true;
    }

    private void chooseImage() {
        chooserType = ChooserType.REQUEST_PICK_PICTURE;
        imageChooserManager = new ImageChooserManager(this,
                ChooserType.REQUEST_PICK_PICTURE, getResources().getString(R.string.folder), true);
        imageChooserManager.setImageChooserListener(this);
        try {
            progressDialog = ProgressDialog.show(this, null, getResources().getString(R.string.wait));
            filePath = imageChooserManager.choose();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void takePicture() {
        chooserType = ChooserType.REQUEST_CAPTURE_PICTURE;
        imageChooserManager = new ImageChooserManager(this,
                ChooserType.REQUEST_CAPTURE_PICTURE, getResources().getString(R.string.folder), true);
        imageChooserManager.setImageChooserListener(this);
        try {
            progressDialog = ProgressDialog.show(this, null, getResources().getString(R.string.wait));
            filePath = imageChooserManager.choose();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected String getCurrentTime() {
        Date now = new Date();
        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
        return dateFormat.format(now);
    }

    protected String getPicUrl() {
        return picUrl;
    }

    private void saveAsDraft() {
        helper.insert(new Diary(title_tv.getText().toString(), content_tv.getText().toString(), getCurrentTime(), getCurrentTime()));
    }

    private void insertOrUpdate(String title, String content) {
        int complete = 1;
        if(create_at != null && !create_at.equals("")) {
            helper.update(new Diary(_id, title, content, create_at, getCurrentTime(), getPicUrl(), complete));
        } else {
            helper.insert(new Diary(title, content, getCurrentTime(), getCurrentTime(), getPicUrl(), complete));
        }
    }

    private void beforeLeaveActivity() {
        if (title_tv.getText().toString().equals("")
                && content_tv.getText().toString().equals("")
                || (create_at != null
                && !create_at.equals(""))) {
            finish();
        } else {
            saveAsDraft();
            Intent intent = new Intent();
            setResult(RESULT_CODE, intent);
            finish();
        }
    }
    private void dealIntent() {
        Intent intent = getIntent();
        _id = intent.getLongExtra("_id", 0l);
        create_at = intent.getStringExtra("create_at");
        if (create_at != null && !create_at.equals("")) {
            title_tv.setText(intent.getStringExtra("title"));
            content_tv.setText(intent.getStringExtra("content"));
        }
    }
}
