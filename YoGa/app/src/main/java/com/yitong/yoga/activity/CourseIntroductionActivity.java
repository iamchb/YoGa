package com.yitong.yoga.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yitong.yoga.R;

public class CourseIntroductionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_introduction);
//        getSupportActionBar().hide();
        RelativeLayout titleLay = (RelativeLayout) findViewById(R.id.titleLay);
        TextView title = (TextView) titleLay.findViewById(R.id.title_main_txt_title);
        title.setText(getResources().getString(R.string.course_introduction));
        titleLay.setBackgroundColor(getResources().getColor(R.color.thirdColor));


        ImageView   ivDimensCode = (ImageView) findViewById(R.id.title_main_iv_dimension_code);
        ivDimensCode.setImageResource(R.drawable.ic_chevron_left_black_24dp);
        ivDimensCode.setVisibility(View.VISIBLE);
        ivDimensCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button btn= (Button) findViewById(R.id.reserve_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(CourseIntroductionActivity.this);
                builder.setTitle(getResources().getString(R.string.hint_for_logout))
                        .setMessage(getResources().getString(R.string.course_hint_message))
                        .setPositiveButton(getResources().getString(R.string.confirm),
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        dialog.dismiss();

                                    }
                                }).show();
//                        .setNegativeButton(getString(R.string.activity_login_qx),
//                                new DialogInterface.OnClickListener() {
//
//                                    @Override
//                                    public void onClick(DialogInterface dialog,
//                                                        int which) {
//                                        dialog.dismiss();
//                                    }
//                                }).show();
            }
        });
    }
}
