package com.mdgagj.clicker3;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
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
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.plattysoft.leonids.ParticleSystem;

import java.math.BigInteger;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.grantland.widget.AutofitHelper;


public class FullscreenActivity extends AppCompatActivity {
    private BigInteger incrementEverySecond = BigInteger.ONE;
    private BigInteger postsCount = BigInteger.ZERO;
    private BigInteger buttonIncrement = BigInteger.ONE;
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
    private SharedPreferences sPref;
    private Timer timer = new Timer();
    private Timer timer1 = new Timer();
    private TimerTask task = new TimerTask() {
        public void run() {
            if (!paused) postsCount = postsCount.add(incrementEverySecond);
        }
    };
    private TimerTask task1 = new TimerTask() {

        public void run() {
            if (!paused) setText();
        }

    };
    private Boolean paused = false;
    private ParticleSystem part;
    private Random random;
    private AnimatorSet animationIn, animationOut;
    private Boolean isShowing = false;


    @Bind(R.id.click)
    Button yobaButton;
    @Bind(R.id.posts)
    TextView postsView;
    @Bind(R.id.increment)
    TextView incrementView;
    @Bind(R.id.mainView)
    RelativeLayout mainView;
    @Bind(R.id.menuFab)
    FloatingActionButton menuFab;
    @Bind(R.id.shopFab)
    FloatingActionButton shopFab;
    @Bind(R.id.settingsFab)
    FloatingActionButton settingsFab;
    @Bind(R.id.achievementsFab)
    FloatingActionButton achievementsFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setWindowSettings();
        setContentView(R.layout.activity_fullscreen);
        ButterKnife.bind(this);
        loadPrefs();
        setupViews();
        setupAnimation();
        setupListeners();
        timer.schedule(task, 0, 1000);
        timer1.schedule(task1, 0, 100);
        mainView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                mainView.getViewTreeObserver().removeGlobalOnLayoutListener(this);

