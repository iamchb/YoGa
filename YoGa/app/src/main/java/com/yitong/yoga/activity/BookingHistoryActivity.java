package com.yitong.yoga.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yitong.yoga.R;
import com.yitong.yoga.ServiceCode;
import com.yitong.yoga.UserManager;
import com.yitong.yoga.adapter.BookingHistoryAdapter;
import com.yitong.yoga.bean.BookListBean;
import com.yitong.yoga.bean.Curriculum;
import com.yitong.yoga.http.APPResponseHandler;
import com.yitong.yoga.http.APPRestClient;
import com.yitong.yoga.http.ServiceUrlManager;
import com.yitong.yoga.http.YTBaseRequestParams;
import com.yitong.yoga.http.YTRequestParams;
import com.yitong.yoga.utils.Logs;
import com.yitong.yoga.utils.ToastTools;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class BookingHistoryActivity extends AppCompatActivity {
    private BookingHistoryAdapter mAdapter;
    private RecyclerView recyclerView;
    List<Curriculum> personList = new ArrayList<>();
    private Dialog waitDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_history);
//        getSupportActionBar().hide();
        RelativeLayout titleLay = (RelativeLayout) findViewById(R.id.titleLay);
        TextView title = (TextView) titleLay.findViewById(R.id.title_main_txt_title);
        title.setText(getResources().getString(R.string.menu_wdls));
        titleLay.setBackgroundColor(getResources().getColor(R.color.thirdColor));


        ImageView ivDimensCode = (ImageView) findViewById(R.id.title_main_iv_dimension_code);
        ivDimensCode.setImageResource(R.drawable.ic_chevron_left_black_24dp);
//        ImageView  ivUser = (ImageView) findViewById(R.id.title_main_img_user);
//        ivUser.setImageResource(R.drawable.u39);

        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage(this.getString(R.string.progress_load_msg));
        dialog.setIndeterminate(false);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        waitDialog = dialog;

//        ivUser.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                    ((MainActivity) getActivity()).showDialog();
//                Intent intent=new Intent(BookingHistoryActivity.this, CalendarPickerActivity.class);
//                startActivity(intent);
//            }
//        });

        ivDimensCode.setVisibility(View.VISIBLE);
        ivDimensCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.my_recyclerview);



        waitDialog.show();
        personList.clear();
        // 发送登录请求
        Log.v(TAG, "发送请求开始");
        YTRequestParams params = new YTBaseRequestParams(YTBaseRequestParams.PARAM_TYPE_JSON);
        params.put("uid", UserManager.getInstance().getUserInfo().getAgent_id());
//        params.put("date", "2017-01-13");//可选字段
        Logs.e(TAG, ServiceUrlManager.getServiceAbsUrl(ServiceCode.BOOKING_HISTORY, this));
        Logs.e(TAG, params.getParamsString());
        APPRestClient.post(ServiceUrlManager.getServiceAbsUrl(ServiceCode.BOOKING_HISTORY, this), params,
                new APPResponseHandler<BookListBean>(BookListBean.class, null) {

                    @Override
                    public void onSuccess(BookListBean result) {
                        waitDialog.dismiss();
                        Logs.v(TAG, "onSuccess");
                        Logs.v(TAG, result.toString());

                        if (result.getSTATUS().equals("1")) {
//                            DoLogin login=result;
//                            EventBus.getDefault().post(new SwitchFragmentEvent(4));

                            if(result.getLIST()!=null){
                                for (int i = 0; i <result.getLIST().size() ; i++) {
                                    BookListBean.LISTBean bean=result.getLIST().get(i);
                                    personList.add(new Curriculum(bean.getCLASS_NAME(), bean.getSTART_DATE()+" "+bean.getSTART_TIME(), bean.getCOACH_NAME(), bean.getTRAN_MONEY(),bean.getCLASS_ID()+"",bean.getORDER_ID(),bean.getORDER_STATUS(),bean.getORDER_MONEY()));
                                }
                            }
                            if(mAdapter==null){
                                mAdapter = new BookingHistoryAdapter(personList, BookingHistoryActivity.this);
                                recyclerView.setLayoutManager(new LinearLayoutManager(BookingHistoryActivity.this));
                                recyclerView.setAdapter(mAdapter);
                            }else{
                                mAdapter.notifyDataSetChanged();
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
                                ToastTools.showShort(BookingHistoryActivity.this, errorMsg);
                                break;
                            case "YOGA_LOGIN_PASS_FAIL":
                                ToastTools.showShort(BookingHistoryActivity.this, errorMsg);
                                break;
//                            case "SMS_EORROR_THREE_TIMES"://该手机号码已被锁定，请一小时后重试！
//                                ToastTools.showShort(getApplicationContext(), errorMsg);
//                                break;
                            default:
                                ToastTools.showShort(BookingHistoryActivity.this, errorMsg);
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

//        personList.add(new Curriculum(getResources().getString(R.string.data_class1), "2015-12-18 12:09", getResources().getString(R.string.data_name1), "200.0",11+"",""));
//        personList.add(new Curriculum(getResources().getString(R.string.data_class2), "2015-12-19 12:09", getResources().getString(R.string.data_name2), "300.0",11+"",""));
//        personList.add(new Curriculum(getResources().getString(R.string.data_class3), "2015-12-20 12:09", getResources().getString(R.string.data_name3), "100.0",11+"",""));
//        personList.add(new Curriculum(getResources().getString(R.string.data_class4), "2015-12-21 12:09", getResources().getString(R.string.data_name4), "100.0",11+"",""));
//        personList.add(new Curriculum(getResources().getString(R.string.data_class5), "2015-12-22 12:09", getResources().getString(R.string.data_name5), "400.0",11+"",""));
//        mAdapter = new SimpleAdapter(personList, this);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(mAdapter);
    }
}
