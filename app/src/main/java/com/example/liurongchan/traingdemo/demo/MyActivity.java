package com.example.liurongchan.traingdemo.demo;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;

import com.devspark.appmsg.AppMsg;
import com.example.liurongchan.traingdemo.demo.adapter.DropListViewAdapter;
import com.example.liurongchan.traingdemo.demo.fragment.DiaryFragment;
import com.example.liurongchan.traingdemo.demo.fragment.DraftFragment;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;


public class MyActivity extends FragmentActivity implements ActionBar.OnNavigationListener {

    static final int INTENT_CODE = 1;
    static final int RESULT_CODE = 2;

    FragmentManager fragmentManager;

    MenuItem edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        actionBar.setListNavigationCallbacks(new DropListViewAdapter(this), this);
        fragmentManager = getSupportFragmentManager();
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(getApplicationContext()));
    }

    private void setFragment(Fragment fragment) {
        fragmentManager.beginTransaction().replace(R.id.frame, fragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my, menu);
        edit = menu.findItem(R.id.edit);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImageLoader.getInstance().destroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.edit) {
            startActivityForResult(new Intent(this, EditActivity.class), INTENT_CODE);
        }
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(int itemPostion, long itemId) {
        switch (itemPostion) {
            case 0:
                if (edit != null) {
                    edit.setVisible(true);
                }
                setFragment(new DiaryFragment());
                break;
            case 1:
                edit.setVisible(false);
                setFragment(new DraftFragment());
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == INTENT_CODE && resultCode == RESULT_CODE) {
            AppMsg.makeText(this, getResources().getString(R.string.save_as_draft), AppMsg.STYLE_INFO).show();
        }
    }
}
