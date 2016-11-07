package com.yitong.yoga.bean;

/**
 * Created by linlongxin on 2016/1/26.
 */
public class curriculum {



    private String curriculum;
    private String time;
    private String coach;
    private String money;

    public curriculum(String curriculum, String time, String coach,String money) {
        this.curriculum = curriculum;
        this.time = time;
        this.coach = coach;
        this.money = money;
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
}
