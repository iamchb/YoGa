package com.yitong.yoga.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yitong.yoga.R;

public class LanguageSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_selection);
        getSupportActionBar().hide();

        RelativeLayout titleLay = (RelativeLayout) findViewById(R.id.titleLay);
        TextView title = (TextView) titleLay.findViewById(R.id.title_main_txt_title);
        title.setText("語言選擇");
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

        RelativeLayout fanti = (RelativeLayout) findViewById(R.id.language_rl_triditional);
        RelativeLayout jianti = (RelativeLayout) findViewById(R.id.language_rl_implified);
        RelativeLayout english = (RelativeLayout) findViewById(R.id.language_rl_english);

        final ImageView iamgeview1 = (ImageView) findViewById(R.id.language_iv_triditional);
        final ImageView iamgeview2 = (ImageView) findViewById(R.id.language_iv_implified);
        final ImageView iamgeview3 = (ImageView) findViewById(R.id.language_iv_english);
        fanti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iamgeview1.setVisibility(View.VISIBLE);
                iamgeview2.setVisibility(View.GONE);
                iamgeview3.setVisibility(View.GONE);
            }
        });

        jianti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iamgeview1.setVisibility(View.GONE);
                iamgeview2.setVisibility(View.VISIBLE);
                iamgeview3.setVisibility(View.GONE);
            }
        });

        english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iamgeview1.setVisibility(View.GONE);
                iamgeview2.setVisibility(View.GONE);
                iamgeview3.setVisibility(View.VISIBLE);
            }
        });
    }
}
