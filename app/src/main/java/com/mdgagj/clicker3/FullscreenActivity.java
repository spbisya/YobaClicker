package com.mdgagj.clicker3;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.plattysoft.leonids.ParticleSystem;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import me.grantland.widget.AutofitHelper;


public class FullscreenActivity extends AppCompatActivity {
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
    Button yobaButton;
    ListView lv;
    Resources res;
    ArrayList<TestItem> items;
    ObjectAnimator animationType2;
    Timer timer = new Timer();
    Timer timer1 = new Timer();
    TimerTask task = new TimerTask() {
        public void run() {
            if (!paused) postsCount = postsCount.add(incrementEverySecond);
        }
    };
    TimerTask task1 = new TimerTask() {

        public void run() {
            if (!paused) setText();
        }

    };
    RelativeLayout shopView, mainView;
    AnimatorSet setIn, setOut;
    Boolean paused = false, isShopping = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Window win = getWindow();
        win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_fullscreen);
        sPref = getPreferences(MODE_PRIVATE);
        res = getResources();
        Bitmap allah = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeResource(res, R.drawable.yoba_button),
                256, 256);
        yobaButton = (Button) findViewById(R.id.click);
        BitmapDrawable bdrawable = new BitmapDrawable(res, allah);
        yobaButton.setBackground(bdrawable);


        shopView = (RelativeLayout) findViewById(R.id.shopView);
        mainView = (RelativeLayout) findViewById(R.id.mainView);

        animationType2 = ObjectAnimator.ofFloat(yobaButton, "rotation", 0f, 360.0f);
        animationType2.setDuration(1000 * 60 * 1);
        animationType2.setRepeatCount(ValueAnimator.INFINITE);


        ObjectAnimator relativeIn = ObjectAnimator.ofFloat(shopView, "rotation", -90f, 0f);
        ObjectAnimator alphaIn = ObjectAnimator.ofFloat(shopView, "aplha", 0f, 1f);
        setIn = new AnimatorSet();
        setIn.playTogether(alphaIn, relativeIn);
        setIn.setDuration(300);

        ObjectAnimator relativeOut = ObjectAnimator.ofFloat(shopView, "rotation", 0f, 90f);
        ObjectAnimator alphaOut = ObjectAnimator.ofFloat(shopView, "alpha", 1f, 0f);
        setOut = new AnimatorSet();
        setOut.playTogether(relativeOut, alphaOut);
        setOut.setDuration(300);
        setOut.setStartDelay(100);
        setOut.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
                // TODO Auto-generated method stub
                mainView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                // TODO Auto-generated method stub
                shopView.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                // TODO Auto-generated method stub

            }
        });

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
                    } else {
                        Toast.makeText(FullscreenActivity.this, "Недостаточно багортов, чел!", Toast.LENGTH_SHORT).show();
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
                    } else {
                        Toast.makeText(FullscreenActivity.this, "Недостаточно багортов, чел!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        postsCount = new BigInteger(sPref.getString("postsCount", "0"));
        incrementEverySecond = new BigInteger(sPref.getString("incrementEverySecond", "1"));

        String postsViewText = "" + postsCount + " бугуртов";
        postsView.setText(postsViewText);
        String incrementViewText = "" + incrementEverySecond + " в секунду";
        incrementView.setText(incrementViewText);

        timer.schedule(task, 0, 1000);
        timer1.schedule(task1, 0, 100);
        animationType2.start();

        Button increase = (Button) findViewById(R.id.increase);
        increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postsCount = postsCount.multiply(new BigInteger("10"));

            }
        });

        Button achievements = (Button) findViewById(R.id.achievements);
        achievements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FullscreenActivity.this, AchievementsActivity.class);
                startActivity(intent);
            }
        });

        final Button close = (Button) findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeShop();
            }
        });

        Button open = (Button) findViewById(R.id.shop);
        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int width = shopView.getWidth();
                shopView.setPivotX(width);
                shopView.setPivotY(0);
                shopView.setVisibility(View.VISIBLE);
                mainView.setVisibility(View.INVISIBLE);
                setIn.start();
                isShopping = true;
                paused = true;
            }
        });
    }

    private void setText() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String postsViewText = "" + postsCount + " бугуртов";
                postsView.setText(postsViewText);
                String incrementViewText = "" + incrementEverySecond + " в секунду";
                incrementView.setText(incrementViewText);
                Log.d("Dre", "Text set, postsCount = " + postsCount.toString() + " buttonIncrement = "
                        + buttonIncrement.toString() + " incrementEverySecond = " + incrementEverySecond.toString());
            }
        });
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
        paused = true;
    }

    public void onResume() {
        super.onResume();
        paused = false;
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
        part.oneShot(findViewById(R.id.center), 5 + random.nextInt(20));
        postsCount = postsCount.add(buttonIncrement);
        Log.d("Dre", "Button pressed, postsCount = " + postsCount.toString() + " buttonIncrement = "
                + buttonIncrement.toString());
    }

    public void closeShop() {
        shopView.setPivotX(0);
        shopView.setPivotY(0);
        setOut.start();
        isShopping = false;
        paused = false;

    }


    public void onBackPressed() {
        if (isShopping) closeShop();
        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Уверен, что хошь ливнуть, пидр??")
                    .setPositiveButton("В натуре", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                        }
                    })
                    .setNegativeButton("Я подумаю...", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog

                        }
                    });
            AlertDialog d = builder.create();
            d.setTitle("Выпил");
            d.show();
        }
    }


}
