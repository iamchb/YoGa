package com.yitong.yoga.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yitong.yoga.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//     getSupportActionBar().hide();

        RelativeLayout titleLay = (RelativeLayout) findViewById(R.id.titleLay);
        TextView title = (TextView) titleLay.findViewById(R.id.title_main_txt_title);
        title.setText(getResources().getString(R.string.login));
        titleLay.setBackgroundColor(getResources().getColor(R.color.thirdColor));


        ImageView ivDimensCode = (ImageView) findViewById(R.id.title_main_iv_dimension_code);
        ivDimensCode.setImageResource(R.drawable.ic_chevron_left_black_24dp);
        Button ivUser = (Button) findViewById(R.id.title_main_img_user);
        ivUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                finish();
            }
        });

        ivDimensCode.setVisibility(View.VISIBLE);
        ivDimensCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button btn= (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
