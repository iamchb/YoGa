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
import com.yitong.yoga.bean.curriculum;

import java.util.ArrayList;
import java.util.List;

public class BookingHistoryActivity extends AppCompatActivity {
    private SimpleAdapter mAdapter;
    private RecyclerView recyclerView;
    List<curriculum> personList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_history);
        getSupportActionBar().hide();
        RelativeLayout titleLay = (RelativeLayout) findViewById(R.id.titleLay);
        TextView title = (TextView) titleLay.findViewById(R.id.title_main_txt_title);
        title.setText("預定歷史");
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
        personList.add(new curriculum("健康養生", "2015-12-18 12:09",  "尹鵬源","200.0"));
        personList.add(new curriculum("英語", "2015-12-19 12:09",  "joseph","300.0"));
        personList.add(new curriculum("陰陽瑜伽", "2015-12-20 12:09",  "simmon","100.0"));
        personList.add(new curriculum("太極", "2015-12-21 12:09",  "lili","100.0"));
        personList.add(new curriculum("社交禮儀", "2015-12-22 12:09",  "陳相鵬","400.0"));
        mAdapter = new SimpleAdapter(personList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);
    }
}
