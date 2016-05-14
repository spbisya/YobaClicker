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
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.plattysoft.leonids.ParticleSystem;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import me.grantland.widget.AutofitHelper;


public class FullscreenActivity extends Activity {
    public BigInteger incrementEverySecond = BigInteger.ONE;
    public BigInteger postsCount = BigInteger.ZERO;
    public BigInteger buttonIncrement = BigInteger.ONE;
    public TextView postsView, incrementView;
    public BigInteger[] prices = {
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
    public long[] counts = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    SharedPreferences sPref;
    ListView lv;
    Resources res;
    ArrayList<TestItem> items;
    Timer timer = new Timer();
    Timer timer1 = new Timer();
    TimerTask task = new TimerTask() {
        public void run() {
            postsCount = postsCount.add(incrementEverySecond);
        }
    };
    TimerTask task1 = new TimerTask() {

        public void run() {
            setText();
        }

    };


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

        postsView = (TextView) findViewById(R.id.posts);
        incrementView = (TextView) findViewById(R.id.increment);

        AutofitHelper.create(postsView).setMaxLines(1);
        AutofitHelper.create(incrementView).setMaxLines(1);

        lv = (ListView) findViewById(R.id.listView);

        Typeface face = Typeface.createFromAsset(getAssets(), "fofbb_reg.ttf");
        postsView.setTypeface(face);
        incrementView.setTypeface(face);


        for (int i = 0; i < 10; i++) {
            prices[i] = new BigInteger(sPref.getString("price" + i, prices[i].toString()));
        }
        for (int i = 0; i < 10; i++) {
            counts[i] = sPref.getLong("count" + i, 0L);
        }


        items = new ArrayList<>();
        items.add(new TestItem(R.drawable.favicon, "Помолиться Аллаху", "X2", prices[0], counts[0], 0, getAssets(), new BigInteger("2")));
        items.add(new TestItem(R.drawable.favicon, "Личинус", "+1", prices[1], counts[1], 1, getAssets(), new BigInteger("1")));
        items.add(new TestItem(R.drawable.favicon, "Тётя Йоба", "+3", prices[2], counts[2], 1, getAssets(), new BigInteger("3")));
        items.add(new TestItem(R.drawable.favicon, "Дядя Больжедор", "+10", prices[3], counts[3], 1, getAssets(), new BigInteger("10")));
        items.add(new TestItem(R.drawable.favicon, "Дед", "+100", prices[4], counts[4], 1, getAssets(), new BigInteger("100")));
        items.add(new TestItem(R.drawable.favicon, "Батя Бафомет", "+500", prices[5], counts[5], 1, getAssets(), new BigInteger("500")));
        items.add(new TestItem(R.drawable.favicon, "Паук Аркадий", "+1k", prices[6], counts[6], 1, getAssets(), new BigInteger("1000")));
        items.add(new TestItem(R.drawable.favicon, "Le maman", "+2k", prices[7], counts[7], 1, getAssets(), new BigInteger("2000")));
        items.add(new TestItem(R.drawable.favicon, "Ванечка Ерохин", "+10k", prices[8], counts[8], 1, getAssets(), new BigInteger("10000")));
        items.add(new TestItem(R.drawable.favicon, "ЕОТ", "+50k", prices[9], counts[9], 1, getAssets(), new BigInteger("50000")));
        lv.setAdapter(new TestAdapter(this, items));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (items.get(position).getType() == 0) {
                    if (postsCount.compareTo(items.get(position).getPrice()) == 1) {
                        postsCount = postsCount.subtract(items.get(position).getPrice());
                        buttonIncrement = buttonIncrement.multiply(new BigInteger("2"));
                        items.get(position).setPrice(items.get(position).getPrice().multiply(new BigInteger("4")));
                        items.get(position).setLevel(items.get(position).getLevel() + 1);
                        prices[position] = items.get(position).getPrice();
                        counts[position] = items.get(position).getLevel();
                        lv.setAdapter(new TestAdapter(FullscreenActivity.this, items));
                    }
                } else {
                    if (postsCount.compareTo(items.get(position).getPrice()) == 1) {
                        postsCount = postsCount.subtract(items.get(position).getPrice());
                        incrementEverySecond = incrementEverySecond.add(items.get(position).getIncrement());
                        items.get(position).setPrice(items.get(position).getPrice().multiply(new BigInteger("2")));
                        items.get(position).setLevel(items.get(position).getLevel() + 1);
                        prices[position] = items.get(position).getPrice();
                        counts[position] = items.get(position).getLevel();
                        lv.setAdapter(new TestAdapter(FullscreenActivity.this, items));
                    }
                }
            }
        });

        postsCount = new BigInteger(sPref.getString("postsCount", "0"));
        incrementEverySecond = new BigInteger(sPref.getString("incrementEverySecond", "1"));


        String postsViewText = "" + postsCount + " yobs";
        postsView.setText(postsViewText);
        String incrementViewText = "" + incrementEverySecond + " per second";
        incrementView.setText(incrementViewText);


        task = new TimerTask() {
            public void run() {
                postsCount = postsCount.add(incrementEverySecond);
            }
        };

        task1 = new TimerTask() {

            public void run() {
                setText();
            }

        };

        timer.schedule(task, 0, 1000);
        timer1.schedule(task1, 0, 100);

        Button increase = (Button) findViewById(R.id.increase);
        increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postsCount = postsCount.multiply(new BigInteger("10"));

            }
        });
    }

    private void setText() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String postsViewText = "" + postsCount + " yobs";
                postsView.setText(postsViewText);
                String incrementViewText = "" + incrementEverySecond + " per second";
                incrementView.setText(incrementViewText);
                Log.d("Dre", "Text set, postsCount = " + postsCount.toString() + " buttonIncrement = "
                        + buttonIncrement.toString() + " incrementEverySecond = " + incrementEverySecond.toString());
            }
        });
    }

    public void Lose(View v) {
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setVisibility(View.INVISIBLE);
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setVisibility(View.INVISIBLE);
        Button button = (Button) findViewById(R.id.close);
        button.setVisibility(View.INVISIBLE);
    }

    public void SendMe(View v) {
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setVisibility(View.VISIBLE);
        lv.setVisibility(View.VISIBLE);
        //  lv.requestLayout();
        Button button = (Button) findViewById(R.id.close);
        button.setVisibility(View.VISIBLE);
    }

    public void Enull(View v) {
        sPref = getPreferences(MODE_PRIVATE);
        sPref.edit().clear().apply();
        postsCount = BigInteger.ZERO;
        incrementEverySecond = BigInteger.ONE;
        buttonIncrement = BigInteger.ONE;
    }

    protected void onPause() {
        super.onPause();
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString("postsCount", postsCount.toString());
        ed.putString("incrementEverySecond", incrementEverySecond.toString());
        for (int i = 0; i < 10; i++) {
            ed.putString("price" + i, prices[i].toString());
        }
        for (int i = 0; i < 10; i++) {
            ed.putLong("count" + i, counts[i]);
        }
        ed.apply();
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
        postsCount = postsCount.add(buttonIncrement);
        Log.d("Dre", "Button pressed, postsCount = " + postsCount.toString() + " buttonIncrement = "
                + buttonIncrement.toString());
    }

}
