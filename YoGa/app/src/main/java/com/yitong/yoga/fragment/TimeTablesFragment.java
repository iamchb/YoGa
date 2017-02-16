package com.yitong.yoga.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.listener.OnBottomLoadMoreTime;
import com.andview.refreshview.listener.OnTopRefreshTime;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.weavey.loading.lib.LoadingLayout;
import com.yitong.yoga.MyApplication;
import com.yitong.yoga.R;
import com.yitong.yoga.ServiceCode;
import com.yitong.yoga.activity.CalendarPickerActivity;
import com.yitong.yoga.activity.MainActivity;
import com.yitong.yoga.bean.DateBean;
import com.yitong.yoga.bean.FreshTimeTable;
import com.yitong.yoga.bean.FreshTimeTableWithCalendar;
import com.yitong.yoga.bean.SwitchFragmentEvent;
import com.yitong.yoga.http.APPResponseHandler;
import com.yitong.yoga.http.APPRestClient;
import com.yitong.yoga.http.AppJSResponseHandler;
import com.yitong.yoga.http.ServiceUrlManager;
import com.yitong.yoga.http.YTBaseRequestParams;
import com.yitong.yoga.http.YTRequestParams;
import com.yitong.yoga.smileyloadingview.SmileyHeaderView;
import com.yitong.yoga.stickyListHeaders.StickyListBean;
import com.yitong.yoga.stickyListHeaders.StickyListHeadersListView;
import com.yitong.yoga.stickyListHeaders.StickylistAdapter;
import com.yitong.yoga.utils.DataUtils;
import com.yitong.yoga.utils.Logs;
import com.yitong.yoga.utils.NoDoubleClickListener;
import com.yitong.yoga.utils.StringTools;
import com.yitong.yoga.utils.ToastTools;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * YoGa
 * Created by Chb on  2016/10/14 15:33
 */

public class TimeTablesFragment extends Fragment {

