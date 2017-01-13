package com.yitong.yoga.bean;

import java.util.List;

/**
 * Created by chb on 2017/1/13
 */

public class BookingCalendarBean {


    /**
     * LIST : [{"ORDER_TIME":"2017-01-13"},{"ORDER_TIME":"2017-01-12"}]
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
         * ORDER_TIME : 2017-01-13
         */

        private String ORDER_TIME;

        public String getORDER_TIME() {
            return ORDER_TIME;
        }

        public void setORDER_TIME(String ORDER_TIME) {
            this.ORDER_TIME = ORDER_TIME;
        }
    }
}
