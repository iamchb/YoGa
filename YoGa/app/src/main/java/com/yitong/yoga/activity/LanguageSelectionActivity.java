package com.yitong.yoga.activity;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yitong.yoga.MyApplication;
import com.yitong.yoga.R;
import com.yitong.yoga.bean.SwitchFragmentEvent;
import com.yitong.yoga.utils.SharedPreferenceUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.Locale;


public class LanguageSelectionActivity extends AppCompatActivity implements View.OnClickListener {
   private ImageView iamgeview1, iamgeview2, iamgeview3;
   private TextView  fantiText,jiantiText,englishText,title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_selection);
//        getSupportActionBar().hide();



        RelativeLayout titleLay = (RelativeLayout) findViewById(R.id.titleLay);

        RelativeLayout fanti = (RelativeLayout) findViewById(R.id.language_rl_triditional);
        RelativeLayout jianti = (RelativeLayout) findViewById(R.id.language_rl_implified);
        RelativeLayout english = (RelativeLayout) findViewById(R.id.language_rl_english);

        ImageView ivDimensCode = (ImageView) findViewById(R.id.title_main_iv_dimension_code);
        iamgeview1 = (ImageView) findViewById(R.id.language_iv_triditional);
        iamgeview2 = (ImageView) findViewById(R.id.language_iv_implified);
        iamgeview3 = (ImageView) findViewById(R.id.language_iv_english);

        fantiText= (TextView) findViewById(R.id.textView5);
        jiantiText= (TextView) findViewById(R.id.textView6);
        englishText= (TextView) findViewById(R.id.textView7);
        title = (TextView) titleLay.findViewById(R.id.title_main_txt_title);

        title.setText(getResources().getString(R.string.meun_text_yyxz));
        titleLay.setBackgroundColor(getResources().getColor(R.color.thirdColor));
        ivDimensCode.setImageResource(R.drawable.ic_chevron_left_black_24dp);

        ivDimensCode.setVisibility(View.VISIBLE);
        ivDimensCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new SwitchFragmentEvent(3));
                finish();
            }
        });


        Resources resources = getResources();
        Configuration config = resources.getConfiguration();
        if(MyApplication.Language.equals("0")){
            setTextAndIcon(fanti, config);
        }else if(MyApplication.Language.equals("1")){
            setTextAndIcon(jianti, config);
        }else if(MyApplication.Language.equals("2")){
            setTextAndIcon(english, config);
        }
        Log.e("LanguageActivity",config.locale.toString());

        fanti.setOnClickListener(this);
        jianti.setOnClickListener(this);
        english.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        Resources resources = getResources();
        Configuration config = resources.getConfiguration();
        DisplayMetrics dm = resources.getDisplayMetrics();

        setTextAndIcon(view, config);
        resources.updateConfiguration(config, dm);
    }

    private void setTextAndIcon(View view, Configuration config) {
        switch (view.getId()) {
            case R.id.language_rl_triditional:
                config.locale = Locale.TAIWAN;
                iamgeview1.setVisibility(View.VISIBLE);
                iamgeview2.setVisibility(View.GONE);
                iamgeview3.setVisibility(View.GONE);

                title.setText("語言選擇");
                fantiText.setText("繁體");
                jiantiText.setText("簡體");

                MyApplication.Language="0";
                SharedPreferenceUtil.setInfoToShared("LANGUAGE", "0");
                break;
            case R.id.language_rl_implified:
                config.locale = Locale.CHINA;
                iamgeview1.setVisibility(View.GONE);
                iamgeview2.setVisibility(View.VISIBLE);
                iamgeview3.setVisibility(View.GONE);

                title.setText("语言选择");
                fantiText.setText("繁体");
                jiantiText.setText("简体");

                MyApplication.Language="1";
                SharedPreferenceUtil.setInfoToShared("LANGUAGE", "1");
                break;
            case R.id.language_rl_english:
                config.locale = Locale.ENGLISH;
                iamgeview1.setVisibility(View.GONE);
                iamgeview2.setVisibility(View.GONE);
                iamgeview3.setVisibility(View.VISIBLE);

                title.setText("Language selection");
                fantiText.setText("Traditional Chinese character");
                jiantiText.setText("Simplified Chinese character");

                MyApplication.Language="2";
                SharedPreferenceUtil.setInfoToShared("LANGUAGE", "2");
                break;

            default:
                config.locale = Locale.getDefault();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        EventBus.getDefault().post(new SwitchFragmentEvent(3));
        finish();
    }
}
