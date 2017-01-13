package com.yitong.yoga.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import com.yitong.yoga.utils.StringTools;
import com.yitong.yoga.utils.ToastTools;

public class ModifyPasswordActivity extends AppCompatActivity {
    private static final String TAG = "ModifyPasswordActivity";
    private Dialog waitDialog;
    EditText input_old_password, input_password, input_password_agin;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_password);

//        getSupportActionBar().hide();
        RelativeLayout titleLay = (RelativeLayout) findViewById(R.id.titleLay);
        TextView title = (TextView) titleLay.findViewById(R.id.title_main_txt_title);
        title.setText(getResources().getString(R.string.modify_pwd));
        titleLay.setBackgroundColor(getResources().getColor(R.color.thirdColor));

        button = (Button) findViewById(R.id.button);
        input_password = (EditText) findViewById(R.id.input_password);
        input_password_agin = (EditText) findViewById(R.id.input_password_agin);
        input_old_password = (EditText) findViewById(R.id.input_old_password);

        ImageView ivDimensCode = (ImageView) findViewById(R.id.title_main_iv_dimension_code);
        ivDimensCode.setImageResource(R.drawable.ic_chevron_left_black_24dp);
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage(this.getString(R.string.progress_load_msg));
        dialog.setIndeterminate(false);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        waitDialog = dialog;

        ivDimensCode.setVisibility(View.VISIBLE);
        ivDimensCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doNext();

            }
        });


    }


    private void doNext() {
        Log.v(TAG, "doNext");
        String oldPassword = input_old_password.getText().toString().trim();//手机号码
        String password = input_password.getText().toString().trim();//手机号码
        String passwordAgin = input_password_agin.getText().toString().trim();
        // 信息校验


        if (StringTools.isBlank(oldPassword)) {//原密码为空
            ToastTools.showToast(getApplicationContext(), getApplication().getString(R.string.modify_pwd_empty));
            return;
        }
        if (password.length() < 6) {//新密码长度必须大于等于6
            ToastTools.showToast(getApplicationContext(), this.getString(R.string.modify_pwd_less));
            return;
        }


        if (!password.equals(passwordAgin)) {//密码输入不一致
            ToastTools.showToast(getApplicationContext(), this.getString(R.string.modify_pwd_diffrent));
            return;
        }
        // 显示等待层
        Log.v(TAG, "等待层开始");
        waitDialog.show();
        // 发送登录请求
        Log.v(TAG, "发送请求开始");
        YTRequestParams params = new YTBaseRequestParams(YTBaseRequestParams.PARAM_TYPE_JSON);
        if(UserManager.getInstance().getUserInfo()!=null){
            params.put("area_code", UserManager.getInstance().getUserInfo().getAgent_code());
            params.put("phone_number", UserManager.getInstance().getUserInfo().getPhone_number());
            params.put("yoga_pass", UserManager.getInstance().getUserInfo().getLOGIN_PASS());
        }

        params.put("yoga_pass_new", input_password.getText().toString().trim());
        params.put("ID", UserManager.getInstance().getUserInfo().getAgent_id());
//        Logs.e(TAG, areaCode + "---" + account);
        Logs.e(TAG, ServiceUrlManager.getServiceAbsUrl(ServiceCode.MODIFY_PWD, this));
        Logs.e(TAG, params.getParamsString());
        APPRestClient.post(ServiceUrlManager.getServiceAbsUrl(ServiceCode.MODIFY_PWD, this), params,
                new APPResponseHandler<DoLogin>(DoLogin.class, null) {

                    @Override
                    public void onSuccess(DoLogin result) {
                        waitDialog.dismiss();
                        Logs.v(TAG, "onSuccess");
                        Logs.v(TAG, result.toString());

                        if (result.getSTATUS().equals("1")) {
//                            DoLogin login=result;
                            ToastTools.showShort(getApplicationContext(), result.getMSG());
//                            EventBus.getDefault().postSticky(result);
//                            startActivity(new Intent(RegisterActivity.this, RegisterSuccessActivity.class));

                            finish();
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
        Logs.v(TAG, "发送请求结束");
    }
}
