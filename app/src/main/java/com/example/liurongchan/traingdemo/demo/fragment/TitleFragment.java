package com.example.liurongchan.traingdemo.demo.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.liurongchan.traingdemo.demo.ArticleActivity;
import com.example.liurongchan.traingdemo.demo.R;
import com.example.liurongchan.traingdemo.demo.adapter.ListViewAdapter;

/**
 * Created by liurongchan on 14/10/20.
 */
public class TitleFragment  extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.fragment_layout, container, false);
        ListView listView = (ListView) rootView.findViewById(R.id.listView);
        listView.setAdapter(new ListViewAdapter(getActivity(), getFragmentManager(), listView));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(getActivity(), ArticleActivity.class));
            }
        });
        return rootView;
    }
}
