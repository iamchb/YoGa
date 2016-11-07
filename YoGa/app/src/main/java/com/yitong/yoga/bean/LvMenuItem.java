package com.yitong.yoga.bean;

import android.text.TextUtils;
import android.util.Log;

/**
 * YoGa
 * Created by Chb on  2016/10/20 10:29
 */

public class LvMenuItem {

    private static final int NO_ICON = 0;
    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_NO_ICON = 1;
    public static final int TYPE_SEPARATOR = 2;

    private int type;
    private String name;
    private int icon;


    public LvMenuItem(int icon, String name) {
        this.icon = icon;
        this.name = name;

        if (icon == NO_ICON && TextUtils.isEmpty(name)) {
            type = TYPE_SEPARATOR;
        } else if (icon == NO_ICON) {
            type = TYPE_NO_ICON;
        } else {
            type = TYPE_NORMAL;
        }

        if (type != TYPE_SEPARATOR && TextUtils.isEmpty(name)) {
            throw new IllegalArgumentException("you need st a name for a non-SEPARATOR item");
        }

        Log.e("LvMenuItem", type + "");


    }

    public LvMenuItem(String name) {
        this(NO_ICON, name);
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public LvMenuItem() {
        this(null);
    }




}

