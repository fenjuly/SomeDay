package com.example.liurongchan.traingdemo.demo.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;

import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.example.liurongchan.traingdemo.demo.R;
import com.r0adkll.postoffice.PostOffice;
import com.r0adkll.postoffice.model.Design;
import com.r0adkll.postoffice.styles.ListStyle;


/**
 * Created by liurongchan on 14/10/20.
 */
public class ListViewAdapter extends BaseSwipeAdapter {

    private Context mContext;
    private FragmentManager fragmentManager;

    ListView listView;
    private int count = 20;

    public static final CharSequence[] LIST_CONTENT = new CharSequence[]{
            "Batman",
            "The Flash",
            "Super Man",
            "Jon Stewart",
            "Wonder Woman",
            "Black Canary",
            "Red Tornado",
            "Cpt. Atom",
            "The Queston",
            "Hawk Girl",
            "Martian Man-Hunter",
            "Green Arrow",
            "Red Arrow",
            "Robin",
            "Nightwing",
            "Aquaman",
            "The Joker",
            "Cpt. Cold",
            "Cpt. Boomerang",
            "Darkseid",
            "Mongol",
            "Two Face",
            "The Penguin",
            "Ra's al Ghul",
            "Lex Luthor",
            "Gorilla Grod",
            "Shade",
            "Bizarro",
            "Ultra Humanite"
    };


    public ListViewAdapter(Context mContext, FragmentManager fragmentManager, ListView listView) {
        this.mContext = mContext;
        this.fragmentManager = fragmentManager;
        this.listView = listView;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public Object getItem(int position) {
        return R.string.title;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    @Override
    public View generateView(int position, ViewGroup parent) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_content, null);
        SwipeLayout swipeLayout = (SwipeLayout)v.findViewById(getSwipeLayoutResourceId(position));
        swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
            }
        });
        swipeLayout.setOnDoubleClickListener(new SwipeLayout.DoubleClickListener() {
            @Override
            public void onDoubleClick(SwipeLayout layout, boolean surface) {
                PostOffice.newSimpleListMail(mContext, "DC Universe", Design.MATERIAL_LIGHT, LIST_CONTENT, new ListStyle.OnItemAcceptedListener<CharSequence>() {
                    @Override
                    public void onItemAccepted(CharSequence item, int position) {
                        Toast.makeText(mContext, String.format("%s is the bestest DC Character", item), Toast.LENGTH_SHORT).show();
                    }
                })
                        .show(fragmentManager, "LIST_MATERIAL");
            }
        });
        return v;
    }

    @Override
    public void fillValues(int position, View convertView) {
        Holder holder = (Holder) convertView.getTag();
        if(holder == null) {
            TextView title = (TextView) convertView.findViewById(R.id.title);
            TextView content = (TextView) convertView.findViewById(R.id.content);
            ImageView img = (ImageView) convertView.findViewById(R.id.img);
            Button delete = (Button) convertView.findViewById(R.id.delete);
            holder = new Holder(title, content, img, delete);
            convertView.setTag(holder);
        }
        holder.title.setText(R.string.title);
        holder.content.setText(R.string.content);
        holder.img.setImageResource(R.drawable.avatar);
        holder.delete.setOnClickListener(new View.OnClickListener() {
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
                                count --;
                                notifyDataSetChanged();
                            }
                        })
                        .show(fragmentManager, "DIALOG_MATERIAL");
            }
        });
    }

    class Holder {
        TextView title;
        TextView content;
        ImageView img;
        Button delete;
        public Holder(TextView title, TextView content, ImageView img, Button delete) {
            this.title = title;
            this.content = content;
            this.img = img;
            this.delete = delete;
        }

    }
}
