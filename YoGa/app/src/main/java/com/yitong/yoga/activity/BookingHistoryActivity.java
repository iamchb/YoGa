package com.yitong.yoga.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yitong.yoga.R;
import com.yitong.yoga.adapter.SimpleAdapter;
import com.yitong.yoga.bean.Curriculum;

import java.util.ArrayList;
import java.util.List;

public class BookingHistoryActivity extends AppCompatActivity {
    private SimpleAdapter mAdapter;
    private RecyclerView recyclerView;
    List<Curriculum> personList = new ArrayList<>();
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
        ImageView  ivUser = (ImageView) findViewById(R.id.title_main_img_user);
        ivUser.setImageResource(R.drawable.u39);

        ivUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                    ((MainActivity) getActivity()).showDialog();
                Intent intent=new Intent(BookingHistoryActivity.this, CalendarPickerActivity.class);
                startActivity(intent);
            }
        });

        ivDimensCode.setVisibility(View.VISIBLE);
        ivDimensCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.my_recyclerview);
        personList.add(new Curriculum(getResources().getString(R.string.data_class1), "2015-12-18 12:09", getResources().getString(R.string.data_name1), "200.0"));
        personList.add(new Curriculum(getResources().getString(R.string.data_class2), "2015-12-19 12:09", getResources().getString(R.string.data_name2), "300.0"));
        personList.add(new Curriculum(getResources().getString(R.string.data_class3), "2015-12-20 12:09", getResources().getString(R.string.data_name3), "100.0"));
        personList.add(new Curriculum(getResources().getString(R.string.data_class4), "2015-12-21 12:09", getResources().getString(R.string.data_name4), "100.0"));
        personList.add(new Curriculum(getResources().getString(R.string.data_class5), "2015-12-22 12:09", getResources().getString(R.string.data_name5), "400.0"));
        mAdapter = new SimpleAdapter(personList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);
    }
}
