package com.yitong.yoga.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationItem;
import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationView;
import com.luseen.luseenbottomnavigation.BottomNavigation.OnBottomNavigationItemClickListener;
import com.yitong.yoga.R;
import com.yitong.yoga.adapter.MenuItemAdapter;
import com.yitong.yoga.bean.SwitchFragmentEvent;
import com.yitong.yoga.fragment.ReservationRecordFragment;
import com.yitong.yoga.fragment.TimeTablesFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //    private BottomNavigationView bottomNavigationView;
    private ArrayList<Fragment> list = new ArrayList<>();
    private ListView mLvLeftMenu;

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

    private BottomNavigationView getBottomNavigationView() {
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigation);

        int[] image = {R.drawable.ic_favorite_black_24dp,
                R.drawable.ic_book_black_24dp};
//        int[] image = {R.drawable.ic_chevron_left_black_24dp,
//                R.drawable.ic_chevron_right_black_24dp};
        int[] color = {
                ContextCompat.getColor(this, R.color.thirdColor), ContextCompat.getColor(this, R.color.fourthColor)};

        BottomNavigationItem bottomNavigationItem = new BottomNavigationItem
                ("課程表", color[0], image[0]);
        BottomNavigationItem bottomNavigationItem1 = new BottomNavigationItem
                ("預定記錄", color[1], image[1]);

        if (bottomNavigationView != null) {
            bottomNavigationView.isWithText(false);
            // bottomNavigationView.activateTabletMode();
            bottomNavigationView.isColoredBackground(true);
            bottomNavigationView.setTextActiveSize(getResources().getDimension(R.dimen.text_active));
            bottomNavigationView.setTextInactiveSize(getResources().getDimension(R.dimen.text_inactive));
//            bottomNavigationView.setItemActiveColorWithoutColoredBackground(ContextCompat.getColor(this, R.color.firstColor));
            bottomNavigationView.setFont(Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/Noh_normal.ttf"));

            bottomNavigationView.addTab(bottomNavigationItem);
            bottomNavigationView.addTab(bottomNavigationItem1);
        }

        return bottomNavigationView;
    }

    //    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        // Handle navigation view item clicks here.
//        int id = item.getItemId();
//
//        if (id == R.id.nav_camera) {
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
//    }
    private void setUpDrawer() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View myView = inflater.inflate(R.layout.nav_header_main, mLvLeftMenu, false);
        TextView login = (TextView) myView.findViewById(R.id.tologin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
        mLvLeftMenu.addHeaderView(myView);
        mLvLeftMenu.setAdapter(new MenuItemAdapter(this));
        mLvLeftMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Log.e("onItemClick", position + "");

                switch (position) {

                    case 1:
                        closeDrawLayout();
                        startActivity(new Intent(MainActivity.this, MyAccountActivity.class));
                        break;
                    case 2:
//                        closeDrawLayout();
                        startActivity(new Intent(MainActivity.this, BookingHistoryActivity.class));
                        break;
                    case 3:
//                        closeDrawLayout();
                        startActivity(new Intent(MainActivity.this, LanguageSelectionActivity.class));
                        break;
                    case 4:
//                        closeDrawLayout();
                        startActivity(new Intent(MainActivity.this, ContactUsActivity.class));
                        break;
                    case 5:
//                        closeDrawLayout();
                        startActivity(new Intent(MainActivity.this, AboutUsActivity.class));
                        break;
                    case 8:
//                        closeDrawLayout();
                        startActivity(new Intent(MainActivity.this, RegisterActivity.class));
                        break;
                    case 9:
//                        closeDrawLayout();
                        startActivity(new Intent(MainActivity.this, ModifyPasswordActivity.class));
                        break;
                    case 10:
                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("溫馨提示")
                                .setMessage("您確定登出用戶？")
                                .setPositiveButton("是",
                                        new DialogInterface.OnClickListener() {

                                            @Override
                                            public void onClick(DialogInterface dialog,
                                                                int which) {
                                                dialog.dismiss();
                                                closeDrawLayout();
                                            }
                                        })
                                .setNegativeButton("否",
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

    private void closeDrawLayout() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSwitchFragmentEvent(SwitchFragmentEvent event) {

        if (null != event) {
            Log.e("MainActivity", event.getPosition() + "");
            if (event.getPosition() == 0) {
//                ShowFragment(0);
                getBottomNavigationView().selectTab(0);
            } else if (event.getPosition() == 1) {
//                ShowFragment(1);
                getBottomNavigationView().selectTab(1);
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
        setContentView(R.layout.drawerlayout);


        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        EventBus.getDefault().register(this);
        BottomNavigationView bottomNavigationView = getBottomNavigationView();
        mLvLeftMenu = (ListView) findViewById(R.id.id_lv_left_menu);

//        nextYear.add(Calendar.MONTH, 1);
//        lastYear.add(Calendar.MONTH, -1);
//        initCalendarInDialog("日期选择", R.layout.dialog);

        TimeTablesFragment fragment1 = new TimeTablesFragment();
        ReservationRecordFragment  fragment2 = new ReservationRecordFragment();
        list.add(fragment1);
        list.add(fragment2);

        getSupportFragmentManager().beginTransaction().add(R.id.framlayout, list.get(0))
                .show(list.get(0)).commit();

//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);
        setUpDrawer();

        bottomNavigationView.setOnBottomNavigationItemClickListener(new OnBottomNavigationItemClickListener() {
            @Override
            public void onNavigationItemClick(int index) {
                ShowFragment(index);
            }
        });


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
