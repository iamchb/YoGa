package com.yitong.yoga.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yitong.yoga.R;
import com.yitong.yoga.ServiceCode;
import com.yitong.yoga.UserManager;
import com.yitong.yoga.bean.MyAccountBean;
import com.yitong.yoga.bean.SwitchFragmentEvent;
import com.yitong.yoga.http.APPResponseHandler;
import com.yitong.yoga.http.APPRestClient;
import com.yitong.yoga.http.ServiceUrlManager;
import com.yitong.yoga.http.YTBaseRequestParams;
import com.yitong.yoga.http.YTRequestParams;
import com.yitong.yoga.utils.Logs;
import com.yitong.yoga.utils.StringTools;
import com.yitong.yoga.utils.ToastTools;

import org.greenrobot.eventbus.EventBus;

public class MyAccountActivity extends AppCompatActivity {
    private Dialog waitDialog;
    private static final String TAG = "MyAccountActivity";
    TextView account_balance,residual_class,consumption_this_month,last_month_consumption ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
//        getSupportActionBar().hide();
        RelativeLayout titleLay = (RelativeLayout) findViewById(R.id.titleLay);
        TextView title = (TextView) titleLay.findViewById(R.id.title_main_txt_title);
        title.setText(getResources().getString(R.string.menu_wdzh));
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

        account_balance= (TextView) findViewById(R.id.account_balance);
        residual_class= (TextView) findViewById(R.id.residual_class);
        consumption_this_month= (TextView) findViewById(R.id.consumption_this_month);
        last_month_consumption= (TextView) findViewById(R.id.last_month_consumption);

        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage(this.getString(R.string.progress_load_msg));
        dialog.setIndeterminate(false);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        waitDialog = dialog;


        Button btn= (Button) findViewById(R.id.reserve);
        Button btn2= (Button) findViewById(R.id.reservation_record);


        YTRequestParams params = new YTBaseRequestParams(YTBaseRequestParams.PARAM_TYPE_JSON);
        if(UserManager.getInstance().isLogin()){
            params.put("ID", UserManager.getInstance().getUserInfo().getAgent_id());
        }

        Logs.e(TAG, ServiceUrlManager.getServiceAbsUrl(ServiceCode.MY_ACCOUNT, this));
        Logs.e(TAG, params.getParamsString());
        waitDialog.show();
        APPRestClient.post(ServiceUrlManager.getServiceAbsUrl(ServiceCode.MY_ACCOUNT, this), params,
                new APPResponseHandler<MyAccountBean>(MyAccountBean.class, null) {

                    @Override
                    public void onSuccess(MyAccountBean result) {
                        waitDialog.dismiss();
                        Logs.v(TAG, "onSuccess");
                        Logs.v(TAG, result.toString());

                        if (result.getSTATUS().equals("1")) {
                            ToastTools.showShort(getApplicationContext(), result.getMSG());

                            if(!StringTools.isEmpty(result.getMap().getAccountBalance())){
                                account_balance.setText(result.getMap().getAccountBalance());
                            }
                            if(!StringTools.isEmpty(result.getMap().getConsumption_last())){
                                last_month_consumption.setText(result.getMap().getConsumption_last());
                            }
                            if(!StringTools.isEmpty(result.getMap().getConsumption_this())){
                                consumption_this_month.setText(result.getMap().getConsumption_this());
                            }
                            if(!StringTools.isEmpty(result.getMap().getResidualFrequency())){
//                                residual_class.setText(result.getMap().getResidualFrequency());
                            }
                        }

                    }

                    @Override
                    public void onFailure(String errorCode, String errorMsg) {
                        waitDialog.dismiss();
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
                        if (null != waitDialog) {
                            waitDialog.dismiss();
                        }
                    }

                }, null);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new SwitchFragmentEvent(0));
                finish();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new SwitchFragmentEvent(1));
                finish();
            }
        });
    }


}
