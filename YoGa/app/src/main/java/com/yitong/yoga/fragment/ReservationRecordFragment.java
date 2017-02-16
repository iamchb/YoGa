package com.yitong.yoga.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.listener.OnBottomLoadMoreTime;
import com.yitong.yoga.R;
import com.yitong.yoga.ServiceCode;
import com.yitong.yoga.UserManager;
import com.yitong.yoga.activity.CalendarPickerActivity;
import com.yitong.yoga.activity.LoginActivity;
import com.yitong.yoga.activity.MainActivity;
import com.yitong.yoga.adapter.EmptyAdapter;
import com.yitong.yoga.adapter.FrozenAdapter;
import com.yitong.yoga.adapter.NoLoginAdapter;
import com.yitong.yoga.adapter.SimpleAdapter;
import com.yitong.yoga.bean.BookListBean;
import com.yitong.yoga.bean.BookingCalendarBean;
import com.yitong.yoga.bean.Curriculum;
import com.yitong.yoga.bean.FreshTimeTableWithCalendar;
import com.yitong.yoga.bean.SwitchFragmentEvent;
import com.yitong.yoga.http.APPResponseHandler;
import com.yitong.yoga.http.APPRestClient;
import com.yitong.yoga.http.ServiceUrlManager;
import com.yitong.yoga.http.YTBaseRequestParams;
import com.yitong.yoga.http.YTRequestParams;
import com.yitong.yoga.smileyloadingview.SmileyHeaderView;
import com.yitong.yoga.utils.Logs;
import com.yitong.yoga.utils.NoDoubleClickListener;
import com.yitong.yoga.utils.StringTools;
import com.yitong.yoga.utils.ToastTools;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * YoGa
 * Created by Chb on  2016/10/14 15:34
 */

public class ReservationRecordFragment extends Fragment {


    protected View contentView = null;
    private SimpleAdapter mAdapter;
    private RecyclerView recyclerView;
    //    Toolbar mToolbar;
    List<Curriculum> personList = new ArrayList<>();
    private ImageView ivDimensCode;// 二维码
    private ImageView ivUser;// 个人中心
    TextView title;
    private XRefreshView refreshView;
    private Dialog waitDialog;
    private final int mPinnedTime = 1000;
    //    protected View contentView = null;
    protected boolean isFirstLoad = true;
//    private LoadingLayout loading;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (contentView == null) {
            contentView = inflater.inflate(R.layout.fragment_reservationrecord, container, false);
            isFirstLoad = true;
            EventBus.getDefault().register(this);
            RelativeLayout titleLay = (RelativeLayout) contentView.findViewById(R.id.titleLay);
            title = (TextView) titleLay.findViewById(R.id.title_main_txt_title);
            title.setText(getResources().getString(R.string.reservation_record));
            titleLay.setBackgroundColor(getResources().getColor(R.color.fourthColor));

            ivUser = (ImageView) contentView.findViewById(R.id.title_main_img_user);
            ivDimensCode = (ImageView) contentView.findViewById(R.id.title_main_iv_dimension_code);
            ivUser.setImageResource(R.drawable.u39);
            ivDimensCode.setImageResource(R.drawable.ic_dehaze_black_24dp);
            ivDimensCode.setVisibility(View.VISIBLE);


//            loading = (LoadingLayout) contentView.findViewById(R.id.loading);
//            loading.setLoadingPage(R.layout.define_loading_page).setOnReloadListener(new LoadingLayout.OnReloadListener() {
//
//                @Override
//                public void onReload(View v) {
//
//                    Toast.makeText(getActivity(), "重试", Toast.LENGTH_SHORT).show();
//                }
//            });

            refreshView = (XRefreshView) contentView.findViewById(R.id.custom_view);
            refreshView.setPullLoadEnable(false);
//            refreshView.setAutoRefresh(true);
            refreshView.setPinnedTime(mPinnedTime);
            refreshView.setCustomHeaderView(new SmileyHeaderView(getActivity()));
//            refreshView.setCustomHeaderView(new CustomHeader(getActivity(), mPinnedTime));
//            refreshView.setMoveFootWhenDisablePullLoadMore(false);

//            refreshView.setOnTopRefreshTime(new OnTopRefreshTime() {
//
//                @Override
//                public boolean isTop() {
////                    if (recyclerView.getFirstVisiblePosition() == 0) {
////                        View firstVisibleChild = recyclerView.getChildAt(0);
////                        return firstVisibleChild.getTop() >= 0;
////                    }
//
//                    return  true;
//                }
//            });

