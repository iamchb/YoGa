package com.yitong.yoga.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yitong.yoga.R;
import com.yitong.yoga.utils.QRCodeUtil;

public class BookingDetailActivity extends AppCompatActivity {

    ImageView my_sccode_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_detail);
//        getSupportActionBar().hide();
        RelativeLayout titleLay = (RelativeLayout) findViewById(R.id.titleLay);
        TextView title = (TextView) titleLay.findViewById(R.id.title_main_txt_title);
        TextView booking_number = (TextView)findViewById(R.id.booking_number);
        my_sccode_image= (ImageView) findViewById(R.id.my_sccode_image);
        title.setText(getResources().getString(R.string.booking_detail));



        titleLay.setBackgroundColor(getResources().getColor(R.color.fourthColor));


        ImageView ivDimensCode = (ImageView) findViewById(R.id.title_main_iv_dimension_code);
        ivDimensCode.setImageResource(R.drawable.ic_chevron_left_black_24dp);
        ivDimensCode.setVisibility(View.VISIBLE);
        ivDimensCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent=getIntent();
        String code_id=intent.getStringExtra("code_id");
//        booking_number.setText(getResources().getString(R.string.booking_number));
        booking_number.setText(code_id);

        Bitmap logo= BitmapFactory.decodeResource(getResources(),R.drawable.u99,null);
        Bitmap image= QRCodeUtil.createImage(code_id,500,500,logo);

        my_sccode_image.setImageBitmap(image);
    }
}