                int[] locations = new int[2];
                menuFab.getLocationOnScreen(locations);
                createAnimatorSets(menuFab);
                int x = locations[0];
                int y = locations[1];
                Log.d("DRE", "x=" + x + ", y=" + y);
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
            }
        });
    }

    public void Enull(View v) {
        sPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        sPref.edit().clear().apply();
        postsCount = BigInteger.ZERO;
        incrementEverySecond = BigInteger.ONE;
        buttonIncrement = BigInteger.ONE;
    }

    protected void onPause() {
        super.onPause();
        savePrefs();
        paused = true;
    }

    public void onResume() {
        super.onResume();
        loadPrefs();
//        if (isShowing) animationOut.start();
        paused = false;
    }

    public void onBackPressed() {
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

    private BitmapDrawable getDrawableFromRes() {
        Resources res = getResources();
        Bitmap allah = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeResource(res, R.drawable.yoba_button),
                256, 256);
        return new BitmapDrawable(res, allah);
    }

    private void setWindowSettings() {
        Window win = getWindow();
        win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            win.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    private void setupViews() {
        yobaButton.setBackground(getDrawableFromRes());

        Typeface face = Typeface.createFromAsset(getAssets(), "Roboto-Thin.ttf");
        postsView.setTypeface(face);
        incrementView.setTypeface(face);

        AutofitHelper.create(postsView).setMaxLines(1);
        AutofitHelper.create(incrementView).setMaxLines(1);

        postsCount = new BigInteger(sPref.getString("postsCount", "0"));
        incrementEverySecond = new BigInteger(sPref.getString("incrementEverySecond", "1"));

        String postsViewText = "" + postsCount + " бугуртов";
        postsView.setText(postsViewText);
        String incrementViewText = "" + incrementEverySecond + " в секунду";
        incrementView.setText(incrementViewText);
    }

    private void setupAnimation() {
        ObjectAnimator yobaAnimation = ObjectAnimator.ofFloat(yobaButton, "rotation", 0f, 360.0f);
        yobaAnimation.setDuration(1000 * 60);
        yobaAnimation.setRepeatMode(ValueAnimator.RESTART);
        yobaAnimation.setRepeatCount(ValueAnimator.INFINITE);
        yobaAnimation.setInterpolator(new LinearInterpolator());
        yobaAnimation.start();
    }

    private void createAnimatorSets(View v) {
        animationIn = new AnimatorSet();
        Log.d("DRE", v.getX() + " " + v.getY());
        float density = getResources().getDisplayMetrics().density;
        int fifteen = (int) (35 * density);
        ObjectAnimator animX = ObjectAnimator.ofFloat(settingsFab, "x", v.getX() - fifteen - v.getWidth());
        ObjectAnimator animY = ObjectAnimator.ofFloat(settingsFab, "y", v.getY());
        AnimatorSet animSetXY = new AnimatorSet();
        animSetXY.setDuration(250).playTogether(animX, animY);

        animX = ObjectAnimator.ofFloat(shopFab, "x", v.getX()  - v.getWidth());
        animY = ObjectAnimator.ofFloat(shopFab, "y", v.getY() - v.getHeight());
        AnimatorSet animSetXY2 = new AnimatorSet();
        animSetXY2.setDuration(250).playTogether(animX, animY);

        animX = ObjectAnimator.ofFloat(achievementsFab, "x", v.getX());
        animY = ObjectAnimator.ofFloat(achievementsFab, "y", v.getY() - fifteen - v.getHeight());
        AnimatorSet animSetXY3 = new AnimatorSet();
        animSetXY3.setDuration(250).playTogether(animX, animY);

        animationIn.playTogether(animSetXY, animSetXY2, animSetXY3);
        animationIn.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                isShowing = true;
                settingsFab.animate().alpha(1f).setDuration(250).start();
                shopFab.animate().alpha(1f).setDuration(250).start();
                achievementsFab.animate().alpha(1f).setDuration(250).start();
                menuFab.animate().rotation(90f).setDuration(250).start();
            }

            @Override
            public void onAnimationEnd(Animator animator) {
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        animationOut = new AnimatorSet();

        ObjectAnimator animX1 = ObjectAnimator.ofFloat(settingsFab, "x", v.getX());
        ObjectAnimator animY1 = ObjectAnimator.ofFloat(settingsFab, "y", v.getY());
        AnimatorSet animSetXY4 = new AnimatorSet();
        animSetXY4.setDuration(250).playTogether(animX1, animY1);

        animX1 = ObjectAnimator.ofFloat(shopFab, "x", v.getX());
        animY1 = ObjectAnimator.ofFloat(shopFab, "y", v.getY());
        AnimatorSet animSetXY5 = new AnimatorSet();
        animSetXY5.setDuration(250).playTogether(animX1, animY1);

        animX1 = ObjectAnimator.ofFloat(achievementsFab, "x", v.getX());
        animY1 = ObjectAnimator.ofFloat(achievementsFab, "y", v.getY());
        AnimatorSet animSetXY6 = new AnimatorSet();
        animSetXY6.setDuration(250).playTogether(animX1, animY1);

        animationOut.playTogether(animSetXY4, animSetXY5, animSetXY6);
        animationOut.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                isShowing = false;
                settingsFab.animate().alpha(0f).setDuration(250).start();
                shopFab.animate().alpha(0f).setDuration(250).start();
                achievementsFab.animate().alpha(0f).setDuration(250).start();
                menuFab.animate().rotation(0f).setDuration(250).start();
            }

            @Override
            public void onAnimationEnd(Animator animator) {

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    private void setupListeners() {
        menuFab.setOnClickListener(inListener);

        settingsFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postsCount = postsCount.multiply(new BigInteger("10"));
            }
        });

        achievementsFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FullscreenActivity.this, AchievementsActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.from_middle, R.anim.to_middle);
            }
        });

        shopFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FullscreenActivity.this, ShopActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.from_middle, R.anim.to_middle);
                savePrefs();
                paused = true;
            }
        });

        yobaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (isShowing) animationOut.start();
                random = new Random();
                part = new ParticleSystem(FullscreenActivity.this, 5 + random.nextInt(20), R.drawable.yoba_particle, 3000);
                part.setSpeedRange(0.2f, 0.5f).setRotationSpeed(144);
                part.oneShot(mainView, 5 + random.nextInt(20));
                postsCount = postsCount.add(buttonIncrement);
//                Log.d("Dre", "Button pressed, postsCount = " + postsCount.toString() + " buttonIncrement = "
//                        + buttonIncrement.toString());
            }
        });
    }

    View.OnClickListener inListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            animationIn.start();
            view.setOnClickListener(outListener);
        }
    };

    View.OnClickListener outListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            animationOut.start();
            view.setOnClickListener(inListener);
        }
    };


    private void savePrefs() {
        sPref = getSharedPreferences("clicker", 0);
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

    private void loadPrefs() {
        sPref = getSharedPreferences("clicker", 0);
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

}
