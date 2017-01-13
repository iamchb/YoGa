package com.yitong.yoga.bean;

import java.util.List;

/**
 * Created by chb on 2017/1/12
 */

public class ClaassDetal {


    /**
     * LIST : [{"CLASS_ADDR":"珠海市夏灣禦龍商務中心326","CLASS_DESC":"????11","CLASS_ID":3,"CLASS_NAME":"課程3","COACH_NAME":"陳海波","START_DATE":"2017-01-20","START_TIME":"15:00","TAKES_TIME":60}]
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
         * CLASS_ADDR : 珠海市夏灣禦龍商務中心326
         * CLASS_DESC : ????11
         * CLASS_ID : 3
         * CLASS_NAME : 課程3
         * COACH_NAME : 陳海波
         * START_DATE : 2017-01-20
         * START_TIME : 15:00
         * TAKES_TIME : 60
         */

        private String CLASS_ADDR;
        private String CLASS_DESC;
        private int CLASS_ID;
        private String CLASS_NAME;
        private String COACH_NAME;
        private String START_DATE;
        private String START_TIME;
        private int TAKES_TIME;

        public String getCLASS_ADDR() {
            return CLASS_ADDR;
        }

        public void setCLASS_ADDR(String CLASS_ADDR) {
            this.CLASS_ADDR = CLASS_ADDR;
        }

        public String getCLASS_DESC() {
            return CLASS_DESC;
        }

        public void setCLASS_DESC(String CLASS_DESC) {
            this.CLASS_DESC = CLASS_DESC;
        }

        public int getCLASS_ID() {
            return CLASS_ID;
        }

        public void setCLASS_ID(int CLASS_ID) {
            this.CLASS_ID = CLASS_ID;
        }

        public String getCLASS_NAME() {
            return CLASS_NAME;
        }

        public void setCLASS_NAME(String CLASS_NAME) {
            this.CLASS_NAME = CLASS_NAME;
        }

        public String getCOACH_NAME() {
            return COACH_NAME;
        }

        public void setCOACH_NAME(String COACH_NAME) {
            this.COACH_NAME = COACH_NAME;
        }

        public String getSTART_DATE() {
            return START_DATE;
        }

        public void setSTART_DATE(String START_DATE) {
            this.START_DATE = START_DATE;
        }

        public String getSTART_TIME() {
            return START_TIME;
        }

        public void setSTART_TIME(String START_TIME) {
            this.START_TIME = START_TIME;
        }

        public int getTAKES_TIME() {
            return TAKES_TIME;
        }

        public void setTAKES_TIME(int TAKES_TIME) {
            this.TAKES_TIME = TAKES_TIME;
        }

        @Override
        public String toString() {
            return "LISTBean{" +
                    "CLASS_ADDR='" + CLASS_ADDR + '\'' +
                    ", CLASS_DESC='" + CLASS_DESC + '\'' +
                    ", CLASS_ID=" + CLASS_ID +
                    ", CLASS_NAME='" + CLASS_NAME + '\'' +
                    ", COACH_NAME='" + COACH_NAME + '\'' +
                    ", START_DATE='" + START_DATE + '\'' +
                    ", START_TIME='" + START_TIME + '\'' +
                    ", TAKES_TIME=" + TAKES_TIME +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ClaassDetal{" +
                "MSG='" + MSG + '\'' +
                ", STATUS='" + STATUS + '\'' +
                ", LIST=" + LIST +
                '}';
    }
}
