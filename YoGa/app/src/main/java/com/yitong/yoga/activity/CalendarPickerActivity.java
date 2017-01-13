package com.yitong.yoga.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.timessquare.CalendarPickerView;
import com.yitong.yoga.R;
import com.yitong.yoga.bean.FreshTimeTableWithCalendar;

import org.greenrobot.eventbus.EventBus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * YoGa
 * Created by Chb on  2016/10/19 10:34
 */

public class CalendarPickerActivity extends AppCompatActivity {

    private CalendarPickerView calendar;
    private ImageView ivDimensCode;// 二维码
    private static final String TAG = "CalendarPickerActivity";
    ArrayList<Date> dates;

    //    private Button ceshi;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        long firstTime = System.currentTimeMillis();
        setContentView(R.layout.activity_calendar);
//        getSupportActionBar().hide();
//        ceshi= (Button) findViewById(R.id.ceshi);


        calendar = (CalendarPickerView) findViewById(R.id.calendar_view);
        final Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.MONTH, 4);

        final Calendar lastYear = Calendar.getInstance();

        Intent intent = getIntent();

        ArrayList<String> list = intent.getStringArrayListExtra("date");
        final String type = intent.getStringExtra("type");
        dates = new ArrayList<>();
        lastYear.add(Calendar.MONTH, -1);
        if (list != null) {

            for (int i = 0; i < list.size(); i++) {
                Date date = null;
                try {
                    date = new SimpleDateFormat("yyyy-MM-dd").parse(list.get(i));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                dates.add(date);
            }
//            new Date(System.currentTimeMillis() - 48 * 60 * 60)
            calendar.init(new Date(), nextYear.getTime()) //
                    .inMode(CalendarPickerView.SelectionMode.SINGLE) //
                    .withHighlightedDates(dates)
                    .withSelectedDate(new Date());
        } else {
            calendar.init(new Date(), nextYear.getTime()) //
                    .inMode(CalendarPickerView.SelectionMode.SINGLE) //
                    .withSelectedDate(new Date());
        }


        calendar.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {


                if (dates != null && dates.size() >= 1) {

                    Log.e(TAG, type);

                    for (int i = 0; i < dates.size(); i++) {

                        if (date.toString().equals(dates.get(i).toString())) {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                            String str = sdf.format(date);
                            if (type.equals("1")) {
                                EventBus.getDefault().post(new FreshTimeTableWithCalendar(str, "1"));
                                finish();
                            } else if (type.equals("2")) {
                                Log.e(TAG, str);
                                EventBus.getDefault().post(new FreshTimeTableWithCalendar(str, "2"));
                                finish();
                            }
                            return;

                        }
//                    else {
//                        ToastUtils.showShortToast("对不起，该天没有相关课程");
//                    }
                    }
                }


            }

            @Override
            public void onDateUnselected(Date date) {

            }
        });

//        final Calendar currentYear = Calendar.getInstance();
//        currentYear.add(Calendar.YEAR,0);//今年
//
//        final Calendar ca = Calendar.getInstance();
//        ca.set(1800, 4, 14);
//        Date year = ca.getTime(); //自己设置时间

//        Log.e("shijiancha",(System.currentTimeMillis()-firstTime)+"");
//        init(Date selectedDate, Date minDate, Date maxDate) {...}
        // selectedDate 当前选中日期
        // minDate 对早可选日期 （包含）
        // maxDate 最晚可选日期（不包含

        RelativeLayout titleLay = (RelativeLayout) findViewById(R.id.titleLay);
        TextView title = (TextView) titleLay.findViewById(R.id.title_main_txt_title);
        title.setText(getResources().getString(R.string.calendar));
        titleLay.setBackgroundColor(getResources().getColor(R.color.blue_light));
        ivDimensCode = (ImageView) findViewById(R.id.title_main_iv_dimension_code);
        ivDimensCode.setImageResource(R.drawable.ic_chevron_left_black_24dp);
        ivDimensCode.setVisibility(View.VISIBLE);


        ivDimensCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

//        DatePicker picker = (DatePicker) findViewById(R.id.main_dp);
//        picker.setDate(2015, 10);
//        picker.setMode(DPMode.SINGLE);


    }
}
