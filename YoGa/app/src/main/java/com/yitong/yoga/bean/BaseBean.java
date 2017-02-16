package com.yitong.yoga.bean;

/**
 * Created by chb on 2017/2/6
 */

public class BaseBean {


    /**
     * MSG : 交易成功
     * STATUS : 1
     */

    private String MSG;
    private String STATUS;

    public String getMSG() {
        return MSG;
    }

    public void setMSG(String MSG) {
        this.MSG = MSG;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    @Override
    public String toString() {
        return "BaseBean{" +
                "MSG='" + MSG + '\'' +
                ", STATUS='" + STATUS + '\'' +
                '}';
    }
}
