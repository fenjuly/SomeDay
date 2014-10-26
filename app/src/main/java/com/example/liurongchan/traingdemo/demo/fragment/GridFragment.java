package com.example.liurongchan.traingdemo.demo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.daimajia.swipe.implments.SwipeItemMangerImpl;
import com.example.liurongchan.traingdemo.demo.ImageViewActivity;
import com.example.liurongchan.traingdemo.demo.R;
import com.example.liurongchan.traingdemo.demo.adapter.GridViewAdapter;

/**
 * Created by liurongchan on 14/10/20.
 */
public class GridFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.grid_layout, container, false);
        final GridView gridView = (GridView) v.findViewById(R.id.gridview);
        final GridViewAdapter adapter = new GridViewAdapter(getActivity());
        gridView.setAdapter(adapter);
        gridView.setSelected(false);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(getActivity(), ImageViewActivity.class));
            }
        });
        return v;
    }
}
