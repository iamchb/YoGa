package com.yitong.yoga.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.yitong.yoga.R;
import com.yitong.yoga.ServiceCode;
import com.yitong.yoga.bean.DoLogin;
import com.yitong.yoga.http.APPResponseHandler;
import com.yitong.yoga.http.APPRestClient;
import com.yitong.yoga.http.ServiceUrlManager;
import com.yitong.yoga.http.YTBaseRequestParams;
import com.yitong.yoga.http.YTRequestParams;
import com.yitong.yoga.utils.Logs;
import com.yitong.yoga.utils.RegularExpressionUtil;
import com.yitong.yoga.utils.StringTools;
import com.yitong.yoga.utils.ToastTools;


public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";
    private Dialog waitDialog;
    EditText verification_code, input_phone, input_password_agin;
    MaterialSpinner spinner;
    String data[] = {"86", "853", "852", "email"};




    //    Button send_verification;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
//        getSupportActionBar().hide();
        RelativeLayout titleLay = (RelativeLayout) findViewById(R.id.titleLay);
        TextView title = (TextView) titleLay.findViewById(R.id.title_main_txt_title);
        title.setText(getResources().getString(R.string.register));
        titleLay.setBackgroundColor(getResources().getColor(R.color.thirdColor));

//        send_verification= (Button) findViewById(R.id.send_verification);//发送验证码
        input_phone = (EditText) findViewById(R.id.input_phone);
        verification_code = (EditText) findViewById(R.id.verification_code);
        input_password_agin = (EditText) findViewById(R.id.input_password_agin);

        ImageView ivDimensCode = (ImageView) findViewById(R.id.title_main_iv_dimension_code);
        ivDimensCode.setImageResource(R.drawable.ic_chevron_left_black_24dp);
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

        spinner = (MaterialSpinner) findViewById(R.id.spinner);
        spinner.setPadding(20, 5, 0, 0);
        spinner.setGravity(Gravity.CENTER_VERTICAL);
//        spinner.setItems("+86", "+853", "+87","email");
        spinner.setItems(data);
//            spinner.setItems("dsda", "dasdad", "fdfdfd", "cbcb");
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
//                    Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
            }
        });


        Button btn = (Button) findViewById(R.id.next);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("RegisterActivity", "跳到设置密码界面");
                doNext();

            }
        });
        setHintSize(input_phone, getResources().getString(R.string.input_phone_number), 12);

    }

    private void doNext() {
        Log.v(TAG, "doNext");
        final String account = input_phone.getText().toString().trim();//手机号码
//        TextView textView = (TextView) dropDownListView.getChildAt(0).findViewById(R.id.text);
        String verificationCode = verification_code.getText().toString().trim();
        String inputPwdAgin = input_password_agin.getText().toString().trim();
        String areaCode = data[spinner.getSelectedIndex()];
        // 信息校验


        if (StringTools.isBlank(account)) {
            ToastTools.showToast(getApplicationContext(), this.getString(R.string.input_phone_num));
            return;
        }

//        if (account.length() < 8) {
//            ToastTools.showToast(getApplicationContext(), this.getString(R.string.login_phone_minsize));
//            return;
//        }
        if (RegularExpressionUtil.isMobileOrEmail(areaCode,account)) {

        }else {
            ToastTools.showToast(getApplicationContext(), this.getString(R.string.regesiter_inuput_error));
            return;
        }

        if (verificationCode.length() < 6) {
            ToastTools.showToast(getApplicationContext(), this.getString(R.string.logoin_pwd));
            return;
        }

        if (!inputPwdAgin.equals(verificationCode)) {
            ToastTools.showToast(getApplicationContext(), this.getString(R.string.setpwd_error_3));
            return;
        }


        // 显示等待层
        Log.v(TAG, "等待层开始");
        waitDialog.show();

        // 发送登录请求
        Log.v(TAG, "发送请求开始");
//        startActivity(new Intent(RegisterActivity.this, SetPasswordActivity.class));

        YTRequestParams params = new YTBaseRequestParams(YTBaseRequestParams.PARAM_TYPE_JSON);
        params.put("area_code", areaCode);
        params.put("phone_number", account);
//        params.put("email", AndroidUtil.getDeviceUUID(this));//设备id
        params.put("yoga_pass", inputPwdAgin);//设备类型
        Logs.e(TAG, areaCode + "---" + account);
        Logs.e(TAG, ServiceUrlManager.getServiceAbsUrl(ServiceCode.LOGIN_REGISTER, this));
        Logs.e(TAG, params.getParamsString());
        APPRestClient.post(ServiceUrlManager.getServiceAbsUrl(ServiceCode.LOGIN_REGISTER, this), params,
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
                            startActivity(new Intent(RegisterActivity.this, RegisterSuccessActivity.class));

                            finish();
                        }

                    }

                    @Override
                    public void onFailure(String errorCode, String errorMsg) {
                        waitDialog.dismiss();
                        Logs.e(TAG, "onFailure");
                        Logs.e("Login_errorCode", errorCode);

                        switch (errorCode) {
                            case "USER_NOT_EXIT":
                                ToastTools.showShort(getApplicationContext(), errorMsg);
                                break;
                            case "PHONE_NOT_EXIT":
                                ToastTools.showShort(getApplicationContext(), errorMsg);
                                break;
                            case "SMS_EORROR_THREE_TIMES"://该手机号码已被锁定，请一小时后重试！
                                ToastTools.showShort(getApplicationContext(), errorMsg);
                                break;
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

    private void setHintSize(EditText editText, String text, int size) {
        SpannableString ss = new SpannableString(text);//定义hint的值
//        SpannableString ss = new SpannableString(getResources().getString(R.string.input_phone_num));//定义hint的值
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(size, true);//设置字体大小 true表示单位是sp
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        editText.setHint(new SpannedString(ss));
    }
}
