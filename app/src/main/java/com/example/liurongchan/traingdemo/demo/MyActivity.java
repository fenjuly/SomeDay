package com.example.liurongchan.traingdemo.demo;

import android.app.ActionBar;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;

import com.example.liurongchan.traingdemo.demo.adapter.DropListViewAdapter;
import com.example.liurongchan.traingdemo.demo.fragment.GridFragment;
import com.example.liurongchan.traingdemo.demo.fragment.TitleFragment;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;


public class MyActivity extends FragmentActivity implements ActionBar.OnNavigationListener{

    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        actionBar.setListNavigationCallbacks(new DropListViewAdapter(this), this);
        fragmentManager = getSupportFragmentManager();
        setFragment(new TitleFragment());
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(getApplicationContext()));
    }

    private void setFragment(Fragment fragment) {
        fragmentManager.beginTransaction().replace(R.id.frame, fragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my, menu);
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
            startActivity(new Intent(this, EditActivity.class));
        }
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(int itemPostion, long itemId) {
        switch (itemPostion) {
            case 0:
                setFragment(new TitleFragment());
                break;
            case 1 :
                setFragment(new GridFragment());
                break;
        }
        return true;
    }
}
