package com.yitong.yoga.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.yitong.yoga.MyApplication;
import com.yitong.yoga.R;
import com.yitong.yoga.adapter.MenuItemAdapter;
import com.yitong.yoga.bean.SwitchFragmentEvent;
import com.yitong.yoga.fragment.ReservationRecordFragment;
import com.yitong.yoga.fragment.TimeTablesFragment;
import com.yitong.yoga.utils.SharedPreferenceUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Locale;

import me.majiajie.pagerbottomtabstrip.Controller;
import me.majiajie.pagerbottomtabstrip.PagerBottomTabLayout;
import me.majiajie.pagerbottomtabstrip.TabItemBuilder;
import me.majiajie.pagerbottomtabstrip.TabLayoutMode;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectListener;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Fragment> list = new ArrayList<>();
    private ListView mLvLeftMenu;
//    private BottomNavigationView bottomNavigationView;
    private PagerBottomTabLayout bottomNavigationView;
    Controller controller;
    private MenuItemAdapter adapter;
    private View myView;
//    BottomNavigationItem bottomNavigationItem,bottomNavigationItem1;
//    int image[];
//    int color[];

//    private CalendarPickerView dialogView;
//    private AlertDialog theDialog;
//    final Calendar nextYear = Calendar.getInstance();
//    final Calendar lastYear = Calendar.getInstance();

    private void ShowFragment(int index) {
        switch (index) {
            case 0:
                if (!list.get(0).isAdded()) { // 先判断是否被add过
                    getSupportFragmentManager().beginTransaction().add(R.id.framlayout, list.get(0)).hide(list.get(1))
                            .show(list.get(0)).commitAllowingStateLoss();// 隐藏当前的fragment，add下一个到Activity中
                    // getSupportFragmentManager(). executePendingTransactions();

                } else {
                    getSupportFragmentManager().beginTransaction().hide(list.get(1))
                            .show(list.get(0)).commitAllowingStateLoss();
                    // getSupportFragmentManager(). executePendingTransactions();
                }
                break;
            case 1:
                if (!list.get(1).isAdded()) { // 先判断是否被add过
                    getSupportFragmentManager().beginTransaction().add(R.id.framlayout, list.get(1)).hide(list.get(0))
                            .show(list.get(1)).commitAllowingStateLoss();// 隐藏当前的fragment，add下一个到Activity中
                    // getSupportFragmentManager(). executePendingTransactions();

                } else {
                    getSupportFragmentManager().beginTransaction().hide(list.get(0))
                            .show(list.get(1)).commitAllowingStateLoss();
                    // getSupportFragmentManager(). executePendingTransactions();
                }
                break;
            default:
                break;
        }
    }

