package com.mdgagj.clicker3;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.plattysoft.leonids.ParticleSystem;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class FullscreenActivity extends Activity {
    public double inc = 0.1;
    public long i = 0;
    public long butcl = 1;
    public double inc2 = 0;
    public long[] prices = {7000L, 60L, 300L, 1000L, 10000L, 100000L, 1000000L, 10000000L, 100000000L, 1000000000L};
    public long[] pricesh = {7000L, 60L, 300L, 1000L, 10000L, 100000L, 1000000L, 10000000L, 100000000L, 1000000000L};
    public int[] counts = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    SharedPreferences sPref;
    ListView lv;
    Resources res;
    ArrayList<TestItem> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen);
        sPref = getPreferences(MODE_PRIVATE);
        res = getResources();
        Bitmap allah = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeResource(res, R.drawable.yoba_button),
                256, 256);
        Button button = (Button) findViewById(R.id.click);
        BitmapDrawable bdrawable = new BitmapDrawable(res, allah);
        button.setBackground(bdrawable);

  /*      for (int i = 1; i < 11; i++) {
            counts[i - 1] = sPref.getInt("count" + i, 0);
        }
        for (int i = 1; i < 11; i++) {
            prices[i - 1] = sPref.getLong("price" + i, pricesh[i - 1]);
        }*/
        TextView tv = (TextView) findViewById(R.id.posts);
        lv = (ListView) findViewById(R.id.listView2);

        Typeface face = Typeface.createFromAsset(getAssets(), "fofbb_reg.ttf");
        tv.setTypeface(face);

        i = sPref.getLong("i", 0);
        inc = (double) sPref.getFloat("inc", 0.0f);

        DecimalFormatSymbols m = new DecimalFormatSymbols();
        m.setDecimalSeparator('.');
        DecimalFormat c = new DecimalFormat("#####0.0", m);
        tv.setText("" + i + " yobs" + "\n" + "" + c.format(inc) + " per second");

        final Timer timer = new Timer();
        final TimerTask task = new TimerTask() {
            public void run() {
                inc2 += inc;
                if (inc2 > 1.0) {
                    i += (new Double(inc2)).longValue();
                    inc2 = 0.0;
                }
            }
        };
        timer.schedule(task, 0, 1000);

        final Timer timer1 = new Timer();
        final TimerTask task1 = new TimerTask() {

            public void run() {
                setText();
            }

        };
        timer1.schedule(task1, 0, 10);

        Button increase = (Button) findViewById(R.id.increase);
        increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = 100000000L;
            }
        });
    }

    private void setText() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView tv = (TextView) findViewById(R.id.posts);
                DecimalFormatSymbols m = new DecimalFormatSymbols();
                m.setDecimalSeparator('.');
                DecimalFormat c = new DecimalFormat("#####0.0", m);
                tv.setText("" + i + " posts" + "\n" + "" + c.format(inc) + " per second");
            }
        });
    }

    public void Lose(View v) {
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setVisibility(View.INVISIBLE);
        ListView listView = (ListView) findViewById(R.id.listView2);
        listView.setVisibility(View.INVISIBLE);
        Button button = (Button) findViewById(R.id.button);
        button.setVisibility(View.INVISIBLE);
    }

    public void SendMe(View v) {
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setVisibility(View.VISIBLE);
        items = new ArrayList<>();
        items.add(new TestItem(R.drawable.favicon, "Помолиться Аллаху", "X2", prices[0], counts[0], 0, getAssets(), 2.0));
        items.add(new TestItem(R.drawable.favicon, "Личинус", "+0.1", prices[1], counts[1], 1, getAssets(), 0.1));
        items.add(new TestItem(R.drawable.favicon, "Тётя Йоба", "+3", prices[2], counts[2], 1, getAssets(), 3.0));
        items.add(new TestItem(R.drawable.favicon, "Дядя Больжедор", "+10", prices[3], counts[3], 1, getAssets(), 10.0));
        items.add(new TestItem(R.drawable.favicon, "Дед", "+100", prices[4], counts[4], 1, getAssets(), 100.0));
        items.add(new TestItem(R.drawable.favicon, "Батя Бафомет", "+500", prices[5], counts[5], 1, getAssets(), 500.0));
        items.add(new TestItem(R.drawable.favicon, "Паук Аркадий", "+1k", prices[6], counts[6], 1, getAssets(), 1000.0));
        items.add(new TestItem(R.drawable.favicon, "Le maman", "+2k", prices[7], counts[7], 1, getAssets(), 2000.0));
        items.add(new TestItem(R.drawable.favicon, "Ванечка Ерохин", "+10k", prices[8], counts[8], 1, getAssets(), 10000.0));
        items.add(new TestItem(R.drawable.favicon, "ЕОТ", "+50k", prices[9], counts[9], 1, getAssets(), 50000.0));
        lv.setAdapter(new TestAdapter(this, items));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (items.get(position).getType() == 0) {
                    if (i > items.get(position).getPrice()) {
                        i -= items.get(position).getPrice();
                        butcl *= 2;
                        items.get(position).setPrice((new Double(items.get(position).getPrice() * 4)).longValue());
                        items.get(position).setLevel(items.get(position).getLevel() + 1);
                        lv.setAdapter(new TestAdapter(FullscreenActivity.this, items));
                    }
                } else {
                    if (i > items.get(position).getPrice()) {
                        i -= items.get(position).getPrice();
                       inc += items.get(position).getIncrement();
                        items.get(position).setPrice((new Double(items.get(position).getPrice() * 1.5)).longValue());
                        items.get(position).setLevel(items.get(position).getLevel() + 1);
                        lv.setAdapter(new TestAdapter(FullscreenActivity.this, items));
                    }
                }
            }
        });
        lv.setVisibility(View.VISIBLE);
        lv.requestLayout();
        Button button = (Button) findViewById(R.id.button);
        button.setVisibility(View.VISIBLE);

    }

    public void Enull(View v) {
        sPref = getPreferences(MODE_PRIVATE);
        sPref.edit().clear().commit();
        i = 0;
        inc = 0.0;
        inc2 = 0.0;
    }

    protected void onPause() {
        super.onPause();
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putLong("i", i);
        ed.putFloat("inc", (float) inc);
        for (int i = 1; i < 11; i++) {
            ed.putLong("price" + i, prices[i - 1]);
        }
        for (int i = 1; i < 11; i++) {
            ed.putInt("count" + i, counts[i - 1]);
        }
        ed.commit();
    }

    public void getAch(View v) {
        Toast.makeText(this,
                "Пока не готово.",
                Toast.LENGTH_SHORT).show();
    }

    public void dre(View v) {
        Random random = new Random();
        ParticleSystem part = new ParticleSystem(this, 5 + random.nextInt(20), R.drawable.yoba_particle, 3000);
        part.setSpeedRange(0.2f, 0.5f)
                .setRotationSpeed(144);
        part.oneShot(findViewById(R.id.click), 5 + random.nextInt(20));
        i += butcl;
    }

}