    private StickyListHeadersListView stickyLv;
    private List<StickyListBean> list = new ArrayList<StickyListBean>();
    private XRefreshView refreshView;
    private int mTotalItemCount;
    private StickylistAdapter adapter;
    private final int mPinnedTime = 1000;
    protected View contentView = null;
    protected boolean isFirstLoad = true;
    private ImageView ivDimensCode;// 二维码
    private ImageView ivUser;// 个人中心
    MaterialSpinner spinner;
    TextView title;
    private static final String TAG = "TimeTablesFragment";
    private Dialog waitDialog;
    private LoadingLayout loading;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (contentView == null) {
            EventBus.getDefault().register(this);
            contentView = inflater.inflate(R.layout.fragment_timetables, container, false);
            isFirstLoad = true;
            RelativeLayout titleLay = (RelativeLayout) contentView.findViewById(R.id.titleLay);
            title = (TextView) titleLay.findViewById(R.id.title_main_txt_title);
            title.setText(R.string.timetable);
            titleLay.setBackgroundColor(getResources().getColor(R.color.thirdColor));

            ivUser = (ImageView) contentView.findViewById(R.id.title_main_img_user);
            ivDimensCode = (ImageView) contentView.findViewById(R.id.title_main_iv_dimension_code);
            ivUser.setImageResource(R.drawable.u39);
            ivDimensCode.setImageResource(R.drawable.ic_dehaze_black_24dp);
            ivDimensCode.setVisibility(View.VISIBLE);

            ivDimensCode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity) getActivity()).openOrClose();
                }
            });

            loading = (LoadingLayout) contentView.findViewById(R.id.loading);
            loading.setLoadingPage(R.layout.define_loading_page).setOnReloadListener(new LoadingLayout.OnReloadListener() {

                @Override
                public void onReload(View v) {

                    Toast.makeText(getActivity(), "重试", Toast.LENGTH_SHORT).show();
                }
            });


            ivUser.setOnClickListener(new NoDoubleClickListener() {

                @Override
                public void onNoDoubleClick(View v) {
//                    ((MainActivity) getActivity()).showDialog();
                    waitDialog.show();
                    Log.v(TAG, "发送请求开始");
                    YTRequestParams params = new YTBaseRequestParams(YTBaseRequestParams.PARAM_TYPE_JSON);
//        params.put("area_code", areaCode);
//        params.put("phone_number", account);
                    Logs.e(TAG, ServiceUrlManager.getServiceAbsUrl(ServiceCode.CURRI_CALENDAR, getActivity()));
                    Logs.e(TAG, params.getParamsString());
                    APPRestClient.post(ServiceUrlManager.getServiceAbsUrl(ServiceCode.CURRI_CALENDAR, getActivity()), params,
                            new APPResponseHandler<DateBean>(DateBean.class, null) {

                                @Override
                                public void onSuccess(DateBean result) {
                                    waitDialog.dismiss();
                                    Logs.v(TAG, "onSuccess");
                                    Logs.v(TAG, result.toString());

                                    if (result.getSTATUS().equals("1")) {


                                        ArrayList<String> dates = new ArrayList<>();
                                        for (int i = 0; i < result.getLIST().size(); i++) {
                                            dates.add(result.getLIST().get(i).getSTART_DATE());
                                        }
                                        Intent intent = new Intent(getActivity(), CalendarPickerActivity.class);
                                        intent.putStringArrayListExtra("date", dates);
                                        intent.putExtra("type", "1");
                                        getActivity().startActivity(intent);
//                                        }


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

                }
            });

            ProgressDialog dialog = new ProgressDialog(getActivity());
            dialog.setMessage(this.getString(R.string.progress_load_msg));
            dialog.setIndeterminate(false);
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(false);
            waitDialog = dialog;

            spinner = (MaterialSpinner) contentView.findViewById(R.id.spinner);
            spinner.setPadding(20, 5, 0, 0);
            spinner.setGravity(Gravity.CENTER_VERTICAL);
            spinner.setItems(getResources().getString(R.string.course_list_all), getResources().getString(R.string.course_list_aomen), getResources().getString(R.string.course_list_sz), getResources().getString(R.string.course_list_luhuan));
//            spinner.setItems("dsda", "dasdad", "fdfdfd", "cbcb");

            initData();


            stickyLv = (StickyListHeadersListView) contentView.findViewById(R.id.sticky_list);
            adapter = new StickylistAdapter(getActivity(), list);
            stickyLv.setAdapter(adapter);
            refreshView = (XRefreshView) contentView.findViewById(R.id.custom_view);
            refreshView.setPullLoadEnable(false);
//            refreshView.setAutoRefresh(true);
            refreshView.setPinnedTime(mPinnedTime);
            refreshView.setCustomHeaderView(new SmileyHeaderView(getActivity()));
//            refreshView.setCustomHeaderView(new CustomHeader(getActivity(), mPinnedTime));
            refreshView.setMoveFootWhenDisablePullLoadMore(false);

            refreshView.setOnTopRefreshTime(new OnTopRefreshTime() {

                @Override
                public boolean isTop() {
                    if (stickyLv.getFirstVisiblePosition() == 0) {
                        View firstVisibleChild = stickyLv.getListChildAt(0);

                        if (null != firstVisibleChild) {
                            return firstVisibleChild.getTop() >= 0;
                        }
                    }
                    return false;
                }
            });

            refreshView.setOnBottomLoadMoreTime(new OnBottomLoadMoreTime() {

                @Override
                public boolean isBottom() {
                    if (stickyLv.getLastVisiblePosition() == mTotalItemCount - 1) {
                        View lastChild = stickyLv.getListChildAt(stickyLv.getListChildCount() - 1);

                        if (null != lastChild) {
                            return (lastChild.getBottom() + stickyLv.getPaddingBottom()) <= stickyLv.getMeasuredHeight();
                        }
                    }
                    return false;
                }
            });

            stickyLv.setOnScrollListener(new AbsListView.OnScrollListener() {

                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {

                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem,
                                     int visibleItemCount, int totalItemCount) {
                    mTotalItemCount = totalItemCount;
                }
            });

            refreshView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {

                @Override
                public void onRefresh() {


                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            list.clear();
                            getData("0", null);
                            spinner.setSelectedIndex(0);
                            refreshView.stopRefresh();
                        }
                    }, 2000);

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
            spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

                @Override
                public void onItemSelected(MaterialSpinner view, int position, long id, String item) {

                    list.clear();
                    getData(position + "", null);

//                    adapter = new StickylistAdapter(getActivity(), list);
//                    stickyLv.setAdapter(adapter);
                }
            });

        } else {
            isFirstLoad = false;
            ViewGroup vp = (ViewGroup) contentView.getParent();
            if (null != vp) {
                vp.removeView(contentView);
            }
        }

        return contentView;
    }

    int section = 0;
    String YM = null;
    String content = null;
    String chineseNumber[] = {"星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"};

    //        String EnglishNumber[] = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    private void initData() {
        Log.e("week_language", MyApplication.Language + "");
        getData("0", null);

