package com.mdgagj.clicker3.adapters;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mdgagj.clicker3.R;
import com.mdgagj.clicker3.models.ShopItem;

import java.math.BigInteger;
import java.util.List;

/**
 * Project YobaClicker. Created by gwa on 7/22/16.
 */

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ItemViewHolder> {
    private List<ShopItem> items;

    public ShopAdapter(List<ShopItem> items) {
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.shop_item, viewGroup, false);
        Typeface face = Typeface.createFromAsset(viewGroup.getContext().getAssets(), "Roboto-Regular.ttf");
        return new ItemViewHolder(v, face);
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder itemViewHolder, int i) {
        final int position = i;
        itemViewHolder.card.setOnClickListener(new View.OnClickListener() {
            private BigInteger[] prices = {
                    BigInteger.valueOf(7000L),
                    BigInteger.valueOf(60L),
                    BigInteger.valueOf(300L),
                    BigInteger.valueOf(1000L),
                    BigInteger.valueOf(10000L),
                    BigInteger.valueOf(100000L),
                    BigInteger.valueOf(1000000L),
                    BigInteger.valueOf(10000000L),
                    BigInteger.valueOf(100000000L),
                    BigInteger.valueOf(1000000000L)
            };
            private long[] counts = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            private BigInteger incrementEverySecond = BigInteger.ONE;
            private BigInteger postsCount = BigInteger.ZERO;
            private BigInteger buttonIncrement = BigInteger.ONE;

            @Override
            public void onClick(View view) {
                ShopItem item = items.get(position);
                SharedPreferences sPref = item.getsPref();
                loadPrefs(sPref);
                Log.d("DRE", "cash is " + postsCount.toString() + ", price is " + item.getPrice());
                if (item.getType() == 0) {
                    if (postsCount.compareTo(item.getPrice()) == 1) {
                        postsCount = postsCount.subtract(item.getPrice());
                        buttonIncrement = buttonIncrement.multiply(new BigInteger("2"));
                        items.get(position).setPrice(item.getPrice().multiply(new BigInteger("4")));
                        items.get(position).setLevel(item.getLevel() + 1);
                        prices[position] = item.getPrice();
                        counts[position] = item.getLevel();
                    } else {
                        Toast.makeText(view.getContext(), "Недостаточно багортов, чел!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (postsCount.compareTo(item.getPrice()) == 1) {
                        postsCount = postsCount.subtract(item.getPrice());
                        incrementEverySecond = incrementEverySecond.add(item.getIncrement());
                        items.get(position).setPrice(item.getPrice().multiply(new BigInteger("2")));
                        items.get(position).setLevel(item.getLevel() + 1);
                        prices[position] = item.getPrice();
                        counts[position] = item.getLevel();
                    } else {
                        Toast.makeText(view.getContext(), "Недостаточно багортов, чел!", Toast.LENGTH_SHORT).show();
                    }
                }
                savePrefs(sPref);
                notifyDataSetChanged();
            }

            void savePrefs(SharedPreferences sPref) {
                SharedPreferences.Editor ed = sPref.edit();
                ed.putString("postsCount", postsCount.toString());
                ed.putString("buttonIncrement", buttonIncrement.toString());
                ed.putString("incrementEverySecond", incrementEverySecond.toString());
                for (int i = 0; i < 10; i++) {
                    ed.putString("price" + i, prices[i].toString());
                }
                for (int i = 0; i < 10; i++) {
                    ed.putLong("count" + i, counts[i]);
                }
                ed.apply();
            }

            void loadPrefs(SharedPreferences sPref) {
                postsCount = new BigInteger(sPref.getString("postsCount", "0"));
                buttonIncrement = new BigInteger(sPref.getString("buttonIncrement", "1"));
                incrementEverySecond = new BigInteger(sPref.getString("incrementEverySecond", "1"));
                for (int i = 0; i < 10; i++) {
                    prices[i] = new BigInteger(sPref.getString("price" + i, prices[i].toString()));
                }
                for (int i = 0; i < 10; i++) {
                    counts[i] = sPref.getLong("count" + i, 0L);
                }
            }

        });
        itemViewHolder.bps.setText(items.get(i).getBpsValue());
        itemViewHolder.level.setText(String.valueOf(items.get(i).getLevel()));
        itemViewHolder.itemName.setText(items.get(i).getItemDescr());
        itemViewHolder.price.setText(String.valueOf(items.get(i).getPrice()));
        itemViewHolder.itemImage.setImageResource(items.get(i).getImageIco());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        CardView card;
        TextView bps;
        TextView level;
        TextView itemName;
        TextView price;
        ImageView itemImage;

        ItemViewHolder(View v, Typeface face) {
            super(v);
            card = (CardView) itemView.findViewById(R.id.card);
            bps = (TextView) itemView.findViewById(R.id.bps);
            bps.setTypeface(face);
            level = (TextView) itemView.findViewById(R.id.level);
            level.setTypeface(face);
            price = (TextView) itemView.findViewById(R.id.price);
            price.setTypeface(face);
            itemName = (TextView) itemView.findViewById(R.id.item_name);
            itemName.setTypeface(face);
            itemImage = (ImageView) itemView.findViewById(R.id.item_image);
        }
    }

}
