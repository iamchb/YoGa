package com.yitong.yoga.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by iamch on 2016/6/15
 */
public class LoginUserInfo implements Parcelable {

    private String agent_code;//账户号码
    private String agent_contact_id;//账户联系人ID
    private String agent_id;//账户ID
    private String area_code;//区号
    private String area_code_id;//区号ID
    private String phone_number;//电话
    private String LOGIN_PASS;//密码、IVR码、验证码
    private String CLIENT_OS_TYPE;//设备类型
    private String PEVICE_UUID;//设备ID
    private String LOGIN_PAY;//交易密码

    //戶口資料 菜單及功能鍵需要檢查CHECK 權限是否為1, 0則顯示權限不足訊息
    private String CHECK;

    //即時訂房, 寄賣, 續訂房間 菜單及功能鍵需要檢查CONSUMPTION 權限是否為1, 0則顯示權限不足訊息
    private String CONSUMPTION;

//    protected LoginUserInfo(Parcel in) {
//        agent_code = in.readString();
//        agent_contact_id = in.readString();
//        agent_id = in.readString();
//        area_code = in.readString();
//        area_code_id = in.readString();
//        phone_number = in.readString();
//        LOGIN_PASS = in.readString();
//        CLIENT_OS_TYPE = in.readString();
//        PEVICE_UUID = in.readString();
//        LOGIN_PAY = in.readString();
//        CHECK=in.readString();
//        CONSUMPTION=in.readString();
//    }

    public static final Creator<LoginUserInfo> CREATOR = new Creator<LoginUserInfo>() {
        @Override
        public LoginUserInfo createFromParcel(Parcel in) {
            LoginUserInfo loginUserInfo=new LoginUserInfo();

            loginUserInfo. agent_code=in.readString();//账户号码
            loginUserInfo. agent_contact_id=in.readString();//账户联系人ID
            loginUserInfo.agent_id=in.readString();//账户ID
            loginUserInfo.area_code=in.readString();//区号
            loginUserInfo. area_code_id=in.readString();//区号ID
            loginUserInfo.phone_number=in.readString();//电话
            loginUserInfo. LOGIN_PASS=in.readString();//密码、IVR码、验证码
            loginUserInfo. CLIENT_OS_TYPE=in.readString();//设备类型
            loginUserInfo. PEVICE_UUID=in.readString();//设备ID
            loginUserInfo. LOGIN_PAY=in.readString();
            loginUserInfo.CHECK=in.readString();
            loginUserInfo.CONSUMPTION=in.readString();
//            return new LoginUserInfo(in);
            return loginUserInfo;
        }

        @Override
        public LoginUserInfo[] newArray(int size) {
            return new LoginUserInfo[size];
        }
    };

    public LoginUserInfo() {

    }

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

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getLOGIN_PASS() {
        return LOGIN_PASS;
    }

    public void setLOGIN_PASS(String LOGIN_PASS) {
        this.LOGIN_PASS = LOGIN_PASS;
    }

    public String getCLIENT_OS_TYPE() {
        return CLIENT_OS_TYPE;
    }

    public void setCLIENT_OS_TYPE(String CLIENT_OS_TYPE) {
        this.CLIENT_OS_TYPE = CLIENT_OS_TYPE;
    }

    public String getPEVICE_UUID() {
        return PEVICE_UUID;
    }

    public void setPEVICE_UUID(String PEVICE_UUID) {
        this.PEVICE_UUID = PEVICE_UUID;
    }

    public String getLOGIN_PAY() {
        return LOGIN_PAY;
    }

    public void setLOGIN_PAY(String LOGIN_PAY) {
        this.LOGIN_PAY = LOGIN_PAY;
    }

    public String getCONSUMPTION() {
        return CONSUMPTION;
    }

    public void setCONSUMPTION(String CONSUMPTION) {
        this.CONSUMPTION = CONSUMPTION;
    }

    public String getCHECK() {
        return CHECK;
    }

    public void setCHECK(String CHECK) {
        this.CHECK = CHECK;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(agent_code);
        parcel.writeString(agent_contact_id);
        parcel.writeString(agent_id);
        parcel.writeString(area_code);
        parcel.writeString(area_code_id);
        parcel.writeString(phone_number);
        parcel.writeString(LOGIN_PASS);
        parcel.writeString(CLIENT_OS_TYPE);
        parcel.writeString(PEVICE_UUID);
        parcel.writeString(LOGIN_PAY);
        parcel.writeString(CHECK);
        parcel.writeString(CONSUMPTION);
    }

    @Override
    public String toString() {
        return "LoginUserInfo{" +
                "agent_code='" + agent_code + '\'' +
                ", agent_contact_id='" + agent_contact_id + '\'' +
                ", agent_id='" + agent_id + '\'' +
                ", area_code='" + area_code + '\'' +
                ", area_code_id='" + area_code_id + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", LOGIN_PASS='" + LOGIN_PASS + '\'' +
                ", CLIENT_OS_TYPE='" + CLIENT_OS_TYPE + '\'' +
                ", PEVICE_UUID='" + PEVICE_UUID + '\'' +
                ", LOGIN_PAY='" + LOGIN_PAY + '\'' +
                ", CHECK='" + CHECK + '\'' +
                ", CONSUMPTION='" + CONSUMPTION + '\'' +
                '}';
    }
}
