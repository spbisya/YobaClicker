package com.mdgagj.clicker3;

import android.content.res.AssetManager;

import java.math.BigInteger;

/**
 * Created by 777 on 1/2/2016.
 */
public class TestItem {

    /**
     * type - 0-Click, 1-Cps (текст, который показывается перед cps_value)
     * itemDescr - описание вещи
     * imageIco - ссылка на ресурс с картинкой
     * level - сколько раз было куплена та или иная вещь
     * price - цена вещи
     * cps_value - значение ускорителя
     */
  private   int type, imageIco;
    private long level;
    private  String itemDescr, cps_value;
    private  BigInteger price;
    private  BigInteger increment;
    private  AssetManager assets;


    TestItem(int imageIco, String itemDescr, String cps_value, BigInteger price, long level, int type, AssetManager assets, BigInteger increment) {
        this.imageIco = imageIco;
        this.itemDescr = itemDescr;
        this.cps_value = cps_value;
        this.price = price;
        this.level = level;
        this.type = type;
        this.assets = assets;
        this.increment=increment;
    }

    public BigInteger getIncrement() {
        return increment;
    }

    public AssetManager getAssets() {
        return assets;
    }

    public void setLevel(long level) {
        this.level = level;
    }

    public void setPrice(BigInteger price) {
        this.price = price;
    }

    public long getLevel() {
        return level;
    }

    public int getType() {
        return type;
    }

    public int getImageIco() {
        return imageIco;
    }

    public String getItemDescr() {
        return itemDescr;
    }

    public String getCps_value() {
        return cps_value;
    }

    public BigInteger getPrice() {
        return price;
    }
}
