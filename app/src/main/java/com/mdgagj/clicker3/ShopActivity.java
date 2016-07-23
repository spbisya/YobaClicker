package com.mdgagj.clicker3;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TimingLogger;
import android.view.Window;
import android.view.WindowManager;

import com.mdgagj.clicker3.adapters.ShopAdapter;
import com.mdgagj.clicker3.models.ShopItem;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Project YobaClicker. Created by gwa on 7/22/16.
 */

public class ShopActivity extends AppCompatActivity {
    @Bind(R.id.shopView)
    RecyclerView recyclerView;
    private List<ShopItem> items;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TimingLogger timingLogger = new TimingLogger("DRE", "onCreate");
        Log.d("DRE", "Is Loggable? " + Log.isLoggable("DRE", Log.VERBOSE));
        setWindowSettings();
        timingLogger.addSplit("setWindow");
        setContentView(R.layout.shop_activity);
        ButterKnife.bind(this);
        timingLogger.addSplit("layout and butterknife");
        recyclerView.setHasFixedSize(true);
        timingLogger.addSplit("size");
        LinearLayoutManager llm = new LinearLayoutManager(this);
        timingLogger.addSplit("llm create");
        recyclerView.setLayoutManager(llm);
        timingLogger.addSplit("set llm");
        sPref = getSharedPreferences("clicker", 0);
        timingLogger.addSplit("get prefs");
        initializeData();
        timingLogger.addSplit("create array");
        ShopAdapter adapter = new ShopAdapter(items);
        timingLogger.addSplit("create adapter");
        recyclerView.setAdapter(adapter);
        timingLogger.addSplit("setAdapter");
        timingLogger.dumpToLog();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.from_middle, R.anim.to_middle);
    }

    private void initializeData() {
        loadPrefs();
        items = new ArrayList<>();
        items.add(new ShopItem(R.drawable.favicon, "Помолиться Аллаху", "X2", prices[0], counts[0], 0, sPref, new BigInteger("2")));
        items.add(new ShopItem(R.drawable.favicon, "Личинус", "+1", prices[1], counts[1], 1, sPref, new BigInteger("1")));
        items.add(new ShopItem(R.drawable.favicon, "Тётя Йоба", "+3", prices[2], counts[2], 1, sPref, new BigInteger("3")));
        items.add(new ShopItem(R.drawable.favicon, "Дядя Больжедор", "+10", prices[3], counts[3], 1, sPref, new BigInteger("10")));
        items.add(new ShopItem(R.drawable.favicon, "Дед", "+100", prices[4], counts[4], 1, sPref, new BigInteger("100")));
        items.add(new ShopItem(R.drawable.favicon, "Батя Бафомет", "+500", prices[5], counts[5], 1, sPref, new BigInteger("500")));
        items.add(new ShopItem(R.drawable.favicon, "Паук Ар  кадий", "+1k", prices[6], counts[6], 1, sPref, new BigInteger("1000")));
        items.add(new ShopItem(R.drawable.favicon, "Le maman", "+2k", prices[7], counts[7], 1, sPref, new BigInteger("2000")));
        items.add(new ShopItem(R.drawable.favicon, "Ванечка Ерохин", "+10k", prices[8], counts[8], 1, sPref, new BigInteger("10000")));
        items.add(new ShopItem(R.drawable.favicon, "ЕОТ", "+50k", prices[9], counts[9], 1, sPref, new BigInteger("50000")));
    }

    private void setWindowSettings() {
        Window win = getWindow();
        win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            win.setStatusBarColor(Color.parseColor("#3b3b3b"));
        }
    }


    private void loadPrefs() {
        sPref = getSharedPreferences("clicker", 0);
        for (int i = 0; i < 10; i++) {
            prices[i] = new BigInteger(sPref.getString("price" + i, prices[i].toString()));
        }
        for (int i = 0; i < 10; i++) {
            counts[i] = sPref.getLong("count" + i, 0L);
        }
    }
}
