package com.yitong.yoga.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yitong.yoga.R;

public class AboutUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
//        getSupportActionBar().hide();
//        StatusBarCompat.setStatusBarColor(this, Color.GREEN);
//        StatusBarCompat.setStatusBarColor(this, Color.TRANSPARENT);
        RelativeLayout titleLay = (RelativeLayout) findViewById(R.id.titleLay);
        TextView title = (TextView) titleLay.findViewById(R.id.title_main_txt_title);
        title.setText(getResources().getString(R.string.about_us));
        titleLay.setBackgroundColor(getResources().getColor(R.color.thirdColor));


        ImageView ivDimensCode = (ImageView) findViewById(R.id.title_main_iv_dimension_code);
        ivDimensCode.setImageResource(R.drawable.ic_chevron_left_black_24dp);

        ivDimensCode.setVisibility(View.VISIBLE);
        ivDimensCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
