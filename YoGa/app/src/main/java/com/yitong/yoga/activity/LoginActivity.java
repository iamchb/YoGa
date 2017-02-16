package com.yitong.yoga.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
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
import com.yitong.yoga.UserManager;
import com.yitong.yoga.bean.DoLogin;
import com.yitong.yoga.bean.LoginUserInfo;
import com.yitong.yoga.bean.SwitchFragmentEvent;
import com.yitong.yoga.http.APPResponseHandler;
import com.yitong.yoga.http.APPRestClient;
import com.yitong.yoga.http.ServiceUrlManager;
import com.yitong.yoga.http.YTBaseRequestParams;
import com.yitong.yoga.http.YTRequestParams;
import com.yitong.yoga.utils.Logs;
import com.yitong.yoga.utils.RegularExpressionUtil;
import com.yitong.yoga.utils.StringTools;
import com.yitong.yoga.utils.ToastTools;

import org.greenrobot.eventbus.EventBus;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    EditText phoneNumber;
    EditText passWd;
    private Dialog waitDialog;
    MaterialSpinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//     getSupportActionBar().hide();

        RelativeLayout titleLay = (RelativeLayout) findViewById(R.id.titleLay);
        TextView title = (TextView) titleLay.findViewById(R.id.title_main_txt_title);
        title.setText(getResources().getString(R.string.login));
        titleLay.setBackgroundColor(ContextCompat.getColor(this, R.color.thirdColor));

        phoneNumber = (EditText) findViewById(R.id.input_phone);
        passWd = (EditText) findViewById(R.id.input_password);
        ImageView ivDimensCode = (ImageView) findViewById(R.id.title_main_iv_dimension_code);
        ivDimensCode.setImageResource(R.drawable.ic_chevron_left_black_24dp);
        Button ivUser = (Button) findViewById(R.id.title_main_img_user);


        setHintSize(phoneNumber,getResources().getString(R.string.input_phone_num),12);
        setHintSize(passWd,getResources().getString(R.string.input_pwd),12);

        spinner= (MaterialSpinner) findViewById(R.id.spinner);
        spinner.setPadding(20,5,0,0);
        spinner.setGravity(Gravity.CENTER_VERTICAL);
        spinner.setItems("86", "853", "852","email");
//            spinner.setItems("dsda", "dasdad", "fdfdfd", "cbcb");
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
//                    Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
            }
        });

        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage(this.getString(R.string.progress_load_msg));
        dialog.setIndeterminate(false);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        waitDialog = dialog;

        ivUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
            }
        });

        ivDimensCode.setVisibility(View.VISIBLE);
        ivDimensCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doNext();
            }
        });
    }

    private void setHintSize(EditText editText,String text,int size) {
        SpannableString ss = new SpannableString(text);//定义hint的值
//        SpannableString ss = new SpannableString(getResources().getString(R.string.input_phone_num));//定义hint的值
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(size,true);//设置字体大小 true表示单位是sp
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        editText.setHint(new SpannedString(ss));
    }


    private void doNext() {
        Log.v(TAG, "doNext");
        final String account = phoneNumber.getText().toString().trim();//手机号码
//        TextView textView = (TextView) dropDownListView.getChildAt(0).findViewById(R.id.text);
        final String areaCode = spinner.getText().toString().trim();//区号
        final String  passWord=passWd.getText().toString().trim();
        // 信息校验


//        if (StringTools.isBlank(areaCode)) {
//            ToastTools.showToast(getApplicationContext(), this.getString(R.string.login_area_cannot_empty));
//        }

        if (StringTools.isBlank(account)) {
            ToastTools.showToast(getApplicationContext(), this.getString(R.string.login_phone_cannot_empty));
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

        if(passWord.length()<6){
            ToastTools.showToast(getApplicationContext(), this.getString(R.string.logoin_pwd));
            return;
        }
        // 显示等待层
        Log.v(TAG, "等待层开始");
        waitDialog.show();

        // 发送登录请求
        Log.v(TAG, "发送请求开始");
        YTRequestParams params = new YTBaseRequestParams(YTBaseRequestParams.PARAM_TYPE_JSON);

        if(areaCode.equals("email")){
            params.put("email", account);
        }else{
            params.put("area_code", areaCode);
            params.put("phone_number", account);
        }
//        params.put("email", AndroidUtil.getDeviceUUID(this));//设备id
        params.put("yoga_pass", passWord);//设备类型
        Logs.e(TAG, areaCode + "---" + account);
        Logs.e(TAG, ServiceUrlManager.getServiceAbsUrl(ServiceCode.LOGIN_DO, this));
        Logs.e(TAG, params.getParamsString());
        APPRestClient.post(ServiceUrlManager.getServiceAbsUrl(ServiceCode.LOGIN_DO, this), params,
                new APPResponseHandler<DoLogin>(DoLogin.class, null) {

                    @Override
                    public void onSuccess(DoLogin result) {
                        waitDialog.dismiss();
                        Logs.v(TAG, "onSuccess");
                        Logs.v(TAG, result.toString());

                        if (result.getSTATUS().equals("1")) {
//                            DoLogin login=result;
                            ToastTools.showShort(getApplicationContext(), result.getMSG());

//                            startActivity(new Intent(LoginActivity.this, RegisterSuccessActivity.class));
                            UserManager.getInstance().setLogin(true);
                            LoginUserInfo userInfo=new LoginUserInfo();
                            userInfo.setPhone_number(account);
                            userInfo.setAgent_code(areaCode);
                            userInfo.setLOGIN_PASS(passWord);
                            userInfo.setAgent_id(result.getID());
                            UserManager.getInstance().setUserInfo(userInfo);
                            EventBus.getDefault().post(new SwitchFragmentEvent(4));
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(waitDialog!=null){
            waitDialog.dismiss();
        }


    }
}
