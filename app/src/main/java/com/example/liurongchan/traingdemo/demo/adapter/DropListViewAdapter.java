package com.example.liurongchan.traingdemo.demo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.liurongchan.traingdemo.demo.R;

/**
 * Created by liurongchan on 14/10/20.
 */
public class DropListViewAdapter  extends BaseAdapter{

    private Context mContext;

    String[] des = {"文章", "图片", "草稿"};
    int [] icons = {R.drawable.article, R.drawable.picture, R.drawable.draft};


    public DropListViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if(view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.drop_down_item, null);
        }
        TextView type = (TextView) view.findViewById(R.id.type);
        ImageView icon = (ImageView) view.findViewById(R.id.icon);
        type.setText(des[position]);
        icon.setImageResource(icons[position]);
        return view;
    }
}
