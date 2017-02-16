package com.yitong.yoga.adapter;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.TextViewCompat;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yitong.yoga.R;
import com.yitong.yoga.UserManager;
import com.yitong.yoga.bean.LvMenuItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * YoGa
 * Created by Chb on  2016/10/20 10:26
 */

public  class MenuItemAdapter extends BaseAdapter
{
    private final int mIconSize;
    private LayoutInflater mInflater;
    private Context mContext;
    private List<LvMenuItem> mItems ;
    public MenuItemAdapter(Context context)
    {
        mInflater = LayoutInflater.from(context);
        mContext = context;

        mIconSize = context.getResources().getDimensionPixelSize(R.dimen.drawer_icon_size);

        if(UserManager.getInstance().isLogin()){
            mItems=new ArrayList<>(
                    Arrays.asList(
                            new LvMenuItem(R.drawable.ic_menu_camera, mContext.getResources().getString(R.string.menu_wdzh)),
                            new LvMenuItem(R.drawable.ic_menu_gallery,mContext.getResources().getString(R.string.menu_wdls)),
//                            new LvMenuItem(R.drawable.ic_menu_manage,mContext.getResources().getString(R.string.menu_yyxz)),
                            new LvMenuItem(R.drawable.ic_call_black_24dp, mContext.getResources().getString(R.string.menu_lxwm)),
                            new LvMenuItem(R.drawable.ic_menu_send, mContext.getResources().getString(R.string.menu_gywm)),
                            new LvMenuItem(),
                            new LvMenuItem(mContext.getResources().getString(R.string.menu_zhgl)),
                            new LvMenuItem(R.drawable.ic_menu_share, mContext.getResources().getString(R.string.menu_zc)),
                            new LvMenuItem(R.drawable.ic_menu_manage,mContext.getResources().getString(R.string.menu_xgmm)),
                            new LvMenuItem(R.drawable.ic_menu_slideshow, mContext.getResources().getString(R.string.menu_dc))
                    ));
        }else{
            mItems=new ArrayList<>(
                    Arrays.asList(
                            new LvMenuItem(R.drawable.ic_menu_camera, mContext.getResources().getString(R.string.menu_wdzh)),
                            new LvMenuItem(R.drawable.ic_menu_gallery,mContext.getResources().getString(R.string.menu_wdls)),
//                            new LvMenuItem(R.drawable.ic_menu_manage,mContext.getResources().getString(R.string.menu_yyxz)),
                            new LvMenuItem(R.drawable.ic_call_black_24dp, mContext.getResources().getString(R.string.menu_lxwm)),
                            new LvMenuItem(R.drawable.ic_menu_send, mContext.getResources().getString(R.string.menu_gywm)),
                            new LvMenuItem(),
                            new LvMenuItem(mContext.getResources().getString(R.string.menu_zhgl)),
                            new LvMenuItem(R.drawable.ic_menu_share, mContext.getResources().getString(R.string.menu_zc)),
                            new LvMenuItem(R.drawable.ic_menu_manage,mContext.getResources().getString(R.string.menu_xgmm))
                    ));

        }

    }



    @Override
    public int getCount()
    {
        return mItems.size();
    }


    @Override
    public Object getItem(int position)
    {
        return mItems.get(position);
    }


    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public int getViewTypeCount()
    {
        return 3;
    }

    @Override
    public int getItemViewType(int position)
    {
        return mItems.get(position).getType();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LvMenuItem item = mItems.get(position);
        switch (item.getType())
        {
            case LvMenuItem.TYPE_NORMAL:
                if (convertView == null)
                {
                    convertView = mInflater.inflate(R.layout.design_drawer_item, parent,
                            false);
                }
//                TextView itemView = (TextView) convertView.findViewById(R.id.list_item_textview);
                TextView itemView = (TextView) convertView;
                itemView.setText(item.getName());
                Drawable icon = null;
                if(item.getIcon()!=0){
                     icon = mContext.getResources().getDrawable(item.getIcon());
                }
               
                Drawable rightIcon=mContext.getResources().getDrawable(R.drawable.right_icon);

                setIconColor(icon);
                setIconColor(rightIcon);
                if (icon != null)
                {
                    icon.setBounds(0, 0, mIconSize, mIconSize);
                    rightIcon.setBounds(0, 0, mIconSize, mIconSize);
                    TextViewCompat.setCompoundDrawablesRelative(itemView, icon, null, rightIcon, null);
                }

                break;
            case LvMenuItem.TYPE_NO_ICON:
                if (convertView == null)
                {
                    convertView = mInflater.inflate(R.layout.design_drawer_item_subheader,
                            parent, false);
                }
                TextView subHeader = (TextView) convertView.findViewById(R.id.list_item_textview);
                subHeader.setText(item.getName());
                break;
            case LvMenuItem.TYPE_SEPARATOR:
                if (convertView == null)
                {
                    convertView = mInflater.inflate(R.layout.design_drawer_item_separator,
                            parent, false);
                }
                break;
        }

        return convertView;
    }

    private void setIconColor(Drawable icon)
    {
        int textColorSecondary = android.R.attr.textColorSecondary;
        TypedValue value = new TypedValue();
        if (!mContext.getTheme().resolveAttribute(textColorSecondary, value, true))
        {
            return;
        }
        int baseColor = mContext.getResources().getColor(value.resourceId);
        icon.setColorFilter(baseColor, PorterDuff.Mode.MULTIPLY);
    }
}
