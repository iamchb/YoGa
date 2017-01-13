package com.yitong.yoga.bean;

import java.util.List;

/**
 * Created by chb on 2017/1/12
 */

public class DateBean {


    /**
     * LIST : [{"START_DATE":"2017-01-12"},{"START_DATE":"2017-01-13"}]
     * MSG : 交易成功
     * STATUS : 1
     */

    private String MSG;
    private String STATUS;
    private List<LISTBean> LIST;

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

    public List<LISTBean> getLIST() {
        return LIST;
    }

    public void setLIST(List<LISTBean> LIST) {
        this.LIST = LIST;
    }

    public static class LISTBean {
        /**
         * START_DATE : 2017-01-12
         */

        private String START_DATE;

        public String getSTART_DATE() {
            return START_DATE;
        }

        public void setSTART_DATE(String START_DATE) {
            this.START_DATE = START_DATE;
        }
    }

    @Override
    public String toString() {
        return "DateBean{" +
                "MSG='" + MSG + '\'' +
                ", STATUS='" + STATUS + '\'' +
                ", LIST=" + LIST +
                '}';
    }
}