//    private void getBottomNavigationView() {
//
//      int[] image = new int[]{R.drawable.ic_favorite_black_24dp,
//                R.drawable.ic_book_black_24dp};
////        int[] image = {R.drawable.ic_chevron_left_black_24dp,
////                R.drawable.ic_chevron_right_black_24dp};
//       int[] color=new int[]{
//                ContextCompat.getColor(this, R.color.thirdColor), ContextCompat.getColor(this, R.color.fourthColor)};
//
//        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigation);
//        if (bottomNavigationView != null) {
//            bottomNavigationView.isWithText(false);
////            bottomNavigationView.activateTabletMode();
//            bottomNavigationView.isColoredBackground(true);
////            bottomNavigationView.willNotRecreate(true);
//            bottomNavigationView.setTextActiveSize(getResources().getDimension(R.dimen.text_active));
//            bottomNavigationView.setTextInactiveSize(getResources().getDimension(R.dimen.text_inactive));
////            bottomNavigationView.setItemActiveColorWithoutColoredBackground(ContextCompat.getColor(this, R.color.firstColor));
//            bottomNavigationView.setFont(Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/Noh_normal.ttf"));
//
//        }
//
//        BottomNavigationItem  bottomNavigationItem = new BottomNavigationItem
//                (getResources().getString(R.string.timetable), color[0], image[0]);
//        BottomNavigationItem bottomNavigationItem1 = new BottomNavigationItem
//                (getResources().getString(R.string.reservation_record), color[1], image[1]);
//
//        bottomNavigationView.addTab(bottomNavigationItem);
//
//        bottomNavigationView.addTab(bottomNavigationItem1);
////        bottomNavigationView.disableShadow();
//    }


    private void getBottomNavigationView() {

        int[] image = new int[]{R.drawable.ic_favorite_black_24dp,
                R.drawable.ic_book_black_24dp};
//        int[] image = {R.drawable.ic_chevron_left_black_24dp,
//                R.drawable.ic_chevron_right_black_24dp};
        int[] color=new int[]{
                ContextCompat.getColor(this, R.color.thirdColor), ContextCompat.getColor(this, R.color.fourthColor)};

        bottomNavigationView = (PagerBottomTabLayout) findViewById(R.id.bottomNavigation);
//用TabItemBuilder构建一个导航按钮
        TabItemBuilder tabItemBuilder = new TabItemBuilder(this).create()
                .setDefaultIcon(image[0])
                .setText(getResources().getString(R.string.timetable))
                .setSelectedColor(color[0])
                .setTag("A")
                .build();

        //构建导航栏,得到Controller进行后续控制
        controller = bottomNavigationView.builder()
                .addTabItem(tabItemBuilder)
                .addTabItem(image[1], getResources().getString(R.string.reservation_record), color[1])
//                .setMode(TabLayoutMode.HIDE_TEXT)
//                .setMode(TabLayoutMode.CHANGE_BACKGROUND_COLOR)
                .setMode(TabLayoutMode.HIDE_TEXT | TabLayoutMode.CHANGE_BACKGROUND_COLOR)
                .build();

//        controller.setMessageNumber("A",2);
//        controller.setDisplayOval(0,true);

        controller.addTabItemClickListener(listener);
    }
    OnTabItemSelectListener listener = new OnTabItemSelectListener() {
        @Override
        public void onSelected(int index, Object tag) {
            Log.i("asd", "onSelected:" + index + "   TAG: " + tag.toString());
            ShowFragment(index);
        }

        @Override
        public void onRepeatClick(int index, Object tag) {
            Log.i("asd", "onRepeatClick:" + index + "   TAG: " + tag.toString());
        }
    };
    private void setUpDrawer() {
        addHeadview();
        adapter = new MenuItemAdapter(this);
        mLvLeftMenu.setAdapter(adapter);
        mLvLeftMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {

                    case 1:
                        closeDrawLayout();
                        startActivity(new Intent(MainActivity.this, MyAccountActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(MainActivity.this, BookingHistoryActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(MainActivity.this, LanguageSelectionActivity.class));
                        break;
                    case 4:
                        startActivity(new Intent(MainActivity.this, ContactUsActivity.class));
                        break;
                    case 5:
                        startActivity(new Intent(MainActivity.this, AboutUsActivity.class));
                        break;
                    case 8:
                        startActivity(new Intent(MainActivity.this, RegisterActivity.class));
                        break;
                    case 9:
                        startActivity(new Intent(MainActivity.this, ModifyPasswordActivity.class));
                        break;
                    case 10:
                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MainActivity.this);
                        builder.setTitle(getResources().getString(R.string.hint_for_logout))
                                .setMessage(getResources().getString(R.string.hint_logout))
                                .setPositiveButton(getResources().getString(R.string.yes),
                                        new DialogInterface.OnClickListener() {

                                            @Override
                                            public void onClick(DialogInterface dialog,
                                                                int which) {
                                                dialog.dismiss();
                                                closeDrawLayout();
                                            }
                                        })
                                .setNegativeButton(getResources().getString(R.string.no),
                                        new DialogInterface.OnClickListener() {

                                            @Override
                                            public void onClick(DialogInterface dialog,
                                                                int which) {
                                                dialog.dismiss();
                                            }
                                        }).show();
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void addHeadview() {
        LayoutInflater inflater = LayoutInflater.from(this);
         myView = inflater.inflate(R.layout.nav_header_main, mLvLeftMenu, false);
        TextView login = (TextView) myView.findViewById(R.id.tologin);
        login.setText(getResources().getString(R.string.menu_qdl));
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
        mLvLeftMenu.addHeaderView(myView);
    }

    private void closeDrawLayout() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSwitchFragmentEvent(SwitchFragmentEvent event) {

        if (null != event) {
            if (event.getPosition() == 0) {
//                bottomNavigationView.selectTab(0);
                controller.setSelect(0);
            } else if (event.getPosition() == 1) {
//                bottomNavigationView.selectTab(1);
                controller.setSelect(1);
            } else if (event.getPosition() == 3) {
                changLanguage();

                bottomNavigationView.removeAllViews();
                getBottomNavigationView();

                mLvLeftMenu.removeHeaderView(myView);
                addHeadview();
                adapter = new MenuItemAdapter(this);
                mLvLeftMenu.setAdapter(adapter);
            }

        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void openOrClose() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            drawer.openDrawer(GravityCompat.START);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        changLanguage();

        setContentView(R.layout.drawerlayout);
        EventBus.getDefault().register(this);

        getBottomNavigationView();
        mLvLeftMenu = (ListView) findViewById(R.id.id_lv_left_menu);

//        nextYear.add(Calendar.MONTH, 1);
//        lastYear.add(Calendar.MONTH, -1);
//        initCalendarInDialog("日期选择", R.layout.dialog);

        TimeTablesFragment fragment1 = new TimeTablesFragment();
        ReservationRecordFragment fragment2 = new ReservationRecordFragment();
        list.add(fragment1);
        list.add(fragment2);

        getSupportFragmentManager().beginTransaction().add(R.id.framlayout, list.get(0))
                .show(list.get(0)).commit();

//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);
        setUpDrawer();
//        bottomNavigationView.selectTab(0);
        controller.setSelect(0);
//        bottomNavigationView.setOnBottomNavigationItemClickListener(new OnBottomNavigationItemClickListener() {
//            @Override
//            public void onNavigationItemClick(int index) {
//                ShowFragment(index);
//            }
//        });


    }

    private void changLanguage() {
        String language = SharedPreferenceUtil.getInfoFromShared("LANGUAGE", "0");
        Configuration config = getResources().getConfiguration();
        DisplayMetrics dm = getResources().getDisplayMetrics();
        switch (language) {
            case "0":
                config.locale = Locale.TAIWAN;
                MyApplication.Language = "0";
                break;
            case "1":
                config.locale = Locale.CHINA;
                MyApplication.Language = "1";
                break;
            case "2":
                config.locale = Locale.ENGLISH;
                MyApplication.Language = "2";
                break;
            default:

                break;
        }
        getResources().updateConfiguration(config, dm);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


//    private void initCalendarInDialog(String title, int layoutResId) {
////        dialogView = (CalendarPickerView) LayoutInflater.from(getActivity()).inflate(layoutResId, null, false);
//        dialogView = (CalendarPickerView) getLayoutInflater().inflate(layoutResId, null, false);
//        dialogView.init(lastYear.getTime(), nextYear.getTime()) //
//                .withSelectedDate(new Date());
//
//        theDialog = new AlertDialog.Builder(this) //
//                .setTitle(title)
//                .setView(dialogView)
//                .setNeutralButton("确定", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        dialogInterface.dismiss();
//                    }
//                })
//                .create();
//        theDialog.setOnShowListener(new DialogInterface.OnShowListener() {
//            @Override
//            public void onShow(DialogInterface dialogInterface) {
//                Log.d("manactivity", "onShow: fix the dimens!");
//                dialogView.fixDialogDimens();
//            }
//        });
//
////        theDialog.show();
////        theDialog.dismiss();
//    }
//
//    public void showDialog() {
//        if (null != theDialog)
//            theDialog.show();
//
//    }


}
