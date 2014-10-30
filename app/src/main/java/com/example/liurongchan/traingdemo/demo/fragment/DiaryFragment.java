package com.example.liurongchan.traingdemo.demo.fragment;

import android.app.ProgressDialog;
import android.support.v4.content.Loader;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.support.v4.app.LoaderManager;

import com.devspark.appmsg.AppMsg;
import com.example.liurongchan.traingdemo.demo.App;
import com.example.liurongchan.traingdemo.demo.R;
import com.example.liurongchan.traingdemo.demo.adapter.CardsAnimationAdapter;
import com.example.liurongchan.traingdemo.demo.adapter.DiaryAdapter;
import com.example.liurongchan.traingdemo.demo.dao.DiaryDataHelper;
import com.nhaarman.listviewanimations.swinginadapters.AnimationAdapter;

/**
 * Created by liurongchan on 14/10/20.
 */
public class DiaryFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, DiaryAdapter.DateChangedListener{

    private DiaryAdapter diaryAdapter;

    private ProgressDialog progressDialog;
    private ListView listView;
    private ImageView face;
    private LinearLayout linearLayout;

    LoaderManager loaderManager;


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.fragment_layout, container, false);
        listView = (ListView) rootView.findViewById(R.id.listView);
        linearLayout = (LinearLayout) rootView.findViewById(R.id.hide);
        face = (ImageView) rootView.findViewById(R.id.face);
        diaryAdapter = new DiaryAdapter(getActivity(), null, getFragmentManager(), this);
        AnimationAdapter animationAdapter = new CardsAnimationAdapter(diaryAdapter);
        animationAdapter.setAbsListView(listView);
        listView.setAdapter(animationAdapter);
        loaderManager = getLoaderManager();
        loaderManager.initLoader(0, null, this);
        progressDialog = ProgressDialog.show(getActivity(), null, getResources().getString(R.string.loading));
        return rootView;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new DiaryDataHelper(App.getContext()).getCompletedCursorLoader();
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        diaryAdapter.changeCursor(cursor);
        progressDialog.dismiss();
        if (cursor.getCount() <= 0) {
            listView.setVisibility(View.GONE);
            linearLayout.setVisibility(View.VISIBLE);
        } else {
            listView.setVisibility(View.VISIBLE);
            linearLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        diaryAdapter.changeCursor(null);
    }

    @Override
    public void onDateChanged() {
        loaderManager.restartLoader(0, null, this);
    }
}
