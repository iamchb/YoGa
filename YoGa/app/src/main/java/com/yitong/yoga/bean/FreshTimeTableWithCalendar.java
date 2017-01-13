package com.yitong.yoga.bean;

/**
 * Created by chb on 2017/1/12
 */

public class FreshTimeTableWithCalendar {
    String  date;
    String type;

    public FreshTimeTableWithCalendar(String str,String type) {
        this.date=str;
        this.type=type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
