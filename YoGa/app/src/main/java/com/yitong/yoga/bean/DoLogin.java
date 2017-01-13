package com.yitong.yoga.bean;

import android.os.Parcel;

import java.util.List;

/**
 * Created by iamch on 2016/6/13
 */
public class DoLogin {
    /**
     * LIST : [{"agent_code":"M638","agent_contact_id":"03A758CE25586E9BE0539715A8C0CB01","agent_id":"03A6FFF41D48694DE0539715A8C073FF","area_code":"+853","area_code_id":"03A758CE1F246E9BE0539715A8C0CB01","telephone_number":"62075169"},{"agent_code":"MA9888","agent_contact_id":"03A758CE25586E9BE0539715A8C0CB01","agent_id":"03A6FFF41D64694DE0539715A8C073FF","area_code":"+853","area_code_id":"03A758CE1F246E9BE0539715A8C0CB01","telephone_number":"62075169"}]
     * MSG : 交易成功
     * STATUS : 1
     */
//    接口返回{"ID":"22","MSG":"交易成功","STATUS":"1"}
    private String MSG;
    private String STATUS;
    private String ID;
    /**
     * agent_code : M638
     * agent_contact_id : 03A758CE25586E9BE0539715A8C0CB01
     * agent_id : 03A6FFF41D48694DE0539715A8C073FF
     * area_code : +853
     * area_code_id : 03A758CE1F246E9BE0539715A8C0CB01
     * telephone_number : 62075169
     */

    private List<LISTBean> LIST;

    protected DoLogin(Parcel in) {
        MSG = in.readString();
        STATUS = in.readString();
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

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
        private String agent_code;
        private String agent_contact_id;
        private String agent_id;
        private String area_code;
        private String area_code_id;
        private String telephone_number;

        public String getAgent_code() {
            return agent_code;
        }

        public void setAgent_code(String agent_code) {
            this.agent_code = agent_code;
        }

        public String getAgent_contact_id() {
            return agent_contact_id;
        }

        public void setAgent_contact_id(String agent_contact_id) {
            this.agent_contact_id = agent_contact_id;
        }

        public String getAgent_id() {
            return agent_id;
        }

        public void setAgent_id(String agent_id) {
            this.agent_id = agent_id;
        }

        public String getArea_code() {
            return area_code;
        }

        public void setArea_code(String area_code) {
            this.area_code = area_code;
        }

        public String getArea_code_id() {
            return area_code_id;
        }

        public void setArea_code_id(String area_code_id) {
            this.area_code_id = area_code_id;
        }

        public String getTelephone_number() {
            return telephone_number;
        }

        public void setTelephone_number(String telephone_number) {
            this.telephone_number = telephone_number;
        }

        @Override
        public String toString() {
            return "LISTBean{" +
                    "agent_code='" + agent_code + '\'' +
                    ", agent_contact_id='" + agent_contact_id + '\'' +
                    ", agent_id='" + agent_id + '\'' +
                    ", area_code='" + area_code + '\'' +
                    ", area_code_id='" + area_code_id + '\'' +
                    ", telephone_number='" + telephone_number + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "DoLogin{" +
                "MSG='" + MSG + '\'' +
                ", STATUS='" + STATUS + '\'' +
                ", ID='" + ID + '\'' +
                ", LIST=" + LIST +
                '}';
    }
}
