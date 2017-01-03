package com.yitong.yoga.utils;

import android.content.Context;

import java.util.Locale;

/**
 * Created by iamch on 2016/7/28
 */
public class LangeuageUtils {

    public static boolean isCn(Context context) {
        Locale locale = context.getResources().getConfiguration().locale;
        String country= locale.getCountry();
        Logs.e("TAG",country);
        return country.equals("CN");
    }
}