//        section = 0;
//
//        String time[] = {"2016-10-19", "2016-10-20", "2016-10-21", "2016-10-22", "2016-10-23", "2016-10-24", "2016-10-25"};
//        for (int i = 0; i < 35; i++) {
//            if (i % 5 == 0) {
//                if (section < 7) {
//
//                    if (MyApplication.Language.equals("2")) {
//                        YM = EnglishNumber[section] + "  " + time[section];
//                    } else {
//                        YM = "星期" + chineseNumber[section] + "  " + time[section];
//                    }
//                }
//                section++;
//            }
////            content = "第" + (i +1)+ "門課程";
//            content = getResources().getString(R.string.data_class3) + i;
//            StickyListBean bean = new StickyListBean(section, YM, content, "joseph", getResources().getString(R.string.course_list_aomen), "6:30", "am");
//            list.add(bean);
//        }

    }

    private void getData(String type, String date) {
//        waitDialog.show();
        YTRequestParams params = new YTBaseRequestParams(YTBaseRequestParams.PARAM_TYPE_JSON);
        params.put("type", type);
        if (!StringTools.isEmpty(date) && type.equals("4")) {
            params.put("date", date);
        }

        params.put("local", "0");//设备类型
        Logs.e(TAG, ServiceUrlManager.getServiceAbsUrl(ServiceCode.CURRI_SCHEDULE, getActivity()));
        Logs.e(TAG, params.getParamsString());
        APPRestClient.post(ServiceUrlManager.getServiceAbsUrl(ServiceCode.CURRI_SCHEDULE, getActivity()), params,

                new AppJSResponseHandler() {
                    @Override
                    public void onSuccess(String result, String successFunc) {
//                        waitDialog.dismiss();
                        Logs.v(TAG, "onSuccess");
                        Logs.v(TAG, result);

//                        if( refreshView!=null){
//                            refreshView.stopRefresh();
//                        }
                        try {
                            list.clear();
                            JSONObject object = new JSONObject(result);
                            JSONArray array = object.getJSONArray("LIST");
                            Logs.v(TAG, array.toString());

                            if (array.length() <= 0) {
                                loading.setStatus(LoadingLayout.Empty);//无数据

                            } else {
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject jsonObject = array.getJSONObject(i);
                                    String date = jsonObject.getString("START_DATE");
                                    JSONArray array1 = jsonObject.getJSONArray(date);
                                    for (int j = 0; j < array1.length(); j++) {

                                        JSONObject object1 = array1.getJSONObject(j);
                                        String CLASS_ADDR = object1.getString("CLASS_ADDR");
                                        int CLASS_ID = object1.getInt("CLASS_ID");
                                        String CLASS_NAME = object1.getString("CLASS_NAME");
                                        String COACH_NAME = object1.getString("COACH_NAME");
                                        String START_DATE = object1.getString("START_DATE");
                                        int week = DataUtils.dayForWeek(START_DATE);
                                        Logs.v(TAG, week + "");
                                        String START_TIME = object1.getString("START_TIME");
                                        GregorianCalendar ca = new GregorianCalendar();
                                        String for_split[] = START_TIME.split(":");
                                        Logs.v(TAG, for_split[0]);
                                        String ap_pm;
                                        int time = Integer.parseInt(for_split[0]);
                                        if (time > 12) {
                                            ap_pm = "am";
                                        } else {
                                            ap_pm = "pm";
                                        }
                                        StickyListBean bean = new StickyListBean(i, chineseNumber[week - 1] + "  " + START_DATE, CLASS_NAME, COACH_NAME, CLASS_ADDR, START_TIME, CLASS_ID + "", ap_pm);
                                        list.add(bean);
                                        loading.setStatus(LoadingLayout.Success);
                                        if (null != adapter) {
                                            adapter.notifyDataSetChanged();
                                        } else {
                                            adapter = new StickylistAdapter(getActivity(), list);
                                            stickyLv.setAdapter(adapter);
                                        }
                                    }
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int errorCode, String failureFunc) {
//                        waitDialog.dismiss();
                        Logs.e(TAG, "onFailure");
                        Logs.e(TAG, errorCode + "");
                        ToastTools.showShort(getActivity(), failureFunc);
                    }

                }, null);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSwitchFragmentEvent(SwitchFragmentEvent event) {

        if (null != event) {
//            Log.e("TimeTablesFragment", event.getPosition() + "");
            if (event.getPosition() == 3) {
                title.setText(R.string.timetable);
                spinner.setItems(getResources().getString(R.string.course_list_all), getResources().getString(R.string.course_list_aomen), getResources().getString(R.string.course_list_luhuan), getResources().getString(R.string.course_list_sz));
                list.clear();
                initData();
                adapter = new StickylistAdapter(getActivity(), list);
                stickyLv.setAdapter(adapter);
            }

        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void FreshWithCalendar(FreshTimeTableWithCalendar event) {

        if (null != event) {

            if (!StringTools.isEmpty(event.getDate()) && event.getType().equals("1")) {
                Log.e("TimeTablesFragment", event.getDate());
                list.clear();
                getData("4", event.getDate());
//                adapter = new StickylistAdapter(getActivity(), list);
//                stickyLv.setAdapter(adapter);
            }

        }

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void FreshWithCalendar(FreshTimeTable event) {

        if (null != event) {

            if (!StringTools.isEmpty(event.getFlag()) && event.getFlag().equals("10")) {
                Log.e("TimeTablesFragment", event.getFlag());
                list.clear();
                initData();
                adapter = new StickylistAdapter(getActivity(), list);
                stickyLv.setAdapter(adapter);
            }

        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
