package com.mdgagj.clicker3;

import android.content.res.AssetManager;

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
    int level, type, imageIco;
    String itemDescr, cps_value;
    long price;
    Double increment;
    AssetManager assets;


    TestItem(int imageIco, String itemDescr, String cps_value, long price, int level, int type, AssetManager assets, Double increment) {
        this.imageIco = imageIco;
        this.itemDescr = itemDescr;
        this.cps_value = cps_value;
        this.price = price;
        this.level = level;
        this.type = type;
        this.assets = assets;
        this.increment=increment;
    }

    public Double getIncrement() {
        return increment;
    }

    public AssetManager getAssets() {
        return assets;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setImageIco(int imageIco) {
        this.imageIco = imageIco;
    }

    public void setItemDescr(String itemDescr) {
        this.itemDescr = itemDescr;
    }

    public void setCps_value(String cps_value) {
        this.cps_value = cps_value;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public int getLevel() {
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

    public long getPrice() {
        return price;
    }
}
