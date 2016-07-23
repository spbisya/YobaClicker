package com.mdgagj.clicker3.models;

import android.content.SharedPreferences;

import java.math.BigInteger;

/**
 * Project YobaClicker. Created by gwa on 7/22/16.
 */

public class ShopItem {
    /**
     * type - 0-Click, 1-Cps (текст, который показывается перед cps_value)
     * itemDescr - описание вещи
     * imageIco - ссылка на ресурс с картинкой
     * level - сколько раз было куплена та или иная вещь
     * price - цена вещи
     * cps_value - значение ускорителя
     */
    private int type, imageIco;
    private long level;
    private String itemDescr, bpsValue;
    private BigInteger price;
    private BigInteger increment;
    private SharedPreferences sPref;

    public ShopItem(int imageIco, String itemDescr, String bpsValue, BigInteger price, long level,
                    int type, SharedPreferences sPref, BigInteger increment) {
        this.type = type;
        this.imageIco = imageIco;
        this.level = level;
        this.itemDescr = itemDescr;
        this.bpsValue = bpsValue;
        this.price = price;
        this.increment = increment;
        this.sPref = sPref;
    }

    public SharedPreferences getsPref() {
        return sPref;
    }

    public int getType() {
        return type;
    }

    public int getImageIco() {
        return imageIco;
    }

    public long getLevel() {
        return level;
    }

    public void setLevel(long level) {
        this.level = level;
    }

    public String getItemDescr() {
        return itemDescr;
    }

    public String getBpsValue() {
        return bpsValue;
    }

    public BigInteger getPrice() {
        return price;
    }

    public void setPrice(BigInteger price) {
        this.price = price;
    }

    public BigInteger getIncrement() {
        return increment;
    }

}