            refreshView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {

                @Override
                public void onRefresh() {


                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            refreshView.stopRefresh();
                        }
                    }, 2000);

                    personList.clear();
                    mAdapter = null;
                    if (UserManager.getInstance().isLogin()) {
                        setData(UserManager.getInstance().getUserInfo().getAgent_id(), null);
                    }

                }

                @Override
                public void onLoadMore(boolean isSilence) {

                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            refreshView.stopLoadMore();
//                            refreshView.setLoadComplete(true);
                        }
                    }, 500);
                }
            });

            refreshView.setOnBottomLoadMoreTime(new OnBottomLoadMoreTime() {

                @Override
                public boolean isBottom() {
//                    if (stickyLv.getLastVisiblePosition() == mTotalItemCount - 1) {
//                        View lastChild = stickyLv.getListChildAt(stickyLv.getListChildCount() - 1);
//                        return (lastChild.getBottom() + stickyLv.getPaddingBottom()) <= stickyLv.getMeasuredHeight();
//                    }
                    return false;
                }
            });


            ivDimensCode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity) getActivity()).openOrClose();
                }
            });
            ivUser.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {

                    if (UserManager.getInstance().isLogin()) {
                        waitDialog.show();
                        Log.v(TAG, "发送请求开始");
                        YTRequestParams params = new YTBaseRequestParams(YTBaseRequestParams.PARAM_TYPE_JSON);
                        params.put("uid", UserManager.getInstance().getUserInfo().getAgent_id());
//        params.put("phone_number", account);
                        Logs.e(TAG, ServiceUrlManager.getServiceAbsUrl(ServiceCode.COURSE_CALENDAR, getActivity()));
                        Logs.e(TAG, params.getParamsString());
                        APPRestClient.post(ServiceUrlManager.getServiceAbsUrl(ServiceCode.COURSE_CALENDAR, getActivity()), params,
                                new APPResponseHandler<BookingCalendarBean>(BookingCalendarBean.class, null) {

                                    @Override
                                    public void onSuccess(BookingCalendarBean result) {
                                        waitDialog.dismiss();
//                                    接口返回{"LIST":[{"ORDER_TIME":"2017-01-13"},{"ORDER_TIME":"2017-01-12"}],"MSG":"交易成功","STATUS":"1"}
                                        Logs.v(TAG, "onSuccess");
                                        Logs.v(TAG, result.toString());

                                        if (result.getSTATUS().equals("1")) {

                                            ArrayList<String> dates = new ArrayList<>();
                                            for (int i = 0; i < result.getLIST().size(); i++) {
                                                dates.add(result.getLIST().get(i).getORDER_TIME());
                                            }
                                            Intent intent = new Intent(getActivity(), CalendarPickerActivity.class);
                                            intent.putStringArrayListExtra("date", dates);
                                            intent.putExtra("type", "2");

                                            getActivity().startActivity(intent);
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
                                                ToastTools.showShort(getActivity(), errorMsg);
                                                break;
                                            case "YOGA_LOGIN_PASS_FAIL":
                                                ToastTools.showShort(getActivity(), errorMsg);
                                                break;
//                            case "SMS_EORROR_THREE_TIMES"://该手机号码已被锁定，请一小时后重试！
//                                ToastTools.showShort(getApplicationContext(), errorMsg);
//                                break;
                                            default:
                                                ToastTools.showShort(getActivity(), errorMsg);
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
                    } else {
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);

                    }


                }
            });
            recyclerView = (RecyclerView) contentView.findViewById(R.id.my_recyclerview);
            ProgressDialog dialog = new ProgressDialog(getActivity());
            dialog.setMessage(this.getString(R.string.progress_load_msg));
            dialog.setIndeterminate(false);
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(false);
            waitDialog = dialog;

            if (UserManager.getInstance().isLogin()) {
                setData(UserManager.getInstance().getUserInfo().getAgent_id(), null);
            } else {
                NoLoginAdapter noLoginAdapter = new NoLoginAdapter(null, getActivity());
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setAdapter(noLoginAdapter);

            }


        } else {
            isFirstLoad = false;
            ViewGroup vp = (ViewGroup) contentView.getParent();
            if (null != vp) {
                vp.removeView(contentView);
            }
        }

        return contentView;
    }

    private void setData(String uid, String date) {


//        waitDialog.show();
        personList.clear();
        // 发送登录请求
        Log.v(TAG, "发送请求开始");
        YTRequestParams params = new YTBaseRequestParams(YTBaseRequestParams.PARAM_TYPE_JSON);
        params.put("uid", uid);
        if (!StringTools.isEmpty(date)) {
            params.put("date", date);
        }
////        params.put("email", AndroidUtil.getDeviceUUID(this));//设备id
//        params.put("yoga_pass", passWord);//设备类型
//        Logs.e(TAG, areaCode + "---" + account);
        Logs.e(TAG, ServiceUrlManager.getServiceAbsUrl(ServiceCode.BOOKING_LIST, getActivity()));
        Logs.e(TAG, params.getParamsString());
        APPRestClient.post(ServiceUrlManager.getServiceAbsUrl(ServiceCode.BOOKING_LIST, getActivity()), params,
                new APPResponseHandler<BookListBean>(BookListBean.class, null) {

                    @Override
                    public void onSuccess(BookListBean result) {
//                        waitDialog.dismiss();
                        Logs.v(TAG, "onSuccess");
                        Logs.v(TAG, result.toString());

//                        if(refreshView.mPullRefreshing){
//                            refreshView.stopRefresh();
//                        }
                        if (result.getSTATUS().equals("1")) {
//                            DoLogin login=result;
//                            EventBus.getDefault().post(new SwitchFragmentEvent(4));

                            if (result.getLIST() != null && result.getLIST().size() > 0) {
                                for (int i = 0; i < result.getLIST().size(); i++) {
                                    BookListBean.LISTBean bean = result.getLIST().get(i);
                                    personList.add(new Curriculum(bean.getCLASS_NAME(), bean.getSTART_DATE() + " " + bean.getSTART_TIME(), bean.getCOACH_NAME(), bean.getTRAN_MONEY() + "", bean.getCLASS_ID() + "", bean.getORDER_ID(), bean.getORDER_STATUS()));
                                }
//                                loading.setStatus(LoadingLayout.Success);

                            } else {
//                                loading.setStatus(LoadingLayout.Empty);//无数据
                                EmptyAdapter emptyAdapter = new EmptyAdapter(null, getActivity());
                                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                                recyclerView.setAdapter(emptyAdapter);

                                return;

                            }
                            if (mAdapter == null) {

                                mAdapter = new SimpleAdapter(personList, getActivity());
                                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                                recyclerView.setAdapter(mAdapter);
                            } else {
                                mAdapter.notifyDataSetChanged();

                            }
                        }


                    }


                    @Override
                    public void onFailure(String errorCode, String errorMsg) {
//                        waitDialog.dismiss();
                        Logs.e(TAG, "onFailure");
                        Logs.e("Login_errorCode", errorCode);
//                        接口返回{"MSG":"登录密码错误！","STATUS":"YOGA_LOGIN_PASS_FAIL"}
//                        "MSG":"该账户还没注册，请先注册！","STATUS":"YOGA_ISNOT_REGISTERED"
                        switch (errorCode) {
                            case "YOGA_CUST_FREEZE"://該帳戶已凍結，請與管理員聯繫！
                                ToastTools.showShort(getActivity(), errorMsg);
                                FrozenAdapter frozenAdapter = new FrozenAdapter(null, getActivity());
                                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                                recyclerView.setAdapter(frozenAdapter);
                                break;
                            case "sessionLogOut"://登录超时  SessionTimeout!
                                ToastTools.showShort(getActivity(), errorMsg);
                                break;
//                            case "SMS_EORROR_THREE_TIMES"://该手机号码已被锁定，请一小时后重试！
//                                ToastTools.showShort(getApplicationContext(), errorMsg);
//                                break;
                            default:
                                ToastTools.showShort(getActivity(), errorMsg);
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


//        personList.add(new Curriculum(getResources().getString(R.string.data_class1), "2015-12-18 12:09", getResources().getString(R.string.data_name1), "200.0"));
//        personList.add(new Curriculum(getResources().getString(R.string.data_class2), "2015-12-19 12:09", getResources().getString(R.string.data_name2), "300.0"));
//        personList.add(new Curriculum(getResources().getString(R.string.data_class3), "2015-12-20 12:09", getResources().getString(R.string.data_name3), "100.0"));
//        personList.add(new Curriculum(getResources().getString(R.string.data_class4), "2015-12-21 12:09", getResources().getString(R.string.data_name4), "100.0"));
//        personList.add(new Curriculum(getResources().getString(R.string.data_class5), "2015-12-22 12:09", getResources().getString(R.string.data_name5), "400.0"));
//        mAdapter = new SimpleAdapter(personList, getActivity());
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        recyclerView.setAdapter(mAdapter);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSwitchFragmentEvent(SwitchFragmentEvent event) {

        if (null != event) {
//            Log.e("ReservationFragment", event.getPosition() + "");
            if (event.getPosition() == 3) {
                title.setText(getResources().getString(R.string.reservation_record));
                personList.clear();
                setData(UserManager.getInstance().getUserInfo().getAgent_id(), null);
            }
            if (event.getPosition() == 4) {//登陆刷新界面
                personList.clear();
                mAdapter = null;
                setData(UserManager.getInstance().getUserInfo().getAgent_id(), null);
            }

            if (event.getPosition() == 5) {//退出登录刷新界面
                NoLoginAdapter noLoginAdapter = new NoLoginAdapter(null, getActivity());
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setAdapter(noLoginAdapter);
            }

        }

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void FreshWithCalendar(FreshTimeTableWithCalendar event) {

        if (null != event) {

            if (!StringTools.isEmpty(event.getDate()) && event.getType().equals("2")) {
                Log.e(TAG, event.getDate());
                personList.clear();
                mAdapter = null;
                setData(UserManager.getInstance().getUserInfo().getAgent_id(), event.getDate());
            }

        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
