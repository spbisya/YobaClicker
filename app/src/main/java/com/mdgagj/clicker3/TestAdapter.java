package com.mdgagj.clicker3;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by 777 on 1/2/2016.
 */
public class TestAdapter extends BaseAdapter {
    ArrayList<TestItem> items = new ArrayList<TestItem>();
    Context context;

    public TestAdapter(Context context, ArrayList<TestItem> arr) {
        if (arr != null) {
            items = arr;
        }
        this.context = context;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.shop_item_test, parent, false);
        }

        ImageView imageIco = (ImageView) convertView.findViewById(R.id.imageIco);
        TextView itemDescr = (TextView) convertView.findViewById(R.id.itemDescr);
        TextView type = (TextView) convertView.findViewById(R.id.type);
        TextView price = (TextView) convertView.findViewById(R.id.price);
        TextView cps_value = (TextView) convertView.findViewById(R.id.cps_value);
        TextView level = (TextView) convertView.findViewById(R.id.level);

        Typeface face = Typeface.createFromAsset(items.get(position).getAssets(), "9607.ttf");

       // imageIco.setImageResource(items.get(position).getImageIco());
        itemDescr.setText(items.get(position).getItemDescr());
       // itemDescr.setTypeface(face);
        type.setText(items.get(position).getType() == 0 ? "Click" : "Bps");
      //  type.setTypeface(face);
        price.setText("" + items.get(position).getPrice());
      //  price.setTypeface(face);
        cps_value.setText("" + items.get(position).getCps_value());
       // cps_value.setTypeface(face);
        level.setText("" + items.get(position).getLevel());
       // level.setTypeface(face);
        return convertView;
    }
}
