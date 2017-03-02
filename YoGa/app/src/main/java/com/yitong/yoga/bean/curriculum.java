package com.yitong.yoga.bean;

/**
 * Created by linlongxin on 2016/1/26
 */
public class Curriculum {



    private String curriculum;
    private String time;
    private String coach;
    private String money;
    private String class_id;
    private String code_id;
    private String ORDER_STATUS;
    private String ORDER_MONEY;;



    public Curriculum(String curriculum, String time, String coach, String money,String class_id,String code_id,String ORDER_STATUS,String ORDER_MONEY) {
        this.curriculum = curriculum;
        this.time = time;
        this.coach = coach;
        this.money = money;
        this.class_id=class_id;
        this.code_id=code_id;
        this. ORDER_STATUS=ORDER_STATUS;
        this.ORDER_MONEY=ORDER_MONEY;
    }

    public String getORDER_STATUS() {
        return ORDER_STATUS;
    }

    public void setORDER_STATUS(String ORDER_STATUS) {
        this.ORDER_STATUS = ORDER_STATUS;
    }

    public String getClass_id() {
        return class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }

    public String getCode_id() {
        return code_id;
    }

    public void setCode_id(String code_id) {
        this.code_id = code_id;
    }

    public String getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(String curriculum) {
        this.curriculum = curriculum;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCoach() {
        return coach;
    }

    public void setCoach(String coach) {
        this.coach = coach;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getORDER_MONEY() {
        return ORDER_MONEY;
    }

    public void setORDER_MONEY(String ORDER_MONEY) {
        this.ORDER_MONEY = ORDER_MONEY;
    }

    @Override
    public String toString() {
        return "Curriculum{" +
                "class_id='" + class_id + '\'' +
                ", curriculum='" + curriculum + '\'' +
                ", time='" + time + '\'' +
                ", coach='" + coach + '\'' +
                ", money='" + money + '\'' +
                ", code_id='" + code_id + '\'' +
                ", ORDER_STATUS='" + ORDER_STATUS + '\'' +
                ", ORDER_MONEY='" + ORDER_MONEY + '\'' +
                '}';
    }
}
