package com.example.liurongchan.traingdemo.demo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.example.liurongchan.traingdemo.demo.R;

/**
 * Created by liurongchan on 14/10/20.
 */
public class GridViewAdapter extends BaseSwipeAdapter {

    private Context mContext;

    public GridViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    @Override
    public View generateView(int position, ViewGroup parent) {
        return LayoutInflater.from(mContext).inflate(R.layout.grid_item, null);
    }

    @Override
    public void fillValues(int position, View convertView) {
        Holder holder = (Holder) convertView.getTag();
        if (holder == null) {
            ImageView img = (ImageView) convertView.findViewById(R.id.up);
            holder = new Holder(img);
            convertView.setTag(holder);
        }
        holder.img.setImageResource(R.drawable.grid_avatar);
    }

    @Override
    public int getCount() {
        return 50;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

   class Holder {
       ImageView img;
       public Holder(ImageView img) {
           this.img = img;
       }
   }

}
