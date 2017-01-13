package com.yitong.yoga.utils;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by chb on 2017/1/10
 */

public class RegularExpressionUtil {

    public static final String EMAIL_REG = "^([a-zA-Z0-9_\\.\\-])+\\@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,4})+$";

    /*
   移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
   联通：130、131、132、152、155、156、185、186
   电信：133、153、180、189、（1349卫通）
   总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
   "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
   */
    public static final String MOBILE_REG = "[1][358]\\d{9}";

    public static final String MOBILE_HONGKAN = "^([6|9])\\d{7}$";
    public static final String MOBILE_AOMEN = "^[0][9]\\d{8}$";

    public static boolean isEmail(String email) {

        if (TextUtils.isEmpty(email)) {
            return false;
        } else {
            return email.matches(EMAIL_REG);
        }
    }

    /**
     * 验证手机格式
     */
    public static boolean isMobile(String mobiles) {

        if (TextUtils.isEmpty(mobiles)) return false;
        else return mobiles.matches(MOBILE_REG);
    }

    public static boolean isMobileInHongKong(String mobiles) {

        if (TextUtils.isEmpty(mobiles)) return false;
        else return mobiles.matches(MOBILE_HONGKAN);
    }

    public static boolean isMobileInAoMen(String mobiles) {

        if (TextUtils.isEmpty(mobiles)) return false;
        else return mobiles.matches(MOBILE_AOMEN);
    }


    private static boolean CheckNum(String src)
    {
        Pattern pattern = Pattern.compile("[0-9]+");
        Matcher matcher = pattern.matcher((CharSequence) src);
        boolean result = matcher.matches();
       return  result;
    }
    public static boolean isMobileOrEmail(String area, String mobiles) {

        switch (area) {
            case "86"://内地

                if (isMobile(mobiles)&&CheckNum(mobiles)) {
                    return true;
                }

            break;

            case "853"://香港
                if (mobiles.length()==8&&CheckNum(mobiles)) {
                    return true;
                }
            break;

            case "852"://澳门
                if (mobiles.length()==8&&CheckNum(mobiles)) {
                    return true;
                }
            break;

            case "email"://邮箱
                if (isEmail(mobiles)) {
                    return true;
                }
            break;

            default:
                break;

        }
        return false;
    }

}
