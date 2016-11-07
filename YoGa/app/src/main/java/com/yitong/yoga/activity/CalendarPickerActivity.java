package com.yitong.yoga.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.timessquare.CalendarPickerView;
import com.yitong.yoga.R;

import java.util.Calendar;
import java.util.Date;

/**
 * YoGa
 * Created by Chb on  2016/10/19 10:34
 */

public class CalendarPickerActivity extends AppCompatActivity {

    private CalendarPickerView calendar;
    private ImageView ivDimensCode;// 二维码

    //    private Button ceshi;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        long firstTime = System.currentTimeMillis();
        setContentView(R.layout.activity_calendar);
        getSupportActionBar().hide();
//        ceshi= (Button) findViewById(R.id.ceshi);
        calendar = (CalendarPickerView) findViewById(R.id.calendar_view);
        final Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.MONTH, 1);

        final Calendar lastYear = Calendar.getInstance();
        lastYear.add(Calendar.MONTH, -1);

        calendar.init(lastYear.getTime(), nextYear.getTime()) //
                .inMode(CalendarPickerView.SelectionMode.SINGLE) //
                .withSelectedDate(new Date());



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
        title.setText("日曆");
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
