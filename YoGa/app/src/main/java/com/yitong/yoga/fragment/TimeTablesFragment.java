package com.yitong.yoga.fragment;

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

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.listener.OnBottomLoadMoreTime;
import com.andview.refreshview.listener.OnTopRefreshTime;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.yitong.yoga.MyApplication;
import com.yitong.yoga.R;
import com.yitong.yoga.activity.CalendarPickerActivity;
import com.yitong.yoga.activity.MainActivity;
import com.yitong.yoga.bean.SwitchFragmentEvent;
import com.yitong.yoga.smileyloadingview.SmileyHeaderView;
import com.yitong.yoga.stickyListHeaders.StickyListBean;
import com.yitong.yoga.stickyListHeaders.StickyListHeadersListView;
import com.yitong.yoga.stickyListHeaders.StickylistAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
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


            ivUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    ((MainActivity) getActivity()).showDialog();
                    Intent intent=new Intent(getActivity(), CalendarPickerActivity.class);
                    getActivity().startActivity(intent);
                }
            });

            spinner = (MaterialSpinner) contentView.findViewById(R.id.spinner);
            spinner.setPadding(20,5,0,0);
            spinner.setGravity(Gravity.CENTER_VERTICAL);
            spinner.setItems(getResources().getString(R.string.course_list_all), getResources().getString(R.string.course_list_aomen), getResources().getString(R.string.course_list_luhuan), getResources().getString(R.string.course_list_sz));
//            spinner.setItems("dsda", "dasdad", "fdfdfd", "cbcb");
            spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

                @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
//                    Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
                }
            });

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
            refreshView. setMoveFootWhenDisablePullLoadMore(false);
            refreshView.setOnTopRefreshTime(new OnTopRefreshTime() {

                @Override
                public boolean isTop() {
                    if (stickyLv.getFirstVisiblePosition() == 0) {
                        View firstVisibleChild = stickyLv.getListChildAt(0);
                        return firstVisibleChild.getTop() >= 0;
                    }
                    return false;
                }
            });

            refreshView.setOnBottomLoadMoreTime(new OnBottomLoadMoreTime() {

                @Override
                public boolean isBottom() {
                    if (stickyLv.getLastVisiblePosition() == mTotalItemCount - 1) {
                        View lastChild = stickyLv.getListChildAt(stickyLv.getListChildCount() - 1);
                        return (lastChild.getBottom() + stickyLv.getPaddingBottom()) <= stickyLv.getMeasuredHeight();
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

    private void initData() {
        Log.e("week_language",MyApplication.Language+"");
        section=0;
        String chineseNumber[]={  "一", "二", "三", "四", "五", "六", "日"  };
        String EnglishNumber[]={  "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"  };
        String time[]={"2016-10-19","2016-10-20","2016-10-21","2016-10-22","2016-10-23","2016-10-24","2016-10-25"};
        for (int i = 0; i < 35; i++) {
            if (i % 5 == 0) {
                if(section<7){

                    if(MyApplication.Language.equals("2")){
                        YM = EnglishNumber[section]+"  "+time[section];
                    }else{
                        YM = "星期" + chineseNumber[section]+"  "+time[section];
                    }
                }
                section++;
            }
//            content = "第" + (i +1)+ "門課程";
            content = getResources().getString(R.string.data_class3)+i;
            StickyListBean bean = new StickyListBean(section, YM, content,"joseph",getResources().getString(R.string.course_list_aomen),"6:30","am");
            list.add(bean);
        }

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

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
