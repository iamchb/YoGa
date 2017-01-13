package com.yitong.yoga.stickyListHeaders;

public class StickyListBean {
    //    {"LIST":
// [{"CLASS_ADDR":"???????????326?","CLASS_ID":1,"CLASS_NAME":"????2","COACH_NAME":"???","START_DATE":"2017-01-12","START_TIME":"15:00"},
// {"CLASS_ADDR":"???????????326?","CLASS_ID":2,"CLASS_NAME":"????2","COACH_NAME":"???","START_DATE":"2017-01-12","START_TIME":"15:00"},
// {"CLASS_ADDR":"???????????326?","CLASS_ID":3,"CLASS_NAME":"????3","COACH_NAME":"???","START_DATE":"2017-01-13","START_TIME":"15:00"}
// ],
// "MSG":"交易成功","STATUS":"1"}

//    {"LIST":[{"2017-01-12":[{"CLASS_ADDR":"珠海市夏灣禦龍商務中心326","CLASS_ID":1,"CLASS_NAME":"課程2","COACH_NAME":"陳海波","START_DATE":"2017-01-12","START_TIME":"15:00"},{"CLASS_ADDR":"珠海市夏灣禦龍商務中心326","CLASS_ID":2,"CLASS_NAME":"課程2","COACH_NAME":"陳海波","START_DATE":"2017-01-12","START_TIME":"15:00"}],"START_DATE":"2017-01-12"},{"2017-01-13":[{"CLASS_ADDR":"珠海市夏灣禦龍商務中心326","CLASS_ID":3,"CLASS_NAME":"課程3","COACH_NAME":"陳海波","START_DATE":"2017-01-13","START_TIME":"15:00"}],"START_DATE":"2017-01-13"}],"MSG":"交易成功","STATUS":"1"}

    private int section;
    private String YM;//头
    private String content;//课程名称
    private String classTeacher;
    private String classLocation;
    private String classTime;
    private String id;
    private String classTimeFlag;
    public StickyListBean(int section, String YM, String content, String classTeacher, String classLocation, String classTime, String id,String classTimeFlag) {
        this.section = section;
        this.YM = YM;
        this.content = content;
        this.classTeacher = classTeacher;
        this.classLocation = classLocation;
        this.classTime = classTime;
        this.id = id;
        this.classTimeFlag=classTimeFlag;
    }

    public String getClassTimeFlag() {
        return classTimeFlag;
    }

    public void setClassTimeFlag(String classTimeFlag) {
        this.classTimeFlag = classTimeFlag;
    }

    public int getSection() {
        return section;
    }

    public void setSection(int section) {
        this.section = section;
    }

    public String getYM() {
        return YM;
    }

    public void setYM(String YM) {
        this.YM = YM;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getClassTeacher() {
        return classTeacher;
    }

    public void setClassTeacher(String classTeacher) {
        this.classTeacher = classTeacher;
    }

    public String getClassLocation() {
        return classLocation;
    }

    public void setClassLocation(String classLocation) {
        this.classLocation = classLocation;
    }

    public String getClassTime() {
        return classTime;
    }

    public void setClassTime(String classTime) {
        this.classTime = classTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "StickyListBean{" +
                "section=" + section +
                ", YM='" + YM + '\'' +
                ", content='" + content + '\'' +
                ", classTeacher='" + classTeacher + '\'' +
                ", classLocation='" + classLocation + '\'' +
                ", classTime='" + classTime + '\'' +
                ", id='" + id + '\'' +
                ", classTimeFlag='" + classTimeFlag + '\'' +
                '}';
    }
}
