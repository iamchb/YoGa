package com.yitong.yoga.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
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
import com.yitong.yoga.utils.Logs;
import com.yitong.yoga.utils.StringTools;
import com.yitong.yoga.utils.ToastTools;

public class SetPasswordActivity extends AppCompatActivity {
    private static final String TAG = "SetPasswordActivity";
    EditText input_password_agin,input_password;
    private Dialog waitDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);
//        getSupportActionBar().hide();
        RelativeLayout titleLay = (RelativeLayout) findViewById(R.id.titleLay);
        TextView title = (TextView) titleLay.findViewById(R.id.title_main_txt_title);
        title.setText(getResources().getString(R.string.set_pwd));
        titleLay.setBackgroundColor(getResources().getColor(R.color.thirdColor));

        input_password= (EditText) findViewById(R.id.input_password);
        input_password_agin= (EditText) findViewById(R.id.input_password_agin);
        ImageView ivDimensCode = (ImageView) findViewById(R.id.title_main_iv_dimension_code);
        ivDimensCode.setImageResource(R.drawable.ic_chevron_left_black_24dp);

        Button btn= (Button) findViewById(R.id.button);
        ivDimensCode.setVisibility(View.VISIBLE);
        ivDimensCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage(this.getString(R.string.progress_load_msg));
        dialog.setIndeterminate(false);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        waitDialog = dialog;

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doNext();

            }
        });
    }

    private void doNext() {
        Log.v(TAG, "doNext");
        final String password = input_password.getText().toString().trim();//手机号码
//        TextView textView = (TextView) dropDownListView.getChildAt(0).findViewById(R.id.text);
        String  passwordAgin=input_password_agin.getText().toString().trim();
        // 信息校验



        if (StringTools.isBlank(password)) {//密码为空
            ToastTools.showToast(getApplicationContext(), this.getString(R.string.setpwd_error_1));
            return;
        }

        if (password.length() < 6) {//密码长度必须大于等于6
            ToastTools.showToast(getApplicationContext(), this.getString(R.string.setpwd_error_2));
            return;
        }


        if (!password.equals(passwordAgin)) {//密码输入不一致
            ToastTools.showToast(getApplicationContext(), this.getString(R.string.setpwd_error_3));
            return;
        }
        // 显示等待层
        Log.v(TAG, "等待层开始");
        waitDialog.show();
        waitDialog.dismiss();
        startActivity(new Intent(SetPasswordActivity.this,RegisterSuccessActivity.class));
        finish();
        // 发送登录请求
        Log.v(TAG, "发送请求开始");
//        YTRequestParams params = new YTBaseRequestParams(YTBaseRequestParams.PARAM_TYPE_JSON);
//        params.put("area_code", areaCode);
//        params.put("phone_number", account);
//        params.put("DEVICE_UUID", AndroidUtil.getDeviceUUID(this));//设备id
//        params.put("CLIENT_OS_TYPE", "android");//设备类型
//        String key = CryptoUtil.genRandomKey();
//        Logs.e(TAG, areaCode + "---" + account);
//        Logs.e(TAG, ServiceUrlManager.getServiceAbsUrl(ServiceCode.LOGIN_DO, this));
//        Logs.e(TAG, params.getParamsString());
//        APPRestClient.post(ServiceUrlManager.getServiceAbsUrl(ServiceCode.LOGIN_DO, this), params,
//                new APPResponseHandler<DoLogin>(DoLogin.class, key) {
//
//                    @Override
//                    public void onSuccess(DoLogin result) {
//                        Logs.v(TAG, "onSuccess");
//                        Logs.v(TAG, result.toString());
//
//                        if (result.getSTATUS().equals("1")) {
////                            DoLogin login=result;
//                            ToastTools.showShort(getApplicationContext(), result.getMSG());
//                            EventBus.getDefault().postSticky(result);
//                            Intent intent = new Intent(LoginActiviy.this, ChooseAccountActivity.class);
//                            startActivity(intent);
//                            finish();
//                        }
//
//                    }
//
//                    @Override
//                    public void onFailure(String errorCode, String errorMsg) {
//                        Logs.e(TAG, "onFailure");
//                        Logs.e("Login_errorCode", errorCode);
//
//                        switch (errorCode) {
//                            case "USER_NOT_EXIT":
//                                ToastTools.showShort(getApplicationContext(), errorMsg);
//                                break;
//                            case "PHONE_NOT_EXIT":
//                                ToastTools.showShort(getApplicationContext(), errorMsg);
//                                Intent intent = new Intent(LoginActiviy.this, SafeCodeActivity.class);
//                                String[] userinfo = {account, areaCode};
//                                intent.putExtra("toSafeCode", userinfo);
//
//                                startActivity(intent);
//                                finish();
//                                break;
//                            case "SMS_EORROR_THREE_TIMES"://该手机号码已被锁定，请一小时后重试！
//                                ToastTools.showShort(getApplicationContext(), errorMsg);
//                                break;
//                            default:
//                                ToastTools.showShort(getApplicationContext(), errorMsg);
//                                break;
//                        }
//                    }
//
//                    @Override
//                    public void onFinish() {
//                        Logs.e(TAG, "onFinish");
//                        if (null != waitDialog) {
//                            waitDialog.dismiss();
//                        }
//                    }
//
//                }, key);
        Logs.v(TAG, "发送请求结束");
    }
}
