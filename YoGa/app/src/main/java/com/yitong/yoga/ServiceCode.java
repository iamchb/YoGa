package com.yitong.yoga;

/**
 * 存放请求接口地址和页面地址
 * 注：页面地址如果动态获取，不需要配置
 */
public class ServiceCode {

    // ============================ 网络请求接口 ===================================
//    http://10.10.10.71:8088/YogaApp/yogaRegistered.do
    // 注册
    public static final String LOGIN_REGISTER = "yogaRegistered.do";

    //登录
    public static final String LOGIN_DO = "yogaLogin.do";

    //修改密码
    public static final String MODIFY_PWD = "updateYogaLoginPass.do";

    //登出
    public static final String LOGIN_OUT = "yogaLogout.do";

    //我的账户
    public static final String MY_ACCOUNT = "yogaMyAccount.do";

    //课程列表
    public static final String CURRI_SCHEDULE = "yogaCourseList.do";

    //课程详情
    public static final String CURRI_DETAIL = "yogaCourseDetail.do";

    //课程日历
    public static final String CURRI_CALENDAR = "yogaCourseCalendar.do";

    //预定课程
    public static final String COURSE_BOOKING = "yogaCourseBooking.do";

    //预定日历
   public static final String  COURSE_CALENDAR="yogaOrderCalendar.do";

//    预订记录列表
   public static final String  BOOKING_LIST="yogaOrderList.do" ;   //order_id

//    预订记录详情
   public static final String BOOKING_DETAIL="yogaOrderDetail.do";

//    取消预订接口
  public static final String  CACEL_ORDER="yogaOrderCancelBooking.do";

//    预订历史记录接口
   public static final String BOOKING_HISTORY= "yogaOrderHistoryBooking.do";


    // ============================ 静态页面以及文件 ===================================

    // 测试
    public static final String HTML_TEST = "page/index.html";

    // web资源版本文件
    public static final String WEBCACHE_VERSION = "R.version";
    // web资源列表文件
    public static final String WEBCACHE_RESOURCE = "R.info";
}
