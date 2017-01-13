package com.yitong.yoga.bean;

import java.util.List;

/**
 * Created by chb on 2017/1/11
 */

public class TimeTableBean {

//    {
//        "LIST": [
//        {
//            "2017-01-12": [
//            {
//                "CLASS_ADDR": "珠海市夏灣禦龍商務中心326",
//                    "CLASS_ID": 1,
//                    "CLASS_NAME": "課程2",
//                    "COACH_NAME": "陳海波",
//                    "START_DATE": "2017-01-12",
//                    "START_TIME": "15:00"
//            },
//            {
//                "CLASS_ADDR": "珠海市夏灣禦龍商務中心326",
//                    "CLASS_ID": 2,
//                    "CLASS_NAME": "課程2",
//                    "COACH_NAME": "陳海波",
//                    "START_DATE": "2017-01-12",
//                    "START_TIME": "15:00"
//            }
//            ],
//            "START_DATE": "2017-01-12"
//        },
//        {
//            "2017-01-13": [
//            {
//                "CLASS_ADDR": "珠海市夏灣禦龍商務中心326",
//                    "CLASS_ID": 3,
//                    "CLASS_NAME": "課程3",
//                    "COACH_NAME": "陳海波",
//                    "START_DATE": "2017-01-13",
//                    "START_TIME": "15:00"
//            }
//            ],
//            "START_DATE": "2017-01-13"
//        }
//        ],
//        "MSG": "交易成功",
//            "STATUS": "1"
//    }

    /**
     * LIST : [{"2017-01-12":[{"CLASS_ADDR":"珠海市夏灣禦龍商務中心326","CLASS_ID":1,"CLASS_NAME":"課程2","COACH_NAME":"陳海波","START_DATE":"2017-01-12","START_TIME":"15:00"},{"CLASS_ADDR":"珠海市夏灣禦龍商務中心326","CLASS_ID":2,"CLASS_NAME":"課程2","COACH_NAME":"陳海波","START_DATE":"2017-01-12","START_TIME":"15:00"}],"START_DATE":"2017-01-12"},{"2017-01-13":[{"CLASS_ADDR":"珠海市夏灣禦龍商務中心326","CLASS_ID":3,"CLASS_NAME":"課程3","COACH_NAME":"陳海波","START_DATE":"2017-01-13","START_TIME":"15:00"}],"START_DATE":"2017-01-13"}]
     * MSG : 交易成功
     * STATUS : 1
     */

    private String MSG;
    private String STATUS;
    private List<MapBean> LIST;

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

    public List<MapBean> getLIST() {
        return LIST;
    }

    public void setLIST(List<MapBean> LIST) {
        this.LIST = LIST;
    }

    public static class MapBean {
//                "CLASS_ADDR": "珠海市夏灣禦龍商務中心326",
//                "CLASS_ID": 1,
//                "CLASS_NAME": "課程2",
//                "COACH_NAME": "陳海波",
//                "START_DATE": "2017-01-12",
//                "START_TIME": "15:00"

        private String ADDR;
        private String CLASS_ID;
        private String COACH_NAME;
        private String START_DATE;
        private String START_TIME;

        public String getADDR() {
            return ADDR;
        }

        public void setADDR(String ADDR) {
            this.ADDR = ADDR;
        }

        public String getCLASS_ID() {
            return CLASS_ID;
        }

        public void setCLASS_ID(String CLASS_ID) {
            this.CLASS_ID = CLASS_ID;
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

        @Override
        public String toString() {
            return "MapBean{" +
                    "ADDR='" + ADDR + '\'' +
                    ", CLASS_ID='" + CLASS_ID + '\'' +
                    ", COACH_NAME='" + COACH_NAME + '\'' +
                    ", START_DATE='" + START_DATE + '\'' +
                    ", START_TIME='" + START_TIME + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "TimeTableBean{" +
                "MSG='" + MSG + '\'' +
                ", STATUS='" + STATUS + '\'' +
                ", LIST=" + LIST +
                '}';
    }
}
