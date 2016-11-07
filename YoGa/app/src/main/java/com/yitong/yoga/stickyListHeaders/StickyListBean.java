package com.yitong.yoga.stickyListHeaders;

public class StickyListBean {
    private int section;
    private String YM;//头
    private String content;//课程名称
    private String classTeacher;
    private String classLocation;
    private String classTime;
    private String classTimeFlag;

    public StickyListBean(int section, String YM, String content, String classTeacher, String classLocation, String classTime, String classTimeFlag) {
        this.section = section;
        this.YM = YM;
        this.content = content;
        this.classTeacher = classTeacher;
        this.classLocation = classLocation;
        this.classTime = classTime;
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

    public String getClassTimeFlag() {
        return classTimeFlag;
    }

    public void setClassTimeFlag(String classTimeFlag) {
        this.classTimeFlag = classTimeFlag;
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
                ", classTimeFlag='" + classTimeFlag + '\'' +
                '}';
    }
}
