package com.yitong.yoga.stickyListHeaders;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.yitong.yoga.R;
import com.yitong.yoga.activity.CourseIntroductionActivity;

import java.util.ArrayList;
import java.util.List;

public class StickylistAdapter extends BaseAdapter implements
        StickyListHeadersAdapter, SectionIndexer {
    private List<StickyListBean> list = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context context;

    public StickylistAdapter(Context context, List<StickyListBean> list) {
        this.list = list;
        this.context=context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    public void setData(List<StickyListBean> alarmList) {
        this.list = alarmList;
    }

    @Override
    public StickyListBean getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder  mHolder ;
        if (convertView == null) {
            convertView = mInflater
                    .inflate(R.layout.item_sticky, parent, false);
            mHolder = new ViewHolder();
            mHolder.tvTime = (TextView) convertView.findViewById(R.id.item);
            mHolder.class_time_flag = (TextView) convertView.findViewById(R.id.class_time_flag);
            mHolder.class_time = (TextView) convertView.findViewById(R.id.class_time);
            mHolder.class_location = (TextView) convertView.findViewById(R.id.class_location);
            mHolder.teacher = (TextView) convertView.findViewById(R.id.teacher_name);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        StickyListBean info = getItem(position);
        mHolder.tvTime.setText(info.getContent());
        mHolder.teacher.setText(info.getClassTeacher());
        mHolder.class_location.setText(info.getClassLocation());
        mHolder.class_time.setText(info.getClassTime());
        mHolder.class_time_flag.setText(info.getClassTimeFlag());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, CourseIntroductionActivity.class));
            }
        });
        return convertView;
    }

    private static class ViewHolder {
        private TextView tvTime;
        private TextView class_time_flag;
        private TextView class_time;
        private TextView class_location;
        private TextView teacher;
    }

    @Override
    public Object[] getSections() {
        return null;
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        return 0;
    }

    @Override
    public int getSectionForPosition(int position) {
        if (position < getCount()) {
            return getItem(position).getSection();
        }
        return 0;
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderViewHolder holder;

        if (convertView == null) {
            holder = new HeaderViewHolder();
            convertView = mInflater.inflate(R.layout.item_sticky_header,
                    parent, false);
            holder.text = (TextView) convertView.findViewById(R.id.header);

            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }

        String headerText = getItem(position).getYM();
        holder.text.setText(headerText);

        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        if (position < getCount()) {
            return getItem(position).getSection();
        }
        return 0;
    }

    private class HeaderViewHolder {
        TextView text;

    }

}
