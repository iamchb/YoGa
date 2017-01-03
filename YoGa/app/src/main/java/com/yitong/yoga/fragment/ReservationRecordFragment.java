package com.yitong.yoga.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yitong.yoga.R;
import com.yitong.yoga.activity.CalendarPickerActivity;
import com.yitong.yoga.activity.MainActivity;
import com.yitong.yoga.adapter.SimpleAdapter;
import com.yitong.yoga.bean.Curriculum;
import com.yitong.yoga.bean.SwitchFragmentEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * YoGa
 * Created by Chb on  2016/10/14 15:34
 */

public class ReservationRecordFragment extends Fragment {


    protected View contentView = null;
    protected boolean isFirstLoad = true;
    private SimpleAdapter mAdapter;
    private RecyclerView recyclerView;
//    Toolbar mToolbar;
    List<Curriculum> personList = new ArrayList<>();
    private ImageView ivDimensCode;// 二维码
    private ImageView ivUser;// 个人中心
    TextView title;
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
                    Intent intent = new Intent(getActivity(), CalendarPickerActivity.class);
                    getActivity().startActivity(intent);
                }
            });
            recyclerView = (RecyclerView) contentView.findViewById(R.id.my_recyclerview);
            setData();


        } else {
            isFirstLoad = false;
            ViewGroup vp = (ViewGroup) contentView.getParent();
            if (null != vp) {
                vp.removeView(contentView);
            }
        }

        return contentView;
    }

    private void setData() {
        personList.add(new Curriculum(getResources().getString(R.string.data_class1), "2015-12-18 12:09", getResources().getString(R.string.data_name1), "200.0"));
        personList.add(new Curriculum(getResources().getString(R.string.data_class2), "2015-12-19 12:09", getResources().getString(R.string.data_name2), "300.0"));
        personList.add(new Curriculum(getResources().getString(R.string.data_class3), "2015-12-20 12:09", getResources().getString(R.string.data_name3), "100.0"));
        personList.add(new Curriculum(getResources().getString(R.string.data_class4), "2015-12-21 12:09", getResources().getString(R.string.data_name4), "100.0"));
        personList.add(new Curriculum(getResources().getString(R.string.data_class5), "2015-12-22 12:09", getResources().getString(R.string.data_name5), "400.0"));
        mAdapter = new SimpleAdapter(personList, getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mAdapter);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSwitchFragmentEvent(SwitchFragmentEvent event) {

        if (null != event) {
//            Log.e("ReservationFragment", event.getPosition() + "");
            if (event.getPosition() == 3) {
                title.setText(getResources().getString(R.string.reservation_record));
                personList.clear();
                setData();
            }

        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
