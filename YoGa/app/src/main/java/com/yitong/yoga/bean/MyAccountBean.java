package com.yitong.yoga.bean;

/**
 * Created by chb on 2017/1/11
 */

public class MyAccountBean {
    /**
     * MSG : 交易成功
     * Map : {"accountBalance":"1000","consumption_last":"800","consumption_this":"500","residualFrequency":"2"}
     * STATUS : 1
     */

    private String MSG;
    private MapBean Map;
    private String STATUS;

    public String getMSG() {
        return MSG;
    }

    public void setMSG(String MSG) {
        this.MSG = MSG;
    }

    public MapBean getMap() {
        return Map;
    }

    public void setMap(MapBean Map) {
        this.Map = Map;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public static class MapBean {
        /**
         * accountBalance : 1000
         * consumption_last : 800
         * consumption_this : 500
         * residualFrequency : 2
         */

        private String accountBalance;
        private String consumption_last;
        private String consumption_this;
        private String residualFrequency;

        public String getAccountBalance() {
            return accountBalance;
        }

        public void setAccountBalance(String accountBalance) {
            this.accountBalance = accountBalance;
        }

        public String getConsumption_last() {
            return consumption_last;
        }

        public void setConsumption_last(String consumption_last) {
            this.consumption_last = consumption_last;
        }

        public String getConsumption_this() {
            return consumption_this;
        }

        public void setConsumption_this(String consumption_this) {
            this.consumption_this = consumption_this;
        }

        public String getResidualFrequency() {
            return residualFrequency;
        }

        public void setResidualFrequency(String residualFrequency) {
            this.residualFrequency = residualFrequency;
        }
    }


}
