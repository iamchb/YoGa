package com.yitong.yoga.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yitong.yoga.JustifyTextView;
import com.yitong.yoga.R;
import com.yitong.yoga.ServiceCode;
import com.yitong.yoga.UserManager;
import com.yitong.yoga.bean.DoLogin;
import com.yitong.yoga.http.APPResponseHandler;
import com.yitong.yoga.http.APPRestClient;
import com.yitong.yoga.http.ServiceUrlManager;
import com.yitong.yoga.http.YTBaseRequestParams;
import com.yitong.yoga.http.YTRequestParams;
import com.yitong.yoga.utils.Logs;
import com.yitong.yoga.utils.ToastTools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CourseIntroductionActivity extends AppCompatActivity {

    private static final String TAG = "CourseIntroductionActiv";
    TextView booking_class, booking_class_time, booking_class_howlong, booking_class_place;
    JustifyTextView class_desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_introduction);
//        getSupportActionBar().hide();
        RelativeLayout titleLay = (RelativeLayout) findViewById(R.id.titleLay);
        TextView title = (TextView) titleLay.findViewById(R.id.title_main_txt_title);
        title.setText(getResources().getString(R.string.course_introduction));
        titleLay.setBackgroundColor(getResources().getColor(R.color.thirdColor));




        ImageView ivDimensCode = (ImageView) findViewById(R.id.title_main_iv_dimension_code);
        ivDimensCode.setImageResource(R.drawable.ic_chevron_left_black_24dp);
        ivDimensCode.setVisibility(View.VISIBLE);
        ivDimensCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        Intent intent = getIntent();

        String CLASS_NAME = intent.getStringExtra("CLASS_NAME");
        String CLASS_ADDR = intent.getStringExtra("CLASS_ADDR");
        String CLASS_DESC = intent.getStringExtra("CLASS_DESC");
        final String START_DATE = intent.getStringExtra("START_DATE");
        String START_TIME = intent.getStringExtra("START_TIME");
        int TAKES_TIME = intent.getIntExtra("TAKES_TIME", 0);
        final int CLASS_ID = intent.getIntExtra("CLASS_ID", 0);

        class_desc= (JustifyTextView) findViewById(R.id.class_desc);
        booking_class= (TextView) findViewById(R.id.booking_class);
        booking_class_time= (TextView) findViewById(R.id.booking_class_time);
        booking_class_howlong= (TextView) findViewById(R.id.booking_class_howlong);
        booking_class_place= (TextView) findViewById(R.id.booking_class_place);
        Button btn = (Button) findViewById(R.id.reserve_btn);

        class_desc.setText(CLASS_DESC);
        booking_class.setText(CLASS_NAME);
        booking_class_time.setText(START_DATE+"   "+START_TIME);
        booking_class_howlong.setText(TAKES_TIME+"");
        booking_class_place.setText(CLASS_ADDR);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Date inDate = null;
                try {
//                    inDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(booking_class_time.getText().toString());
                    inDate = new SimpleDateFormat("yyyy-MM-dd").parse(START_DATE);
                    long time=System.currentTimeMillis()-inDate.getTime();

//                    Date date1 = new Date();
//                    Date date2 = new Date();
//
//                    long  between = date2.getTime() - date1.getTime();
//                    TimeUtils. getTimeSpan(inDate,System.currentTimeMillis(),ConstUtils.TimeUnit.DAY,)

                    if(time > (48* 3600000)){
                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(CourseIntroductionActivity.this);
                        builder.setTitle(getResources().getString(R.string.hint_for_logout))
                                .setMessage(getResources().getString(R.string.course_hint_message))
                                .setPositiveButton(getResources().getString(R.string.confirm),
                                        new DialogInterface.OnClickListener() {

                                            @Override
                                            public void onClick(DialogInterface dialog,
                                                                int which) {
                                                dialog.dismiss();

                                            }
                                        }).show();

                        //                        .setNegativeButton(getString(R.string.activity_login_qx),
//                                new DialogInterface.OnClickListener() {
//
//                                    @Override
//                                    public void onClick(DialogInterface dialog,
//                                                        int which) {
//                                        dialog.dismiss();
//                                    }
//                                }).show();

                    }else{
                        YTRequestParams params = new YTBaseRequestParams(YTBaseRequestParams.PARAM_TYPE_JSON);
                        params.put("uid", UserManager.getInstance().getUserInfo().getAgent_id());
                        params.put("id", CLASS_ID+"");
                        Logs.e(TAG, ServiceUrlManager.getServiceAbsUrl(ServiceCode.COURSE_BOOKING, CourseIntroductionActivity.this));
                        Logs.e(TAG, params.getParamsString());
                        APPRestClient.post(ServiceUrlManager.getServiceAbsUrl(ServiceCode.COURSE_BOOKING, CourseIntroductionActivity.this), params,
                                new APPResponseHandler<DoLogin>(DoLogin.class, null) {

                                    @Override
                                    public void onSuccess(DoLogin result) {
                                        Logs.v(TAG, "onSuccess");
                                        Logs.v(TAG, result.toString());

                                        if (result.getSTATUS().equals("1")) {
//                            DoLogin login=result;
                                            ToastTools.showShort(getApplicationContext(), result.getMSG());
                                            finish();
                                        }

                                    }

                                    @Override
                                    public void onFailure(String errorCode, String errorMsg) {
                                        Logs.e(TAG, "onFailure");
                                        Logs.e("Login_errorCode", errorCode);
//                        接口返回{"MSG":"登录密码错误！","STATUS":"YOGA_LOGIN_PASS_FAIL"}
//                        "MSG":"该账户还没注册，请先注册！","STATUS":"YOGA_ISNOT_REGISTERED"
                                        switch (errorCode) {
                                            case "YOGA_ISNOT_REGISTERED":
                                                ToastTools.showShort(getApplicationContext(), errorMsg);
                                                break;
                                            case "YOGA_LOGIN_PASS_FAIL":
                                                ToastTools.showShort(getApplicationContext(), errorMsg);
                                                break;
//                            case "SMS_EORROR_THREE_TIMES"://该手机号码已被锁定，请一小时后重试！
//                                ToastTools.showShort(getApplicationContext(), errorMsg);
//                                break;
                                            default:
                                                ToastTools.showShort(getApplicationContext(), errorMsg);
                                                break;
                                        }
                                    }

                                    @Override
                                    public void onFinish() {
                                        Logs.e(TAG, "onFinish");
//                                if (null != waitDialog) {
//                                    waitDialog.dismiss();
//                                }
                                    }

                                }, null);


                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }


                
                


            }
        });
    }
}
